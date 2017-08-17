package smrjay001.csc2003s.tetris;

/**
 * This object manages a Shape with which the player plays.
 */
public class Shape {
	int[][] shape;
	int x = 0;
	int y = 0;

	public Shape(int[][] shape, int x, int y) {
		this.shape = shape;
		this.x = x;
		this.y = y;
	}

	Shape(int[][] shape) {
		this.shape = shape;
	}

	public int[][] getShape() {
		return shape;
	}

	void down() {
		y += 1;
	}

	void left() {
		x -= 1;
	}

	void right() {
		x += 1;
	}

	void rotate() {
		int[][] rotated = new int[shape.length][shape.length];   // Can do this as matrix is always square

		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape.length; j++) {
				rotated[i][j] = shape[shape.length - j - 1][i];
			}
		}
		shape = rotated;
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();

		for (int[] row : shape) {
			for (int value : row) {
				out.append(value).append(" ,");
			}
			out.append("\n");
		}
		return out.toString();
	}
}
