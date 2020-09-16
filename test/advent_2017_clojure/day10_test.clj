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

(deftest string-to-ascii-test
  (testing "Unformatted data"
    (is (= '(49,44,50,44,51) (string-to-ascii "1,2,3"))))
  (testing "Formatted data"
    (is (= '(49 44 50 44 51 17 31 73 47 23) (string-to-formatted-ascii "1,2,3")))))

(deftest to-hex-text
  (testing "Sample text"
    (is (= "4007ff") (to-hex [64 7 255]))))

(deftest part2-tests
  (testing "Sample inputs"
    (is (= "a2582a3a0e66e6e86e3812dcb672a272" (part2 "")))
    (is (= "33efeb34ea91902bb2f59c9920caa6cd" (part2 "AoC 2017")))
    (is (= "3efbe78a8d82f29979031a4aa0b16a9d" (part2 "1,2,3")))
    (is (= "63960835bcdc130f0b66d7ff4f6a5a8e" (part2 "1,2,4"))))
  (testing "Puzzle input"
    (is (= "e1462100a34221a7f0906da15c1c979a" (part2 PUZZLE_INPUT)))))
