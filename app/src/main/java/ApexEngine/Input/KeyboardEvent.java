package ApexEngine.Input;

public abstract class KeyboardEvent   
{
    public ApexEngine.Input.InputManager.KeyboardKey key = ApexEngine.Input.InputManager.KeyboardKey.None;
    public KeyboardEvent(ApexEngine.Input.InputManager.KeyboardKey key) {
        this.key = key;
    }
    public abstract void evt();

}


