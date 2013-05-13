;; ## Length
;; Definition of the length unit types.

(ns ^{:author "Jeremy Schoffen."}
  units.length
  (:refer-clojure :exclude (rem))
  (:use [units.macro-utils :only (def-simple-unit)]
        [converso.core :only (add-conversion remove-all-conversions)]))


;; ### Types definitions

(def-simple-unit Em  em  "em")
(def-simple-unit Rem rem "rem")
(def-simple-unit Ex  ex  "ex")
(def-simple-unit Ch  ch  "ch")

(def-simple-unit Vw   vw   "vw")
(def-simple-unit Vh   vh   "vh")
(def-simple-unit VMin vmin "vmin")
(def-simple-unit VMax vmax "vmax")

(def-simple-unit Percentage % "%")

(def-simple-unit Pixel      px "px")
(def-simple-unit Millimeter mm "mm")
(def-simple-unit Centimeter cm "cm")
(def-simple-unit Inch       in "in")
(def-simple-unit Point      pt "pt")
(def-simple-unit Pica       pc "pc")


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