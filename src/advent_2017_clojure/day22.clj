(ns advent-2017-clojure.day22
  (:require [clojure.string :as str]))

; State: {:carrier [x y],
;         :direction :up,
;         :unclean {[x y] :status}
;         :infection-count 0}

(def direction-map
  "Maps a direction to [look-left, look-right, look-back, move-amount]"
  {:up    [:left :right :down [0 -1]]
   :right [:up :down :left [1 0]]
   :down  [:right :left :up [0 1]]
   :left  [:down :up :right [-1 0]]})

(defn turn
  "Given a change [:left, :right, :reverse, nil] and the current direction, return the direction after turning."
  [change current]
  (let [mappings (direction-map current)]
    (case change
      :left (first mappings)
      :right (second mappings)
      :reverse (nth mappings 2)
      nil current)))

(defn move
  "Simply adds two points together."
  [point dir]
  (mapv + point dir))

(defn infected-indexes
  "Given a line of periods and hash marks, the indexes of all hash marks (aka infected cells)."
  [line]
  (keep-indexed (fn [x c] (when (= c \#) x)) line))

(defn find-midpoint
  "Given a rectangular grid of strings, returns the [x,y] coordinates of the center cell."
  [input]
  (let [lines (str/split-lines input)]
    (vector (quot (count (first lines)) 2)
            (quot (count lines) 2))))

(defn find-infected-nodes
  "Given an input of an infection graph, returns {[x,y] :infected} for all cells marked with a hash mark."
  [input]
  (->> (str/split-lines input)
       (map-indexed (fn [y line] (map #(vector % y) (infected-indexes line))))
       (apply concat)
       (map #(vector % :infected))
       (into {})))

(defn parse-input
  "Given an input of an infection graph, returns the starting state. This includes:
  - :carrier - the initial point of the virus carrier
  - :direction - the initial direction the carrier is facing
  - :unclean - a map of all dimensions that are not clean, to their current status
  - :infection-count - the number of times a cell became infected"
  [input]
  {:carrier         (find-midpoint input)
   :direction       :up
   :unclean         (find-infected-nodes input)
   :infection-count 0})

(defn status-at-carrier
  "Returns the current unclean status of the virus carrier's position, or nil if it's clean."
  [{:keys [carrier unclean]}] (unclean carrier))

(defn turn-carrier
  "Updates the direction of the carrier based on the status of its currently-occupied cell."
  [state]
  (update state :direction #(turn (case (status-at-carrier state)
                                    nil :left
                                    :weakened nil
                                    :infected :right
                                    :flagged :reverse) %)))

(defn affect-carrier-node
  "Updates the currently-occupied cell, based on the already-calculated next status it should have."
  [next-status {carrier :carrier :as state}]
  (as-> state s
        (update s :unclean #(if (some? next-status)
                              (assoc % carrier next-status)
                              (dissoc % carrier)))
        (update s :infection-count (if (= next-status :infected) inc identity))))

(defn move-carrier
  "Updates the position of the virus carrier, based on its current direction."
  [{direction :direction :as state}]
  (update state :carrier #(move % (nth (direction-map direction) 3))))

(defn run-burst
  "Performs the next \"burst\" steps of the virus - turn, affect its current cell, and move forward."
  [virus-alg state]
  (let [next-status (virus-alg (status-at-carrier state))]
    (->> state
         turn-carrier
         (affect-carrier-node next-status)
         move-carrier)))

(defn solve
  "Run \"num-iterations\" bursts of the input state, and return the final infection count."
  [input num-iterations virus-alg]
  (-> (iterate (partial run-burst virus-alg)
               (parse-input input))
      (nth num-iterations)
      :infection-count))

(defn simple-virus-impact
  "Part 1 virus algorithm, which makes clean cells infected, and vice versa."
  [node]
  (if (some? node) nil :infected))

(defn complex-virus-impact
  "Part 2 virus algorithm, which rotates cells from clean to weakened, to infected, to flagged, to clean."
  [node]
  (case node
    nil :weakened
    :weakened :infected
    :infected :flagged
    :flagged nil))

(defn part1 [input num-iterations]
  (solve input num-iterations simple-virus-impact))

(defn part2 [input num-iterations]
  (solve input num-iterations complex-virus-impact))
