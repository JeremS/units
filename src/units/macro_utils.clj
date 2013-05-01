(ns units.macro-utils
  (:use [clojure.string :only (lower-case)]))

(defn build-record [a-name mag-sym a-str]
  `(defrecord ~a-name [~mag-sym]
     Object
     (toString [_#] (str ~mag-sym ~a-str))))

(defn build-type-test [a-type a-cstr]
  (let [test-name (symbol (str (lower-case a-cstr) \?))]
  `(defn ~test-name [val#]
     (= (type val#) ~a-type))))