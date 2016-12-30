//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:50 PM
//

package ApexEngine.Rendering;

import ApexEngine.Assets.LoadedAsset;
import ApexEngine.Rendering.RenderManager;
import ApexEngine.Rendering.Texture;

public abstract class Texture   
{
    protected int id;
    public enum WrapMode
    {
        Repeat,
        ClampToBorder,
        ClampToEdge
    }
    public enum FilterMode
    {
        Linear,
        Nearest,
        Mipmap
    }
    public static Texture loadTexture(LoadedAsset asset)  {
        return RenderManager.getRenderer().loadTexture2D(asset);
    }

    public static int genTextureID()  {
        return RenderManager.getRenderer().genTexture();
    }

    public Texture(int id)  {
        this.id = id;
    }

    public int getID()  {
        return this.id;
    }

    public void setID(int id)  {
        this.id = id;
    }

    public static void activeTextureSlot(int slot)  {
        RenderManager.getRenderer().activeTextureSlot(slot);
    }

    public abstract void use()  ;

    public abstract void setFilter(int min, int mag)  ;

    public abstract void generateMipmap()  ;

}


