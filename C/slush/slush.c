#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <pwd.h>
#include <signal.h>

#define BUFFERSIZE 256

pid_t cpid;

void sig_handler(int signum) {
        kill(cpid, SIGTERM);
        printf("\n");
}


int main(int argc, char* argv[]) {

        char input[BUFFERSIZE];
        while (1) {

        char* homedir = getenv("HOME");
        char cwd[BUFFERSIZE];
        getcwd(cwd, BUFFERSIZE);

        char homedirCopy[BUFFERSIZE];
        strcpy(homedirCopy, homedir);

        char* homeTok;
        char* curTok;
        char* homeRem;
        char* curRem;
        char* restoreHome;

        homeTok = strtok(homedirCopy, "/");
        homeRem = strtok(NULL, "");
        curTok = strtok(cwd, "/");
        curRem = strtok(NULL, "");


        while (1) {

                if (homeRem == NULL) {
                        break;
                }

                homeTok = strtok(homeRem, "/");
                homeRem = strtok(NULL, "");
                curTok = strtok(curRem, "/");
                curRem = strtok(NULL, "");

        }

        if (curRem != NULL) {
                printf("slush|%s>", curRem);
        }
        else {
                printf("slush|>");
        }



        if (fgets(input, BUFFERSIZE, stdin) == 0) {
                //EOF detected
                exit(0);
        }

        if (input[0] == 10) {
                continue;
        }

        char* commands[15];
        char* arguments[16][15];
        char* outerToken;
        char* innerToken;

        outerToken = strtok(input, "\n");
        outerToken = strtok(outerToken, "(");

        int flag = 0;
        int i = 0;
        while (1) {
                if (strcmp(outerToken, " ") == 0) {
                        flag = 1;
                        break;
                }
                commands[i] = outerToken;
                outerToken = strtok(NULL, "(");
                if (outerToken == NULL) {
                        break;
                }
                i++;
        }

        if (flag == 1) {
                printf("invalid syntax\n");
                continue;
        }

        commands[i+1] = NULL;


        int flag2 = 0;
        for (int k = 0; k <= i; k++) {
                innerToken = strtok(commands[k], " ");
                int k2 = 0;
                while (1) {

                        arguments[k][k2] = innerToken;
                        innerToken = strtok(NULL, " ");
                        if (innerToken == NULL) {
                                break;
                        }
                        k2++;
                        if (k2 == 15) {
                                flag2 = 1;
                                break;
                        }
                }


                if (k2 == 15) {
                        printf("too many arguments: max 15 arguments\n");
                        continue;
                }
                arguments[k][k2+1] = NULL;
        }

        if (flag2 == 1) {
                continue;
        }

        //only one command
        if (i == 0) {

                char* cmd = arguments[0][0];

                if (strcmp(cmd, "cd") == 0) {

                        char nwd[BUFFERSIZE];

                        if (arguments[0][1] == NULL) {
                                strcpy(nwd, homedir);
                        }
                        else {
                                if (strcmp(arguments[0][1], "..") == 0) {
                                        char cwdTemp[BUFFERSIZE];
                                        char* cwdTok;
                                        char* cwdRem;
                                        char* homeTokTemp;
                                        char* homeTempRem;
                                        getcwd(cwdTemp, BUFFERSIZE);

                                        strcpy(homedirCopy, homedir);

                                        char* dirToks[BUFFERSIZE];

                                        cwdTok = strtok(cwdTemp, "/");
                                        cwdRem = strtok(NULL, "");
                                        homeTokTemp = strtok(homedirCopy, "/");
                                        homeTempRem = strtok(NULL, "");

                                        int startPoint = 0;
                                        int totalToks = 0;
                                        while (1) {
                                                dirToks[totalToks] = cwdTok;
                                                if (homeTokTemp != NULL) {
                                                        homeTokTemp = strtok(homeTempRem, "/");
                                                        homeTempRem = strtok(NULL, "");
                                                        startPoint++;
                                                }
                                                cwdTok = strtok(cwdRem, "/");
                                                cwdRem = strtok(NULL, "");
                                                if (cwdTok == NULL) {
                                                        break;
                                                }
                                                totalToks++;
                                        }

                                        nwd[0] = 0;

                                        if (startPoint == totalToks) {
                                                strcpy(nwd, homedir);
                                        }
                                        else {
                                                for (startPoint; startPoint < totalToks; startPoint++) {
                                                        strcat(nwd, dirToks[startPoint]);
                                                        strcat(nwd, "/");
                                                }
                                        }
                                        chdir(homedir);
                                }
                                else {
                                        getcwd(nwd, BUFFERSIZE);
                                        strcat(nwd, "/");
                                        strcat(nwd, arguments[0][1]);
                                }
                        }

                        int ret = chdir(nwd);
                        if (ret == -1) {
                                perror("error changing directory");
                        }

                }
                else {

                //pid_t
                cpid = fork();
                if (cpid == 0) {

                        int ret = execvp(cmd, arguments[0]);
                        if (ret == -1) {
                                perror("error executing start");
                        }
                        return 0;
                }
                signal(SIGINT, sig_handler);
                waitpid(cpid, NULL, 0);
                }
        }

        else {

        pid_t childPIDs[i];


        int pipefd[2];
        pipe(pipefd);

        for (int k = i; k >= 0; k--) {

                //first
                if (k == i) {

                        //pid_t
                        cpid = fork();
                        childPIDs[i-k] = cpid;
                        if (cpid == 0) {

                                dup2(pipefd[1], STDOUT_FILENO);
                                close(pipefd[0]);

                                char* cmd = arguments[k][0];

                                int ret = execvp(cmd, arguments[k]);
                                if (ret == -1) {
                                        perror("error executing start");
                                }
                                return 0;
                        }
                }

                //last
                else if (k == 0) {

                        //pid_t
                        cpid = fork();
                        childPIDs[i-k] = cpid;
                        if (cpid == 0) {

                                dup2(pipefd[0], STDIN_FILENO);
                                close(pipefd[1]);

                                char* cmd = arguments[k][0];

                                int ret = execvp(cmd, arguments[k]);
                                if (ret == -1) {
                                        perror("error executing last");
                                }
                                return 0;
                        }
                }


                //middle
                else {

                        //pid_t
                        cpid = fork();
                        childPIDs[i-k] = cpid;
                        if (cpid == 0) {

                                dup2(pipefd[0], STDIN_FILENO);
                                close(pipefd[1]);
                                dup2(pipefd[1], STDOUT_FILENO);

                                char* cmd = arguments[1][0];
                                int ret = execvp(cmd, arguments[k]);
                                if (ret == -1) {
                                        perror("error executing middle");
                                }
                                return 0;
                        }
                }
        }
        close(pipefd[0]);
        close(pipefd[1]);




        for (int k = 0; k <= i; k++) {
                signal(SIGINT, sig_handler);
                waitpid(childPIDs[k], NULL, 0);
        }
        }
        }
        return 0;
}
