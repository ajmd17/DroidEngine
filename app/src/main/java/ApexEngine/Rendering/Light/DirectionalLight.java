//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:49 PM
//

package ApexEngine.Rendering.Light;

import ApexEngine.Math.Vector3f;
import ApexEngine.Math.Vector4f;
import ApexEngine.Rendering.Shader;

public class DirectionalLight extends LightSource {
    public static final String D_LIGHT_NAME = "Env_DirectionalLight";
    public static final String D_LIGHT_DIRECTION = "direction";
    public static final String D_LIGHT_COLOR = "color";
    protected Vector3f direction = new Vector3f();

    public DirectionalLight() {
        direction.set(1.0f, 1.0f, 1.0f);
        color.set(1f, 1f, 1f, 1.0f);
    }

    public DirectionalLight(Vector3f dir) {
        direction.set(dir);
        color.set(1f, 0.9f, 0.8f, 1.0f);
    }

    public DirectionalLight(Vector3f dir, Vector4f clr) {
        direction.set(dir);
        color.set(color);
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f value) {
        direction.set(value);
    }

    public void bindLight(int index, Shader shader) {
        shader.setUniform(D_LIGHT_NAME + "." + D_LIGHT_DIRECTION, this.direction);
        shader.setUniform(D_LIGHT_NAME + "." + D_LIGHT_COLOR, this.color);
    }

}


