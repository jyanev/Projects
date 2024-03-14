module Vector where

type Vector = [Double]

scale :: Double -> Vector -> Vector
scale 0 [0] = [0]
scale y v = map (y*) v

add :: Vector -> Vector -> Vector
add [0] [0] = [0]
add [x] [y] = [x+y]
add (x:xs) (y:ys) = [x+y] ++ add xs ys

dot :: Vector -> Vector -> Double
dot [0] [0] = 0
dot [x] [y] = x*y
dot (x:xs) (y:ys) = dot xs ys + x*y

norm :: Int -> Vector -> Double
norm 0 [0] = 0
norm p v = root p (sum (map (**(fromIntegral p)) v))

root :: Int -> Double -> Double
root p x = x**(1 / (fromIntegral p))
