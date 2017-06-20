package fr.timotheecraig.keypredistribution.ui;

import fr.timotheecraig.keypredistribution.main.Network;
import fr.timotheecraig.keypredistribution.ui.listeners.MySliderListener;

import javax.swing.*;
import java.awt.*;

/**
 * Credits to Max for this class.
 * Modified by Timothee on 07/06/2017.
 */
public class Display extends JFrame {

    private NetworkDisplay networkDisplay;
    private JSlider amountOfNodesToCompromise;

    public Display(Network network, int gridSize, int deg, int keysPerNode, int nodesToCompr, int amountOfKeys, int polynomialsOrder, int nodesToCompromise){
        super("Representation of the sensor network");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
        NetworkDisplay networkDisplay = new NetworkDisplay(network, gridSize);
        this.networkDisplay = networkDisplay;

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));

        MySliderListener mySliderListener = new MySliderListener(network, networkDisplay, amountOfKeys, polynomialsOrder, keysPerNode, nodesToCompromise);

        JLabel degreeLabel = new JLabel();
        degreeLabel.setText("Degree");
        JSlider degree = new JSlider(JSlider.HORIZONTAL, 1, 31, deg);
        degree.setName("degree");
        degree.addChangeListener(mySliderListener);
        degreeLabel.setLabelFor(degree);
        degree.setMajorTickSpacing(5);
        degree.setMinorTickSpacing(1);
        degree.setPaintTicks(true);
        degree.setPaintLabels(true);

        JLabel amountOfKeysPerNodeLabel = new JLabel("Total amount of keys");
        JSlider amountOfKeysPerNode = new JSlider(JSlider.HORIZONTAL, 1, 15001, amountOfKeys);
        amountOfKeysPerNode.setName("totalAmountOfKeys");
        amountOfKeysPerNode.addChangeListener(mySliderListener);
        amountOfKeysPerNode.setMajorTickSpacing(2500);
        amountOfKeysPerNode.setMinorTickSpacing(1000);
        amountOfKeysPerNode.setPaintTicks(true);
        amountOfKeysPerNode.setPaintLabels(true);

        JLabel totalAmountLabel = new JLabel("Amount of keys per node");
        JSlider totalAmount = new JSlider(JSlider.HORIZONTAL, 1, 201, keysPerNode);
        totalAmount.setName("amountOfKeysPerNode");
        totalAmount.addChangeListener(mySliderListener);
        totalAmount.setMajorTickSpacing(40);
        totalAmount.setMinorTickSpacing(10);
        totalAmount.setPaintTicks(true);
        totalAmount.setPaintLabels(true);

        JLabel amountOfNodesToCompromiseLabel = new JLabel("Amount of nodes to compromise", JLabel.CENTER);
        JSlider amountOfNodesToCompromise = new JSlider(JSlider.HORIZONTAL, 0, network.getNodes().size(), nodesToCompr);
        amountOfNodesToCompromise.setName("amountOfNodesToCompromise");
        amountOfNodesToCompromise.addChangeListener(mySliderListener);
        amountOfNodesToCompromise.setMajorTickSpacing(100);
        amountOfNodesToCompromise.setMinorTickSpacing(10);
        amountOfNodesToCompromise.setPaintTicks(true);
        amountOfNodesToCompromise.setPaintLabels(true);

        this.amountOfNodesToCompromise = amountOfNodesToCompromise;

        sliderPanel.add(degreeLabel);
        sliderPanel.add(degree);
        sliderPanel.add(totalAmountLabel);
        sliderPanel.add(totalAmount);
        sliderPanel.add(amountOfKeysPerNodeLabel);
        sliderPanel.add(amountOfKeysPerNode);
        sliderPanel.add(amountOfNodesToCompromiseLabel);
        sliderPanel.add(amountOfNodesToCompromise);


        getContentPane().add(networkDisplay, BorderLayout.LINE_START);
        getContentPane().add(sliderPanel, BorderLayout.LINE_END);

        degreeLabel.revalidate();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void setNetwork(Network network) {
        this.networkDisplay.setNetwork(network);
    }

    public void setAmountOfNodesToCompromise(int amountOfNodes) {
        this.amountOfNodesToCompromise.setValue(amountOfNodes);
    }
}
