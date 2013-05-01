(ns units.colors
  (:use [units.utils  :only (circular-val keep-inside)]
        [converso.core]))


(defn- color [mag]
  (keep-inside mag 0 255))

(defn- deg [mag]
  (circular-val mag 360))

(defn- % [mag]
  (keep-inside mag 0 100))

(defn- opacity [mag]
  (keep-inside mag 0 1))

(defrecord RGBa [r g b a])

(defn rgba
  ([value]
   (convert value RGBa))
  ([r g b]
   (rgba r g b 1.0))
  ([r g b a]
   {:pre [(every? number? (list r g b a))]}

   (RGBa. (color r)
          (color g)
          (color b)
          (opacity a))))


(declare hsla)

(defrecord HSLa [h s l a])

(defn hsla
  ([value]
   (convert value HSLa))
  ([h s l]
   (hsla h s l 1.0))
  ([h s l a]
   {:pre [(every? number? (list h s l a))]}
   (HSLa. (deg h)
          (% s)
          (% l)
          (opacity a))))




;; conversions from https://github.com/jolby/colors/blob/master/src/com/evocomputing/colors.clj

(defn- hue->rgb [m1 m2 h]
  (let [h (cond (< h 0) (inc h)
                (> h 1) (dec h)
                :else h)]
    (cond (< (* h 6) 1) (+ m1 (* (- m2 m1) h 6))
          (< (* h 2) 1) m2
          (< (* h 3) 2) (+ m1 (* (- m2 m1) (- (/ 2.0 3) h) 6))
          :else m1)))

(defn hsla->rgba [{:keys [h s l a]}]
  (let [h (/ h 360.0)
        s (/ s 100.0)
        l (/ l 100.0)

        m2 (if (<= l 0.5)
             (* l (+ 1 s))
             (- (+ l s) (* l s)))
        m1 (- (* l 2) m2)

        r (hue->rgb m1 m2 (+ h (/ 1.0 3)))
        g (hue->rgb m1 m2 h)
        b (hue->rgb m1 m2 (- h (/ 1.0 3)))]

    (rgba (-> r (* 0xff) Math/round )
          (-> g (* 0xff) Math/round )
          (-> b (* 0xff) Math/round )
          a)))


(defn rgba->hsla [{:keys [r g b a]}]
  (let [r (/ r 255.0)
        g (/ g 255.0)
        b (/ b 255.0)

        M (max r g b)
        m (min r g b)
        d (- M m)

        h (condp = M
            m 0.0
            r    (* 60 (/ (- g b) d))
            g (+ (* 60 (/ (- b r) d)) 120)
            b (+ (* 60 (/ (- r g) d)) 240))

        l (/ (+ M m) 2.0)

        s (cond (= M m) 0
                (< l 0.5) (/ d (* 2 l))
                :else     (/ d (- 2 (* 2 l))))]

    (hsla h (* s 100.0) (* l 100.0) a)))


;; Conversions
(add-conversion RGBa HSLa rgba->hsla hsla->rgba)


;; Utilities

; algorithms from http://sass-lang.com/docs/yardoc/Sass/Script/Functions.html#mix-instance_method
(defn red [color]
  "Red component of a color."
  (-> color rgba :r))

(defn green [color]
  "Green component of a color."
  (-> color rgba :g))

(defn blue [color]
  "Blue component of a color."
  (-> color rgba :b))


(defn hue [color]
  "Hue of a color."
  (-> color hsla :h))

(defn saturation [color]
  "Saturation of a color."
  (-> color hsla :s))

(defn lightness [color]
  "Lightness of a color."
  (-> color hsla :l))

(defn alpha [color]
  "Get the alpha/opacity of a color."
  (:a color))



(defn adjust-hue [c mag]
  "Adds to the hue of a color."
  (let [{h :h :as c} (hsla c)]
    (assoc c :h (deg (+ h mag)))))


(defn lighten
  "Add light to a color"
  [c mag]
  (let [{l :l :as c} (hsla c)]
    (assoc c :l (% (+ l mag)))))

(defn darken
  "Remove light from a color"
  [c mag]
  (lighten c (- mag)))

(defn saturate [c mag]
  "Add saturation to a color"
  (let [{s :s :as c} (hsla c)]
    (assoc c :s (% (+ s mag)))))

(defn desaturate
  "Remove saturation from a color"
  [c mag]
  (saturate c (- mag)))

(defn redify [c mag]
  "Makes a color redder"
  (let [{r :r :as c} (rgba c)]
    (assoc c :r (color (+ r mag)))))

(defn deredify
  "Make a color redder"
  [c mag]
  (redify c (- mag)))

(defn greenify [c mag]
  "Makes a color greener"
  (let [{g :g :as c} (rgba c)]
    (assoc c :g (color (+ g mag)))))

(defn degreenify
  "Make a color less green."
  [c mag]
  (greenify c (- mag)))


(defn blueify [color mag]
  "Makes a color bluer."
  (let [{:keys [r g b a]} (rgba color)]
    (rgba r g (+ b mag) a)))

(defn deblueify
  "Makes a color less blue."
  [color mag]
  (blueify color (- mag)))

(defn opacify [{a :a :as color} mag]
  "Makes a color more opaque"
  (assoc color :a (keep-inside (+ a mag) 0 1)))

(defn transparentize [color mag]
  "Make a more transparent color"
  (opacify color (- mag)))

(defn grayscale [c]
  "Desaturate a color complitely."
  (desaturate c 100))

(defn c-complement [c]
  "Returns the complement of a color."
  (adjust-hue c 180))

(defn inverse [c]
  "Returns the inverse of a color."
  (let [{:keys [r g b a]} (rgba c)]
    (rgba (- 255 r)
          (- 255 g)
          (- 255 b)
          a)))

(defn mix [c1 c2 w]
  (let [{r1 :r g1 :g b1 :b a1 :a} (rgba c1)
        {r2 :r g2 :g b2 :b a2 :a} (rgba c2)

        p (/ w 100.0)
        w (- (* 2 p) 1)
        a (- a1 a2)

        i (if (= (* w a) -1)
             w
             (/ (+ w a) (+ 1 (* w a))))

        w1 (/ (+ i 1) 2)

        w2 (- 1 w1)

        alpha (+ (* a1 p) (* a2 (- 1 p)))

        res #(+ (* %1 w1) (* %2 w2))]
    (rgba (res r1 r2)
          (res g1 g2)
          (res b1 b2)
          alpha)))
