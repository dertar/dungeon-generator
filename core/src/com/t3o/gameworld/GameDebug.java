package com.t3o.gameworld;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.t3o.assets.Assets;
import com.t3o.gameworld.components.ColorBatchComponent;
import com.t3o.gameworld.components.PositionComponent;
import com.t3o.gameworld.components.StringDataComponent;
import com.t3o.gameworld.systems.PhysicsDebugSystem;
import com.t3o.gameworld.systems.UserDataDebugSystem;
import com.t3o.gameworld.systems.RoomsDebugSystem;
import com.t3o.screens.GameScreen;
import com.t3o.utils.Utils;

import java.util.ArrayList;

public class GameDebug extends GameModule{
    PhysicsDebugSystem physicsDebugSystem = null;
    RoomsDebugSystem roomsDebugSystem = null;
    UserDataDebugSystem userdataDebugSystem;


    Array<Entity> entitiesRooms = null;

    Assets assets;

    BitmapFont debugFont;

    public GameDebug(final GameScreen gameScreen,final Assets assets) {
        super(gameScreen);

        this.assets = assets;

        debugFont = new BitmapFont(assets.preFont.getData(),assets.preFont.getRegion(),true);
        debugFont.getData().flipped = true;
        debugFont.isFlipped();
    }


    @Override
    public void init() {
        physicsDebugSystem = new PhysicsDebugSystem(getGameWorld().getWorld(),getRender().getCamera());
        roomsDebugSystem = new RoomsDebugSystem(getRender().getCamera(),getRender().getBatch(), debugFont);
        userdataDebugSystem = new UserDataDebugSystem(getRender().getCamera(),getRender().getBatch(), debugFont);
    }

    public void showBox(boolean on){
        if(on){
            getGameWorld().getEngine().addSystem(physicsDebugSystem);
        }else {
            getGameWorld().getEngine().removeSystem(physicsDebugSystem);
        }
    }

    public boolean isShowBox(){
        return getGameWorld().getEngine().getSystems().contains(physicsDebugSystem,true);
    }

    public void rooms(boolean isOn){
        if(isOn) {
            getGameWorld().getEngine().addSystem(roomsDebugSystem);

            entitiesRooms = getEntitiesRooms(32,false);

            getGameWorld().appendToEngine(entitiesRooms);
        }
        else {
            getGameWorld().getEngine().removeSystem(roomsDebugSystem);
            getGameWorld().removeFromEngine(entitiesRooms);
        }
    }

    public boolean isRooms(){
        return getGameWorld().getEngine().getSystems().contains(roomsDebugSystem,true);
    }

    public void userdata(boolean isOn){
        if(isOn) {
            getGameWorld().getEngine().addSystem(userdataDebugSystem);
        }
        else {
            getGameWorld().getEngine().removeSystem(userdataDebugSystem);
        }
    }

    public boolean isUserData(){
        return getGameWorld().getEngine().getSystems().contains(userdataDebugSystem,true);
    }

    private Entity genRoom(int x, int y, float r, float g, float b, String str){
        return new Entity()
                .add(new PositionComponent(x,y))
                .add(new ColorBatchComponent(r,g,b,0.75f))
                .add(new StringDataComponent(str));
    }

    private Entity genRoom(int x, int y, float[] rgb , String str) {
        if(rgb.length < 3) return genRoom(x,y,1f,1f,1f,str);
        return genRoom(x,y,rgb[0],rgb[1],rgb[2],str);
    }

    private Array<Entity> getEntitiesRooms(int sizeTile, boolean withBlock){
        Array<Entity> ret = new Array<Entity>();
        /*
        int[][] rooms = getGameWorld().getMap().roomMap;

        int max = Utils.max(rooms) + 1;

        float [][]colors = genColors(max);

        if(max >= 1) {
            //Gdx.app.log("GD.getEnitiesRooms", "Rooms: " + Utils.toString(Utils.wrap(rooms)));
            //Gdx.app.log("GD.getEnitiesRooms", "Colors: " + Utils.toString(Utils.wrap(colors)));
            for (int i = 0; i < rooms.length; i++) {
                for (int j = 0; j < rooms[0].length; j++) {
                    if(rooms[i][j] == -1 && withBlock){ ret.add(genRoom(i * sizeTile, j * sizeTile, colors[0], "0")); }
                    else if(rooms[i][j] != -1) ret.add(genRoom(i * sizeTile, j * sizeTile, colors[rooms[i][j]], rooms[i][j] + ""));
                }
            }
        }else Gdx.app.error("GD.getEntitiesRooms", "Max is Zero");

       // Gdx.app.log("Gd.getEntitiesRooms", "Max: " + Utils.max(rooms));*/
        return ret;
    }

    private float[][] genColors(int colors){
        float[][] ret = new float[colors][3];

        for(int i = 0; i < ret.length; i++){
            ret[i]= new float[]{ Utils.random.nextFloat(),Utils.random.nextFloat(),Utils.random.nextFloat()};

        }

        return ret;
    }

    public PhysicsDebugSystem getPhysicsDebugSystem(){
        return physicsDebugSystem;
    }
}
