# units

Library allowing to manipulate different kinds of web units.
 - length units
 - angle units
 - rgba & hsla colors

(I extracted this library from [cljss](https://github.com/JeremS/cljss)
thus the lack of more general units.)

## Installation
In `project.clj`:
```clojure
[jeremys/units "0.2.2"]
```

## Usage
### Lengths & angles
Roughly this library let's you write code like with lengths
and angles:

```clojure
(ns my.ns
  (:require [clojure.algo.generic.arithmetic :as agen]
            [clojure.algo.generic.comparison :as cgen])
  (:use units.lenght))

(def l1 (agen/+ (px 10) (mm 25)))
(def l2 (agen/- (agen/* 3 (in 1)) (cm 1)))

l1 ;=> (mm 27.645833333333332)
l2 ;=> (mm 6.62)

(px l1) ;=> (px 104.48818897637796)
(px l2) ;=> (px 250.2047244094488)

(cgen/< l1 l2) ;=> true

```

The same operations are available for angle `units.angle`
at the difference that angle mesures are automatically
bound in an interval. For instance:

```clojure
(use units.angle)

(deg 380) ;=> (deg 20)
```

## Changelog
### 0.2.2
 - ADDED: Support for using rgba hexa notations of colors.

### 0.2.1
 - ADDED: Support of pretty much every unit type specified in css3.
 - ADDED: functions red blue... can now generate a new color when used
 with 2 args.

### 0.2.0
 - ADDED: conversions are now supported
 - ADDED: generic comparison is now supported
 - ADDED: arithmetic between different unit types are now possible
 if the types are compatibles.
 - ADDED: what holds for arithmetic holds for comparisons too.


## Todo
 - Convert string -> color (rgba string -> color)
 - Rethink the arithmetic of the numbers contained in the different types
   - convert everything to floats ?
   - use ratios then to float at the last moment?
   - round at certain decimal ?
 - use defined types for the colors (deg, % for hsla) ?

## License

Copyright © 2013 Jérémy Schoffen.

Distributed under the Eclipse Public License, the same as Clojure.
