//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:48 PM
//

package ApexEngine.Rendering;

import ApexEngine.Math.Matrix4f;
import ApexEngine.Math.Ray;
import ApexEngine.Math.Vector2f;
import ApexEngine.Math.Vector3f;

public abstract class Camera {
    protected Vector3f translation = new Vector3f(0, 0, 0);
    protected Vector3f direction = new Vector3f(0, 0, 1);
    protected Vector3f up = new Vector3f(0, 1, 0);
    protected int width = 512, height = 512;
    protected float near = 0.6f, far = 150.0f;

    protected Matrix4f viewMatrix = new Matrix4f(),
            projMatrix = new Matrix4f(),
            viewProjMatrix = new Matrix4f(),
            invViewProjMatrix = new Matrix4f();

    protected boolean enabled = true;

    public Camera() {
    }

    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean value) {
        enabled = value;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(Matrix4f value) {
        viewMatrix = value;
    }

    public Matrix4f getProjectionMatrix() {
        return projMatrix;
    }

    public void setProjectionMatrix(Matrix4f value) {
        projMatrix = value;
    }

    public Matrix4f getViewProjectionMatrix() {
        return viewProjMatrix;
    }

    public void setViewProjectionMatrix(Matrix4f value) {
        viewProjMatrix = value;
    }

    public Matrix4f getInverseViewProjectionMatrix() {
        return invViewProjMatrix;
    }

    public void setInverseViewProjectionMatrix(Matrix4f value) {
        invViewProjMatrix = value;
    }

    public Vector3f unproject(Vector2f mouseXY) {
        return unproject(mouseXY.getX(), mouseXY.getY());
    }

    public Vector3f unproject(float mouseX, float mouseY) {
        Vector3f vec = new Vector3f();
        vec.x = -2f * (mouseX / width);
        vec.y = 2f * (mouseY / height);
        vec.z = 0f;
        vec.multiplyStore(projMatrix.invert());
        vec.multiplyStore(viewMatrix.invert());
        vec.subtractStore(getTranslation());
        return vec;
    }

    /**
     * @return Returns a new ray which can be used to intersect objects in the scene.
     */
    public Ray getCameraRay() {
        return new Ray(direction.multiply(1000), translation);
    }

    /**
     * @return Returns a new ray which can be used to intersect objects in the scene.
     */
    public Ray getCameraRay(int mx, int my) {
        Vector3f origin = getTranslation();
        Vector3f unprojected = unproject(mx, my);
        unprojected.multiplyStore(1000f);

        Ray ray = new Ray(unprojected, origin);

        return ray;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3f value) {
        translation.set(value);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int value) {
        width = value;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int value) {
        height = value;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f value) {
        direction.set(value);
    }

    public Vector3f getUp() {
        return up;
    }

    public void setUp(Vector3f value) {
        up.set(value);
    }

    public float getNear() {
        return near;
    }

    public void setNear(float value) {
        near = value;
    }

    public float getFar() {
        return far;
    }

    public void setFar(float value) {
        far = value;
    }

    public void rotate(Vector3f axis, float angle) {
        direction.rotateStore(axis, angle);
        direction.normalizeStore();
    }

    public void lookAtDirection(Vector3f dir) {
        direction.set(dir);
    }

    public void lookAt(Vector3f location) {
        direction.set(location.subtract(translation));
    }

    public void update() {
        if (enabled) {
            updateCamera();
        }

        updateMatrix();
    }

    public abstract void updateMatrix();

    public abstract void updateCamera();

}


