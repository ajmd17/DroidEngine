package ApexEngine.UI.Controls;

import ApexEngine.Rendering.*;
import ApexEngine.Math.*;
import ApexEngine.Scene.*;
import ApexEngine.UI.*;;

public class Panel extends AbstractControl implements Clickable {
	private static Color4f grey = new Color4f(0.65f, 0.65f, 0.65f, 1.0f);

	public Panel(String name, int x, int y, int width, int height, Color4f color) {
		super(name, x, y);
		this.width = width;
		this.height = height;
		this.setColor(color);
	}

	public Panel(String name, int x, int y, int width, int height) {
		this(name, x, y, width, height, grey);
	}

	@Override
	public void render() {
		getSpriteRenderer().draw(null, getWorldTranslation().x, getWorldTranslation().y, width, height,
				this.getColor());
	}

	@Override
	public void updateControl() {
	}

	@Override
	public GameObject clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void leftClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rightClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	public void letGo() {
		// TODO Auto-generated method stub

	}

}
