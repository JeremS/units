;; ## Length
;; Definition of the length unit types.

(ns ^{:author "Jeremy Schoffen."}
  units.length
  (:refer-clojure :exclude (rem))
  (:use [units.macro-utils :only (build-generic)]
        [converso.core :only (add-conversion remove-all-conversions convert)]))


;; ### Definition template
;; Template used to define each length types.

(defmacro ^:private deflength
  [u-name u-cstr u-str]
  (let [class-sym (symbol (str u-name "."))]
    `(do
       ~(build-generic u-name u-cstr u-str)

       (defn ~u-cstr [mag#]
         (if (isa? (type mag#) Number)
           (~class-sym mag#)
           (convert mag# ~u-name)))

       )))


;; ### Types definitions

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


;; ### Conversions
;; No need to define each and every conversion,
;; converso take care of us.

(defn- cm->mm [x] (-> x :mag (* 10) mm))
(defn- mm->cm [x] (-> x :mag (/ 10) cm))

(defn- in->cm [x] (-> x :mag (* 2.54) cm))
(defn- cm->in [x] (-> x :mag (/ 2.54) in))

(defn- in->px [x] (-> x :mag (* 96.0) px))
(defn- px->in [x] (-> x :mag (/ 96.0) in))

(defn- in->pt [x] (-> x :mag (* 72.0) pt))
(defn- pt->in [x] (-> x :mag (/ 72.0) in))

(defn- pc->pt [x] (-> x :mag (* 12.0) pt))
(defn- pt->pc [x] (-> x :mag (/ 12.0) pc))

;; Cleanning converso (usefful when developping at the repl.)
(remove-all-conversions Centimeter Millimeter)
(remove-all-conversions Inch       Centimeter)
(remove-all-conversions Inch       Pixel)
(remove-all-conversions Inch       Point)
(remove-all-conversions Pica       Point)

;; Set up of converso.
(add-conversion Centimeter Millimeter cm->mm mm->cm)
(add-conversion Inch       Centimeter in->cm cm->in)
(add-conversion Inch       Pixel      in->px px->in)
(add-conversion Inch       Point      in->pt pt->in)
(add-conversion Pica       Point      pc->pt pt->pc)