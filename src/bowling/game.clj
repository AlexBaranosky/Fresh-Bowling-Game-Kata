(ns bowling.game)
  
(def ^{:private true} sum (partial reduce +))

(def ^{:private true} spare? (comp (partial = 10) sum (partial take 2)))

(def ^{:private true} strike? (comp (partial = 10) first))  
   
(defn rolls-to-advance [rolls frame]
    (cond (and (= 10 frame) (or (spare? rolls) (strike? rolls))) 3
	      (strike? rolls) 1    
		  :else 2))
		     
(defn items-in-frame [rolls]
  (cond (strike? rolls) 3
        (spare? rolls) 3
        :else 2))		
	  
(defn score-rolls [rolls]
  (prn "rolls: " rolls)
  (loop [total 0 frame 1 rolls rolls]
    (if (not (empty? rolls))
      (recur (+ total (sum (take (items-in-frame rolls) rolls)))
             (inc frame)
             (drop (rolls-to-advance rolls frame) rolls))
      total)))