package smrjay001.csc2003s.tetris.screens;

import com.badlogic.gdx.Screen;
import smrjay001.csc2003s.tetris.TetrisGame;

public class LoadingScreen implements Screen {
	private TetrisGame parent;

	public LoadingScreen(TetrisGame parent) {
		this.parent = parent;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		parent.changeScreen(TetrisGame.MENU);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
