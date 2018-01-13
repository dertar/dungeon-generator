package com.t3o.utils;

import com.t3o.assets.Assets;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinatesTest {
    @Test
    public void equals(){
        Coordinates A = new Coordinates(5,5);
        Coordinates B = new Coordinates(5,5);

        Assert.assertEquals("a == b",true,A.equals(B));
        Assert.assertEquals("b == a",true,B.equals(A));
        Assert.assertEquals("b == b",true,B.equals(B));
    }

    @Test
    public void notEquals(){
        Coordinates B = new Coordinates(5,6);
        Coordinates C = new Coordinates(6,5);

        Assert.assertNotEquals("B != C", true, B.equals(C));
        Assert.assertNotEquals("C != B", true, C.equals(B));

    }
}