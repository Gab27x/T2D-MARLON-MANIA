# T2D-MARLON-MANIA
**Marlon Mania** is a new version of a game known as *Pipe Mania*, developed as the integrative task for the course "Computación y Estructuras Discretas I" at the University of Icesi, Colombia.The goal of the project was to implement different versions of a graph and usage of graph algorithms through the development of a game.


## Table of Contents

1. [Team Members and Professor](#team-members-and-professor)
2. [Technologies and Concepts Used](#technologies-and-concepts-used)
3. [Game instructions](#game-instructions)
4. [Documentation](#documentation)
5. [View](#view)

##  Team Members and Professor

### Group

| Name | Student Code |
| ----------- | ----------- |
| [Gabriel Escobar](https://github.com/Gab27x) | A00399291 |
| [Luis Manuel Rojas](https://github.com/Lrojas898) | A00399289 |
| [Vanessa Sánchez](https://github.com/VaSaMo) | A00397949 |

### Professor

Marlon Gomez Victoria, Ph.D.
- Professor of the Science and Technology Department at ICESI University.

## Technologies and Concepts Used

### Technologies
- Java 21 (Logic): Used for the project's logic.
- JavaFX 21 (UI): Used for creating the user interface.
- Maven (Dependency management): Used for managing project dependencies.
- JUnit 5 (Testing): Used for writing and running tests.

### Concepts
For this integrative task, the main data structure used was a graph in 2 different implementations and along 2 different of its algorithms:

*Graph Implementations*
- Adajacency List
- Adjacency Matrix

*Graph Algorithms*
- DFS (Depth-first search) algorithm
- Dijkstra´s algorithm

## Game Instructions

*Marlon Mania* is a game in which players will have to go deep into the sewage system to solve a massive problem caused by a huge earthquake. This earthquake has destroyed some crucial parts of the sewage system, leaving most of the population without access to water. To solve this problem the player must be able to connect two sets of pipes that were disconnected due to the earthquake. The starting point will be known as the source (represented on the board game as an `F`) and the arrival point will be known as the drainage (represented on the board game as a `D`). 

The idea is that the player achieves this connection using the least amount of pipes and for the most serious damage, the player must place the pipes so that the water takes the shortest time to arrive. In order to do this, the player is able to place 3 different types of pipes: 

1. *Horizontal Pipe (`=`):*
   Allows water to flow form right to left or vice versa
2. *Vertical Pipe (`||`):*
   Allows water to flow up and down or vice versa
3. *Circular Pipe (`o`):*
   Allows to change the flow of water from up or down to left or right.

Players must know that the game calculates the score based on how effective the player's solution is.
At the beggining of the game they can choose the level difficulty (which can be `Easy` or `Hard`), and the graph implementation that they wish to use (which can be `List` or `Matrix`). 

## Documentation

Inside the project structure, you will find a folder called `Docs` that contains the project´s documentation, including:
- Engineering Method.
- Graph Structure TAD.
- Test Documentation.

## View



