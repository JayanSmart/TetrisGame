package smrjay001.csc2003s.tetris;

import com.badlogic.gdx.Game;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import smrjay001.csc2003s.tetris.screens.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TetrisGame extends Game {

	private JSONParser parser;
	private JSONObject settings;

	private LoadingScreen loadingScreen;
	private ConfigScreen configScreen;
	private ApplicationScreen applicationScreen;
	private Menu menuScreen;
	private EndScreen endScreen;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;

	public final static String ACHIEVEMENTS	 		= "Achievements";
	public final static String HIGH_SCORE			= "HighScore";
	public final static String HIGH_SCORE_PLAYER	= "HighScorePlayer";
	public final static String DIFFICULTY			= "Difficulty";
	public final static String GAME_WIDTH           = "GameWidth";
	public final static String GAME_LENGTH          = "GameLength";

	public final static String CONFIG_PATH 			= "assets/readable/settings.json";

	//Achievement Properties
	public final static String NUMBER				= "Number";
	public final static String NAME               	= "Name";
	public final static String COMPLETED          	= "Completed";
	public final static String MULTIPLIER         	= "Multiplier";
	public final static String TRIGGER            	= "Trigger";
	public final static String POINTS				= "Points";

	//General triggers
	public final String TRUE 						= "True";
	public final String FALSE 						= "False";

	@Override
	public void create () {

		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);


		parser = new JSONParser();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	/**
	 * This manages the switching from one screen in the Game.
	 * @param screen Screen Alias passed as an int. Aliases are stored as satic final ints, for ease of readability.
	 */
	public void changeScreen(int screen) {
		getConfig();
		switch (screen) {
			case MENU:
				if(menuScreen == null) menuScreen = new Menu(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(configScreen == null) configScreen = new ConfigScreen(this, settings);
				this.setScreen(configScreen);
				break;
			case APPLICATION:
				if(applicationScreen == null) applicationScreen = new ApplicationScreen(this, settings);
				this.setScreen(applicationScreen);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
		}
	}

	/**
	 * Fetch and set all the data that the system stores from the previous session into the game_properties map.
	 */
	private void getConfig() {
		if (parser != null) try {

			settings = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream(CONFIG_PATH)));

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dispose () {

	}


}
