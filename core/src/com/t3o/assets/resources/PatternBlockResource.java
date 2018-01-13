package com.t3o.assets.resources;

import com.badlogic.gdx.math.MathUtils;

public class PatternBlockResource {

    public enum PATTERNS{
        EMPTY,
        SOLID_BLOCK,
        DROP_PLATFORM,
        FLOOR_TRAP,
        SHOOT_TRAP,
        WALL_TRAP,
        LIGHT,
        IN_OUT
    }

    PATTERNS p;
    float c;

    public PatternBlockResource(){
        set(PATTERNS.EMPTY,1.01f);
    }

    public PatternBlockResource(PATTERNS pattern){
        set(pattern,1.01f);
    }

    public PatternBlockResource(PATTERNS pattern, float chance){
        set(pattern,chance);
    }

    void set(PATTERNS pattern, float chance){
        this.p = pattern;
        this.c = chance;
    }

    public float getChance() {
        return c;
    }

    public PATTERNS getPattern() {
        return p;
    }

}
