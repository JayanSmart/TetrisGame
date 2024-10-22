package smrjay001.csc2003s.tetris.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import smrjay001.csc2003s.tetris.TetrisGame;

class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Tetris for 2!";
		config.height = 800;
		config.width = 1200;
		new LwjglApplication(new TetrisGame(), config);
	}
}