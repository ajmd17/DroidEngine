//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:48 PM
//

package ApexEngine.Rendering.Cameras;

import ApexEngine.Input.InputManager;
import ApexEngine.Input.KeyboardEvent;
import ApexEngine.Input.MouseEvent;
import ApexEngine.Math.MathUtil;
import ApexEngine.Math.Vector3f;

public class DefaultCamera extends PerspectiveCamera {
    public enum CameraMode {
        FPSMode, DragMode, ChaseMode
    }

    private InputManager inputManager = null;
    protected CameraMode camMode = CameraMode.DragMode;
    protected boolean mouseCaptured = true, mouseDragging = false;
    private Vector3f dirCrossY = new Vector3f();
    private float oldX = 0, oldY = 0, magX = 0, magY = 0;

    public DefaultCamera(InputManager inputManager) {
        super();
        this.inputManager = inputManager;
    }

    public DefaultCamera(InputManager inputManager, float fov) {
        super();
        this.fov = fov;
        this.inputManager = inputManager;
        KeyboardEvent evt_mouseRelease = new KeyboardEvent(ApexEngine.Input.InputManager.KeyboardKey.LeftAlt) {

            @Override
            public void evt() {
                centerMouse();
                mouseCaptured = !mouseCaptured;
                if (camMode == CameraMode.FPSMode)
                    DefaultCamera.this.inputManager.setMouseVisible(!mouseCaptured);
            }
        };

        inputManager.addKeyboardEvent(evt_mouseRelease);
        MouseEvent evt_mouseClick = new MouseEvent(ApexEngine.Input.InputManager.MouseButton.Left, false) {

            @Override
            public void evt() {
                System.out.println("Click!");
                centerMouse();
                mouseDragging = true;
                mouseCaptured = true;
                // if (camMode == CameraMode.DragMode)
                DefaultCamera.this.inputManager.setMouseVisible(!mouseDragging);
            }

        };
        inputManager.addMouseEvent(evt_mouseClick);
        MouseEvent evt_mouseUp = new MouseEvent(ApexEngine.Input.InputManager.MouseButton.Left, true) {

            @Override
            public void evt() {
                System.out.println("Release!");
                centerMouse();
                mouseDragging = false;
                if (camMode == CameraMode.DragMode)
                    DefaultCamera.this.inputManager.setMouseVisible(!mouseDragging);
            }
        };

        inputManager.addMouseEvent(evt_mouseUp);
    }

    protected void centerMouse() {
        if (enabled) {
            int halfWidth = inputManager.SCREEN_WIDTH / 2;
            int halfHeight = inputManager.SCREEN_HEIGHT / 2;
            inputManager.setMousePosition(halfWidth, halfHeight);
        }

    }

    public void updateCamera() {
        this.width = inputManager.SCREEN_WIDTH;
        this.height = inputManager.SCREEN_HEIGHT;
        input();
    }

    float rot = 0f;

    protected void input() {
        rot += 0.1f;
        //mouseInput(inputManager.getMouseX(), inputManager.getMouseY(), width / 2, height / 2);
        keyboardInput();
    }

    protected void mouseInput(int x, int y, int halfWidth, int halfHeight) {
        if (camMode == CameraMode.FPSMode && mouseCaptured) {
            magX = (float) x;
            magY = (float) y;
            //magX *= -0.1f;
            //magY *= -0.1f;
            dirCrossY.set(direction);
            dirCrossY.crossStore(Vector3f.UnitY);
            rotate(Vector3f.UnitY, magX);
            rotate(dirCrossY, magY);
            inputManager.setMousePosition(halfWidth, halfHeight);
        } else if (camMode == CameraMode.DragMode && mouseDragging) {
            magX = ((float) x) - oldX;
            magY = ((float) y) - oldY;
            magX *= 0.05f;
            magY *= 0.05f;
            dirCrossY.set(direction);
            dirCrossY.crossStore(Vector3f.UnitY);
            rotate(Vector3f.UnitY, magX);
            rotate(dirCrossY, magY);
            inputManager.setMousePosition(halfWidth, halfHeight);
            oldX = (float) x;
            oldY = (float) y;
        }
        //rotate(Vector3f.UnitY, 0.1f);
    }

    protected void keyboardInput() {
        if (inputManager.isKeyDown(ApexEngine.Input.InputManager.KeyboardKey.W)) {
            // forwards
            translation.x += direction.x * 0.1f;
            translation.y += direction.y * 0.1f;
            translation.z += direction.z * 0.1f;
        }

        if (inputManager.isKeyDown(ApexEngine.Input.InputManager.KeyboardKey.S)) {
            // backwards
            translation.x += direction.x * -0.1f;
            translation.y += direction.y * -0.1f;
            translation.z += direction.z * -0.1f;
        }

        if (inputManager.isKeyDown(ApexEngine.Input.InputManager.KeyboardKey.A)) {
            // left
            translation.x += 0.1f * (float) Math.sin(MathUtil.toRadians(yaw + 90));
            translation.z -= 0.1f * (float) Math.cos(MathUtil.toRadians(yaw + 90));
        }

        if (inputManager.isKeyDown(ApexEngine.Input.InputManager.KeyboardKey.D)) {
            // left
            translation.x += 0.1f * (float) Math.sin(MathUtil.toRadians(yaw - 90));
            translation.z -= 0.1f * (float) Math.cos(MathUtil.toRadians(yaw - 90));
        }

    }

}
