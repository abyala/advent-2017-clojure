(ns advent-2017-clojure.day20
  (:require [clojure.string :as str]))

(def PATTERN #"p=<(.*),(.*),(.*)>, v=<(.*),(.*),(.*)>, a=<(.*),(.*),(.*)>")
(defn parse-line [line]
  (let [matches (map #(Integer/parseInt %)
                     (drop 1 (re-matches PATTERN line)))]
    {:x {:p (nth matches 0) :v (nth matches 3) :a (nth matches 6)}
     :y {:p (nth matches 1) :v (nth matches 4) :a (nth matches 7)}
     :z {:p (nth matches 2) :v (nth matches 5) :a (nth matches 8)}}))

(defn parse-input [input]
  (map-indexed (fn [idx line] (assoc (parse-line line) :id idx))
               (str/split-lines input)))

(defn map-ordinates [f particle]
  (reduce-kv #(assoc %1 %2 (f %3))
             particle
             (select-keys particle [:x :y :z])))

(defn sum-of-prop [name particle]
  (apply + (map name [(:x particle) (:y particle) (:z particle)])))

; ********* HEY: NOBODY CAN READ THIS!  DO BETTER...
(defn smallest-group [name particles]
  (second (first (sort-by #(first %)
                          (group-by #(sum-of-prop name %) particles)))))

(defn negate-all [ordinate]
  (reduce-kv #(assoc %1 %2 (- 0 %3)) {} ordinate))

(defn pos-acceleration [ordinate]
  (if (>= (:a ordinate) 0) ordinate (negate-all ordinate)))

(defn going-positive [particle]
  (map-ordinates pos-acceleration particle))

(defn part1 [input]
  (:id (first ((comp (partial smallest-group :p)
                     (partial smallest-group :v)
                     (partial smallest-group :a))
               (map going-positive (parse-input input))))))
