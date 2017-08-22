package smrjay001.csc2003s.tetris;

import com.badlogic.gdx.ApplicationAdapter;

import java.util.Random;

/**
 * This is the actual game object that will contain the logic of the game.
 */
public class GameScreen extends ApplicationAdapter {
	Map map;
	Shape[] shapes;
	Shape active;
	Random random;


	@Override
	public void create() {
		map = new Map(new int[20][10]);

		random = new Random();

		System.out.println(map.toString());

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


	}

	@Override
	/**
	 * This is the main render function for the game logic
	 */
	public void render() {
		int count = 0;
		Boolean gameOver = false;
		while (gameOver == false) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e){
				continue;
			}
			if (active == null) {
				System.out.println(shapes.length);
				active = new Shape(shapes[random.nextInt(shapes.length - 1)].getShape(), 0, 0);
				System.out.println(active.toString());
				System.out.println(map.toString(active));
			}

			if (checkCollision()) {
				if (active.y < 3) {
					gameOver = true;
				}
				map.printShape(active);
				active = new Shape(shapes[random.nextInt(shapes.length)].getShape());
				active.x = 0;
				active.y = 0;
			} else {
				active.down();
				System.out.println(map.toString(active));
			}
		count++;
		}
	}

	/**
	 * Check if the next game state will have a collision.
	 * @return True if collision occurs in next game state, else False.
	 */
	private boolean checkCollision() {
		System.out.println("Check collision");
		Shape checker = new Shape(active.getShape(), active.x, active.y);
		checker.down();
		int[][] field = map.getMap().clone();
		int[][] shape = checker.getShape().clone();
		for (int i = checker.y; i < checker.y + shape.length; i++) {
			for (int j = checker.x; j < checker.x + shape.length; j++) {
				try {
					if (field[i][j] == 1 && shape[i-checker.y][j-checker.x] == 1) {
						return true;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					if (shape[i-checker.y][j-checker.x] == 1) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.render();
	}



}
