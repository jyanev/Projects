#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <crypt.h>
#include <pthread.h>

#define BUFFERSIZE 100

struct vars {
        int iterStart;
        int keysize;
        int iterEnd;
        char saltlessTarget[12];
        char currentGuess[9];
        char str[BUFFERSIZE];
        int t;
        char salt[3];
};


int checkPassword(char* target, char* guess, char* salt) {

        char* result;

        // borrowed from https://stackoverflow.com/questions/9335777/crypt-r-example
        struct crypt_data data;  //
        data.initialized = 0;    //
        ///////////////////////////

        result = crypt_r(guess, salt, &data);

        if (result == NULL) {
                perror("Crypt() failed");
                return -1;
        }
        char saltlessResult[12];
        strncpy(saltlessResult, result+2, 12);

        if (strcmp(saltlessResult, target) == 0) {
                return 1;
        }
        return 0;
}



void* thread_crack(void* args) {

        char alphabet[26] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        int position = 0;
        int letter = 1;

        struct vars* arg_ptr = (struct vars*) args;

        char* currentGuess = arg_ptr->currentGuess;
        char* saltlessTarget = arg_ptr->saltlessTarget;

        int start = arg_ptr->iterStart;
        int place = start;
        int end = arg_ptr->iterEnd;
        int keysize = arg_ptr->keysize;
        int threadNum = arg_ptr->t;
        char* salt = arg_ptr->salt;

        int i = 1;
        int rem;
        while (i < keysize) {

                rem = place % 26;
                currentGuess[i] = alphabet[rem];
                place /= 26;
                i++;
        }

        for (start; start < end; start++) {

                for (int k=0; k < 25; k++) {

                        if (checkPassword(saltlessTarget, currentGuess, salt) == 1) {
                                printf("Password cracked: %s\n", currentGuess);
                                exit(0);
                        }

                        currentGuess[position] = alphabet[letter];
                        letter++;
                }


                saltlessTarget[11] = 0;

                if (checkPassword(saltlessTarget, currentGuess, salt) == 1) {
                        printf("Password cracked: %s\n", currentGuess);
                        exit(0);
                }

                while (currentGuess[position] == 122) {
                        position++;
                }

                letter = currentGuess[position] - 96;
                currentGuess[position] = alphabet[letter];
                letter = 0;

                while (position > 0) {
                        position--;
                        currentGuess[position] = alphabet[letter];
                }
                position = 0;
                letter++;



        }
        return NULL;
}


int main(int argc, char* argv[]) {



        if (argc != 4) {
                printf("Three arguments expected, but %d given\nUsage: ./crack <threads> <keysize> <target>\n", (argc-1));
                exit(0);
        }


        int numThreads = atoi(argv[1]);
        int keysize = atoi(argv[2]);
        char* target = argv[3];
        char salt[3];
        salt[0] = target[0];
        salt[1] = target[1];
        salt[2] = 0;

        long totalIterations = 1;

        for (int x=1; x <= keysize; x++) {

                int itersPerThread = totalIterations / numThreads;
                pthread_t threadsArray[numThreads];
                struct vars structsArray[numThreads];


        for (int thread = 0; thread < numThreads; thread++) {
                structsArray[thread].t = thread;
                snprintf(structsArray[thread].str, BUFFERSIZE, "Thread %d", (thread+1));
                structsArray[thread].keysize = x;
                for (int i=0; i < 3; i++) {
                        structsArray[thread].salt[i] = salt[i];
                }

                if (thread == 0) {
                        structsArray[thread].iterStart  = 0;
                        structsArray[thread].iterEnd = itersPerThread;
                }
                else if (thread == numThreads - 1) {
                        structsArray[thread].iterStart = itersPerThread * thread;
                        structsArray[thread].iterEnd = totalIterations;
                }
                else {
                        structsArray[thread].iterStart = itersPerThread * thread;
                        structsArray[thread].iterEnd = itersPerThread * (thread + 1);
                }
                strncpy(structsArray[thread].saltlessTarget, target+2, 12);
                for (int i=0; i < x; i++) {
                        structsArray[thread].currentGuess[i] = 97;
                }
                structsArray[thread].currentGuess[x] = 0;

                struct vars *s_ptr = &structsArray[thread];
                pthread_create(&threadsArray[thread], NULL, thread_crack, s_ptr);


        }
        totalIterations *= 26;


        for (int i=0; i < numThreads; i++) {
                pthread_join(threadsArray[i], NULL);
        }
        }


        return 0;
}
