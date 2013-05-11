import Data.Graph.Inductive.Internal.Heap(Heap(..),insert,findMin,deleteMin)
import System.Random

-- Quick Sort 
quicksort :: (Ord a) => [a] -> [a]
quicksort [] = []
quicksort (x:xs) = 
  let smallerSorted = quicksort [a | a <- xs, a <= x]
      biggerSorted  = quicksort [a | a <- xs, a > x] 
  in  smallerSorted ++ [x] ++ biggerSorted

-- Insertion Sort 
insertionSort :: (a -> a -> Bool) -> [a] -> [a] 
insertionSort _ [] = []
insertionSort p (x:xs) = insert' p x (insertionSort p xs) 

insert' :: (a -> a -> Bool) -> a -> [a] -> [a]
insert' p x [] = [x] 
insert' p x (y:ys) 
    | p x y     = (x:y:ys)
    | otherwise = y:(insert' p x ys) 

-- Heap Sort 
build :: (Ord a) => [(a,b)] -> Heap a b 
build = foldr insert Empty 

toList :: (Ord a) => Heap a b -> [(a,b)]
toList Empty = []
toList h     = x:toList r 
  where (x,r) = (findMin h, deleteMin h) 

heapsort :: (Ord a) => [a] -> [a] 
heapsort = (map fst) . toList . build . map(\x -> (x,x))

-- Test Data 
g = mkStdGen 5675309
