//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:50 PM
//

package ApexEngine.Rendering.Shaders;

import ApexEngine.Assets.AssetManager;
import ApexEngine.Rendering.Shader;
import ApexEngine.Rendering.ShaderProperties;

public class DepthShader  extends Shader 
{
    public DepthShader(ShaderProperties properties) {
        super(properties, (String)AssetManager.load("shaders/depth.vert"), (String)AssetManager.load("shaders/depth.frag"));
    }

}


