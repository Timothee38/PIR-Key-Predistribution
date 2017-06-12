package fr.timotheecraig.keypredistribution.util;


import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Polynomial {

    private long[] coefs; // [a, b, c...n] => a + b*(x+y) + c*x*y+...+n*(x^n)*(y^n)
    private int module;

    public Polynomial(Polynomial polynomial) {
        this.coefs = polynomial.coefs;
        this.module = polynomial.module;
    }

    /**
     * Get the coefficients of a polynomial : [a, b, c...n] | a + b*(x+y) + c*x*y+...+n*(x^n)*(y^n)
     * @return the coefs of a polynomial
     */
    public long[] getCoefs() {
        return coefs;
    }

    public Polynomial(long[] coefs) {
        int randomMultiplier = ThreadLocalRandom.current().nextInt(1, 9 + 1); // Random int in [1;9]
        this.module = randomMultiplier * (int) Math.pow(2, 8);
        for(long coef: coefs) {
            coef %= this.module;
        }
        this.coefs = coefs;
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
            for(int i = 0; i < this.coefs.length; i++) {
                int computedValue = (int) (this.coefs[i]*Math.pow(id, i));
                this.coefs[i] = computedValue % this.module;
            }
        }
    }

    public static Polynomial generatePolynomial(int maxPolynomialOrder, int biggestCoef) {

        // generate random coefs
        long[] coefs = new long[maxPolynomialOrder + 1]; // example : order = 2 : 3 coefs
        for(int i = 0; i <= maxPolynomialOrder; i++) {
            int randomCoef = ThreadLocalRandom.current().nextInt(0, biggestCoef + 1);

            coefs[i] = randomCoef;
        }
        return new Polynomial(coefs);
    }

    public static Integer getCommonId(HashMap<Integer, Polynomial> nodePolynomials, HashMap<Integer, Polynomial> neighbourPolynomials) {
        if (nodePolynomials != null && neighbourPolynomials != null) {
            for (Integer key : nodePolynomials.keySet()) {
                if(neighbourPolynomials.containsKey(key)) {
                    return key;
                }
            }
        }
        return -1;
    }

    public int computeValue(int id) {
        int ret = 0;
        if(this.coefs != null) {
            for(int i = 0; i < this.coefs.length; i++) {
                ret += (this.coefs[i] * Math.pow(id, i));
                ret %= this.module;
            }
        }
        return ret;
    }
}
