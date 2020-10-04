(ns advent-2017-clojure.day20-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day20 :refer :all]))

(def TEST_INPUT "p=<3,0,0>, v=<2,0,0>, a=<-1,0,0>\np=<4,0,0>, v=<0,0,0>, a=<-2,0,0>")
(def PUZZLE_INPUT (slurp "test\\advent_2017_clojure\\day20_data.txt"))


(deftest going-positive-test
    (testing "Positive overall"
      (is (= {:x {:p 1 :v 2 :a 3} :y {:p 4 :v 5 :a 6} :z {:p 7 :v 8 :a 9}}
             (going-positive {:x {:p 1 :v 2 :a 3} :y {:p -4 :v -5 :a -6} :z {:p 7 :v 8 :a 9}}))))
    (testing "Negative overall"
      (is (= {:x {:p -1 :v -2 :a 4} :y {:p 4 :v 5 :a 6} :z {:p 7 :v 8 :a 9}}
             (going-positive {:x {:p 1 :v 2 :a -4} :y {:p -4 :v -5 :a -6} :z {:p 7 :v 8 :a 9}})))))

(deftest part1-test
  (is (= 0 (part1 TEST_INPUT)))
  (is (= 0 (part1 "p=<1,2,3>, v=<1,2,3>, a=<-1,-2,-3>\np=<1,2,3>, v=<1,2,4>, a=<1,2,3>")))
  (is (= 0 (part1 "p=<1,2,3>, v=<1,2,3>, a=<-1,-2,-3>\np=<1,2,3>, v=<1,2,1>, a=<1,2,3>")))
  (is (= 1 (part1 "p=<1,2,3>, v=<-1,-2,-3>, a=<-1,-2,-3>\np=<1,2,3>, v=<1,2,1>, a=<1,2,3>")))
  (is (= 0 (part1 "p=<1,2,4>, v=<-1,-2,-1>, a=<-1,-2,-3>\np=<1,2,3>, v=<1,2,1>, a=<1,2,3>")))
  (is (= 1 (part1 "p=<-1,-2,-4>, v=<-1,-2,-1>, a=<-1,-2,-3>\np=<1,2,3>, v=<1,2,1>, a=<1,2,3>")))
  (is (= 144 (part1 PUZZLE_INPUT))))
