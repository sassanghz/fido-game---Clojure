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
      ;; Out of bounds
      (or (< row 0) (>= row rows) (< col 0) (>= col cols)) false

      ;; Found the food
      (= (get-in map-data [row col]) \@) (do
                                           (println "\nWoo Hoo - Fido found her food!")
                                           (assoc-in map-data [row col] \+))

      ;; hit a wall, already visited, or marked as a dead end
      (or (= (get-in map-data [row col]) \#)
          (= (get-in map-data [row col]) \+)
          (= (get-in map-data [row col]) \!)) false

      ;; fido going through the open doors
      :else (let [updated-map (assoc-in map-data [row col] \+)] ;; Mark current cell as part of the path
              (or
               ;; moving in all directions
               (find-food updated-map (dec row) col)   ;; Up
               (find-food updated-map (inc row) col)   ;; Down
               (find-food updated-map row (dec col))   ;; Left
               (find-food updated-map row (inc col))   ;; Right)
               ;; Mark as dead-end if no path
               (assoc-in updated-map [row col] \!))))))
