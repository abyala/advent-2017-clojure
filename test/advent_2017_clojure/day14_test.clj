(ns advent-2017-clojure.day14-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day14 :refer :all]))

(def TEST_INPUT "flqrgnkx")
(def PUZZLE_INPUT "ffayrhll")

(deftest part1-test
  (testing "Test input"
    (is (= 8108 (part1 TEST_INPUT))))
  (testing "Puzzle input"
    (is (= 8190 (part1 PUZZLE_INPUT)))))

(deftest part2-test
  (testing "Test input"
    (is (= 1242 (part2 TEST_INPUT))))
  (testing "Puzzle input"
    (is (= 1134 (part2 PUZZLE_INPUT)))))