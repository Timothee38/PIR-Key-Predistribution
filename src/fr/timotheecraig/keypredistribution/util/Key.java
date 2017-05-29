package fr.timotheecraig.keypredistribution.util;

import fr.timotheecraig.keypredistribution.util.Polynomial;

/**
 * Created by Timothee on 11/04/2017.
 */
public class Key {

    private String keyString;

    private Key(String keyString) {
        this.keyString = keyString;
    }

    /**
     * Generate a random key composed of random characters.
     * @param sizeOfKey the amount of random characters
     * @return a new Key from the generated String
     */
    public static Key createRandomKey(int sizeOfKey) {
        String ret = "";
        for(int i = 0; i < sizeOfKey; i++) {
            ret += (char) ((Math.random()*74) + 48);
        }
        return new Key(ret);
    }

    /**
     * Generate a key from a polynomial.
     * @param p a polynomial
     * @return a new Key from the generated String
     */
    public static String createKeyFromPolynomial(Polynomial p) {
        String ret = "(KEY) TODO: generate key from polynomial";
        return ret;
    }

    @Override
    public String toString() {
        return this.keyString;
    }

}
