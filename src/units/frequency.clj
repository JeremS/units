;; ## Time
;; We define here units of frequency.

(ns  ^{:author "Jeremy Schoffen."}
  units.frequency
  (:use [units.macro-utils :only (def-simple-unit)]
        [converso.core :only (add-conversion remove-all-conversions)]))


;; ### Types definitions

(def-simple-unit Hertz       hz   "Hz")
(def-simple-unit KiloHertz  khz  "kHz")


;; ### Conversions

(defn hz->khz [t] (-> t :mag (/ 1000) khz))
(defn khz->hz [t] (-> t :mag (* 1000) hz))

(remove-all-conversions Hertz KiloHertz)


;; Set up of converso.
(add-conversion Hertz KiloHertz hz->khz khz->hz)


