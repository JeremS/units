;; ## Utilities

(ns units.utils)

(defn between? [value lower upper]
  (and (<= lower value)
       (<= value upper)))

(defn keep-inside [value lower upper]
  (cond
   (< value lower) lower
   (> value upper) upper
   :else           value))

(defn circular-val
  "Keeps a value between `0` and `neutral`."
  [mag neutral]
  (-> mag
      (mod neutral)
      (+ neutral)
      (mod neutral)))

