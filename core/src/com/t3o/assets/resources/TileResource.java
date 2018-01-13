package com.t3o.assets.resources;

import com.t3o.map.Pattern;

public class TileResource {

    public Pattern pattern;

    public String[][] textureName;

    public boolean isBg;
    public boolean canStep;
    public boolean isDecor;


    public float chance;

    public TileResource(){
        isBg = false;
        canStep = false;
        isDecor = false;

        textureName = null;

        chance = 1f;
    }

    static public boolean isCorrect(TileResource resource){
        return (resource.textureName.length + 2 == resource.pattern.pattern.length ) &&
                (resource.textureName[0].length + 2 == resource.pattern.pattern[0].length);
    }

    public int getWeight(){
        return textureName.length * textureName[0].length;
    }

}
