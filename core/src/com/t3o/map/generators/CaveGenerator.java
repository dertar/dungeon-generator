package com.t3o.map.generators;

import com.t3o.map.WorldMap;
import com.t3o.utils.Utils;

/*
    Статический класс, который генерировает карту с помощью алгоритма клеточного автомата(Cellular Automaton)
 */
public class CaveGenerator{


    public enum CAVE{
        NORMAL,GUTS,EMPTY
    }


    static public boolean[][] generate(int width,int height,CAVE type){
        if(type.equals(CAVE.GUTS)){
             return generate(width,height,0.45f,5,3,4);
        }else if(type.equals(CAVE.EMPTY)){
            return generate(width,height,0.25f,2,3,4);
        }
        return generate(width,height,0.4f,10,3,4); //generate(width,height,0.4f,4,3,3);
    }

    static public boolean[][] generate(int width,int height,CAVE type,float ratio){
        if(ratio > 0.1f && ratio < 0.9f){
            ratio = 0.5f;
        }

        int cells = width * height;
        float correctRatio = cells * ratio;
        int chance = 0;
        boolean[][] current,best = generate(width,height,type);
        int   countCurrent = 0, countBest = count(best,WorldMap.BEMPTY);

        do {
            current = generate(width, height, type);
            countCurrent = count(current, WorldMap.BEMPTY);

            if( countCurrent > countBest ){
                countBest = countCurrent;
                best = current;
            }


        }while( countBest <= correctRatio && chance++ <= 10);

        //System.out.println("" + countBest  + ":" + correctRatio);

        return best;

    }

    static int count(boolean[][] map, boolean type){
        int ret = 0;

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] == type){
                    ret++;
                }
            }
        }

        return ret;
    }

    static private boolean[][] generate(int width, int height,float alive, int steps, int deathLimit,int birthLimit){
        boolean [][]ret = initMap(width,height,alive);

        for(int i = 0; i < steps; i++){
            ret = step(ret, deathLimit, birthLimit);
        }

        return ret;
    }

    /*
        Инициализируем двоичную(boolean) карту c шириной: width и высотой: height.
        У каждой клетки, есть шанс сгенерироваться непроходимой с коэффициентом ALIVE
        true - непроходимая.
        false - проходимая.
     */
    static private boolean[][] initMap(int width, int height,float alive){
        boolean ret[][] = new boolean[width][height];

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                ret[i][j] = Utils.random.nextFloat() <= alive;
            }
        }

        return ret;
    }
    /*
       Подсчет сколько "живых соседей" (true)
     */
    static private int countNeighbors(boolean map[][],int x,int y){
        int ret = 0,nX,nY;

        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                nX = x + i;
                nY = y + j;

                if(i == 0 && j == 0) continue;
                else if(nX < 0 || nY < 0 || nX >= map.length || nY >= map[0].length) ret++;
                else if(map[nX][nY]) ret++;

                /*if( (nX != 0 && nY != 0) && (nX >= 0 && nY >= 0 && nX < map.length && nY < map[0].length) && map[nX][nY] )
                    ret++;*/
            }
        }

        return ret;
    }
    /*
        Оброботка жизненего шага.
        Создает дубликат карты.
        Подссчитаются все соседи на карте и поправилам игры выставляется логической значние.

        deathLimit,birthLimit - параметры жизни
     */
    static private boolean[][] step(boolean[][] map, int deathLimit, int birthLimit){
        boolean[][] ret = new boolean[map.length][map[0].length];
        int neighbors;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                neighbors = countNeighbors(map,i,j);
                if(map[i][j]){
                    if(neighbors < deathLimit){
                        ret[i][j] = false;
                    }
                    else{
                        ret[i][j] = true;
                    }
                } else{
                    if(neighbors > birthLimit){
                        ret[i][j] = true;
                    }
                    else{
                        ret[i][j] = false;
                    }
                }
            }
        }
        return ret;
    }

}
