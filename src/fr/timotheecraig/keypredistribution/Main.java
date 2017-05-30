package fr.timotheecraig.keypredistribution;

import fr.timotheecraig.keypredistribution.enums.NodeState;
import fr.timotheecraig.keypredistribution.external.Attacker;
import fr.timotheecraig.keypredistribution.main.Network;
import fr.timotheecraig.keypredistribution.main.Node;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Main {

    public static void main(String[] args) {

        // BASIC SCHEME

        System.out.println("Key Predistribution Simulation - By Timothée Craig");
        System.out.println("--------------------------------------------------");
        System.out.println("");

        int degree = 4; // 4 nodes
        int size = 1000; // 1000 meters
        int nodeEmissionRadius = 50; // 50 meters
        Network network = Network.getByDegree(degree, size, nodeEmissionRadius);

        System.out.println("        Done with network initialisation          ");
        System.out.println("");
        System.out.println("                Adding keys...                    ");
        System.out.println("--------------------------------------------------");
        System.out.println("");

        network.addAmountOfKeys(1000, 2048);
        System.out.println(network);

        System.out.println("");
        System.out.println("              Pre-Distributing keys               ");
        System.out.println("--------------------------------------------------");
        System.out.println("");

        network.predistributeKeys(5); // 5 keys per node
        network.setNodesInitialised();

        //network.getNodes().forEach(System.out::println);

        // network.displayKeys();
        System.out.println("             Pre-distribution Completed           ");
        System.out.println("");
        System.out.println("                     Deploying...                 ");
        System.out.println("         Initializing neighbour discovery         ");
        System.out.println("--------------------------------------------------");
        System.out.println("");

        network.neighbourDiscovery();
        //network.getNodes().forEach(System.out::println);

        System.out.println("            Neighbour discovery completed         ");
        System.out.println("");
        System.out.println("                  Creating paths                  ");
        System.out.println("--------------------------------------------------");
        System.out.println("");

        //network.createPaths();
        //network.displayLinks();
        //network.displayNodes();

        System.out.println("            Attacker attack the network           ");
        System.out.println("--------------------------------------------------");
        System.out.println("");

        //Attacker.compromiseNodes(10, network);

        //network.displayNodes(NodeState.compromised);


        //
        //
        //


        // Polynomial pool-based key pre-distribution for Wireless Sensor Networks (after basic scheme)

        /*System.out.println("Key Predistribution Simulation - By Timothée Craig");
        System.out.println("--------------------------------------------------");

        int degree = 4; // 4 nodes
        int size = 1000; // 1000 meters
        int nodeEmissionRadius = 50; // 50 meters
        //Network network = Network.getDefault();
        Network network = Network.getByDegree(degree, size, nodeEmissionRadius);
        //network.displayNodes(); // Nodes coordinates are random atm, will need to find something better, not sure what yet though

        System.out.println("        Done with network initialisation          ");
        System.out.println("                Adding keys...                    ");
        System.out.println("--------------------------------------------------");

        network.addAmountOfKeys(1000, 2048);

        //network.generatePolynomialPool(100,10,50);
        //network.displayPolynomialPool();

        System.out.println(network);


        for (Node node : network.getNodes()) {
            System.out.println(node);
        }

        System.out.println("              Pre-Distributing keys               ");
        System.out.println("--------------------------------------------------");

        //network.predistributePolynomials(5);

        network.setNodesInitialised();

        //network.getNodes().forEach(Node::displayPolynomials);

        // network.displayKeys(); // Keys are random atm, better make it less random sometime
        System.out.println("                     Deploying...                 ");
        System.out.println("         Initializing neighbour discovery         ");
        System.out.println("--------------------------------------------------");

        network.neighbourDiscovery();

        System.out.println("                    Creating paths                ");
        System.out.println("--------------------------------------------------");

        //network.createPaths();
        //network.displayLinks();
        //network.displayNodes();

        System.out.println("            Attacker attack the network           ");
        System.out.println("--------------------------------------------------");

        //Attacker.compromiseNodes(10, network);

        //network.displayNodes(NodeState.compromised);
        */

    }

}
