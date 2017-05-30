package fr.timotheecraig.keypredistribution.external;

import fr.timotheecraig.keypredistribution.enums.NodeState;
import fr.timotheecraig.keypredistribution.main.Network;
import fr.timotheecraig.keypredistribution.main.Node;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by timothee on 30/05/17.
 */
public class Attacker {

    public static void compromiseNodes(int t, Network n) {
        ArrayList<Node> networkNodes = n.getNodes();
        if(networkNodes != null) {
            System.out.println("Randomly compromising " + t + " nodes...");
            int randomIndex = 0;
            ArrayList<Integer> alreadyCompromisedIndex = new ArrayList<Integer>();
            for (int i = 0; i < t; i++) {
                // Pick a random index and compromise the node at the given index
                while (alreadyCompromisedIndex.contains(randomIndex)) {
                    randomIndex = ThreadLocalRandom.current().nextInt(0, networkNodes.size());
                }
                alreadyCompromisedIndex.add(randomIndex);

                Node nodeToCompromise = networkNodes.get(randomIndex);

                nodeToCompromise.setState(NodeState.compromised);

            }
        }
    }

}
