(ns units.length
  (:refer-clojure :exclude [rem])
  (:use [units.utils :only (arithmetic-expansion)]))

(defmacro deflength [u-name u-cstr u-str]
  (let [mag-sym (symbol "mag")
        mag-key (keyword "mag")
        class-sym (symbol (str u-name "."))]
    `(do
    
       (defrecord ~u-name [~mag-sym]
         Object
         (toString [_#] (str ~mag-sym ~u-str)))
       
       (defn ~u-cstr [mag#]
         {:pre [(number? mag#)]}
         (~class-sym mag#))
       
       ~@(arithmetic-expansion u-name u-cstr mag-key)
       
       )))


(deflength Em  em  "em")
(deflength Rem rem "rem")
(deflength Ex  ex  "ex")
(deflength Ch  ch  "ch")

(deflength Vw   vw   "vw")
(deflength Vh   vh   "vh")
(deflength VMin vmin "vmin")
(deflength VMax vmax "vmax")

(deflength Percentage % "%")

(deflength Pixel      px "px")
(deflength Millimeter mm "mm")
(deflength Centimeter cm "cm")
(deflength Inch       in "in")
(deflength Point      pt "pt")
(deflength Pica       pc "pc")
