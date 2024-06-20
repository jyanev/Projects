#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <conio.h>

#define TRUE     1
#define FALSE    0


int days_in_month[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
char* weekdays[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
char* months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

int input_year(void) {
    int year;
	
	printf("Please enter a year (example: 1999) : ");
	scanf("%d", &year);

	return year; 
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
        printf("  *** JULIAN CALENDAR NOT IMPLEMENTED ***  ");
        // use Julian calculator
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
        if (day < 10) {
            printf("%d    ", day);
        } else {
            printf("%d   ", day);
        }
        weekday = (weekday + 1) % 7;
    }
    return weekday;
}

void process_user_input(int day, int month, int year) {

    day = print_month_info(day, month, year);
    
    printf("\n\n\n");
    printf("\033[0;33m");
    printf("Press 'd' for Next Month, 'a' for Previous Month, and 'q' to Quit.\nRed date indicates a note added to  date. Press 'x' to view note details: ");
    printf("\033[0m");

    int user_input;
    user_input = getch();
    printf("\n\n\n");

    switch (user_input) {
        case 'd':
            month++;
            if (month == 12) {
                year++;
                month = 0;
                is_leap_year(year);
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
            printf("  **NOT IMPLEMENTED**  ");
            day = calculate_first_day_of_month(month, year);
            process_user_input(day, month, year);
        case 'q':
            printf("Closing...");
            exit(0);
        default:
            printf("  **INVALID INPUT**  ");
            day = calculate_first_day_of_month(month, year);
            process_user_input(day, month, year);
            
    }
}

int main(void) {
    
    int year = input_year();
    int month = 0;
    int firstDay = calculate_first_day_of_month(month, year);
    is_leap_year(year);
    process_user_input(firstDay, month, year);
    
}
