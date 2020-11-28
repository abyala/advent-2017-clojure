(ns advent-2017-clojure.duet-test
  (:require [clojure.test :refer :all]
            [advent-2017-clojure.duet :refer :all]
            [clojure.core.async :refer [chan sliding-buffer take! <!!]]))

(def duet (new-duet))

(deftest blocked?-test
  (is (false? (blocked? duet)))
  (is (true? (blocked? (assoc duet :blocked true))))
  (is (false? (blocked? (assoc duet :blocked false)))))

(deftest reg-value-test
  (is (= 0 (reg-value duet "a")))
  (is (= 0 (reg-value (set-register duet "b" 3) "a")))
  (is (= 3 (reg-value (set-register duet "b" 3) "b"))))

(deftest set-register-test
  (is (= {"a" 3}
         (:registers (set-register duet "a" 3))))
  (is (= {"a" 3 "b" 4}
         (:registers (-> duet
                         (set-register "a" 3)
                         (set-register "b" 4)))))
  (is (= {"a" 5}
         (:registers (-> duet
                         (set-register "a" 3)
                         (set-register "a" 5)))))
  (is (= {"a" 3 "b" 3}
         (:registers (-> duet
                         (set-register "a" 3)
                         (set-register "b" "a"))))))

(deftest add-register-test
  (is (= {"a" 3}
         (:registers (add-register duet "a" 3))))
  (is (= {"a" 6}
         (-> (iterate #(add-register % "a" 3) duet)
             (nth 2)
             :registers))))

(deftest subtract-register-test
  (is (= {"a" -3}
         (:registers (subtract-register duet "a" 3))))
  (is (= {"a" 2 "b" 3}
         (-> duet
             (set-register "a" 5)
             (set-register "b" 3)
             (subtract-register "a" "b")
             :registers))))

(deftest multiply-register-test
  (let [a-duet (-> duet (set-register "a" 2) (set-register "b" 3))]
    (is (= {"a" 6 "b" 3}
           (:registers (multiply-register a-duet "a" 3))))
    (is (= {"a" 2 "b" 12}
           (-> (iterate #(multiply-register % "b" "a") a-duet)
               (nth 2)
               :registers)))))

(deftest play-sound-test
  (testing "Single play"
    (let [c (chan (sliding-buffer 1))
          d (-> (new-duet c c)
                (set-register "a" 3)
                (set-register "b" 4))]
      (send-message d "a")
      (is (= 3 (<!! c)))))
  (testing "Only keep last sound playe4d"
    (let [c (chan (sliding-buffer 1))
          d (-> (new-duet c c)
                (set-register "a" 3)
                (set-register "b" 4))]
      (send-message d "a")
      (send-message d "b")
      (is (= 4 (<!! c))))))

(deftest recover-frequency-test
  (testing "Non-zero register"
    (let [c (chan (sliding-buffer 1))
          d (-> (new-duet c c)
                (set-register "a" 3))]
      (send-message d "a")
      (is (= 3
             (:recovered (recover-frequency d "a"))))))
  (testing "Last was zero"
    (let [c (chan (sliding-buffer 1))
          d (-> (new-duet c c)
                (set-register "a" 0)
                (set-register "b" 1))]
      (send-message d "b")
      (is (nil? (:recovered (recover-frequency d "a"))))))
  (testing "Overwrite value"
    (let [c (chan (sliding-buffer 1))
          d (-> (new-duet c c)
                (set-register "a" 3)
                (set-register "b" 4))]
      (send-message d "a")
      (send-message d "b")
      (is (= 4 (:recovered (recover-frequency d "a")))))))

(deftest jump-from-greater-than-zero-test
  (let [d (-> duet (set-register "a" 3) (set-register "b" 4))]
    (is (= 4 (jump-from-greater-than-zero d "a" "b")))
    (is (nil? (jump-from-greater-than-zero d "c" "b")))))

(deftest jump-from-not-zero-test
  (let [d (-> duet
              (set-register "a" 3)
              (set-register "b" 4)
              (set-register "c" 0))]
    (is (= 4 (jump-from-not-zero d "a" "b")))
    (is (nil? (jump-from-not-zero d "c" "b")))
    (is (nil? (jump-from-not-zero d "d" "b")))))

(deftest parse-instruction-test
  (is (= ["add" "a" "b"] (parse-instruction "add a b")))
  (is (= ["set" "c" 5] (parse-instruction "set c 5"))))
