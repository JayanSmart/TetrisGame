package smrjay001.csc2003s.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;

import java.util.Arrays;
import java.util.List;
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
	private int score;

	private Map map;
	BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();
		red_block = new Texture("red_block.png");
		grey_block = new Texture("grey_block.png");
		random = new Random();

		game_length = 24;
		game_width = 10;
		map = new Map(game_length, game_width);

		game_over = false;
		score = 0;
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
					// Check Game Over
					if (!checkRow(game_length-3, 1)) {
						game_over = true;
						System.out.println("GAME OVER!!");
						Gdx.app.exit();
					}

					map.printShape(active);

					// Check row completed
					for (int row = 0; row < active.y; row++) {
						System.out.println("Checking rows");
						while (checkRow(row, 0)) {
							System.out.println("removeRow: "+row);
							map.removeRow(row);
							score += 10;
						}

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

		Timer.schedule(moveDownTask, 2f, 0.5f);
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
		font.draw(batch, "Score: "+score, 20*(game_width+2), 20*(game_length-5));



		if (active == null) {
			active = new Shape(shapes[random.nextInt(shapes.length - 1)].getShape(), 0, 19);
		}

		for (int y = 1; y <= game_length - 4; y++) {
			for (int x = 1; x <= game_width; x++) {
				if (map.seeShape(active)[y-1][x-1] == 1) {
					batch.draw(red_block, 20 * x, 20 * y);
				} else {
					batch.draw(grey_block, 20 * x, 20 * y);
				}
			}
		}

		batch.end();
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

		if (action.equals("down")) {
			checker.down();

		} else if (action.equals("left")) {
			checker.left();

		} else if (action.equals("right")) {
			checker.right();

		} else if (action.equals("rotate")) {
			checker.rotate();

		}

		int[][] field = map.getMap().clone();
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
	 * This will check if specific row of the map contains a specified value
	 * @param row The map row to check.
	 * @param val The value you are looking for
	 * @return true if the value is not contained in the row, false if it is.
	 */
	private boolean checkRow(int row, int val) {
		return Arrays.stream(map.getMap()[row]).noneMatch(i -> i == val);
	}

}
