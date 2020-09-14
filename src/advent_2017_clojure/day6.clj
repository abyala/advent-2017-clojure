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

; Test cases
(assert (= 2 (first-index-of-largest [0 2 7 0])))
(assert (= 1 (first-index-of-largest [2 4 1 2])))
(assert (= 0 (first-index-of-largest [3 1 2 3])))

(assert (= [2 4 1 2] (reallocate-banks [0 2 7 0])))
(assert (= [3 1 2 3] (reallocate-banks [2 4 1 2])))
(assert (= [0 2 3 4] (reallocate-banks [3 1 2 3])))

(assert (= 5 (num-reallocations-until-loop [0 2 7 0])))

; Actual data
(def INPUT_DATA "4\t1\t15\t12\t0\t9\t9\t5\t5\t8\t7\t3\t14\t5\t12\t3")
(def INPUT_BANK (vec (map #(Integer/parseInt %) (str/split INPUT_DATA #"\t"))))

(assert (= 6681 (num-reallocations-until-loop INPUT_BANK)))
(assert (= 2392 (loop-size INPUT_BANK)))
