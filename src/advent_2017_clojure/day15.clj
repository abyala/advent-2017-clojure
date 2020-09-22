(ns advent-2017-clojure.day15
  (:require [advent-2017-clojure.utils :refer [left-pad]]))

(def generator-a-factor 16807)
(def generator-b-factor 48271)
(def divider 2147483647)

(defn generator-sequence "Creates a lazy sequence for feeding generator data into itself"
  [factor seed]
  (let [next (mod (* seed factor) divider)]
    (lazy-seq (cons next (generator-sequence factor next)))))

(def generator-a (partial generator-sequence generator-a-factor))
(def generator-b (partial generator-sequence generator-b-factor))


(defn last-16-digits [n] (mod n 65536))

(defn num-low-bit-matches [target-iters seed-a seed-b]
  (loop [accum 0, n 0, seq-a (generator-a seed-a), seq-b (generator-b seed-b)]
    (if (= n target-iters)
      accum
      (recur (if (= (last-16-digits (first seq-a)) (last-16-digits (first seq-b)))
               (inc accum)
               accum)
             (inc n)
             (drop 1 seq-a)
             (drop 1 seq-b)))))

(defn part1 [seed-a seed-b]
  (num-low-bit-matches 40000000 seed-a seed-b))
