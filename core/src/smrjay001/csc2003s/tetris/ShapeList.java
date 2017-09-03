package smrjay001.csc2003s.tetris;

import java.util.Random;

public class ShapeList {

	private Random random;

	private Shape[] shapes = new Shape[] {
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

	public ShapeList() {
		this.random = new Random();
	}

	/**
	 * Fetch a random Shape object from the ShapeList.
	 * @return a Shape Object picked from the shapeList.
	 */
	public Shape getShape() {
		return new Shape(shapes[random.nextInt(shapes.length)].getShape());
	}
}
