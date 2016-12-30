package ApexEngine.Rendering.PostProcess;

import java.util.ArrayList;

import ApexEngine.Math.Vector2f;
import ApexEngine.Math.Vector3f;
import ApexEngine.Rendering.Camera;
import ApexEngine.Rendering.Framebuffer;
import ApexEngine.Rendering.Mesh;
import ApexEngine.Rendering.Mesh.PrimitiveTypes;
import ApexEngine.Rendering.PostProcess.Filters.DefaultPostFilter;
import ApexEngine.Rendering.PostProcess.PostFilter;
import ApexEngine.Rendering.RenderManager;
import ApexEngine.Rendering.Texture;
import ApexEngine.Rendering.Vertex;
import ApexEngine.Scene.Geometry;

public class PostProcessor   
{
    private Framebuffer fbo;
    private Texture colorTexture, depthTexture;
    private RenderManager renderManager;
    private Camera cam;
    private ArrayList<PostFilter> postFilters = new ArrayList<PostFilter>();
    private Geometry quadGeom;
    public PostProcessor(RenderManager rm, Camera cam) {
        this.renderManager = rm;
        this.cam = cam;
        fbo = new Framebuffer(cam.getWidth(),cam.getHeight());
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

    public ArrayList<PostFilter> getPostFilters() {
        return postFilters;
    }

    public void init() {
        fbo.init();
        Mesh mesh = new Mesh();
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        vertices.add(new Vertex(new Vector3f(-1f, -1f, 0),new Vector2f(0,0)));
        vertices.add(new Vertex(new Vector3f(1, -1f, 0),new Vector2f(1f, 0)));
        vertices.add(new Vertex(new Vector3f(1f, 1f, 0),new Vector2f(1f, 1f)));
        vertices.add(new Vertex(new Vector3f(-1f, 1f, 0),new Vector2f(0, 1f)));
        mesh.setVertices(vertices);
        mesh.setPrimitiveType(PrimitiveTypes.TriangleFan);
        quadGeom = new Geometry(mesh);
        postFilters.add(new DefaultPostFilter());
    }

    public void capture() {
        if (fbo.getWidth() != cam.getWidth() || fbo.getHeight() != cam.getHeight())
        {
            fbo.setWidth(cam.getWidth());
            fbo.setHeight(cam.getHeight());
            fbo.init();
        }
         
        fbo.capture();
        RenderManager.getRenderer().clear(true, true, false);
    }

    public void release() {
        fbo.release();
        colorTexture = fbo.getColorTexture();
        depthTexture = fbo.getDepthTexture();
        for (int i = postFilters.size() - 1;i > -1;i--)
        {
            PostFilter pf = postFilters.get(i);
            pf.setCam(cam);
            pf.setColorTexture(colorTexture);
            pf.setDepthTexture(depthTexture);
            pf.getShader().use();
            pf.update();
            quadGeom.setShader(pf.getShader());
            quadGeom.render(null, cam);
            pf.end();
            if (pf.getSaveColorTexture())
                renderManager.saveScreenToTexture(cam, colorTexture);
             
        }
    }

}


