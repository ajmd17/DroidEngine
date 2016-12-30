package ApexEngine.Assets;

import ApexEngine.Assets.AssetManager;
import ApexEngine.Assets.LoadedAsset;

public abstract class AssetLoader   
{
    public abstract Object load(LoadedAsset asset) ;

    public abstract void resetLoader() ;

    public AssetLoader(String... extensions) {
        for (String ext : extensions)
        {
            AssetManager.registerLoader(ext,this);
        }
    }

}


