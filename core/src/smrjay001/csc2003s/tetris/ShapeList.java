package smrjay001.csc2003s.tetris;

import java.util.Random;

class ShapeList {

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

	ShapeList() {
		this.random = new Random();
	}

	/**
	 * Fetch a random Shape object from the ShapeList.
	 * @return a Shape Object picked from the shapeList.
	 */
	Shape getShape() {
		return new Shape(shapes[random.nextInt(shapes.length)].getShape());
	}

	/**
	 * This method will convert all the shapes to use the playerID instead of 1 for an "active" space.
	 */
	void convertShapes(int playerID) {
		for (Shape shape :
				shapes) {
			for (int[] row :
					shape.shape) {
				for (int i = 0; i < row.length; i++) {
					if (row[i] != 0) {
						row[i] = playerID;
					}
				}
			}
		}
	}
}
