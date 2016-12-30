package ApexEngine.UI.Controls;

import ApexEngine.Assets.AssetManager;
import ApexEngine.Input.InputManager.MouseButton;
import ApexEngine.Math.*;
import ApexEngine.Rendering.*;
import ApexEngine.UI.AbstractControl;
import ApexEngine.UI.Clickable;

public abstract class Button extends AbstractControl implements Clickable {
	Texture2D tex;
	Texture2D mainTex, mouseOverTex, mouseClickTex;
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private float tick = 0f, maxTick = .05f;

	public Button(String text, int x, int y) {
		super("Button_" + text);

		this.text = text;

		mainTex = (Texture2D) AssetManager.load("textures/ui/button.png");
		mouseOverTex = (Texture2D) AssetManager.load("textures/ui/button_mouseover.png");
		mouseClickTex = (Texture2D) AssetManager.load("textures/ui/button_clicked.png");

		tex = mainTex;
		width = 126;
		height = 37;
		setLocalTranslation(new Vector3f(x, y, 0));

	}

	@Override
	public void updateControl() {
		if (tex == mouseClickTex) {

			if (!this.getInputManager().isMouseButtonDown(MouseButton.Left)) {
				if (tick > maxTick) {
					tex = mainTex;
					tick = 0f;
				} else {
					tick += 0.1f;// GameTime.getDeltaTime();
				}
			}
		}
	}

	@Override
	public void render() {
		getSpriteRenderer().draw(tex, getWorldTranslation().x, getWorldTranslation().y, width, height, this.getColor());
		// SpriteRenderer.drawText(GUIManager.getDefaultFont(), text,
		// getWorldTranslation().x + (width/2) -
		// (GUIManager.getDefaultFont().getWidth(text)/2),
		// getWorldTranslation().y + (height/2) -
		// (GUIManager.getDefaultFont().getHeight()/2), Color4f.WHITE);
	}

	@Override
	public void leftClicked() {
		tex = mouseClickTex;
	}

	@Override
	public void rightClicked() {
	}

	@Override
	public void letGo() {
		onMouseLeftClick();
		tex = mouseOverTex;
	}

	public abstract void onMouseLeftClick();

	@Override
	public void mouseOver() {
		tex = mouseOverTex;
	}

	@Override
	public void mouseLeave() {
		tex = mainTex;
	}

}
