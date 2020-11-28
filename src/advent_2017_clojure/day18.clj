(ns advent-2017-clojure.day18
  (:require [clojure.core.async :refer [chan sliding-buffer poll! put! <!!]]
            [advent-2017-clojure.duet :as d]
            [clojure.string :as str]))

(def part1-action-map {"snd" [d/side-effect d/send-message]
                       "set" [d/state-changer d/set-register]
                       "add" [d/state-changer d/add-register]
                       "mul" [d/state-changer d/multiply-register]
                       "mod" [d/state-changer d/mod-register]
                       "rcv" [d/state-changer d/recover-frequency]
                       "jgz" [d/mover d/jump-from-greater-than-zero]})

(def part2-action-map
  (merge part1-action-map
         {"rcv" [d/state-changer d/receive-frequency]
          "snd" [d/state-changer (comp (partial d/increment-counter :send-counter)
                                       d/send-message)]}))

(defn parse-instructions [input]
  (mapv d/parse-instruction (str/split-lines input)))

(defn part1 [input]
  (let [c (chan (sliding-buffer 1))]
    (->> (d/run-duet part1-action-map input c c)
         (keep #(when-some [rec (:recovered %)] rec))
         first)))

(defn part2 [input]
  (let [send-counter (atom 0)
        c1 (chan (sliding-buffer 1024))
        c2 (chan (sliding-buffer 1024))
        d1 (-> (d/new-duet c1 c2)
               (d/set-register "p" 0))
        d2 (-> (d/new-duet c2 c1)
               (d/set-register "p" 1)
               (assoc :send-counter send-counter))
        actions part2-action-map
        instructions (parse-instructions input)]
    (->> [d1 d2]
         (iterate (fn [d] (doall (map #(d/take-action actions instructions %) d))))
         (keep #(when (every? d/blocked? %)
                  (-> (second %) :send-counter deref)))
         first)))