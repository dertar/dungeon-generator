package com.t3o.map;

import com.t3o.utils.ShapeRect;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ShapeBoxBuilderTest {

    @Test
    public void getHorizonRectTest(){
        boolean[][] map = {
                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED},
                {WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY},
                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED},
                {WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY},
                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED}
        };

        ArrayList<ShapeRect> lst = ShapeBoxBuilder.makeRects(map);
        for(ShapeRect it : lst){
            System.out.println(it.toString());
        }
        Assert.assertEquals(3,lst.size());

        Assert.assertEquals(new ShapeRect(0, 0, 5, 1), lst.get(0));
        Assert.assertEquals(new ShapeRect(2, 0, 5, 1), lst.get(1));
        Assert.assertEquals(new ShapeRect(4, 0, 5, 1), lst.get(2));

    }

    @Test
    public void getHorizonRectTest2(){
        boolean[][] map = {
                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BEMPTY},
                {WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY},
                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY},
                {WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY},
                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BEMPTY, WorldMap.BBLOCKED, WorldMap.BBLOCKED}
        };

        ArrayList<ShapeRect> lst = ShapeBoxBuilder.makeRects(map);
        Assert.assertEquals(4,lst.size());

        Assert.assertEquals(new ShapeRect(0, 0, 4, 1), lst.get(0));
        Assert.assertEquals(new ShapeRect(2, 0, 2, 1), lst.get(1));
        Assert.assertEquals(new ShapeRect(4, 0, 2, 1), lst.get(2));
        Assert.assertEquals(new ShapeRect(4, 3, 2, 1), lst.get(3));
    }

}