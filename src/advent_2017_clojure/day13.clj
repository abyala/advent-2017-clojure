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

(defn move-layer [{range :range, pos :pos, dir :dir}]
  (let [next-pos (+ pos (case dir :up -1 :down 1))
        next-dir (cond (and (= dir :up) (= next-pos 0)) :down
                       (and (= dir :down) (= (inc next-pos) range)) :up
                       :else dir)]
    {:range range, :pos next-pos, :dir next-dir}))

(defn move-board [board]
  (into {} (map (fn [[k v]] [k (move-layer v)]) board)))

(defn walk-through-firewall [board]
  (let [max-depth (largest-depth board)]
    (loop [layers-caught #{}, current-board board, current-depth 0]
      (if (> current-depth max-depth)
        layers-caught
        (recur (if (= 0 (:pos (current-board current-depth)))
                 (conj layers-caught current-depth)
                 layers-caught)
               (move-board current-board)
               (inc current-depth))))))

(defn severity-of-firewalk [board]
  (reduce + (map #(* % (:range (board %)))
                 (walk-through-firewall board))))
(defn part1 [input-text]
  (severity-of-firewalk (create-board input-text)))
