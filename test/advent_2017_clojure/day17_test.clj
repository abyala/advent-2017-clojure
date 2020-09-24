(ns advent-2017-clojure.day17-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day17 :refer :all]))

(def PUZZLE_STEPS 316)

(deftest part1-test
  (testing "Test input"
    (is (= 638 (part1 3))))
  (testing "Puzzle input"
    (is (= 180 (part1 PUZZLE_STEPS)))))

(deftest part2-test
  (testing "Puzzle input"
    (is (= 13326437 (part2 PUZZLE_STEPS)))))
