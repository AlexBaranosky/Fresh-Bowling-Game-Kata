(ns bowling.test.game
  (:use bowling.game)
  (:use midje.sweet))	
	
(fact "score 0 when no pins knocked down for whole game"
  (score-rolls (repeat 20 0)) => 0)

(fact "score 20 when every roll knocks down 1 pin"
   (score-rolls (repeat 20 1)) => 20)

(fact "counts the roll after a spare twice"
  (score-rolls (concat [5 5 3] (repeat 16 0))) => 16)

(fact "counts the two rolls afer a strike twice"
  (score-rolls (concat [10 3 4] (repeat 16 0))) => 24)

(fact "scores 300 for a perfect game: 12 strikes"
  (score-rolls (repeat 12 10)) => 300)

(fact "scores 150 for all fives (a spare each round)"
  (score-rolls (repeat 21 5)) => 150)