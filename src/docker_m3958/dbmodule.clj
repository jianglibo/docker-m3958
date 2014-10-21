(ns docker-m3958.dbmodule
  (:require [clojure.java.io :as io])
  (:require [clojure.data.json :as json])
  (:require [docker-m3958.const :as const])
  (:require [clojure.string :as str])
  (:require [clojure.java.shell :as shell])
  (:require [clojure.java.jdbc :as j])
  (:import java.nio.file.Paths)
  (:require [clojure.math.numeric-tower :as math])
  (:import org.hsqldb.jdbc.JDBCDriver)
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (:gen-class))

;:table-spec "ENGINE=InnoDB"
(def pooled-db-ref)


(defn init-db-path []
  (io/make-parents const/DB-PATH))

(defn db-spec [db-fn]
  {:classname "org.hsqldb.jdbc.JDBCDriver"
   :subprotocol "hsqldb"
   :subname (str "file:" db-fn)
   :user "aUserName"
   :password "3xLVz"})

(defn- pool
  [spec]
  (let [cpds (doto (ComboPooledDataSource.)
               (.setDriverClass (:classname spec))
               (.setJdbcUrl (str "jdbc:" (:subprotocol spec) ":" (:subname spec)))
               (.setUser (:user spec))
               (.setPassword (:password spec))
               ;; expire excess connections after 30 minutes of inactivity:
               (.setMaxIdleTimeExcessConnections (* 30 60))
               ;; expire connections after 3 hours of inactivity:
               (.setMaxIdleTime (* 3 60 60)))]
    {:datasource cpds}))


(defn- init-pool-var
  [db-path]
  (def pooled-db-ref (delay (pool (db-spec db-path)))))

(defn init
  [& args]
  (if args
    (init-pool-var (first args))
    (init-pool-var const/DB-PATH)))

(defn cleanup
  []
  (.close (:datasource @pooled-db-ref)))

(defn db-connection [] @pooled-db-ref)

(defn db-meta []
  (j/with-db-metadata [md (db-connection)]
        (j/metadata-result (.getTables md nil nil nil (into-array ["TABLE"])))))

(defn table-meta [tablename]
  (j/with-db-metadata [md (db-connection)]
        (j/metadata-result (.getColumns md nil nil tablename nil))))
; :identifiers #(.replace % \_ \-)

(defn all-table-names []
  (map :table_name (db-meta)))

(defn table-exist? [tablename]
  (some #(= (str/upper-case tablename) (str/upper-case %1))
    (map :table_name (db-meta))))

(defn column-exist? [tablename colname]
  (some #(= (str/upper-case tablename) (str/upper-case %1))
    (map :table_name (table-meta (str/upper-case (name colname))))))

(defn get-table-ddl [td]
  (apply j/create-table-ddl td))

(defn create-table
  "create one table."
  [td]
  (try
    (j/db-do-commands (db-connection) (apply j/create-table-ddl td))
    (catch Exception e)))

(defn init-tables []
  (for [td (map const/TABLE-DSCS [:app :apptpl :image :host :container :apptpl_image])]
    (create-table td)))

(defn drop-tables []
  (for [tname (map name [:container :apptpl_image :app :apptpl :image :host])]
    (j/db-do-commands (db-connection)
                      (j/drop-table-ddl tname))))

(defn create-apptpl [tplname desc]
  (j/insert! (db-connection) :apptpl
             {:name tplname :desc desc}))

(defn get-apptpls []
  (j/query (db-connection) ["SELECT * FROM apptpl"]
           :identifiers #(.replace % \_ \-)))



;(defn init
;  [& args]
;  (if (bound? #'pooled-db-ref) (throw (Exception. "pooled-db-ref already initialized."))
;    (if args (init-pool-var const/DB-PATH) (init-pool-var (first args)))))

;(random-name 5)

;(try
;  (j/execute! (db-connection) ["CREATE SEQUENCE myseq AS INTEGER"])
;  (catch Exception e (.getMessage e)))


;(j/query (db-connection) ["SELECT NEXT VALUE FOR myseq AS ni FROM image"])

;(init-tables)
;(all-table-names)
;(create-apptpl "firsttpl" "firstpl")
;(get-apptpls)
;(drop-tables)

;(str/join [1 2 3])

;(def alphanumeric (delay
;  (concat (map char (range 97 123)) (map char (range 48 57)))))

;(str/join (take 8 (repeatedly #(rand-nth "0123456789abcdefghijklmnopqrstuvwxyz"))))
;(math/expt 36 8)
;(math/expt 38 8)
;(#{(list 1 2 3)} (list 1 2 3))
;java -cp C:\Users\admin\.m2\repository\org\hsqldb\hsqldb\2.3.2\hsqldb.jar org.hsqldb.util.DatabaseManager
;java -cp C:\Users\admin\.m2\repository\org\hsqldb\hsqldb\2.3.2\hsqldb.jar org.hsqldb.util.DatabaseManagerSwing
;(map :table_name (db-meta))
;(map :column_name (table-meta (str/upper-case (name (first (first tds))))))
;(init-tables)

;(for [x (list 0 1 2 3 4 5)
;             :let [y (* x 3)]
;             :when (even? y)]
;         y)
;(j/db-do-commands (db-connection) (j/drop-table-ddl :fruit))

;(let [[a b c & d :as e] [1 2 3 4 5 6 7]]
;  [a b c d e])
;
;(let [[[x1 y1][x2 y2]] [[1 2] [3 4]]]
;  [x1 y1 x2 y2])
;
;(let [[a b & c :as str] "asdjhhfdas"]
;  [a b c str])
;
;(let [{a :a, b :b, c :c, :as m :or {a 2 b 3}}  {:a 5 :c 6}]
;  [a b c m])


;(name :smb)
;(let [{:keys [table-spec entities] :or {table-spec "a" entities "b"}} {}]
;  [table-spec entities])
;
;(defn create-table-ddl
;  "Given a table name and column specs with an optional table-spec
;   return the DDL string for creating that table."
;  [table & specs]
;  (let [col-specs (take-while (fn [s]
;                                (not (or (= :table-spec s)
;                                         (= :entities s)))) specs)
;        other-specs (drop (count col-specs) specs)
;        {:keys [table-spec entities] :or {entities identity}} other-specs
;        table-spec-str (or (and table-spec (str " " table-spec)) "")
;        spec-to-string (fn [spec]
;                         (str/join " " (cons (as-sql-name entities (first spec))
;                                             (map name (rest spec)))))]
;    (format "CREATE TABLE %s (%s)%s"
;            (as-sql-name entities table)
;            (str/join ", " (map spec-to-string col-specs))
;            table-spec-str)))
