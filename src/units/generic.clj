(ns units.generic
  (:refer-clojure :exclude [+ - * / = >])
  (:use clojure.algo.generic.arithmetic
        [clojure.algo.generic.comparison
         :only (= >)]
        [clojure.algo.generic :only (root-type)]
        [converso.core :only (convert)]))


(defmacro defgeneric [g-fn]
  `(defmethod ~g-fn [root-type root-type]
     [~'v1 ~'v2]
     (try
       (let [~'v1 (convert ~'v1 (type ~'v2))]
      (~g-fn ~'v1 ~'v2))
    (catch Exception ~'e
      (throw (ex-info "Incompatible types" {:param1 ~'v1 :param2 ~'v2}))))))

(defmacro defgenerics [& gs]
  `(do ~@(for [g gs]
           (list 'defgeneric g))))

(defgenerics + -)

(defn build-arithmetic [a-name a-cstr mag-key]
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

    (defmethod / [~a-name]
      [{m# ~mag-key}]
      (~a-cstr (clojure.core// m#)))

    (defmethod / [~a-name Number]
      [{m# ~mag-key} n#]
      (~a-cstr (clojure.core// m# n#)))))