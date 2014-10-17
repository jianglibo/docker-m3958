(ns docker-m3958.core
  (:require [clojure.java.io :as io])
  (:require [clojure.data.json :as json])
  (:require [clojure.string :as str])
  (:require [clojure.java.shell :as shell])
  (:require [clojure.java.jdbc :as j])
  (:require [docker-m3958.dbmodule :as dbmodule])
  (:import java.nio.file.Paths)
  (:import org.hsqldb.jdbc.JDBCDriver)
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (loop [iline nil]
    (if (or (= iline "exit") (= iline "quit"))
      (println "bye!")
      (do (println iline) (recur (read-line))))))

(ns-publics 'docker-m3958.dbmodule)
(dbmodule/init-tables)


;(j/query (db-connection) ["SELECT * FROM FRUIT"])



;(j/execute! db-spec
;            ["update fruit set cost = ( 2 * grade ) where grade > ?" 50.0])

;(j/update! db-spec :fruit
;           {:cost 49}
;           ["grade < ?" 75])

;(j/execute! db-spec ["DELETE FROM fruit WHERE grade < ?" 25.0])

;(j/insert! db-spec :fruit
;           [:name :cost]
;           ["Mango" 722]
;           ["Feijoa" 441])

;(j/insert! db-spec :fruit
;           {:name "Pomegranate" :appearance "fresh" :cost 585}
;           {:name "Kiwifruit" :grade 93})

;(j/insert! db-spec :fruit {:name "Pear" :appearance "green" :cost 99})
;(j/drop-table-ddl :fruit :entities clojure.string/upper-case)


;ResultSet getTables(String catalog,
;                    String schemaPattern,
;                    String tableNamePattern,
;                    String[] types)
;                    throws SQLException

;(metadata-result rs-or-value & {:keys [identifiers as-arrays? row-fn result-set-fn], :or {identifiers lower-case, row-fn identity}})

;(doto (io/file db-filename) (.exists))
;(-> (io/file db-filename) (.exists))

;(with-db-connection [db-con db-spec]
;  (let [;; fetch some rows using this connection
;        rows (jdbc/query db-con ["SELECT * FROM table WHERE id = ?" 42])]
;    ;; insert a copy of the first row using the same connection
;    (jdbc/insert! db-con :table (dissoc (first rows) :id))))

;(def db-spec
;  {:classname "com.mysql.jdbc.Driver"
;   :subprotocol "mysql"
;   :subname "//127.0.0.1:3306/mydb"
;   :user "myaccount"
;   :password "secret"})

;(defn update-or-insert!
;  "Updates columns or inserts a new row in the specified table"
;  [db table row where-clause]
;  (j/with-db-transaction [t-con db]
;    (let [result (j/update! t-con table row where-clause)]
;      (if (zero? (first result))
;        (j/insert! t-con table row)
;        result))))
;
;(update-or-insert! mysql-db :fruit
;                   {:name "Cactus" :appearance "Spiky" :cost 2000}
;                   ["name = ?" "Cactus"])
;;; inserts Cactus (assuming none exists)
;(update-or-insert! mysql-db :fruit
;                   {:name "Cactus" :appearance "Spiky" :cost 2500}
;                   ["name = ?" "Cactus"])
;;; updates the Cactus we just inserted

;(jdbc/db-do-commands db-spec
;                     (jdbc/create-table-ddl :fruit
;                                            [:name "varchar(32)"]
;                                            [:appearance "varchar(32)"]
;                                            [:cost :int]
;                                            [:grade :real]))
;(jdbc/db-do-commands db-spec "CREATE INDEX name_ix ON fruit ( name )")

;(jdbc/insert! db-spec :table {:col1 42 :col2 "123"}) ;; Create
;(jdbc/query!  db-spec ["SELECT * FROM table WHERE id = ?" 13]) ;; Read
;(jdbc/update! db-spec :table {:col1 77 :col2 "456"} ["id = ?" 13]) ;; Update
;(jdbc/delete! db-spec :table ["id = ?" 13]) ;; Delete

;; (?d) Unix lines (only match \newline)
;; (?i) Case-insensitive
;; (?u) Unicode-aware Case
;; (?m) Multiline
;; (?s) Dot matches all (including newline)
;; (?x) Ignore Whitespace and comments


;(defn read-from-user
;  "read user input"
;  (while (not (str/blank? (read-line)))
;    )
;  )
;; Apache Derby
;[org.apache.derby/derby "10.8.1.2"]
;; HSQLDB
;[hsqldb/hsqldb "1.8.0.10"]
;; Microsoft SQL Server using the jTDS driver
;[net.sourceforge.jtds/jtds "1.2.4"]
;; MySQL
;[mysql/mysql-connector-java "5.1.25"]
;; PostgreSQL
;[postgresql/postgresql "8.4-702.jdbc4"]
;; SQLite
;[org.xerial/sqlite-jdbc "3.7.2"]

;(j/query db-spec ["SELECT * FROM mixedTable"]
;         :identifiers #(.replace % \_ \-))

;(take-while str/blank? (repeatedly (read-line)))
;(take-while (comp str/blank? not) ["1" "2" "" "3"])
