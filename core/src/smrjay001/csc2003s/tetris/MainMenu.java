package smrjay001.csc2003s.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/**
 * Main Menu for the tetris game
 */
public class MainMenu extends ApplicationAdapter{

	private TetrisGame game;
	private GameScreen screen;
	private Stage stage;

	MainMenu(TetrisGame game) {
		this.game = game;
	}

	@Override
	public void create() {
		Skin menuSkin = new Skin(Gdx.files.internal("ui/skin/quantum-horizon-ui.json"));
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		Button play = new ImageTextButton("PLAY", menuSkin);
		play.setFillParent(false);
		play.getStyle().up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/raw/button.png"))));
		play.getStyle().down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/raw/button-pressed.png"))));
		play.getStyle().over = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/raw/button.png"))));
		play.setSize(100, 50);
		play.setPosition(200, 300);
		play.addListener(new InputListener() {
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				screen = new GameScreen();
				screen.create();
				screen.render();
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("Pressed a button");
				return true;
			}
		});
		stage.addActor(play);
		Button highscore = new ImageTextButton("HIGH SCORE", menuSkin);
		highscore.getStyle().up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/raw/button.png"))));
		highscore.getStyle().down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/raw/button-pressed.png"))));
		highscore.setSize(260, 50);
		highscore.setPosition(100, 200);
		highscore.addListener(new InputListener() {
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("UP");
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("Pressed a button");
				return true;
			}
		});
		stage.addActor(highscore);
		Button exit = new ImageTextButton("EXIT", menuSkin);
		exit.getStyle().up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/raw/button.png"))));
		exit.getStyle().down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/raw/button-pressed.png"))));
		exit.setSize(100, 50);
		exit.setPosition(200, 100);
		exit.addListener(new InputListener() {
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("UP");
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("Pressed a button");
				game.dispose();
				return true;
			}
		});
		stage.addActor(exit);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() { stage.dispose(); }
}
