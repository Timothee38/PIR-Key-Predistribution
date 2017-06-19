package fr.timotheecraig.keypredistribution.util;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Polynomial {

    private long[] coefs; // [a, b, c...n] => a + b*(x+y) + c*x*y+...+n*(x^n)*(y^n)
    private long module;
    private int order;

    public Polynomial(Polynomial polynomial) {
        this.coefs = new long[polynomial.coefs.length];
        // -> !!!
        System.arraycopy(polynomial.coefs, 0, this.coefs, 0, polynomial.coefs.length);
        // WRONG !!! : this.coefs = polynomial.coefs;
        this.module = polynomial.module;
    }

    /**
     * Get the coefficients of a polynomial : [a, b, c...n] | a + b*(x+y) + c*x*y+...+n*(x^n)*(y^n)
     * @return the coefs of a polynomial
     */
    public long[] getCoefs() {
        return coefs;
    }

    public int getOrder() {
        return order;
    }

    public Polynomial(long[] coefs, int order) {
//        int randomMultiplier = ThreadLocalRandom.current().nextInt(1, 9 + 1); // Random int in [1;9]
//        this.module = (long) (randomMultiplier * Math.pow(2, 8));
        this.module = (long) Math.pow(2,8);
        for(int i = 0; i < coefs.length; i++) {
            coefs[i] %= this.module;
        }
        this.coefs = coefs;
        this.order = order;
        //System.out.println(this.coefs.length);
    }

    public Polynomial(long[] coefs, long module) {
        this.coefs = coefs;
        this.module = module;
    }

    @Override
    public String toString() {
        return "Polynomial : " + Arrays.toString(coefs) + ", order " + this.order;
    }

    /**
     * Apply the id of the node to the coefs
     * example :
     * f(x,y) = 3 + 8(x + y) + 5xy | f(id, y) = 3 + 8*id + 8*y + 5*id*y
     * @param id the identifier of the node
     */
    public void applyIdToCoefs(int id) {
        if(this.coefs != null) {
            //System.out.println(Arrays.toString(this.coefs));
            for(int i = 0; i < this.coefs.length; i++) {
                int order = (i+1) / 2;
                double idPowered = Math.pow(id, order); // ID * Coef (in the coef table)
                if(i%2 != 0) { // Indexes 1,3,5,7,9....
                    // (a + b*id + d*id² + f*id³...) -> this.coefs[0]
                    this.coefs[0] += (long) (this.coefs[i] * idPowered);
                    this.coefs[0] %= this.module;
                }
                else if((i%2 == 0) && (i != 0)) { // indexes 2,4,6,8...
                    long tempVal = this.coefs[i-1];
                    long tempVal2 = this.coefs[i];
//                    System.out.println(tempVal + " " + tempVal2);
                    this.coefs[order] = (long) (tempVal2 * idPowered + tempVal);
                    this.coefs[order] %= this.module;
//                    System.out.println(idPowered+ " " + order +" "+ i);
//                    System.out.println(this.coefs[order] == (115+126*488)%256);
                }
            }
            this.coefs = Arrays.copyOfRange(this.coefs, 0, (((this.coefs.length)/2)+1));

        }
    }

    public static Polynomial generatePolynomial(int maxPolynomialOrder, int biggestCoef) {
        // generate random coefs
        long[] coefs = new long[2*maxPolynomialOrder + 1]; // example : order = 2 : 3 coefs
        for(int i = 0; i <= 2*maxPolynomialOrder; i++) {
            int randomCoef = ThreadLocalRandom.current().nextInt(0, biggestCoef + 1);
            coefs[i] = randomCoef;
        }
        System.out.println(coefs.length);
        return new Polynomial(coefs, maxPolynomialOrder);
    }

    public static List<Integer> getCommonIds(HashMap<Integer, Polynomial> nodePolynomials, HashMap<Integer, Polynomial> neighbourPolynomials) {
        Set<Integer> s;
        if (nodePolynomials != null && neighbourPolynomials != null) {
            s = new HashSet<Integer>(nodePolynomials.keySet());
            s.retainAll(neighbourPolynomials.keySet());
        }
        else {
            s = new HashSet<Integer>();
        }
        return new ArrayList<Integer>(s);
    }

    public int computeValue(int id) {
        int ret = 0;
        if(this.coefs != null) {
            for(int i = 0; i < this.coefs.length; i++) {
                // [a, b, c, d]... f(x) = a + bx + cx² + dx³...
                long calculatedValue = (long) (this.coefs[i]*Math.pow(id, i));
                ret += (calculatedValue)%this.module;
            }
        }
        ret %= (int)this.module;
        return ret;
    }

    public long getModule() {
        return module;
    }
}
