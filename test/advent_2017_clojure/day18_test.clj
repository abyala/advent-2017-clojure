(ns advent-2017-clojure.day18-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day18 :refer :all]))

(def PART1_TEST_INPUT "set a 1\nadd a 2\nmul a a\nmod a 5\nsnd a\nset a 0\nrcv a\njgz a -1\nset a 1\njgz a -2")
(def PART2_TEST_INPUT "snd 1\nsnd 2\nsnd p\nrcv a\nrcv b\nrcv c\nrcv d\n")
(def PUZZLE_INPUT (slurp "test\\advent_2017_clojure\\day18_data.txt"))

(deftest part1-test
  (is (= 4 (part1 PART1_TEST_INPUT)))
  (is (= 2951 (part1 PUZZLE_INPUT))))

(deftest part2-test
  (is (= 3 (part2 PART2_TEST_INPUT)))
  (is (= 7366 (part2 PUZZLE_INPUT))))