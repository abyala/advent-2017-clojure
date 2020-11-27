(ns advent-2017-clojure.duet
  (:require [clojure.core.async :refer [chan >!! <!!]]
            [clojure.string :as str]))

(defstruct Duet :registers :pos :inbox :outbox :recovered)

(defn new-duet
  ([] (let [c (chan)] (new-duet c c)))
  ([inbox outbox] (struct Duet {} 0 inbox outbox nil)))

(defmulti reg-value (fn [_ x] (type x)))
(defmethod reg-value java.lang.String [duet x]
  (or (get-in duet [:registers x]) 0))
(defmethod reg-value java.lang.Long [_ x] x)

(defn set-register [duet x y]
  (assoc-in duet [:registers x] (reg-value duet y)))

(defn apply-fn-to-registers [duet f x y]
  (set-register duet x (apply f (map (partial reg-value duet) [x y]))))

(defn add-register [duet x y]
  (apply-fn-to-registers duet + x y))

(defn multiply-register [duet x y]
  (apply-fn-to-registers duet * x y))

(defn mod-register [duet x y]
  (apply-fn-to-registers duet mod x y))

(defn play-sound [{outbox :outbox :as duet} x & _]
  (>!! outbox (reg-value duet x)))

(defn recover-frequency [{outbox :outbox :as duet} x & _]
  (when (not= 0 (reg-value duet x))
    (let [v (<!! outbox)]
      (if (zero? v) duet
                    (assoc duet :recovered v)))))

(defn jump-from-non-zero [duet x y]
  (when (pos? (reg-value duet x)) (reg-value duet y)))

(defn state-changer [f duet x y]
  (when-some [d (f duet x y)]
    {:duet d}))

(defn mover [f duet x y]
  (when-let [move (f duet x y)] {:move move}))

(defn side-effect [f duet x y]
  (f duet x y)
  nil)

(defn take-action [action-map instructions {pos :pos :as duet}]
  (when (< -1 pos (count instructions))
    (let [[cmd x y] (instructions pos)
          [action-class action] (action-map cmd)
          result (action-class action duet x y)
          {next-duet :duet move-by :move} (merge {:duet duet :move 1} result)]
      (update next-duet :pos (partial + move-by)))))

(defn parse-instruction [line]
  (->> (str/split line #" ")
       (mapv #(try (Long/parseLong %)
                   (catch NumberFormatException _ %)))))