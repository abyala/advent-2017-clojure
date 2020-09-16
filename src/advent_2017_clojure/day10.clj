(ns advent-2017-clojure.day10)

(defn reverse-sub-array [ary, pos, len]
  (loop [loop-ary ary, low pos, high (+ pos len -1)]
    (if (>= low high)
      loop-ary
      (let [a (mod low (count ary)), b (mod high (count ary))]
        (recur (assoc loop-ary a (loop-ary b) b (loop-ary a)) (inc low) (dec high))))))

(defn process-ring [size lines]
  (loop [array (vec (range size)), pos 0, skip 0, [x & xs] lines]
    (if (nil? x)
      array
      (recur (reverse-sub-array array pos x) (+ pos x skip) (inc skip) xs))))

(defn part1 [size lines]
  (let [[a b] (process-ring size lines)]
    (* a b)))
