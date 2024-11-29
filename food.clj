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
  (read-line) ; Wait for the user to press a key
  (print (str (char 27) "[2J")); Clear the screen
  (flush))

;; Read the map and convert it into a 2D data structure
(defn read-map [file-name]
  (let [file (io/file file-name)]
    (if (.exists file)
      (let [file-content (slurp file)
            map-data (vec (map vec (str/split-lines file-content)))]
        map-data)
      nil))) ;; return nil if file doesnt exist


(defn explore-food [map-data row col found?]
  (let [rows (count map-data)
        cols (count (first map-data))]
    (cond
      ;; Out of bounds
      (or (< row 0) (>= row rows) (< col 0) (>= col cols)) [map-data found?]

      ;; Hit a wall, already visited, or marked as a dead end
      (or (= (get-in map-data [row col]) \#)
          (= (get-in map-data [row col]) \+)
          (= (get-in map-data [row col]) \!)) [map-data found?]

      ;; Found the food
      (= (get-in map-data [row col]) \@)
      [(assoc-in map-data [row col] \@) true] ;; Preserve `@` and mark as f>

      ;; Explore paths recursively
      :else
      (let [updated-map (assoc-in map-data [row col] \+) ;; Mark current po>            [up-map up-found] (explore-food updated-map (dec row) col found>            [down-map down-found] (explore-food up-map (inc row) col up-fou>            [left-map left-found] (explore-food down-map row (dec col) down>            [right-map right-found] (explore-food left-map row (inc col) le>        (if right-found
          [right-map true] ;; If food is found, propagate success
          ;; If no food is found, mark the current cell as a dead end
          [(assoc-in right-map [row col] \!) false])))))

(defn find-food [map-data row col]
  (let [[updated-map found?] (explore-food map-data row col false)]
    (if found?
      (do
        (println "\nWoo Hoo - Fido found her food!")
        updated-map)
      (do
        (println "\nOh no - Fido could not find her food")
        updated-map))))