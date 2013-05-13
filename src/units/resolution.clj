;; ## Resolutions

(ns ^{:author "Jeremy Schoffen."}
  units.resolution
  (:use [units.macro-utils :only (def-simple-unit)]
        [converso.core :only (add-conversion remove-all-conversions)]))

;; ### Types definitions

(def-simple-unit DotsPerInch dpi  "dpi")
(def-simple-unit DotsPerCentimeter dpcm  "dpcm")
(def-simple-unit DotsPerPixel dppx  "dppx")


;; ### Conversions

(defn dpi->dpcm [r] (-> r :mag (* 2.54) dpcm))
(defn dpcm->dpi [r] (-> r :mag (/ 2.54) dpi))

(defn dpi->dppx [r] (-> r :mag (* 96) dppx))
(defn dppx->dpi [r] (-> r :mag (/ 96.0) dpi))


(remove-all-conversions DotsPerInch DotsPerCentimeter)
(remove-all-conversions DotsPerInch DotsPerPixel)


;; Set up of converso.

(add-conversion DotsPerInch DotsPerCentimeter dpi->dpcm dpcm->dpi)
(add-conversion DotsPerInch DotsPerPixel dpi->dppx dppx->dpi)


