(ns clj-obt-service.core
  (:use [ring.adapter.jetty]
        [ring.util.response]
        [ring.middleware params]
        [net.cgrand.moustache])
  (:require [clj-obt.core :as obt]
            [cheshire.core :as json])
  (:gen-class :main true))

(defn probably-vector? [data]
  (if (and (= (first data) \[) (= (last data) \]))
    true false))

(defn tag [req]
  (let [data ((-> req :form-params) "data")
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
   ["" &] (response "Tag text by POST the parameter data to /text or /json")
   ["text" &] {:post (wrap-params tag-text)}
   ["json" &] {:post (wrap-params tag-json)}))

(defn -main [& args]
  (do (obt/set-obt-path! (second args))
      (run-jetty #'routes {:port (Integer/parseInt (first args)) :join? false})))
