(ns units.colors
  (:use [units.length :only (%)]
        [units.angle  :only (deg)]
        [units.utils  :only (between)]))

(defn- rgb-valid? [n]
  (between n 0 255))

(defn- alpha-valid? [a]
  (between a 0 1))

(defn- sl-valid? [v]
  (between v 0 100))


(defrecord RGBa [r g b a]
  Object
  (toString [_] (str "rgba(" r "," g"," b "," a")")))

(defn rgba
  ([r g b]
   (rgba r g b 1.0))
  ([r g b a]
   {:pre [(every? number? (list r g b a))
          (every? rgb-valid? (list r g b))
          (alpha-valid? a)]}
   (RGBa. r g b a)))


(defrecord HSLa [h s l a]
  Object
  (toString [_] (str "hsla(" h "," s"," l "," a")")))

(defn hsla 
  ([h s l]
   (hsla h s l 1.0))
  ([h s l a]
   {:pre [(every? number? (list h s l a))
          (every? sl-valid? (list s l))
          (alpha-valid? a)]}
   (HSLa. (deg h) (% s) (% l) a)))



;; conversions from https://github.com/jolby/colors/blob/master/src/com/evocomputing/colors.clj

(defn- hue->rgb [m1 m2 h]
  (let [h (cond (< h 0) (inc h)
                (> h 1) (dec h)
                :else h)]
    (cond (< (* h 6) 1) (+ m1 (* (- m2 m1) h 6))
          (< (* h 2) 1) m2
          (< (* h 3) 2) (+ m1 (* (- m2 m1) (- (/ 2.0 3) h) 6))
          :else m1)))

(defn hsla->rgba [color]
  (let [h (-> color :h :mag (/ 360.0))
        s (-> color :s :mag (/ 100.0))
        l (-> color :l :mag (/ 100.0))
        a (-> color :a)
        
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


;; try to use core.logic for convertion library
