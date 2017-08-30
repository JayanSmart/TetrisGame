package smrjay001.csc2003s.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
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
	private Texture red_block;
	private Texture grey_block;
	private Shape[] shapes;
	private Shape active;
	private Random random;
	private int game_width, game_length;
	private boolean game_over;
	private long score;

	private JSONParser parser;
	private JSONObject settings;

	private GameMap gameMap;
	private BitmapFont font;


	private final String ACHIEVEMENTS	 	= "Achievements";
	private final String HICH_SCORE 		= "HighScore";
	private final String CONFIG_PATH 		= "readable/settings.json";
	private final String DIFFICULTY			= "Difficulty";




	@Override
	public void create () {
		batch = new SpriteBatch();
		red_block = new Texture("red_block.png");
		grey_block = new Texture("grey_block.png");
		random = new Random();

		game_length = 24;
		game_width = 10;
		gameMap = new GameMap(game_length, game_width);


		parser = new JSONParser();


		score = 0;
		game_over = false;

		getConfig();

		font = new BitmapFont();

		this.shapes = new Shape[] {
			new Shape(new int[][] {
					{0,0,1,0},
					{0,0,1,0},
					{0,0,1,0},
					{0,0,1,0},
			}),
			new Shape(new int[][] {
					{1,1,0},
					{0,1,0},
					{0,1,0},
			}),
			new Shape(new int[][] {
					{0,1,1},
					{0,1,0},
					{0,1,0},
			}),
			new Shape(new int[][] {
					{0,1,0},
					{0,1,1},
					{0,1,0},
			}),
			new Shape(new int[][] {
					{1,1},
					{1,1}
			}),
			new Shape(new int[][] {
					{1,1,0},
					{0,1,1},
					{0,0,0}
			}),
			new Shape(new int[][] {
					{0,1,1},
					{1,1,0},
					{0,0,0}
			}),
		};

		final Timer.Task moveDownTask = new Timer.Task() {
			@Override
			public void run() {

				if (checkCollision("down")) {
					int multiplier = 1;

					gameMap.printShape(active);

					// Check row completed
					for (int row = 0; row < active.y; row++) {
						while (checkRow(row, 0)) {
							gameMap.removeRow(row);
							score += 10*multiplier;
							multiplier += multiplier;
						}

					}

					// Check Game Over
					if (!checkRow(game_length-4, 1)) {
						game_over = true;
						System.out.println("GAME OVER!!");

						// Update the high score
						if (score >	(long)settings.get("HighScore")) settings.put("HighScore", score);
						updateConfig();
						Gdx.app.exit();
					}

					active = new Shape(shapes[random.nextInt(shapes.length)].getShape());
					active.x = random.nextInt(game_width - active.shape.length);
					active.y = game_length-1;
					for (int i = 0; i < random.nextInt(3); i++) {
						active.rotate();
					}
				} else {
					active.down();
				}
			}
		};


		switch (((Long) (settings.get(DIFFICULTY))).intValue()) {
			case 1:
				Timer.schedule(moveDownTask, 2f, 1.0f);
				break;
			case 2:
				Timer.schedule(moveDownTask, 2f, 0.9f);
				break;
			case 3:
				Timer.schedule(moveDownTask, 2f, 0.8f);
				break;
			case 4:
				Timer.schedule(moveDownTask, 2f, 0.7f);
				break;
			case 5:
				Timer.schedule(moveDownTask, 2f, 0.6f);
				break;
			case 6:
				Timer.schedule(moveDownTask, 2f, 0.5f);
				break;
			case 7:
				Timer.schedule(moveDownTask, 2f, 0.4f);
				break;
			case 8:
				Timer.schedule(moveDownTask, 2f, 0.3f);
				break;
			case 9:
				Timer.schedule(moveDownTask, 2f, 0.2f);
				break;
			case 10:
				Timer.schedule(moveDownTask, 2f, 0.1f);
				break;
		}
	}


	@Override
	public void render () {
		Gdx.graphics.setWindowedMode(1200,800);
		Gdx.graphics.setResizable(true);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			if (!checkCollision("down")) {
				active.down();
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			if (!checkCollision("left")) {
				active.left();
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			if (!checkCollision("right")) {
				active.right();
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			if (!checkCollision("rotate")) {
				active.rotate();
			}
		}


		batch.begin();

		font.setColor(Color.GOLD);
		font.draw(batch, "Score: "+ String.valueOf(score), 20*(game_width+2), 20*(game_length-5));
		font.draw(batch, "High Score: "+ settings.get("HighScore"), 20*(game_width+2), 20*(game_length-6));


		if (active == null) {
			active = new Shape(shapes[random.nextInt(shapes.length - 1)].getShape(), 0, 19);
		}

		for (int y = 1; y <= game_length - 4; y++) {
			for (int x = 1; x <= game_width; x++) {
				if (gameMap.seeShape(active)[y-1][x-1] == 1) {
					batch.draw(red_block, 20 * x, 20 * y);
				} else {
					batch.draw(grey_block, 20 * x, 20 * y);
				}
			}
		}
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}



	@Override
	public void dispose () {
		batch.dispose();
		red_block.dispose();
		font.dispose();
	}

	/**
	 * This will check for any collisions in the next game state.
	 * @return true if collision will occur, else false.
	 * @param action The movement of the active block that collision will be tested against, either: 'rotate', 'down', 'left' or 'right'.
	 */
	private boolean checkCollision(String action) {
		Shape checker = new Shape(active.getShape(), active.x, active.y);

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
						if (shape[checker.y - y][x] == 1) {
							return true;
						}
					}
				}
			}
		}

		// Check if the block if off the left or right side of the screen
		for (int[] row : shape) {
			for (int x = 0; x < shape.length; x++) {
				if (x + checker.x >= game_width && row[x] == 1) {
					// The block would be off the right side of the game screen
					return true;
				} else if (checker.x + x < 0 && row[x] == 1) {
					// The block would be off the left side of the game screen
					return true;
				}
			}
		}

		// Check if ths active shape collides with an in game shape
		for (int y = 0; y < shape.length ; y++) {
			for (int x = 0; x < shape.length; x++) {
				if ((x + checker.x < game_width) && (x + checker.x >= 0) && (checker.y-y>=0)) {
					if (field[checker.y - y][checker.x + x] == 1 && shape[y][x] == 1) {
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

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  Write out the contents of (JSONObject) settings to the config file. This is the last thing done at an end game.
	 */
	private void updateConfig() {
		if (settings != null) {
			try (FileWriter file = new FileWriter(CONFIG_PATH)) {

				file.write(settings.toJSONString());
				file.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
