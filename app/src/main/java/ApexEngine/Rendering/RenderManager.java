//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:49 PM
//

package ApexEngine.Rendering;

import java.util.ArrayList;

import ApexEngine.Math.Vector4f;
import ApexEngine.Rendering.Camera;
import ApexEngine.Rendering.Environment;
import ApexEngine.Rendering.Framebuffer;
import ApexEngine.Rendering.Material;
//import ApexEngine.Rendering.PostProcess.PostProcessor;
import ApexEngine.Rendering.Renderer;
import ApexEngine.Rendering.RenderManager;
import ApexEngine.Rendering.Shader;
import ApexEngine.Rendering.ShaderManager;
import ApexEngine.Rendering.ShaderProperties;
//import ApexEngine.Rendering.Shaders.DepthShader;
import ApexEngine.Rendering.Texture;
import ApexEngine.Rendering.Texture2D;
import ApexEngine.Rendering.PostProcess.PostProcessor;
import ApexEngine.Rendering.Shaders.DepthShader;
import ApexEngine.Scene.Components.RenderComponent;
import ApexEngine.Scene.Geometry;

public class RenderManager {
    private static float elapsedTime = 0f;
    private static Renderer renderer;

    protected ArrayList<Geometry> geometries = new ArrayList<Geometry>();
    protected ArrayList<RenderComponent> components = new ArrayList<RenderComponent>();

    private SpriteRenderer spriteRenderer;
    private PostProcessor postProcessor;
    private Framebuffer depthFbo;
    private Texture depthTexture;
    private Vector4f backgroundColor = new Vector4f(0.39f, 0.58f, 0.93f, 1.0f);

    public enum Bucket {
        Opaque,
        Transparent,
        Sky,
        Particle
    }

    public enum DepthRenderMode {
        Depth,
        Shadow
    }

    public RenderManager(Renderer renderer, Camera cam)  {
        RenderManager.renderer = renderer;
        postProcessor = new PostProcessor(this, cam);
        depthFbo = new Framebuffer(cam.getWidth(),cam.getHeight());
    }

	public SpriteRenderer getSpriteRenderer() {
		return spriteRenderer;
	}

	public void setSpriteRenderer(SpriteRenderer spriteRenderer) {
		this.spriteRenderer = spriteRenderer;
	}

    public static Renderer getRenderer()  {
        return renderer;
    }

    public static void setRenderer(Renderer value)  {
        renderer = value;
    }

    public Vector4f getBackgroundColor()  {
        return backgroundColor;
    }

    public void setBackgroundColor(Vector4f value)  {
        backgroundColor.set(value);
    }

    public PostProcessor getPostProcessor()  {
        return postProcessor;
    }

    public Texture getDepthTexture()  {
        return depthTexture;
    }

    public ArrayList<RenderComponent> getRenderComponents()  {
        return components;
    }

    public ArrayList<Geometry> getGeometryList()  {
        return geometries;
    }

    public void init()  {
        postProcessor.init();
        depthFbo.init();
    }

    public void addComponent(RenderComponent cmp)  {
        components.add(cmp);
        cmp.renderManager = this;
        cmp.init();
    }

    public void removeComponent(RenderComponent cmp)  {
        components.remove(cmp);
        if (cmp != null)
            cmp.renderManager = null;
         
    }

    public static float getElapsedTime()  {
        return elapsedTime;
    }

    public static void setElapsedTime(float value)  {
        elapsedTime = value;
    }

    public void addGeometry(Geometry geom)  {
        if (!geometries.contains(geom)) {
            geometries.add(geom);
        }
    }

    public void removeGeometry(Geometry geom)  {
        if (geometries.contains(geom)) {
            geometries.remove(geom);
        }
    }

    public void saveScreenToTexture(Camera cam, Texture toSaveTo)  {
        toSaveTo.use();
        getRenderer().copyScreenToTexture2D(cam.getWidth(),cam.getHeight());
        Texture2D.clear();
    }

    public void renderDepthTexture(Environment env, Camera cam) {
        if (depthFbo.getWidth() != cam.getWidth() || depthFbo.getHeight() != cam.getHeight()) {
            depthFbo.setWidth(cam.getWidth());
            depthFbo.setHeight(cam.getHeight());
            depthFbo.init();
        }
         
        depthFbo.capture();
        renderer.clear(true, true, false);
        renderBucketDepth(env,cam,ApexEngine.Rendering.RenderManager.Bucket.Opaque,DepthRenderMode.Depth);
        renderBucketDepth(env,cam,ApexEngine.Rendering.RenderManager.Bucket.Transparent,DepthRenderMode.Depth);
        depthFbo.release();

        depthTexture = depthFbo.getColorTexture();
    }

    public void renderBucket(Environment env, Camera cam, ApexEngine.Rendering.RenderManager.Bucket bucket) {
        for (int i = 0; i < geometries.size(); i++) {
            if (geometries.get(i).getAttachedToRoot() && geometries.get(i).getMaterial().getBucket() == bucket) {
                geometries.get(i).render(env, cam);
            }
        }
    }

    public void renderBucketDepth(Environment env, Camera cam, ApexEngine.Rendering.RenderManager.Bucket bucket, DepthRenderMode renderMode) {
        for (int i = 0; i < geometries.size(); i++) {
            if (geometries.get(i).getAttachedToRoot() && geometries.get(i).getMaterial().getBucket() == bucket) {
                if (geometries.get(i).getDepthShader() == null) {
                    if (geometries.get(i).getShader() == null) {
                        geometries.get(i).setDefaultShader();
                    }

                    ShaderProperties p = new ShaderProperties(geometries.get(i).getShaderProperties());
                    p.setProperty("DEPTH", true);
                    geometries.get(i).setDepthShader(ShaderManager.getShader(DepthShader.class, p));
                }

                if (renderMode == DepthRenderMode.Shadow && geometries.get(i).getMaterial().getBool(Material.MATERIAL_CASTSHADOWS)) {
                    geometries.get(i).getDepthShader().use();
                    geometries.get(i).getDepthShader().applyMaterial(geometries.get(i).getMaterial());
                    geometries.get(i).getDepthShader().setTransforms(geometries.get(i).getWorldMatrix(), cam.getViewMatrix(), cam.getProjectionMatrix());
                    geometries.get(i).getDepthShader().update(env, cam, geometries.get(i).getMesh());
                    geometries.get(i).getDepthShader().render(geometries.get(i).getMesh());
                    geometries.get(i).getDepthShader().end();
                }

                Shader.clear();
                Texture2D.clear();
            }
        }
    }

    public void renderBucketNormals(Environment env, Camera cam, ApexEngine.Rendering.RenderManager.Bucket bucket)  {
        for (int i = 0; i < geometries.size(); i++) {
            if (geometries.get(i).getAttachedToRoot() && geometries.get(i).getMaterial().getBucket() == bucket) {
                if (geometries.get(i).getNormalsShader() == null) {
                    if (geometries.get(i).getShader() == null) {
                        geometries.get(i).setDefaultShader();
                    }
                     
                    ShaderProperties p = new ShaderProperties(geometries.get(i).getShaderProperties());
                    p.setProperty("NORMALS",true);
                    geometries.get(i).setNormalsShader(ShaderManager.getShader(geometries.get(i).getShader().getClass(),p));
                }
                 
                geometries.get(i).getNormalsShader().use();
                geometries.get(i).getNormalsShader().applyMaterial(geometries.get(i).getMaterial());
                geometries.get(i).getNormalsShader().setTransforms(geometries.get(i).getWorldMatrix(),cam.getViewMatrix(),cam.getProjectionMatrix());
                geometries.get(i).getNormalsShader().update(env,cam,geometries.get(i).getMesh());
                geometries.get(i).getNormalsShader().render(geometries.get(i).getMesh());
                geometries.get(i).getNormalsShader().end();

                Shader.clear();
            }
        }
    }

    public void render(Environment env, Camera cam) {
        renderer.viewport(0,0,cam.getWidth(),cam.getHeight());
        renderer.clearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, backgroundColor.w);
        renderer.clear(true, true, false);

        for (RenderComponent rc : components) {
            rc.render();
            rc.update();
        }

        postProcessor.capture();
        renderBucket(env,cam,ApexEngine.Rendering.RenderManager.Bucket.Sky);
        renderBucket(env,cam,ApexEngine.Rendering.RenderManager.Bucket.Opaque);
        renderBucket(env,cam,ApexEngine.Rendering.RenderManager.Bucket.Transparent);
        renderBucket(env,cam,ApexEngine.Rendering.RenderManager.Bucket.Particle);
        postProcessor.release();
    }
}


