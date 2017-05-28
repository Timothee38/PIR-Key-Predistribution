package fr.timotheecraig.keypredistribution.main;

import fr.timotheecraig.keypredistribution.util.Key;
import fr.timotheecraig.keypredistribution.util.Coordinates;

import java.util.ArrayList;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Node {

    private String name;
    private Coordinates coordinates;
    private ArrayList<Key> keys;

    public Node(String name, Coordinates coords, ArrayList<Key> keys) {
        this.name = name;
        this.coordinates = coords;
        this.keys = keys;
    }

}