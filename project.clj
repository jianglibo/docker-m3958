(defproject docker-m3958 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.5"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [com.mchange/c3p0 "0.9.2.1"]
                 [org.hsqldb/hsqldb "2.3.2"]]
  :main ^:skip-aot docker-m3958.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
