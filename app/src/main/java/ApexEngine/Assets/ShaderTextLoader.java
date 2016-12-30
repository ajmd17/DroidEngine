package ApexEngine.Assets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ApexEngine.Rendering.Util.ShaderUtil;

public class ShaderTextLoader extends AssetLoader {

    private static ShaderTextLoader instance;

    public static ShaderTextLoader getInstance() {
        if (instance == null)
            instance = new ShaderTextLoader();
        return instance;
    }

    public ShaderTextLoader() {
        super("frag", "vert", "geom", "fs", "vs", "gs", "glsl", "gl");
    }

    @Override
    public Object load(LoadedAsset asset) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(asset.getData()));
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return ShaderUtil.formatShaderIncludes(asset.getFilePath(), sb.toString());
    }

    @Override
    public void resetLoader() {
    }

}
