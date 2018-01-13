package com.t3o.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.t3o.gameworld.systems.MovementSystem;
import com.t3o.screens.GameScreen;

public class GameInput extends GameModule{

    private MovementSystem movementSystem = null;

    public GameInput(final GameScreen gameScreen) {
        super(gameScreen);
        movementSystem = new MovementSystem(getGameWorld().getWorld());
    }


    public void handle(){
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            getRender().getCamera().zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            getRender().getCamera().zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)) {
            getRender().getCamera().translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6)) {
            getRender().getCamera().translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_5)) {
            getRender().getCamera().translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8)) {
            getRender().getCamera().translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            getRender().getCamera().rotate(-0.5f, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            getRender().getCamera().rotate(0.5f, 0, 0, 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
            getGameWorld().newGame();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
            //getDebug().rooms(!getDebug().isRooms());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
            getDebug().showBox(!getDebug().isShowBox());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
            getDebug().userdata(!getDebug().isUserData());
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
            getRender().attachCamera(!getRender().isAttached());
        }
        //stopping

        //


    }

    @Override
    public void init() {

    }

    public MovementSystem getMovementSystem(){
        return movementSystem;
    }
}
