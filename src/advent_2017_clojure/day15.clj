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

(defn num-low-bit-matches [target-iters gen-a gen-b]
  (loop [accum 0, n 0, seq-a gen-a, seq-b gen-b]
    (if (= n target-iters)
      accum
      (recur (if (= (last-16-digits (first seq-a)) (last-16-digits (first seq-b)))
               (inc accum)
               accum)
             (inc n)
             (drop 1 seq-a)
             (drop 1 seq-b)))))

(defn part1 [seed-a seed-b]
  (num-low-bit-matches 40000000 (generator-a seed-a) (generator-b seed-b)))

; Why not apply a partial function to make part2 look simpler?
; The alternative is (filter #(= 0 (mod % 4)) (generator-a seed-a)), but that's gross.
(def filtered-by (partial (fn [f gen] (filter #(= 0 (mod % f)) gen))))

(defn part2 [seed-a seed-b]
  (num-low-bit-matches 5000000
                       (filtered-by 4 (generator-a seed-a))
                       (filtered-by 8 (generator-b seed-b))))

