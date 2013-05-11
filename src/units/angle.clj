;; ## Angles
;; Here we define the necessary inmplementations
;; to be able to use angle as value that we can
;; add, multiply and compare.

(ns ^{:author "Jeremy Schoffen."}
  units.angle
  (:use [units.utils :only (circular-val)]
        [units.macro-utils :only (build-generic)]
        [converso.core :only (add-conversion remove-all-conversions convert)]))


;; ### Template definition
;; Here we define a template used to construct each angle type.


(defmacro ^:private defangle
  "Constructs the necessary parts needed to represent
  angle units."
  [a-name a-cstr a-str a-neutral]
  (let [class-sym (symbol (str a-name "."))]
    `(do
       ~(build-generic a-name a-cstr a-str)

       (defn ~a-cstr [mag#]
         (if (isa? (type mag#) Number)
           (~class-sym (circular-val mag# ~a-neutral))
           (convert mag# ~a-name)))

       )))

;; ### Definition of the different angle types
(defangle Degree   deg  "deg" 360)
(defangle Gradiant grad "grad" 400)
(defangle Radiant  rad  "rad" (* 2 Math/PI))
(defangle Turn     turn "turn" 1)

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


