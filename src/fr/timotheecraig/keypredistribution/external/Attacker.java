package fr.timotheecraig.keypredistribution.external;

import fr.timotheecraig.keypredistribution.enums.LinkState;
import fr.timotheecraig.keypredistribution.enums.NetworkType;
import fr.timotheecraig.keypredistribution.enums.NodeState;
import fr.timotheecraig.keypredistribution.main.Link;
import fr.timotheecraig.keypredistribution.main.Network;
import fr.timotheecraig.keypredistribution.main.Node;
import fr.timotheecraig.keypredistribution.util.Key;
import fr.timotheecraig.keypredistribution.util.Polynomial;

import java.util.ArrayList;
import java.util.HashMap;
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

        if(n.getScheme() == NetworkType.polynomialScheme) {
            if(n.getNodes() != null) {
                ArrayList<Key> generatedKeysFromStolenPolynomials = new ArrayList<Key>();
                ArrayList<Node> nodesToCompromise = new ArrayList<Node>();

                t = t > n.getNodes().size() ? n.getNodes().size() : t;
                // Retrieve t "random" nodes from n
                for(int i = 0; i < t; i++) {
                    nodesToCompromise.add(n.getNodes().get(i));
                }

                // Generate keys from nodesToCompromise
                if(nodesToCompromise != null) {
                    for (Node node : nodesToCompromise) {
                        ArrayList<Node> nNeighbours = node.getNeighbours();
                        if(nNeighbours != null) {
                            for (Node neighbour : nNeighbours) {
                                HashMap<Integer, Polynomial>  neighbourPolynomials = new HashMap<Integer, Polynomial>(neighbour.getPolynomials());
                                HashMap<Integer, Polynomial>  nodePolynomials = new HashMap<Integer, Polynomial>(node.getPolynomials());

                                Integer commonId = Polynomial.getCommonId(nodePolynomials, neighbourPolynomials);
                                if(commonId != -1) {
                                    // make a copy ?
                                    Polynomial nodePol = nodePolynomials.get(commonId);
                                    Polynomial neighbourPol = neighbourPolynomials.get(commonId);
                                    int nodeComputedValue = nodePol.computeValue(neighbour.getId());
                                    int neighbourComputedValue = neighbourPol.computeValue(node.getId());
                                    if(nodeComputedValue == neighbourComputedValue) {
                                        Key genKey = Key.createKeyFromPolynomial(nodeComputedValue);
                                        System.out.println(genKey);
                                        generatedKeysFromStolenPolynomials.add(genKey);
                                    }
                                }
                            }
                        }
                    }
                }

                // Todo here: compromise links with the key array

                if (n.getLinks() != null) {
                   for(Link l: n.getLinks()) {
                       if(generatedKeysFromStolenPolynomials.contains(l.getKey())) {
                           l.setLinkLinkState(LinkState.compromised);
                           amountOfLinksCompromised++;
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
            System.out.println("This network is not of type " + NetworkType.polynomialScheme.name());
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