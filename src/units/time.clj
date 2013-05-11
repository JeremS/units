;; ## Time
;; We define here units of  times usefull in css transtions.

(ns  ^{:author "Jeremy Schoffen."}
  units.time
  (:use [units.macro-utils :only (build-generic)]
        [converso.core :only (add-conversion remove-all-conversions convert)]))


;; ### Definition template
;; Template used to define each duration types.

(defmacro ^:private defduration
  [d-name d-cstr d-str]
  (let [class-sym (symbol (str d-name "."))]
    `(do
       ~(build-generic d-name d-cstr d-str)

       (defn ~d-cstr [mag#]
         (if (isa? (type mag#) Number)
           (~class-sym mag#)
           (convert mag# ~d-name)))

       )))


;; ### Types definitions

(defduration Second  s  "s")
(defduration MilliSecond  ms  "ms")


;; ### Conversions

(defn s->ms [t] (-> t :mag (* 1000) ms))
(defn ms->s [t] (-> t :mag (/ 1000) s))

(remove-all-conversions Second MilliSecond)


;; Set up of converso.
(add-conversion Second MilliSecond s->ms ms->s)



