(ns advent-2017-clojure.day21-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day21 :refer :all]))

(def TEST_INPUT "../.# => ##./#../...\n.#./..#/### => #..#/..../..../#..#")
(def PUZZLE_INPUT (slurp "test\\advent_2017_clojure\\day21_data.txt"))

(deftest transform-row-group-test
  (is (= '("abc" "def")
         (transform-row-group {["ab" "cd"] ["abc" "def"]}
                              (list ["ab" "cd"]))))
  (is (= '("abcabc" "defdef")
         (transform-row-group {["ab" "cd"] ["abc" "def"]}
                              (list ["ab" "cd"] ["ab" "cd"])))))

(deftest part1-test
  (is (= 12 (solve TEST_INPUT 2)))
  (is (= 136 (solve PUZZLE_INPUT 5))))

(deftest part2-test
  (is (= 1911767 (solve PUZZLE_INPUT 18))))