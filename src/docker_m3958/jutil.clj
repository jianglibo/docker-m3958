(ns docker-m3958.jutil
  (:require [clojure.java.io :as io])
  (:require [clojure.data.json :as json])
  (:require [clojure.string :as str])
  (:require [clojure.java.shell :as shell])
  (:import java.nio.file.Paths)
  (:import [java.util UUID])
  (:gen-class))

(defn uuid []
  (str/replace (UUID/randomUUID) #"-" ""))

(count (uuid))

(defn is-windows
  []
  (re-find #"(?i)windows" (System/getProperty "os.name")))

(defn user-home
  []
  (System/getProperty "user.home"))



(defn filename
  [& pp]
  (if-not pp
    nil
    (->
     (Paths/get (first pp) (into-array String (rest pp)))
     (.normalize)
     (.toAbsolutePath)
     (.toString))))

(defn filename-in-user-home
  [& pp]
  (if pp
    (apply filename (user-home) pp)))

(defn random-name [size]
  (str/join (take size (repeatedly #(rand-nth "0123456789abcdefghijklmnopqrstuvwxyz")))))
