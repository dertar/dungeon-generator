package com.t3o.map;

import com.badlogic.gdx.utils.Array;
import com.t3o.assets.Assets;
import com.t3o.assets.resources.TileResource;
import com.t3o.utils.Coordinates;
import com.t3o.utils.RoomCorridorStructure;
import com.t3o.utils.ShapeRect;
import com.t3o.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class MapTest {
//
//    @Test
//    public void test(){
//        assertEquals(true,true);
//    }
//
//    @Test
//    public void convertToRoomsTest(){
//
//        boolean[][] map = {
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY}
//        };
//
//        int[][] roomMap = Map.convertToRooms(map);
//
//        Assert.assertArrayEquals( new int[]{Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY}, roomMap[0]  );
//        Assert.assertArrayEquals( new int[]{Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY}, roomMap[1]  );
//        Assert.assertArrayEquals( new int[]{Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY}, roomMap[2]  );
//        Assert.assertArrayEquals( new int[]{Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY}, roomMap[3]  );
//        Assert.assertArrayEquals( new int[]{Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY}, roomMap[4]  );
//    }
//
//    @Test
//    public void convertToRoomsTest1(){
//
//        boolean[][] map = {
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED}
//        };
//
//        int[][] roomMap = Map.convertToRooms(map);
//
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[0]  );
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[1]  );
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[2]  );
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[3]  );
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[4]  );
//    }
//
//    @Test
//    public void convertToRoomsTest2(){
//
//        boolean[][] map = {
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED}
//        };
//
//        int[][] roomMap = Map.convertToRooms(map);
//
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[0]  );
//        Assert.assertArrayEquals( new int[]{Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY}, roomMap[1]  );
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[2]  );
//        Assert.assertArrayEquals( new int[]{Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY}, roomMap[3]  );
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[4]  );
//    }
//
//    @Test
//    public void roomTest(){
//
//        boolean[][] map = {
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED}
//        };
//
//        int[][] roomMap = Map.rooms(map);
//
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[0]  );
//        Assert.assertArrayEquals( new int[]{1,1,1,1,1}, roomMap[1]  );
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[2]  );
//        Assert.assertArrayEquals( new int[]{2,2,2,2,2}, roomMap[3]  );
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[4]  );
//    }
//
//    @Test
//    public void roomTest1(){
//
//        boolean[][] map = {
//                {Map.BEMPTY,Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BEMPTY},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BEMPTY,Map.BEMPTY}
//        };
//
//        int[][] roomMap = Map.rooms(map);
//
//        Assert.assertArrayEquals( new int[]{1,1,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[0]  );
//        Assert.assertArrayEquals( new int[]{1,1,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[1]  );
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[2]  );
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,2,Map.BLOCKED,Map.BLOCKED,3}, roomMap[3]  );
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,3,3}, roomMap[4]  );
//    }
//
//
//    @Test
//    public void makePuffinsTest(){
//        boolean[][] map = {
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY}
//        };
//        Map.makePuffins(map);
//
//
//
//        Assert.assertArrayEquals( new boolean[]{Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED}, map[0]  );
//        Assert.assertArrayEquals( new boolean[]{Map.BBLOCKED,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BBLOCKED}, map[1]  );
//        Assert.assertArrayEquals( new boolean[]{Map.BBLOCKED,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BBLOCKED}, map[2]  );
//        Assert.assertArrayEquals( new boolean[]{Map.BBLOCKED,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BBLOCKED}, map[3]  );
//        Assert.assertArrayEquals( new boolean[]{Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED}, map[4]  );
//    }
//
//    @Test
//    public void getInfoRoomTest(){
//        boolean[][] map = {
//                {Map.BEMPTY,Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BEMPTY},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BEMPTY,Map.BEMPTY}
//        };
//
//        int[][] roomMap = Map.rooms(map);
//
//        Assert.assertArrayEquals( new int[]{1,1,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[0]  );
//        Assert.assertArrayEquals( new int[]{1,1,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[1]  );
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,Map.BLOCKED}, roomMap[2]  );
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,2,Map.BLOCKED,Map.BLOCKED,3}, roomMap[3]  );
//        Assert.assertArrayEquals( new int[]{Map.BLOCKED,Map.BLOCKED,Map.BLOCKED,3,3}, roomMap[4]  );
//
//        ArrayList<ArrayList<Coordinates>> infoRoom = Map.getInfoRoom(roomMap);
//
//
//        Coordinates[] coordinates = new Coordinates[] {
//            new Coordinates(0,0),new Coordinates(0,1),new Coordinates(1,0),new Coordinates(1,1),
//        };
//
//        Coordinates[] coordinates1 = new Coordinates[]{
//                new Coordinates(3,1)
//        };
//
//        Coordinates[] coordinates2 = new Coordinates[]{
//                new Coordinates(3,4),new Coordinates(4,4),new Coordinates(4,3)
//        };
//
//        Assert.assertEquals(coordinates.length,infoRoom.get(1).size());
//
//
//        Assert.assertEquals(coordinates1.length,infoRoom.get(2).size());
//
//        Assert.assertEquals(coordinates2.length,infoRoom.get(3).size());
//
//        for(int i = 0; i < infoRoom.get(1).size(); i++){
//            Assert.assertEquals(coordinates[i].toString() + " has in " + infoRoom.get(1).toString(),true,Utils.isContains(infoRoom.get(1),coordinates[i]));
//        }
//
//        for(int i = 0; i < infoRoom.get(2).size(); i++){
//            Assert.assertEquals(coordinates1[i].toString() + " has in " + infoRoom.get(2).toString(),true,Utils.isContains(infoRoom.get(2),coordinates1[i]));
//        }
//
//        for(int i = 0; i < infoRoom.get(3).size(); i++){
//            Assert.assertEquals(coordinates2[i].toString() + " has in " + infoRoom.get(3).toString(),true,Utils.isContains(infoRoom.get(3),coordinates2[i]));
//        }
//
//    }
//
//    @Test
//    public void distanceRoomsTest(){
//        boolean[][] map = {
//                {Map.BEMPTY,Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BEMPTY},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BEMPTY,Map.BEMPTY}
//        };
//
//        int[][] roomMap = Map.rooms(map);
//
//        for(int i = 0; i < roomMap.length; i++){
//            for(int j = 0; j < roomMap[0].length; j++){
//                System.out.print(roomMap[i][j] + " ");
//            }
//            System.out.print("\n");
//        }
//        ArrayList<ArrayList<Coordinates>> infoRoom = Map.getInfoRoom(roomMap);
//
//        ArrayList<RoomCorridorStructure> struct = Map.distanceRooms(infoRoom);
//
//        for(int i = 0; i < struct.size(); i++) {
//            System.out.print(struct.get(i).roomA + "->" + struct.get(i).A + "->" + struct.get(i).distance);
//            System.out.println( "---->" + struct.get(i).roomB + "->" + struct.get(i).B + "->" + struct.get(i).distance);
//        }
//    }
//    @Test
//    public void distanceRoomsTest1(){
//
//        boolean[][] map = {
//                {Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BEMPTY}
//        };
//
//        int[][] roomMap = Map.rooms(map);
//
//
//        ArrayList<ArrayList<Coordinates>> infoRoom = Map.getInfoRoom(roomMap);
//
//        ArrayList<RoomCorridorStructure> struct = Map.distanceRooms(infoRoom);
//
//        for(int i = 0; i < struct.size(); i++) {
//            System.out.print(struct.get(i).roomA + "->" + struct.get(i).A + "->" + struct.get(i).distance);
//            System.out.println( "---->" + struct.get(i).roomB + "->" + struct.get(i).B + "->" + struct.get(i).distance);
//        }
//    }
//    @Test
//    public void distanceMainRoomTest1(){
//
//        boolean[][] map = {
//                {Map.BEMPTY,Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BEMPTY,Map.BBLOCKED,Map.BEMPTY,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BEMPTY}
//        };
//
//        int[][] roomMap = Map.rooms(map);
//
//
//        ArrayList<ArrayList<Coordinates>> infoRoom = Map.getInfoRoom(roomMap);
//
//        ArrayList<RoomCorridorStructure> struct = Map.distanceMainRoom(infoRoom);
//
//        for(int i = 0; i < struct.size(); i++) {
//            System.out.print(struct.get(i).roomA + "->" + struct.get(i).A + "->" + struct.get(i).distance);
//            System.out.println( "---->" + struct.get(i).roomB + "->" + struct.get(i).B + "->" + struct.get(i).distance);
//        }
//    }
//
//    @Test
//    public void makeCorridorTest(){
//
//        boolean[][] map = {
//                {Map.BEMPTY,Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BEMPTY},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BEMPTY,Map.BEMPTY}
//        };
//
//        int[][] roomMap = Map.rooms(map);
//
//
//        ArrayList<ArrayList<Coordinates>> infoRoom = Map.getInfoRoom(roomMap);
//
//        ArrayList<RoomCorridorStructure> struct = Map.distanceRooms(infoRoom);
//
//        Map.makeCorridors(map,struct,Utils.max(roomMap));
//
//        for(int i = 0; i < map.length; i++){
//            for(int j = 0; j < map[0].length; j++){
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.print("\n");
//        }
//
//        int[][] checkMap = Map.rooms(map);
//
//        Assert.assertEquals(1,Utils.max(checkMap));
//    }
//    @Test
//    public void makeCorridorTest1(){
//
//        boolean[][] map = {
//                {Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BEMPTY}
//        };
//
//        int[][] roomMap = Map.rooms(map);
//
//
//        ArrayList<ArrayList<Coordinates>> infoRoom = Map.getInfoRoom(roomMap);
//
//        ArrayList<RoomCorridorStructure> struct = Map.distanceRooms(infoRoom);
//
//        Map.makeCorridors(map,struct,Utils.max(roomMap));
//
//        for(int i = 0; i < map.length; i++){
//            for(int j = 0; j < map[0].length; j++){
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.print("\n");
//        }
//
//        int[][] checkMap = Map.rooms(map);
//
//        Assert.assertEquals(1,Utils.max(checkMap));
//    }
//
//    @Test
//    public void makeCorridorTest2(){
//
//        boolean[][] map = {
//                {Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BEMPTY},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BBLOCKED,Map.BEMPTY,Map.BBLOCKED,Map.BEMPTY},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BEMPTY}
//        };
//
//        int[][] roomMap = Map.rooms(map);
//
//
//        ArrayList<ArrayList<Coordinates>> infoRoom = Map.getInfoRoom(roomMap);
//
//        ArrayList<RoomCorridorStructure> struct = Map.distanceRooms(infoRoom);
//
//        Map.makeCorridors(map,struct,Utils.max(roomMap));
//
//        for(int i = 0; i < map.length; i++){
//            for(int j = 0; j < map[0].length; j++){
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.print("\n");
//        }
//
//        int[][] checkMap = Map.rooms(map);
//
//        Assert.assertEquals(1,Utils.max(checkMap));
//    }
//
//    @Test
//    public void makeCorridorTest3(){
//
//        boolean[][] map = {
//                {Map.BEMPTY,Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BEMPTY,Map.BBLOCKED,Map.BEMPTY,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BEMPTY}
//        };
//
//        int[][] roomMap = Map.rooms(map);
//
//
//        ArrayList<ArrayList<Coordinates>> infoRoom = Map.getInfoRoom(roomMap);
//
//        ArrayList<RoomCorridorStructure> struct = Map.distanceMainRoom(infoRoom);
//
//        Map.makeCorridors(map,struct,Utils.max(roomMap));
//
//        for(int i = 0; i < map.length; i++){
//            for(int j = 0; j < map[0].length; j++){
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.print("\n");
//        }
//
//        int[][] checkMap = Map.rooms(map);
//
//        Assert.assertEquals(1,Utils.max(checkMap));
//    }
//
//    @Test
//    public void findWallsTest1(){
//        boolean[][] map = {
//                {Map.BEMPTY,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED}
//        };
//
//        Array<Coordinates> walls = Map.findEdges(map);
//        for(int i = 0; i < walls.size; i++){
//            System.out.println(walls.get(i));
//        }
//        Coordinates[] answerWalls = new Coordinates[]{
//                new Coordinates(0,1), new Coordinates(1,0), new Coordinates(1,1)
//        };
//        Assert.assertEquals(answerWalls.length,walls.size);
//        for(int i = 0; i < walls.size; i++){
//            Assert.assertEquals(answerWalls[i].toString() + " has in " + walls.toString(),true,Utils.isContains(answerWalls,walls.get(i)));
//        }
//
//    }
//
//    @Test
//    public void makeFloorMapTest1(){
//        boolean[][] map = {
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED}
//        };
//        TileResource.TILE[][] tiles = {
//                {null,null,null,null,null},
//                {null,null,null,null,null},
//                {TileResource.TILE.WALKABLE,TileResource.TILE.WALKABLE,TileResource.TILE.WALKABLE,TileResource.TILE.WALKABLE,TileResource.TILE.WALKABLE},
//                {null,null,null,null,null},
//                {TileResource.TILE.WALKABLE,TileResource.TILE.WALKABLE,TileResource.TILE.WALKABLE,TileResource.TILE.WALKABLE,TileResource.TILE.WALKABLE}
//        };
//        Array<Coordinates> edges = Map.findEdges(map);
//
//        int[][] floorMap = CorrectPath.makeFloorMap(edges,tiles,new FloorGraph());
//
//        for(int i = 0; i < floorMap.length; i++){
//            for(int j = 0; j < floorMap[0].length; j++){
//                System.out.print(floorMap[i][j] + " ");
//            }
//            System.out.println();
//        }
//
//        Assert.assertArrayEquals(new int[]{Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY},floorMap[0]);
//        Assert.assertArrayEquals(new int[]{Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY},floorMap[1]);
//        Assert.assertArrayEquals(new int[]{1,1,1,1,1},floorMap[2]);
//        Assert.assertArrayEquals(new int[]{Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY},floorMap[3]);
//        Assert.assertArrayEquals(new int[]{2,2,2,2,2},floorMap[4]);
//    }
//
//    @Test
//    public void makeFloorMapTest2(){
//        boolean[][] map = {
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY,Map.BEMPTY},
//                {Map.BBLOCKED,Map.BEMPTY,Map.BBLOCKED,Map.BEMPTY,Map.BBLOCKED},
//                {Map.BEMPTY,Map.BBLOCKED,Map.BEMPTY,Map.BBLOCKED,Map.BEMPTY},
//                {Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED,Map.BBLOCKED}
//        };
//        TileResource.TILE[][] tiles = {
//                {null,null,null,null,null},
//                {null,null,null,null,null},
//                {TileResource.TILE.WALKABLE,null,TileResource.TILE.WALKABLE,null,TileResource.TILE.WALKABLE},
//                {null,TileResource.TILE.WALKABLE,null,TileResource.TILE.WALKABLE,null},
//                {TileResource.TILE.WALKABLE,TileResource.TILE.WALKABLE,TileResource.TILE.WALKABLE,TileResource.TILE.WALKABLE,TileResource.TILE.WALKABLE}
//        };
//        Array<Coordinates> edges = Map.findEdges(map);
//
//        int[][] floorMap = CorrectPath.makeFloorMap(edges,tiles,new FloorGraph());
//
//        for(int i = 0; i < floorMap.length; i++){
//            for(int j = 0; j < floorMap[0].length; j++){
//                System.out.print(floorMap[i][j] + " ");
//            }
//            System.out.println();
//        }
//
//        Assert.assertArrayEquals(new int[]{Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY},floorMap[0]);
//        Assert.assertArrayEquals(new int[]{Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY,Map.EMPTY},floorMap[1]);
//        Assert.assertArrayEquals(new int[]{1,0,2,0,3},floorMap[2]);
//        Assert.assertArrayEquals(new int[]{0,4,0,5,0},floorMap[3]);
//        Assert.assertArrayEquals(new int[]{6,6,6,6,6},floorMap[4]);
//    }
//    @Test
//    public void makeFloorMapTest3(){
//        boolean[][] map = {
//                {Map.BBLOCKED,Map.BEMPTY,Map.BBLOCKED,Map.BEMPTY,Map.BEMPTY}
//        };
//        TileResource.TILE[][] tiles = {
//                {TileResource.TILE.WALKABLE,null,TileResource.TILE.WALKABLE,null,null},
//        };
//        Array<Coordinates> edges = Map.findEdges(map);
//
//        int[][] floorMap = CorrectPath.makeFloorMap(edges,tiles,new FloorGraph());
//
//        for(int i = 0; i < floorMap.length; i++){
//            for(int j = 0; j < floorMap[0].length; j++){
//                System.out.print(floorMap[i][j] + " ");
//            }
//            System.out.println();
//        }
//
//        Assert.assertArrayEquals(new int[]{1,0,2,0,0},floorMap[0]);
//    }


}