(defproject clj-obt-service "0.0.2"
  :description "A simple server for exposing the functionality of clj-obt over HTTP"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [ring "1.0.2"]
                 [clj-obt "0.3.5"]
                 [net.cgrand/moustache "1.1.0"]
                 [cheshire "2.2.2"]]
  :main clj-obt-service.core)
