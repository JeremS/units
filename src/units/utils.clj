(ns units.utils)

(defn between? [value lower upper]
  (and (<= lower value)
       (<= value upper)))

(defn keep-inside [value lower upper]
  (cond
   (< value lower) lower
   (> value upper) upper
   :else           value))


(defn circular-val [mag neutral]
  (-> mag
      (mod neutral)
      (+ neutral)
      (mod neutral)))

