--------------------------------------
Authors: John Yanev
---------------------------------------
Brief overview of program, including any known bugs:

Overview

This LanguageHelper class takes in a list of words that serves as a dictionary of "correctly spelled" words.
It then provides a list of suggestions for how to correctly spell an inputted word based on the dictionary,
assuming the word is not already spelled correctly


Methods

1. __init__(words)
The class is initialized with two variables, self._words, and self._chars
	1.1 self._words includes all of the words in a given dictionary of words ; these words are considered as correctly spelled
	1.2 self._chars includes all of the letter of the alphabet as well as an apostrophe and hyphen

2. __contains__(query)
using the following logic, returns True if the inputted query is contained within the dictionary of words, self._words, False otherwise
        2.1 the inputted word must be spelled the same way as is in self._words
        2.2 if the correct word is capitalized, such as Europe, then returns False if the inputted word is lowercase, ie. europe
        2.3 if the correct word is lowercase, such as dog, then returns True whether or not the inputted word is capitalized

3. __checkDict__(query)
behaves the same as __contains__, with one difference
        3.1 if the correct word is not capitalized, the returns True only if the inputted query is not capitalized, returns False otherwise

4. getSuggestions(query)
returns an empty list if the inputted query is found within the dictionary of words, self._words
otherwise, returns a list of suggestions for correctly spelling the inputted query

5. deleteLetter(query)
creates a set of all the possible combinations where we delete one letter from an inputted query 

6. addLetter(query)
creates a set of all the possible combinations where we add one letter to an inputted query 

7. replaceLetter(query)
creates a set of all the possible combinations where we replace one letter of an inputted query with another

8. inverrtLetters(query)
creates a set of all the possible combinations where we invert two letters within an inputted query

9. changeCase(query)
creates a set of all the possible combinations where we change a letter of an inputted query from upper to lower or vice versa

---------------------------------------
