(ns units.angle
  (:use [units.utils :only (build-record build-type-test build-arithmetic)]))


(defn simplify-angle [mag neutral]
  (-> mag 
      (mod neutral) 
      (+ neutral)
      (mod neutral)))

(defmacro defangle [a-name a-cstr a-str a-neutral]
  (let [class-sym (symbol (str a-name "."))]
    `(do 
       
       ~(build-record a-name 'mag a-str)
       
       ~(build-type-test a-name a-cstr)
       
       (defn ~a-cstr [mag#]
         {:pre [(number? mag#)]}
         (~class-sym (simplify-angle mag# ~a-neutral)))
       
       ~@(build-arithmetic a-name a-cstr :mag)
       
       )))

(defangle Degree   deg  "deg" 360)
(defangle Gradiant grad "grad" 400)
(defangle Radiant  rad  "rad" (* 2 Math/PI))
(defangle Turn     turn "turn" 1)
