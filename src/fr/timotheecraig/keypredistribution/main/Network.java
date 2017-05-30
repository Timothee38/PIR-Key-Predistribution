package fr.timotheecraig.keypredistribution.main;

import fr.timotheecraig.keypredistribution.enums.NodeState;
import fr.timotheecraig.keypredistribution.util.Coordinates;
import fr.timotheecraig.keypredistribution.util.Key;
import fr.timotheecraig.keypredistribution.util.Polynomial;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Network {

    private String name;
    private ArrayList<Node> nodes;
    private ArrayList<Polynomial> mainPolynomialsPool;
    private ArrayList<Key> keys; // This will be replaced with the main polynomials pool later on
    private ArrayList<Link> links;

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

    /**
     * Display all the nodes from the network, by state.
     * @param state the state of the nodes to display
     */
    public void displayNodes(NodeState state) {
        if (this.nodes != null) {
            for (int i = 0; i < this.nodes.size(); i++) {
                Node nodeToDisplay = this.nodes.get(i);
                if(nodeToDisplay.getState() == state) {
                    System.out.println(nodeToDisplay);
                }
            }
        } else {
            System.out.println("No nodes "+ state.name() + " for this network.");
        }
    }

    /**
     * Display all the nodes from the network.
     */
    public void displayNodes() {
        if (this.nodes != null) {
            for (int i = 0; i < this.nodes.size(); i++) {
                System.out.println(this.nodes.get(i));
            }
        } else {
            System.out.println("No nodes set for this network.");
        }
    }

    /**
     * Display all the links from the network.
     */
    public void displayLinks() {
        if (this.links != null) {
            for (int i = 0; i < this.links.size(); i++) {
                System.out.println(this.links.get(i));
            }
        } else {
            System.out.println("No links set for this network.");
        }
    }

    /**
     * Display all the keys from the network.
     */
    public void displayKeys() {
        if (this.keys != null) {
            for (int i = 0; i < this.keys.size(); i++) {
                System.out.println(this.keys.get(i));
            }
        } else {
            System.out.println("No keys set for this network.");
        }
    }

    /**
     * Display all the polynomials from the network.
     */
    public void displayPolynomialPool() {
        if (this.mainPolynomialsPool != null) {
            for (int i = 0; i < this.mainPolynomialsPool.size(); i++) {
                System.out.println(this.mainPolynomialsPool.get(i));
            }
        } else {
            System.out.println("No polynomials set for this network.");
        }
    }

    /**
     * Add nodes to the network.
     *
     * @param amount amount of nodes to add
     */
    public void addAmountOfNodes(int amount) {
        String nodeName = "node-";
        if (this.nodes == null) {
            this.nodes = new ArrayList<Node>();
            for (int i = 0; i < amount; i++) {
                int randomX = ThreadLocalRandom.current().nextInt(0, 200 + 1);
                int randomY = ThreadLocalRandom.current().nextInt(0, 200 + 1);
                int randomEmissionRadius = ThreadLocalRandom.current().nextInt(30, 40 + 1);
                this.nodes.add(new Node((i + 1), nodeName + (i + 1), new Coordinates(randomX, randomY), randomEmissionRadius, null));
            }
        } else {
            int lastNodeId = this.nodes.get(this.nodes.size() - 1).getId();
            for (int i = lastNodeId; i < (amount + lastNodeId); i++) {
                int randomX = ThreadLocalRandom.current().nextInt(0, 200 + 1);
                int randomY = ThreadLocalRandom.current().nextInt(0, 200 + 1);
                int randomEmissionRadius = ThreadLocalRandom.current().nextInt(30, 40 + 1);
                this.nodes.add(new Node((i + 1), nodeName + (i + 1), new Coordinates(randomX, randomY), randomEmissionRadius, null));
            }
        }
    }

    /**
     * Add keys to the network.
     *
     * @param amount  amount of keys to add
     * @param keySize amount of characters to define the key size
     */
    public void addAmountOfKeys(int amount, int keySize) {
        this.keys = this.keys == null ? new ArrayList<Key>() : this.keys;
        for (int i = 0; i < amount; i++) {
            this.keys.add(Key.createRandomKey(keySize));
        }
    }

    /**
     * Get default network setup
     *
     * @return a default network by the name "default network"
     */
    public static Network getDefault() {
        return new Network();
    }

    /**
     * Calculate the distance between two nodes.
     *
     * @param nodeA A node of the network
     * @param nodeB Another node of the network
     * @return the distance between two nodes.
     */
    public double distanceBetween(Node nodeA, Node nodeB) {
        return Math.sqrt(Math.pow(nodeA.getCoordinates().getX() - nodeB.getCoordinates().getX(), 2)
                + Math.pow(nodeA.getCoordinates().getY() - nodeB.getCoordinates().getY(), 2));
    }

    /**
     * Compares the distance between each node and set them as neighbours if they are in the node radius.
     */
    public void neighbourDiscovery() {
        for (int i = 0; i < this.nodes.size(); i++) {
            Node nodeToCompare = this.nodes.get(i);
            nodeToCompare.setState(NodeState.deployed);
            for (int j = 0; j < this.nodes.size(); j++) {
                if (i != j) {
                    Node potentialNeighbour = this.nodes.get(j);
                    if (distanceBetween(nodeToCompare, potentialNeighbour) <= nodeToCompare.getEmissionRadius()) {
                        nodeToCompare.addNeighbour(potentialNeighbour);
                    }
                }
            }
        }
    }

    /**
     * This method is used to generate a polynomial master pool for the network.
     *
     * @param amount             Amount of polynomials to generate
     * @param maxPolynomialOrder The maximum order for all polynomials
     * @param biggestCoef        The maximum/minimum coefficient integer
     */
    public void generatePolynomialPool(int amount, int maxPolynomialOrder, int biggestCoef) {
        if (this.mainPolynomialsPool == null) {
            this.mainPolynomialsPool = new ArrayList<Polynomial>();
            for (int i = 0; i < amount; i++) {
                int polynomialSize = (int) (Math.random() * maxPolynomialOrder + 1);
                int coefs[] = new int[polynomialSize];
                for (int j = 0; j < polynomialSize; j++) {
                    coefs[j] = ThreadLocalRandom.current().nextInt(-biggestCoef, biggestCoef + 1);
                }
                this.mainPolynomialsPool.add(new Polynomial(i + 1, coefs));
            }
        } else {
            int lastPolynomialId = this.mainPolynomialsPool.get(this.mainPolynomialsPool.size() - 1).getIdentifier();
            for (int i = lastPolynomialId; i < (lastPolynomialId + amount); i++) {
                int polynomialSize = (int) (Math.random() * maxPolynomialOrder + 1);
                int coefs[] = new int[polynomialSize];
                for (int j = 0; j < polynomialSize; j++) {
                    coefs[j] = ThreadLocalRandom.current().nextInt(-biggestCoef, biggestCoef + 1);
                }
                this.mainPolynomialsPool.add(new Polynomial(i + 1, coefs));
            }
        }

    }

    /**
     * This method pre-distributes a set of polynomials to each node in the network.
     *
     * @param amountOfPolynomialsToDistribute This is the amount of polynomials to distribute per node.
     */
    public void predistributePolynomials(int amountOfPolynomialsToDistribute) {
        ArrayList<Polynomial> copy = this.mainPolynomialsPool;
        if (this.mainPolynomialsPool != null) {
            amountOfPolynomialsToDistribute =
                    amountOfPolynomialsToDistribute <= this.mainPolynomialsPool.size() ?
                            amountOfPolynomialsToDistribute : this.mainPolynomialsPool.size();
            for (Node node : this.nodes) {
                Collections.shuffle(copy);//-> lol this doesnt work it seems
                List<Polynomial> subList = copy.subList(0, amountOfPolynomialsToDistribute);
                //System.out.println(subList);
                node.distributePolynomials(subList);
            }
        }
    }

    @Override
    public String toString() {
        int nodesLen = this.nodes != null ? this.nodes.size() : 0;
        int keyPoolLen = this.mainPolynomialsPool != null ? this.mainPolynomialsPool.size() : 0;
        return this.name + " : " + nodesLen + " nodes, " + keyPoolLen + " keys";
    }

    /**
     * This method creates paths between nodes.
     */
    public void createPaths() {
        this.links = this.links == null ? this.links = new ArrayList<Link>() : this.links;
        for (Node node : this.nodes) {
            this.links.addAll(node.compareNeighbours());
        }
    }

    public void setNodesInitialised() {
        if (this.nodes != null) {
            for (Node node : this.nodes) {
                node.setState(NodeState.initialised);
            }
        }
    }
}
