;; ## Utilities

(ns  ^{:author "Jeremy Schoffen."}
  units.utils)

(defn between? [value lower upper]
  (and (<= lower value)
       (<= value upper)))

(defn keep-inside [value lower upper]
  (cond
   (< value lower) lower
   (> value upper) upper
   :else           value))

(defn circular-val
  "Keeps a value between `0` and `neutral`,
  `neutral being positive`."
  [mag neutral]
  {:pre [(pos? neutral)]}
  (-> mag
      (mod neutral)
      (+ neutral)
      (mod neutral)))

