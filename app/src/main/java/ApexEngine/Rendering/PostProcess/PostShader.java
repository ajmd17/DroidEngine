package ApexEngine.Rendering.PostProcess;

import ApexEngine.Rendering.Shader;
import ApexEngine.Rendering.ShaderProperties;

public class PostShader extends Shader {
    private static final String VERTEX_CODE = "attribute vec3 a_position;\n" +
            "attribute vec2 a_texcoord0;\n" +
            "varying vec2 v_texCoord0;" +
            "void main()\n" +
            "{\n" +
            "    v_texCoord0 = a_texcoord0;\n" +
            "    gl_Position = vec4(a_position, 1.0);\n" +
            "}\n";

    public PostShader(String fs_code) {
        this(new ShaderProperties(), fs_code);
    }

    public PostShader(ShaderProperties properties, String fs_code) {
        super(properties, VERTEX_CODE, fs_code);
    }

}


