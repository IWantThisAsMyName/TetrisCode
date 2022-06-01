# Tetris
by: Camden Bartelt and Calvin Luo

![Pieces](https://o.remove.bg/downloads/6f508425-a86a-4755-864d-f2a3c9f467ca/pieces-removebg-preview.png)
## Controls
- Left arrow: move piece left
- Right arrow: move piece right
- Up arrow: rotate piece OR increase level
- Down arrow: soft drop piece OR decrease level
- Space bar: hard (instant) drop piece
- Esc: pause OR resume
- C: hold piece AND deploy held piece
- Enter: start game
## Most Basic Rules
- Pieces come from the top of the board
- Pieces can only rotate and move left, right, and down
- Game is over once pieces reach the top of the board (from the bottom)
- Can only remove pieces once all space in a line is filled
- More lines cleared = faster pace = more points
- Held pieces cannot be repeatedly deployed
### Scoring Rules
- 1 line cleared: 100 x lvl
- 2 lines cleared: 300 x lvl
- 3 lines cleared: 500 x lvl
- 4 lines cleared: 800 x lvl
>You also get points for soft and hard dropping!
- Soft: 1 point per cell covered
- Hard: 2 points per cell covered
## Menu Screen
![Menu Screen](https://gcdnb.pbrd.co/images/FmbZF6BkkA3Z.png?o=1)
This is what you first see when you run the project. Notice that there are two sections in the menu screen: mode and level (lvl). Mode is always set to A as we have yet to create a mode B, so you can ignore that section. Levels can be increased via up key and decreased via down key. The minimum level is 1 and maximum is 19. The higher the level, the faster the pieces drop (stronger gravity). However, you also get more points for clearing lines at a higher level. Once the level has been selected, press enter to begin the game! 
## The Game
![Tetris](https://user-images.githubusercontent.com/90801636/171360119-0d60ea42-df01-4d7c-8d1d-b6934057d76e.png)
Notice the three rectangular sections to the left of the game board.
 - The top of the three sections displays the held piece. You do not start with a held piece so that section is left blank in the beginning.
 - The middle of the three sections display your very next piece that will come from the top of the game board after the current piece lands and locks in.
 - The bottom of the three sections display the score.
Notice the rectangular section to the right of the game board.
 - displays the next five pieces (top-to-bottom order) after the very next piece, which is already displayed to the left of the game board.
### Ghost Pieces
![Ghost](https://user-images.githubusercontent.com/90801636/171367487-720aad22-05f1-462d-be03-d3477c48767a.png)
- Ghost pieces are basically guides for where the current piece wouuld end up if you hard drop in that instant.
- Take advantage of them, it's very useful.
### Piece Holding
There are some more intricate rules when it comes to the piece holding mechanism.
- By holding a piece, you are ALSO deploying the previously held piece (only if there is one).
- Pieces cannot be consecutively held/deployed.
- When initially holding the current piece, the previously held piece is deployed from the top of the screen.
