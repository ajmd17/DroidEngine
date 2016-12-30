//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:48 PM
//

package ApexEngine.Rendering.Cameras;

import ApexEngine.Rendering.Camera;

public class OrthoCamera extends Camera {
    protected float left, right, bottom, top;

    public float getLeft() {
        return left;
    }

    public void setLeft(float value) {
        left = value;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float value) {
        right = value;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float value) {
        top = value;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float value) {
        bottom = value;
    }

    public OrthoCamera(float left, float right, float bottom, float top, float near, float far) {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
        this.near = near;
        this.far = far;
    }

    public void updateCamera() {
    }

    public void updateMatrix() {
        projMatrix.setToOrtho(left, right, bottom, top, near, far);
        viewProjMatrix.set(viewMatrix);
        viewProjMatrix.multiplyStore(projMatrix);
        invViewProjMatrix.set(viewProjMatrix);
        invViewProjMatrix.invertStore();
    }

}


