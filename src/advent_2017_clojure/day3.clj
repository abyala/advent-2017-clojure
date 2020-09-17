(ns advent-2017-clojure.day3
  (:require [advent-2017-clojure.utils :refer [abs]]))

(defn move-point [[x y], dir]
  (case dir :up [x (inc y)], :down [x (dec y)], :left [(dec x) y], :right [(inc x) y]))

(defn at-border? [[x y], dir, border]
  (= border (case dir :up y, :down (- y), :left (- x), :right x)))

(defn next-dir [dir]
  (case dir :right :up, :up :left, :left :down, :down :right))

(defn spiral-coordinates
  ([]
   (spiral-coordinates [0, 0] :right 1))
  ([last-loc dir border]
   (let [next-loc (move-point last-loc dir)
         next-dir (if (at-border? next-loc dir border) (next-dir dir) dir)
         next-border (if (and (= dir :down) (= next-dir :right)) (inc border) border)]
     (lazy-seq (cons last-loc
                     (spiral-coordinates next-loc next-dir next-border))))))

(defn manhattan-distance [[x y]] (+ (abs x) (abs y)))

(defn distance-to-access-port [n]
  (manhattan-distance (nth (spiral-coordinates) (dec n))))

(defn adjacent-points [[x y]]
  (remove #(= [x y] [(first %) (second %)])
          (for [xs [(dec x) x (inc x)]
                ys [(dec y) y (inc y)]]
            [xs ys])))


(defn sum-of-adjacent-seq
  ([] (sum-of-adjacent-seq (spiral-coordinates) {(first (spiral-coordinates)) 1}))
  ([[x & xs] history]
   (let [next-loc (first xs)
         next-value (reduce + (keep #(history %) (adjacent-points next-loc)))]
     (lazy-seq (cons (history x)
                     (sum-of-adjacent-seq xs (merge history {next-loc next-value})))))))

(defn stress-test [n]
  (first (filter #(> % n) (sum-of-adjacent-seq))))
