package smrjay001.csc2003s.tetris;

/**
 * This is the object that controls the game map on which the player plays
 * This will not store the active map, only the static one.
 */
class GameMap {
	private final int[][] map;
	private final int length;
	private final int width;

	GameMap(int length, int width) {
		this.length = length;
		this.width = width;
		this.map = new int[length][width];
	}

	int[][] getMap() {
		return map;
	}

	/**
	 * This method will print the active shape to the game map, making it a static background object.
	 * @param active The shape object that will be printed to the game map
	 */
	void printShape(Shape active) {
		int[][] shape = active.getShape().clone();

		for (int y = 0; y<shape.length; y++) {
			for (int x = 0; x < shape.length; x++) {
				if (x + active.x < width && x + active.x >= 0 && (active.y-y>=0)) {
					if (map[active.y - y][active.x + x] == 0) {
						map[active.y - y][active.x + x] = shape[y][x];
					}
				}
			}
		}
	}

	/**
	 * This returns the active game shape as if it were printed on the board.
	 * @param active the shape object currently under user control
	 * @return int[][] of the game board with the active piece on the board.
	 */
	int[][] seeShape(Shape active) {
		int[][] board = new int[length][width];
		for (int i = 0; i < length; i++) {
			System.arraycopy(map[i], 0, board[i], 0, width);
		}

		for (int y = 0; y<active.shape.length; y++) {
			for (int x = 0; x < active.shape.length; x++) {
				if (x + active.x < width && x + active.x >= 0 && (active.y-y>=0)) {
					if (board[active.y - y][active.x + x] == 0) {
						board[active.y - y][active.x + x] = active.shape[y][x];
					}
				}
			}
		}
		return board;
	}

	/**
	 * Remove one row from the game board and add a new empty one to the top of the board
	 * @param row The index of the row to be removed
	 */
	public void removeRow(int row) {
		if (row >= 0 && row < length) {
			for (int i = row; i < length-1; i++) {
				map[i] = map[i+1];
			}
		}
		map[length-1] = new int[width];
	}


	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		for (int [] line:
				map) {
			for (int i :
					line) {
				output.append(i).append(", ");
			}
			output = new StringBuilder(output.substring(0, output.length() - 2));
			output.append("\n");
		}
		return output.toString().trim();
	}

	String toString(Shape shape) {
		StringBuilder output = new StringBuilder();
		for (int y = 0; y < map.length; y++) {
			for (int x=0; x < map[y].length; x++) {
				if (
						(y >= shape.y) && (y <shape.y+shape.shape.length) &&
								(x >= shape.x) && (x < shape.x+shape.shape.length) &&
								(shape.shape[y-shape.y][x-shape.x] != 0)
						) {
					output.append(shape.shape[y - shape.y][x - shape.x]).append(", ");
				} else {
					output.append(map[y][x]).append(", ");
				}
			}
			output = new StringBuilder(output.substring(0, output.length() - 2));
			output.append("\n");
		}
		return output.toString().trim();
	}
}
