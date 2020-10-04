(ns advent-2017-clojure.day19-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day19 :refer :all]))


(def TEST_INPUT "    | \n    |  +--+\n    A  |  C\nF---|----E|--+\n    |  |  |  D\n    +B-+  +--+")
(def PUZZLE_INPUT (slurp "test\\advent_2017_clojure\\day19_data.clj"))

(deftest part1-test
  (testing "Test input"
    (is (= "ABCDEF" (part1 TEST_INPUT))))
  (testing "Puzzle input"
    (is (= "VTWBPYAQFU" (part1 PUZZLE_INPUT)))))

(deftest part2-test
  (testing "Test input"
    (is (= 38 (part2 TEST_INPUT))))
  (testing "Puzzle input"
    (is (= 17358 (part2 PUZZLE_INPUT)))))