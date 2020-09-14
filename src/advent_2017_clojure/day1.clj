(ns advent-2017-clojure.day1)

(defn count-equal-across-offset "Sums all characters that equal counterparts an offset away"
  [offset word]
  (reduce + (map-indexed (fn [idx c] (if
                                       (= c
                                          (get word (mod (+ idx offset)
                                                         (count word))))
                                       (Character/getNumericValue c)
                                       0))
                         word)))

(def count-adjacent-characters "Part 1: Compares characters adjacent to each other"
  (partial count-equal-across-offset 1))                    ; Fun with currying!

(defn count-matching-halfway "Part 2: Compares characters halfway across the word from each other"
  [word]
  (count-equal-across-offset (/ (count word) 2) word))


