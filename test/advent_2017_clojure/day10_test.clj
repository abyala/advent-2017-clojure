(ns advent-2017-clojure.day10-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day10 :refer :all]
            [clojure.string :as str]))

(def PUZZLE_INPUT "130,126,1,11,140,2,255,207,18,254,246,164,29,104,0,224")
(def PUZZLE_INTEGERS (map #(Integer/parseInt %) (str/split PUZZLE_INPUT #",")))

(deftest reverse-sub-array-tests
  (testing "Dummy tests"
    (is (= [2 1 0 3 4] (reverse-sub-array [0 1 2 3 4] 0 3)))
    (is (= [8 1 2 3 4 5 6 7 0 9] (reverse-sub-array (vec (range 10)) 8 3)))
    (is (= [9 8 2 3 4 5 6 7 1 0] (reverse-sub-array (vec (range 10)) 8 4)))))


(deftest part1-tests
  (testing "Sample input"
    (is (= 12 (part1 5 '(3 4 1 5)))))
  (testing "Puzzle input"
    (is (= 38628 (part1 256 PUZZLE_INTEGERS)))))

