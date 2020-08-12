public class GameCoordinates{
    private int x;
    private int y;

    public int getX() {                                                     //functions to get x and y = snake length
        return x;
    }

    public int getY() {
        return y;
    }

    public GameCoordinates(int x, int y){
        this.x = x;                                                         //working w properties from obj being used
        this.y = y;
    }

    public boolean equals(GameCoordinates o){                                               //comparing simultaneously
        return this.x == o.x && this.y == o.y;
    }
}
