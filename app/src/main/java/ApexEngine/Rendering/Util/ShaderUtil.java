package ApexEngine.Rendering.Util;

import java.io.File;
import java.util.ArrayList;

import ApexEngine.Assets.AssetManager;
import ApexEngine.Assets.ShaderTextLoader;
import ApexEngine.Math.Vector2f;
import ApexEngine.Math.Vector3f;
import ApexEngine.Math.Vector4f;
import ApexEngine.Rendering.ShaderProperties;

public class ShaderUtil {
    public static String getValueString(String varName, Object val) {
        if (val instanceof Boolean) {
            boolean bval = (Boolean) val;
            return (bval ? "true" : "false");
        } else if (val instanceof Integer) {
            int ival = (Integer) val;
            return String.valueOf(ival);
        } else if (val instanceof Float) {
            float fval = (Float) val;
            return String.valueOf(fval);
        } else if (val instanceof Vector2f) {
            Vector2f vval = (Vector2f) val;
            return "vec2(" + vval.x + ", " + vval.y + ")";
        } else if (val instanceof Vector3f) {
            Vector3f vval = (Vector3f) val;
            return "vec3(" + vval.x + ", " + vval.y + ", " + vval.z + ")";
        } else if (val instanceof Vector4f) {
            Vector4f vval = (Vector4f) val;
            return "vec4(" + vval.x + ", " + vval.y + ", " + vval.z + ", " + vval.w + ")";
        }

        return "";
    }

    public static boolean compareShader(ShaderProperties a, ShaderProperties b) {
        if (a.values.size() != b.values.size()) {
            return false;
        }

        String[] keys_a = (String[]) (a.values.keySet()).toArray();
        String[] keys_b = (String[]) (b.values.keySet()).toArray();
        Object[] vals_a = a.values.values().toArray();
        Object[] vals_b = b.values.values().toArray();

        for (int i = 0; i < keys_a.length; i++) {
            if (!keys_a[i].equals(keys_b[i])) {
                return false;
            } else {
                if (!vals_a[i].equals(vals_b[i])) {
                    return false;
                }
            }
        }

        return true;
    }

    public static String formatShaderVersion(String origCode) {
        String res = "";
        String verString = "";
        String[] lines = origCode.split("\n");
        for (String line : lines) {
            if (line.trim().startsWith("#version")) {
                verString = line.trim();
                line = "";
            }

            if (!lines.equals("")) {
                res += line + "\n";
            }
        }

        return verString + "\n" + res;
    }

    public static String formatShaderIncludes(String shaderPath, String origCode) {
        String res = "";
        String[] lines = origCode.split("\n");
        for (String line : lines) {
            if (line.trim().startsWith("#include")) {
                String path = line.trim().substring("#include ".length());
                if (path.contains("<") || path.contains(">")) {
                    // internal resource
                    path = path.replace("<", "");
                    path = path.replace(">", "");
                    line = (String) AssetManager.load("shaders/inc/" + path, ShaderTextLoader.getInstance());
                } else {
                    // external resource
                    path = path.replace("\"", "");
                    String parentPath = new File(shaderPath).getParent();
                    String incPath = parentPath + "\\" + path;
                    line = (String) AssetManager.load(incPath, ShaderTextLoader.getInstance());
                }
            }

            if (!line.equals("")) {
                res += line + "\n";
            }

        }
        return res;
    }

    public static String formatShaderProperties(String origCode, ShaderProperties properties) {
        String res = "";
        String[] lines = origCode.split("\n");
        boolean inIfStatement = false;
        String ifStatementText = "";
        boolean removing = false;
        ArrayList<String> ifdefs = new ArrayList<String>();
        ArrayList<String> ifndefs = new ArrayList<String>();
        String currentIfDef = "";
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.trim().startsWith("#ifdef")) {
                inIfStatement = true;
                ifStatementText = lines[i].trim().substring(7);
                ifdefs.add("!" + ifStatementText);
                currentIfDef = "!" + ifStatementText;
                boolean remove = !(properties.getBool(ifStatementText));
                int num_ifdefs = 0;
                int num_endifs = 0;
                for (int j = i; j < lines.length; j++) {
                    if (lines[j].trim().startsWith("#ifdef") || lines[j].trim().startsWith("#ifndef")) {
                        num_ifdefs++;
                    } else if (lines[j].trim().startsWith("#endif")) {
                        num_endifs++;
                        if (num_endifs >= num_ifdefs) {
                            break;
                        }

                    } else {
                        if (remove) {
                            lines[j] = "";
                        }

                    }
                }
                lines[i] = "";
            } else if (lines[i].trim().startsWith("#ifndef")) {
                inIfStatement = true;
                ifStatementText = lines[i].trim().substring(8);
                ifdefs.add("!" + ifStatementText);
                currentIfDef = "!" + ifStatementText;
                boolean remove = (properties.getBool(ifStatementText));
                int num_ifdefs = 0;
                int num_endifs = 0;
                for (int j = i; j < lines.length; j++) {
                    if (lines[j].trim().startsWith("#ifdef") || lines[j].trim().startsWith("#ifndef")) {
                        num_ifdefs++;
                    } else if (lines[j].trim().startsWith("#endif")) {
                        num_endifs++;
                        if (num_endifs >= num_ifdefs) {
                            break;
                        }

                    } else {
                        if (remove) {
                            lines[j] = "";
                        }

                    }
                }
                lines[i] = "";
            } else if (lines[i].trim().startsWith("#endif")) {
                lines[i] = "";
            }

            if (!lines[i].equals("")) {
                res += lines[i] + "\n";
            }
        }

        for (String val : properties.values.keySet()) {
            res = res.replaceAll("$" + val, properties.values.get(val).toString());
        }

        return res;
    }
}
