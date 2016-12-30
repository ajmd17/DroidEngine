//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:49 PM
//

package ApexEngine.Rendering.Light;

import ApexEngine.Math.Vector3f;
import ApexEngine.Rendering.Light.LightSource;
import ApexEngine.Rendering.Shader;

public class PointLight  extends LightSource 
{
    public static final String P_LIGHT_NAME = "Env_PointLights";
    public static final String P_LIGHT_POSITION = "position";
    public static final String P_LIGHT_COLOR = "color";
    private Vector3f position = new Vector3f();
    public Vector3f getPosition()  {
        return position;
    }

    public void setPosition(Vector3f value)  {
        position.set(value);
    }

    public void bindLight(int index, Shader shader)  {
        shader.setUniform(P_LIGHT_NAME + "[" + String.valueOf(index) + "]." + P_LIGHT_POSITION,position);
        shader.setUniform(P_LIGHT_NAME + "[" + String.valueOf(index) + "]." + P_LIGHT_COLOR,color);
    }

}


