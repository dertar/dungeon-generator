package com.t3o.utils;

import com.t3o.assets.Assets;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


public class ShapeRectTest {
    @Test
    public void toStringTest() {
        ShapeRect a = new ShapeRect(2,2,5,5);
        Assert.assertEquals("(2,2,5,5)",a.toString());
    }

    @Test
    public void equalsTest() {
        ShapeRect a = new ShapeRect(2,2,5,5);
        ShapeRect b = new ShapeRect(1,1,5,5);

        Assert.assertEquals(false,a.equals(b));
        Assert.assertEquals(false,b.equals(a));
        Assert.assertEquals(true,a.equals(a));
        Assert.assertEquals(true,b.equals(b));
    }

    @Test
    public void equalsTest2() {
        ShapeRect a = new ShapeRect(2,2,5,5);
        ShapeRect b = new ShapeRect(2,2,4,3);

        Assert.assertEquals(false,a.equals(b));
        Assert.assertEquals(false,b.equals(a));
        Assert.assertEquals(true,a.equals(a));
        Assert.assertEquals(true,b.equals(b));
    }

    @Test
    public void equalsTest3() {
        ShapeRect a = new ShapeRect(2,2,5,5);
        ShapeRect b = new ShapeRect(2,2,5,5);

        Assert.assertEquals(true,a.equals(b));
        Assert.assertEquals(true,b.equals(a));
        Assert.assertEquals(true,a.equals(a));
        Assert.assertEquals(true,b.equals(b));
    }

}