Welcome to TiVOO!

Description:

This is a program to input XML calendars, filter them and then preview or output a HTML page.

Please run the main class to run the program! Have fun!


GUI instruction (Guide)

1. Load one or more XML files from your local disk (using the File -- Load file option on the menu).

2. Filter the events by specific method (using the File -- SelectFilter option on the menu):
   After the program pop-up a input dialogue, user should input a string (a keyword or a time) to find events that satisfy 
   user's requirement.
   2.1 Input format for a keyword:
        There is no specific format for a keyword, but notice that if user inputs a strange keyword, the webpage may not be able to show
        anything since events are all filtered out.
   2.2 Input format for time:
       2.2.1 Filter events at Time:
             This option requires user to input a specific time string. 
             The format is: yyyyMMdd'T'HHmmss , 
             where yyyy represents a 4-digit year; MM represents a 2-digit month; dd represents a 2-digit day;
             'T' is a literal character; HH represents a 2-digit hour; mm represents a 2-digit minute and ss represents a 2-digit second.
             For example: "20110926T100000" is a legal input
       2.2.2 Filter events between Time:
             This option requires user to input a string to indicate start and end time . 
             The format is: yyyyMMdd'T'HHmmss 'to' yyyyMMdd'T'HHmmss.
              For example: "20110926T100000 to 20110928T100000" is a legal input
              
 3. After user inputs a string to filter events, s/he can preview events through 'Preview Webpage' option