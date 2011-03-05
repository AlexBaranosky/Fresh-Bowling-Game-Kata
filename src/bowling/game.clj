(ns bowling.game
  (:use clojure.contrib.def))
  
(defn start-game []
  )  
  
(defn- score-roll [pins-hit]
  )  
  
(defnk roll [pins-hit :times 1]
   (doseq [i (repeat times pins-hit)]
    (score-roll i))) 
   
(defn roll-spare [] 
  (roll 5 :times 2))

(defn roll-strike []
  (roll 10))
   
(defn score-game []
  0)
   
