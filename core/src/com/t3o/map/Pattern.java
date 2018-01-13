package com.t3o.map;

import com.badlogic.gdx.Gdx;
import com.t3o.utils.Utils;

public class Pattern {

    public enum PATTERN{
        BLOCKED,NONE,EMPTY
    }


    public PATTERN[][] pattern;


    public boolean isApproach(boolean[][] bmap,int i,int j){

        boolean ret = true;
        for(int pI = 0,mJ = j; pI < pattern.length; pI++, mJ--){
            for(int pJ = 0,mI = i; pJ < pattern[0].length; pJ++, mI++) {
                if(!Utils.isFringle( mI,mJ,bmap.length,bmap[0].length)) {

                    if(pattern[pI][pJ].equals(PATTERN.NONE)) continue;

                    if (pattern[pI][pJ].equals(PATTERN.BLOCKED) && (bmap[mI][ mJ] != WorldMap.BBLOCKED))
                        ret = false;
                    else if (pattern[pI][pJ].equals(PATTERN.EMPTY) && (bmap[mI][mJ] != WorldMap.BEMPTY))
                        ret = false;

                }else if ( pattern[pI][pJ].equals(PATTERN.EMPTY) ){
                    ret = false;
                }
            }
        }


        return ret;
    }


    @Override
    public String toString(){
        String ret = "";
        for(int i  = 0; i < pattern.length; i++){
            for(int j = 0; j < pattern[j].length;j++){
                ret += pattern.toString() + " ";
            }
            ret += "\n";
        }
        return ret;

    }

    public int getWeigth(){
        return pattern.length * pattern[0].length;
    }

}
