(ns bowling.game
  (:use clojure.contrib.def))
  
(defvar- sum (partial reduce +))
  
(defvar- rolls (atom []))

(defn spare? [rolls]
  (= 10 (sum (take 2 rolls))))

(defn strike? [rolls]
  (= 10 (first rolls)))   
   
(defn rolls-to-advance [rolls frame]
    (cond (and (= 10 frame) (or (spare? rolls) (strike? rolls))) 3
	      (strike? rolls) 1    
		  :else 2))
		     
(defn items-in-frame [rolls]
  (cond (strike? rolls) 3
        (spare? rolls) 3
        :else 2))
   
(defn start-game! []
  (prn "starting game...")
  (reset! rolls []))		
		
(defnk roll! [pins-hit :times 1]
  (dotimes [i times] 
   (swap! rolls conj pins-hit))) 
   
(defn score-game []
  (prn "rolls: " @rolls)
  (loop [total 0 frame 1 rolls @rolls]
    (if (not (empty? rolls))
      (recur (+ total (sum (take (items-in-frame rolls) rolls)))
             (inc frame)
             (drop (rolls-to-advance rolls frame) rolls))
      total)))