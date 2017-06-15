package fr.timotheecraig.keypredistribution.ui;

import fr.timotheecraig.keypredistribution.enums.LinkState;
import fr.timotheecraig.keypredistribution.main.Link;
import fr.timotheecraig.keypredistribution.main.Node;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by timothee on 15/06/17.
 */
public class NetworkDisplay extends JPanel {
    ArrayList<Node> sensors;
    ArrayList<Link> links;

    public void setNodes(ArrayList<Node> nodes) {
        this.sensors = nodes;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    public NetworkDisplay(ArrayList<Node> s, ArrayList<Link> l, int gridSize) {
        super();
        this.sensors = s;
        this.links = l;

        this.setPreferredSize(new Dimension(gridSize, gridSize));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //perform your drawings here..
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 1000);
        // Draw links
        g.setColor(Color.WHITE);
        for(int j = 0; j < links.size(); j++){
            if(links.get(j).getLinkState() == LinkState.compromised) {
                g.setColor(Color.RED);
            }
            else {
                g.setColor(Color.WHITE);
            }
            g.drawLine(links.get(j).getNode1().getCoordX(), links.get(j).getNode1().getCoordY(), links.get(j).getNode2().getCoordX(), links.get(j).getNode2().getCoordY());
        }
        // Draw nodes
        g.setColor(Color.GREEN);
        for(int i = 0; i<sensors.size(); i++){
            g.drawLine(sensors.get(i).getCoordX(), sensors.get(i).getCoordY(), sensors.get(i).getCoordX(), sensors.get(i).getCoordY());
        }
    }

}
