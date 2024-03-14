data Stream a = Cons a (Stream a)

instance Show a => Show (Stream a) where
  show stream = show (take 20 (streamToList stream))

instance Functor Stream where
  fmap f (Cons x xs) = Cons (f x) (fmap f xs)

streamToList :: Stream a -> [a]
streamToList (Cons x xs) = x : streamToList xs 

streamRepeat :: a -> Stream a
streamRepeat x = Cons x (streamRepeat x)

streamIterate :: (a -> a) -> a -> Stream a
streamIterate f x = Cons x (streamIterate f (f x))

streamInterleave :: Stream a -> Stream a -> Stream a
streamInterleave (Cons x xs) ys = Cons x (streamInterleave ys xs)

nats :: Stream Integer
nats = streamIterate (+1) 0

powersoftwo :: Stream Integer
powersoftwo = streamIterate (*2) 1

triangular :: Stream Integer
triangular = fmap next nats
  where
    next n = (n * (n + 1)) `div` 2
