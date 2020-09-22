(ns advent-2017-clojure.utils
  (:require [clojure.string :as str]))

(defn abs [n] (if (< n 0) (- n) n))

(defn left-pad [size padding input]
  (str/replace (format (str "%" size "s") input) \  padding))