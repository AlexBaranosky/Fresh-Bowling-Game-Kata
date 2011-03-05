(ns bowling.test.game
  (:use bowling.game)
  (:use midje.sweet))

(defn roll-many [pins-hit num-rolls]
  (doseq [i (repeat num-rolls pins-hit)]
    (roll i)))   
   
(defn- roll-spare [] 
  (roll-many 5 2))

(defn- roll-strike []
  (roll 10))
	
(fact "scores 0 when no pins knocked down for whole game"
  (start-game)
  (roll-many 0 20)
  (score-game) => 0)

(fact "score 20 when all rolls of one pin"
  (start-game)
  (roll-many 1 20)
  (score-game) => 20)

(fact "counts the roll after a spare twice"
  (start-game)
  (roll-spare)
  (roll 3)
  (roll-many 0 16)
  (score-game) => 16)

(fact "counts the two rolls afer a strike twice"
  (start-game)
  (roll-strike)
  (roll 3)
  (roll 4)
  (roll-many 0 16)
  (score-game) => 24)

(fact "scores 300 for a perfect game: 12 strikes"
  (start-game)
  (roll-many 10 12)
  (score-game) => 300)

(fact "scores 150 for all fives (a spare each round)"
  (start-game)
  (roll-many 5 21)
  (score-game) => 150)

	
