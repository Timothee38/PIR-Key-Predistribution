package fr.timotheecraig.keypredistribution.ui.listeners;

import fr.timotheecraig.keypredistribution.Main;
import fr.timotheecraig.keypredistribution.enums.NetworkType;
import fr.timotheecraig.keypredistribution.external.Attacker;
import fr.timotheecraig.keypredistribution.main.Network;
import fr.timotheecraig.keypredistribution.ui.NetworkDisplay;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by timothee on 15/06/17.
 */
public class MySliderListener implements ChangeListener {


    private Network network;
    private NetworkDisplay networkDisplay;
    private int amountOfKeys;
    private int polynomialsOrder;
    private int keysPerNode;
    private int amountOfNodesToCompromise;

    public MySliderListener(Network network, NetworkDisplay networkDisplay, int amountOfKeys, int polynomialsOrder, int keysPerNode, int nodesToCompromise) {
        this.network = network;
        this.networkDisplay = networkDisplay;
        this.amountOfKeys = amountOfKeys;
        this.polynomialsOrder = polynomialsOrder;
        this.keysPerNode = keysPerNode;
        this.amountOfNodesToCompromise = nodesToCompromise;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider src = (JSlider) e.getSource();
        System.out.println(src.getName());
        int degree = network.getDegree();
        int amountOfKeysPerNode = this.keysPerNode;
        int nodesToCompromise = this.amountOfNodesToCompromise;
        int totalAmountOfKeys = this.amountOfKeys;

        if(!src.getValueIsAdjusting()) {

            switch (src.getName()) {
                case "degree":
                    degree = (int) src.getValue();
                    break;
                case "amountOfKeysPerNode":
                    amountOfKeysPerNode = (int) src.getValue();
                    break;
                case "amountOfNodesToCompromise":
                    nodesToCompromise = (int) src.getValue();
                    break;
                case "totalAmountOfKeys":
                    totalAmountOfKeys = (int) src.getValue();
                    break;
                default:
                    break;
            }

            if(src.getName() == "degree" || src.getName() == "amountOfKeysPerNode" || src.getName() == "totalAmountOfKeys") {
                network = Network.getByDegree(degree, network.getSize(), network.getEmissionRadius(), network.getScheme());

                if(network.getScheme() == NetworkType.polynomialScheme) {
                    network.generatePolynomialPool(totalAmountOfKeys, polynomialsOrder, (int) Math.pow(2, 8));
                }
                else if(network.getScheme() == NetworkType.basicScheme) {
                    network.addAmountOfKeys(amountOfKeys, 128);
                }

                networkDisplay.setNetwork(network);

                if(network.getScheme() == NetworkType.polynomialScheme) {
                    network.predistributePolynomials(amountOfKeysPerNode);
                }
                else if(network.getScheme() == NetworkType.basicScheme) {
                    network.predistributeKeys(amountOfKeysPerNode);
                }
                network.setNodesInitialised();

                network.neighbourDiscovery();
                if(network.getScheme() == NetworkType.polynomialScheme) {
                    network.createLinks_polynomials();
                }
                else if(network.getScheme() == NetworkType.basicScheme) {
                    network.createLinks_basic();
                }


                networkDisplay.paint(networkDisplay.getGraphics());
            }
            else {
                Attacker.uncompromiseNodes(network);
                if(network.getScheme() == NetworkType.polynomialScheme) {
                    Attacker.compromiseNetwork_Polynomial_Scheme(nodesToCompromise, network);
                }
                else if(network.getScheme() == NetworkType.basicScheme) {
                    Attacker.compromiseNetwork_Basic_Scheme(nodesToCompromise, network);
                }
                networkDisplay.paint(networkDisplay.getGraphics());
            }
        }
    }
}
