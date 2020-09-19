(ns advent-2017-clojure.day12
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-line "Parses the input line, based on format \"ID <-> a, b, c\""
  [line]
  (let [[_ id rhs] (re-matches #"(\w+) <-> (.*)" line)
        links (set (str/split rhs #", "))]
    {id links}))

(defn find-group-containing "Finds all values connected to the initial value"
  [input-map initial-value]
  (loop [seen #{}, candidates #{initial-value}]
    (if (empty? candidates)
      seen
      (let [next (first candidates)
            next-seen (conj seen next)
            next-candidates (set/difference (set/union candidates (input-map next))
                                            next-seen)]
        (recur next-seen next-candidates)))))

(defn split-into-groups "Converts the input map into a collection of sets of non-overlapping values"
  [input-map]
  (loop [groups (), remaining input-map]
    (if (empty? remaining)
      groups
      (let [[choice _] (first remaining)]
        (let [new-group (find-group-containing remaining choice)]
          (recur (merge groups new-group) (reduce dissoc remaining new-group)))))))

(defn apply-to-groups "Applies function fun to the set of all non-overlapping groups"
  [fun input-str]
  (fun (split-into-groups (reduce merge (map parse-line (str/split-lines input-str))))))

(defn part1 "Counts the number of elements in the group containing \"0\""
  [input-string]
  (apply-to-groups (fn [groups] (first (keep #(if (contains? % "0") (count %))
                                             groups)))
                   input-string))

(defn part2 "Counts the number of non-overlapping groups"
  [input-string]
  (apply-to-groups count input-string))
