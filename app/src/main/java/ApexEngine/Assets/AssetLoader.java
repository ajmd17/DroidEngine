package ApexEngine.Assets;

public abstract class AssetLoader {
    public abstract Object load(LoadedAsset asset);

    public abstract void resetLoader();

    public AssetLoader(String... extensions) {
        for (String ext : extensions) {
            AssetManager.registerLoader(ext, this);
        }
    }

}


