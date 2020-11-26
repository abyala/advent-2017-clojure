(ns advent-2017-clojure.day22-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day22 :refer :all]))

(def TEST_INPUT "..#\n#..\n...")
(def PUZZLE_INPUT (slurp "test\\advent_2017_clojure\\day22_data.txt"))

(deftest turn-test
  (is (= :right (turn :right :up)))
  (is (= :left (turn :left :up)))
  (is (= :down (turn :right :right))))

(deftest move-test
  (is (= [1 1] (move [1 2] [0 -1])) "Up test"))

(deftest parse-input-test
  (is (= {:carrier         [1 1]
          :direction       :up
          :unclean         {[2 0] :infected, [0 1] :infected}
          :infection-count 0}
         (parse-input TEST_INPUT))))

(deftest affect-carrier-node-test
  (is (= {:carrier [3 3] :unclean {[1 1] :infected [2 2] :infected [3 3] :infected}
          :infection-count 6}
         (affect-carrier-node :infected
                              {:carrier         [3 3]
                               :unclean         {[1 1] :infected
                                                 [2 2] :infected}
                               :infection-count 5})))
  (is (= {:carrier [1 1] :unclean {[2 2] :infected}
          :infection-count 5}
         (affect-carrier-node nil
                              {:carrier         [1 1]
                               :unclean         {[1 1] :infected
                                                 [2 2] :infected}
                               :infection-count 5}))))

(deftest turn-carrier-test
  (is (= :left
         (:direction (turn-carrier {:carrier [0 0] :direction :up :unclean {[1 1] :infected} :infection-count 2}))))
  (is (= :right
         (:direction (turn-carrier {:carrier [1 1] :direction :up :unclean {[1 1] :infected} :infection-count 2}))))
  (is (= :up
         (:direction (turn-carrier {:carrier [1 1] :direction :left :unclean {[1 1] :infected} :infection-count 2})))))

(deftest move-carrier-test
  (is (= [3 4]
         (:carrier (move-carrier {:carrier [3 3] :direction :down}))))
  (is (= [2 3]
         (:carrier (move-carrier {:carrier [3 3] :direction :left})))))

(deftest part1-test
  (is (= 41 (part1 TEST_INPUT 70)))
  (is (= 5587 (part1 TEST_INPUT 10000)))
  (is (= 5339 (part1 PUZZLE_INPUT 10000))))

(deftest simple-virus-impact-test
  (is (= :infected (simple-virus-impact nil)))
  (is (nil? (simple-virus-impact :infected))))

(deftest complex-virus-impact-test
  (is (= :weakened (complex-virus-impact nil)))
  (is (= :infected (complex-virus-impact :weakened)))
  (is (= :flagged (complex-virus-impact :infected)))
  (is (nil? (complex-virus-impact :flagged))))

(deftest part2-test
  (is (= 26 (part2 TEST_INPUT 100)))
  (is (= 2511944 (part2 TEST_INPUT 10000000)))
  (is (= 2512380 (part2 PUZZLE_INPUT 10000000))))