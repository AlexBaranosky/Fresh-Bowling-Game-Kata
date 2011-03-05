(ns bowling.game
  (:use clojure.contrib.def))
  
(defvar- sum (partial reduce +))  
  
(defn- replace-last [seq item]
  (conj (vec (drop-last 1 seq)) item))
  
(defvar- frames (atom [[]]))
  
(defn start-game []
  (reset! frames [[]]))
  
(defn add-roll [frames pins-hit]  
  (if (= 2 (count (last frames)))
      (conj frames [pins-hit])
	  (replace-last frames (conj (last frames) pins-hit))))
  
(defn- score-roll [pins-hit]
  (swap! frames add-roll pins-hit))	
  
(defnk roll [pins-hit :times 1]
   (doseq [i (repeat times pins-hit)]
    (score-roll i))) 
   
(defn- spare? [frame]
  (and (= 2 (count frame)) (= 10 (sum frame))))   
   
(defn- score-frame [frames idx frame]
  (if (and (spare? frame) (< idx 9))
    (+ (sum frame) (first (get frames (inc idx))))
	(sum frame)))
   
(defn- score-for-each-frame [frames]
  (map-indexed (partial score-frame frames) frames))  
   
(defn score-game []
  (prn (count @frames))
  (sum (score-for-each-frame @frames)))