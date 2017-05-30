package fr.timotheecraig.keypredistribution.main;

import fr.timotheecraig.keypredistribution.enums.NodeState;
import fr.timotheecraig.keypredistribution.util.Key;
import fr.timotheecraig.keypredistribution.util.Coordinates;
import fr.timotheecraig.keypredistribution.util.Polynomial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Node {

    private int id;
    private String name;
    private Coordinates coordinates;
    private int emissionRadius; // max radius to which the node can find its neighbours
    private List<Polynomial> polynomials;
    private ArrayList<Node> neighbours;
    private ArrayList<Link> links;
    private NodeState nodeState;

    public Node(int id, String name, Coordinates coords, int emissionRadius, List<Polynomial> keys) {
        this.id = id;
        this.name = name;
        this.coordinates = coords;
        this.emissionRadius = emissionRadius;
        this.polynomials = keys;
        this.links = new ArrayList<Link>();
        this.nodeState = NodeState.waitingForInit;
    }

    /**
     * Get a node's identifier.
     * @return id of the node
     */
    public int getId() {
        return id;
    }

    /**
     * Get a node's full name.
     * @return name of the node
     */
    public String getName() {
        return name;
    }

    /**
     * Get the radius of a node.
     * @return emissionRadius of a node.
     */
    public int getEmissionRadius() {
        return emissionRadius;
    }

    /**
     * Get coordinates of a node
     * @return coordinates of a node
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Get the polynomials of a node.
     * @return polynomials of a node
     */
    public List<Polynomial> getPolynomials() { return polynomials; }

    /**
     * Set a node's coordinates.
     * @param x node's value on x-axis
     * @param y node's value on y-axis
     */
    public void setCoordinates(int x, int y) {
        int[] newCoords = {x, y};
        this.coordinates.setCoords(newCoords);
    }

    /**
     * Set a node's coordinates.
     * @param coordinates node's formatted coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Add a neighbour to a node.
     * @param node the neighbour to add
     */
    public void addNeighbour(Node node) {
        this.neighbours = this.neighbours == null ? new ArrayList<Node>() : this.neighbours;
        this.neighbours.add(node);
    }

    /**
     * Display the neighbours of a node.
     */
    public void displayNeighbours() {
        if(this.neighbours != null) {
            for(Node neighbour: this.neighbours) {
                System.out.println(neighbour.toString());
            }
        }
    }

    /**
     * Display polynomials of a node.
     */
    public void displayPolynomials() {
        if(this.polynomials != null) {
            /*for(Polynomial polynomial: this.polynomials) {
                System.out.println(polynomial.toString());
            }*/
            System.out.println(this+" "+this.polynomials);
        }
    }

    /**
     * Set the polynomials of a node
     * @param pol list of polynomials to copy
     */
    public void distributePolynomials(List<Polynomial> pol) {
        // this.polynomials = pol; -> Keeping this here, we must COPY pol to polynomials, not set it to "=" as it's modified later on
        this.polynomials = new ArrayList<Polynomial>(pol);
    }

    @Override
    public String toString() {
        int neighbourSize = this.neighbours != null ? this.neighbours.size() : 0;
        int polynomialPoolSize = this.polynomials != null ? this.polynomials.size() : 0;
        int linksSize = this.links != null ? this.links.size() : 0;
        return this.name + " : " + this.coordinates + ", radius: " + this.emissionRadius + "m, "
                + neighbourSize + " neighbours, " + polynomialPoolSize + " polynomials, " + linksSize + " links, state : " + this.nodeState;
    }


    public ArrayList<Link> compareNeighbours() {
        ArrayList<Link> links = new ArrayList<Link>();
        if(this.neighbours != null) {
            for (Node node : this.neighbours) {
                // check if node contains a value from this.polynomials.getIds -> todo method
                for (Polynomial pol : this.polynomials) {
                    if (node.getPolynomials().contains(pol)) { // If the neighbours polynomials array contains a polynomial of the node
                        Link link = new Link(this, node, pol);
                        if(!links.contains(link)) {
                            links.add(link);
                            this.links.add(link);
                        }
                    }
                }
            }
        }
        return links;
    }

    public void setState(NodeState state) {
        this.nodeState = state;
    }

    public NodeState getState() {
        return this.nodeState;
    }
}
