(ns food
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

;; List all .txt files in the current directory
(defn list-files []
  (println "Map List:")
  (doseq [file (file-seq (io/file "."))]
    (when (and (.isFile file) (.endsWith (.getName file) ".txt"))
      (println (str "* " (.getPath file)))))
  (println "\nPress any key to continue")
  (flush)
  (read-line)) ; Wait for the user to press a key

;; Read the map and convert it into a 2D data structure
(defn read-map [file-name]
  (let [file (io/file file-name)]
    (if (.exists file)
      (let [file-content (slurp file)
            map-data (vec (map vec (str/split-lines file-content)))]
        (println "\nMap File Content:")
        (println file-content)
        map-data)
      (do
        (println "Oops: specified file does not exist")
        nil))))


(defn find-food [map-data row col]
  (let [rows (count map-data)
        cols (count (first map-data))]
    (cond
      (or (< row 0) (>= row rows) (< col 0) (>= col cols)) false
      (= (get-in map-data [row col]) \@) (do
                                           (assoc-in map-data [row col] \+)
                                           (println "\nWoo Hoo - Fido found her food!")
                                           true)
      (or (= (get-in map-data [row col]) \#)
          (= (get-in map-data [row col]) \+)
          (= (get-in map-data [row col]) \!)) false
      :else ((let [updated-map (assoc-in map-data [row col] \+)];; creates a new map but doe not modify the original
                (if (or
                     (find-food updated-map (dec row) col) ;up
                     (find-food updated-map (inc row) col) ;down
                     (find-food updated-map row (dec col)) ;left
                     (find-food updated-map row (inc col))) ;right   
                     updated-map
                  (assoc-in updated-map [row col] \!))))))
  )