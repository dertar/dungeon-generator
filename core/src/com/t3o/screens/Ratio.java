package com.t3o.screens;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;

public class Ratio {
    static private HashMap<String,Float> dimensionScreen = null;

    static public float sizeTile = 32f;
    static public float sizeTileBG = 48f;
    static public float sizeRatio = Ratio.sizeTileBG/Ratio.sizeTile;

    static public void process(){
        dimensionScreen = new HashMap<String, Float>();
//
        dimensionScreen.put("width",800f);
        dimensionScreen.put("height",480f);


        dimensionScreen.put("orthoWidth",dimensionScreen.get("width")/2);
        dimensionScreen.put("orthoHeight",dimensionScreen.get("height")/2);

        dimensionScreen.put("realWidth",(float)Gdx.graphics.getWidth());
        dimensionScreen.put("realHeight",(float)Gdx.graphics.getHeight());


        dimensionScreen.put("aspectRatio", dimensionScreen.get("realWidth") / dimensionScreen.get("realHeight") );
    }

    static public float getScreen(String key){
        if(dimensionScreen.containsKey(key)){
            return dimensionScreen.get(key);
        }else{
            Gdx.app.error("Ratio.getScreen","Error can't find key " + key);
        }
        return 0f;
    }


}
