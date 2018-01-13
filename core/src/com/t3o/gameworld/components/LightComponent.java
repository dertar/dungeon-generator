package com.t3o.gameworld.components;

import box2dLight.Light;
import box2dLight.PointLight;
import com.badlogic.ashley.core.Component;
import com.t3o.utils.Box2DScale;

public class LightComponent implements Component{
    public Light light;

    public float maxDist = Box2DScale.toBox2D(450);
    public float minDist = Box2DScale.toBox2D(350);

    public boolean force = false;
}
