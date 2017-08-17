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

	void printShape(Shape active){
		System.out.println("PrintShape");
		int[][] shape = active.getShape().clone();
		for (int i = active.y; i < shape.length; i++) {
			for (int j = active.x; j < shape.length; j++) {
				System.out.println("Shape here: "+shape[i-active.y][j-active.x]);
				map[i][j] = shape[i- active.y][j- active.x];
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
				if ((y >= shape.y) && (y <shape.y+shape.shape.length) && (x >= shape.x) && (x < shape.x+shape.shape.length)) {
						output.append(shape.shape[y-shape.y][x-shape.x]).append(", ");
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
