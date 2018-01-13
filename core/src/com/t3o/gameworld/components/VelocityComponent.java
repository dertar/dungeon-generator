package com.t3o.gameworld.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {
    public Vector2 velocity = new Vector2();

    public VelocityComponent(float xV, float xY){
        velocity.x = xV;
        velocity.y = xY;
    }

}
