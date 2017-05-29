package fr.timotheecraig.keypredistribution.util;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Coordinates {

    private int x;
    private int y;

    public Coordinates() {
        this(0,0);
    }

    public Coordinates(int x, int y) {
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int[] getFullCoords() {
        int[] ret = {this.x, this.y};
        return ret;
    }

    public void setCoords(int[] coords) {
        this.x = coords[0];
        this.y = coords[1];
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

}
