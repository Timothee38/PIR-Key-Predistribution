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
//    ArrayList<Node> sensors;
//    ArrayList<Link> links;

    public Display(ArrayList<Node> s, ArrayList<Link> l, int gridSize){
        super("Representation of the sensor network");
//        this.sensors = s;
//        this.links = l;

        setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
        JPanel networkDisplay = new NetworkDisplay(s, l, gridSize);

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));

        JLabel degreeLabel = new JLabel();
        degreeLabel.setText("Degree");
        JSlider degree = new JSlider(JSlider.HORIZONTAL, 0, 500, 0);
        degreeLabel.setLabelFor(degree);
        degree.setMajorTickSpacing(100);
        degree.setMinorTickSpacing(10);
        degree.setPaintTicks(true);
        degree.setPaintLabels(true);

        JLabel amountOfKeysPerNodeLabel = new JLabel("Amount of keys per node");
        JSlider amountOfKeysPerNode = new JSlider(JSlider.HORIZONTAL, 0, 500, 0);
        amountOfKeysPerNode.setMajorTickSpacing(100);
        amountOfKeysPerNode.setMinorTickSpacing(10);
        amountOfKeysPerNode.setPaintTicks(true);
        amountOfKeysPerNode.setPaintLabels(true);

        JLabel amountOfNodesToCompromiseLabel = new JLabel("Amount of nodes to compromise", JLabel.CENTER);
        JSlider amountOfNodesToCompromise = new JSlider(JSlider.HORIZONTAL, 0, 500, 0);
        amountOfNodesToCompromise.setMajorTickSpacing(100);
        amountOfNodesToCompromise.setMinorTickSpacing(10);
        amountOfNodesToCompromise.setPaintTicks(true);
        amountOfNodesToCompromise.setPaintLabels(true);


        sliderPanel.add(degreeLabel);
        sliderPanel.add(degree);
        sliderPanel.add(amountOfKeysPerNodeLabel);
        sliderPanel.add(amountOfKeysPerNode);
        sliderPanel.add(amountOfNodesToCompromiseLabel);
        sliderPanel.add(amountOfNodesToCompromise);

//        degreeLabel.repaint();
//        degree.repaint();
//        amountOfKeysPerNodeLabel.repaint();
//        amountOfKeysPerNode.repaint();
//        amountOfNodesToCompromiseLabel.repaint();
//        amountOfNodesToCompromise.repaint();

        getContentPane().add(networkDisplay, BorderLayout.LINE_START);
        getContentPane().add(sliderPanel, BorderLayout.LINE_END);

        degreeLabel.revalidate();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

//    public void toto(Graphics g){
//        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, 1000, 1000);
//        // Draw links
//        g.setColor(Color.WHITE);
//        for(int j = 0; j < links.size(); j++){
//            if(links.get(j).getLinkState() == LinkState.compromised) {
//                g.setColor(Color.RED);
//            }
//            else {
//                g.setColor(Color.WHITE);
//            }
//            g.drawLine(links.get(j).getNode1().getCoordX(), links.get(j).getNode1().getCoordY(), links.get(j).getNode2().getCoordX(), links.get(j).getNode2().getCoordY());
//        }
//        // Draw nodes
//        g.setColor(Color.GREEN);
//        for(int i = 0; i<sensors.size(); i++){
//            g.drawLine(sensors.get(i).getCoordX(), sensors.get(i).getCoordY(), sensors.get(i).getCoordX(), sensors.get(i).getCoordY());
//        }
//    }
//
//    public void setNodes(ArrayList<Node> nodes) {
//        this.sensors = nodes;
//    }
//
//    public void setLinks(ArrayList<Link> links) {
//        this.links = links;
//    }
}
