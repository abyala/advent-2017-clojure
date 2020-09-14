(ns advent-2017-clojure.day6
  (:require [clojure.string :as str]))

(defn first-index-of-largest [banks]
  (let [largest (reduce max banks)]
    (first (keep #(when
                    (= (second %) largest)
                    (first %))
                 (map-indexed (fn [idx val] [idx val]) banks)))))

(defn next-wrapped [vec current]
  (if (= (inc current) (count vec)) 0 (inc current)))

(defn reallocate-banks [banks]
  (let [index (first-index-of-largest banks)]
    (loop [b (assoc banks index 0)
           i (next-wrapped b index)
           remaining (banks index)]
      (if (= 0 remaining)
        b
        (recur (assoc b i (inc (b i)))
               (next-wrapped b i)
               (dec remaining))))))

(defn get-first-cycle "Return information about first cycle within a memory bank (:num-reallocations and :loop-size)"
  [banks]
  (loop [b banks
         history {}
         cycles 0]
    (if (contains? history b)
      { :num-reallocations cycles, :loop-size (- cycles (history b))}
      (recur (reallocate-banks b)
             (merge history {b cycles})
             (inc cycles)))))

(defn num-reallocations-until-loop [banks] (:num-reallocations (get-first-cycle banks)))
(defn loop-size [banks] (:loop-size (get-first-cycle banks)))
