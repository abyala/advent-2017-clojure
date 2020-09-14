(ns advent-2017-clojure.day3)

(defn move-point [[x y], dir]
  (case dir :up [x (inc y)], :down [x (dec y)], :left [(dec x) y], :right [(inc x) y]))

(defn at-border? [[x y], dir, border]
  (= border (case dir :up y, :down (- y), :left (- x), :right x)))

(defn next-dir [dir]
  (case dir :right :up, :up :left, :left :down, :down :right))

(defn spiral-coordinates
  ([]
   (spiral-coordinates [0, 0] :right 1))
  ([last-loc dir border]
   (let [next-loc (move-point last-loc dir)
         next-dir (if (at-border? next-loc dir border) (next-dir dir) dir)
         next-border (if (and (= dir :down) (= next-dir :right)) (inc border) border)]
     (lazy-seq (cons last-loc
                     (spiral-coordinates next-loc next-dir next-border))))))


(defn abs [n] (if (< n 0) (- n) n))
(defn manhattan-distance [[x y]] (+ (abs x) (abs y)))

(defn distance-to-access-port [n]
  (manhattan-distance (nth (spiral-coordinates) (dec n))))

(defn adjacent-points [[x y]]
  (remove #(= [x y] [(first %) (second %)])
          (for [xs [(dec x) x (inc x)]
                ys [(dec y) y (inc y)]]
            [xs ys])))


(defn sum-of-adjacent-seq
  ([] (sum-of-adjacent-seq (spiral-coordinates) {(first (spiral-coordinates)) 1}))
  ([[x & xs] history]
   (let [next-loc (first xs)
         next-value (reduce + (keep #(history %) (adjacent-points next-loc)))]
     (lazy-seq (cons (history x)
                     (sum-of-adjacent-seq xs (merge history {next-loc next-value})))))))

(defn stress-test [n]
  (first (filter #(> % n) (sum-of-adjacent-seq))))

; Test cases
(assert (= [3 6] (move-point [3 5] :up)))
(assert (= [3 4] (move-point [3 5] :down)))
(assert (= [2 5] (move-point [3 5] :left)))
(assert (= [4 5] (move-point [3 5] :right)))
(assert (at-border? [3 5] :up 5))
(assert (cond at-border? [3 -5] :up 5))
(assert (cond at-border? [3 5] :down 5))
(assert (at-border? [3 -5] :down 5))
(assert (cond at-border? [3 5] :left 3))
(assert (at-border? [-3 5] :left 3))
(assert (at-border? [3 5] :right 3))
(assert (cond at-border? [-3 5] :right 3))
(assert (= 0 (abs 0)))
(assert (= 4 (abs 4)))
(assert (= 4 (abs -4)))
(assert (= 0 (distance-to-access-port 1)))
(assert (= 3 (distance-to-access-port 12)))
(assert (= 2 (distance-to-access-port 23)))
(assert (= 31 (distance-to-access-port 1024)))

; Actual data
(assert (= 326 (distance-to-access-port 361527)))
(assert (= 363010 (stress-test 361527)))



