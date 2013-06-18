;; ## Time
;; We define here units of times usefull in css transtions.

(ns  ^{:author "Jeremy Schoffen."}
  units.time
  (:use [units.macro-utils :only (def-simple-unit)]
        [converso.core :only (defconversion)]))


;; ### Types definitions

(def-simple-unit Second  s  "s")
(def-simple-unit MilliSecond  ms  "ms")


;; ### Conversions

(defn s->ms [t] (-> t :mag (* 1000) ms))
(defn ms->s [t] (-> t :mag (/ 1000) s))


;; Set up of converso.

(defconversion Second MilliSecond s->ms ms->s)



