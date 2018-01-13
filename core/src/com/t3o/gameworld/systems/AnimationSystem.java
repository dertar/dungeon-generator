package com.t3o.gameworld.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ArrayMap;
import com.t3o.gameworld.components.AnimationComponent;
import com.t3o.gameworld.components.StateComponent;
import com.t3o.gameworld.components.TextureComponent;

public class AnimationSystem extends IteratingSystem{

    ComponentMapper<TextureComponent> mapperTexture;
    ComponentMapper<AnimationComponent> mapperAnim;

    ArrayMap<String,Animation> animations;

    public AnimationSystem(final ArrayMap<String,Animation> animations) {
        super(Family.all(TextureComponent.class,AnimationComponent.class).get());

        mapperAnim = ComponentMapper.getFor(AnimationComponent.class);
        mapperTexture = ComponentMapper.getFor(TextureComponent.class);

        this.animations = animations;
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animationComponent = mapperAnim.get(entity);
        TextureComponent textureComponent = mapperTexture.get(entity);



        if( !animations.containsKey(animationComponent.animations) ){
            Gdx.app.error("AnimationSystem", animationComponent.animations);
        }else {
            textureComponent.textureRegion = animations.get( animationComponent.animations ).getKeyFrame(animationComponent.getStateTime());
        }
        //animationComponent.wipeStateTime(animationComponent.animations.get(AnimationResource.ANIMATIONS.IDLE).isAnimationFinished(animationComponent.stateTime));

        animationComponent.delta(deltaTime);


    }
}
