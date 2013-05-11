;; ## Time
;; We define here units of frequency.

(ns  ^{:author "Jeremy Schoffen."}
  units.frequency
  (:use [units.macro-utils :only (build-generic)]
        [converso.core :only (add-conversion remove-all-conversions convert)]))


;; ### Definition template
;; Template used to define each frequency types.

(defmacro ^:private deffrequency
  [f-name f-cstr f-str]
  (let [class-sym (symbol (str f-name "."))]
    `(do
       ~(build-generic f-name f-cstr f-str)

       (defn ~f-cstr [mag#]
         (if (isa? (type mag#) Number)
           (~class-sym mag#)
           (convert mag# ~f-name)))

       )))


;; ### Types definitions

(deffrequency Hertz       hz   "Hz")
(deffrequency KiloHertz  khz  "kHz")


;; ### Conversions

(defn hz->khz [t] (-> t :mag (/ 1000) khz))
(defn khz->hz [t] (-> t :mag (* 1000) hz))

(remove-all-conversions Hertz KiloHertz)


;; Set up of converso.
(add-conversion Hertz KiloHertz hz->khz khz->hz)


