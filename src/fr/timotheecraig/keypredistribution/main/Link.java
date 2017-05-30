package fr.timotheecraig.keypredistribution.main;

import fr.timotheecraig.keypredistribution.util.Polynomial;
import fr.timotheecraig.keypredistribution.enums.State;

/**
 * Created by timothee on 30/05/17.
 */
public class Link {

    private Node node1;
    private Node node2;
    private Polynomial polynomial;
    private State linkState;

    public Link(Node node1, Node node2, Polynomial polynomial, State state) {
        this.node1 = node1;
        this.node2 = node2;
        this.polynomial = polynomial;
        this.linkState = state;
    }

    /**
     * Create a link between two nodes, link up by default.
     * @param node1 a node
     * @param node2 another node
     * @param polynomial linking both nodes
     */
    public Link(Node node1, Node node2, Polynomial polynomial) {
        this(node1, node2, polynomial, State.up);
    }

    /**
     * Set link state to a new one.
     * @param linkState the link state
     */
    public void setLinkState(State linkState) {
        this.linkState = linkState;
    }

    @Override
    public String toString() {
        return node1.getName() + " - " + node2.getName() + " : " + this.linkState.name();
    }

}
