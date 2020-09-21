(ns advent-2017-clojure.day14
  (:require [advent-2017-clojure.day10 :refer [apply-knot-hash]]
            [clojure.set :as set]))

(defn split-inputs "Converts the input key into a list of all input keys (val-0, val-1, val-2...)"
  ([input-string] (split-inputs 128 input-string))
  ([r input-string] (map #(str input-string \- %) (range r))))

(defn hex-to-binary "Converts each hex digit into its 4-bit binary form"
  [word]
  (.toString (new BigInteger word 16) 2))

(defn padded-hex-to-binary "Converts each hex digit into its 4-bit binary form, zero padded"
  [word]
  (.replace (format (str "%" (* 4 (count word)) "s") (hex-to-binary word)) \  \0))

(defn run-grid-of-knot-hashes "Runs each line of input through the knot hash, converting output to padded binary"
  [input]
  (map (comp padded-hex-to-binary apply-knot-hash) (split-inputs 128 input)))

(defn part1 [input]
  (reduce + (map #((frequencies %) \1) (run-grid-of-knot-hashes input))))

(defn all-pairs "Returns a list of all [x y] points within the grid where there is a value of 1"
  [grid]
  (apply concat (map-indexed (fn [y row]
                               (keep-indexed #(if (= \1 %2) [%1 y]) row))
                             grid)))

(defn edge-adjacent-points "Returns the points up, down, left and right of the input"
  [[x y]]
  [[x (inc y)]
   [x (dec y)]
   [(inc x) y]
   [(dec x) y]])

(defn points-in-group "Given a set of points and a member, returns the set of all edge-adjacent points"
  [all-points point]
  (assert (contains? all-points point) (str "Point " point " must reside within the listed points"))
  (loop [in-group #{}, connected #{point}, out-group (disj all-points point)]
    (if (empty? connected)
      in-group
      (let [chosen (first connected)
            neighbors (set/intersection out-group (set (edge-adjacent-points chosen)))]
        (recur (cons chosen in-group)
               (disj (set/union connected neighbors) chosen)
               (set/difference out-group neighbors))))))

(defn num-regions "Counts the number of edge-adjacent regions within a grid"
  [grid]
  (loop [region-count 0, points (set (all-pairs grid))]
    (if (empty? points)
      region-count
      (let [next-group (points-in-group points (first points))]
        (recur (inc region-count) (set/difference points next-group))))))

(defn part2 [input]
  (num-regions (run-grid-of-knot-hashes input)))
