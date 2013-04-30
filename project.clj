(defproject units "0.2.0"
  :description "Library allowing to manipulate different kinds of units."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/algo.generic "0.1.1"]
                 [converso "0.1.0"]
                 [potemkin "0.2.2"]]

  :profiles {:dev
             {:dependencies [[org.clojure/tools.trace "0.7.5"]
                             [midje "1.5.1"]]}})