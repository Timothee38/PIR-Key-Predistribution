package fr.timotheecraig.keypredistribution.util;


import java.util.Arrays;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Polynomial {

    private int identifier;
    private int[] coefs; // [a, b, c...n] => a + b*x*y + c*x²y²+...+n*(x^n)*(y^n)

    public int getIdentifier() {
        return identifier;
    }

    public int[] getCoefs() {
        return coefs;
    }

    public Polynomial(int identifier, int[] coefs) {
        this.identifier = identifier;
        this.coefs = coefs;
    }

    @Override
    public String toString() {
        return "Polynomial "+ identifier + " : " + Arrays.toString(coefs);
    }

}
