#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>


#define TRUE               1
#define FALSE              0
#define MAX_NOTE_LENGTH   255


int days_in_month[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
char* weekdays[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
char* months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
char** notes;
int size;


void allocate_new_note_mem(int new_size) {
    
    notes = (char**)realloc(notes, (new_size * 12 * 31) * sizeof(char*));

    for (int i=(size * 12 * 31) ; i < (new_size * 12 * 31) ; i++) {
        notes[i] = (char*)malloc((MAX_NOTE_LENGTH + 1) * sizeof(char));
        sprintf(notes[i], "");
    }
    size = new_size;
}


void is_leap_year(int year) {
    if(year % 4 == FALSE && year % 100 != FALSE || year % 400 == FALSE) {
        days_in_month[1] = 29;
    } else {
	    days_in_month[1] = 28;
    }
}


int calculate_first_day_of_month(int month, int year) {
    int day;
    month++;
    if (month == 1 || month == 2) {
        year--;
        month += 12;
    }

    if (year > 1582) {
        day = ((((13*(month+1))/5 + (year%100) + (year%100)/4 + year/400 + 5*(year/100) + 1) % 7) + 6) % 7;
    } else {
        printf("  *** tf you doing back here??? your great great great grandmother hadnt even been born yet ***  ");
        free (notes);
        exit(0);
    }
    return day;
}


int print_month_info(int weekday, int month, int year) {
    
    printf("        ----------------------------------        \n");
    printf("                  %s, %d\n", months[month], year);
    printf("        ----------------------------------        \n");
    printf("        Sun  Mon  Tue  Wed  Thu  Fri  Sat\n");

    char startFormatting[41] = "        ";
    for (int i = 0 ; i < 5*weekday ; i++) {
        strcat(startFormatting, " ");
    }
    printf(startFormatting);

    int firstweek = 1;

    for (int day = 1 ; day <= days_in_month[month] ; day++) {
        if (weekday == 0 && firstweek == 0) {
            printf("\n        ");
        } else {
            firstweek = 0;
        }

        if (strcmp(notes[((year-1583) * 12 * 31) + ((month) * 31) + day - 1], "") != 0) {
            printf("\033[0;31m");
        } 

        if (day < 10) {
            printf("%d    ", day);
        } else {
            printf("%d   ", day);
        }
        weekday = (weekday + 1) % 7;
        printf("\033[0m");
    }
    return weekday;
}


void process_user_input(int day, int month, int year) {

    day = print_month_info(day, month, year);
    
    printf("\n\n\n");
    printf("\033[0;33m");
    printf("Press 'd' for Next Month, 'a' for Previous Month, and 'q' to Quit.\nRed date indicates a note added to date. Press 'x' to choose date: ");
    printf("\033[0m");

    int user_input;
    user_input = getchar();
    fflush(stdin);
    printf("\n\n\n");

    switch (user_input) {
        case 'd':
            month++;
            if (month == 12) {
                year++;
                month = 0;
                is_leap_year(year);
                if (year - 1582 > size) {
                    allocate_new_note_mem(year - 1582 + 50);
                }
            }
            process_user_input(day, month, year);

        case 'a':
            month--;
            if (month == -1) {
                year--;
                month = 11;
                is_leap_year(year);
            }
            day = calculate_first_day_of_month(month, year);
            process_user_input(day, month, year);

        case 'x':
            int d;
            printf("Which date would you like to view? : ");
            char temp_day[2];
            fgets(temp_day, 3, stdin);
            d = atoi(temp_day);
            fflush(stdin);
            printf("\n\nViewing day: %d\n\n", d);

            int index = ((year-1583) * 12 * 31) + ((month) * 31) + d  - 1;

            if (strcmp(notes[index], "") != 0) {
                printf("Note:\n-----\n%s\n\n", notes[index]);

                printf("Would you like to replace this note? (y/n) : ");
                user_input = getchar();
                fflush(stdin);
                printf("\n\n");
                if (user_input == 'y') {
                    printf("New note (max %d characters): ", MAX_NOTE_LENGTH);
                    char buffer[MAX_NOTE_LENGTH];
                    fgets(buffer, MAX_NOTE_LENGTH+1, stdin);
                    buffer[strcspn(buffer, "\n")] = 0;
                    notes[index] = buffer;
                } else {
                    printf("Would you like to remove this note? (y/n) : ");
                    user_input = getchar();
                    fflush(stdin);
                    printf("\n\n");
                    if (user_input == 'y') {
                        notes[index] = "";
                    }
                }

            } else {
                printf("No notes\n\n");
                printf("Would you like to add a note? (y/n) : ");
                user_input = getchar();
                fflush(stdin);
                printf("\n\n");
                if (user_input == 'y') {
                    printf("New note (max %d characters) : ", MAX_NOTE_LENGTH);
                    char buffer[MAX_NOTE_LENGTH];
                    fgets(buffer, MAX_NOTE_LENGTH+1, stdin);
                    buffer[strcspn(buffer, "\n")] = 0;
                    notes[index] = buffer;
                }
            }

            printf("\n\n\n");
            day = calculate_first_day_of_month(month, year);
            process_user_input(day, month, year);
        
        case 'q':
            printf("Saving and Closing...");
            FILE *save_file;
            save_file = fopen("notes.txt","w");
            char* reset = NULL;
            fwrite(reset, 1, 1, save_file);
            fclose(save_file);
            
            save_file = fopen("notes.txt","wb");

            for (int i=0 ; i < (size * 12 * 31) ; i++) {
                fprintf(save_file, "%s,", notes[i]);
            }
            fclose(save_file);
            free (notes);
            exit(0);

        default:
            printf("  **INVALID INPUT**  ");
            day = calculate_first_day_of_month(month, year);
            process_user_input(day, month, year);
    }
}


int main() {
    
    time_t current_time = time(NULL);
    struct tm time;
    time = *gmtime(&current_time);

    int month = time.tm_mon;
    int year = time.tm_year + 1900;

    int first_day = calculate_first_day_of_month(month, year);
    is_leap_year(year);

    size = (year - 1582);

    notes = (char**)malloc((size * 12 * 31) * sizeof(char*));

    for (int i=0 ; i < (size * 12 * 31) ; i++) {
        notes[i] = (char*)malloc((MAX_NOTE_LENGTH + 1) * sizeof(char));
        sprintf(notes[i], "");
    }

    FILE *save_file;
    save_file = fopen("notes.txt","r");

    char buffer[MAX_NOTE_LENGTH+1]; 
    int result;
    int i = 0;
    do {
        result = fscanf(save_file, "%[^,]", buffer);

        if(result == 0) {
            i++;
            result = fscanf(save_file, "%*c");
        } else {
            if (result != EOF) {
                if (i > (size * 12 * 31)) {
                    allocate_new_note_mem(size + 50);
                }
                notes[i] = buffer;
            }
        }

    } while(result != EOF);
    fclose(save_file);
    
    
    process_user_input(first_day, month, year);
    
}