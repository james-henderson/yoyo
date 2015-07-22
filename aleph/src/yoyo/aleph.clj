(ns yoyo.aleph
  (:require [aleph.http :as http]
            [clojure.tools.logging :as log]))

(defn with-server [{:keys [handler server-opts]}]
  (fn [f]
    (log/infof "Starting web server on port %d..." (:port server-opts))
    (let [server (http/start-server (some-fn handler
                                             (constantly {:status 404
                                                          :body "Not found"}))

                                    server-opts)]
      (log/info "Started web server.")

      (f server
         (fn []
           (log/info "Stopping web server...")
           (.close server)
           (log/info "Stopped web server."))))))
