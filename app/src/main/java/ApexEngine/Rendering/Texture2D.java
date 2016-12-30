//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:50 PM
//

package ApexEngine.Rendering;

public class Texture2D extends Texture {
    protected String texturePath = "";
    protected int width, height;

    public Texture2D(int id) {
        super(id);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int value) {
        width = value;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int value) {
        height = value;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(String value) {
        texturePath = value;
    }

    public static void clear() {
        RenderManager.getRenderer().bindTexture2D(0);
    }

    public void use() {
        RenderManager.getRenderer().bindTexture2D(id);
    }

    public void setWrap(int s, int t) {
        use();
        RenderManager.getRenderer().textureWrap2D(WrapMode.Repeat, WrapMode.Repeat);
    }

    public void setFilter(int min, int mag) {
        use();
        RenderManager.getRenderer().textureFilter2D(min, mag);
    }

    public void generateMipmap() {
        use();
        RenderManager.getRenderer().generateMipmap2D();
    }

    public String toString() {
        return texturePath;
    }

}


