import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class snakeBody {
	private int snakeBody = 3; //default length of the snake h+b+b
	private Direction direction = Direction.NONE; //class variable
	private ArrayList<GameCoordinates> body = new ArrayList<>();

	public snakeBody() {																	//default position of snake
		body.add(new GameCoordinates(100, 100));										//constructor
		body.add(new GameCoordinates(75, 100));
		body.add(new GameCoordinates(50, 100));
	}

	/**
	 * Returns the direction it moved in based on the previous segment.
	 * @return Direction snake has traveled last time
	 */
	public Direction previousMovement() {														//method
		GameCoordinates head = body.get(0);
		GameCoordinates previous = body.get(1);
		if(head.getX() == previous.getX() - 25)
			return Direction.LEFT;
		if(head.getY() == previous.getY() - 25)
			return Direction.DOWN;
		if(head.getY() == previous.getY() + 25)
			return Direction.UP;
		return Direction.RIGHT;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}         //method

	public List<GameCoordinates> getBody(){												//return all points of snake
		 List<GameCoordinates> r = Collections.unmodifiableList(body);					//method
		 return r;
	}

	public void render() {											//render method created to draw head, body on screen
		String imagePath = "";										//empty string for png file
		switch(direction) {											//each direction will draw head based upon direction
			case LEFT:
				imagePath = "leftmouth.png";
				break;
			case DOWN:
				imagePath = "downmouth.png";
				break;
			case UP:
				imagePath = "upmouth.png";
				break;
			default:
				imagePath = "rightmouth.png";
				break;
		}
		Graphics.drawTexture(body.get(0), imagePath);
		for (int i = 1; i < snakeBody; i++) {
			GameCoordinates point = body.get(i);
			Graphics.drawTexture(point, "snakebody.png");
		}
	}

	public boolean checkBait(Bait bait) {   									//index 0: snake head meets bait_x & y
		if (body.get(0).equals(bait.getCoordinate())) {
			snakeBody++;
			return true;																				//(comparison)
		}
		return false;
	}

	public boolean checkCollision(Game game) {							//method to check if head collides with body
		GameCoordinates head = body.get(0);
		for (int c = 1; c < snakeBody; c++) {
			GameCoordinates bodyPart = body.get(c);
			if (bodyPart.equals(head)){
				return true;
			}
		}
		return false;
	}

	public boolean checkOutOfBounds(Game game) {							//method to check if head collides with wall
		GameCoordinates head = body.get(0);
		return (head.getX() > game.getWidth() || head.getX() < 0) || (head.getY() > game.getHeight()
				|| head.getY() < 0);
	}

	public void move() {											//move method needs to be called once timer started
		GameCoordinates head = body.get(0);							//snake head will move w/ body as it gets longer
		int x = head.getX();										//head will shift to index 1
		int y = head.getY();

		switch(direction) {
			case RIGHT:
				x += 25;
				break;
			case LEFT:
				x -= 25;
				break;
			case UP:
				y += 25;
				break;
			case DOWN:
				y -= 25;
				break;
			default:
				return;
		}
		body.add(0, new GameCoordinates(x, y));

		if (body.size() > 1000) {
			body.remove(1000);
		}
	}
}