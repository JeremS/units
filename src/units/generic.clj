;; ## Generic
;; Here we defined generic implementations
;; for arithmetic and comparison.

(ns ^{:author "Jeremy Schoffen."}
  units.generic
  (:refer-clojure :exclude [+ - * / = not= < > <= >= zero? pos? neg? min max])
  (:use clojure.algo.generic.arithmetic
        clojure.algo.generic.comparison
        [clojure.algo.generic :only (root-type)]
        [converso.core :only (convert)]

        clojure.repl))


;; ### symbols of the generic functions.

(def ^:private unary-comparison-fns
  '(zero? pos? neg?))


(def ^:private binary-comparison-fns
  '(= < > <= >=))

(def ^:private unary-arithmetic-fns
  '(- /))

(def ^:private binary-arithmetic-fns
  '(+ -))


;; ### Template of code for generic implementations.

(defn- defgeneric-binary
  "Construct the defmethod for binary comparisons between
  2 different types.

  To do so, the contructed method tries to convert the
  first arg into the type of the other."
  [g-fn]
  `(defmethod ~g-fn [root-type root-type]
     [~'v1 ~'v2]
     (try
       (let [~'v1 (convert ~'v1 (type ~'v2))]
      (~g-fn ~'v1 ~'v2))
    (catch Exception ~'e
      (throw (ex-info "Incompatible types" {:param1 ~'v1 :param2 ~'v2}))))))

(defmacro ^:private defgenerics-binary
  "Construct all the generic binary comparisons."
  []
  `(do

     ~@(for [g (concat binary-arithmetic-fns binary-comparison-fns)]
           (defgeneric-binary g))))

;; We generates the generic comparisons here
(defgenerics-binary)


(defn- build-unary-comparison
  "Construct the defmethod for an unary comparison of a given type."
  [a-name comp-fn]
  (let [g-fn (symbol "clojure.algo.generic.comparison" (str comp-fn))
        core-fn (symbol "clojure.core" (str comp-fn))]
    `(defmethod ~g-fn ~a-name
       [{~'m1 :mag}]
       (~core-fn ~'m1))))

(defn- build-binary-comparison
  "Construct the defmethod for binary comparison of 2 value
  of one given type."
  [a-name comp-fn]
  (let [g-fn (symbol "clojure.algo.generic.comparison" (str comp-fn))
        core-fn (symbol "clojure.core" (str comp-fn))]
    `(defmethod ~g-fn [~a-name ~a-name]
       [{~'m1 :mag} {~'m2 :mag}]
       (~core-fn ~'m1 ~'m2))))

(defn build-comparisons
  "Construct unary and binary comparison defmethods
  for a given type."
  [a-name]
  `(do

     ~@(for [c unary-comparison-fns]
         (build-unary-comparison a-name c))

     ~@(for [c binary-comparison-fns]
         (build-binary-comparison a-name c))))


(-> '(build-comparisons Pixel) macroexpand-1)


(defn build-arithmetic
  "Construct the arithmetic defmethods for a given
  type given its contructor fn."
  [a-name a-cstr]
  `(do
     (defmethod + [~a-name ~a-name]
       [{m1# :mag} {m2# :mag}]
       (~a-cstr (clojure.core/+ m1# m2#)))

     (defmethod - ~a-name
       [{m# :mag}]
       (~a-cstr (clojure.core/- m#)))

     (defmethod - [~a-name ~a-name]
       [{m1# :mag}{m2# :mag}]
       (~a-cstr (clojure.core/- m1# m2#)))

     (defmethod * [~a-name Number]
       [{m1# :mag} n#]
       (~a-cstr (clojure.core/* m1# n#)))

     (defmethod * [Number ~a-name]
       [n# {m1# :mag}]
       (~a-cstr (clojure.core/* n# m1#)))

     (defmethod / [~a-name]
       [{m# :mag}]
       (~a-cstr (clojure.core// m#)))

     (defmethod / [~a-name Number]
       [{m# :mag} n#]
       (~a-cstr (clojure.core// m# n#)))))

