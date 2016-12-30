package ApexEngine.Input;

public abstract class MouseEvent {
    public ApexEngine.Input.InputManager.MouseButton btn = ApexEngine.Input.InputManager.MouseButton.None;
    public boolean mouseUpEvt = false;

    public MouseEvent(ApexEngine.Input.InputManager.MouseButton btn, boolean isMouseUpEvt) {
        this.btn = btn;
        this.mouseUpEvt = isMouseUpEvt;
    }

    public abstract void evt();

}


