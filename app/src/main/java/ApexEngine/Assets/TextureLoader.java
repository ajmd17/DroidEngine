package ApexEngine.Assets;

import ApexEngine.Rendering.Texture;

public class TextureLoader extends AssetLoader {
    private static TextureLoader instance = new TextureLoader();

    public static TextureLoader getInstance() {
        return instance;
    }

    public TextureLoader() {
        super("png", "jpg", "jpeg", "bmp", "tiff", "gif");
    }

    public Object load(LoadedAsset asset) {
        return Texture.loadTexture(asset);
    }

    public void resetLoader() {
    }

}


