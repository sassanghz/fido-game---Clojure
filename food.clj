(ns food
  (:require [clojure.java.io :as io]))

(defn list-files []
  (println "Map List:")
  ;; traverse the dir and .txt files
  (doseq [file (file-seq (io/file "."))]
    (when (and (.isFile file) (.endsWith (.getName file) ".txt"))
      (println (str "* " (.getPath file)))))
  (println "\nPress any key to continue")
  (flush) 
  (read-line)) ; Wait for the user to press the key
