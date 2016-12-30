package ApexEngine.Rendering.PostProcess;

import ApexEngine.Rendering.Camera;
import ApexEngine.Rendering.Shader;
import ApexEngine.Rendering.ShaderProperties;
import ApexEngine.Rendering.Texture;

public abstract class PostFilter   
{
    protected PostShader shader;
    protected Camera cam;
    private boolean saveColorTexture = true;
    protected Texture colorTexture, depthTexture;
    public PostFilter(String fs_code) {
        this(new PostShader(fs_code));
    }

    public PostFilter(ShaderProperties properties, String fs_code) {
        this(new PostShader(properties, fs_code));
    }

    public PostFilter(Shader shader) {
        if (shader instanceof PostShader)
            this.shader = (PostShader)shader;
		else
			try {
				throw new Exception("Must be of type PostShader!");
			} catch (Exception e) {
				e.printStackTrace();
			} 
    }

    public PostShader getShader() {
        return shader;
    }

    public void setShader(PostShader value) {
        shader = value;
    }

    public Camera getCam() {
        return cam;
    }

    public void setCam(Camera value) {
        cam = value;
    }

    public boolean getSaveColorTexture() {
        return saveColorTexture;
    }

    public void setSaveColorTexture(boolean value) {
        saveColorTexture = value;
    }

    public Texture getColorTexture() {
        return colorTexture;
    }

    public void setColorTexture(Texture value) {
        colorTexture = value;
    }

    public Texture getDepthTexture() {
        return depthTexture;
    }

    public void setDepthTexture(Texture value) {
        depthTexture = value;
    }

    public abstract void update() ;

    public abstract void end() ;

}


