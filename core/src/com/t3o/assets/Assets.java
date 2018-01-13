package com.t3o.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.t3o.assets.resources.*;
import com.t3o.gameobjects.subjects.Player;
import com.t3o.map.generators.TemplateGenerator;
import com.t3o.screens.parallax.ParallaxLayer;

import java.util.Arrays;

public class Assets {
    private final String FILE_RESOURCES = "resources.json";

    private TileResource  []tiles;

    public final ArrayMap<String,Animation> animations = new ArrayMap<String, Animation>();


    public final ArrayMap<TemplateGenerator.ROOM, Array<PatternBlockResource[][]> > roomPatterns =
            new ArrayMap<TemplateGenerator.ROOM,  Array< PatternBlockResource[][]> >();

    private ParallaxLayer[] layers;

    private DecorationResource []decorationResources;
    private Array<Integer> indexOfNoTorchesLights;
    private Array<Integer> indexOfLights;

    private StaticDecorationResource []staticDecorationResources;

    private Array<Integer> indexOfNearDecor;
    private Array<Integer> indexOfNonNearDecor;
    private Array<Integer> indexOfRoofDecor;
    private Array<Integer> indexOfRoofDynamicDecor;
//    private Array<Integer> indexOfTileDecor;


    private ArrayMap<String,String> RESOURCES_NAME = null;

    private final  AssetManager manager = new AssetManager();


    public BitmapFont preFont = null;


    String []packNames = new String[]{
        "tiles_pack","anim_player_pack","decorations_pack","parallax_pack"
    };



    public Assets(){
        preLoad();
    }

    private void preLoad(){
        //todo: load bitmap font && logo;
        RESOURCES_NAME = Saver.json.fromJson(ArrayMap.class, Gdx.files.internal(FILE_RESOURCES));

        preFont =  new BitmapFont(Gdx.files.internal(RESOURCES_NAME.get("prefont_fnt")),false);

        layers = Saver.json.fromJson(ParallaxLayer[].class,Gdx.files.internal(RESOURCES_NAME.get("parallax_json")));



        // init dyncamic decor
        decorationResources = Saver.json.fromJson(DecorationResource[].class,Gdx.files.internal(RESOURCES_NAME.get("decorations_json")));
        int size = 0;

        indexOfNoTorchesLights = new Array<Integer>();
        indexOfRoofDynamicDecor = new Array<Integer>();

        for(int i = 0; i < decorationResources.length; i++) {
            if(!decorationResources[i].isRoof) {
                if (!decorationResources[i].torch) indexOfNoTorchesLights.add(i);
            }else indexOfRoofDynamicDecor.add(i);
        }
        // init static decor
        staticDecorationResources = Saver.json.fromJson(StaticDecorationResource[].class,Gdx.files.internal(RESOURCES_NAME.get("static_decorations_json")));


        indexOfNonNearDecor = new Array<Integer>();
        indexOfNearDecor = new Array<Integer>();
        indexOfRoofDecor = new Array<Integer>();

        for(int i = 0; i < staticDecorationResources.length; i++) {
            if(staticDecorationResources[i].isFloor) {
                if (staticDecorationResources[i].isNear) indexOfNearDecor.add(i);
                else indexOfNonNearDecor.add(i);
            }else  indexOfRoofDecor.add(i);
        }
        tiles = Saver.json.fromJson(TileResource[].class,Gdx.files.internal(RESOURCES_NAME.get("tiles_json")));
//        // init index decor tiles
//        indexOfTileDecor = new Array<Integer>();
//
//        for(int i = 0; i < tiles.length; i++){
//            if(tiles[i].isDecor) indexOfTileDecor.add(i);
//        }

        indexOfLights = new Array<Integer>();

        for(int i = 0; i < decorationResources.length; i++){
            if(decorationResources[i].torch) indexOfLights.add(i);
        }


    }

    public void postLoad(){
        for(AnimationResource animation : Player.ANIMATIONS){
            animations.put(animation.name,
                    new Animation(animation.speed,manager.get(RESOURCES_NAME.get("anim_player_pack"),
                            TextureAtlas.class).findRegions(animation.name), animation.playMode));
        }

        for(DecorationResource decoration : decorationResources){
                    animations.put(decoration.animation.name,
                        new Animation(decoration.animation.speed, manager.get(RESOURCES_NAME.get("decorations_pack"),TextureAtlas.class).findRegions(decoration.animation.name),decoration.animation.playMode));
        }

        for(TileResource tile : tiles){
            if(! TileResource.isCorrect(tile) ){
                Gdx.app.error("Assets.load","incorrect tileResource " + Arrays.deepToString(tile.textureName));
            }
        }

        roomPatterns.put(TemplateGenerator.ROOM.LEFT_RIGHT,
                Saver.json.fromJson(Array.class,PatternBlockResource[][].class,Gdx.files.internal( RESOURCES_NAME.get("room_patterns_left_right") )));


        roomPatterns.put(TemplateGenerator.ROOM.TOP_BOT_LEFT_RIGHT,
                Saver.json.fromJson(Array.class,PatternBlockResource[][].class,Gdx.files.internal( RESOURCES_NAME.get("room_patterns_top_bot_left_right") )));


        roomPatterns.put(TemplateGenerator.ROOM.TOP_LEFT_RIGHT,
                Saver.json.fromJson(Array.class,PatternBlockResource[][].class,Gdx.files.internal( RESOURCES_NAME.get("room_patterns_top_left_right") )));


        roomPatterns.put(TemplateGenerator.ROOM.BOT_LEFT_RIGHT,
                Saver.json.fromJson(Array.class,PatternBlockResource[][].class,Gdx.files.internal( RESOURCES_NAME.get("room_patterns_bot_left_right") )));

        // init parallax
        for(ParallaxLayer layer : layers){
            layer.region = getTextureRegion(layer.regionName);
        }
    }


    public void  loadQueue(){
        for(String pack : packNames) {
            manager.load(
                    RESOURCES_NAME.get(pack),
                    TextureAtlas.class
            );
        }
    }





    public TextureRegion getTextureRegion(String name){

        TextureRegion ret = null;
        for(String fileName : packNames){
            ret = manager.get(RESOURCES_NAME.get(fileName),TextureAtlas.class).findRegion(name);
            if(ret != null) return ret;
        }

        Gdx.app.error("Assets.getTileRegion",  name);

        return ret;
    }





    public void dispose(){
        preFont.dispose();
        manager.dispose();
    }

    public boolean isLoaded(){
        return manager.update();
    }

    public float getProgress() {
        return manager.getProgress();
    }



    public DecorationResource rndDecor() {
        if(indexOfNoTorchesLights.size == 0){
            Gdx.app.error("assets.rndDecor","indexOfNoTorchesLights.size == 0");
            return null;
        }
        return decorationResources[  indexOfNoTorchesLights.random()  ];
    }

    public DecorationResource findDecor(String name){
        for(DecorationResource it : decorationResources){
            if(it.animation.name.equals(name)) return it;
        }

        Gdx.app.error("assets.findDecor",name);
        return null;
    }

    public StaticDecorationResource  rndStaticDecor(boolean isNear) {



        return staticDecorationResources[  isNear ?
                indexOfNearDecor.random() :
                indexOfNonNearDecor.random()
                ];
    }

    public StaticDecorationResource  rndStaticDecorRoof() {
        if(indexOfRoofDecor.size == 0){
            Gdx.app.error("assets.rndStaticDecorRoof","decor roof is empty!");
            return null;
        }

        return staticDecorationResources[ indexOfRoofDecor.random() ];
    }

    public DecorationResource  rndDynamicDecorRoof() {
        if(indexOfRoofDynamicDecor.size == 0){
            Gdx.app.error("assets.rndDynamicDecorRoof","indexOfRoofDynamicDecor.size == 0");
            return null;
        }

        return decorationResources[ indexOfRoofDynamicDecor.random() ];
    }


    public TextureAtlas.AtlasRegion findTexture(String textureName){
        TextureAtlas.AtlasRegion ret = manager.get(RESOURCES_NAME.get("decorations_pack"),TextureAtlas.class).findRegion(textureName);

        if(ret == null){
            Gdx.app.error("Assets.findTexture", "can't find " + textureName);
        }

        return ret;
    }

    public TileResource[] getTiles(){
        return tiles;
    }

//    public TileResource getRandomDecorTile(){
//        return tiles[ indexOfTileDecor.random() ];
//    }
//
//    public Integer getRandomIndexDecorTile(){
//        return indexOfTileDecor.random();
//    }


    public TileResource getTileByIndex(int i){

        if(i > tiles.length || i < 0){

            Gdx.app.error("Assets.getTileByIndex " + tiles.length, "Error index " + i + ", returned null");

            return null;
        }

        return tiles[i];
    }


    public TextureAtlas.AtlasRegion getTile(String tileName){
        TextureAtlas.AtlasRegion ret = manager.get(RESOURCES_NAME.get("tiles_pack"),TextureAtlas.class).findRegion(tileName);
        if(ret == null){
            Gdx.app.error("Assets.getTileByIndex", "can't find " + tileName);
        }

        return ret;
    }

    public ParallaxLayer[] getParallaxLayer(String name) {
        return layers;
    }

    public Array<Integer> getIndexOfLights(){
        return indexOfLights;
    }

    public DecorationResource getDecor(int index){
        return index >= 0 && index < decorationResources.length ? decorationResources[index] : null;
    }

}
