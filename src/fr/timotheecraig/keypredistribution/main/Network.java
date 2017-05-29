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
    private ArrayList<Key> keys; // This will be replaced with the main polynomials pool later on

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

    public void displayKeys() {
        if(this.keys != null) {
            for(int i = 0; i < this.keys.size(); i++) {
                System.out.println(this.keys.get(i));
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
                int randomEmissionRadius = ThreadLocalRandom.current().nextInt(30, 40 + 1);
                this.nodes.add(new Node((i+1) , nodeName+(i+1), new Coordinates(randomX, randomY), randomEmissionRadius, null));
            }
        }
        else {
            int lastNodeId = this.nodes.get(this.nodes.size() - 1).getId();
            for (int i = lastNodeId; i < (amount + lastNodeId); i++) {
                int randomX = ThreadLocalRandom.current().nextInt(0, 200 + 1);
                int randomY = ThreadLocalRandom.current().nextInt(0, 200 + 1);
                int randomEmissionRadius = ThreadLocalRandom.current().nextInt(30, 40 + 1);
                this.nodes.add(new Node((i+1), nodeName+(i+1), new Coordinates(randomX, randomY), randomEmissionRadius, null));
            }
        }
    }

    public void addAmountOfKeys(int amount, int keySize) {
        this.keys = this.keys == null ? new ArrayList<Key>() :this.keys;
        for(int i = 0; i < amount; i++) {
            this.keys.add(Key.createRandomKey(keySize));
        }
    }

    public static Network getDefault() {
        return new Network();
    }

    public double distanceBetween(Node nodeA, Node nodeB) {
        return Math.sqrt(Math.pow(nodeA.getCoordinates().getX() - nodeB.getCoordinates().getX(), 2)
                             + Math.pow(nodeA.getCoordinates().getY() - nodeB.getCoordinates().getY(), 2));
    }

    public void neighbourDiscovery() {
        for(int i = 0; i < this.nodes.size(); i++) {
            Node nodeToCompare = this.nodes.get(i);
            for(int j = 0; j < this.nodes.size(); j++) {
                if(i != j) {
                    Node potentialNeighbour =  this.nodes.get(j);
                    if(distanceBetween(nodeToCompare, potentialNeighbour) <= nodeToCompare.getEmissionRadius()) {
                        nodeToCompare.addNeighbour(potentialNeighbour);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        int nodesLen = this.nodes != null ? this.nodes.size() : 0;
        int keyPoolLen = this.mainPolynomialsPool != null ? this.mainPolynomialsPool.size() : 0;
        return this.name + " : " + nodesLen + " nodes, " + keyPoolLen + " keys";
    }

}
