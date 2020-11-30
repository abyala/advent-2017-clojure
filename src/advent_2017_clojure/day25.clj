(ns advent-2017-clojure.day25
  (:require [clojure.string :as str]))

(defstruct turing-machine :tape :cursor :state)

(def new-turing-machine (struct turing-machine {} 0 :a))

(defn current-value [{:keys [:tape :cursor] :as tm}]
  (or (tape cursor) 0))

(defn run-step [instructions {:keys [:cursor :state] :as tm}]
  (let [[v move next-state] (-> instructions
                                (get state)
                                (get (current-value tm)))]
    (-> tm
        (assoc-in [:tape cursor] v)
        (update :cursor move)
        (assoc :state next-state))))

(defn part1 [instructions checksum-step]
  (let [last-state (nth (iterate (partial run-step instructions)
                                 new-turing-machine)
                        checksum-step)]
    (->> (:tape last-state)
         vals
         (filter pos?)
         count)))