(ns advent-2017-clojure.day18
  (:require [clojure.core.async :refer [chan sliding-buffer poll! put! <!!]]
            [advent-2017-clojure.duet :as d]
            [clojure.string :as str]))

(def part1-action-map {"snd" [d/side-effect d/play-sound]
                       "set" [d/state-changer d/set-register]
                       "add" [d/state-changer d/add-register]
                       "mul" [d/state-changer d/multiply-register]
                       "mod" [d/state-changer d/mod-register]
                       "rcv" [d/state-changer d/recover-frequency]
                       "jgz" [d/mover d/jump-from-non-zero]})

(defn parse-instructions [input]
  (mapv d/parse-instruction (str/split-lines input)))

(defn part1 [input]
  (let [c (chan (sliding-buffer 1))
        actions part1-action-map
        instructions (parse-instructions input)]
    (->> (iterate (partial d/take-action actions instructions)
                  (d/new-duet c c))
         (keep #(when-some [rec (:recovered %)] rec))
         first)))