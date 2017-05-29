package fr.timotheecraig.keypredistribution.util;


import java.util.Arrays;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Polynomial {

    private int identifier;
    private int[] coefs; // [a, b, c...n] => a + b*x*y + c*x²y²+...+n*(x^n)*(y^n)

    /**
     * Get the identifier of a polynomial
     * @return the identifier of a polynomial
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * Get the coefficients of a polynomial : [a, b, c...n] => a + b*x*y + c*x²y²+...+n*(x^n)*(y^n)
     * @return the coefs of a polynomial
     */
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
