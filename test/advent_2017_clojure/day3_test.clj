(ns advent-2017-clojure.day3-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day3 :refer :all]
            [advent-2017-clojure.utils :refer [abs]]))

(deftest move-point-tests
  (is (= [3 6] (move-point [3 5] :up)))
  (is (= [3 4] (move-point [3 5] :down)))
  (is (= [2 5] (move-point [3 5] :left)))
  (is (= [4 5] (move-point [3 5] :right))))

(deftest  at-border-tests
  (is (at-border? [3 5] :up 5))
  (is (cond at-border? [3 -5] :up 5))
  (is (cond at-border? [3 5] :down 5))
  (is (at-border? [3 -5] :down 5))
  (is (cond at-border? [3 5] :left 3))
  (is (at-border? [-3 5] :left 3))
  (is (at-border? [3 5] :right 3))
  (is (cond at-border? [-3 5] :right 3)))

(deftest abs-tests
  (is (= 0 (abs 0)))
  (is (= 4 (abs 4)))
  (is (= 4 (abs -4))))

(deftest distance-to-access-port-tests
  (testing "With provided sample data"
    (is (= 0 (distance-to-access-port 1)))
    (is (= 3 (distance-to-access-port 12)))
    (is (= 2 (distance-to-access-port 23)))
    (is (= 31 (distance-to-access-port 1024))))
  (testing "With puzzle data"
    (is (= 326 (distance-to-access-port 361527)))))

(deftest stress-test-tests
  (testing "With puzzle data"                               ;Shame on me - no unit tests!
    (is (= 363010 (stress-test 361527)))))
