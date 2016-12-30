//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:50 PM
//

package ApexEngine.Rendering;

import ApexEngine.Rendering.ShaderProperties;
import java.util.HashMap;

public class ShaderProperties   
{
    public HashMap<String,Object> values = new HashMap<String,Object>();
    public ShaderProperties()  {
    }

    public ShaderProperties(ShaderProperties other)  {
        for (String str : other.values.keySet())
        {
            values.put(str, other.values.get(str));
        }
    }

    public boolean equals(Object obj) {
        try
        {
            if (obj == null)
                return false;
             
            if (obj instanceof ShaderProperties)
            {
                return equals((ShaderProperties)obj);
            }
            else
            {
                return false;
            } 
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

    public ShaderProperties combine(ShaderProperties other)  {
        String[] keys_other = (String[])(other.values.keySet()).toArray();
        Object[] vals_other = other.values.values().toArray();
        for (int i = 0;i < keys_other.length;i++)
        {
            if (!values.containsKey(keys_other[i]))
            {
                values.put(keys_other[i], vals_other[i]);
            }
             
        }
        return this;
    }

    public int hashCode() {
        try
        {
            return super.hashCode();
        }
        catch (RuntimeException __dummyCatchVar1)
        {
            throw __dummyCatchVar1;
        }
        catch (Exception __dummyCatchVar1)
        {
            throw new RuntimeException(__dummyCatchVar1);
        }
    
    }

    public boolean equals(ShaderProperties s1) {
        try
        {
            return ApexEngine.Rendering.Util.ShaderUtil.compareShader(this, s1);
        }
        catch (RuntimeException __dummyCatchVar2)
        {
            throw __dummyCatchVar2;
        }
        catch (Exception __dummyCatchVar2)
        {
            throw new RuntimeException(__dummyCatchVar2);
        }
    
    }

    public ShaderProperties setProperty(String name, Object val)  {
        values.put(name, val);
        return this;
    }

    public Object getValue(String name)  {
        if (values.containsKey(name))
            return values.get(name);
        else
            return null; 
    }

    public boolean getBool(String name)  {
        Object obj = getValue(name);
        if (obj instanceof Boolean)
        {
            return (Boolean)obj;
        }
         
        return false;
    }

    public int getInt(String name)  {
        Object obj = getValue(name);
        if (obj instanceof Integer)
        {
            return (Integer)obj;
        }
         
        return 0;
    }

    public float getFloat(String name)  {
        Object obj = getValue(name);
        if (obj instanceof Float)
        {
            return (Float)obj;
        }
         
        return Float.NaN;
    }

    public String toString() {
        try
        {
            String res = "Shader Properties:\n{\n";
            String[] keys = (String[])(values.keySet().toArray());
            Object[] vals = (Object[])(values.values().toArray());
            for (int i = 0;i < keys.length;i++)
            {
                res += "\t" + keys[i] + ": " + vals[i].toString() + "\n";
            }
            res += "}";
            return res;
        }
        catch (RuntimeException __dummyCatchVar3)
        {
            throw __dummyCatchVar3;
        }
        catch (Exception __dummyCatchVar3)
        {
            throw new RuntimeException(__dummyCatchVar3);
        }
    
    }

}


