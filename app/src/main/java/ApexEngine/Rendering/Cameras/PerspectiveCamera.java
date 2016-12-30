//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:48 PM
//

package ApexEngine.Rendering.Cameras;

import ApexEngine.Math.Quaternion;
import ApexEngine.Math.Vector3f;
import ApexEngine.Rendering.Camera;

public class PerspectiveCamera  extends Camera 
{
    protected Quaternion rotation = new Quaternion();
    protected float fov = 45f, yaw, roll, pitch;
    private Vector3f tmp = new Vector3f();
    public PerspectiveCamera() {
        super();
    }

    public PerspectiveCamera(float fov, int width, int height) {
        super(width, height);
        this.fov = fov;
    }

    public float getFieldOfView() {
        return fov;
    }

    public void setFieldOfView(float value) {
        fov = value;
    }

    public void updateMatrix() {
        tmp.set(translation);
        tmp.addStore(direction);
        rotation.setToLookAt(direction, up);
        viewMatrix.setToLookAt(translation, tmp, up);
        projMatrix.setToProjection(fov, width, height, 0.2f, far);
        viewProjMatrix.set(projMatrix);
        viewProjMatrix.multiplyStore(viewMatrix);
        invViewProjMatrix.set(viewProjMatrix);
        invViewProjMatrix.invertStore();
        yaw = rotation.getYaw();
        roll = rotation.getRoll();
        pitch = rotation.getPitch();
    }

    public void updateCamera() {
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    public float getPitch() {
        return pitch;
    }

}


