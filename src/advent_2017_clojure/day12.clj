(ns advent-2017-clojure.day12
  (:require [clojure.string :as str]
            [clojure.set :as set])
  )

(defn parse-line [line]
  (let [[_ id rhs] (re-matches #"(\w+) <-> (.*)" line)
        links (set (str/split rhs #", "))]
    {id links}))

(defn num-connected-programs [input-map]
  (loop [seen #{}, candidates #{"0"}]
    (if (empty? candidates)
      (count seen)
      (let [next (first candidates)
            next-seen (conj seen next)
            next-candidates (set/difference (set/union candidates (input-map next))
                                            next-seen)]
        (recur next-seen next-candidates)))))

(defn part1 [input-string]
  (num-connected-programs (reduce merge (map parse-line (str/split input-string #"\n")))))