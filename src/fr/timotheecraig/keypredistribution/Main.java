package fr.timotheecraig.keypredistribution;

import fr.timotheecraig.keypredistribution.main.Network;
import fr.timotheecraig.keypredistribution.main.Node;
import fr.timotheecraig.keypredistribution.util.Key;

import java.util.ArrayList;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Main {

    public static void main(String[] args) {
        /* TODO: Generate a network of nodes, predestribute polynomials, Compare them from node to node
           generate keys accordingly
        */
        System.out.println("Key Predistrbution Simulation - By Timothée Craig");
        System.out.println("-------------------------------------------------");

        Network network = Network.getDefault();
        //network.displayNodes(); // Nodes coordinates are random atm, will need to find something better, not sure what yet though

        network.addAmountOfKeys(200, 2048);

        System.out.println(network);

        // network.displayKeys(); // Keys are random atm, better make it less random sometime
        System.out.println("        Initializing neighbour discovery         ");
        System.out.println("-------------------------------------------------");
        network.neighbourDiscovery();

        for (Node node : network.getNodes()) {
            System.out.println(node);
            //node.displayNeighbours();
        }

    }

}
