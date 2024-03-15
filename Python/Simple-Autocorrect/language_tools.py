class LanguageHelper:
    def __init__(self, words):
        
        '''
        LanguageHelper provides a list of suggestions for the correct spelling of an inputted word
        based on a list of "correctly" spelled words
        
        self._chars includes all of the letter of the alphabet as well as an apostrophe and hyphen
        self._words includes all of the words in a given dictionary of words ; these words are considered as correctly spelled
        '''
        
        self._chars = list('abcdefghijklmnopqrstuvwxyz-\'')

        self._words = []
        for w in words:
            if '\n' in w:              
                self._words.append(w[0:-1])
            else:
                self._words.append(w)
                

    def __contains__(self, query):
        
        '''
        using the following logic, returns True if the inputted query is contained within the dictionary of words, self._words, False otherwise

        - the inputted word must be spelled the same way as is in self._words
        - if the correct word is capitalized, such as Europe, then returns False if the inputted word is lowercase, ie. europe
        - if the correct word is lowercase, such as dog, then returns True whether or not the inputted word is capitalized
        '''
        
        if query in self._words:
            return True     
        elif (query[0].lower() + query[1:]) in self._words:
            return True        
        else:
            return False
        
    def __checkDict__(self, query):
        
        '''
        behaves the same as __contains__, with one difference

        - if the correct word is not capitalized, the returns True only if the inputted query is not capitalized, returns False otherwise
        '''
        
        if query in self._words:
            return True
        else:
            return False


    def getSuggestions(self, query):
        
        '''
        returns an empty list if the inputted query is found within the dictionary of words, self._words
        otherwise, returns a list of suggestions for correctly spelling the inputted query
        '''
        
        suggestions = []
        
        if self.__contains__(query):    # checks if the word is already correct
            return []
        
        else:            
            for option in self.deleteLetter(query):    #adds the correct suggestions from deleteLetter          
                if self.__checkDict__(option):
                    if query[0].isupper():
                        suggestions.append(option.capitalize())
                    else:
                        suggestions.append(option)
            
            for option in self.addLetter(query):       #adds the correct suggestions from addLetter
                if query[0].isupper():
                    if self.__contains__(option):                       
                        suggestions.append(option)
                else:              
                    if self.__checkDict__(option):                       
                        suggestions.append(option)
            
            for option in self.replaceLetter(query):   #adds the correct suggestions from replaceLetter
                if self.__contains__(option):
                    suggestions.append(option)               
                    
            for option in self.invertLetters(query):
                if self.__checkDict__(option):
                    if query[0].isupper():
                        suggestions.append(option.capitalize())
                    else:
                        suggestions.append(option)
                    
            for option in self.changeCase(query):      #adds the correct suggestions from changeCase          
                if self.__checkDict__(option):
                    suggestions.append(option)
            
            return sorted(suggestions)  #sorts the list of suggestions in alphabetical order


    def deleteLetter(self, word):
        
        '''
        creates a set of all the possible combinations
        where we delete one letter from an inputted query 
        '''
        
        possible = set()
            
        for letter in word:
            possible.add(word.replace(letter, '', 1))
        return possible



    def addLetter(self, word):

        '''
        creates a set of all the possible combinations
        where we add one letter to an inputted query 
        '''
        
        possible = set()

        for i in range(len(word)+1):          
            for v in self._chars:
                if v.isalpha():
                    if i == 0:
                        possible.add(v + word)
                        possible.add(v.upper() + word)

                    elif i < len(word):
                        possible.add(word[0:i] + v + word[i:])
                        possible.add(word[0:i] + v.upper() + word[i:])
                        
                    else:
                        possible.add(word + v)
                        possible.add(word + v.upper())
                        
                else:
                    if i == 0:
                        possible.add(v + word)
                        
                    else:
                        possible.add(word[0:i] + v + word[i:])

        return possible

    
    def replaceLetter(self, word):

        '''
        creates a set of all the possible combinations
        where we replace one letter of an inputted query with another
        '''
        
        possible = set()

        for letter in word:
            for v in self._chars:
                if letter.isupper():
                    possible.add(word.replace(letter, v.upper(), 1))
                    
                else:
                    possible.add(word.replace(letter, v, 1))
                    
        return possible


                        
    def invertLetters(self, word):

        '''
        creates a set of all the possible combinations
        where we invert two letters within an inputted query
        '''
        
        possible = set()

        for i in range(len(word)-1):
            possible.add(word.replace((word[i]+word[i+1]), (word[i+1]+word[i]), 1))
            
        return possible
    

                   
    def changeCase(self, word):

        '''
        creates a set of all the possible combinations
        where we change a letter of an inputted query from upper to lower or vice versa
        '''

        possible = set()
        
        for letter in word:
            if letter.isalpha():
                if letter == word[0]:
                    if self.__contains__(letter.upper()+word[1:]):                       
                        possible.add(word.replace(letter, letter.upper(), 1))
                        
                elif letter.isupper():
                    possible.add(word.replace(letter, letter.lower(), 1))
                    
                else:
                    possible.add(word.replace(letter, letter.upper(), 1))
                    
        return possible
