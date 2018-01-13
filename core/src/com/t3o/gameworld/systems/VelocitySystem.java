package com.t3o.gameworld.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.t3o.gameworld.components.PositionComponent;
import com.t3o.gameworld.components.VelocityComponent;

public class VelocitySystem extends IteratingSystem{
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> mm = ComponentMapper.getFor(VelocityComponent.class);

    public VelocitySystem () {
        super(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {
        PositionComponent position = pm.get(entity);
        VelocityComponent movement = mm.get(entity);

        position.position.x += movement.velocity.x * deltaTime;
        position.position.y += movement.velocity.y * deltaTime;
    }
}
