(ns advent-2017-clojure.day18-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day18 :refer :all]))

(def TEST_INPUT "set a 1\nadd a 2\nmul a a\nmod a 5\nsnd a\nset a 0\nrcv a\njgz a -1\nset a 1\njgz a -2")
(def PUZZLE_INPUT (slurp "test\\advent_2017_clojure\\day18_data.txt"))

(deftest part1-test
  (is (= 4 (part1 TEST_INPUT)))
  (is (= 2951 (part1 PUZZLE_INPUT))))