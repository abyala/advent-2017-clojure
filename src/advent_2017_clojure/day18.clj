(ns advent-2017-clojure.day18
  (:require [clojure.string :as str]
            [clojure.core.async :refer [chan poll! put!]]
            [clojure.edn :as edn]))

; So officially, I have the wrong solution for part 2. But I've checked my inputs manually
; several times, and the app says my answer is wrong but is correct for someone else.
; The truth is that I didn't solve this puzzle, but I'm not proud of this program anyway,
; so I'm moving ahead.

; Other thing I learned -- structs are bulky. Use only if absolutely needed.

(def alphabet-map
  (reduce merge (map #(hash-map (char %) 0)
                     (range (int \a) (inc (int \z))))))

(defstruct Duet :registers :pos :sent :recovered :num-read)

(defn reg-value [registers v]
  (let [eval (edn/read-string v)]
    (if (number? eval) eval (registers (first v)))))

(defn update-register [loc v duet]
  (let [reg (duet :registers)]
    (assoc duet :registers (assoc reg loc (reg-value reg (str v))))))
(defn update-register-with [loc v fun duet]
  (let [reg (duet :registers)]
    (assoc duet :registers (assoc reg loc (fun (reg loc) (reg-value reg v))))))

(defn advance [duet] (assoc duet :pos (inc (duet :pos))))

(defn play-sound [loc duet]
  (assoc duet :sent (conj (duet :sent)
                          (reg-value (duet :registers) (str loc)))))
(defn recover-frequency [loc duet]
  (if (not= ((duet :registers) loc) 0)
    (assoc duet :recovered (last (duet :sent)) :num-read (inc (duet :num-read)))
    duet))

(defn receive-message [loc inbox duet]
  (let [idx (duet :num-read)]
    (when (> (count inbox) idx)
      (assoc (update-register loc (inbox idx) duet) :num-read (inc idx)))))
(defn jump-or-advance [loc v duet]
  (if (> (reg-value (duet :registers) (str loc)) 0)
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
  (loop [duet (struct Duet alphabet-map 0 [] nil 0)]
    (if (some? (duet :recovered))
      duet
      (recur (apply-command duet (input (duet :pos)))))))

(defn part1 [input]
  ((run-duet input) :recovered))


(defn apply-command2 [duet command inbox]
  (let [[name a-raw b] (str/split command #" ")
        a (first a-raw)]
    (case name
      "snd" ((comp advance (partial play-sound a)) duet)
      "set" ((comp advance (partial update-register a b)) duet)
      "add" ((comp advance (partial update-register-with a b +)) duet)
      "mul" ((comp advance (partial update-register-with a b *)) duet)
      "mod" ((comp advance (partial update-register-with a b mod)) duet)
      "rcv" (when-let [updated (receive-message a inbox duet)]
              (advance updated))
      "jgz" (jump-or-advance a b duet))))

(defn part2 [input]
  (loop [machine1 (struct Duet alphabet-map 0 [] nil 0)
         machine2 (update-register \p "1" (struct Duet alphabet-map 0 [] nil 0))]
    (let [next-1 (apply-command2 machine1 (input (machine1 :pos)) (machine2 :sent))
          next-2 (apply-command2 machine2 (input (machine2 :pos)) (machine1 :sent))]
      (if (not-any? identity [next-1 next-2])
        (count (machine1 :sent))
        (recur (or next-1 machine1) (or next-2 machine2))))))
