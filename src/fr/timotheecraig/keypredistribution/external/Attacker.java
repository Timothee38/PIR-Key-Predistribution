package fr.timotheecraig.keypredistribution.external;

import fr.timotheecraig.keypredistribution.enums.LinkState;
import fr.timotheecraig.keypredistribution.enums.NetworkType;
import fr.timotheecraig.keypredistribution.enums.NodeState;
import fr.timotheecraig.keypredistribution.main.Link;
import fr.timotheecraig.keypredistribution.main.Network;
import fr.timotheecraig.keypredistribution.main.Node;
import fr.timotheecraig.keypredistribution.util.Key;
import fr.timotheecraig.keypredistribution.util.Polynomial;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by timothee on 30/05/17.
 */
public class Attacker {

    /**
     * Compromise t nodes on the network and compromised the links attached to the keys from each node.
     * This function only works on a network following the basic scheme.
     * @param t amount of nodes to compromise
     * @param n the network
     * @return the amount of links compromised
     */
    public static int compromiseNetwork_Basic_Scheme(int t, Network n) {

        int amountOfLinksCompromised = 0;

        if(n.getScheme() == NetworkType.basicScheme) {
            if(n.getNodes() != null) {
                ArrayList<Key> compromisedKeys = new ArrayList<Key>();
                ArrayList<Integer> indexes = new ArrayList<Integer>();

                // Compromise nodes
                int randomIndex = ThreadLocalRandom.current().nextInt(0, n.getNodes().size());
                for (int i = 0; i < t; i++) {
                    while (indexes.contains(randomIndex)) {
                        randomIndex = ThreadLocalRandom.current().nextInt(0, n.getNodes().size());
                    }
                    indexes.add(randomIndex);
                    Node nodeToCompromise = n.getNodes().get(randomIndex);
                    nodeToCompromise.setState(NodeState.compromised);
                    compromisedKeys.addAll(nodeToCompromise.getKeys());
                }

                // Compromise links with the gathered keys
                ArrayList<Link> networkLinks = n.getLinks();
                if(networkLinks != null) {
                    ArrayList<Link> linksToCompromise = new ArrayList<Link>();
                    // Get a list of links to compromise
                    for (Link l: networkLinks) {
                        if(compromisedKeys.contains(l.getKey())) {
                            amountOfLinksCompromised++;
                            l.setLinkLinkState(LinkState.compromised);
                        }
                    }

                }
                else {
                    System.out.println("No links in the network \"" + n + "\"");
                }
            }
            else {
                System.out.println("No nodes in the network \"" + n + "\"");
            }
        }
        else {
            System.out.println("This network is not of type " + NetworkType.basicScheme.name());
        }

        return amountOfLinksCompromised;
    }

    /**
     * Compromise t nodes on the network and compromised the links attached to the keys from each node.
     * This function only works on a network following the polynomial scheme.
     * @param t amount of nodes to compromise
     * @param n the network
     * @return the amount of links compromised
     */
    public static int compromiseNetwork_Polynomial_Scheme(int t, Network n) {
        int amountOfLinksCompromised = 0;

        // TODO:
        // - hack t nodes in the network, retrieve the polynomials in a large table
        // - After you get n+1 nodes with their respective generated polynomials, get the polynomial id
        // - Compromise the links created with the given polynomial

        List<Node> capturedNodes = new ArrayList<Node>();

        // Capturing t random nodes from the network
//        Collections.shuffle(n.getNodes());
        capturedNodes = n.getNodes().subList(0,t);

        // { NumPoly: amountOfCapturedForThisNum }
        HashMap<Integer, Integer> amountOfCapturedPolynomials = new HashMap<Integer, Integer>();
        for(Node node: capturedNodes) {
            for(Integer i: node.getPolynomials().keySet()) {
                if(!amountOfCapturedPolynomials.containsKey(i)) {
                    amountOfCapturedPolynomials.put(i, 1);
                }
                else {
                    amountOfCapturedPolynomials.put(i, amountOfCapturedPolynomials.get(i) + 1);
                }
            }
        }

        for(Integer key: amountOfCapturedPolynomials.keySet()) {

            // Get the keys from the links where the polynomial is Px
            // Add the amount of links with the polynomial to the amountOfLinksCompromised value, if
            // The polynomial in the amountOfCapturedPolynomials > polynomials.get(key).order
            if(amountOfCapturedPolynomials.get(key) > n.getMainPolynomialsPool().get(key).getOrder()) {
                for(Link l: n.getLinks()) {
                    if(l.getPolynomialId() == key) {
                        amountOfLinksCompromised++;
                        l.setLinkLinkState(LinkState.compromised);
                    }
                }
            }

        }

        return amountOfLinksCompromised;
    }


    /**
     * This method uncompromises the network (for testing purposes)
     * @param network the network to uncompromise
     */
    public static void uncompromiseNodes(Network network) {
        if(network != null) {
            for (Node n: network.getNodes()) {
                n.setState(NodeState.deployed);
            }
            for (Link l: network.getLinks()) {
                l.setLinkLinkState(LinkState.up);
            }
        }
    }
}