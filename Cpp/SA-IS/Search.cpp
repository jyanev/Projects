#include "Search.h"
#include <iostream>
using namespace std;

vector<size_t> searchFor(const string& pattern,
                         const string& text,
                         const SuffixArray& suffixArr) {
  /* TODO: Implement this! */

  vector<size_t> results = {};
  int left = 0;
  int right = suffixArr.size() - 1;
  size_t patternLength = pattern.length();

  
  while (left <= right) {
    size_t mid = left + (right - left) / 2;
    size_t textIndex = suffixArr[mid];

    string substring = text.substr(textIndex, patternLength);
    
    if (substring == pattern) {
      results.push_back(textIndex); 
      int i = 1;
      textIndex = suffixArr[mid + i];
      if (textIndex + patternLength <= text.length()) {
        substring = text.substr(textIndex, patternLength);
        while (mid + i < suffixArr.size() && substring == pattern) {
            results.push_back(textIndex);
            i++;
            textIndex = suffixArr[mid + i];
            if (textIndex + patternLength <= text.length()) 
              substring = text.substr(textIndex, patternLength);
            else
              break;
        }
      }
      i = 1;
      textIndex = suffixArr[mid - i];
      if (textIndex + patternLength <= text.length()) {
      substring = text.substr(textIndex, patternLength);
        while (mid - i >= 0 && substring == pattern) {
            results.push_back(textIndex);
            i++;
            textIndex = suffixArr[mid - i];
            if (textIndex + patternLength <= text.length()) 
              substring = text.substr(textIndex, patternLength);
            else
              break;
        }
      }
      return results;
    } 

    else if (substring < pattern)
      left = mid + 1;

    else
      right = mid - 1;

  }
  return {};
}

