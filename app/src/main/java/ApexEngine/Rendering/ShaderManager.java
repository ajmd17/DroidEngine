//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:50 PM
//

package ApexEngine.Rendering;

import java.util.ArrayList;

public class ShaderManager {
    private static ArrayList<Shader> shaders = new ArrayList<Shader>();

    public static Shader getShader(Class<?> shaderType) {
        return getShader(shaderType, new ShaderProperties());
    }

    public static Shader getShader(Class<?> shaderType, ShaderProperties properties) {
        try {
            if (Shader.class.isAssignableFrom(shaderType)) {
                for (Shader shader : shaders) {
                    if (shader.getClass() == shaderType && ApexEngine.Rendering.Util.ShaderUtil.compareShader(shader.getProperties(), properties)) {
                        return shader;
                    }
                }
                // create a new instance
                return (Shader)shaderType.getDeclaredConstructor(ShaderProperties.class).newInstance(properties);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}


