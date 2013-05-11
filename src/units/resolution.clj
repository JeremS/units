;; ## Resolutions

(ns  ^{:author "Jeremy Schoffen."}
  units.resolution
  (:use [units.macro-utils :only (build-generic)]
        [converso.core :only (add-conversion remove-all-conversions convert)]))


;; ### Definition template
;; Template used to define each duration types.

(defmacro ^:private defresolution
  [r-name r-cstr r-str]
  (let [class-sym (symbol (str r-name "."))]
    `(do
       ~(build-generic r-name r-cstr r-str)

       (defn ~r-cstr [mag#]
         (if (isa? (type mag#) Number)
           (~class-sym mag#)
           (convert mag# ~r-name)))

       )))


;; ### Types definitions

(defresolution DotsPerInch dpi  "dpi")
(defresolution DotsPerCentimeter dpcm  "dpcm")
(defresolution DotsPerPixel dppx  "dppx")


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


