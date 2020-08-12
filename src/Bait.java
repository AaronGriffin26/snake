import java.awt.*;
import java.util.List;
import java.util.Random;

public class Bait {
     private GameCoordinates gameCoordinates;                                                            //obj of class
     public Bait(snakeBody snake, int width, int height) { //constructor
        Random rand = new Random();
        int x = rand.nextInt(25) * 25;                       //variables to the store the random bait_x & bait_y
        int y = rand.nextInt(25) * 25;
        gameCoordinates = new GameCoordinates(x, y);
        while (!validatePoint(snake)) {
            x = rand.nextInt(25) * 25;
            y = rand.nextInt(25) * 25;
            gameCoordinates = new GameCoordinates(x, y); // can access but cant set
        }
    }

    private boolean validatePoint(snakeBody body){                  //compares x/y right away//don't place bait on snake
        List<GameCoordinates> bodyParts = body.getBody();
        for(int i = 0; i < bodyParts.size(); i++){
            GameCoordinates bodyPart = bodyParts.get(i);
            if(bodyPart.equals(gameCoordinates)){
                return false;
            }
        }
        return true;
    }

    public GameCoordinates getCoordinate(){
        return gameCoordinates;
    }

    public void render(){
        Graphics.drawTexture(gameCoordinates, "bait.png");
    }
}
