package fr.timotheecraig.keypredistribution.main;

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

    public int getId() {
        return id;
    }

    public int getEmissionRadius() {
        return emissionRadius;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public List<Polynomial> getPolynomials() { return polynomials; }

    public void setCoordinates(int x, int y) {
        int[] newCoords = {x, y};
        this.coordinates.setCoords(newCoords);
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void addNeighbour(Node node) {
        this.neighbours = this.neighbours == null ? new ArrayList<Node>() : this.neighbours;
        this.neighbours.add(node);
    }

    public Node(int id, String name, Coordinates coords, int emissionRadius, List<Polynomial> keys) {
        this.id = id;
        this.name = name;
        this.coordinates = coords;
        this.emissionRadius = emissionRadius;
        this.polynomials = keys;
    }

    public void displayNeighbours() {
        if(this.neighbours != null) {
            for(Node neighbour: this.neighbours) {
                System.out.println(neighbour.toString());
            }
        }
    }

    public void displayPolynomials() {
        if(this.polynomials != null) {
            /*for(Polynomial polynomial: this.polynomials) {
                System.out.println(polynomial.toString());
            }*/
            System.out.println(this+" "+this.polynomials);
        }
    }

    public void distributePolynomials(List<Polynomial> pol) {
        // this.polynomials = pol; -> Keeping this here, we must COPY pol to polynomials, not set it to "=" as it's modified later on
        this.polynomials = new ArrayList<Polynomial>(pol);
    }

    @Override
    public String toString() {
        int neighbourSize = this.neighbours != null ? this.neighbours.size() : 0;
        int polynomialPoolSize = this.polynomials != null ? this.polynomials.size() : 0;
        return this.name + " : " + this.coordinates + ", radius: " + this.emissionRadius + "m, " + neighbourSize + " neighbours, " + polynomialPoolSize + " polynomials.";
    }


}
