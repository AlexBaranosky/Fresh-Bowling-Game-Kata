(ns bowling.game
  (:use clojure.contrib.def))
  
(defvar- sum (partial reduce +))  
  
(defn- replace-last [seq item]
  (conj (vec (drop-last 1 seq)) item))
  
(defvar- frames (atom [[] [] [] [] [] [] [] [] [] []]))
  
(defn start-game []
  )
  
(defn add-roll [frames pins-hit]  
  (if (= 2 (count (last frames)))
      (conj frames [pins-hit])
	  (replace-last frames (conj (last frames) pins-hit))))
  
(defn- score-roll [pins-hit]
  (swap! frames add-roll pins-hit))	
  
(defnk roll [pins-hit :times 1]
   (doseq [i (repeat times pins-hit)]
    (score-roll i))) 
   
(defn score-game []
  (sum (map sum @frames)))
   
