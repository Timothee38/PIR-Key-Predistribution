package fr.timotheecraig.keypredistribution.main;

import fr.timotheecraig.keypredistribution.enums.LinkState;
import fr.timotheecraig.keypredistribution.enums.NetworkType;
import fr.timotheecraig.keypredistribution.enums.NodeState;
import fr.timotheecraig.keypredistribution.util.Coordinates;
import fr.timotheecraig.keypredistribution.util.Key;
import fr.timotheecraig.keypredistribution.util.Polynomial;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Network {

    private String name;
    private ArrayList<Node> nodes;
    private HashMap<Integer, Polynomial> mainPolynomialsPool;
    private ArrayList<Key> keys; // This will be replaced with the main polynomials pool later on
    private ArrayList<Link> links;
    private NetworkType scheme;
    private double density;
    private int totalNumberOfLinks = 0;
    private int totalNumberOfSecuredLinks = 0;
    private int nodeEmissionRadius;
    private int size;
    private int degree;

    // Accessors
    public String getName() {
        return name;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public NetworkType getScheme() {
        return scheme;
    }

    public HashMap<Integer, Polynomial> getMainPolynomialsPool() {
        return mainPolynomialsPool;
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public int getTotalNumberOfLinks() {
        return totalNumberOfLinks;
    }

    public int getTotalNumberOfSecuredLinks() {
        return totalNumberOfSecuredLinks;
    }

    // Constructors

    /**
     * Create a network instance.
     * @param networkName the network name
     * @param networkNodes the nodes that constitute the network
     * @param mainPolynomialsPool the polynomials pool for this network
     */
    public Network(String networkName, ArrayList<Node> networkNodes, HashMap<Integer, Polynomial> mainPolynomialsPool) {
        this.name = networkName;
        this.nodes = networkNodes;
        this.mainPolynomialsPool = mainPolynomialsPool;
    }

    public Network(String networkName) {
        this.name = networkName;
    }

    public Network() {
        this("Default Network", null, null);
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
     * @param amount amount of nodes to add
     * @param emissionRadius the emission radius of the nodes
     * @param sizeOfGrid the size of one side of the area
     */
    public void addAmountOfNodes(int amount, int emissionRadius, int sizeOfGrid) {
        String nodeName = "node-";
        if (this.nodes == null) {
            this.nodes = new ArrayList<Node>();
            for (int i = 0; i < amount; i++) {
                int randomX = ThreadLocalRandom.current().nextInt(0, sizeOfGrid + 1);
                int randomY = ThreadLocalRandom.current().nextInt(0, sizeOfGrid + 1);
                this.nodes.add(new Node((i + 1), nodeName + (i + 1), new Coordinates(randomX, randomY), emissionRadius, null));
            }
        } else {
            int lastNodeId = this.nodes.get(this.nodes.size() - 1).getId();
            for (int i = lastNodeId; i < (amount + lastNodeId); i++) {
                int randomX = ThreadLocalRandom.current().nextInt(0, sizeOfGrid + 1);
                int randomY = ThreadLocalRandom.current().nextInt(0, sizeOfGrid + 1);
                this.nodes.add(new Node((i + 1), nodeName + (i + 1), new Coordinates(randomX, randomY), emissionRadius, null));
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
            this.mainPolynomialsPool = new HashMap<Integer, Polynomial>();
            for (int i = 0; i < amount; i++) {
                this.mainPolynomialsPool.put(i, Polynomial.generatePolynomial(maxPolynomialOrder, biggestCoef));
            }
        }

    }

    /**
     * Predistribute a certain amount of keys to each node in the network.
     * @param amount the amount of keys per node
     */
    public void predistributeKeys(int amount) {
        ArrayList<Key> copy = this.keys;
        if(this.keys != null) {
            amount = amount < copy.size() ? amount : copy.size();
            for(Node node: this.nodes) {
                Collections.shuffle(copy);//-> lol this doesnt work it seems
                List<Key> subList = copy.subList(0, amount);
                //System.out.println(subList);
                node.distributeKeys(subList);
            }
        }
    }

    /**
     * This method pre-distributes a set of polynomials to each node in the network.
     *
     * @param amountOfPolynomialsToDistribute This is the amount of polynomials to distribute per node.
     */
    public void predistributePolynomials(int amountOfPolynomialsToDistribute) {
        if(this.mainPolynomialsPool != null) {
            amountOfPolynomialsToDistribute =
                    amountOfPolynomialsToDistribute < this.mainPolynomialsPool.size() ? amountOfPolynomialsToDistribute : this.mainPolynomialsPool.size();

            for (Node node: this.nodes) {

                ArrayList<Integer> hashMapKeys = new ArrayList<Integer>(this.mainPolynomialsPool.keySet());
                Collections.shuffle(hashMapKeys);

                for (Integer i: hashMapKeys.subList(0, amountOfPolynomialsToDistribute)) {
                    node.distributePolynomial(i, new Polynomial(this.mainPolynomialsPool.get(i)));
                }

                node.applyIdToAllPolynomials();
//                node.displayPolynomials();
            }

        }
    }

    public void setNodesInitialised() {
        if (this.nodes != null) {
            for (Node node : this.nodes) {
                node.setState(NodeState.initialised);
            }
        }
    }

    /**
     * Calculate the density of a network.
     * @param degree the average degree
     * @param nodeEmissionRadius the emission radius of each node
     * @return the calculated density as an amountOfNodes/m²
     */
    private double calculateDensity(int degree, int nodeEmissionRadius) {
        return degree / (Math.PI * (Math.pow(nodeEmissionRadius, 2)));
    }

    /**
     * Get a network by its average degree, generating the proper amount of nodes from this degree.
     * @param degree = pi**(nodeEmissionRadius²) * density
     * @param size the size in meter of a side of the square to generate
     * @param nodeEmissionRadius the emission radius of a node
     * @param scheme the scheme employed for the network
     * @return a network instance
     */
    public static Network getByDegree(int degree, int size, int nodeEmissionRadius, NetworkType scheme) {
        Network network = new Network("My WSN");
        network.density = network.calculateDensity(degree, nodeEmissionRadius);
        network.size = size;
        network.degree = degree;
        network.nodeEmissionRadius = nodeEmissionRadius;
        int amountOfNodesToGenerate = (int) (network.density * Math.pow(size, 2));
        network.addAmountOfNodes(amountOfNodesToGenerate, nodeEmissionRadius, size);
        network.scheme = scheme;
        return network;
    }

    /**
     * This function add links to the link array on a network, checking every node neighbours and then creating
     * the links.
     */
    public void createLinks_basic() {
        if(this.scheme == NetworkType.basicScheme) {
            if(this.nodes != null) {
                for (Node n: this.nodes){
                    ArrayList<Node> nNeighbours = n.getNeighbours();
                    if(nNeighbours != null) {
                        for(Node node: nNeighbours) {
                            if(!node.isVisited) { // if the neighbour of n wasn't visited
                                this.totalNumberOfLinks++;
                                Link l = new Link(node, n, LinkState.down);
                                this.links = this.links == null ? new ArrayList<Link>() : this.links;
                                // perform temporary copies
                                ArrayList<Key> neighbourKeys = new ArrayList<Key>(node.getKeys());
                                ArrayList<Key> nodeKeys = new ArrayList<Key>(n.getKeys());
                                // Get common elements between both lists
                                nodeKeys.retainAll(neighbourKeys);
                                if(nodeKeys.size() > 0) { // If they share a common key
                                    this.totalNumberOfSecuredLinks++;
                                    l.setLinkLinkState(LinkState.up);
                                    l.setKey(nodeKeys.get(0));
                                }
                                this.links.add(l);
                            }
                        }
                    }
                    n.isVisited = true;
                }
            }
        }
        else {
            System.out.println("The network isn't using the basic scheme.");
        }
    }

    public void createLinks_polynomials() {
        this.links = this.links == null ? new ArrayList<Link>() : this.links;
        if(this.scheme == NetworkType.polynomialScheme) {
            if(this.nodes != null) {
                for(Node n : this.nodes) {
                    ArrayList<Node> nNeighbours = n.getNeighbours();
                    if(nNeighbours != null) {
                        for (Node neighbour: nNeighbours) {
                            if(!neighbour.isVisited) {
                                this.totalNumberOfLinks++;

                                HashMap<Integer, Polynomial>  neighbourPolynomials = new HashMap<Integer, Polynomial>(neighbour.getPolynomials());
                                HashMap<Integer, Polynomial>  nodePolynomials = new HashMap<Integer, Polynomial>(n.getPolynomials());

                                // Check if they have some polynomials in common with the same polynomial Id
                                // If they do, check if applying the id of the nodes to the polynomials make them equal
                                // Example : node-1, node-2, polynomial in common: [1,2,3]
                                // f(1,y) = 1 + 2*(1 + y) + 3*1*y = 3 + 2y + 3y
                                // f(2,y) = 1 + 2*(2 + y) + 3*2*y = 5 + 2y + 6y
                                // f(1,2) = f(2,1)
                                // => 3 + 2*2 + 3*2 = 5 + 2*1 + 6*1
                                // => 13 = 13
                                // create a key for the link from the polynomial -> From the computed result

                                List<Integer> commonId = Polynomial.getCommonIds(nodePolynomials, neighbourPolynomials);

                                if(commonId.size() >= 1) {
//                                    System.out.println("Common Ids :");
//                                    for (Integer i: commonId) {
//                                        System.out.println(neighbourPolynomials + " " + nodePolynomials);
//                                        System.out.println(i + " : " +neighbourPolynomials.get(i) + " | " + nodePolynomials.get(i));
//                                    }
                                    Polynomial nodePol = new Polynomial(nodePolynomials.get(commonId.get(0)));
                                    Polynomial neighbourPol = new Polynomial(neighbourPolynomials.get(commonId.get(0)));
                                    int nodeComputedValue = nodePol.computeValue(neighbour.getId());
                                    int neighbourComputedValue = neighbourPol.computeValue(n.getId());
//                                    System.out.println(n.getId() + ": " + nodePol + " | "+ neighbour.getId() + ": " + neighbourPol);
//                                    System.out.println(nodeComputedValue + " = " + neighbourComputedValue + " ?");
                                    if(nodeComputedValue == neighbourComputedValue) {
                                        this.totalNumberOfSecuredLinks++;
                                        this.links.add(new Link(n, neighbour, Key.createKeyFromPolynomial(nodeComputedValue), commonId.get(0)));
                                    }
                                }
                            }
                        }
                        n.isVisited = true;
                    }
                }
            }
        }
        else {
            System.out.println("The network isn't using the polynomial scheme.");
        }
    }

    @Override
    public String toString() {
        int nodesLen = this.nodes != null ? this.nodes.size() : 0;
        if(this.scheme == NetworkType.basicScheme) {
            int keyPoolLen = this.keys != null ? this.keys.size() : 0;
            return this.name + " : " + nodesLen + " nodes, " + keyPoolLen + " keys";
        }
        else if (this.scheme == NetworkType.polynomialScheme) {
            int polPoolLen = this.mainPolynomialsPool != null ? this.mainPolynomialsPool.size() : 0;
            return this.name + " : " + nodesLen + " nodes, " + polPoolLen + " polynomials";
        }
        return this.name + " : " + nodesLen + " nodes.";
    }

    public int getSize() {
        return this.size;
    }

    public int getEmissionRadius() {
        return this.nodeEmissionRadius;
    }

    public int getDegree() {
        return this.degree;
    }
}
