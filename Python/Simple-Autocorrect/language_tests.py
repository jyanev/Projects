from language_tools import LanguageHelper
import unittest

# We define the custom lexicon that we will use for our controlled tests
sample = ('car', 'cat', 'Cate', 'cater', 'care',
          'cot', 'cute', 'dare', 'date', 'dog', 'dodge',
          'coffee', 'pickle', 'grate')

rhymesWithDog = ('bog', 'cog', 'clog', 'fog', 'frog', 'hog', 'log')




class BasicTest(unittest.TestCase):
  
    # make sure that all the words in the lexicon are recognized
    def testContainment(self):
        helper = LanguageHelper(sample)
        for w in sample:
            self.assertTrue(w in helper)
  
    def testFailures(self):
        helper = LanguageHelper(sample)
        self.assertFalse('cate' in helper)     # only allowed when capitalized
        self.assertFalse('fox' in helper)      # word is not there
        self.assertFalse('cofee' in helper)    # mis-spell word is not there

    def testSuggestInsertion(self):
        helper = LanguageHelper(sample)
        self.assertEqual(helper.getSuggestions('pikle'), ['pickle'])
        self.assertEqual(helper.getSuggestions('ct'), ['cat','cot'])

    def testSuggestDeletion(self):
        helper = LanguageHelper(sample)
        self.assertEqual(helper.getSuggestions('gratle'), ['grate'])

    def testSugeestionsMany(self):
        helper = LanguageHelper(rhymesWithDog)
        self.assertEqual(helper.getSuggestions('rog'), ['bog','cog','fog','frog','hog','log'])

    def testSugeestionsCapitalization(self):
        helper = LanguageHelper(sample)
        self.assertEqual(helper.getSuggestions('Gate'), ['Cate', 'Date', 'Grate'])

    def testSuggestionsNone(self):
        helper = LanguageHelper(sample)
        self.assertEqual(helper.getSuggestions('blech'), [])


    def testSuggestionsFullCaps(self):
        with open("EnglishTest.txt") as EnglishTest:
            helper = LanguageHelper(EnglishTest)
            self.assertEqual(helper.getSuggestions('nATO'), ['NATO'])
            self.assertEqual(helper.getSuggestions('NaTO'), ['NATO'])
            self.assertEqual(helper.getSuggestions('NAtO'), ['NATO'])
            self.assertEqual(helper.getSuggestions('NATo'), ['NATO'])
            self.assertEqual(helper.getSuggestions('nato'), [])
            self.assertEqual(helper.getSuggestions('sLU'), ['SLU'])
            self.assertEqual(helper.getSuggestions('SlU'), ['SLU'])
            self.assertEqual(helper.getSuggestions('SLu'), ['SLU'])
            self.assertEqual(helper.getSuggestions('slu'), [])

    def testSuggestionsRepeatingLetter(self):
        with open("EnglishTest.txt") as EnglishTest:
            helper = LanguageHelper(EnglishTest)
            self.assertEqual(helper.getSuggestions('biger'), ['bigger', 'binger'])
        

    def testSuggestionsRhyme(self):
        with open("EnglishTest.txt") as EnglishTest:
            helper = LanguageHelper(EnglishTest)
            self.assertEqual(helper.getSuggestions('tork'), ['fork', 'pork', 'stork'])
            self.assertEqual(helper.getSuggestions('ork'), ['York', 'Zork', 'fork', 'orc', 'pork'])

    def testSuggestionsUncapitalized(self):
        with open("EnglishTest.txt") as EnglishTest:
            helper = LanguageHelper(EnglishTest)
            self.assertEqual(helper.getSuggestions('missouri'), ['Missouri'])
            self.assertEqual(helper.getSuggestions('zork'), ['Zork', 'fork', 'pork'])

    def testSuggestionsHyphen(self):
        with open("EnglishTest.txt") as EnglishTest:
            helper = LanguageHelper(EnglishTest)
            self.assertEqual(helper.getSuggestions('longterm'), ['long-term'])
            self.assertEqual(helper.getSuggestions('long term'), ['long-term'])

    def testSuggestionsApostrophe(self):
        with open("EnglishTest.txt") as EnglishTest:
            helper = LanguageHelper(EnglishTest)
            self.assertEqual(helper.getSuggestions('itd'), ['it\'d'])
            self.assertEqual(helper.getSuggestions('it\''), ['it\'d', 'it\'s'])
            self.assertEqual(helper.getSuggestions('cant'), ['can\'t'])

    def testUppercase(self):
        with open("EnglishTest.txt") as EnglishTest:
            helper = LanguageHelper(EnglishTest)
            self.assertTrue(helper.__contains__('Missouri'))
            self.assertTrue(helper.__contains__('Bigger'))
            self.assertTrue(helper.__contains__('Wrangler'))
            self.assertTrue(helper.__contains__('Can\'t'))
            self.assertTrue(helper.__contains__('York'))
            self.assertTrue(helper.__contains__('SLU'))
            
    def testLowercase(self):
        with open("EnglishTest.txt") as EnglishTest:
            helper = LanguageHelper(EnglishTest)
            self.assertTrue(helper.__contains__('pork'))
            self.assertTrue(helper.__contains__('bigger'))
            self.assertTrue(helper.__contains__('can\'t'))
            self.assertFalse(helper.__contains__('missouri'))
            self.assertFalse(helper.__contains__('wrangler'))
            self.assertFalse(helper.__contains__('slu'))
            self.assertFalse(helper.__contains__('sLU'))

    def testNotAWord(self):
        with open("EnglishTest.txt") as EnglishTest:
            helper = LanguageHelper(EnglishTest)
            self.assertFalse(helper.__contains__('frog'))
            self.assertFalse(helper.__contains__('crazy'))
            self.assertFalse(helper.__contains__('aergiubaeirubgu'))

    def testNoSuggestions(self):
        with open("EnglishTest.txt") as EnglishTest:
            helper = LanguageHelper(EnglishTest)
            self.assertEqual(helper.getSuggestions('dsfgudin'), [])
            self.assertEqual(helper.getSuggestions('BigWordNoSuggestions'), [])
            self.assertEqual(helper.getSuggestions('pinapple'), [])

    def testSpelledCorrectly(self):
        with open("EnglishTest.txt") as EnglishTest:
            helper = LanguageHelper(EnglishTest)
            self.assertEqual(helper.getSuggestions('bigger'), [])
            self.assertEqual(helper.getSuggestions('Fork'), [])
            self.assertEqual(helper.getSuggestions('Wrangler'), [])
            self.assertEqual(helper.getSuggestions('can\'t'), [])
            self.assertEqual(helper.getSuggestions('long-term'), [])
    
    
if __name__ == '__main__':
    unittest.main()
    
