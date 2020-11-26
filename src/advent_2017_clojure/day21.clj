(ns advent-2017-clojure.day21
  (:require [clojure.string :as str]))

(defn on? [c] (= c \#))
(def initial-pattern [".#." "..#" "###"])

(defn flip [grid] (mapv str/reverse grid))

; Using defmulti because I can, and because I couldn't find a convenient
; algorithm to rotate characters in a way that scales beyond 2 or 3.
(defmulti rotate (fn [grid] (count grid)))
(defmethod rotate 2 [[[a b] [c d]]]
  [(str c a) (str d b)])
(defmethod rotate 3 [[[a b c] [d e f] [g h i]]]
  [(str g d a) (str h e b) (str i f c)])

(defn rotations [grid]
  (take 4 (iterate rotate grid)))

(defn permutations [grid]
  (->> (list grid (flip grid))
       (map (partial rotations))
       (apply concat)
       set))

(defn parse-line [line]
  (letfn [(parse [s] (vec (re-seq #"[\.#]+" s)))]
    (let [[pattern output] (str/split line #" => ")]
      (zipmap (permutations (parse pattern)) (repeat (parse output))))))

(defn parse-all-rules [input]
  (->> (str/split-lines input)
       (map parse-line)
       (apply merge)))

(defn grid-width [grid]
  (if (zero? (mod (count grid) 2)) 2 3))

(defn sub-grids [width row-group]
  (let [pattern (re-pattern (str ".{" width "}"))]
    (->> (map (partial re-seq pattern) row-group)
         (apply interleave)
         (partition width))))

(defn transform-row-group
  "Maps a ruleset to a row-group; a seq of grids within a set of rows.
  A row-group is a sequence of sequences.
  - The outer seq represents a sub-grid
  - The inner seq represents each row of characters in the sub-grid."
  [rules row-group]
  (->> (map (partial rules) row-group)
       (apply interleave)
       (partition (count row-group))
       (map (partial apply str))))

(defn apply-transformations [rules grid]
  (let [width (grid-width grid)]
    (->> (partition width grid)
         (map (partial sub-grids width))
         (map (partial transform-row-group rules))
         (apply concat))))

(defn num-on-pixels [grid]
  (->> (apply str grid)
       (filter on?)
       count))

(defn solve [input num-iterations]
  (let [rules (parse-all-rules input)]
    (->> (nth (iterate (partial apply-transformations rules) initial-pattern)
              num-iterations)
         num-on-pixels)))