;; ## Macro helpers
;; Generic template for macros definig unit types.
(ns ^{:author "Jeremy Schoffen."}
  units.macro-utils
  (:use [clojure.string :only (lower-case)]
        [units.generic :only (build-arithmetic build-comparisons)]))

(defn build-record [a-name a-str]
  "Return the code delaring a record with
  the name `a-name` and one key, :mag`"
  `(defrecord ~a-name [~'mag]
     Object
     (toString [~'_] (str ~'mag ~a-str))))

(defn build-type-test [a-type a-cstr]
  "Return the code declaring a function that
  tests if its parameter is of type `a-type`."
  (let [test-name (symbol (str (lower-case a-cstr) \?))]
  `(defn ~test-name [val#]
     (= (type val#) ~a-type))))

(defn build-generic [t-name t-cstr t-str]
  `(do

     (declare ~t-cstr)

     ~(build-record t-name t-str)
     ~(build-type-test t-name t-cstr)
     ~(build-arithmetic t-name t-cstr)
     ~(build-comparisons t-name)))