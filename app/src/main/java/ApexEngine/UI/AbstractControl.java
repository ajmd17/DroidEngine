package ApexEngine.UI;

import ApexEngine.Input.InputManager;
import ApexEngine.Math.*;
import ApexEngine.Rendering.*;
import ApexEngine.Scene.*;

public abstract class AbstractControl extends Node {
	public enum Layout {
		FIXED, CENTERED
	}

	public boolean clicked = false;
	protected Layout layout = Layout.FIXED;
	protected int width, height;
	protected boolean isMouseOver = false;
	protected boolean hasFocus = false;
	protected String name = "control";
	protected Color4f color = new Color4f(1, 1, 1, 1);
	private boolean isVibrating = false;
	private float vibrateTick = 0f, maxVibrateTick = 1f;

	public abstract void render();

	public abstract void updateControl();

	private float oldX;

	private InputManager inputManager;
	private SpriteRenderer spriteRenderer;

	public SpriteRenderer getSpriteRenderer() {
		return spriteRenderer;
	}

	public void setSpriteRenderer(SpriteRenderer spriteRenderer) {
		this.spriteRenderer = spriteRenderer;
	}

	public InputManager getInputManager() {
		return inputManager;
	}

	public void setInputManager(InputManager inputManager) {
		this.inputManager = inputManager;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	@Override
	public void update(RenderManager renderManager) {
		super.update(renderManager);
		if (isVibrating) {
			if (vibrateTick < maxVibrateTick) {
				vibrateTick += 0.1f;// GameTime.getDeltaTime()*2f;
				setLocalTranslation(
						new Vector3f(oldX + (float) Math.sin(vibrateTick * 15f) * 5f, getLocalTranslation().y, 0));
			} else {
				setLocalTranslation(new Vector3f(oldX, getLocalTranslation().y, 0));
				isVibrating = false;
				onDoneVibrating();
			}
		} else if (!isVibrating) {

			if (this.layout == Layout.CENTERED) {
				if (this.getParent() != null && (getParent() instanceof AbstractControl)) {
					float parentCenterX = ((AbstractControl) getParent()).getWidth() / 2f;
					float parentCenterY = ((AbstractControl) getParent()).getHeight() / 2f;
					float halfWidth = this.getWidth() / 2f;
					float halfHeight = this.getHeight() / 2f;
					if (getLocalTranslation().x != (parentCenterX - halfWidth)
							|| getLocalTranslation().y != (parentCenterY - halfHeight)) {
						this.setLocalTranslation(
								new Vector3f(parentCenterX - halfWidth, parentCenterY - halfHeight, 0));
					}
				} else if (this.getParent() == null || !(getParent() instanceof AbstractControl)) {
					float halfWidth = this.getWidth() / 2f;
					float halfHeight = this.getHeight() / 2f;
					float halfScreenWidth = inputManager.SCREEN_WIDTH / 2f;
					float halfScreenHeight = inputManager.SCREEN_HEIGHT / 2f;
					if (getLocalTranslation().x != (halfScreenWidth - halfWidth)
							|| getLocalTranslation().y != (halfScreenHeight - halfHeight)) {
						this.setLocalTranslation(
								new Vector3f(halfScreenWidth - halfWidth, halfScreenHeight - halfHeight, 0));
					}
				}

			}
		}
		updateControl();
	}

	public void vibrate() {
		onStartVibrating();
		vibrateTick = 0f;
		isVibrating = true;
		oldX = getLocalTranslation().x;
	}

	public void vibrate(float time) {
		maxVibrateTick = time;
		vibrate();
	}

	public void onStartVibrating() {

	}

	public void onDoneVibrating() {

	}

	@Override
	public void updateParents() {
		super.updateParents();
		/*
		 * if (attachedToRoot) { if (!GUIManager.getControls().contains(this)) {
		 * GUIManager.getControls().add(this); } } else { if
		 * (GUIManager.getControls().contains(this)) {
		 * GUIManager.getControls().remove(this); } }
		 */
	}

	public Color4f getColor() {
		return color;
	}

	public void setColor(Color4f color) {
		this.color = color;
	}

	public void mouseOver() {
		isMouseOver = true;
	}

	public void mouseLeave() {
		isMouseOver = false;
	}

	public final boolean isMouseOver() {
		return isMouseOver;
	}

	public AbstractControl(String name) {
		setName(name);
		// this.setBucket(Bucket.Particle);;
	}

	public AbstractControl(String name, int x, int y) {
		this(name);
		this.setLocalTranslation(new Vector3f(x, y, 0));
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
