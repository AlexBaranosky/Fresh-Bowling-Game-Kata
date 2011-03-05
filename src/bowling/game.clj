(ns bowling.game
  (:use clojure.contrib.def))

(defvar- the-game (atom 0))
  
(defn start-game []
  )
  
(defn- score-roll [pins-hit]
  (swap! the-game + pins-hit))	
  
(defnk roll [pins-hit :times 1]
   (doseq [i (repeat times pins-hit)]
    (score-roll i))) 
   
(defn score-game []
  @the-game)
   
