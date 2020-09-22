(ns advent-2017-clojure.day15-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day15 :refer :all]))

(def TEST_SEEDS [65 8921])
(def PUZZLE_INPUT [883 879])

(deftest part1-test
  (testing "Test input"
    (is (= 588 (part1 (first TEST_SEEDS) (second TEST_SEEDS)))))
  (testing "Puzzle input"
    (is (= 609 (part1 (first PUZZLE_INPUT) (second PUZZLE_INPUT))))))

(deftest part2-test
  (testing "Test input"
    (is (= 309 (part2 (first TEST_SEEDS) (second TEST_SEEDS)))))
  (testing "Puzzle input"
    (is (= 253 (part2 (first PUZZLE_INPUT) (second PUZZLE_INPUT))))))
