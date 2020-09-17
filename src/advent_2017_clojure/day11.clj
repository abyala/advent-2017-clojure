(ns advent-2017-clojure.day11
  (:require [advent-2017-clojure.utils :refer [abs]])
  (:require [clojure.string :as str]))

(defn adjust-location [loc direction]
  (let [adjust (case direction
                 "n" [0 1]
                 "ne" [1 0.5]
                 "se" [1 -0.5]
                 "s" [0 -1]
                 "sw" [-1 -0.5]
                 "nw" [-1 0.5])]
    ; Lesson for the day: the map function is lazy, so it can cause a StackOverflowError if not evaluated as the
    ; return value of a tail recursive call.  Invoking "doall" forces an immediate evaluation.
    ; Of course, I could have just applied inc/dec instead of mapping the addition function to the adjustment vector,
    ; but I would have missed out on the "fun" of tracking down this bug!
    (doall (map + loc adjust))))

(defn steps-to-origin [[x y]]
  (let [ax (abs x), ay (abs y)]
    (int (+ ax (max 0 (- ay (/ ax 2)))))))

(defn apply-along-the-path [acc steps]
  (loop [loc [0 0], calc (acc loc nil), [x & xs] (str/split steps #",")]
    (if (nil? x)
      calc
      (let [next (adjust-location loc x)]
        (recur next (acc next calc) xs)))))

(defn final-distance [path]
  (steps-to-origin (apply-along-the-path (fn [loc _] loc)
                                         path)))

(defn max-distance [path]
  (apply-along-the-path (fn [loc last] (max (steps-to-origin loc) (or last 0)))
                        path))


;; The code below also worked by returning all steps in the path.
;; This worked fine, but the max-distance algorithm was significantly slower.
;(defn all-steps-along-path [steps]
;  (loop [path [[0 0]], [x & xs] steps]
;    (if (nil? x)
;      path
;      (recur (conj path (adjust-location (last path) x)) xs))))
;
;(defn final-distance [path]
;  (steps-to-origin (last (all-steps-along-path (str/split path #",")))))
;
;(defn max-distance [path]
;  (reduce max (map steps-to-origin (all-steps-along-path (str/split path #",")))))

