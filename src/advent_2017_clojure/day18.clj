(ns advent-2017-clojure.day18
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def alphabet-map
  (reduce merge (map #(hash-map (char %) 0)
                     (range (int \a) (inc (int \z))))))

(defstruct Duet :registers :pos :sound :recovered)

(defn reg-value [registers v]
  (let [eval (edn/read-string v)]
    (if (number? eval) eval (registers (first v)))))

; Individual processing commands
(defn update-register [loc v duet]
  (let [reg (duet :registers)]
    (assoc duet :registers (assoc reg loc (reg-value reg v)))))
(defn update-register-with [loc v fun duet]
  (let [reg (duet :registers)]
    (assoc duet :registers (assoc reg loc (fun (reg loc) (reg-value reg v))))))

(defn advance [duet] (assoc duet :pos (inc (duet :pos))))
(defn play-sound [loc duet] (assoc duet :sound ((duet :registers) loc)))
(defn recover-frequency [loc duet]
  (if (not= ((duet :registers) loc) 0)
    (assoc duet :recovered (duet :sound))
    duet))
(defn jump-or-advance [loc v duet]
  (if (> ((duet :registers) loc) 0)
    (assoc duet :pos (+ (duet :pos) (reg-value (duet :registers) v)))
    (advance duet)))

(defn apply-command [duet command]
  (let [[name a-raw b] (str/split command #" ")
        a (first a-raw)]
    (case name
      "snd" ((comp advance (partial play-sound a)) duet)
      "set" ((comp advance (partial update-register a b)) duet)
      "add" ((comp advance (partial update-register-with a b +)) duet)
      "mul" ((comp advance (partial update-register-with a b *)) duet)
      "mod" ((comp advance (partial update-register-with a b mod)) duet)
      "rcv" ((comp advance (partial recover-frequency a)) duet)
      "jgz" (jump-or-advance a b duet))))

(defn run-duet [input]
  (loop [duet (struct Duet alphabet-map 0)]
    (if (some? (duet :recovered))
      duet
      (recur (apply-command duet (input (duet :pos)))))))

(defn part1 [input]
  ((run-duet input) :recovered))
