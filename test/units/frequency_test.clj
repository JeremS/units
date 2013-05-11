(ns  ^{:author "Jeremy Schoffen."}
  units.frequency-test
  (:refer-clojure :exclude [rem + - * / = not= < > <= >= zero? pos? neg? min max])
  (:use units.frequency
        midje.sweet
        clojure.algo.generic.arithmetic
        clojure.algo.generic.comparison))


(fact "Frequencies have a string representation"
  (str (hz 10))  => "10Hz"
  (str (khz 10))  => "10kHz")

(fact "We can test frequency types"
  (hz? (hz 10)) => truthy
  (hz? (khz 10)) => falsey)


(fact "we can add two frequencies"
  (+ (hz 1) (hz 3)) => (hz 4)
  (+ (khz 100) (khz 10)) => (khz 110))


(fact "we can negate a frequency"
  (- (hz 100)) => (hz -100)
  (- (hz -100)) => (hz 100))

(fact "we can substract two frequencies"
  (- (khz 1) (khz 3)) => (khz -2)
  (- (hz 100) (hz 10)) => (hz 90))

(fact "we can multiply a frequency"
  (* (khz 100) 10) => (khz 1000)
  (* 10 (khz 100)) => (khz 1000))

(fact "we can divide a frequency"
  (/ (khz 100) 10) => (khz 10))

(fact "We can convert frequencies into one another"
  (-> 100.0 hz khz) => (khz 0.1)
  (-> 100 khz hz) => (hz 100000))

(fact "We can add or substract frequencies of different types"
  (+ (khz 1) (hz 1)) => (hz 1001)
  (- (hz 1000) (khz 1)) => (khz 0))


(fact "We can compare frequencies"
  (neg?  (khz 10)) => falsey
  (pos?  (khz 10)) => truthy
  (zero? (khz 10)) => falsey

  (neg?  (khz -10)) => truthy
  (pos?  (khz -10)) => falsey
  (zero? (khz -10)) => falsey

  (zero? (khz 0)) => truthy


  (= (khz 10) (khz 10)) => truthy
  (= (khz 1) (hz 1000)) => truthy

  (not= (khz 10) (hz 10)) => truthy
  (not= (khz 1) (hz 1000)) => falsey

  (< (khz 10) (khz 10)) => falsey
  (< (khz 10) (hz 100)) => falsey

  (> (khz 10) (khz 10)) => falsey
  (> (khz 10) (hz 100)) => truthy

  (<= (khz 1) (hz 1000)) => truthy

  (<= (khz 10) (hz 100)) => falsey

  (>= (khz 10) (hz 1000)) => truthy

  (>= (khz 100) (hz 100)) => truthy

  (min (khz 1) (hz 1001)) => (khz 1)
  (max (khz 1) (hz 1001)) => (hz 1001))








