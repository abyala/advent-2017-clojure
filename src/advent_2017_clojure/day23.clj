(ns advent-2017-clojure.day23
  (:require [clojure.core.async :refer [chan sliding-buffer poll! put! <!!]]
            [advent-2017-clojure.duet :as d]
            [clojure.string :as str]))

(def part1-action-map {"set" [d/state-changer d/set-register]
                       "sub" [d/state-changer d/subtract-register]
                       "mul" [d/state-changer (comp (partial d/increment-counter :mul-counter)
                                                    d/multiply-register)]
                       "jnz" [d/mover d/jump-from-not-zero]})

(defn part1 [input]
  (let [mul-counter (atom 0)
        duet (-> (d/new-duet) (assoc :mul-counter mul-counter))]
    (first (keep #(when (d/blocked? %) @mul-counter)
                 (d/run-duet part1-action-map input duet)))))

(defn not-prime? [n]
  (-> (BigInteger/valueOf n)
      (.isProbablePrime 5)
      not))

(defn part2 [input]
  (let [seed (Integer/parseInt (nth (str/split input #"\s") 2))]
    (->> (* seed 100)
         (+ 100000)
         (iterate (partial + 17))
         (take 1001)
         (filter not-prime?)
         count)))
