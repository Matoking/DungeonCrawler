DESCRIPTION
====
A simple dungeon crawler game where the player can explore a dungeon by controlling a player character. The game is presented as a 2D grid with different icons representing walls, the player character, etc.

FUNCTIONS
====
* Starting the game
  - when game is launched or when the player restarts the game
* Controlling the player character with arrow keys
* Rendering the current state of the game
  - player is displayed in the middle of the screen
* Enemies
  - wander around aimlessly when idle
  - if player comes near, enemy will start chasing the player
* Keys
  - player picks up keys placed around the map by walking over them
  - the game is won when all keys have been picked up
* Game log
  - displays messages related to game events, such as player attacking an enemy or picking up a key
* Apples
  - Apples that restore player's health
* Map generator
  - Generates a different map for each game
