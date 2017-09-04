package smrjay001.csc2003s.tetris;

import com.badlogic.gdx.graphics.Texture;

class Player {
	private String name;
	private int score;
	private Shape shape;
	private Shape nextShape;
	private ShapeList shapes;
	private Texture block;

	Player(String name, Texture block) {
		this.name = name;
		this.score = 0;
		this.shapes = new ShapeList();
		this.shape = shapes.getShape();
		this.nextShape = shapes.getShape();
		this.block = block;

	}

	String getName() {
		return name;
	}



	int getScore() {
		return score;
	}

	Shape getShape() {
		return shape;
	}

	void newShape() {
		shape =  new Shape(nextShape);
		nextShape = shapes.getShape();
	}

	void setShapePos(int x, int y) {
		this.shape.x = x;
		this.shape.y = y;
	}

	void addScore(int added) {
		this.score += added;
	}

	Texture getBlock() {
		return block;
	}

	void dispose() {
		this.block.dispose();
	}
}
