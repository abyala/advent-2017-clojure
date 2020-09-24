(ns advent-2017-clojure.day17)

(defn spinlock [steps num-insertions]
  (loop [n 0, buffer '(0), pos 0]
    (if (= n num-insertions)
      buffer
      (let [insert-pos (inc (mod (+ pos steps) (count buffer)))]
        (recur (inc n)
               (concat (take insert-pos buffer) (cons (inc n) (drop insert-pos buffer)))
               insert-pos)))))

(defn value-after [buf target]
  (second (drop-while #(not= % target) buf)))

(defn part1 [steps]
  (value-after (spinlock steps 2017) 2017))


(defn value-after-zero [num-insertions steps]
  (loop [n 0, pos 0, last-write 0]
    (if (= n num-insertions)
      last-write
      (let [next-pos (inc (mod (+ pos steps) (inc n)))]
        (recur (inc n)
               next-pos
               (if (= next-pos 1) (inc n) last-write))))))

(def part2 (partial value-after-zero 50000000))