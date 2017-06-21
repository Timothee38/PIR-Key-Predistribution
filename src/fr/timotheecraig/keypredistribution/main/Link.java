package fr.timotheecraig.keypredistribution.main;

import fr.timotheecraig.keypredistribution.util.Key;
import fr.timotheecraig.keypredistribution.util.Polynomial;
import fr.timotheecraig.keypredistribution.enums.LinkState;

import java.util.List;

/**
 * Created by timothee on 30/05/17.
 */
public class Link {

    private Node node1;
    private Node node2;
    private Polynomial polynomial;
    private int polynomialId;
    private Key key;
    private LinkState linkState;

    public Link(Node node1, Node node2, LinkState linkState) {
        this.node1 = node1;
        this.node2 = node2;
        this.linkState = linkState;
    }

    /**
     * Create a link between two nodes, link up by default.
     * @param node1 a node
     * @param node2 another node
     * @param polynomial linking both nodes
     */
    public Link(Node node1, Node node2, Polynomial polynomial) {
        this(node1, node2, LinkState.up);
        this.polynomial = polynomial;
    }

    /**
     * Create a link between two nodes, link up by default.
     * @param node1 a node
     * @param node2 another node
     * @param key linking both nodes
     */
    public Link(Node node1, Node node2, Key key) {
        this(node1, node2, LinkState.up);
        this.key = key;
    }

    public Link(Node n, Node neighbour, Key keyFromPolynomial, int commonId) {
        this(n, neighbour, keyFromPolynomial);
        this.polynomialId = commonId;
    }

    /**
     * Set link state to a new one.
     * @param linkState the link state
     */
    public void setLinkLinkState(LinkState linkState) {
        this.linkState = linkState;
    }

    public Key getKey() {
        return key;
    }

    public LinkState getLinkState() {
        return linkState;
    }

    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }

    public int getPolynomialId() {
        return polynomialId;
    }

    @Override
    public String toString() {
        return node1.getName() + " - " + node2.getName() + " : " + this.linkState.name();
    }

    public void setKey(Key key) {
        this.key = key;
    }
}
