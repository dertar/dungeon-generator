package com.t3o.gameworld;

import com.t3o.screens.GameScreen;

abstract public class GameModule {
    private GameScreen gameScreen = null;

    public GameModule(final GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }

    public GameDebug getDebug(){
        return gameScreen.getGameDebug();
    }

    public GameInput getInput(){
        return gameScreen.getGameInput();
    }

    public GameRender getRender(){
        return gameScreen.getGameRender();
    }

    public GameWorld getGameWorld(){
        return gameScreen.getGameWorld();
    }

    abstract public void init();



}
