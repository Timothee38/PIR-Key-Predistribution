package fr.timotheecraig.keypredistribution.main;

import fr.timotheecraig.keypredistribution.util.Key;
import fr.timotheecraig.keypredistribution.util.Coordinates;

import java.util.ArrayList;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Node {

    private int id;
    private String name;
    private Coordinates coordinates;
    private ArrayList<Key> keys;

    public int getId() {
        return id;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Node(int id, String name, Coordinates coords, ArrayList<Key> keys) {
        this.id = id;
        this.name = name;
        this.coordinates = coords;
        this.keys = keys;
    }

    @Override
    public String toString() {
        return this.name + " : " + this.coordinates;
    }

}
