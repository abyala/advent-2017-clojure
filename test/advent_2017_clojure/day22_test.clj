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
          :direction      :up
          :infected        #{[2 0] [0 1]}
          :infection-count 0}
         (parse-input TEST_INPUT))))

(deftest infect-or-clean-test
  (is (= {:carrier [3 3] :infected #{[1 1] [2 2] [3 3]} :infection-count 6}
         (infect-or-clean {:carrier [3 3] :infected #{[1 1] [2 2]} :infection-count 5})))
  (is (= {:carrier [1 1] :infected #{[2 2]} :infection-count 5}
         (infect-or-clean {:carrier [1 1] :infected #{[1 1] [2 2]} :infection-count 5}))))

(deftest turn-carrier-test
  (is (= :left
         (:direction (turn-carrier {:carrier [0 0] :direction :up :infected #{[1 1]} :infection-count 2}))))
  (is (= :right
         (:direction (turn-carrier {:carrier [1 1] :direction :up :infected #{[1 1]} :infection-count 2}))))
  (is (= :up
         (:direction (turn-carrier {:carrier [1 1] :direction :left :infected #{[1 1]} :infection-count 2})))))

(deftest move-carrier-test
  (is (= [3 4]
         (:carrier (move-carrier {:carrier [3 3] :direction :down}))))
  (is (= [2 3]
         (:carrier (move-carrier {:carrier [3 3] :direction :left})))))

(deftest part1-test
  (is (= 41 (part1 TEST_INPUT 70)))
  (is (= 5587 (part1 TEST_INPUT 10000)))
  (is (= 5339 (part1 PUZZLE_INPUT 10000))))