package ApexEngine.Rendering.PostProcess.Filters;

import ApexEngine.Rendering.PostProcess.PostFilter;
import ApexEngine.Rendering.PostProcess.PostShader;
import ApexEngine.Rendering.Texture;

public class DefaultPostFilter extends PostFilter {
    public DefaultPostFilter() throws Exception {
        super(new PostShader(
                "uniform sampler2D u_texture;\n" +
                "varying vec2 v_texCoord0;\n" +
                "void main()\n" +
                "{\n" +
                "  gl_FragColor = texture2D(u_texture, v_texCoord0);\n" +
                "}\n"));
    }

    public void end() {
    }

    public void update() {
        Texture diffTex = colorTexture;

        if (diffTex != null) {
            Texture.activeTextureSlot(0);
            diffTex.use();
            shader.setUniform("u_texture", 0);
        }
    }

}


