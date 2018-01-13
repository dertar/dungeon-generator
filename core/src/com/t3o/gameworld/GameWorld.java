package com.t3o.gameworld;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.t3o.assets.Assets;
import com.t3o.assets.resources.DecorationResource;
import com.t3o.gameobjects.miscs.Decorations;
import com.t3o.gameobjects.subjects.Player;
import com.t3o.gameobjects.userdata.UserData;
import com.t3o.gameworld.components.*;
import com.t3o.gameworld.systems.PhysicsSystem;
import com.t3o.map.Tile;
import com.t3o.map.WorldMap;
import com.t3o.screens.GameScreen;
import com.t3o.screens.Ratio;
import com.t3o.utils.*;

public class GameWorld extends GameModule{
    World world = new World(new Vector2(0f,-40f),true);
    PooledEngine pooledEngine = null;
    PhysicsSystem physicsSystem = null;

    private WorldMap map;

    public Player player = null;

    Array<Entity> entitiesBodyTile;
    Decorations decorations;


    Assets assets;


    public GameWorld(final GameScreen gameScreen,final Assets assets){
        super(gameScreen);

        this.assets = assets;

        physicsSystem = new PhysicsSystem(world);
        map = new WorldMap(assets);
        decorations = new Decorations();
    }

    @Override
    public void init(){
        pooledEngine = new PooledEngine();

        pooledEngine.addSystem(getRender().getSystem());
        pooledEngine.addSystem(physicsSystem);

        pooledEngine.addSystem(getRender().getAttach());

        pooledEngine.addSystem(getRender().getAnimationSystem());
        pooledEngine.addSystem(getInput().getMovementSystem());
    }


    public void newGame(){
        physicsSystem.stop();

        clearBodies();

        generateMap();

        if(map.inputs.size == 0){
            Gdx.app.error("GameWorld.newGame","not found input coordinates");
        }
        int iCell = Utils.rndInteger(0,map.inputs.size - 1);

        player = new Player(
                physicsSystem.createBody(
                        Box2DScale.toBox2D(map.inputs.get(iCell).x*32),
                        Box2DScale.toBox2D(map.inputs.get(iCell).y*32),
                        Box2DScale.toBox2D(Player.SIZE.x),
                        Box2DScale.toBox2D(Player.SIZE.y),
                        BodyDef.BodyType.DynamicBody,
                        true,false,true,
                        PhysicsSystem.BIT_SUBJECTS,
                        (short)(PhysicsSystem.BIT_PLATFROMS | PhysicsSystem.BIT_SUBJECTS) )
                );
        player.getBody().setUserData(new UserData(UserData.TYPE.SUBJECT));

        getRender().lightSystem.createPlayerLight(player.getBody(),64);

        pooledEngine.addEntity(player.getEntity().add(new AttachComponent(player)).add(new MovementComponent(player)).add(new StateComponent("PLAYER")));

        getRender().getAttach().setFringle(map.getWidth() * 32 ,map.getHeight() * 32,false);

        getRender().parallax.attachPos(player.getPosition());



        physicsSystem.start();

    }

    private /*Array<Entity>*/ void spawnDecorations(){

        decorations.clear();


        for(DecorationStruct struct : map.decorationStructs){
            Entity entity = new Entity();

            if(struct.isStatic){
                entity.add(new TextureComponent(assets.findTexture(struct.name),struct.isMirrored))
                        .add(new ColorBatchComponent())
                        .add(new PositionComponent(struct.pos.x,
                                struct.pos.y,
                                struct.layer, struct.scale));

                decorations.eStaticDecor.add(entity);
            }else{

                if(struct.lightContainer != null){
                    decorations.lights.add(struct.lightContainer);
                }

                entity.add(new TextureComponent(struct.isMirrored))
                        .add(new AnimationComponent(struct.name, struct.delta))
                        .add(new PositionComponent(struct.pos.x, struct.pos.y, struct.layer, struct.scale))
                        .add(new ColorBatchComponent());

                decorations.eDecor.add(entity);



            }

            pooledEngine.addEntity(entity);
        }
        Gdx.app.log("GW.spawnDecorations", "e : " + decorations.eDecor.size );
    }

    Array<Entity> createLights(Array<LightContainer> lights, float radius){
        Array<Entity> ret = new Array<Entity>();
        getRender().lightSystem.clear();

        for(LightContainer it : lights){

            Body body = createBodyLight(it.x,it.y,radius);

            LightComponent lightComponent = new LightComponent();
            lightComponent.minDist = Box2DScale.toBox2D(it.pxDistMin);
            lightComponent.maxDist = Box2DScale.toBox2D(it.pxDistMax);
            lightComponent.light = getRender().lightSystem.createLight(body, (int)MathUtils.random(it.pxDistMin,it.pxDistMax),it.color,it.rays);

            ret.add(new Entity().add(lightComponent));
        }

        return ret;

    }

    private void generateMap(){
        physicsSystem.stop();
        physicsSystem.destroyAll();

        clearEngine();

        map.generate();

        makeTiles( map.map, Ratio.sizeTile, 1f ,1f, new ColorBatchComponent()) ;

        makeTiles( map.background, Ratio.sizeTileBG,2f,1.5f, new ColorBatchComponent(0.6f,0.6f,0.6f,1f) ) ;


        entitiesBodyTile = createBodyRects(map.bodyRects,Ratio.sizeTile);

        spawnDecorations();



        Array<Entity> lights = createLights(decorations.lights,4f);



        appendToEngine(lights);


        physicsSystem.start();

    }


    Body createBodyLight(float x,float y,float radius){

        return physicsSystem.createBody(
                Box2DScale.toBox2D(x),
                Box2DScale.toBox2D(y),
                Box2DScale.toBox2D( radius),
                Box2DScale.toBox2D( radius),
                BodyDef.BodyType.StaticBody,
                false,true,false, PhysicsSystem.BIT_PASSIVE,
                (short)(PhysicsSystem.BIT_PASSIVE | PhysicsSystem.BIT_PLATFROMS) );
    }


    /*private  Array<Entity> createBodyWall(Array<Coordinates> walls,float size){
        Array<Entity> ret = new Array<Entity>();
        for(Coordinates it : walls){
            Body body = physicsSystem.createBody(
                    Box2DScale.toBox2D(it.x * size),
                    Box2DScale.toBox2D(it.y * size),
                    Box2DScale.toBox2D( size),
                    Box2DScale.toBox2D( size),
                    BodyDef.BodyType.StaticBody,
                    true);


            ret.add(new Entity().add(new BodyComponent(body)));

        }
        appendToEngine(ret);

        Gdx.app.log("GW.createBodyWall","added bodies " + walls.size + " to engine");
        return ret;
    }*/

    private Array<Entity> createBodyRects(Array<ShapeRect> rects,float size){
        Array<Entity> ret = new Array<Entity>();
        for(ShapeRect it : rects){

            Body body = physicsSystem.createBody(
                    Box2DScale.toBox2D(it.xy.x * size),
                    Box2DScale.toBox2D(it.xy.y * size),
                    Box2DScale.toBox2D(it.size.y * size),
                    Box2DScale.toBox2D(it.size.x * size),
                    BodyDef.BodyType.StaticBody,
                    true,true,false,PhysicsSystem.BIT_PLATFROMS,
                    (short)(PhysicsSystem.BIT_PLATFROMS | PhysicsSystem.BIT_SUBJECTS));

            body.setUserData(it.getData());
            if(it.getData().equals(UserData.TYPE.WALL)){
                body.getFixtureList().first().setFriction(0f);
            }
            ret.add(new Entity().add(new BodyComponent(body)));
        }

        appendToEngine(ret);

        Gdx.app.log("GW.createBodyWall","added bodies " + rects.size + " to engine");
        return ret;
    }

    private void makeTiles(String [][]map, float size, float layer, float scale, ColorBatchComponent colorBatchComponent){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] != null){
                    pooledEngine.addEntity(genEntityRender(map[i][j],(int)(i * size),(int)(j * size),size,layer,scale,colorBatchComponent));
                }
            }
        }
    }

    private void makeTiles(Tile[][]map, float size, float layer, float scale, ColorBatchComponent colorBatchComponent){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] != null && map[i][j].getTile() != null){
                    pooledEngine.addEntity(genEntityRender(map[i][j],(int)(i * size),(int)(j * size),size,layer,scale,colorBatchComponent));
                }
            }
        }
    }

    public void appendToEngine(Array<Entity> entities){
        for(Entity entity : entities){
            pooledEngine.addEntity(entity);
        }
    }

    public void removeFromEngine(Array<Entity> entities){
        for(Entity entity : entities){
            pooledEngine.removeEntity(entity);
        }
    }

    private Entity genEntityRender(Tile tile, int x, int y, float size, float layer, float scale, ColorBatchComponent colorBatchComponent){
        /*return new Entity()
                .add(new PositionComponent(x,y, layer,scale))
                .add(new TextureComponent( assets.getTile(tile.getTileByIndex()) ))
                .add(colorBatchComponent);*/
        return genEntityRender(tile.getTile(),x,y,size,layer,scale,colorBatchComponent);
    }

    private Entity genEntityRender(String tileName, int x, int y, float size, float layer, float scale, ColorBatchComponent colorBatchComponent){
        return new Entity()
                .add(new PositionComponent(x,y, layer,scale))
                .add(new TextureComponent(assets.getTile(tileName)))
                .add(colorBatchComponent);
    }


    /*private Entity genEntityBody(float x, float y,float width, float height, BodyDef.BodyType type){
        return genEntityBody(x,y,width,height,physicsSystem.createBody(x * width,y * height,width,height, type,true));
    }*/

    private Entity genEntityBody(float x,float y, float width,float height, Body body){
        return new Entity()
                .add(new PositionComponent(x * width,y * height))
                .add(new BodyComponent(body));
    }




    public void update(float delta){
        pooledEngine.update(delta);
    }


    public void clearEngine(){
        pooledEngine.removeAllEntities();
    }


    public World getWorld(){
        return world;
    }

    public PooledEngine getEngine(){
        return pooledEngine;
    }

    public WorldMap getMap(){
        return map;
    }

    private void clearBodies(){
        getRender().lightSystem.getRayHandler().removeAll();

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for(Body body : bodies){
            world.destroyBody(body);
        }
    }
    public Array<Entity> getEntitiesBodyTile(){
        return entitiesBodyTile;
    }

    public void dispose(){
        world.dispose();
    }
}
