(ns advent-2017-clojure.day10)

(defn reverse-sub-array "Returns array with circular replacements of characters from pos"
  [ary, pos, len]
  (loop [loop-ary ary, low pos, high (+ pos len -1)]
    (if (>= low high)
      loop-ary
      (let [a (mod low (count ary)), b (mod high (count ary))]
        (recur (assoc loop-ary a (loop-ary b) b (loop-ary a)) (inc low) (dec high))))))

(defn apply-lines-to-ring "Applies all length substitutions to a ring"
  [ring, lines]
  (loop [array (:data ring), pos (:position ring), skip (:skip-size ring), [x & xs] lines]
    (if (nil? x)
      {:data array, :position pos, :skip-size skip}
      (recur (reverse-sub-array array pos x) (+ pos x skip) (inc skip) xs))))

(defn process-ring "Applies the length substitution \"times\" times to a ring of \"size\" size."
  [size times lines]
  (loop [ring {:data (vec (range size)), :position 0, :skip-size 0},
         times-remaining times]
    (if (= times-remaining 0)
      (:data ring)
      (recur (apply-lines-to-ring ring lines) (dec times-remaining)))))

(defn string-to-ascii "Converts each character in a String to ASCII"
  [word]
  (map #(int %) (seq word)))

(defn string-to-formatted-ascii "Converts a String to ASCII and applies the puzzle-required suffix"
  [word]
  (concat (string-to-ascii word) '(17 31 73 47 23)))

(defn to-hex "Converts a sequence of bytes to two-character hex"
  [digits]
  (apply str (map #(format "%02x" %) digits)))

(defn to-dense-hash "Applies the puzzle's dense-hash algorithm (hex representation of 16-byte xor)"
  [sparse-hash]
  (to-hex (map #(reduce bit-xor %) (partition 16 sparse-hash))))

(defn part1 [size lines]
  (let [[a b] (process-ring size 1 lines)]
    (* a b)))

(defn part2 [input-text]
  (let [ascii-codes (string-to-formatted-ascii input-text)
        ring (process-ring 256 64 ascii-codes)]
    (to-dense-hash ring)))



