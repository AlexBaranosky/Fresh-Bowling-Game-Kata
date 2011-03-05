(ns bowling.test.game
  (:use bowling.game)
  (:use midje.sweet))
  
(defn roll-many [n pins]
   (dorun (map roll (repeat n pins))))

(defn- roll-spare []
  (do
    (roll 5)
    (roll 5)))

(defn- roll-strike []
  (do
    (roll 10)))
	
(fact "scores 0 when no pins knocked down for whole game"
  (do
    (start-game)
    (roll-many 20 0)
    (score-game)) => 0)

(fact "score 20 when all rolls of one pin"
  (do
    (start-game)
    (roll-many 20 1)
    (score-game)) => 20)

(fact "counts the roll after a spare twice"
  (do
    (start-game)
    (roll-spare)
    (roll 3)
    (roll-many 16 0)
    (score-game)) => 16)

(fact "counts the two rolls afer a strike twice"
  (do
    (start-game)
    (roll-strike)
    (roll 3)
    (roll 4)
    (roll-many 16 0)
    (score-game)) => 24)

(fact "scores 300 for a perfect game: 12 strikes"
  (do
    (start-game)
    (roll-many 12 10)
    (score-game)) => 300)

(fact "scores 150 for all fives (a spare each round)"
  (do
    (start-game)
    (roll-many 21 5)
    (score-game)) => 150)

	
