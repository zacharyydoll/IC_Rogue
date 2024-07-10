# ICROGUE

## General Description

ICRogue is a Rogue-like dungeon game in which the main character starts in the easiest room and makes his way to rooms of various difficulties every time he completes the necessary tasks. The game is composed of different types of enemies that need to be killed and objects that must be collected. Once all of the tasks in a room are completed, the player can move on to the next room. If all the rooms have been visited the player wins; however, if the player dies to a monster, then he loses.

## Map Description

Map2 is composed of 6 different rooms. The player starts in the main room and will make his way towards the Boss. The player needs to pick up the key guarded by the Dark Lord in order to open the locked room, in which he will need to successfully destroy the turret monsters to finally free his friend the King.

## Description of Items

- **Staff**: Lets the player throw fireballs. Unlimited amount.
- **Bow**: Lets the player throw arrows. 20 arrows maximum are given.
- **Cherry**: Increases the player’s life by 25HP and increases arrow fire-rate by 50%. Be careful not to use the cherry when the player has not yet lost lives. His energy level cannot exceed the maximum threshold and there will only be one cherry per level. The cherry must be used wisely. Step over it to pick it up.
- **Key**: Used to open both of the locked doors. The player must walk over the key in order for it to be picked up.

## Description of Actors

- **Player**: Starts with 100HP.
- **Dark Lord**: Moves randomly and throws fireballs in multiple directions at a time. Has a maximum HP of 200. Located in the key room and can only be harmed through fireballs or arrows.
- **Turrets**: Throw arrows in multiple directions. Situated in the locked chamber which can only be accessed with the key. Can be killed by being shot with a projectile or by being stepped over by the Player.
- **Friend**: To save your friend, all the tasks in the rooms must have been completed.

## Controls to Play the Game

- **Directional Arrows**: Move the player in the corresponding direction.
- **W**: Interact (open locked doors, pick up the staff and the bow).
- **X**: Launch fireballs or arrows.
- **S**: Switch between weapons once the staff and bow have been picked up.
- **R**: Reset the game.

## End of the Game

Once the Dark Lord and the turrets are killed and all the objects are collected, the game is finished and the player has won, and “WIN” will appear on the terminal. However, if the player is killed by one of the enemies, the game is over and the player lost. In this case, “GAME OVER” will appear on the terminal.

## Extensions

- Cherry that increases fire-rate and health points.
- Boss that moves randomly and shoots fireballs in random directions.
- Staff and Bow sprite for the player.
- Change of sprites (of weapons) when pressing S, or switches automatically when the player has no more arrows.
- Added 2 new Rooms.
- Added 1 Character (King.java, which extends the added class Ally.java, needed for future implementation of other allies).
- Added code to be able to differentiate arrows and fireballs shot by the player, with those shot by enemies, needed to prevent the player from harming himself.
