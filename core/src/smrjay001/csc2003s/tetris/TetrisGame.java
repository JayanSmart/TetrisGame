package smrjay001.csc2003s.tetris;

import com.badlogic.gdx.ApplicationAdapter;

/**
 * This controls the flow of the program and initializes the game.
 */
public class TetrisGame extends ApplicationAdapter {
	Splash splash;
	GameScreen gameScreen;
	MainMenu menu;
	
	@Override
	public void create () {
		splash = new Splash();
		splash.run();
		gameScreen = new GameScreen();
		gameScreen.create();
		menu = new MainMenu(this);
		menu.create();
	}

	@Override
	public void render () {
		menu.render();
	}
	
	@Override
	public void dispose () {
		System.out.println("Disposing");
		splash.dispose();
		menu.dispose();
		gameScreen.dispose();
		System.out.println("Disposed");
		System.exit(0);
	}
}
