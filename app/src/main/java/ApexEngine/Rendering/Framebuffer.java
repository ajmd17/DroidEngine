package ApexEngine.Rendering;

public class Framebuffer {
    private int width, height, framebufferID, colorTextureID, depthTextureID;
    private Texture colorTexture, depthTexture;

    public Texture getColorTexture() {
        return colorTexture;
    }

    public Texture getDepthTexture() {
        return depthTexture;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int value) {
        width = value;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int value) {
        height = value;
    }

    public Framebuffer(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void use() {
        RenderManager.getRenderer().bindFramebuffer(framebufferID);
    }

    public static void clear() {
        RenderManager.getRenderer().bindFramebuffer(0);
    }

    public void init() {
        if (framebufferID == 0) {
            RenderManager.getRenderer().genFramebuffers(1, framebufferID);
        }

        if (colorTextureID == 0) {
            RenderManager.getRenderer().genTextures(1, colorTextureID);
        }

        if (depthTextureID == 0) {
            RenderManager.getRenderer().genTextures(1, depthTextureID);
        }

        use();
        RenderManager.getRenderer().setupFramebuffer(framebufferID, colorTextureID, depthTextureID, width, height);
        clear();

        if (colorTexture == null) {
            colorTexture = new Texture2D(colorTextureID);
        }

        if (depthTexture == null) {
            depthTexture = new Texture2D(depthTextureID);
        }
    }

    public void capture() {
        use();
        RenderManager.getRenderer().viewport(0, 0, width, height);
    }

    public void release() {
        clear();
        Texture2D.clear();
    }

}


