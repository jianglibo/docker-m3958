(ns docker-m3958.core-test
  (:require [clojure.test :refer :all]
            [clojure.test.junit :refer :all]
            [clojure.test.tap :refer :all]
            [docker-m3958.dbmodule :as dbmodule]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1) "0 not equal to 1")))

(def x 5)

(deftest re-binding-test
  (is (thrown? IllegalStateException (binding [x 6] x)))
  (is (thrown-with-msg? IllegalStateException #"bind non-dynamic var" (binding [x 6] x))))

(with-junit-output
  (run-tests))

(with-tap-output
   (run-tests))

(dbmodule/init "fixturedb")

(dbmodule/init-tables)

(assoc {:a "a" :b "b"} :a "new a")
(def z)
(bound? (var z))

::x
