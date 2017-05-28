package fr.timotheecraig.keypredistribution.main;

import fr.timotheecraig.keypredistribution.util.Key;
import sun.nio.ch.Net;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Network {

    private String name;
    private ArrayList<Node> nodes;
    private ArrayList<Key> mainKeyPool;

    // Accessors
    public String getName() {
        return name;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Key> getMainKeyPool() {
        return mainKeyPool;
    }

    // Constructors
    public Network(String networkName, ArrayList<Node> networkNodes, ArrayList<Key> mainKeyPool) {
        this.name = networkName;
        this.nodes = networkNodes;
        this.mainKeyPool = mainKeyPool;
    }

    public Network(String networkName) {
        this.name = networkName;
    }

    public Network() {
        this("Default Network", null, null); // Change null values later to some generated thingy
    }

    public static Network getDefault() {
        return new Network();
    }

    @Override
    public String toString() {
        int nodesLen = this.nodes != null ? this.nodes.size() : 0;
        int keyPoolLen = this.mainKeyPool != null ? this.mainKeyPool.size() : 0;
        return this.name + " : " + nodesLen + " nodes, " + keyPoolLen + " keys";
    }

}
