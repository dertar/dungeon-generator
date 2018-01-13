package com.t3o.screens.parallax;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ParallaxLayer {


    public TextureRegion region;

    public float ratioX,ratioY;


    public float posX,posY;

    public String regionName;

    public ParallaxLayer(TextureRegion region,float ratioX,float ratioY){
        this.region = region;
        this.ratioX = ratioX;
        this.ratioY = ratioY;

        posX = posY = 0f;
    }

    public ParallaxLayer() {
    }


    public void moveX(float delta,float x){
        posX += delta * (ratioX * x);
    }

    public void moveY(float delta,float y){
        posY += delta * (ratioY * y);
    }
}
