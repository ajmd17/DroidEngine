package ApexEngine.Scene;

import ApexEngine.Math.Color4f;
import ApexEngine.Rendering.Camera;
import ApexEngine.Rendering.Environment;
import ApexEngine.Rendering.SpriteRenderer;
import ApexEngine.Rendering.Texture2D;

public class Sprite extends Geometry {
    private Texture2D texture;
    private static Color4f white = new Color4f(1, 1, 1, 1);

    public Sprite(Texture2D texture) {
        this.texture = texture;
    }

    @Override
    public void render(Environment environment, Camera cam) {
        SpriteRenderer spriteRenderer = renderManager.getSpriteRenderer();
        spriteRenderer.draw(texture, getWorldTransform(), getWorldTransform().getTranslation().x,
                getWorldTransform().getTranslation().y,
                texture.getWidth() * getWorldTransform().getScale().x, texture.getHeight() * getWorldTransform().getScale().y, white);
    }
}
