package smrjay001.csc2003s.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

public class TetrisGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture grey_block;
	private Player activePlayer;

	private Random random;
	private Timer.Task moveDownTask;

	private int game_width, game_length;
	private boolean game_over;
	private Array<Player> players;

	private JSONParser parser;
	private JSONObject settings;
	private JSONArray achievements;

	private GameMap gameMap;
	private BitmapFont font;
	private BitmapFont uncompletedFont;
	private BitmapFont completedFont;

	private final String ACHIEVEMENTS	 	= "Achievements";
	private final String HIGH_SCORE			= "HighScore";
	private final String HIGH_SCORE_PLAYER	= "HighScorePlayer";
	private final String DIFFICULTY			= "Difficulty";
	private final String GAME_WIDTH         = "GameWidth";
	private final String GAME_LENGTH        = "GameLength";

	private final String CONFIG_PATH 		= "readable/settings.json";
	private final String TEMPLATE_PATH      = "readable/settings.template";

	//Achievement Properties
	private final String NUMBER				= "Number";
	private final String NAME               = "Name";
	private final String COMPLETED          = "Completed";
	private final String MULTIPLIER         = "Multiplier";
	private final String TRIGGER            = "Trigger";
	private final String POINTS				= "Points";

	//General triggers
	private final String TRUE = "True";
	private final String FALSE = "False";

	@Override
	public void create () {
		batch = new SpriteBatch();
		grey_block = new Texture("grey_block.png");
		random = new Random();

		parser = new JSONParser();
		getConfig();

		game_length = ((Long) settings.get(GAME_LENGTH)).intValue();
		game_width  = ((Long) settings.get(GAME_WIDTH)).intValue();
		gameMap = new GameMap(game_length, game_width);

		players = new Array<>(new Player[] {
				new Player("Player 1", new Texture("blue_block.png"), 1),
				new Player("Player 2", new Texture("red_block.png"), 2)
		});

		activePlayer = players.first();

		players.forEach((Player player) -> player.setShapePos(random.nextInt(
				game_width - activePlayer.getShape().shape.length),
				game_length-1)
		);

		game_over = false;

		font = new BitmapFont();
		font.setColor(Color.GOLDENROD);
		uncompletedFont = new BitmapFont();
		uncompletedFont.setColor(Color.GRAY);
		completedFont = new BitmapFont();
		completedFont.setColor(Color.GOLDENROD);

		moveDownTask = new Timer.Task() {
			@Override
			public void run() {

				if (checkCollision("down")) {
					int multiplier = 1;

					gameMap.printShape(activePlayer.getShape());

					// Check row completed
					for (int row = 0; row <= activePlayer.getShape().y; row++) {
						while (checkRow(row, 0)) {
							gameMap.removeRow(row);
							activePlayer.addScore(10*multiplier);
							multiplier += multiplier;
						}
					}

					checkAchievements(multiplier);

					// Check Game Over
					if (!checkRow(game_length-5, 1) || !checkRow(game_length-5, 2)) {
						game_over = true;
						System.out.println("GAME OVER!!");

						updateConfig();
					}

					// Fetch the players next shape
					activePlayer.newShape();
					activePlayer.setShapePos(random.nextInt(game_width - activePlayer.getShape().shape.length),game_length-1);

					for (int i = 0; i < random.nextInt(3); i++) {
						activePlayer.getShape().rotate();
					}

					activePlayer = getNextPlayer();

				} else {
					activePlayer.getShape().down();
					System.out.println(gameMap.toString(activePlayer.getShape()));
					System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++");
				}
			}
		};

		Timer.schedule(moveDownTask, 2f, 1.0f- 0.1f*((Long) (settings.get(DIFFICULTY))).intValue());
	}


	@Override
	public void render () {
		Gdx.graphics.setWindowedMode(1200,800);
		Gdx.graphics.setResizable(true);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (game_over) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				dispose();
				create();
			}
		} else {

			if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
				if (!checkCollision("down")) {
					activePlayer.getShape().down();
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
				if (!checkCollision("left")) {
					activePlayer.getShape().left();
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
				if (!checkCollision("right")) {
					activePlayer.getShape().right();
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				if (!checkCollision("rotate")) {
					activePlayer.getShape().rotate();
				}
			}
//		if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
//			System.out.println("RESET CONFIG");
//			resetConfig();
//		}


			batch.begin();

			font.setColor(Color.GOLD);
			font.draw(batch, players.get(0).getName() + ": " + String.valueOf(players.get(0).getScore()), 20 * (game_width + 2), 20 * (game_length - 5));
			font.draw(batch, players.get(1).getName() + ": " + String.valueOf(players.get(1).getScore()), 20 * (game_width + 2), 20 * (game_length - 6));
			font.draw(batch, "High Score: " + settings.get(HIGH_SCORE), 20 * (game_width + 2), 20 * (game_length - 7));

			font.draw(batch, ACHIEVEMENTS + ":", 40 * game_width, 20 * (game_length - 5));

			achievements.forEach(jsonObject -> {
				if (((JSONObject) jsonObject).get(COMPLETED).equals(TRUE)) {
					completedFont.draw(batch, (String) ((JSONObject) jsonObject).get(NAME), 41 * game_width, 20 * (game_length - 4 - 2 * ((Long) ((JSONObject) jsonObject).get(NUMBER)).intValue()));
					font.draw(batch, (String) ((JSONObject) jsonObject).get(TRIGGER), 42 * game_width, 20 * (game_length - 5 - 2 * ((Long) ((JSONObject) jsonObject).get(NUMBER)).intValue()));
				} else {
					uncompletedFont.draw(batch, (String) ((JSONObject) jsonObject).get(NAME), 41 * game_width, 20 * (game_length - 4 - 2 * ((Long) ((JSONObject) jsonObject).get(NUMBER)).intValue()));
					uncompletedFont.draw(batch, (String) ((JSONObject) jsonObject).get(TRIGGER), 42 * game_width, 20 * (game_length - 5 - 2 * ((Long) ((JSONObject) jsonObject).get(NUMBER)).intValue()));
				}
			});

			if (activePlayer.getShape() == null) {
				activePlayer.newShape();
			}
			batch.draw(players.get(0).getBlock(),20 * (game_width + 2) - 22, 20 * (game_length - 6));
			batch.draw(players.get(1).getBlock(), 20 * (game_width + 2) - 22, 20 * (game_length - 7));

			for (int y = 1; y <= game_length - 4; y++) {
				for (int x = 1; x <= game_width; x++) {
					if (gameMap.seeShape(activePlayer.getShape())[y - 1][x - 1] == activePlayer.getPlayerID()) {
						batch.draw(activePlayer.getBlock(), 20 * x, 20 * y);
					} else if (gameMap.getMap()[y - 1][x - 1] == getNextPlayer().getPlayerID()) {
						batch.draw(getNextPlayer().getBlock(), 20 * x, 20 * y);
					}else {
						batch.draw(grey_block, 20 * x, 20 * y);
					}
				}
			}
			batch.end();
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}



	@Override
	public void dispose () {
		batch.dispose();
		players.forEach(Player::dispose);
		font.dispose();
		uncompletedFont.dispose();
		completedFont.dispose();
	}

	/**
	 * This will check for any collisions in the next game state.
	 * @return true if collision will occur, else false.
	 * @param action The movement of the activeShape block that collision will be tested against, either: 'rotate', 'down', 'left' or 'right'.
	 */
	private boolean checkCollision(String action) {
		Shape checker = new Shape(activePlayer.getShape().getShape(), activePlayer.getShape().x, activePlayer.getShape().y);

		switch (action) {
			case "down":
				checker.down();

				break;
			case "left":
				checker.left();

				break;
			case "right":
				checker.right();

				break;
			case "rotate":
				checker.rotate();

				break;
		}

		int[][] field = gameMap.getMap().clone();
		int[][] shape = checker.getShape().clone();

		// Check if the shape has hit the bottom of the floor
		for (int y = checker.y; y > checker.y - shape.length; y--) {
			for (int x = 0; x < shape.length; x++) {
				if (y<0) {
					if (x + checker.x < game_width && x + checker.x >= 0) {
						if (shape[checker.y - y][x] != 0) {
							return true;
						}
					}
				}
			}
		}

		// Check if the block if off the left or right side of the screen
		for (int[] row : shape) {
			for (int x = 0; x < shape.length; x++) {
				if (x + checker.x >= game_width && row[x] != 0) {
					// The block would be off the right side of the game screen
					return true;
				} else if (checker.x + x < 0 && row[x] != 0) {
					// The block would be off the left side of the game screen
					return true;
				}
			}
		}

		// Check if ths activeShape shape collides with an in game shape
		for (int y = 0; y < shape.length ; y++) {
			for (int x = 0; x < shape.length; x++) {
				if ((x + checker.x < game_width) && (x + checker.x >= 0) && (checker.y-y>=0)) {
					if (field[checker.y - y][checker.x + x] != 0 && shape[y][x] != 0) {
						// The block collides with a fixed block
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * This will check if specific row of the gameMap contains a specified value
	 * @param row The gameMap row to check.
	 * @param val The value you are looking for
	 * @return true if the value is not contained in the row, false if it is.
	 */
	private boolean checkRow(int row, int val) {
		return Arrays.stream(gameMap.getMap()[row]).noneMatch(i -> i == val);
	}


	/**
	 * Fetch and set all the data that the system stores from the previous session into the game_properties map.
	 */
	private void getConfig() {
		if (parser != null) try {

			settings = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream(CONFIG_PATH)));

			achievements = (JSONArray) settings.get(ACHIEVEMENTS);

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  Write out the contents of (JSONObject) settings to the config file. This is the last thing done at an end game.
	 */
	private void updateConfig() {
		if (settings != null) try (FileWriter file = new FileWriter(CONFIG_PATH)) {

// 				No longer write the achievements as they have become a part of the multiplayer strategy
//				settings.replace(ACHIEVEMENTS,achievements);
//

			int high = ((Long) settings.get(HIGH_SCORE)).intValue();
			if (activePlayer.getScore() > high && activePlayer.getScore() > getNextPlayer().getScore()) {
				settings.replace(HIGH_SCORE, activePlayer.getScore());
				settings.replace(HIGH_SCORE_PLAYER, activePlayer.getName());
			} else if (getNextPlayer().getScore() > high){
				settings.replace(HIGH_SCORE, getNextPlayer().getScore());
				settings.replace(HIGH_SCORE_PLAYER, getNextPlayer().getName());
			}

			System.out.println(settings.toString());
			file.write(settings.toJSONString());
			file.flush();

		} catch (Exception e) {
			System.out.println(settings.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Reset the settings.json file to match the base template. This will set the High Score to 0, the difficulty to 5,
	 * and all achievements will be lost.
	 */
	private void resetConfig() {
		try (FileWriter file = new FileWriter(CONFIG_PATH)) {

			InputStreamReader read_file = new InputStreamReader(new FileInputStream(TEMPLATE_PATH));
			System.out.println(read_file.read());

//
//			file.write();
//			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check if the last moved triggered any achievements
	 */
	private void checkAchievements(int multiplier) {
		for (Object iterator:
		     achievements) {
			JSONObject achievement = (JSONObject) iterator;
			if ((!Boolean.parseBoolean((String) achievement.get(COMPLETED))) && achievement.containsKey(MULTIPLIER)) {
				if (((Long) (achievement.get(MULTIPLIER))).intValue() == multiplier) {
					achievement.replace(COMPLETED, "True");
					activePlayer.addScore(((Long) achievement.get(POINTS)).intValue());
				}
			}
		}
	}

	/**
	 * Get the next player in the players array
	 * @return A Player object from the players array
	 */
	private Player getNextPlayer() {
		if (activePlayer.equals(players.first())) {
			return players.get(1);
		} else {
			return players.first();
		}
	}
}
