(ns advent-2017-clojure.day5)

(defn escape-from-maze "Walks through the maze, changing the previous value based on the supplied function"
  [maze replace-fun]
  (loop [pos 0, m maze, steps 0]
    (if (>= pos (count m))
      steps
      (recur (+ pos (m pos))
             (assoc m pos (replace-fun (m pos)))
             (inc steps)))))

(defn simple-maze-walk [maze] (escape-from-maze maze inc))
(defn complex-maze-walk [maze] (escape-from-maze maze #(if (>= % 3) (dec %) (inc %))))
