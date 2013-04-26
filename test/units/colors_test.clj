(ns units.colors_test
  (:use units.colors
        midje.sweet))

(facts "We can convert colors"
  ; tests from https://github.com/jolby/colors/blob/master/test/com/evocomputing/test/colors.clj
  (fact "from hsl to rgb"
    (hsla->rgba (hsla 360.0 100.0 50.0)) => (rgba 255   0   0)
    (hsla->rgba (hsla   0.0 100.0 50.0)) => (rgba 255   0   0)
    (hsla->rgba (hsla 120.0 100.0 50.0)) => (rgba   0 255   0)
    (hsla->rgba (hsla 240.0 100.0 50.0)) => (rgba   0   0 255))
  
  (fact "from rgb to hsl"
    (rgba->hsla (rgba 255   0   0)) = (hsla 0.0   100.0 50.0)
    (rgba->hsla (rgba   0 255   0)) = (hsla 120.0 100.0 50.0)
    (rgba->hsla (rgba   0   0 255)) = (hsla 240.0 100.0 50.0))

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
    =>  (rgba 10   10  10)))