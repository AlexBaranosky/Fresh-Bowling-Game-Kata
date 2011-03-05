(ns bowling.test.game
  (:use bowling.game)
  (:use midje.sweet))
	
(fact "scores 0 when no pins knocked down for whole game"
  (start-game)
  (roll 0 :times 20)
  (score-game) => 0)

(fact "score 20 when all rolls of one pin"
  (start-game)
  (roll 1 :times 20)
  (score-game) => 20)

(fact "counts the roll after a spare twice"
  (start-game)
  (roll-spare)
  (roll 3)
  (roll 0 :times 16)
  (score-game) => 16)

(fact "counts the two rolls afer a strike twice"
  (start-game)
  (roll-strike)
  (roll 3)
  (roll 4)
  (roll 0 :times 16)
  (score-game) => 24)

(fact "scores 300 for a perfect game: 12 strikes"
  (start-game)
  (roll 10 :times 12)
  (score-game) => 300)

(fact "scores 150 for all fives (a spare each round)"
  (start-game)
  (roll 5 :times 21)
  (score-game) => 150)

	
