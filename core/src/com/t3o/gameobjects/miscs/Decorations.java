package com.t3o.gameobjects.miscs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.t3o.assets.resources.DecorationResource;
import com.t3o.utils.DecorationStruct;
import com.t3o.utils.LightContainer;

public class Decorations {
    public Array<Entity> eStaticDecor;
    public Array<Entity> eDecor;

    public Array<LightContainer> lights;

    public Decorations(){
        eStaticDecor = new Array<Entity>();
        eDecor = new Array<Entity>();

        lights = new Array<LightContainer>();
    }

    public void clear() {
        eDecor.clear();
        eStaticDecor.clear();
        lights.clear();
    }


}
