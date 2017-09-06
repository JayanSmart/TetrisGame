package smrjay001.csc2003s.tetris;

import com.badlogic.gdx.graphics.Texture;

public class Player {
	private String name;
	private int score;
	private Shape shape;
	private Shape nextShape;
	private ShapeList shapes;
	private Texture block;
	private int playerID;

	public Player(String name, Texture block, int playerID) {
		this.name = name;
		this.score = 0;
		this.shapes = new ShapeList();
		this.shape = shapes.getShape();
		this.nextShape = shapes.getShape();
		this.block = block;
		this.playerID = playerID;
		if (playerID != 1) {
			shapes.convertShapes(playerID);
		}
	}

	public String getName() {
		return name;
	}

	/**
	 * Get the player score
	 * @return player score as an int
	 */
	public int getScore() {
		return score;
	}

	public Shape getShape() {
		return shape;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void newShape() {
		shape =  new Shape(nextShape);
		nextShape = shapes.getShape();
	}

	public void setShapePos(int x, int y) {
		this.shape.x = x;
		this.shape.y = y;
	}

	public void addScore(int added) {
		this.score += added;
	}

	public Texture getBlock() {
		return block;
	}

	public void dispose() {
		this.block.dispose();
	}
}
