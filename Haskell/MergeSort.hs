mergeSort :: (Ord a) => [a] -> [a]
mergeSort [] = []
mergeSort [x] = [x]
mergeSort x = merge (mergeSort left) (mergeSort right) []
              where (left, right) = splitAt ((length x + 1) `div` 2) x

merge :: (Ord a) => [a] -> [a] -> [a] -> [a]
merge [] [] z = z
merge [x] [] z = (z ++ [x])
merge [] [y] z = (z ++ [y])
merge x [] z = (z ++ x)
merge [] y z = (z ++ y)
merge [x] [y] z = do
 if (x <= y)
  then (z ++ [x] ++ [y])
  else (z ++ [y] ++ [x])
merge (x:xs) (y:ys) z = do
 if (x <= y)
  then merge xs (y:ys) (z ++ [x])
  else merge (x:xs) ys (z ++ [y])

