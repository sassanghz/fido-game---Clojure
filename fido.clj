;; -------------
;; This is the menu template for the Fido app. You can use it as a starting
;; point for the assignment
;; ------------

(ns a3
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))
  ; this is where you would include/require the food module


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


;; Display all files in the current folder
(defn option1
  []
  (println "use file-seq to get and print a list of all files in the current folder"))
    
    
    
;; Get the map specified by the user and start looking for the food
(defn option2
  []
  (print "\nPlease enter a file name => ") 
  (flush)
  (let [file_name (read-line)]
     (println "now read" file_name "with slurp and try to find Fido's food")))



; If the menu selection is valid, call the relevant function to 
; process the selection
(defn processOption
  [option] 
  (if( = option "1")
     (option1)
     (if( = option "2")
        (option2)   
        (println "Invalid menu option"))))


; Display the menu and get a menu item selection. Process the
; selection and then loop again to get the next menu selection
(defn menu
  [] ; parm(s) can be provided here, if needed
  (let [option (str/trim (showMenu))]
    (if (= option "3")
      (println "\nGood Bye\n")
      (do 
         (processOption option)
         (recur )))))   



; ------------------------------
; Run the program.
(menu)
