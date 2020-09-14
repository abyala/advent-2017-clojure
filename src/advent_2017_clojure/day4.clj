(ns advent-2017-clojure.day4
  (:require [clojure.string :as str]))

(defn valid-passphrase? "Returns true if all words in the vector are unique"
  [words]
  (every? #(= (second %) 1) (frequencies words)))

(defn count-valid-passphrases "Counts the number of valid passphrases, based on a tranformation function on each word"
  [transform-fn passphrases]
  (count (filter (fn [words] (valid-passphrase? (map transform-fn words)))
                 passphrases)))

; This is ugly/confusing, but it shows how to use def to reference a function, and defn to define a new one.
; In "real" production code, I think I'd probably expand the low-security-algorithm for better readability.
(def low-security-algorithm "Low-security passphrase check that does not modify input passphrases"
  identity)
(defn high-security-algorithm "High-security passphrase that searches for anagrams of input passphrases"
  [passphrase] (apply str (sort passphrase)))

(defn parse-spaced-lines "Parses a string into lines of spaced strings"
  [doc]
  (map (fn [line] (str/split line #" "))
       (str/split doc #"\n")))
