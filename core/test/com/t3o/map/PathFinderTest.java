package com.t3o.map;

import com.t3o.utils.Coordinates;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class PathFinderTest {
//
//    @Test
//    public void leeFour(){
//        boolean[][] map = {
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED},
//                {WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY},
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED},
//                {WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY},
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED}
//        };
//
//        ArrayList<Coordinates> path = PathFinder.lee(map,new Coordinates(1,0),new Coordinates(1,4),false,true);
//
//
//        Coordinates[] answerPath = new Coordinates[]
//                { new Coordinates(1,0), new Coordinates(1,1),new Coordinates(1,2), new Coordinates(1,3),new Coordinates(1,4) };
//
//
//        System.out.println(path.toString());
//
//        Assert.assertEquals("start",answerPath[0],path.get(0));
//
//        Assert.assertEquals("move.length ",answerPath.length,path.size());
//
//        Assert.assertEquals("end",answerPath[answerPath.length - 1],path.get(path.size() - 1));
//
//        for(int i = 0; i < answerPath.length; i++){
//            Assert.assertEquals("Sequence path: " + answerPath[i] + " -> " + path.get(i), answerPath[i],path.get(i) );
//        }
//
//    }
//
//    @Test
//    public void leeFour1(){
//        boolean[][] map = {
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED},
//                {WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY},
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED},
//                {WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY},
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED}
//        };
//
//        ArrayList<Coordinates> path = PathFinder.lee(map,new Coordinates(1,0),new Coordinates(2,4),false,true);
//
//
//        Assert.assertEquals(true,path == null);
//
//
//    }
//
//    @Test
//    public void leeFour2(){
//        boolean[][] map = {
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED},
//                {WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY},
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED},
//                {WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY},
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED}
//        };
//
//        ArrayList<Coordinates> path = PathFinder.lee(map,new Coordinates(1,0),new Coordinates(3,4),false,true);
//
//
//        Assert.assertEquals(true,path == null);
//
//
//    }
//
//    @Test
//    public void leeFour3(){
//        boolean[][] map = {
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED},
//                {WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY},
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED},
//                {WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY, WorldMap.BEMPTY},
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED}
//        };
//
//        ArrayList<Coordinates> path = PathFinder.lee(map,new Coordinates(2,0),new Coordinates(2,4),false,false);
//
//
//        Coordinates[] answerPath = new Coordinates[]
//                { new Coordinates(2,0), new Coordinates(2,1),new Coordinates(2,2), new Coordinates(2,3),new Coordinates(2,4) };
//
//
//        System.out.println(path.toString());
//
//        Assert.assertEquals("start",answerPath[0],path.get(0));
//
//        Assert.assertEquals("move.length ",answerPath.length,path.size());
//
//        Assert.assertEquals("end",answerPath[answerPath.length - 1],path.get(path.size() - 1));
//
//        for(int i = 0; i < answerPath.length; i++){
//            Assert.assertEquals("Sequence path: " + answerPath[i] + " -> " + path.get(i), answerPath[i],path.get(i) );
//        }
//
//    }
//
//    @Test
//    public void leeFour4(){
//        boolean[][] map = {
//                {WorldMap.BEMPTY, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED},
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED},
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED},
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED},
//                {WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BBLOCKED, WorldMap.BEMPTY}
//        };
//
//
//        ArrayList<Coordinates> path = PathFinder.lee(map,new Coordinates(0,0),new Coordinates(4,4),false,false);
//
//
//        Coordinates[] answerPath = new Coordinates[]
//                { new Coordinates(0,0), new Coordinates(0,1),new Coordinates(1,1), new Coordinates(1,2),new Coordinates(2,2),
//                        new Coordinates(2,3), new Coordinates(2,4),new Coordinates(3,4), new Coordinates(4,4)};
//
//
//        System.out.println(path.toString());
//
//        Assert.assertEquals("start",answerPath[0],path.get(0));
//
//        Assert.assertEquals("move.length ",answerPath.length,path.size());
//
//        Assert.assertEquals("end",answerPath[answerPath.length - 1],path.get(path.size() - 1));
//
//        for(int i = 0; i < answerPath.length; i++){
//            Assert.assertEquals("Sequence path: " + answerPath[i] + " -> " + path.get(i), answerPath[i],path.get(i) );
//        }
//
//    }
}