(ns units.utils
  (:refer-clojure :exclude [+ - * /])
  (:use clojure.algo.generic.arithmetic))

(defn arithmetic-expansion [a-name a-cstr mag-key]
  `((defmethod + [~a-name ~a-name]
         [{m1# ~mag-key}{m2# ~mag-key}]
         (~a-cstr (clojure.core/+ m1# m2#)))
       
       (defmethod - ~a-name
         [{m# ~mag-key}]
         (~a-cstr (clojure.core/- m#)))
       
       (defmethod - [~a-name ~a-name]
         [{m1# ~mag-key}{m2# ~mag-key}]
         (~a-cstr (clojure.core/- m1# m2#)))
       
       (defmethod * [~a-name Number]
         [{m1# ~mag-key} n#]
         (~a-cstr (clojure.core/* m1# n#)))
       
       (defmethod * [Number ~a-name]
         [n# {m1# ~mag-key}]
         (~a-cstr (clojure.core/* n# m1#)))
       
       (defmethod / [~a-name Number]
         [{m# ~mag-key} n#]
         (~a-cstr (clojure.core// m# n#)))
    ))