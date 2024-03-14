#include "SAIS.h"
#include "DC3.h"
#include <list>
#include <vector>
#include <algorithm>


using namespace std;

SuffixArray inducedSort(const vector<size_t>& text, const vector<int>& lmsIndices, const vector<char>& lmsChars, size_t n) {
  size_t max = n;
  vector<int> buckets(max+1);
  vector<int> startPos(max+1, 0);
  SuffixArray suffixArr(text.size(), 0);
  
  for (auto i : text) {
    buckets[i]++;
  }
  
  for (size_t i=1 ; i < max+1 ; i++) {
    startPos[i] = buckets[i - 1] + startPos[i - 1];
  }
  
  vector<int> copyStartPos(startPos);
  
  for (int i=lmsIndices.size()-1 ; i >= 0 ; i--) {
    suffixArr[copyStartPos[text[lmsIndices[i]]+1]-1] = lmsIndices[i];
    copyStartPos[text[lmsIndices[i]]+1]--;
  }
  
  copyStartPos = vector<int>(startPos);
  
  for (size_t i = 0 ; i < text.size() ; i++) {
    if (suffixArr[i] != 0 && lmsChars[suffixArr[i]-1] == 'L') {
      suffixArr[copyStartPos[text[suffixArr[i]-1]]] = suffixArr[i] - 1;
      copyStartPos[text[suffixArr[i]-1]]++;
    }
  }
 
  copyStartPos = vector<int>(startPos);
  
  for (auto it=suffixArr.rbegin() ; it != suffixArr.rend() ; it++) {
    if ((*it) != 0 && lmsChars[(*it)-1] == 'S') {
      suffixArr[copyStartPos[text[(*it)-1]+1]-1] = (*it) - 1;
      copyStartPos[text[(*it)-1]+1]--;     
    }
  }
  
  return suffixArr;
  
}


SuffixArray sais(const vector<size_t>& text) {
  /* TODO: Implement this! */

  if (text.size() == 1) {
   return {0}; 
  }
  
  /***
   * 
   * STEP 1: LMS
   * 
   ***/

  vector<int> lmsIndices = {};
  vector<char> lmsChars = {};
  size_t max = 0;
  
  int i = text.size()-1;
  for (auto it = text.rbegin(); it != text.rend(); ++it) {
    if ((*it) > max)
      max = (*it);
    if (lmsChars.size() == 0) {
      lmsChars.push_back('S');
    }
    else if (*it < *(it-1)) {
      lmsChars.push_back('S');
    }
    else if (*it > *(it-1)) {
      if (lmsChars.back() == 'S') {
        lmsIndices.push_back(i+1);
      }
      lmsChars.push_back('L');
    }
    else {
      if (lmsChars.back() == 'S') {
        lmsChars.push_back('S');
      }
      else {
        lmsChars.push_back('L');
      }
    }
    i--;
  }
  
  reverse(lmsChars.begin(), lmsChars.end());

  /***
   * 
   * STEP 2: FIRST INDUCED SORT
   * 
   ***/

  SuffixArray suffixArr = inducedSort(text, lmsIndices, lmsChars, max);
  
  /***
   * 
   * STEP 3: FORM REDUCED STRING
   * 
   ***/
  
  vector<int> orderedLMS = {};

  for (size_t i=0 ; i < suffixArr.size() ; i++) {
    if (lmsChars[suffixArr[i]] == 'S' && lmsChars[suffixArr[i]-1] == 'L' ) 
      orderedLMS.push_back(suffixArr[i]);
  }
  
  vector<int> lmsLength = {0};
  
  for (size_t i=1 ; i < orderedLMS.size() ; i++) {
    int k = 1;
    int count = 1;
    while (!(lmsChars[orderedLMS[i]+k] == 'S' && lmsChars[orderedLMS[i]+k-1] == 'L') && orderedLMS[i]+k < (int) text.size()) {
      count++;
      k++;
    }
    lmsLength.push_back(count);
  }
  
  vector<int> labels(text.size(), -1);
  labels[text.size()-1] = 0;
  int label = 0;
  
  for (size_t i=1 ; i < orderedLMS.size() ; i++) {
    bool eq = true;
    if (lmsLength[i-1] == lmsLength[i]) {
      eq = false;
      for (int k=0 ; k <= lmsLength[i] ; k++)
        if (text[orderedLMS[i]+k] != text[orderedLMS[i-1]+k])
        eq = true;
    }
    if (eq) {
      label++;
      labels[orderedLMS[i]] = label;
    }
    else
      labels[orderedLMS[i]] = label;
  }
  
  vector<size_t> orderedLabels = {};

  for (int i : labels) {
    if (i != -1) {
      orderedLabels.push_back(i);
    }
  }
  
  /***
   * 
   * STEP 4: SORT LMS SUBSTRINGS
   * 
   ***/

  SuffixArray sortedLMS = dc3(orderedLabels);
  
  vector<int> finalLMS(lmsIndices.size());
  
  for (size_t i=0 ; i < sortedLMS.size() ; i++) {
    finalLMS[i] = lmsIndices[lmsIndices.size()-sortedLMS[i]-1];
  }
 
  /***
   * 
   * STEP 5: FINAL INDUCED SORT
   * 
   ***/
  
  suffixArr = inducedSort(text, finalLMS, lmsChars, max);

  return suffixArr;
}



