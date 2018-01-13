package com.t3o.map;

import com.t3o.assets.Assets;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.plaf.basic.BasicOptionPaneUI;

import static org.junit.Assert.*;

public class PatternTest {
    @Test
    public void isApproach()  {
        boolean[][] map = { {WorldMap.BBLOCKED,WorldMap.BEMPTY,WorldMap.BEMPTY},
                            {WorldMap.BBLOCKED,WorldMap.BBLOCKED,WorldMap.BBLOCKED},
                            {WorldMap.BBLOCKED,WorldMap.BBLOCKED,WorldMap.BBLOCKED}
        };
        Pattern pattern = new Pattern();

        pattern.pattern = new Pattern.PATTERN[][]{
                {Pattern.PATTERN.NONE,Pattern.PATTERN.EMPTY,Pattern.PATTERN.NONE},
                {Pattern.PATTERN.BLOCKED,Pattern.PATTERN.BLOCKED,Pattern.PATTERN.BLOCKED},
                {Pattern.PATTERN.NONE,Pattern.PATTERN.BLOCKED,Pattern.PATTERN.NONE}
        };

        boolean isApproached = pattern.isApproach(map,0,0);

        Assert.assertEquals(true,isApproached);

    }

    @Test
    public void isApproach1()  {
        boolean[][] map = {
                {WorldMap.BBLOCKED,WorldMap.BBLOCKED,WorldMap.BBLOCKED},
                {WorldMap.BBLOCKED,WorldMap.BBLOCKED,WorldMap.BBLOCKED},
                {WorldMap.BBLOCKED,WorldMap.BBLOCKED,WorldMap.BBLOCKED}
        };
        Pattern ROCK_ORIGIN = new Pattern();

        ROCK_ORIGIN.pattern = new Pattern.PATTERN[][]{
                {Pattern.PATTERN.NONE,Pattern.PATTERN.BLOCKED,Pattern.PATTERN.NONE},
                {Pattern.PATTERN.BLOCKED,Pattern.PATTERN.BLOCKED,Pattern.PATTERN.BLOCKED},
                {Pattern.PATTERN.NONE,Pattern.PATTERN.BLOCKED,Pattern.PATTERN.NONE}
        };

        Pattern ROCK_LEFT = new Pattern();

        ROCK_LEFT.pattern = new Pattern.PATTERN[][]{
                {Pattern.PATTERN.NONE,Pattern.PATTERN.BLOCKED,Pattern.PATTERN.NONE},
                {Pattern.PATTERN.BLOCKED,Pattern.PATTERN.BLOCKED,Pattern.PATTERN.EMPTY},
                {Pattern.PATTERN.NONE,Pattern.PATTERN.BLOCKED,Pattern.PATTERN.NONE}
        };


        Assert.assertEquals(false,ROCK_ORIGIN.isApproach(map,-1,-1));
        Assert.assertEquals(true,ROCK_LEFT.isApproach(map,2,3));

    }
}