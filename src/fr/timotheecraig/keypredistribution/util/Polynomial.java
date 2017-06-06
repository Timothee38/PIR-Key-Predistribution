package fr.timotheecraig.keypredistribution.util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Polynomial {

    private int[] coefs; // [a, b, c...n] => a + b*x*y + c*x²y²+...+n*(x^n)*(y^n)
    private int module;

    /**
     * Get the coefficients of a polynomial : [a, b, c...n] | a + b*x*y + c*x²y²+...+n*(x^n)*(y^n)
     * @return the coefs of a polynomial
     */
    public int[] getCoefs() {
        return coefs;
    }

    public Polynomial(int[] coefs) {
        this.coefs = coefs;
        int randomMultiplier = ThreadLocalRandom.current().nextInt(1, 9 + 1); // Random int in [1;20]
        this.module = randomMultiplier * (int) Math.pow(2, 8);
    }

    @Override
    public String toString() {
        return "Polynomial : " + Arrays.toString(coefs);
    }

    /**
     * Apply the id of the node to the coefs
     * example :
     * f(x,y) = 3 + 5xy | f(id, y) = 3 + 5*id*y
     * @param id the identifier of the node
     */
    public void applyIdToCoefs(int id) {
        if(this.coefs != null) {
            for(int i = 1; i < this.coefs.length; i++) {
                double computedValue = (this.coefs[i]*Math.pow(id, i));
                this.coefs[i] = (int) (computedValue) % this.module;
            }
        }
    }

    public static Polynomial generatePolynomial(int maxPolynomialOrder, int biggestCoef) {

        // generate random coefs
        int[] coefs = new int[maxPolynomialOrder + 1]; // example : order = 2 : 3 coefs
        for(int i = 0; i <= maxPolynomialOrder; i++) {
            int randomCoef = ThreadLocalRandom.current().nextInt(-biggestCoef, biggestCoef + 1);
            if(randomCoef == 0) {
                randomCoef = 1;
            }
            coefs[i] = randomCoef;
        }
        return new Polynomial(coefs);
    }

}
