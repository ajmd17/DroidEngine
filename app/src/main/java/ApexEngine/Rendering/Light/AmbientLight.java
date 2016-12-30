//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:49 PM
//

package ApexEngine.Rendering.Light;

import ApexEngine.Math.Vector4f;
import ApexEngine.Rendering.Shader;

public class AmbientLight extends LightSource {
    public static final String A_LIGHT_NAME = "Env_AmbientLight";
    public static final String A_LIGHT_INTENSITY = "intensity";
    public static final String A_LIGHT_COLOR = "color";

    public AmbientLight() {
        color.set(0.3f, 0.2f, 0.1f, 0.0f);
    }

    public AmbientLight(Vector4f color) {
        this.color.set(color);
    }

    public AmbientLight(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
    }

    public void bindLight(int index, Shader shader) {
        shader.setUniform(A_LIGHT_NAME + "." + A_LIGHT_INTENSITY, this.intensity);
        shader.setUniform(A_LIGHT_NAME + "." + A_LIGHT_COLOR, this.color);
    }

}


