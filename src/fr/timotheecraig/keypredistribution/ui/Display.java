package fr.timotheecraig.keypredistribution.ui;

import fr.timotheecraig.keypredistribution.enums.LinkState;
import fr.timotheecraig.keypredistribution.main.Link;
import fr.timotheecraig.keypredistribution.main.Node;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Credits to Max for this class.
 * Modified by Timothee on 07/06/2017.
 */
public class Display extends JFrame {
    JPanel panel;
    ArrayList<Node> sensors;
    ArrayList<Link> links;

    public Display(ArrayList<Node> s, ArrayList<Link> l, int gridSize){
        super("Representation of the sensor network");
        this.sensors = s;
        this.links = l;

        setLayout(null);
        panel = new JPanel();
        panel.setBounds(0, 0, gridSize, gridSize);
        setContentPane(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(gridSize, gridSize);
        setResizable(false);
        setVisible(true);
    }

    public void paint(Graphics g){
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
