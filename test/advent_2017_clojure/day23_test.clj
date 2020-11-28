(ns advent-2017-clojure.day23-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day23 :refer :all]))

(def PUZZLE_INPUT (slurp "test\\advent_2017_clojure\\day23_data.txt"))

(deftest part1-test
  (is (= 6724 (part1 PUZZLE_INPUT))))