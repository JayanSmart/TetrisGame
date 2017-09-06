package smrjay001.csc2003s.tetris.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.json.simple.JSONObject;
import smrjay001.csc2003s.tetris.TetrisGame;

/**
 * This screen shows Game details such as High Scores and Game Difficulty.
 */
public class ConfigScreen implements Screen {

	private TetrisGame parent;
	private JSONObject settings;
	private Stage stage;
	private Skin skin;

	private final String HIGH_SCORE			= "HighScore";
	private final String HIGH_SCORE_PLAYER	= "HighScorePlayer";
	private final String DIFFICULTY			= "Difficulty";;

	private Label title;


	public ConfigScreen(TetrisGame parent, JSONObject settings) {
		this.parent = parent;
		this.settings = settings;

		stage = new Stage(new ScreenViewport());
	}

	@Override
	public void show() {

		this.skin = new Skin(Gdx.files.internal("assets/skins/skin/uiskin.json"));

		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);

		TextButton backButton = new TextButton("BACK", skin);

		title = new Label("Game Details", skin);
		title.setAlignment(Align.center);


		table.add(title).fillX();
		table.row().pad(20, 0,20,  0);

		Label temp = new Label("Difficulty:", skin);
		temp.setColor(Color.ROYAL);
		table.add(temp).left();
		table.add(new Label(settings.get(DIFFICULTY).toString(), skin)).right();
		table.row();

		temp = new Label("High Score:", skin);
		temp.setColor(Color.ROYAL);
		table.add(temp).left();
		table.add(new Label(settings.get(HIGH_SCORE).toString(), skin)).right();
		table.row();

		temp = new Label("By:", skin);
		temp.setColor(Color.ROYAL);
		table.add(temp).center();
		table.add(new Label(settings.get(HIGH_SCORE_PLAYER).toString(), skin)).right();
		table.row().pad(0,0,20,0);

		table.add(backButton);

		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(TetrisGame.MENU);
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
