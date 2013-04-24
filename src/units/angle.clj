(ns units.angle
  (:use [units.utils :only (record-expansion arithmetic-expansion)]))

(defn simplify-angle [mag neutral]
  (let [sign (if (pos? mag) + -)
        abs-mag (Math/abs mag)]
    (loop [current abs-mag]
      (let [new (- current neutral)]
        (cond 
         (neg? new) (sign current)
         (zero? new) 0
          :else      (recur new))))))

(defmacro defangle [a-name a-cstr a-str a-neutral]
  (let [mag-sym (symbol "mag")
        mag-key (keyword "mag")
        class-sym (symbol (str a-name "."))]
    `(do 
       
       ~(record-expansion a-name mag-sym a-str)
       
       (defn ~a-cstr [mag#]
         {:pre [(number? mag#)]}
         (~class-sym (simplify-angle mag# ~a-neutral)))
       
       ~@(arithmetic-expansion a-name a-cstr mag-key)
       
       )))

(defangle Degree   deg  "deg" 360)
(defangle Gradiant grad "grad" 400)
(defangle Radiant  rad  "rad" (* 2 Math/PI))
(defangle Turn     turn "turn" 1)
