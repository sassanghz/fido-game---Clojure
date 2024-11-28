(ns fido
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [food])) ;; Properly include the food namespace

;;-------------------
;; THE MENU FUNCTIONS
;; ------------------

;; Display the menu and ask the user for the option
(defn show-menu []
  (println "\n\n*** Let's Feed Fido ***")
  (println "-----------------------\n")
  (println "1. Display list of map files")
  (println "2. Display a map for Fido")
  (println "3. Exit")
  (do
    (print "\nEnter an option? ")
    (flush)
    (read-line)))

;; Option 1: Display all .txt files
(defn option1 []
  (food/list-files)) ;; Call list-files from food.clj

;; Option 2: Read and display the map, and let Fido find the food
(defn option2 []
  (print "\nPlease enter a file name => ")
  (flush)
  (let [file-name (read-line)
        map-data (food/read-map file-name)] ;; Call read-map from food.clj
    (when map-data
      ;; Print the original map
      (println "\nThis is Fido's challenge:")
      (doseq [line map-data]
        (println (apply str line)))
      ;; Start Fido's search
      (let [updated-map (food/find-food map-data 0 0)]
        ;; Print the updated map
        (println "\nFinal Map:")
        (doseq [line updated-map]
          (println (apply str line)))))))

;; If the menu selection is valid, call the relevant function
(defn process-option [option]
  (cond
    (= option "1") (option1)
    (= option "2") (option2)
    :else (println "Invalid menu option")))

;; Display the menu and get a menu item selection. Process the
;; selection and then loop again to get the next menu selection
(defn menu []
  (let [option (str/trim (show-menu))]
    (if (= option "3")
      (println "\nGood Bye\n")
      (do
        (process-option option)
        (recur)))))

;; Run the program
(menu)
