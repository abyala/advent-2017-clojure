(ns advent-2017-clojure.day2
  (:require [clojure.string :as str]))

(defn diff-min-max "Subtracts the smallest value in the vector from the largest"
  [nums]
  (- (reduce max nums) (reduce min nums)))

(defn vector-of-int-vectors "Parses a string into lines of tabbed integers"
  [doc]
  (map (fn [line] (map #(Integer/parseInt %)
                       (str/split line #"\t")))
       (str/split doc #"\n")))

(defn unique-pairs "Returns a list of pairs of distinct values, smallest before largest"
  [nums]
  (loop [result (), [x & xs] (sort nums)]
    (if (empty? xs)
      result
      (recur (concat result
                     (map #(vector x %) xs))
             xs))))

(defn divide-first-evenly "Divides the first (only?) pair of integers that are evenly divisible"
  [nums]
  (first (keep (fn [[den num]]
                 (when (= 0 (rem num den)) (/ num den)))
               (unique-pairs nums))))

; Checksum functions
(defn apply-checksum [fn doc]                               ; Same logic, different mapping function
  (reduce + (map fn (vector-of-int-vectors doc))))

(def checksum-largest-smallest (partial apply-checksum diff-min-max))
(def checksum-by-divisible (partial apply-checksum divide-first-evenly))
