(ns docker-m3958.dbmodule-test
  (:require [clojure.test :refer :all])
  (:require [clojure.java.io :as io])
  (:require [clojure.test.junit :refer :all])
  (:require [docker-m3958.jutil :as jutil])
  (:require [clojure.test.tap :refer :all])
  (:require [docker-m3958.dbmodule :as dbmodule]))

(def dbpath (delay (jutil/filename-in-user-home "testdb" "fixturedb")))


(io/make-parents @dbpath)

(defn dbfixture
  [f]
  (dbmodule/init @dbpath)
  (f)
  (dbmodule/cleanup))

(use-fixtures :once dbfixture)

(def x 6)

(deftest re-binding-test
  (is (thrown? IllegalStateException (binding [x 6] x)) "static var cann't binding")
  (is (thrown-with-msg? IllegalStateException #"bind non-dynamic var" (binding [x 6] x))) "bind non-dynamic var")

(deftest addition
  (is (= 4 (+ 2 2)))
  (is (= 7 (+ 3 4))))


;(with-junit-output
;  (run-tests))

(deftest create-tables
  (is (= (repeat 6 '(0)) (dbmodule/init-tables)))
  (is (= (repeat 6 '(0)) (dbmodule/drop-tables))))

(run-tests)

;(dbmodule/init @dbpath)
;(dbmodule/init-tables)
;(dbmodule/drop-tables)
;(dbmodule/all-table-names)


;(deftest a-test
;  (testing "FIXME, I fail."
;    (is (= 0 1) "0 not equal to 1")))


;(with-tap-output
;   (run-tests))
