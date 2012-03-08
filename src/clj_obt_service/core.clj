(ns clj-obt-service.core
  (:use [ring.adapter.jetty]
        [ring.util.response]
        [ring.middleware params]
        [net.cgrand.moustache])
  (:require [clj-obt.core :as obt]
            [cheshire.core :as json])
  (:gen-class :main true))

(defn page [req]
  (response "Tag text and specify format with /text/?data= or /json/?data="))

(defn probably-vector? [data]
  (if (and (= (first data) \[) (= (last data) \]))
    true false))

(defn tag [req]
  (let [data (get-in req [:query-params "data"])
        v? (probably-vector? data)
        params (if v? (read-string data) data)
        tagged (obt/obt-tag params)]
    (if v? (str \[ (apply str tagged) \]) (str tagged))))

(defn tag-json [req]
  (-> (tag req)
      json/encode
      response
      (content-type "application/json")))

(defn tag-text [req]
  (-> (tag req)
      response))

(def routes
  (app
   ["" &] (wrap-params page)
   ["text" &] (wrap-params tag-text)
   ["json" &] (wrap-params tag-json)))

(defn -main [& args]
  (do (obt/set-obt-path! (second args))
      (run-jetty #'routes {:port (Integer/parseInt (first args)) :join? false})))
