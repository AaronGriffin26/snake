public class Game {
	//no static variables, all instances of object

	private int timer = 0;
	private snakeBody snake;
	private Bait bait;
	private playerScoreboard playerScore;
	private int width;
	private int height;
	private boolean gameOver = false;

	public Game(int width, int height) {								//constructor
		this.width = width;
		this.height = height;
		snake = new snakeBody();
		bait = new Bait(snake, width, height);
		playerScore = new playerScoreboard();  // created player score object instance to call methods created from the class
	}

	public void update() {
		if(gameOver)
			return;

		if(Input.leftIsHeld() && snake.previousMovement() != Direction.RIGHT)
			snake.setDirection(Direction.LEFT);
		if(Input.upIsHeld() && snake.previousMovement() != Direction.DOWN)
			snake.setDirection(Direction.UP);
		if(Input.rightIsHeld() && snake.previousMovement() != Direction.LEFT)
			snake.setDirection(Direction.RIGHT);
		if(Input.downIsHeld() && snake.previousMovement() != Direction.UP)
			snake.setDirection(Direction.DOWN);
		if(snake.checkBait(bait)) {
			Audio.playSound(Sounds.EAT);
			bait = new Bait(snake, width, height);
			playerScore.score += 100; // if the snake eats the bait then the score will add one hundred points to the current score
		}
		timer++;
		if(timer >= 10) {
			snake.move();
			if(snake.checkCollision(this)) {
				gameOver = true;
				Audio.playSound(Sounds.COLLIDE_SELF);
			}
			if(snake.checkOutOfBounds(this)) {
				gameOver = true;
				Audio.playSound(Sounds.COLLIDE_WALL);
			}
			timer = 0;
		}
	}

	public int getWidth() {   																		//function
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void render() {
		snake.render();
		bait.render();
		playerScore.render();  //called to display the score and current highscore when the game starts
		if (gameOver){
			//  if the game is over the check score method will check to see if the player score was higher than the high score
			playerScore.checkScore();
			// after the high score is executed a message will pop up in the middle of the screen giving user instuctions to play again or exit game
			Graphics.drawText(320, 360, "To play again press the space key \n" +
					"or press backspace to exit game");
		}
	}
}
