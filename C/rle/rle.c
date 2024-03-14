#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <string.h>
#include <errno.h>


int main(int argc, char* argv[]) {

        if ( argc != 5) {
                printf("Four arguments expected, but %d given\nThis Run-Length Encoding algorithm takes four arguments: 
                        <input filename> <output filename> <compression length> <mode bit>\n", argc);
        }

        else {
                char *inputFilename = argv[1];
                char *outputFilename = argv[2];
                int compLength = atoi(argv[3]);
                int mode = atoi(argv[4]);

                if (compLength < 1 || compLength > 256) {
                        printf("Compression length must be more than 1 and less than 256 bytes\n");
                        exit(0);
                }

                int inputFD;
                inputFD = open(inputFilename, O_RDONLY);
                if (inputFD == -1) {
                        perror("open");
                        exit(1);
                }

                int outputFD;
                outputFD = open(outputFilename, O_WRONLY | O_CREAT | O_TRUNC, S_IWUSR | S_IRUSR);
                if (outputFD == -1) {
                        perror("open");
                        exit(1);
                }

                char buffer[compLength];

                if (mode == 0) {
                        char pattern[compLength];
                        int counter = 1;
                        int errChck = read(inputFD, buffer, compLength);
                        if (errChck == -1) {
                                perror("read");
                                exit(1);
                        }
                        strcpy(pattern, buffer);

                        while (1) {
                                int curCount = read(inputFD, buffer, compLength);
                                if (curCount == -1) {
                                        perror("read");
                                        exit(1);
                                }
                                if (curCount == 0) {
                                        char reps[1];
                                        sprintf(reps, "%c", counter);
                                        errChck = write(outputFD, reps, 1);
                                        if (errChck == -1) {
                                                perror("write");
                                                exit(1);
                                        }

                                        errChck = write(outputFD, pattern, compLength);
                                        if (errChck == -1) {
                                                perror("write");
                                                exit(1);
                                        }

                                        break;
                                }

                                if (strcmp(pattern, buffer) == 0) {
                                        counter++;
                                }
                                else {
                                        char reps[1];
                                        sprintf(reps, "%c", counter);
                                        counter = 1;
                                        errChck = write(outputFD, reps, 1);
                                        if (errChck == -1) {
                                                perror("write");
                                                exit(1);
                                        }

                                        errChck = write(outputFD, pattern, compLength);
                                        if (errChck == -1) {
                                                perror("write");
                                                exit(1);
                                        }

                                        strcpy(pattern, buffer);
                                }
                        }
                        int cI = close(inputFD);
                        if (cI == -1) {
                                perror("close");
                                exit(1);
                        }
                        int cO = close(outputFD);
                        if (cO == -1) {
                                perror("close");
                                exit(1);
                        }
                }
                else if (mode == 1) {

                        int n;
                        int errChck;
                        char *mult[2];

                        int curCount = read(inputFD, mult, 1);
                        n = (int)(mult[0]);
                        if (curCount == -1) {
                                perror("read");
                                exit(1);
                        }
                        if (curCount == 0) {
                                exit(0);
                        }
                        else {
                                mult[1] = NULL;
                                curCount = read(inputFD, buffer, compLength);
                                if (curCount == -1) {
                                        perror("read");
                                        exit(1);
                                }
                                for (int i=0; i < n; i++) {
                                        errChck = write(outputFD, buffer, compLength);
                                        if (errChck == -1) {
                                                perror("write");
                                                exit(1);
                                        }
                                }
                        }
                        while (1) {
                                if (curCount == 0) {
                                        break;
                                }
                                else {
                                        curCount = read(inputFD, mult, 1);
                                        n = (int)(mult[0]);
                                        curCount = read(inputFD, buffer, compLength);
                                        if (curCount == -1) {
                                                perror("read");
                                                exit(1);
                                        }
                                        for (int i=0; i < n; i++) {
                                                errChck = write(outputFD, buffer, curCount);
                                                if (errChck == -1) {
                                                        perror("write");
                                                        exit(1);
                                                }
                                        }
                                }
                        }
                        int cI = close(inputFD);
                        if (cI == -1) {
                                perror("close");
                                exit(1);
                        }

                        int cO = close(outputFD);
                        if (cO == -1) {
                                perror("close");
                                exit(1);
                        }
                }
                else {
                        printf("The mode bit must be '0' for compression or '1' for decompression\n");
                        exit(0);
                }
        }
        return 0;
}
