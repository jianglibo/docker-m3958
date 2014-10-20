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

(defn working-dir
  []
  (System/getProperty "user.home"))
