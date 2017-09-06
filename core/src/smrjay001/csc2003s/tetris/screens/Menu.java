package smrjay001.csc2003s.tetris.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import smrjay001.csc2003s.tetris.TetrisGame;


/**
 * The Main Menu Screen, this screen can run the Game, Exit and show Game Details.
 */
public class Menu implements Screen {

	private TetrisGame parent;
	private Stage stage;
	private Skin skin;

	public Menu (TetrisGame parent) {

		this.parent = parent;

		stage = new Stage(new ScreenViewport());
	}

	@Override
	public void show() {
		Table table = new Table();
		table.setFillParent(true);
		Gdx.input.setInputProcessor(stage);

		stage.addActor(table);
		skin = new Skin(Gdx.files.internal("assets/skins/skin/uiskin.json"));

		//create buttons
		TextButton newGameButton = new TextButton("New Game", skin);
		TextButton detailsButton = new TextButton("Game Details", skin);
		TextButton exitButton = new TextButton("Exit", skin);

		//Create Heading
		Label title = new Label("TETRIS FOR 2!", skin);
		title.setFontScale(5.0f);
		title.setAlignment(Align.center);
		table.add(title);
		table.row().pad(10, 0, 10, 0);

		//add buttons to table
		table.add(newGameButton).fillX().uniformX();
		table.row();
		table.add(detailsButton).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(exitButton).fillX().uniformX();

		// create button listeners
		exitButton.addListener(new ChangeListener() {
			@Override
			public void changed(com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent event, Actor actor) {
				stage.clear();
				Gdx.app.exit();
			}
		});

		newGameButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.clear();
				parent.changeScreen(TetrisGame.APPLICATION);
			}
		});

		detailsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.clear();
				parent.changeScreen(TetrisGame.PREFERENCES);
			}
		});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		//Keep buttons in the middle
		stage.getViewport().update(width, height, true);
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
		// dispose of assets when not needed anymore
		stage.dispose();
		skin.dispose();
	}

}
