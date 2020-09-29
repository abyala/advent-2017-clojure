(ns advent-2017-clojure.day18-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.day18 :refer :all]
            [clojure.string :as str]))

(def DUMMY_DUET (struct Duet {\a 10 \b 18 \c 0} 1 [] nil 0))
(def TEST_INPUT '["set a 1" "add a 2" "mul a a" "mod a 5"  "snd a" "set a 0" "rcv a " "jgz a -1" "set a 1" "jgz a -2"])
(def PUZZLE_INPUT "set i 31\nset a 1\nmul p 17\njgz p p\nmul a 2\nadd i -1\njgz i -2\nadd a -1\nset i 127\nset p 316\nmul p 8505\nmod p a\nmul p 129749\nadd p 12345\nmod p a\nset b p\nmod b 10000\nsnd b\nadd i -1\njgz i -9\njgz a 3\nrcv b\njgz b -1\nset f 0\nset i 126\nrcv a\nrcv b\nset p a\nmul p -1\nadd p b\njgz p 4\nsnd a\nset a b\njgz 1 3\nsnd b\nset f 1\nadd i -1\njgz i -11\nsnd a\njgz f -16\njgz a -19")
(def PUZZLE_VEC (str/split-lines PUZZLE_INPUT))



(deftest apply-command-tests
  (is (= (struct Duet {\a 10 \b 18 \c 0} 2 [18] nil 0)
         (apply-command DUMMY_DUET "snd b")))
  (is (= (struct Duet {\a 18 \b 18 \c 0} 2 [] nil 0)
         (apply-command DUMMY_DUET "set a b")))
  (is (= (struct Duet {\a 5 \b 18 \c 0} 2 [] nil 0)
         (apply-command DUMMY_DUET "set a 5")))
  (is (= (struct Duet {\a 15 \b 18 \c 0} 2 [] nil 0)
         (apply-command DUMMY_DUET "add a 5")))
  (is (= (struct Duet {\a 28 \b 18 \c 0} 2 [] nil 0)
         (apply-command DUMMY_DUET "add a b")))
  (is (= (struct Duet {\a 50 \b 18 \c 0} 2 [] nil 0)
         (apply-command DUMMY_DUET "mul a 5")))
  (is (= (struct Duet {\a 180 \b 18 \c 0} 2 [] nil 0)
         (apply-command DUMMY_DUET "mul a b")))
  (is (= (struct Duet {\a 1 \b 18 \c 0} 2 [] nil 0)
         (apply-command DUMMY_DUET "mod a 3")))
  (is (= (struct Duet {\a 10 \b 8 \c 0} 2 [] nil 0)
         (apply-command DUMMY_DUET "mod b a")))
  (is (= (struct Duet {\a 10 \b 18 \c 0} 2 [5] nil 0)
         (apply-command (struct Duet {\a 10 \b 18 \c 0} 1 [5] nil 0)
                        "rcv c")))
  (is (= (struct Duet {\a 10 \b 18 \c 0} 2 [5] 5 1)
         (apply-command (struct Duet {\a 10 \b 18 \c 0} 1 [5] nil 0)
                        "rcv b")))
  (is (= (struct Duet {\a 10 \b 18 \c 0} 11 [] nil 0)
         (apply-command DUMMY_DUET "jgz b a")))
  (is (= (struct Duet {\a 10 \b 18 \c 0} 2 [] nil 0)
         (apply-command DUMMY_DUET "jgz c a"))))

(deftest part1-test
  (testing "Test input"
    (is (= 4 (part1 TEST_INPUT))))
  (testing "Puzzle input"
    (is (= 2951 (part1 PUZZLE_VEC)))))

(deftest part2-test
  (testing "Test input"
    (is (= 3 (part2 ["snd 1" "snd 2" "snd p" "rcv a" "rcv b" "rcv c" "rcv d"]))))
  (testing "Puzzle input"
    (is (= 7493 (part2 PUZZLE_VEC)))))