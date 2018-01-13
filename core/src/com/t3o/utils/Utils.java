package com.t3o.utils;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import java.util.*;

public class Utils {

    static public final Random random = new Random();

    static public boolean rndBool(){
        return random.nextBoolean();
    }

    static public int rndInteger(int min,int max){
        return random.nextInt(max - min + 1) + min;
    }


    static public <T> boolean isContains(T[] set, T n){
        for(T a : set){
            if(a.equals(n)){
                return true;
            }
        }
        return false;
    }

    static public <T> boolean isContains(Array<T> set, T n){
        for(T a : set){
            if(a.equals(n)){
                return true;
            }
        }
        return false;
    }

    static public int max(int[][] matrix){
        if (matrix.length == 0) return -1;
        int ret = matrix[0][0];

        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(matrix[i][j] > ret) ret = matrix[i][j];
            }
        }
        return ret;
    }

    static public boolean[][] copy(boolean[][] matrix){
        boolean[][] ret = new boolean[matrix.length][matrix[0].length];

        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                ret[i][j] = matrix[i][j];
            }
        }

        return ret;
    }

    static public void fill(boolean[][] matrix, boolean b){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                matrix[i][j] = b;
            }
        }
    }
    static public void fill(int[][] matrix, int b){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                matrix[i][j] = b;
            }
        }
    }

    static public <T> String toString(T[] array){
        String ret = "[ ";
        for (int i = 0; i < array.length; i++) ret += array[i] + " ";

        return ret + "]";
    }

    static public <T> String toString(T[][] matrix){
        String ret = "";
        for (int i = 0; i < matrix.length; i++){
            ret += "[ ";
            for(int j = 0; j < matrix[0].length; j++){
                ret += matrix[i][j] + " ";
            }
            ret += "],\n";
        }

        return ret;
    }

    static public Integer[] wrap(int[] array){
        Integer[] ret = new Integer[array.length];

        for(int i = 0; i < array.length; i++){
            ret[i] = new Integer(array[i]);
        }

        return ret;
    }

    static public Float[] wrap(float[] array){
        Float[] ret = new Float[array.length];

        for(int i = 0; i < array.length; i++){
            ret[i] = new Float(array[i]);
        }

        return ret;
    }
    static public Boolean[][] wrap(boolean[][] array){
        Boolean[][] ret = new Boolean[array.length][array[0].length];

        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[0].length; j++) {
                ret[i][j] = new Boolean(array[i][j]);
            }
        }

        return ret;
    }
    static public Float[][] wrap(float[][] array){
        Float[][] ret = new Float[array.length][array[0].length];

        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[0].length; j++) {
                ret[i][j] = new Float(array[i][j]);
            }
        }

        return ret;
    }

    static public Integer[][] wrap(int[][] array){
        Integer[][] ret = new Integer[array.length][array[0].length];

        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[0].length; j++) {
                ret[i][j] = new Integer(array[i][j]);
            }
        }

        return ret;
    }

    static public int distance(int xA, int xB, int yA, int yB){
        return (int)Math.sqrt( Math.pow(xA - xB,2) + Math.pow(yA - yB,2) );
    }

    static public boolean isFringle(int x,int y,int width,int height){
        return x < 0 || y < 0 || x >= width || y >= height;
    }

}
