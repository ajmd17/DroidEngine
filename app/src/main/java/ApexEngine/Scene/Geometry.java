package ApexEngine.Scene;

import ApexEngine.Math.BoundingBox;
import ApexEngine.Rendering.Camera;
import ApexEngine.Rendering.Environment;
import ApexEngine.Rendering.Material;
import ApexEngine.Rendering.Mesh;
import ApexEngine.Rendering.Shader;
import ApexEngine.Rendering.ShaderManager;
import ApexEngine.Rendering.ShaderProperties;
import ApexEngine.Rendering.Shaders.DefaultShader;
import ApexEngine.Rendering.Texture;

public class Geometry extends GameObject {
    protected ShaderProperties g_shaderProperties = new ShaderProperties();
    protected Mesh mesh;
    protected Shader shader, depthShader, normalsShader;
    protected BoundingBox worldBoundingBox, localBoundingBox;
    private Material material = new Material();

    public Geometry() {
        super();
    }

    public Geometry(Mesh mesh) {
        this();
        this.mesh = mesh;
    }

    public Geometry(Mesh mesh, Shader shader) {
        this(mesh);
        this.shader = shader;
    }

    public BoundingBox getWorldBoundingBox() {
        if (worldBoundingBox == null) {
            worldBoundingBox = new BoundingBox();
            updateWorldBoundingBox();
        }

        return worldBoundingBox;
    }

    public BoundingBox getLocalBoundingBox() {
        if (localBoundingBox == null) {
            localBoundingBox = new BoundingBox();
            updateLocalBoundingBox();
        }

        return localBoundingBox;
    }

    public void updateWorldBoundingBox() {
        if (worldBoundingBox != null)
            if (mesh != null)
                worldBoundingBox = mesh.createBoundingBox(getWorldMatrix());

    }

    public void updateLocalBoundingBox() {
        if (localBoundingBox != null)
            if (mesh != null)
                localBoundingBox = mesh.createBoundingBox();

    }

    public ShaderProperties getShaderProperties() {
        return g_shaderProperties;
    }

    public void setShaderProperties(ShaderProperties value) {
        g_shaderProperties = value;
    }

    public Shader getDepthShader() {
        return depthShader;
    }

    public void setDepthShader(Shader value) {
        depthShader = value;
    }

    public Shader getNormalsShader() {
        return normalsShader;
    }

    public void setNormalsShader(Shader value) {
        normalsShader = value;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material value) {
        if (mesh != null) {
            material = value;
        }

    }

    public void setDefaultShader() {
        if (mesh.getSkeleton() != null) {
            g_shaderProperties.setProperty("SKINNING", true).setProperty("NUM_BONES", mesh.getSkeleton().getNumBones());
        }
        g_shaderProperties.setProperty("DEFAULT", true);
        g_shaderProperties.setProperty("NORMALS", false);
        g_shaderProperties.setProperty("DEPTH", false);
        setShader(DefaultShader.class, g_shaderProperties);
        g_shaderProperties.setProperty("DEFAULT", false);
    }

    public void render(Environment environment, Camera cam) {
        if (shader == null) {
            setDefaultShader();
        }

        if (mesh != null) {
            shader.use();
            shader.applyMaterial(getMaterial());
            shader.setTransforms(getWorldMatrix(), cam.getViewMatrix(), cam.getProjectionMatrix());
            shader.update(environment, cam, mesh);
            shader.render(mesh);
            shader.end();
            Shader.clear();
        }

    }

    public void updateShaderProperties() {
        String[] keys = (String[]) getMaterial().values.keySet().toArray();
        Object[] vals = getMaterial().getValues().values().toArray();
        for (int i = 0; i < vals.length; i++) {
            if (vals[i] == null) {
            } else {
                // g_shaderProperties.SetProperty(keys[i].ToUpper(), false);
                /*
				 * if (vals[i] is int)
				 * g_shaderProperties.SetProperty(keys[i].ToUpper(),
				 * (int)vals[i]); else if (vals[i] is float)
				 * g_shaderProperties.SetProperty(keys[i].ToUpper(),
				 * (float)vals[i]); else if (vals[i] is bool)
				 * g_shaderProperties.SetProperty(keys[i].ToUpper(),
				 * (bool)vals[i]); else if (vals[i] is string)
				 * g_shaderProperties.SetProperty(keys[i].ToUpper(),
				 * (string)vals[i]); else
				 */
                if (vals[i] instanceof Texture)
                    g_shaderProperties.setProperty(keys[i].toUpperCase(), true);

            }
        }
        if (shader != null) {
            Class<?> shaderType = shader.getClass();
            setShader(shaderType, g_shaderProperties);
        }

    }

    public void updateParents() {
        super.updateParents();
        if (renderManager != null) {
            if (attachedToRoot) {
                renderManager.addGeometry(this);
            } else {
                renderManager.removeGeometry(this);
            }
        }
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh value) {
        mesh = value;
    }

    public Shader getShader() {
        return shader;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public void setShader(Class<?> shaderType) {
        setShader(ShaderManager.getShader(shaderType, new ShaderProperties().setProperty("DEFAULT", true)));
    }

    public void setShader(Class<?> shaderType, ShaderProperties properties) {
        ShaderProperties p = new ShaderProperties(properties);
        p.setProperty("DEFAULT", true);
        setShader(ShaderManager.getShader(shaderType, p));
    }

    public GameObject clone() {
        Geometry res = new Geometry();
        res.setLocalTranslation(this.getLocalTranslation());
        res.setLocalScale(this.getLocalScale());
        res.setLocalRotation(this.getLocalRotation());
        res.setMesh(this.mesh.clone());
        res.shader = this.shader;
        res.depthShader = this.depthShader;
        res.normalsShader = this.normalsShader;
        res.setShaderProperties(this.getShaderProperties());
        return res;
    }

}
