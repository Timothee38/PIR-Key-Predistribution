package fr.timotheecraig.keypredistribution;

import fr.timotheecraig.keypredistribution.enums.NodeState;
import fr.timotheecraig.keypredistribution.external.Attacker;
import fr.timotheecraig.keypredistribution.main.Network;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("Key Predistribution Simulation - By Timothée Craig");
        System.out.println("--------------------------------------------------");

        Network network = Network.getDefault();
        //network.displayNodes(); // Nodes coordinates are random atm, will need to find something better, not sure what yet though

        network.addAmountOfKeys(200, 2048);

        network.generatePolynomialPool(100,10,50);
        //network.displayPolynomialPool();

        System.out.println(network);

        /*
        for (Node node : network.getNodes()) {
            System.out.println(node);
        }
        */
        System.out.println("            Pre-Distributing polynomials          ");
        System.out.println("--------------------------------------------------");
        network.predistributePolynomials(5);

        network.setNodesInitialised();

        //network.getNodes().forEach(Node::displayPolynomials);

        // network.displayKeys(); // Keys are random atm, better make it less random sometime
        System.out.println("                     Deploying...                 ");
        System.out.println("         Initializing neighbour discovery         ");
        System.out.println("--------------------------------------------------");
        network.neighbourDiscovery();

        System.out.println("                    Creating paths                ");
        System.out.println("--------------------------------------------------");
        network.createPaths();
        //network.displayLinks();
        network.displayNodes();

        System.out.println("            Attacker attack the network           ");
        System.out.println("--------------------------------------------------");
        Attacker.compromiseNodes(10, network);

        network.displayNodes(NodeState.compromised);
    }

}
