//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:49 PM
//

package ApexEngine.Rendering;

import java.util.HashMap;

import ApexEngine.Math.Vector2f;
import ApexEngine.Math.Vector3f;
import ApexEngine.Math.Vector4f;

public class Material {
    public static final String MATERIAL_NAME = "matname";
    public static final String MATERIAL_ALPHADISCARD = "alpha_discard";
    public static final String MATERIAL_BLENDMODE = "blendmode";
    // 0 = opaque, 1 = transparent
    public static final String MATERIAL_DEPTHTEST = "depthtest";
    public static final String MATERIAL_DEPTHMASK = "depthmask";
    public static final String MATERIAL_FACETOCULL = "cullface";
    // 0 = back, 1 = front
    public static final String MATERIAL_CULLENABLED = "cullenable";
    public static final String MATERIAL_CASTSHADOWS = "cast_shadows";
    public static final String COLOR_DIFFUSE = "diffuse";
    public static final String COLOR_SPECULAR = "specular";
    public static final String COLOR_AMBIENT = "ambient";
    public static final String TEXTURE_DIFFUSE = "diffuse_map";
    public static final String TEXTURE_NORMAL = "normal_map";
    public static final String TEXTURE_SPECULAR = "specular_map";
    public static final String TEXTURE_HEIGHT = "height_map";
    public static final String TEXTURE_ENV = "env_map";
    public static final String SPECULAR_EXPONENT = "spec_exponent";
    public static final String SHININESS = "shininess";
    public static final String ROUGHNESS = "roughness";
    public static final String METALNESS = "metalness";
    public static final String TECHNIQUE_SPECULAR = "spec_technique";
    public static final String TECHNIQUE_PER_PIXEL_LIGHTING = "per_pixel_lighting";

    private Vector2f tmpVec2 = new Vector2f();
    private Vector3f tmpVec3 = new Vector3f();
    private Vector4f tmpVec4 = new Vector4f();

    public HashMap<String, Object> values = new HashMap<String, Object>();
    protected ApexEngine.Rendering.RenderManager.Bucket bucket = ApexEngine.Rendering.RenderManager.Bucket.Opaque;
    private Shader shader, depthShader, normalsShader;

    public Material() {
        setValue(COLOR_DIFFUSE, new Vector4f(1.0f));
        setValue(COLOR_SPECULAR, new Vector4f(1.0f));
        setValue(COLOR_AMBIENT, new Vector4f(0.0f));
        setValue(TECHNIQUE_SPECULAR, 1);
        setValue(TECHNIQUE_PER_PIXEL_LIGHTING, 1);
        setValue(SHININESS, 0.5f);
        setValue(ROUGHNESS, 0.2f);
        setValue(METALNESS, 0.0f);
        setValue(SPECULAR_EXPONENT, 20f);
        setValue(MATERIAL_BLENDMODE, 0);
        setValue(MATERIAL_CASTSHADOWS, 1);
        setValue(MATERIAL_DEPTHMASK, true);
        setValue(MATERIAL_DEPTHTEST, true);
        setValue(MATERIAL_ALPHADISCARD, 0.1f);
        setValue(MATERIAL_CULLENABLED, true);
        setValue(MATERIAL_FACETOCULL, 0);
    }

    public HashMap<String, Object> getValues() {
        return values;
    }

    public Material setName(String name) {
        return setValue(MATERIAL_NAME, name);
    }

    public String getName() {
        return getString(MATERIAL_NAME);
    }

    public ApexEngine.Rendering.RenderManager.Bucket getBucket() {
        return bucket;
    }

    public void setBucket(ApexEngine.Rendering.RenderManager.Bucket value) {
        bucket = value;
    }

    public Material setValue(String name, Object val) {
        if (values.containsKey(name)) {
            values.put(name, val);
            return this;
        }

        values.put(name, val);
        return this;
    }

    public Object getValue(String name) {
        if (values.containsKey(name))
            return values.get(name);
        else
            return null;
    }

    public Texture getTexture(String name) {
        Object obj = getValue(name);
        if (obj != null && obj instanceof Texture) {
            return (Texture) obj;
        }
        return null;
    }

    public String getString(String name) {
        Object obj = getValue(name);
        if (obj != null && obj instanceof String) {
            return (String) obj;
        }
        return "";
    }

    public int getInt(String name) {
        Object obj = getValue(name);
        if (obj != null && obj instanceof Integer) {
            return (Integer) obj;
        }
        return 0;
    }

    public boolean getBool(String name) {
        Object obj = getValue(name);
        if (obj != null) {
            if (obj instanceof Boolean) {
                return (Boolean) obj;
            } else if (obj instanceof Integer) {
                return (Integer) obj != 0;
            } else if (obj instanceof Float) {
                return (Float) obj != 0.0f;
            }
        }
        return false;
    }

    public float getFloat(String name) {
        Object obj = getValue(name);
        if (obj != null && obj instanceof Float) {
            return (Float) obj;
        }
        return 0.0f;
    }

    public Vector2f getVector2f(String name) {
        Object obj = getValue(name);
        if (obj != null && obj instanceof Vector2f) {
            return (Vector2f) obj;
        }

        return tmpVec2;
    }

    public Vector3f getVector3f(String name) {
        Object obj = getValue(name);
        if (obj != null && obj instanceof Vector3f) {
            return (Vector3f) obj;
        }
        return tmpVec3;
    }

    public Vector4f getVector4f(String name) {
        Object obj = getValue(name);
        if (obj != null && obj instanceof Vector4f) {
            return (Vector4f) obj;
        }
        return tmpVec4;
    }

    public Shader getShader() {
        return shader;
    }

    public void setShader(Shader value) {
        shader = value;
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

    public Material clone() {
        Material res = new Material();
        res.setBucket(this.getBucket());
        res.setShader(this.getShader());
        res.setDepthShader(this.getDepthShader());
        res.setNormalsShader(this.getNormalsShader());

        for (String str : values.keySet()) {
            res.setValue(str, values.get(str));
        }

        return res;
    }

}


