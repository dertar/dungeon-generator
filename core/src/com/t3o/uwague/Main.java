package com.t3o.uwague;

import com.badlogic.gdx.*;
import com.t3o.assets.Assets;
import com.t3o.screens.GameScreen;
import com.t3o.screens.Ratio;
import com.t3o.screens.splash.LoadSplash;

public class Main extends Game{
	private GameScreen gameScreen = null;
	Assets assets;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);
		Gdx.input.setCatchBackKey(true);

		assets = new Assets();

		Ratio.process();

        setScreen(new LoadSplash(this,assets));
		//postLoadSplash();
	}


	@Override
	public void render(){
		super.render();
	}

	public void postLoadSplash(){
		assets.postLoad();
		gameScreen = new GameScreen(assets);
		setScreen(gameScreen);


		gameScreen.newGame();


		Gdx.app.log("Main","postLoadSplash over");
	}

	@Override
	public void dispose(){
		assets.dispose();

		gameScreen.dispose();
	}
}
