(ns units.core
  (:refer-clojure :exclude [rem + - * /])
  (:require [potemkin :as p]
            [units length angle])
  (:use clojure.algo.generic.arithmetic))

(p/import-vars
 [units.length
  em  rem  ex  ch  vw  vh  vmin  vmax  %  px  mm  cm  in  pt  pc
  em? rem? ex? ch? vw? vh? vmin? vmax? %? px? mm? cm? in? pt? pc?]
 
 [units.angle 
  deg  grad  rad  turn
  deg? grad? rad? turn?])