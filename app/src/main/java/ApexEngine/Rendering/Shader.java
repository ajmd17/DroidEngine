//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:50 PM
//

package ApexEngine.Rendering;

import ApexEngine.Assets.AssetManager;
import ApexEngine.Math.Matrix4f;
import ApexEngine.Math.Vector2f;
import ApexEngine.Math.Vector3f;
import ApexEngine.Math.Vector4f;
import ApexEngine.Rendering.Renderer.Face;
import ApexEngine.Rendering.Renderer.FaceDirection;

public class Shader {
    public static final String A_POSITION = "a_position";
    public static final String A_TEXCOORD0 = "a_texcoord0";
    public static final String A_TEXCOORD1 = "a_texcoord1";
    public static final String A_NORMAL = "a_normal";
    public static final String A_TANGENT = "a_tangent";
    public static final String A_BITANGENT = "a_bitangent";
    public static final String A_BONEWEIGHT = "a_boneweights";
    public static final String A_BONEINDEX = "a_boneindices";
    public static final String APEX_WORLDMATRIX = "Apex_WorldMatrix";
    public static final String APEX_VIEWMATRIX = "Apex_ViewMatrix";
    public static final String APEX_PROJECTIONMATRIX = "Apex_ProjectionMatrix";
    public static final String APEX_CAMERAPOSITION = "Apex_CameraPosition";
    public static final String APEX_CAMERADIRECTION = "Apex_CameraDirection";
    public static final String APEX_ELAPSEDTIME = "Apex_ElapsedTime";
    public static final String MATERIAL_ALPHADISCARD = "Material_AlphaDiscard";
    public static final String MATERIAL_SHININESS = "Material_Shininess";
    public static final String MATERIAL_ROUGHNESS = "Material_Roughness";
    public static final String MATERIAL_METALNESS = "Material_Metalness";
    public static final String MATERIAL_AMBIENTCOLOR = "Material_AmbientColor";
    public static final String MATERIAL_DIFFUSECOLOR = "Material_DiffuseColor";
    public static final String MATERIAL_SPECULARCOLOR = "Material_SpecularColor";
    public static final String MATERIAL_SPECULARTECHNIQUE = "Material_SpecularTechnique";
    public static final String MATERIAL_SPECULAREXPONENT = "Material_SpecularExponent";
    public static final String MATERIAL_PERPIXELLIGHTING = "Material_PerPixelLighting";
    public static final String ENV_FOGCOLOR = "Env_FogColor";
    public static final String ENV_FOGSTART = "Env_FogStart";
    public static final String ENV_FOGEND = "Env_FogEnd";
    public static final String ENV_NUMPOINTLIGHTS = "Env_NumPointLights";

    protected Material currentMaterial = null;
    protected ShaderProperties properties;
    protected Matrix4f worldMatrix, viewMatrix, projectionMatrix;
    protected int id = 0;
    private Matrix4f tmpMat = new Matrix4f();

    public enum ShaderTypes {
        Vertex,
        Fragment,
        Geometry,
        TessEval,
        TessControl
    }

    public static String getApexVertexHeader() {
        String res = "";
        res += "uniform mat4 Apex_WorldMatrix;\nuniform mat4 Apex_ViewMatrix;\nuniform mat4 Apex_ProjectionMatrix;\n";
        res += "mat4 FinalTransform() {\n" + "     return Apex_ProjectionMatrix * Apex_ViewMatrix * Apex_WorldMatrix;\n" + "}\n";
        return res;
    }

    public Shader(ShaderProperties properties, String vs_code, String fs_code) {
        this.properties = properties;
        create();

        addVertexProgram(ApexEngine.Rendering.Util.ShaderUtil.formatShaderIncludes(
                AssetManager.getAppPath(),
                ApexEngine.Rendering.Util.ShaderUtil.formatShaderVersion(
                        ApexEngine.Rendering.Util.ShaderUtil.formatShaderProperties(vs_code, properties)
                )
        ));

        addFragmentProgram(ApexEngine.Rendering.Util.ShaderUtil.formatShaderIncludes(
                AssetManager.getAppPath(),
                ApexEngine.Rendering.Util.ShaderUtil.formatShaderVersion(
                        ApexEngine.Rendering.Util.ShaderUtil.formatShaderProperties(fs_code, properties)
                )
        ));

        compileShader();
    }

    public Shader(ShaderProperties properties, String vs_code, String fs_code, String gs_code) {
        this.properties = properties;
        create();

        addVertexProgram(ApexEngine.Rendering.Util.ShaderUtil.formatShaderIncludes(
                AssetManager.getAppPath(),
                ApexEngine.Rendering.Util.ShaderUtil.formatShaderVersion(
                        getApexVertexHeader() + ApexEngine.Rendering.Util.ShaderUtil.formatShaderProperties(vs_code, properties)
                )
        ));

        addFragmentProgram(ApexEngine.Rendering.Util.ShaderUtil.formatShaderIncludes(
                AssetManager.getAppPath(),
                ApexEngine.Rendering.Util.ShaderUtil.formatShaderVersion(
                        ApexEngine.Rendering.Util.ShaderUtil.formatShaderProperties(fs_code, properties)
                )
        ));

        addGeometryProgram(ApexEngine.Rendering.Util.ShaderUtil.formatShaderIncludes(
                AssetManager.getAppPath(),
                ApexEngine.Rendering.Util.ShaderUtil.formatShaderVersion(
                        ApexEngine.Rendering.Util.ShaderUtil.formatShaderProperties(gs_code, properties)
                )
        ));

        compileShader();
    }

    public ShaderProperties getProperties() {
        return properties;
    }

    public void create() {
        id = RenderManager.getRenderer().generateShaderProgram();
    }

    public void use() {
        RenderManager.getRenderer().bindShaderProgram(id);
    }

    public void end() {
        currentMaterial = null;
    }

    public static void clear() {
        RenderManager.getRenderer().bindShaderProgram(0);
    }

    public void compileShader() {
        use();
        RenderManager.getRenderer().compileShaderProgram(id);
    }

    public void applyMaterial(Material material) {
        currentMaterial = material;

        RenderManager.getRenderer().setFaceDirection(FaceDirection.Ccw);
        RenderManager.getRenderer().setDepthTest(currentMaterial.getBool(Material.MATERIAL_DEPTHTEST));
        RenderManager.getRenderer().setDepthMask(currentMaterial.getBool(Material.MATERIAL_DEPTHMASK));

        if (currentMaterial.getBool(Material.MATERIAL_CULLENABLED)) {
            RenderManager.getRenderer().setCullFace(true);
            RenderManager.getRenderer().setFaceToCull(Face.values()[currentMaterial.getInt(Material.MATERIAL_FACETOCULL)]);
        } else {
            RenderManager.getRenderer().setCullFace(false);
        }

        setUniform(MATERIAL_ALPHADISCARD, currentMaterial.getFloat(Material.MATERIAL_ALPHADISCARD));
    }

    public void render(Mesh mesh) {
        mesh.render();
    }

    public void update(Environment environment, Camera cam, Mesh mesh) {
        setDefaultValues();

        setUniform(APEX_WORLDMATRIX, worldMatrix);
        setUniform(APEX_VIEWMATRIX, viewMatrix);
        setUniform(APEX_PROJECTIONMATRIX, projectionMatrix);
        setUniform(APEX_CAMERAPOSITION, cam.getTranslation());
        setUniform(APEX_CAMERADIRECTION, cam.getDirection());
    }

    private static void setDefaultValues() {
        RenderManager.getRenderer().setDepthClamp(true);
        RenderManager.getRenderer().setFaceDirection(FaceDirection.Ccw);
    }

    public void setTransforms(Matrix4f world, Matrix4f view, Matrix4f proj) {
        worldMatrix = world;
        viewMatrix = view;
        projectionMatrix = proj;
    }

    public Matrix4f getWorldMatrix() {
        return worldMatrix;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public void addVertexProgram(String vs) {
        addProgram(vs, ApexEngine.Rendering.Shader.ShaderTypes.Vertex);
    }

    public void addFragmentProgram(String fs) {
        addProgram(fs, ApexEngine.Rendering.Shader.ShaderTypes.Fragment);
    }

    public void addGeometryProgram(String gs) {
        addProgram(gs, ApexEngine.Rendering.Shader.ShaderTypes.Geometry);
    }

    public void addProgram(String code, ApexEngine.Rendering.Shader.ShaderTypes type) {
        RenderManager.getRenderer().addShader(id, code, type);
    }

    public void setUniform(String name, int i) {
        RenderManager.getRenderer().setShaderUniform(id, name, i);
    }

    public void setUniform(String name, float f) {
        RenderManager.getRenderer().setShaderUniform(id, name, f);
    }

    public void setUniform(String name, float x, float y) {
        RenderManager.getRenderer().setShaderUniform(id, name, x, y);
    }

    public void setUniform(String name, float x, float y, float z) {
        RenderManager.getRenderer().setShaderUniform(id, name, x, y, z);
    }

    public void setUniform(String name, float x, float y, float z, float w) {
        RenderManager.getRenderer().setShaderUniform(id, name, x, y, z, w);
    }

    public void setUniform(String name, Vector2f vec) {
        setUniform(name, vec.getX(), vec.getY());
    }

    public void setUniform(String name, Vector2f[] vec) {
        for (int i = 0; i < vec.length; i++) {
            setUniform(name + "[" + String.valueOf(i) + "]", vec[i].getX(), vec[i].getY());
        }
    }

    public void setUniform(String name, Vector3f vec) {
        setUniform(name, vec.getX(), vec.getY(), vec.getZ());
    }

    public void setUniform(String name, Vector3f[] vec) {
        for (int i = 0; i < vec.length; i++) {
            setUniform(name + "[" + String.valueOf(i) + "]", vec[i].getX(), vec[i].getY(), vec[i].getZ());
        }
    }

    public void setUniform(String name, Vector4f vec) {
        setUniform(name, vec.x, vec.y, vec.z, vec.w);
    }

    public void setUniform(String name, Vector4f[] vec) {
        for (int i = 0; i < vec.length; i++) {
            setUniform(name + "[" + String.valueOf(i) + "]", vec[i].x, vec[i].y, vec[i].z, vec[i].w);
        }
    }

    public void setUniform(String name, Matrix4f mat) {
        RenderManager.getRenderer().setShaderUniform(id, name, mat.getInvertedValues());
    }

}


