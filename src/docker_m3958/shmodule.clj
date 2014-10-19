(ns docker-m3958.core
  (:require [clojure.java.io :as io])
  (:require [clojure.data.json :as json])
  (:require [clojure.string :as str])
  (:require [clojure.java.shell :as shell])
  (:import java.nio.file.Paths)
  (:import [java.io BufferedReader StringReader FileReader])
  (:gen-class))

(defn find-container [cname docker-cmd]
  (some #{cname}
    (map #(last (str/split (str/trim %) #"\s+"))
      (filter #(.startsWith %1 "d")
        (rest
          (line-seq
            (-> (StringReader. (:out (apply shell/sh (str/split docker-cmd #"\n")))) (BufferedReader.))))))))

(defn container-exists? [cname]
  (some? (find-container cname "docker ps -a")))

(defn container-running? [cname]
  (some? (find-container cname "docker ps")))

(defn container-init [docker-cmd]
  "docker run -v /xx --volumes-from cid --link yy:yy m3958/xxx:yyy")

(defn app-init []
  )

(defn app-create [tplid]
  "hello"
  )
(-> (FileReader. "juuid.clj") (BufferedReader.))

;(conj [1 2] 3)
;
;(conj ["docker"] "ps" "-a")
;
;(conj [1] [2 3])
;(cons "docker" ["ps" "-a"])

;CONTAINER ID,IMAGE, COMMAND, CREATED,STATUS,PORTS,NAMES

;(rest [1 2 3 4])
;(next [1 2 3 4])
;(nnext [1 2 3 4])
;(fnext [1 2 3 4])
;(nthnext [1 2 3 4] 3)
