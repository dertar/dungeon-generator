package com.t3o.map.generators;

import com.badlogic.gdx.math.MathUtils;
import com.t3o.utils.Coordinates;
import com.t3o.utils.Utils;

public class TemplateGenerator {

    public enum ROOM{
        EMPTY,
        CAVE,
        TREASURE,
        LEFT_RIGHT,
        BOT_LEFT_RIGHT,
        TOP_BOT_LEFT_RIGHT,
        TOP_LEFT_RIGHT
    }


    static public ROOM[][] generate(int width,int height,float ratio){
        if(ratio > 0.1f && ratio < 0.9f){
            ratio = 0.5f;
        }

        int cells = width * height;
        float correctRatio = cells * ratio;
        int chance = 0;
        ROOM[][] current,best = generate(width,height);
        int   countCurrent = 0, countBest = count(best);

        do {
            current = generate(width, height);
            countCurrent = count(current);

            if( countCurrent > countBest ){
                countBest = countCurrent;
                best = current;
            }


        }while( countBest <= correctRatio && chance++ <= 10);

        return best;

    }

    static int count(ROOM[][] rooms){
        int ret = 0;
        for(int i = 0; i < rooms.length; i++){
            for(int j = 0; j < rooms[0].length; j++){
                if( isPathRoom( rooms[i][j] ))ret++;
            }
        }
        return  ret;
    }


    /*
    http://tinysubversions.com/spelunkyGen/
     */
    static public ROOM[][] generate(int width, int height){
        ROOM rooms[][] = new ROOM[width][height];
        int dice = 0;
        boolean right = true;

        Coordinates current = new Coordinates(0,0),
                    previous = new Coordinates(0,0);

        do{
            previous.set(current);
            if(rooms[current.x][current.y] != null) {
                if (right) current.x++;
                else current.x--;
            }


            if(!Utils.isFringle(current.x,current.y,width,height)) {

                dice = MathUtils.random(0, 4);

                if (dice == 0 || dice == 1) {

                    if(!Utils.isFringle(current.x,current.y - 1,width,height) &&
                            (rooms[current.x][current.y - 1] ==  ROOM.BOT_LEFT_RIGHT || rooms[current.x][current.y - 1] ==  ROOM.TOP_BOT_LEFT_RIGHT  )   ) {
                        rooms[current.x][current.y] =  ROOM.TOP_LEFT_RIGHT;
                    }else rooms[current.x][current.y] = ROOM.LEFT_RIGHT;
                } else if (dice == 2 || dice == 3) {

                    if(!Utils.isFringle(current.x,current.y - 1,width,height) &&
                            rooms[current.x][current.y - 1] ==  ROOM.TOP_LEFT_RIGHT){
                        rooms[current.x][current.y] = ROOM.TOP_BOT_LEFT_RIGHT;
                        right = !right;
                        current.y++;
                    }else {
                        rooms[current.x][current.y] = current.y == 0 ? ROOM.LEFT_RIGHT : ROOM.TOP_LEFT_RIGHT;
                    }

                }else{


                    if(!Utils.isFringle(current.x,current.y - 1,width,height) &&
                            (rooms[current.x][current.y - 1] ==  ROOM.BOT_LEFT_RIGHT || rooms[current.x][current.y - 1] ==  ROOM.TOP_BOT_LEFT_RIGHT  )   ) {
                        rooms[current.x][current.y] =  ROOM.TOP_BOT_LEFT_RIGHT;
                    }else rooms[current.x][current.y] = ROOM.BOT_LEFT_RIGHT;

                    right = !right;
                    current.y++;
                }
            }else{
                rooms[previous.x][previous.y] = ROOM.BOT_LEFT_RIGHT;
                current.set(previous);
                current.y++;
                right = !right;

            }

        }while(current.y != height);

        fillEmpty(rooms);
        fillCave(rooms,true);

        return rooms;
    }

    static void fillEmpty(ROOM [][]rooms){
        for(int i = 0; i < rooms.length; i++){
            for(int j = 0; j < rooms[0].length; j++){
                if(rooms[i][j] == null) rooms[i][j] = ROOM.EMPTY;
            }
        }
    }

    static void fillCave(ROOM [][]rooms,boolean fillCaves){
        if(fillCaves)
            fillAllCave(rooms);
        else
            fillCave(rooms);

    }

    static void fillCave(ROOM [][]rooms){
        for(int i = 0; i < rooms.length; i++){
            for(int j = 0; j < rooms[0].length; j++){
                if(rooms[i][j].equals(ROOM.EMPTY) && (isPathRoom(rooms,i + 1,j) || isPathRoom(rooms,i - 1,j))){
                    rooms[i][j] = ROOM.CAVE;
                }
            }
        }
    }


    static void fillAllCave(ROOM [][]rooms){
        for(int i = 0; i < rooms.length; i++){
            for(int j = 0; j < rooms[0].length; j++){
                if(rooms[i][j].equals(ROOM.EMPTY)){
                    rooms[i][j] = ROOM.CAVE;
                }
            }
        }
    }

    static boolean isPathRoom(ROOM [][]rooms,int i, int j){
        return !Utils.isFringle(i,j, rooms.length, rooms[0].length) && isPathRoom(rooms[i][j]);
    }

    static boolean isPathRoom(ROOM room){
        return room.equals(ROOM.LEFT_RIGHT) ||
                room.equals(ROOM.BOT_LEFT_RIGHT) ||
                room.equals(ROOM.TOP_BOT_LEFT_RIGHT) ||
                room.equals(ROOM.TOP_LEFT_RIGHT);
    }
}
