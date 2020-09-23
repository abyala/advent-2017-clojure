(ns advent-2017-clojure.day16
  (:require [clojure.string :as str]))


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
  (let [commands (str/split input-str #",")]
    (lazy-seq (cons (parse-command (first commands)) (parse-input (rest commands))))))

(defn alphabet-vec [size] (vec (map #(char (+ 97 %)) (range size))))

(defn part1 [input-str size]
  (apply str (reduce #((parse-command %2) %1)
                     (alphabet-vec size)
                     (str/split input-str #","))))

;; ;;;;;;;;
;; NOTE: After seeing part two, consider changing the dance steps into defs instead of defns.

