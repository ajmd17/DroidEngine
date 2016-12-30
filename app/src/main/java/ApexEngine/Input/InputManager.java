package ApexEngine.Input;

import java.util.ArrayList;

public class InputManager {
    public int SCREEN_WIDTH = 1, SCREEN_HEIGHT = 1, WINDOW_X, WINDOW_Y, MOUSE_X, MOUSE_Y;
    private ArrayList<KeyboardEvent> keyEvts = new ArrayList<KeyboardEvent>();
    private ArrayList<MouseEvent> mouseEvts = new ArrayList<MouseEvent>();
    public ArrayList<KeyboardKey> keysdown = new ArrayList<KeyboardKey>();
    public ArrayList<MouseButton> mousebtnsdown = new ArrayList<MouseButton>();

    public enum MouseButton {
        //TODO: add more
        None,
        Left,
        Right,
        Middle
    }

    public enum KeyboardKey {
        //TODO: add more
        None,
        LeftCtrl,
        RightCtrl,
        LeftAlt,
        RightAlt,
        LeftShift,
        RightShift,
        Tab,
        Enter,
        CapsLock,
        Backspace,
        Space,
        LeftArrow,
        RightArrow,
        UpArrow,
        DownArrow,
        Num0,
        Num1,
        Num2,
        Num3,
        Num4,
        Num5,
        Num6,
        Num7,
        Num8,
        Num9,
        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H,
        I,
        J,
        K,
        L,
        M,
        N,
        O,
        P,
        Q,
        R,
        S,
        T,
        U,
        V,
        W,
        X,
        Y,
        Z
    }

    public void addKeyboardEvent(KeyboardEvent e) {
        keyEvts.add(e);
    }

    public void addMouseEvent(MouseEvent e) {
        mouseEvts.add(e);
    }

    public int getMouseX() {
        return MOUSE_X;
    }

    public int getMouseY() {
        return MOUSE_Y;
    }

    public void mouseButtonDown(MouseButton btn) {
        if (!mousebtnsdown.contains(btn)) {
            for (int i = 0; i < mouseEvts.size(); i++) {
                if (!mouseEvts.get(i).mouseUpEvt && mouseEvts.get(i).btn == btn) {
                    mouseEvts.get(i).evt();
                }

            }
            mousebtnsdown.add(btn);
        }

    }

    public boolean isMouseButtonDown(MouseButton btn) {
        return mousebtnsdown.contains(btn);
    }

    public void mouseButtonUp(MouseButton btn) {
        if (mousebtnsdown.contains(btn)) {
            for (int i = 0; i < mouseEvts.size(); i++) {
                if (mouseEvts.get(i).mouseUpEvt && mouseEvts.get(i).btn == btn) {
                    mouseEvts.get(i).evt();
                }

            }
            mousebtnsdown.remove(btn);
        }

    }

    public boolean isMouseButtonUp(MouseButton btn) {
        return !isMouseButtonDown(btn);
    }

    public void keyDown(KeyboardKey key) {
        if (!keysdown.contains(key)) {
            keysdown.add(key);
        }

    }

    public boolean isKeyDown(KeyboardKey key) {
        return keysdown.contains(key);
    }

    public void keyUp(KeyboardKey key) {
        if (keysdown.contains(key)) {
            for (int i = 0; i < keyEvts.size(); i++) {
                if (keyEvts.get(i).key == key) {
                    keyEvts.get(i).evt();
                }

            }
            keysdown.remove(key);
        }

    }

    public boolean isKeyUp(KeyboardKey key) {
        return !isKeyDown(key);
    }

    public void setMousePosition(int mx, int my) {
        //System.Windows.Forms.Cursor.Position = new System.Drawing.Point(WINDOW_X + mx, WINDOW_Y + my);
    }

    public void setMouseVisible(boolean isVisible) {
        // if (!isVisible)
        //     System.Windows.Forms.Cursor.Hide();
        // else
        //     System.Windows.Forms.Cursor.Show();
    }

}


