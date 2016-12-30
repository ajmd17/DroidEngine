//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:50 PM
//

package ApexEngine.Rendering;

import java.util.ArrayList;

import ApexEngine.Rendering.Shader;
import ApexEngine.Rendering.ShaderProperties;

public class ShaderManager   
{
    private static ArrayList<Shader> shaders = new ArrayList<Shader>();
    public static Shader getShader(Class<?> shaderType)  {
        return getShader(shaderType,new ShaderProperties());
    }

    public static Shader getShader(Class<?> shaderType, ShaderProperties properties) {
    	try {
    		if (Shader.class.isAssignableFrom(shaderType)) {
	    		for (int i = 0; i < shaders.size(); i++) {
	                if (shaders.get(i).getClass() == shaderType) {
	                    if (ApexEngine.Rendering.Util.ShaderUtil.compareShader(shaders.get(i).getProperties(), properties)) {
							return shaders.get(i);
						}
	                }
	            }
	    		return (Shader)shaderType.getDeclaredConstructor(ShaderProperties.class).newInstance(properties);
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}

    	return null;
    }

}


