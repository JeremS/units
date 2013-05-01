(ns units.arithmetic
  (:refer-clojure :exclude [+ * - /])
  (:use clojure.algo.generic.arithmetic
        [clojure.algo.generic :only (root-type)]
        [converso.core :only (convert)]

        clojure.tools.trace))

(defmethod + [root-type root-type]
  [u1 u2]
  (try
    (let [u1 (convert u1 (type u2))]
      (+ u1 u2))
    (catch Exception e
      (throw (ex-info "Addition non implemented for these types" {:param1 u1 :param2 u2})))))

(defmethod - [root-type root-type]
  [u1 u2]
  (try
    (let [u1 (convert u1 (type u2))]
      (- u1 u2))
    (catch Exception e
      (throw (ex-info "Addition non implemented for these types" {:param1 u1 :param2 u2})))))

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