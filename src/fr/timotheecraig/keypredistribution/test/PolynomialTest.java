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
            fail("Coefs are wrong after applied Id");
        }

        long[] coefs2 = {113, 161};
        Polynomial p2 = new Polynomial(coefs2, (long) Math.pow(2,8));

        p2.applyIdToCoefs(505);

        long[] supposedCoefs2 = {(113+161*505)%256, 161%256};

        System.out.println(Arrays.toString(p2.getCoefs()) + Arrays.toString(supposedCoefs2));
        if(!Arrays.equals(p2.getCoefs(), supposedCoefs2)) {
            fail("Coefs are wrong after applied Id");
        }

    }

    @Test
    public final void testComputeValue() {
        long[] coefs = {1,2,3};
        Polynomial p = new Polynomial(coefs, (long) Math.pow(2, 8));

        p.applyIdToCoefs(5);

        int ret = p.computeValue(3);

        if(ret != ((11+3*17)%256)) {
            fail("Result is wrong after computed value");
        }

    }

}
