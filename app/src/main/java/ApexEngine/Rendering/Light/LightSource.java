//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:49 PM
//

package ApexEngine.Rendering.Light;

import ApexEngine.Math.Color4f;
import ApexEngine.Rendering.Shader;

public abstract class LightSource {
    protected Color4f color = new Color4f(1.0f);
    protected float intensity = 1.0f;

    public Color4f getColor() {
        return color;
    }

    public void setColor(Color4f value) {
        color.set(value);
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float value) {
        intensity = value;
    }

    public abstract void bindLight(int index, Shader shader);

}


