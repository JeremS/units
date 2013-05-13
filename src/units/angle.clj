;; ## Angles
;; Here we define the necessary implementations
;; to be able to use angle as value that we can
;; add, multiply and compare.

(ns ^{:author "Jeremy Schoffen."}
  units.angle
  (:use [units.utils :only (circular-val)]
        [units.macro-utils :only (def-circular-unit)]
        [converso.core :only (add-conversion remove-all-conversions)]))


;; ### Definition of the different angle types

(def-circular-unit Degree   deg  "deg" 360)
(def-circular-unit Gradiant grad "grad" 400)
(def-circular-unit Radiant  rad  "rad" (* 2 Math/PI))
(def-circular-unit Turn     turn "turn" 1)


;; ### Definition of conversion functions.
;; Here we don't need to define every conversion possible,
;; converso takes care of finding the missing ones.

(defn deg->turn [x] (-> x :mag (/ 360.0) turn))
(defn turn->deg [x] (-> x :mag (* 360.0) deg))

(defn grad->turn [x] (-> x :mag (/ 400.0) turn))
(defn turn->grad [x] (-> x :mag (* 400.0) grad))

(defn rad->turn [x] (-> x :mag (/ (* 2 Math/PI)) turn))
(defn turn->rad [x] (-> x :mag (* 2 Math/PI)  rad))


; clean up the conversions (used when interactively developping)
(remove-all-conversions Degree   Turn)
(remove-all-conversions Gradiant Turn)
(remove-all-conversions Radiant  Turn)

; set up of converso.
(add-conversion Degree   Turn deg->turn  turn->deg)
(add-conversion Gradiant Turn grad->turn turn->grad)
(add-conversion Radiant  Turn rad->turn  turn->rad)


