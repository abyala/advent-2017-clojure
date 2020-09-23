(ns advent-2017-clojure.day16
  (:require [clojure.string :as str]
            [advent-2017-clojure.utils :refer [split-commas]]))

(def one-billion 1000000000)

(defn spin [n dancers]
  (let [pivot (- (count dancers) n)]
    (vec (concat (drop pivot dancers) (take pivot dancers)))))

(defn exchange [x y dancers]
  (assoc dancers x (dancers y) y (dancers x)))

(defn partner [x y dancers]
  (assoc dancers (.indexOf dancers x) y (.indexOf dancers y) x))

(defn split-parameters [word] (str/split (subs word 1) #"\/"))

(defn parse-command [command]
  (case (first command)
    \s (partial spin (Integer/parseInt (subs command 1)))
    \x (let [[a b] (split-parameters command)]
         (partial exchange (Integer/parseInt a) (Integer/parseInt b)))
    \p (let [[a b] (split-parameters command)]
         (partial partner (first a) (first b)))))

(defn parse-input [input-str]
  (let [commands (split-commas input-str)]
    (lazy-seq (cons (parse-command (first commands)) (parse-input (rest commands))))))

(defn alphabet [size] (apply str (map #(char (+ (int \a) %)) (range size))))
(defn alphabet-vec [size] (vec (alphabet size)))

(defn dance [steps dancers]
  (apply str (reduce #((parse-command %2) %1)
                     dancers
                     (str/split steps #","))))

(defn part1 [input-str size]
  (dance input-str (alphabet-vec size)))

(defn part2 [input-str size]
  (loop [c 0, dancers (alphabet size), positions {}]
    (cond
      (= c one-billion) dancers
      (contains? positions dancers)
      (first (keep (fn [[pos idx]] (if (= idx (mod one-billion c)) pos)) positions))
      :else (recur (inc c) (dance input-str (vec dancers)) (assoc positions dancers c)))))
