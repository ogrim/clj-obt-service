(ns clj-obt-service.core
  (:use [ring.adapter.jetty]
        [ring.util.response]
        [ring.middleware params]
        [net.cgrand.moustache])
  (:require [clj-obt.core :as obt]
            [cheshire.core :as json])
  (:gen-class :main true))

(defn page [req]
  (response "Tag plain text or json by using /text/?data= or /json/?data="))

(defn tag-json [req]
  (-> req
    (get-in [:query-params "data"])
    json/decode
    read-string
    obt/obt-tag
    json/encode
    response
    (ring.util.response/content-type "application/json")))

(defn tag-text [req]
  (-> req
      (get-in [:query-params "data"])
      obt/obt-tag
      str
      response))

(def routes
  (app
   ["" &] (wrap-params page)
   ["text" &] (wrap-params tag-text)
   ["json" &] (wrap-params tag-json)))

(defn -main [& args]
  (do (obt/set-obt-path! (second args))
      (run-jetty #'routes {:port (Integer/parseInt (first args)) :join? false})))
