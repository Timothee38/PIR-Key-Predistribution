package fr.timotheecraig.keypredistribution.main;

import fr.timotheecraig.keypredistribution.util.Coordinates;
import fr.timotheecraig.keypredistribution.util.Key;
import fr.timotheecraig.keypredistribution.util.Polynomial;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Network {

    private String name;
    private ArrayList<Node> nodes;
    private ArrayList<Polynomial> mainPolynomialsPool;

    // Accessors
    public String getName() {
        return name;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Polynomial> getMainPolynomialsPool() {
        return mainPolynomialsPool;
    }

    // Constructors
    public Network(String networkName, ArrayList<Node> networkNodes, ArrayList<Polynomial> mainPolynomialsPool) {
        this.name = networkName;
        this.nodes = networkNodes;
        this.mainPolynomialsPool = mainPolynomialsPool;
    }

    public Network(String networkName) {
        this.name = networkName;
    }

    public Network() {
        this("Default Network", null, null);
        this.addAmountOfNodes(45);
        this.addAmountOfNodes(5); // here for debug
    }

    public void displayNodes() {
        if(this.nodes != null) {
            for(int i = 0; i < this.nodes.size(); i++) {
                System.out.println(this.nodes.get(i));
            }
        }
        else {
            System.out.println("No nodes set for this network.");
        }
    }

    public void addAmountOfNodes(int amount) {
        String nodeName = "node-";
        if(this.nodes == null) {
            this.nodes = new ArrayList<Node>();
            for(int i = 0; i < amount; i++) {
                int randomX = ThreadLocalRandom.current().nextInt(0, 200 + 1);
                int randomY = ThreadLocalRandom.current().nextInt(0, 200 + 1);
                this.nodes.add(new Node((i+1) , nodeName+(i+1), new Coordinates(randomX, randomY), null));
            }
        }
        else {
            int lastNodeId = this.nodes.get(this.nodes.size() - 1).getId();
            for (int i = lastNodeId; i < (amount + lastNodeId); i++) {
                int randomX = ThreadLocalRandom.current().nextInt(0, 200 + 1);
                int randomY = ThreadLocalRandom.current().nextInt(0, 200 + 1);
                this.nodes.add(new Node((i+1), nodeName+(i+1), new Coordinates(randomX, randomY), null));
            }
        }
    }

    public static Network getDefault() {
        return new Network();
    }

    @Override
    public String toString() {
        int nodesLen = this.nodes != null ? this.nodes.size() : 0;
        int keyPoolLen = this.mainPolynomialsPool != null ? this.mainPolynomialsPool.size() : 0;
        return this.name + " : " + nodesLen + " nodes, " + keyPoolLen + " keys";
    }

}
