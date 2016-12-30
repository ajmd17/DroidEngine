package ApexEngine.UI;

import java.util.ArrayList;

import ApexEngine.Game;
import ApexEngine.Input.InputManager;
import ApexEngine.Input.InputManager.MouseButton;
import ApexEngine.Input.MouseEvent;
import ApexEngine.Rendering.RenderManager;
import ApexEngine.Rendering.SpriteRenderer;
import ApexEngine.Scene.Node;
import ApexEngine.UI.Controls.Stage;

public class GUIManager {
	private InputManager inputManager;
	private SpriteRenderer spriteRenderer;
	private RenderManager renderManager;
	private ArrayList<AbstractControl> controls = new ArrayList<AbstractControl>();
	private ArrayList<Stage> stages = new ArrayList<Stage>();
	private Stage currentStage = null;
	private Node guiNode = new Node("gui");

	public GUIManager(Game game) {
		this.inputManager = game.getInputManager();
		this.renderManager = game.getRenderManager();
		this.spriteRenderer = game.getRenderManager().getSpriteRenderer();
		game.getRootNode().addChild(guiNode);

		inputManager.addMouseEvent(new MouseEvent(MouseButton.Left, false) {

			@Override
			public void evt() {
				for (int i = 0; i < controls.size(); i++) {
					if (controls.get(i) instanceof Clickable) {
						if (collides(controls.get(i), inputManager.getMouseX() + inputManager.SCREEN_WIDTH / 2,
								inputManager.getMouseY() + inputManager.SCREEN_HEIGHT / 2)) {
							controls.get(i).hasFocus = true;
							((Clickable) controls.get(i)).leftClicked();
							controls.get(i).clicked = true;
						} else {

							controls.get(i).hasFocus = false;
						}
					}
				}
			}
		});

		inputManager.addMouseEvent(new MouseEvent(MouseButton.Left, true) {

			@Override
			public void evt() {
				for (int i = controls.size() - 1; i > -1; i--) {
					if (i < controls.size()) {
						if (controls.get(i).clicked == true) {
							((Clickable) controls.get(i)).letGo();
							controls.get(i).clicked = false;
						}
					}
				}
			}
		});

		inputManager.addMouseEvent(new MouseEvent(MouseButton.Right, false) {

			@Override
			public void evt() {
			}
		});
	}

	public Node getGUINode() {
		return guiNode;
	}

	public void addStage(Stage stage) {
		stages.add(stage);
	}

	public void remove(Stage stage) {
		stages.remove(stage);
	}

	public Stage getStage(String name) {
		for (Stage stage : stages) {
			if (stage.getName().equals(name)) {
				return stage;
			}
		}
		return null;
	}

	public Stage getStage(int index) {
		return stages.get(index);
	}

	public void showStage(Stage stage) {
		currentStage = stage;
	}

	public void showStage(int index) {
		showStage(getStage(index));
	}

	public void showStage(String name) {
		showStage(getStage(name));
	}

	public Stage getCurrentStage() {
		return currentStage;
	}

	public void hideStage() {
		currentStage = null;
	}

	public static boolean collides(AbstractControl control, int x, int y) {
		if (x > control.getWorldTranslation().x && x < control.getWorldTranslation().x + (control.width)) {
			if (y > control.getWorldTranslation().y && y < control.getWorldTranslation().y + (control.height)) {
				return true;
			}
		}
		return false;
	}

	public void update() {
		guiNode.update(renderManager);

		if (currentStage != null) {
			if (currentStage.getWidth() != inputManager.SCREEN_WIDTH
					|| currentStage.getHeight() != inputManager.SCREEN_HEIGHT) {
				currentStage.setWidth(inputManager.SCREEN_WIDTH);
				currentStage.setHeight(inputManager.SCREEN_HEIGHT);
			}
		}

		mouseOver();
	}

	public void render() {
		if (currentStage != null) {
			currentStage.setInputManager(inputManager);
			currentStage.setSpriteRenderer(spriteRenderer);
			currentStage.render();
		}

		for (int i = 0; i < guiNode.getChildren().size(); i++) {
			if (guiNode.getChild(i) instanceof AbstractControl) {
				renderControl((AbstractControl) guiNode.getChild(i));
			}
		}
	}

	private void renderControl(AbstractControl ctrl) {
		ctrl.render();
		for (int i = 0; i < ctrl.getChildren().size(); i++) {
			if (ctrl.getChild(i) instanceof AbstractControl)
				renderControl((AbstractControl) ctrl.getChild(i));
		}
	}

	private void mouseOver() {
		for (int i = 0; i < controls.size(); i++) {
			if (controls.get(i) instanceof Clickable) {
				if (collides(controls.get(i), inputManager.getMouseX() + inputManager.SCREEN_WIDTH / 2,
						inputManager.getMouseY() + inputManager.SCREEN_HEIGHT / 2)) {
					if (!controls.get(i).isMouseOver()) {
						controls.get(i).mouseOver();
						controls.get(i).isMouseOver = true;
					}
				} else {
					if (controls.get(i).isMouseOver()) {
						controls.get(i).mouseLeave();
						controls.get(i).isMouseOver = false;
						if (controls.get(i).clicked == true) {
							controls.get(i).clicked = false;
						}
					}
				}
			}
		}
	}

	public void addControl(AbstractControl ctrl) {
		controls.add(ctrl);
		guiNode.addChild(ctrl);
		ctrl.setInputManager(inputManager);
		ctrl.setSpriteRenderer(spriteRenderer);
	}

	public void removeControl(AbstractControl ctrl) {
		controls.remove(ctrl);
		guiNode.removeChild(ctrl);
	}
}
