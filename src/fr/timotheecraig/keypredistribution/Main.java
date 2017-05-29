package fr.timotheecraig.keypredistribution;

import fr.timotheecraig.keypredistribution.main.Network;

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
        System.out.println(network);
        network.displayNodes(); // Nodes coordinates are random atm, will need to find something better, not sure what yet though
    }

}
