(ns  ^{:author "Jeremy Schoffen."}
  units.time-test
  (:refer-clojure :exclude [rem + - * / = not= < > <= >= zero? pos? neg? min max])
  (:use units.time
        midje.sweet
        clojure.algo.generic.arithmetic
        clojure.algo.generic.comparison))

(fact "units of time have a string representation"
  (str (s 10))  => "10s"
  (str (ms 10))  => "10ms")

(fact "We can test time types"
  (s? (s 10)) => truthy
  (s? (ms 10)) => falsey)


(fact "we can add two durations"
  (+ (s 1) (s 3)) => (s 4)
  (+ (ms 100) (ms 10)) => (ms 110))


(fact "we can negate a duration"
  (- (s 100)) => (s -100)
  (- (s -100)) => (s 100))

(fact "we can substract two durations"
  (- (s 1) (s 3)) => (s -2)
  (- (ms 100) (ms 10)) => (ms 90))

(fact "we can multiply a duration"
  (* (s 100) 10) => (s 1000)
  (* 10 (s 100)) => (s 1000))

(fact "we can divide a duration"
  (/ (s 100) 10) => (s 10))

(fact "We can convert durations types into one another"
  (-> 100.0 ms s) => (s 0.1)
  (-> 100 s ms) => (ms 100000))

(fact "We can add or substract duration of different types"
  (+ (s 1) (ms 1)) => (ms 1001)
  (- (ms 1000) (s 1)) => (s 0))


(fact "We can compare durations"
  (neg?  (s 10)) => falsey
  (pos?  (s 10)) => truthy
  (zero? (s 10)) => falsey

  (neg?  (s -10)) => truthy
  (pos?  (s -10)) => falsey
  (zero? (s -10)) => falsey

  (zero? (s 0)) => truthy


  (= (s 10) (s 10)) => truthy
  (= (s 1) (ms 1000)) => truthy

  (not= (s 10) (ms 10)) => truthy
  (not= (s 1) (ms 1000)) => falsey

  (< (s 10) (s 10)) => falsey
  (< (s 10) (ms 100)) => falsey

  (> (s 10) (s 10)) => falsey
  (> (s 10) (ms 100)) => truthy

  (<= (s 1) (ms 1000)) => truthy

  (<= (s 10) (ms 100)) => falsey

  (>= (s 10) (ms 1000)) => truthy

  (>= (s 100) (ms 100)) => truthy

  (min (s 1) (ms 1001)) => (s 1)
  (max (s 1) (ms 1001)) => (ms 1001))









