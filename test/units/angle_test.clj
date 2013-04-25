(ns units.angle-test
  (:refer-clojure :exclude [+ - * /])
  (:require [units.angle :refer :all])
  (:use midje.sweet
        clojure.algo.generic.arithmetic))

(fact "units have a string representation"
  (str (deg  10)) => "10deg"
  (str (grad 10)) => "10grad")


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


  
