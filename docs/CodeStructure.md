The game's codebase is divided to four packages:
* dungeoncrawler.state
* dungeoncrawler.state.entities
* dungeoncrawler.generator
* dungeoncrawler.ui

dungeoncrawler.state:
  - GameState
    
    Contains the game's state (game map, entities, player), as well as classes responsible for pathfinding, map generation and controls. 
    GameState is also responsible for restarting the game's state and reacting to player's moves.
    
  - GameMap
  
    Contains the game's map, as well as all entities inside it, apart from the player.
    
  - Tile
  
    A single tile in GameMap, can block entity/player movement or not.
      
  - TileType
  
    Enum containing all different types of tiles.
      
  - Pathfinding
  
    Responsible for performing pathfinding and returning the results in different forms (eg. amount of steps, next step to take).
    
  - GameLog
  
    Keeps track of gameplay messages, such as when player gets hit, picks up an item, etc.
    
  - GameMessages
  
    A static class that provides GameLog with different gameplay-related messages.
    
  - Player
  
    The player class, containing player's position, health and remaining keys.
      
  - Entity
  
    The entity interface, which provides the framework for actual entities. Entities can block other entities, react to being touched
    by the player, perform a move every game turn and provide an image name which is rendered by the game's UI.
    
  - Coordinate
  
    A convenience class that allows coordinates to be stored in a single class and provides an easy way to get new coordinates
    (eg. player moves to right from position Coordinate(5,8) -> Coordinate(6,8))
    
  - Direction
  
    Enum containing all directions player and entities can move into (UP, RIGHT, DOWN, LEFT).
      
dungeoncrawler.state.entities:
  - Skeleton
  
    Wanders around when not chasing a player. Starts chasing the player when comes into view and attacks the player when player
    is right next to the skeleton.
    
  - Key
  
    Keys player needs to pick up in order to win the game.
    
  - Apple
  
    Apples player can pick up to restore health.

dungeoncrawler.generator:
  - MapGenerator
    
    Generates a map and loads the generated map layout, entities into GameMap and as places player into the starting position.
    
  - Room
  
    A generated room containing the room's position, width, height and floor tile type. Generated rooms are later loaded into GameMap
    as Tiles.
    
  - Corridor
  
    Corridors connect the rooms and other corridors together and have a width of one tile.
      
dungeoncrawler.ui:
  - GameFrame
  
    The main UI element, contains all of the UI elements as well as Controls.
    
  - Controls
  
    Listens for player's key inputs and reacts accordingly by calling different methods in GameState.
    
  - LogPanel
  
    Displays game log messages tracked by GameLog.
    
  - MapPanel
  
    Displays the player's view using GameMap.
    
  - Sprite
  
    Contains constants used for rendering.
    
  - SpriteCache
  
    Responsible for loading images and rescaling them if necessary.
    
  - StatPanel
  
    Displays player's stats, or just health, really.