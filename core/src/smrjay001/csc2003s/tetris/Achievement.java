package smrjay001.csc2003s.tetris;

import com.badlogic.gdx.graphics.Texture;

/**
 * Base class for the achievement option. Instances of this object are created from information in the config file.
 */
public class Achievement {
	private boolean completed;
	private String title;
	private String description;
	private String trigger;
	private Texture icon;

	public Achievement(boolean completed, String title, String description, String trigger, Texture icon) {
		this.completed = completed;
		this.title = title;
		this.description = description;
		this.trigger = trigger;
		this.icon = icon;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
}
