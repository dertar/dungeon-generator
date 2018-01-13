package com.t3o.gameworld.comparators;


import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.t3o.gameworld.components.PositionComponent;

import java.util.Comparator;


public class LayerZComparator implements Comparator<Entity> {
    private ComponentMapper<PositionComponent> componentPosition;

    public LayerZComparator(){
        componentPosition = ComponentMapper.getFor(PositionComponent.class);
    }

    @Override
    public int compare(Entity A, Entity B){
        return (int) Math.signum(componentPosition.get(B).position.z - componentPosition.get(A).position.z);
    }
}
