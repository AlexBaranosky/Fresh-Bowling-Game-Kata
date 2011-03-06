(ns bowling.game
  (:use clojure.contrib.def))
  
(defvar- sum (partial reduce +))  
  
(defn- replace-last [seq item]
  (conj (vec (drop-last 1 seq)) item))
  
(defvar- frames (atom [[]]))

(defn- all-pins-down-and-on-nth-roll? [nth-roll frame]
  (and (= nth-roll (count frame)) (= 10 (sum frame))))   
	
(defvar- spare? (partial all-pins-down-and-on-nth-roll? 2))

(defvar- strike? (partial all-pins-down-and-on-nth-roll? 1)) 
  
(defn- add-roll-to-frames [frames pins-hit]  
  (if (or (= 2 (count (last frames))) (strike? (last frames)))
      (conj frames [pins-hit])
	  (replace-last frames (conj (last frames) pins-hit))))
  
(defn- add-roll-to-frames! [pins-hit]
  (swap! frames add-roll-to-frames pins-hit))
  
(defn- kind-of-roll [frames idx frame]
  (cond (and (strike? frame) (< idx 9)) :strike
        (and (spare? frame) (< idx 9))  :spare
		:else                           :standard))  
  
(defmulti score-frame kind-of-roll)  
  
(defmethod score-frame :standard [frames idx frame]
  (sum frame)) 

(defmethod score-frame :spare [frames idx frame]
  (+ (sum frame) (first (frames (inc idx))))) 
    
(defmethod score-frame :strike [frames idx frame]
  (+ (sum frame) (first (frames (inc idx))) (second (frames (inc idx)))))

(defn start-game! []
  (reset! frames [[]]))		
		
(defnk roll! [pins-hit :times 1]
  (dotimes [i times] 
    (add-roll-to-frames! pins-hit))) 
   
(defn score-game []
  (let [score-for-each-frame (map-indexed (partial score-frame @frames) @frames)]
    (sum score-for-each-frame)))