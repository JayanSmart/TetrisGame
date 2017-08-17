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
	public void render() {
		int count = 0;
		while (count < 25) {
			if (active == null) {
				System.out.println(shapes.length);
				active = new Shape(shapes[random.nextInt(shapes.length - 1)].getShape(), 0, 0);
				System.out.println(active.toString());
				System.out.println(map.toString(active));
			}

			if (checkCollision()) {
				map.printShape(active);
				active = new Shape(shapes[random.nextInt(shapes.length)].getShape());
				active.x = 0;
				active.y = 0;
			} else {
				active.down();
				System.out.println(map.toString(active));
				System.out.println("active shape is at: ("+active.x+","+active.y+")");
			}
		count++;
		}
	}

	private boolean checkCollision() {
		System.out.println("Check collision");
		Shape checker = new Shape(active.getShape(), active.x, active.y);
		checker.down();
		int[][] field = map.getMap().clone();
		int[][] shape = checker.getShape().clone();
		for (int i = checker.y; i < checker.y + shape.length; i++) {
			for (int j = checker.x; j < checker.x + shape.length; j++) {
				try {
					if (field[i][j] == 1 && checker.getShape()[i][j] == 1) {
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
