(ns bowling.game
  (:use clojure.contrib.def))
  
(defvar- sum (partial reduce +))  
  
(defn- replace-last [seq item]
  (conj (vec (drop-last 1 seq)) item))
  
(defvar- frames (atom [[]]))
  
(defn start-game []
  (reset! frames [[]]))

(defn- all-pins-down-and-on-nth-roll? [nth-roll frame]
  (and (= nth-roll (count frame)) (= 10 (sum frame))))   
	
(defvar- spare? (partial all-pins-down-and-on-nth-roll? 2))

(defvar- strike? (partial all-pins-down-and-on-nth-roll? 1)) 
  
(defn- add-roll-to-frames [frames pins-hit]  
  (if (or (= 2 (count (last frames))) (strike? (last frames)))
      (conj frames [pins-hit])
	  (replace-last frames (conj (last frames) pins-hit))))
  
(defn score-roll [pins-hit]
  (swap! frames add-roll-to-frames pins-hit))
   
(defn- score-frame [frames idx frame]
  (cond (and (spare? frame) (< idx 9))
        (+ (sum frame) (first (frames (inc idx))))
		(and (strike? frame) (< idx 9))
        (+ (sum frame) (first (frames (inc idx))) (second (frames (inc idx))))
		:else
	    (sum frame)))
   
(defn- score-for-each-frame [frames]
  (map-indexed (partial score-frame frames) frames))  
   
(defn score-game []
  (sum (score-for-each-frame @frames)))