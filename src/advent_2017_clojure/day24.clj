(ns advent-2017-clojure.day24
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (let [[a b] (->> (re-seq #"\d+" line)
                   (map #(Integer/parseInt %)))]
    [a b]))

(defn parse-input [input]
  (->> (str/split-lines input)
       (map parse-line)))

(defn connecting-ports [pin ports]
  (filter #(some (partial = pin) %) ports))

(defn remove-port [p ports] (remove (partial = p) ports))

(defn other-side [pin [a b]] (if (= pin a) b a))

(defn find-paths
  ([ports] (find-paths ports 0))
  ([ports pin]
   (apply concat (for [c (connecting-ports pin ports)]
                   (cons [c]
                         (map #(apply merge [c] %)
                              (find-paths (remove-port c ports)
                                          (other-side pin c))))))))

(defn strength [path]
  (->> path flatten (apply +)))

(defn strongest-path [paths]
  (->> (map strength paths)
       (apply max)))

(defn part1 [input]
  (->> (parse-input input)
       find-paths
       strongest-path))

(defn part2 [input]
  (->> (parse-input input)
       find-paths
       (group-by count)
       (sort-by first)
       last
       second
       strongest-path))