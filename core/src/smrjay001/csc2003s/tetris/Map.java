package smrjay001.csc2003s.tetris;

/**
 * This is the object that controls the game map on which the player plays
 * This will not store the active map, only the static one.
 */
public class Map {
	private int[][] map;


	Map(int[][] map) {
		this.map = map;
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
		for (int i = active.y; i < active.y + shape.length; i++) {
			for (int j = active.x; j < active.x + shape.length; j++) {
				if (map[i][j] == 0) {
					this.map[i][j] = shape[i- active.y][j- active.x];
				}
			}
		}
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
					(shape.shape[y-shape.y][x-shape.x] == 1)
					) {
						output.append(shape.shape[y - shape.y][x - shape.x]).append(", ");
				} else {
					output.append(map[y][x]).append(", ");
				}
			}
			output = new StringBuilder(output.substring(0, output.length() - 2));
			output.append("\n");
		}
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return output.toString().trim();
	}
}
