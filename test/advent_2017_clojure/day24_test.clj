(ns advent-2017-clojure.day24-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day24 :refer :all]))

(def TEST_INPUT "0/2\n2/2\n2/3\n3/4\n3/5\n0/1\n10/1\n9/10")
(def PUZZLE_INPUT (slurp "test\\advent_2017_clojure\\day24_data.txt"))

(deftest connecting-ports-test
  (is (= (list [0 1] [1 1] [1 3])
         (connecting-ports 1 [[0 0] [0 1] [1 1] [2 2] [1 3]]))))

(deftest remove-port-test
  (is (= [[0 1] [2 3]]
         (remove-port [1 1] [[0 1] [1 1] [2 3]]))))

(deftest other-side-test
  (is (= 1 (other-side 2 [1 2])))
  (is (= 1 (other-side 2 [2 1])))
  (is (= 2 (other-side 2 [2 2]))))

(deftest find-paths-test
  (is (= []
         (find-paths [[0 1] [0 2]] 3)))
  (is (= [[[0 1]]
          [[0 2]]]
         (find-paths [[0 1] [0 2]] 0)))
  (is (= [[[0 1]]
          [[0 2]]
          [[0 2] [2 3]]]
         (find-paths [[0 1] [0 2] [2 3]] 0))))

(deftest part1-test
  (is (= 31 (part1 TEST_INPUT)))
  (is (= 1859 (part1 PUZZLE_INPUT))))

(deftest part2-test
  (is (= 19 (part2 TEST_INPUT)))
  (is (= 1799 (part2 PUZZLE_INPUT))))