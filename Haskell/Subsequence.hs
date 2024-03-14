module Subsequence where

subsequence :: Eq a => [a] -> [a] -> [Int]
subsequence [] [] = []
subsequence [] ys = []
subsequence _ [] = error "subsequence does not exist"
subsequence (x:xs) (y:ys) = do
  if x == y
    then 0 : map (1+) (subsequence xs ys)
    else map (1+) (subsequence (x:xs) ys)

