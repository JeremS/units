(ns  ^{:author "Jeremy Schoffen."}
  units.colors_test
  (:use units.colors
        midje.sweet))

(facts "We can convert colors"
  ; >> tests from https://github.com/jolby/colors/blob/master/test/com/evocomputing/test/colors.clj
  (fact "from hsl to rgb"
    (hsla->rgba (hsla 360.0 100.0 50.0)) => (rgba 255   0   0)
    (hsla->rgba (hsla   0.0 100.0 50.0)) => (rgba 255   0   0)
    (hsla->rgba (hsla 120.0 100.0 50.0)) => (rgba   0 255   0)
    (hsla->rgba (hsla 240.0 100.0 50.0)) => (rgba   0   0 255))

  (fact "from rgb to hsl"
    (rgba->hsla (rgba 255   0   0)) = (hsla 0.0   100.0 50.0)
    (rgba->hsla (rgba   0 255   0)) = (hsla 120.0 100.0 50.0)
    (rgba->hsla (rgba   0   0 255)) = (hsla 240.0 100.0 50.0))

  ; <<

  (fact "Conversions are inverse functions"
    (-> (rgba 255 255 255) rgba->hsla hsla->rgba)
    =>  (rgba 255 255 255)

    (-> (rgba 255 255   0) rgba->hsla hsla->rgba)
    =>  (rgba 255 255   0)

    (-> (rgba 255   0   0) rgba->hsla hsla->rgba)
    =>  (rgba 255   0   0)

    (-> (rgba 0   255 255) rgba->hsla hsla->rgba)
    =>  (rgba 0   255 255)

    (-> (rgba 0     0 255) rgba->hsla hsla->rgba)
    =>  (rgba 0     0 255)

    (-> (rgba 255   0 255) rgba->hsla hsla->rgba)
    =>  (rgba 255   0 255)

    (-> (rgba 0     0   0) rgba->hsla hsla->rgba)
    =>  (rgba 0     0   0)


    (-> (rgba 10   10  10) rgba->hsla hsla->rgba)
    =>  (rgba 10   10  10))

  (fact "Color constructors automaticaly convert wahen called with one argument"
    (rgba (hsla 360.0 100.0 50.0)) => (rgba 255   0   0)
    (rgba (hsla   0.0 100.0 50.0)) => (rgba 255   0   0)
    (rgba (hsla 120.0 100.0 50.0)) => (rgba   0 255   0)
    (rgba (hsla 240.0 100.0 50.0)) => (rgba   0   0 255)

    (hsla (rgba 255   0   0)) = (hsla 0.0   100.0 50.0)
    (hsla (rgba   0 255   0)) = (hsla 120.0 100.0 50.0)
    (hsla (rgba   0   0 255)) = (hsla 240.0 100.0 50.0)))

(facts "We have helpers to work with colors"

  (fact "we can see colors components"
    (red (rgba 255 255 255)) => 255
    (red (hsla (rgba 255 255 255))) => 255

    (green (rgba 255 255 255)) => 255
    (green (hsla (rgba 255 255 255))) => 255

    (blue (rgba 255 255 255)) => 255
    (blue (hsla (rgba 255 255 255))) => 255


    (hue (hsla 90 50 50)) => 90
    (hue (rgba (hsla 90 50 50))) => (roughly 90 1/2)

    (saturation (hsla 90 50 50)) => 50
    (saturation (rgba (hsla 90 50 50))) => (roughly 50 1/2)

    (lightness (hsla 90 50 50)) => 50
    (lightness (rgba (hsla 90 50 50))) => (roughly 50 1/2)


    (alpha (rgba 1 1 1 0.2)) => 0.2
    (alpha (hsla 1 1 1 0.2)) => 0.2)


  (fact "We can play with color components"

    (-> (hsla 180 50 50) (adjust-hue  60) hue)
    => (+ 180 60)

    (-> (hsla 180 50 50) rgba (adjust-hue  60) hue)
    => (+ 180 60.0)


    (-> (hsla 180 50 50) (lighten  10) lightness)
    => (+ 50 10)

    (-> (hsla 180 50 50) rgba (lighten 10) lightness)
    => (+ 50 10.0)


    (-> (hsla 180 50 50) (saturate  10) saturation)
    => (+ 50 10)

    (-> (hsla 180 50 50) rgba (saturate 10) saturation)
    => (roughly (+ 50 10.0) 1/2)


    (-> (rgba 100 100 100) (redify  10) red)
    => (+ 100 10)

    (-> (rgba 100 100 100) hsla (redify 10) red)
    => (roughly (+ 100 10.0) 1/2)


    (-> (rgba 100 100 100) (redify  10) red)
    => (+ 100 10)

    (-> (rgba 100 100 100) hsla (redify 10) red)
    => (roughly (+ 100 10.0) 1/2)


    (-> (rgba 100 100 100) (greenify  10) green)
    => (+ 100 10)

    (-> (rgba 100 100 100) hsla (greenify 10) green)
    => (roughly (+ 100 10.0) 1/2)


    (-> (rgba 100 100 100) (blueify  10) blue)
    => (+ 100 10)

    (-> (rgba 100 100 100) hsla (blueify 10) blue)
    => (roughly (+ 100 10.0) 1/2)


    (-> (hsla 10 10 10 0.5) (opacify 0.2) alpha)
    => 0.7

    (-> (rgba 10 10 10 0.5) (opacify 0.2) alpha)
    => 0.7


    (-> (hsla 30 50 50) grayscale saturation)
    => (some-checker 0 0.0)

    (-> (hsla 10 10 10) c-complement hue)
    => (+ 180 10)

    (-> (rgba 30 30 30) inverse)
    => (rgba 225 225 225)))


