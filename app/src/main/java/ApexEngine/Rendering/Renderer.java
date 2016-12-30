//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:49 PM
//

package ApexEngine.Rendering;

import ApexEngine.Assets.LoadedAsset;
import ApexEngine.Game;

public abstract class Renderer {
    public enum Face {
        Back, Front
    }

    public enum FaceDirection {
        Cw, Ccw
    }

    public enum BlendMode {
        AlphaBlend
    }

    public enum BufferBit {
        Depth, Accum, Stencil, Color, Coverage
    }

    /**
     * Creates a render context for a game. Allows the game to actually render.
     *
     * @param game
     * @param width
     * @param height
     */
    public abstract void createContext(Game game, int width, int height);

    /**
     * Generate required buffers for the mesh
     *
     * @param mesh
     */
    public abstract void createMesh(Mesh mesh);

    /**
     * Upload mesh data to the GPU
     *
     * @param mesh
     */
    public abstract void uploadMesh(Mesh mesh);

    /**
     * Delete the mesh
     */
    public abstract void deleteMesh(Mesh mesh);

    /**
     * Render the mesh
     *
     * @param mesh
     */
    public abstract void renderMesh(Mesh mesh);

    /**
     * Load a 2D texture
     *
     * @param n
     * @param textures
     */
    public abstract Texture2D loadTexture2D(LoadedAsset asset);

    /**
     * Load a cubemap texture from 6 texture paths
     *
     * @param filepaths
     * @return
     */
    // public abstract Cubemap loadCubemap(String[] filepaths) ;
    public abstract int genTexture();

    public abstract void genTextures(int n, int textures);

    public abstract void bindTexture2D(int i);

    public abstract void bindTexture3D(int i);

    public abstract void bindTextureCubemap(int i);

    public abstract void textureWrap2D(ApexEngine.Rendering.Texture.WrapMode s,
                                       ApexEngine.Rendering.Texture.WrapMode t);

    public abstract void textureWrapCube(ApexEngine.Rendering.Texture.WrapMode r,
                                         ApexEngine.Rendering.Texture.WrapMode s, ApexEngine.Rendering.Texture.WrapMode t);

    public abstract void textureFilter2D(int min, int mag);

    public abstract void generateMipmap2D();

    public abstract void generateMipmapCubemap();

    public abstract void activeTextureSlot(int slot);

    public abstract int generateShaderProgram();

    public abstract void bindShaderProgram(int id);

    public abstract void compileShaderProgram(int id);

    public abstract void addShader(int id, String code, ApexEngine.Rendering.Shader.ShaderTypes type);

    public abstract void setShaderUniform(int id, String name, int i);

    public abstract void setShaderUniform(int id, String name, float i);

    public abstract void setShaderUniform(int id, String name, float x, float y);

    public abstract void setShaderUniform(int id, String name, float x, float y, float z);

    public abstract void setShaderUniform(int id, String name, float x, float y, float z, float w);

    public abstract void setShaderUniform(int id, String name, float[] matrix);

    public abstract void genFramebuffers(int n, int framebuffers);

    public abstract int genFramebuffer();

    public abstract void setupFramebuffer(int framebufferID, int colorTextureID, int depthTextureID, int width,
                                          int height);

    public abstract void bindFramebuffer(int id);

    public abstract void setDepthTest(boolean depthTest);

    public abstract void setDepthMask(boolean depthMask);

    public abstract void setDepthClamp(boolean depthClamp);

    public abstract void setBlend(boolean blend);

    public abstract void setBlendMode(BlendMode blendMode);

    public abstract void setCullFace(boolean cullFace);

    public abstract void setFaceToCull(Face face);

    public abstract void setFaceDirection(FaceDirection faceDirection);

    public abstract void viewport(int x, int y, int width, int height);

    public abstract void clear(boolean clearColor, boolean clearDepth, boolean clearStencil);

    public abstract void drawVertex(float x, float y);

    public abstract void drawVertex(float x, float y, float z);

    public abstract void clearColor(float r, float g, float b, float a);

    public abstract void copyScreenToTexture2D(int width, int height);

}
