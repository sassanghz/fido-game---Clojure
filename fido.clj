(ns fido
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))
  ;; this is where you would include/require the food module if needed


;;-------------------
;; THE MENU FUNCTIONS
;; ------------------

;; Display the menu and ask the user for the option
(defn showMenu
  []
  (println "\n\n*** Let's Feed Fido ***")
  (println     "-----------------------\n")
  (println "1. Display list of map files")
  (println "2. Display a map for Fido")
  (println "3. Exit")
  (do
    (print "\nEnter an option? ")
    (flush)
    (read-line)))


;; Display all .txt files
(defn option1
  []
  (println "\nMap List:")
  ;; Use file-seq to iterate over files in the current folder
  (doseq [file (file-seq (io/file "."))]
    (when (and (.isFile file) (.endsWith (.getName file) ".txt")) 
      (println (str "* " (.getPath file))))) 
  (println "\nPress any key to continue...")
  (flush)
  (read-line))


;; Get the map 
(defn option2
  []
  (print "\nPlease enter a file name => ")
  (flush)
  (let [file_name (read-line)] 
    (if (.exists (io/file file_name))
      (println "now read" file_name "with slurp and try to find Fido's food")
      (println "Error: File does not exist"))))



;; If the menu selection is valid, call the function
(defn processOption
  [option]
  (cond
    (= option "1") (option1)
    (= option "2") (option2)
    :else (println "Invalid menu option")))


;; Display the menu and get a menu item selection. Process the
;; selection and then loop again to get the next menu selection
(defn menu
  [] 
  (let [option (str/trim (showMenu))]
    (if (= option "3")
      (println "\nGood Bye\n")
      (do
        (processOption option)
        (recur)))))


(menu)