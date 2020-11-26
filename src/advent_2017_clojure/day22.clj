(ns advent-2017-clojure.day22
  (:require [clojure.string :as str]))

; State: {:carrier [x y], :direction :up, :infected-nodes #{} :infection-count 0}

(def direction-map {:up    [:left :right [0 -1]]
                    :right [:up :down [1 0]]
                    :down  [:right :left [0 1]]
                    :left  [:down :up [-1 0]]})
(defn turn [change current]
  (->> (direction-map current)
       ((if (= change :left) first second))))

(defn move [point dir]
  (mapv + point dir))

(defn infected-indexes [line]
  (keep-indexed (fn [x c] (when (= c \#) x)) line))

(defn find-midpoint [input]
  (let [lines (str/split-lines input)]
    (vector (quot (count (first lines)) 2)
            (quot (count lines) 2))))

(defn find-infected-nodes [input]
  (->> (str/split-lines input)
       (map-indexed (fn [y line] (map #(vector % y) (infected-indexes line))))
       (apply concat)
       set))

(defn parse-input [input]
  {:carrier         (find-midpoint input)
   :direction       :up
   :infected        (find-infected-nodes input)
   :infection-count 0})

(defn infected? [{:keys [carrier infected]}] (contains? infected carrier))

(defn infect-or-clean [{carrier :carrier :as state}]
  (if (infected? state)
    (update state :infected #(disj % carrier))
    (as-> state s
          (update s :infected #(conj % carrier))
          (update s :infection-count inc))))

(defn turn-carrier [state]
  (update state :direction #(turn (if (infected? state) :right :left) %)))

(defn move-carrier [{direction :direction :as state}]
  (update state :carrier #(move % (nth (direction-map direction) 2))))

(defn run-burst [state]
  (->> (turn-carrier state)
       infect-or-clean
       move-carrier))

(defn part1 [input num-iterations]
  (-> (iterate run-burst (parse-input input))
      (nth num-iterations)
      :infection-count))