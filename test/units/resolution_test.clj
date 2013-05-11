(ns  ^{:author "Jeremy Schoffen."}
  units.resolution-test
  (:refer-clojure :exclude [rem + - * / = not= < > <= >= zero? pos? neg? min max])
  (:use units.resolution
        midje.sweet
        clojure.algo.generic.arithmetic
        clojure.algo.generic.comparison))

(fact "Resolutions have a string representation"
  (str (dpi 10))  => "10dpi"
  (str (dppx 10))  => "10dppx"
  (str (dpcm 10)) => "10dpcm")

(fact "We can test resolution types"
  (dpi? (dpi 10)) => truthy
  (dppx? (dpi 10)) => falsey)


(fact "we can add two resolutions"
  (+ (dppx 1) (dppx 3)) => (dppx 4)
  (+ (dpi 100) (dpi 10)) => (dpi 110))

(fact "we can negate a resolution"
  (- (dpi 100)) => (dpi -100)
  (- (dpi -100)) => (dpi 100))

(fact "we can substract two resolutions"
  (- (dppx 1) (dppx 3)) => (dppx -2)
  (- (dpi 100) (dpi 10)) => (dpi 90))


(fact "we can multiply a resolution"
  (* (dpi 100) 10) => (dpi 1000)
  (* 10 (dpi 100)) => (dpi 1000))

(fact "we can divide a resolution"
  (/ (dpi 100) 10) => (dpi 10))

(fact "We can convert a resolution into one another"
  (-> 100 dpi dpcm :mag) => (roughly (* 100 2.54))
  (-> 100 dpcm dpi :mag) => (roughly (/ 100 2.54)))

(fact "We can add or substract resolutions"
  (+ (dpcm 2.54) (dpi 1)) => (dpi 2.0)
  (- (dpcm 2.54) (dpi 1)) => (dpi 0.0))

(fact "We can compare resolutions"
  (neg?  (dpcm 10)) => falsey
  (pos?  (dpcm 10)) => truthy
  (zero? (dpcm 10)) => falsey

  (neg?  (dpcm -10)) => truthy
  (pos?  (dpcm -10)) => falsey
  (zero? (dpcm -10)) => falsey

  (zero? (dpcm 0)) => truthy


  (= (dpcm 10) (dpcm 10)) => truthy
  (= (dpcm 2.54) (dpi 1.0)) => truthy

  (not= (dpcm 10) (dpi 10)) => truthy
  (not= (dpcm 2.54) (dpi 1.0)) => falsey

  (< (dpcm 10) (dpcm 10)) => falsey
  (< (dpi 10) (dpcm 100)) => truthy

  (> (dpcm 10) (dpcm 10)) => falsey
  (> (dpcm 10) (dpi 100)) => falsey

  (<= (dpcm 2.54) (dpi 100)) => truthy
  (<= (dpcm 100) (dpi 10)) => falsey

  (>= (dpcm 2.54) (dpi 1)) => truthy
  (>= (dpcm 10) (dpi 1)) => truthy

  (min (dpcm 10) (dpi 10)) => (dpcm 10)
  (max (dpcm 10) (dpi 10)) => (dpi 10))

