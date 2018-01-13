package com.t3o.gameworld.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureComponent implements Component {
    public TextureRegion textureRegion;
    public boolean isMirrored;

    public TextureComponent(final TextureRegion textureRegion) {
        set(textureRegion,false);
    }

    public TextureComponent(final TextureRegion textureRegion,boolean isMirrored) {
        set(textureRegion,isMirrored);
    }

    public TextureComponent() {
        set(null,false);
    }


    public TextureComponent(boolean isMirrored) {
        set(null,isMirrored);
    }

    private void set(final TextureRegion textureRegion,boolean isMirrored){
        this.textureRegion = textureRegion;
        this.isMirrored = isMirrored;
    }
}
