(ns advent-2017-clojure.day13
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-line [line]
  (let [[_ depth range] (re-matches #"(\d+): (\d+)" line)]
    [(Integer/parseInt depth) (Integer/parseInt range)]))

(defn create-board [input-text]
  (into {} (map #(let [[d r] (parse-line %)]
                   [d {:range r :pos 0 :dir :down}])
                (str/split-lines input-text))))

(defn largest-depth [board]
  (reduce max (keys board)))

(defn intersects-at-step [range step]
  (and (some? range)
       (= 0 (rem step
                 (* 2 (dec range))))))

(defn matches-during-walk [board initial-delay]
  (filter #(intersects-at-step (:range (board %)) (+ initial-delay %))
          (range (inc (largest-depth board)))))

(defn severity [board]
  (reduce + (map #(* % (:range (board %)))
                 (matches-during-walk board 0))))

(defn avoids-detection [board initial-delay]
  (empty? (matches-during-walk board initial-delay)))

; There's definitely some clever solution here using LCM, but let's go with clarity instead.
(defn delay-to-avoid-detection [board]
  (first (filter #(avoids-detection board %) (range))))

(defn part1 [input-text]
  (severity (create-board input-text)))

(defn part2 [input-text]
  (delay-to-avoid-detection (create-board input-text)))
