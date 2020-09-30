(ns advent-2017-clojure.day19
  (:require [clojure.string :as str]))

(defn create-maze [input]
  (reduce merge (map-indexed (fn [y line]
                               (reduce merge (keep-indexed (fn [x c] (when (not= c \space) {[x y] c})) line)))
                             (str/split-lines input))))

(defn start-of-maze [maze]
  (ffirst (filter (fn [[[_ y] _]] (= y 0))
                 maze)))

(defn adjacent-point [[x y] dir]
  (case dir
    :up [x (dec y)]
    :down [x (inc y)]
    :left [(dec x) y]
    :right [(inc x) y]))

(defn turning-points [point dir]
  (map #(vector (adjacent-point point %) %) (if (contains? #{:up :down} dir)
                                         '(:left :right)
                                         '(:up :down))))

(defn move [point dir maze]
  (let [next (adjacent-point point dir)]
    (if (not= (maze next) \+)
      [next dir]
      [next (first (keep #(when (contains? maze (first %)) (second %))
                         (turning-points next dir)))])))


(defn is-letter? [c]
  (let [i (int c)]
    (and (>= i (int \A)) (<= i (int \Z)))))

(defn path-through-maze [maze]
  (loop [found [], loc (start-of-maze maze), dir :down]
    ;(println "Looped, now at loc" loc "and direction" dir)
    (let [val (maze loc) ]
      (if (nil? val)
        ;(println "We're at the end! Found=" found)
        (apply str found)
        (let [[next-loc next-dir] (move loc dir maze)]
          (recur (if (is-letter? val) (conj found val) found)
                 next-loc
                 next-dir))))))

(defn part1 [input] (path-through-maze (create-maze input)))
