(ns  ^{:author "Jeremy Schoffen."}
  units.length-test
  (:refer-clojure :exclude [rem + - * / = not= < > <= >= zero? pos? neg? min max])
  (:use units.length
        midje.sweet
        clojure.algo.generic.arithmetic
        clojure.algo.generic.comparison))


(fact "units have a string representation"
  (str (px 10))  => "10px"
  (str (mm 10))  => "10mm"
  (str (rem 10)) => "10rem"
  (str (% 10))   => "10%")

(fact "We can test lenghts types"
  (em? (em 10)) => truthy
  (px? (em 10)) => falsey)


(fact "we can add two lengths"
  (+ (px 1) (px 3)) => (px 4)
  (+ (em 100) (em 10)) => (em 110))

(fact "we can negate a length"
  (- (em 100)) => (em -100)
  (- (em -100)) => (em 100))

(fact "we can substract two lengths"
  (- (px 1) (px 3)) => (px -2)
  (- (em 100) (em 10)) => (em 90))


(fact "we can multiply a length"
  (* (em 100) 10) => (em 1000)
  (* 10 (em 100)) => (em 1000))

(fact "we can divide a length"
  (/ (em 100) 10) => (em 10))

(fact "We can convert absolute lengths inot one another"
  (-> 100 mm cm) => (cm 10)
  (-> 100 mm in :mag) => (roughly (/ 100 10 2.54)))

(fact "We can add or substract absolute lengths"
  (+ (cm 2.54) (in 1)) => (in 2.0)
  (- (cm 2.54) (in 1)) => (in 0.0))

(fact "We can compare length types"
  (neg?  (cm 10)) => falsey
  (pos?  (cm 10)) => truthy
  (zero? (cm 10)) => falsey

  (neg?  (cm -10)) => truthy
  (pos?  (cm -10)) => falsey
  (zero? (cm -10)) => falsey

  (zero? (cm 0)) => truthy


  (= (cm 10) (cm 10)) => truthy
  (= (cm 10) (mm 100)) => truthy

  (not= (cm 10) (mm 10)) => truthy
  (not= (cm 10) (mm 100)) => falsey

  (< (cm 10) (cm 10)) => falsey
  (< (cm 10) (mm 100)) => falsey

  (> (cm 10) (cm 10)) => falsey
  (> (cm 10) (mm 100)) => falsey

  (<= (cm 10) (mm 100)) => truthy
  (<= (cm 10) (mm 100)) => truthy

  (<= (cm 100) (mm 100)) => falsey
  (<= (cm 100) (mm 100)) => falsey

  (>= (cm 10) (mm 100)) => truthy
  (>= (cm 10) (mm 100)) => truthy

  (>= (cm 100) (mm 100)) => truthy
  (>= (cm 100) (mm 100)) => truthy

  (min (cm 10) (mm 101)) => (cm 10)
  (max (cm 10) (mm 101)) => (mm 101))


