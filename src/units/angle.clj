(ns units.angle
  (:use [units.utils :only (circular-val)]
        [converso.core :only (add-conversion remove-all-conversions convert)]
        [units.macro-utils :only (build-record build-type-test build-arithmetic)]))


(defmacro defangle [a-name a-cstr a-str a-neutral]
  (let [class-sym (symbol (str a-name "."))]
    `(do

       ~(build-record a-name 'mag a-str)

       ~(build-type-test a-name a-cstr)

       (defn ~a-cstr [mag#]
         (if (isa? (type mag#) Number)
           (~class-sym (circular-val mag# ~a-neutral))
           (convert mag# ~a-name)))

       ~@(build-arithmetic a-name a-cstr :mag)

       )))


(defangle Degree   deg  "deg" 360)
(defangle Gradiant grad "grad" 400)
(defangle Radiant  rad  "rad" (* 2 Math/PI))
(defangle Turn     turn "turn" 1)

(defn deg->turn [x] (-> x :mag (/ 360.0) turn))
(defn turn->deg [x] (-> x :mag (* 360.0) turn))

(defn grad->turn [x] (-> x :mag (/ 400.0) turn))
(defn turn->grad [x] (-> x :mag (* 400.0) grad))

(defn rad->turn [x] (-> x :mag (/ (* 2 Math/PI)) turn))
(defn turn->rad [x] (-> x :mag (* 2 Math/PI)  rad))



(remove-all-conversions Degree   Turn)
(remove-all-conversions Gradiant Turn)
(remove-all-conversions Radiant  Turn)


(add-conversion Degree   Turn deg->turn  turn->deg)
(add-conversion Gradiant Turn grad->turn turn->grad)
(add-conversion Radiant  Turn rad->turn  turn->rad)
