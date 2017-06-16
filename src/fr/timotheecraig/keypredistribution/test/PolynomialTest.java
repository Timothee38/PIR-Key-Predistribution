package fr.timotheecraig.keypredistribution.test;

import fr.timotheecraig.keypredistribution.util.Polynomial;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.fail;

/**
 * Created by timothee on 14/06/17.
 */
public class PolynomialTest {

    @Test
    public final void testApplyIdToCoefs() {
        long[] coefs = {76, 115, 126};
        Polynomial p = new Polynomial(coefs, (long) Math.pow(2,8));

        p.applyIdToCoefs(488);

        long[] supposedCoefs = {(76+115*488)%256, (115+126*488)%256};

        if(!Arrays.equals(p.getCoefs(), supposedCoefs)) {
            fail("Coefs are wrong after applied Id (order 1)");
        }

        long[] coefs2 = {113, 161};
        Polynomial p2 = new Polynomial(coefs2, (long) Math.pow(2,8));

        p2.applyIdToCoefs(505);

        long[] supposedCoefs2 = {(113+161*505)%256, 161%256};

        System.out.println(Arrays.toString(p2.getCoefs()) + Arrays.toString(supposedCoefs2));
        if(!Arrays.equals(p2.getCoefs(), supposedCoefs2)) {
            fail("Coefs are wrong after applied Id (order 1)");
        }

        long[] coefs3 = {113,104,102,53,89,57,4,85,78,77,85}; // Order = 5

        Polynomial p3 = new Polynomial(coefs3, (long) Math.pow(2,8));

        p3.applyIdToCoefs(486);

        long[] supposedCoefs3 = { 157, 12, 57, 153, 53, 45 };
        if(!Arrays.equals(p3.getCoefs(), supposedCoefs3)) {
            fail("Coefs are wrong after the applied id (order 5).");
        }

    }

    @Test
    public final void testComputeValue() {
        long[] coefs = {1,2,3};
        Polynomial p = new Polynomial(coefs, (long) Math.pow(2, 8));

        p.applyIdToCoefs(5);

        int ret = p.computeValue(3);

        if(ret != ((11+3*17)%256)) {
            fail("Result is wrong after computed value (order 2)");
        }

        long[] coefs3 = {113,104,102,53,89,57,4,85,78,77,85}; // Order = 5

        Polynomial p2 = new Polynomial(coefs3, (long) Math.pow(2,8));

        p2.applyIdToCoefs(486);

        int ret2 = p2.computeValue(359);

        int computedVal = (int) (157%256 + (12*359)%256 + (57*Math.pow(359, 2))%256 + (153*Math.pow(359,3))%256 + (53*Math.pow(359,4))%256 + (45*Math.pow(359,5))%256)%256;

        System.out.println(computedVal + " " + ret2);
        if(ret2 != computedVal) {
            fail("Result wrong after computed value (order 5)");
        }


    }

}
