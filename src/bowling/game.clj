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
  (if (or (and (= 2 (count (last frames))) (not= 10 (count frames))) (strike? (last frames)))
      (conj frames [pins-hit])
	  (replace-last frames (conj (last frames) pins-hit))))
  
(defn- add-roll-to-frames! [pins-hit]
  (swap! frames add-roll-to-frames pins-hit))
  
(defn- kind-of-frame [frame next-frame]
  (cond (and (strike? frame) next-frame) :strike
        (and (spare? frame) next-frame)  :spare
        :else                            :standard))  
  
(defmulti #^{:private true} score-frame kind-of-frame)  
  
(defmethod score-frame :standard [frame next-frame]
  (prn "standard...")
  (sum frame)) 

(defmethod score-frame :spare [frame next-frame]
  (prn "spare...")
  (+ (sum frame) (first next-frame)))
    
(defmethod score-frame :strike [frame next-frame]
  (prn "strike...")
  (+ (sum frame) (first next-frame) (second next-frame)))

(defn start-game! []
  (reset! frames [[]]))		
		
(defnk roll! [pins-hit :times 1]
  (dotimes [i times] 
    (add-roll-to-frames! pins-hit))) 
   
(defn score-game []
  (let [next-frames (conj (vec (rest @frames)) nil)]
    (sum (map score-frame @frames next-frames))))