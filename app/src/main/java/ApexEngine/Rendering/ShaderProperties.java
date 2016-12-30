//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:50 PM
//

package ApexEngine.Rendering;

import java.util.HashMap;

public class ShaderProperties {
    public HashMap<String, Object> values = new HashMap<String, Object>();

    public ShaderProperties() {
    }

    public ShaderProperties(ShaderProperties other) {
        for (String str : other.values.keySet()) {
            values.put(str, other.values.get(str));
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return (obj instanceof ShaderProperties) && equals((ShaderProperties) obj);
    }

    public ShaderProperties combine(ShaderProperties other) {
        String[] keys_other = (String[]) (other.values.keySet()).toArray();
        Object[] vals_other = other.values.values().toArray();

        for (int i = 0; i < keys_other.length; i++) {
            if (!values.containsKey(keys_other[i])) {
                values.put(keys_other[i], vals_other[i]);
            }

        }
        return this;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(ShaderProperties s1) {
        return ApexEngine.Rendering.Util.ShaderUtil.compareShader(this, s1);
    }

    public ShaderProperties setProperty(String name, Object val) {
        values.put(name, val);
        return this;
    }

    public Object getValue(String name) {
        return values.containsKey(name) ? values.get(name) : null;
    }

    public boolean getBool(String name) {
        Object obj = getValue(name);
        if (obj instanceof Boolean) {
            return (Boolean)obj;
        }

        return false;
    }

    public int getInt(String name) {
        Object obj = getValue(name);
        if (obj instanceof Integer) {
            return (Integer)obj;
        }

        return 0;
    }

    public float getFloat(String name) {
        Object obj = getValue(name);
        if (obj instanceof Float) {
            return (Float)obj;
        }

        return 0.0f;
    }

    public String toString() {
        try {
            String res = "Shader Properties:\n{\n";
            String[] keys = (String[]) (values.keySet().toArray());
            Object[] vals = (Object[]) (values.values().toArray());
            for (int i = 0; i < keys.length; i++) {
                res += "\t" + keys[i] + ": " + vals[i].toString() + "\n";
            }
            res += "}";
            return res;
        } catch (RuntimeException __dummyCatchVar3) {
            throw __dummyCatchVar3;
        } catch (Exception __dummyCatchVar3) {
            throw new RuntimeException(__dummyCatchVar3);
        }

    }

}


