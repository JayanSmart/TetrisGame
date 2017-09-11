# Tetris for 2

Code Documentation is included as java docs at TetrisGame/docs/

Dependancies:
-
- Java: 1.8

- External Libraries:
	- json-simple-1.1.1.jar

Running The Game:
-
This project has external libraries on which it is dependant. To compile the code, the json-simple-1.1.1.jar file needs to be added as a project library and set as a dependency for every module.
To save the Tutors time I have included a TetrisGame.jar which is in the out/artifacts folder runnable using:\
java -jar TetrisGame.jar from the relevant directory.

Playing the Game:
-
This a a 2 player Tetris Game. One player will use the arrow keys, the other must use WSAD. Controls are as follows:
- DOWN/S: Move a block down
- UP/W: Rotate the block clockwise
- LEFT/A: Move the block to the left
- RIGHT/D: Move the block to the right
- ESCAPE: Exits the current Game

The winner of any one round is decided by the player with the highest score at the end of the game.

The player who's piece "looses" the game has no negative consequences.

This game includes Achievements, these are displayed in grey at the begining of each round. When achieved the player who achieves it will get a proportional score boost. These can swing the game fairly quickly and each achievement can only be achieved once per game.

Resetting:
-
There is a settings.template file in the assets/readable directory. This file can be copied over the settings.jar file to reset things like High Score.
 
-
Enjoy the game!
-