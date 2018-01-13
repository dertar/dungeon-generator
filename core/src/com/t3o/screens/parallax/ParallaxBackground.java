package com.t3o.screens.parallax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.t3o.screens.Ratio;

public class ParallaxBackground {
    ParallaxLayer []layers;

    Vector2 oldPosition;
    Vector2 attachedPos;

    public ParallaxBackground(final ParallaxLayer[] layers){
        this.layers = layers;


    }

    public void attachPos(final Vector2 attachedPos){
        this.attachedPos = attachedPos;
        oldPosition = new Vector2(attachedPos.x,attachedPos.y);
    }

    public void render(final SpriteBatch batch, final Matrix4 projection,final float width, final float height){
        batch.setProjectionMatrix(projection);
        batch.begin();
        for (ParallaxLayer layer : layers) {

            float x = - width / 2 - layer.posX,
                    y = - height / 2 - layer.posY;

            batch.draw(layer.region, x - width , y,
                    Ratio.getScreen("orthoWidth"),Ratio.getScreen("orthoHeight"));

            batch.draw(layer.region, x - width , y + height,
                    Ratio.getScreen("orthoWidth"),Ratio.getScreen("orthoHeight"));

            batch.draw(layer.region, x - width , y - height,
                    Ratio.getScreen("orthoWidth"),Ratio.getScreen("orthoHeight"));

            batch.draw(layer.region, x , y - height,
                    Ratio.getScreen("orthoWidth"),Ratio.getScreen("orthoHeight"));


            batch.draw(layer.region,  x, y,
                    Ratio.getScreen("orthoWidth"),Ratio.getScreen("orthoHeight"));


            batch.draw(layer.region, x  , y + height,
                    Ratio.getScreen("orthoWidth"),Ratio.getScreen("orthoHeight"));


            batch.draw(layer.region, x + width , y + height,
                    Ratio.getScreen("orthoWidth"),Ratio.getScreen("orthoHeight"));

            batch.draw(layer.region, x + width , y - height,
                    Ratio.getScreen("orthoWidth"),Ratio.getScreen("orthoHeight"));

            batch.draw(layer.region, x + width , y,
                    Ratio.getScreen("orthoWidth"),Ratio.getScreen("orthoHeight"));


        }
        batch.end();
    }

    public void moveX(float delta,float deltaX){
        for(ParallaxLayer layer : layers){
            layer.moveX(delta,deltaX);
        }
    }

    public void moveY(float delta,float deltaY){
        for(ParallaxLayer layer : layers){
            layer.moveY(delta,deltaY);
        }
    }

    public void update(float delta){
        if(attachedPos != null) {
            Vector2 deltaPos = oldPosition.sub(attachedPos);

            if (!deltaPos.isZero()) {

                moveX(delta,deltaPos.x);
                moveY(delta,deltaPos.y);
            }

            oldPosition.x = attachedPos.x;
            oldPosition.y = attachedPos.y;

        }else{
            Gdx.app.error("Parallax.update","attachedPos is null");
        }
    }


}
