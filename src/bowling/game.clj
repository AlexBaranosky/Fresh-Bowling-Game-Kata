(ns bowling.game
  (:use clojure.contrib.def))
  
(defvar- sum (partial reduce +))
  
(defvar- frames (atom [[]]))

(defn- all-pins-down-and-on-nth-roll? [nth-roll frame]
  (and (= nth-roll (count frame)) (= 10 (sum frame))))   
	
(defvar- spare? (partial all-pins-down-and-on-nth-roll? 2))

(defvar- strike? (partial all-pins-down-and-on-nth-roll? 1)) 
  
(defn- add-roll-to-frames [frames pins-hit]        
  (if (and (not= 10 (count frames)) 
           (or (= 2 (count (last frames))) (strike? (last frames))))
      (conj frames [pins-hit])
	  (assoc frames (dec (count frames)) (conj (last frames) pins-hit)))) 
  
(defn- score-frame [frame next-frame third-frame]
  (cond (and (strike? frame) next-frame) 
		(+ (sum frame) (first next-frame) (if (= 1 (count next-frame)) (first third-frame) (second next-frame)))
        (and (spare? frame) next-frame)  
		(+ (sum frame) (first next-frame))
        :else 
		(sum frame)))  

(defn start-game! []
  (prn "starting game...")
  (reset! frames [[]]))		
		
(defnk roll! [pins-hit :times 1]
  (dotimes [i times] 
   (swap! frames add-roll-to-frames pins-hit))) 
   
(defn score-game []
  (prn "frames: " @frames)
  (let [next-frames (concat (rest @frames) [nil])
        third-frames (concat (rest next-frames) [nil])]
    (sum (map score-frame @frames next-frames third-frames))))