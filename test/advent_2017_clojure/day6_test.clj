(ns advent-2017-clojure.day6-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day6 :refer :all]
            [clojure.string :as str]))

(def INPUT_DATA "4\t1\t15\t12\t0\t9\t9\t5\t5\t8\t7\t3\t14\t5\t12\t3")
(def INPUT_BANK (vec (map #(Integer/parseInt %) (str/split INPUT_DATA #"\t"))))

(deftest first-index-of-largest-tests
  (is (= 2 (first-index-of-largest [0 2 7 0])))
  (is (= 1 (first-index-of-largest [2 4 1 2])))
  (is (= 0 (first-index-of-largest [3 1 2 3]))))

(deftest reallocate-banks-tests
  (testing "Single steps in the sample program"
    (is (= [2 4 1 2] (reallocate-banks [0 2 7 0])))
    (is (= [3 1 2 3] (reallocate-banks [2 4 1 2])))
    (is (= [0 2 3 4] (reallocate-banks [3 1 2 3])))))

(deftest num-reallocations-until-loop-tests
  (testing "Sample data"
    (is (= 5 (num-reallocations-until-loop [0 2 7 0]))))
  (testing "Puzzle data"
    (is (= 6681 (num-reallocations-until-loop INPUT_BANK)))))

(deftest loop-size-tests
  (testing "Puzzle data"
    (is (= 2392 (loop-size INPUT_BANK)))))
