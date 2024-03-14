module RLE where

-- Run Length Encoding
--
rle :: String -> String
rle [] = "" 
rle xs = show (patternLength) ++ take 1 xs ++ space ++ rle remainingList
         where
           patternLength = calculatePatternLength xs
           remainingList = drop patternLength xs
           space = (if length remainingList == 0 then "" else " ")

calculatePatternLength :: String -> Int
calculatePatternLength [] = 0
calculatePatternLength [x] = 1
calculatePatternLength (x:xs) = (if x == head xs then ((calculatePatternLength xs) + 1) else 1)


-- Run Length Decoding
-- 
rleInverse :: String -> String
rleInverse [] = ""
rleInverse xs = take n pattern ++ rleInverse (drop 3 xs)
                where
                  n = read (take 1 xs)
                  pattern = repeat (xs !! 1)
