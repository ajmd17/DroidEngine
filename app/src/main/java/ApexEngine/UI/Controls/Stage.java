package ApexEngine.UI.Controls;

import ApexEngine.Math.Color4f;

public class Stage extends Panel {

    public Stage(String name) {
        super(name, 0, 0, 100, 100, new Color4f(1, 1, 1, 1));
    }

    @Override
    public void updateControl() {
        this.setWidth(getInputManager().SCREEN_WIDTH);
        this.setHeight(getInputManager().SCREEN_HEIGHT);
    }
}
