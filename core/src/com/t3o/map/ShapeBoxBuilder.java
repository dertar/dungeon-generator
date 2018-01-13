package com.t3o.map;

import com.badlogic.gdx.utils.Array;
import com.t3o.assets.Assets;
import com.t3o.assets.resources.TileResource;
import com.t3o.gameobjects.userdata.UserData;
import com.t3o.utils.Coordinates;
import com.t3o.utils.ShapeRect;
import com.t3o.utils.Utils;

import java.util.ArrayList;

public class ShapeBoxBuilder {

    static public ArrayList<ShapeRect> makeRects(boolean[][] map){
        ArrayList<ShapeRect> ret = new ArrayList<ShapeRect>();
        boolean [][]copy = Utils.copy(map);

        for(int i = 0; i < copy.length; i++){
            for(int j = 0; j < copy.length; j++){
                if(!Utils.isFringle(j,i,copy.length,copy[0].length) && copy[j][i] == WorldMap.BBLOCKED){
                    ret.add(findVerticalShape(copy,j,i));
                }
            }
        }

        return ret;
    }


    static public boolean[][] makeFringleMap(Array<Coordinates> coordinates,int width,int height){
        boolean[][] ret = new boolean[width][height];
        Utils.fill(ret, WorldMap.BEMPTY);

        for(Coordinates it : coordinates){
            ret[it.x][it.y] =  WorldMap.BBLOCKED;
        }

        return ret;
    }


    static public Array<ShapeRect> makeRects(Array<Coordinates> coordinates, Tile[][] map,final Assets assets){
        Array<ShapeRect> ret = new Array<ShapeRect>();

        boolean[][] copy = makeFringleMap(coordinates,map.length,map[0].length);

        for(int i = 0; i < copy.length; i++){
            for(int j = 0; j < copy.length; j++){
                if(!Utils.isFringle(j,i,copy.length,copy[0].length) && copy[j][i] == WorldMap.BBLOCKED){

                    if(map[j][i] != null && map[j][i].canStep){
                        ShapeRect rect = findHorizonShape(copy,j,i);
                        rect.setData(UserData.TYPE.FLOOR);
                        ret.add(rect);
                    }
                }
            }
        }

        for(int i = 0; i < copy.length; i++){
            for(int j = 0; j < copy.length; j++){
                if(!Utils.isFringle(i,j,copy.length,copy[0].length) && copy[i][j] == WorldMap.BBLOCKED){
                    if(map[i][j] != null && !map[i][j].canStep) {
                        ShapeRect rect = findVerticalShapeCap(copy, i, j, 1);
                        if (rect != null) {
                            rect.setData(UserData.TYPE.WALL);
                            ret.add(rect);
                        }
                    }
                }
            }
        }

        for(int i = 0; i < copy.length; i++){
            for(int j = 0; j < copy.length; j++){
                if(!Utils.isFringle(j,i,copy.length,copy[0].length) && copy[j][i] == WorldMap.BBLOCKED ){
                    if(map[j][i] != null && !map[j][i].canStep) {
                        ShapeRect rect = findHorizonShape(copy, j, i);
                        if (rect != null) {
                            rect.setData(UserData.TYPE.WALL);
                            ret.add(rect);
                        }
                    }
                }
            }
        }



        return ret;
    }

    static public ShapeRect findHorizonShapeCap(boolean[][]map, int x, int y,int cap){
        ShapeRect ret = new ShapeRect();
        ret.setXY(x,y);
        ret.setSize(1,1);
        map[x][y] = WorldMap.BEMPTY;
        Array<Coordinates> caps = new Array<Coordinates>();
        caps.add(new Coordinates(x, y));

        while(!Utils.isFringle(x + 1,y,map.length,map[0].length) && map[x + 1][y] == WorldMap.BBLOCKED ){
            x++;
            ret.size.y++;
            map[x][y] = WorldMap.BEMPTY;
            cap--;
            caps.add(new Coordinates(x, y));
        }

        if(cap > 0){
            for(Coordinates it : caps){
                map[it.x][it.y] = WorldMap.BBLOCKED;
            }
            ret = null;
        }

        return ret;
    }

    static private ShapeRect findVerticalShapeCap(boolean[][]map, int x, int y,int cap){
        ShapeRect ret = new ShapeRect();
        ret.setXY(x,y);
        ret.setSize(1,1);
        map[x][y] = WorldMap.BEMPTY;
        Array<Coordinates> caps = new Array<Coordinates>();
        caps.add(new Coordinates(x, y));

        while(!Utils.isFringle(x,y + 1,map.length,map[0].length) && map[x][y + 1] == WorldMap.BBLOCKED ){
            y++;
            ret.size.x++;
            map[x][y] = WorldMap.BEMPTY;
            cap--;
            caps.add(new Coordinates(x, y));
        }

        if(cap > 0){
            for(Coordinates it : caps){
                map[it.x][it.y] = WorldMap.BBLOCKED;
            }
            ret = null;
        }

        return ret;
    }


    static private ShapeRect findHorizonShape(boolean[][]map, int x, int y){
        return findHorizonShapeCap(map,x,y,0);
    }

    static private ShapeRect findVerticalShape(boolean[][]map, int x, int y){
        return findVerticalShapeCap(map,x,y,0);
    }
}
