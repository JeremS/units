;; ## Macro helpers

(ns ^{:author "Jeremy Schoffen."}
  units.macro-utils
  (:use [clojure.string :only (lower-case)]
        [units.generic :only (build-arithmetic build-comparisons)]
        [units.utils :only (circular-val)]
        [converso.core :only (convert)]))

;; Generic templates for macros definig unit types.

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




;; ### Definition templates

;; Template used to define simple types.

(defmacro def-simple-unit
  "Constructs the necessary parts to represent units."
  [u-name u-cstr u-str]
  (let [class-sym (symbol (str u-name "."))]
    `(do
       ~(build-generic u-name u-cstr u-str)

       (defn ~u-cstr [mag#]
         (if (isa? (type mag#) Number)
           (~class-sym mag#)
           (convert mag# ~u-name))))))


(defmacro def-circular-unit
  "Constructs the necessary parts needed to represent circular units."
  [u-name u-cstr u-str u-neutral]
  (let [class-sym (symbol (str u-name "."))]
    `(do
       ~(build-generic u-name u-cstr u-str)

       (defn ~u-cstr [mag#]
         (if (isa? (type mag#) Number)
           (~class-sym (circular-val mag# ~u-neutral))
           (convert mag# ~u-name))))))

