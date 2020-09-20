(ns advent-2017-clojure.day13-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day13 :refer :all]))

(def TEST_TEXT "0: 3\n1: 2\n4: 4\n6: 4")
(def PUZZLE_TEXT "0: 4\n1: 2\n2: 3\n4: 4\n6: 6\n8: 5\n10: 6\n12: 6\n14: 6\n16: 12\n18: 8\n20: 9\n22: 8\n24: 8\n26: 8\n28: 8\n30: 12\n32: 10\n34: 8\n36: 12\n38: 10\n40: 12\n42: 12\n44: 12\n46: 12\n48: 12\n50: 14\n52: 14\n54: 12\n56: 12\n58: 14\n60: 14\n62: 14\n66: 14\n68: 14\n70: 14\n72: 14\n74: 14\n78: 18\n80: 14\n82: 14\n88: 18\n92: 17")

(deftest test-parse-line
  (testing "Sample lines"
    (is (= [1 26] (parse-line "1: 26")))))

(deftest test-severity
  (testing "Sample input"
    (is (= 24 (part1 TEST_TEXT))))
  (testing "Puzzle input"
    (is (= 1580 (part1 PUZZLE_TEXT)))))

(println (part2 PUZZLE_TEXT))

(deftest test-avoids-detection
  (testing "Sample input"
    (is (= 10 (part2 TEST_TEXT))))
  (testing "Puzzle input"
    (is (= 3943252 (part2 PUZZLE_TEXT)))))
