(ns advent-2017-clojure.day25-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day25 :refer :all]))

(def TEST_STATES
  {:a [[1 inc :b] [0 dec :b]]
   :b [[1 dec :a] [1 inc :a]]})

(def PUZZLE_STATES
  {:a [[1 inc :b] [0 dec :c]]
   :b [[1 dec :a] [1 inc :d]]
   :c [[0 dec :b] [0 dec :e]]
   :d [[1 inc :a] [0 inc :b]]
   :e [[1 dec :f] [1 dec :c]]
   :f [[1 inc :d] [1 inc :a]]})

(deftest part1-test
  (is (= 3 (part1 TEST_STATES 6)))
  (is (= 3362 (part1 PUZZLE_STATES 12481997))))