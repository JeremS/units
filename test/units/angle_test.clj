(ns units.angle-test
  (:refer-clojure :exclude [+ - * / = not= < > <= >= zero? pos? neg? min max])
  (:require [units.angle :refer :all])
  (:use midje.sweet
        clojure.algo.generic.arithmetic
        clojure.algo.generic.comparison))

(fact "units have a string representation"
  (str (deg  10)) => "10deg"
  (str (grad 10)) => "10grad")


(fact "We can test angles types"
  (rad? (rad 10)) => truthy
  (rad? (deg 10)) => falsey)

(fact "we can add two angles"
  (+ (deg 1) (deg 3)) => (deg 4)
  (+ (rad 100) (rad 10)) => (contains {:mag (roughly (:mag (rad 110)))}))


(fact "we can negate an angle"
  (- (turn 100)) => (turn -100)
  (- (grad -100)) => (grad 100))

(fact "we can substract two angles"
  (- (deg 1) (deg 3)) => (deg -2)
  (- (grad 100) (grad 10)) => (grad 90))


(fact "we can multiply an angle"
  (* (deg 1) 10) => (deg 10)
  (* 10 (deg 100)) => (deg 1000))

(fact "we can divide a length"
  (/ (grad 100) 10) => (grad 10))

(fact "Angles simplify themself"
  (deg 360)              => (deg 0)
  (grad 400)             => (grad 0)
  (rad (* 2 (Math/PI)))  => (rad 0)
  (turn 1)               => (turn 0)

  (deg (+ 360 20))              => (deg 20)
  (grad (+ 400 20))             => (grad 20)
  (rad (+ (* 2 (Math/PI)) 20))  => (contains {:mag (roughly (:mag (rad 20)))})
  (turn (+ 1 20))               => (turn 20)

  (deg (* 360 20))              => (deg 0)
  (grad (* 400 20))             => (grad 0)
  (rad (* (* 2 (Math/PI)) 20))  => (some-checker (contains {:mag (roughly (:mag (rad 0)))})
                                                 (contains {:mag (roughly (:mag (rad (* 2 3.14))))}))
  (turn (* 1 20))               => (turn 0))


(fact "We can convert angles from one unit to another"
  (-> 180 deg turn rad turn grad deg :mag)
  => (roughly 180.0))

(fact "We can add and substract angle of different units"
  (:mag (+ (deg 180) (turn 0.25))) => (roughly 0.75)
  (:mag (- (deg 180) (turn 0.25))) => (roughly 0.25))


(fact "We can compare angles"
  (pos?  (deg 10)) => truthy
  (zero? (deg 10)) => falsey

  (zero? (deg 0)) => truthy


  (= (deg 90) (deg 90)) => truthy
  (= (deg 90.0) (grad 100.0)) => truthy

  (not= (deg 10.0) (grad 10.0)) => truthy
  (not= (deg 90.0) (grad 100.0)) => falsey

  (< (deg 10.0) (deg 10.0)) => falsey
  (< (deg 90.0) (grad 100.0)) => falsey

  (> (deg 10.0) (deg 10.0)) => falsey
  (> (deg 10.0) (grad 100.0)) => falsey

  (<= (deg 10.0) (deg 100.0)) => truthy
  (<= (deg 10.0) (grad 100.0)) => truthy

  (<= (deg 100.0) (grad 100.0)) => falsey
  (<= (deg 100) (grad 100.0)) => falsey

  (>= (deg 10.0) (deg 100.0)) => falsey
  (>= (deg 100.0) (grad 100.0)) => truthy

  (min (deg 90.0) (grad 101.0)) => (deg 90.0)
  (max (deg 90.0) (grad 101.0)) => (grad 101.0))




