package com.t3o.map;

import com.badlogic.gdx.Gdx;
import com.t3o.assets.resources.PatternBlockResource;
import com.t3o.assets.resources.TileResource;

public class Tile {


    boolean bool;
    String tileName;
    PatternBlockResource.PATTERNS pattern;
    boolean canStep;

    public Tile(){
        tileName = null;
        pattern = PatternBlockResource.PATTERNS.EMPTY;
    }

    public void setBool(boolean bool){
        this.bool = bool;
    }

    public void setPattern(PatternBlockResource.PATTERNS pattern){
        this.pattern = pattern;
    }

    public boolean getBool() {
        return bool;
    }

    public void setTile(String tileName){
        this.tileName = tileName;
    }

    public void setTile(String tileName,boolean canStep){
        this.tileName = tileName;
        setCanStep(canStep);
    }

    public String getTile() {
        return tileName;
    }


    public void setCanStep(boolean canStep){
        this.canStep = canStep;
    }

    public boolean canStep(){
        return this.canStep;
    }
}
