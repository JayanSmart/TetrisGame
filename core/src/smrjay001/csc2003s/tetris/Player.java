package smrjay001.csc2003s.tetris;

public class Player {
	private String name;
	private int score;
	private Shape shape;
	private Shape nextShape;
	private ShapeList shapes;

	public Player(String name) {
		this.name = name;
		this.score = 0;
		this.shapes = new ShapeList();
		this.shape = shapes.getShape();
		this.nextShape = shapes.getShape();
	}

	public String getName() {
		return name;
	}



	public int getScore() {
		return score;
	}

	public Shape getShape() {
		return shape;
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
}
