(ns docker-m3958.juuid
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
