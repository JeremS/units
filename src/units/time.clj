;; ## Time
;; We define here units of  times usefull in css transtions.

(ns  ^{:author "Jeremy Schoffen."}
  units.time
  (:use [units.macro-utils :only (def-simple-unit)]
        [converso.core :only (add-conversion remove-all-conversions)]))


;; ### Types definitions

(def-simple-unit Second  s  "s")
(def-simple-unit MilliSecond  ms  "ms")


;; ### Conversions

(defn s->ms [t] (-> t :mag (* 1000) ms))
(defn ms->s [t] (-> t :mag (/ 1000) s))

(remove-all-conversions Second MilliSecond)


;; Set up of converso.

(add-conversion Second MilliSecond s->ms ms->s)



