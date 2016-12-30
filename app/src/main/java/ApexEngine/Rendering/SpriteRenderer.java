package ApexEngine.Rendering;

import ApexEngine.Game;
import ApexEngine.Math.Color4f;
import ApexEngine.Math.Quaternion;
import ApexEngine.Math.Transform;
import ApexEngine.Math.Vector3f;
import ApexEngine.Rendering.Cameras.OrthoCamera;
import ApexEngine.Rendering.Renderer.BlendMode;
import ApexEngine.Rendering.Util.MeshFactory;
import ApexEngine.Scene.Geometry;

public class SpriteRenderer {
    private static Color4f white = new Color4f(1, 1, 1, 1);

    private Mesh mesh;
    private Geometry geom = new Geometry();
    private Shader shader;

    public Camera camera;
    private Camera mycam = new OrthoCamera(-1, 1, -1, 1, -1, 1);

    private Transform ctransform = new Transform();
    private Transform dtransform = new Transform();
    private Vector3f ctrans = new Vector3f();
    private Vector3f cscale = new Vector3f();
    private Quaternion crot = new Quaternion();

    private Color4f currentColor = new Color4f(white);

    private float widthScale, heightScale;

    private Game game;

    public SpriteRenderer(Game game) {
        this.game = game;
        this.camera = game.getCamera();

        mesh = null;
        shader = null;
    }

    private void setup() {
        float ar = game.getInputManager().SCREEN_WIDTH / game.getInputManager().SCREEN_HEIGHT;
        mycam.getProjectionMatrix().setToOrtho(-ar, ar, -1, 1, -1, 1);

        if (mesh == null) {
            mesh = MeshFactory.createQuad();
            shader = new Shader(new ShaderProperties(),
                    "attribute vec3 a_position;" + "attribute vec3 a_normal;" + "attribute vec2 a_texcoord;"
                            + "uniform mat4 u_world; uniform mat4 u_view; uniform mat4 u_proj;"
                            + "varying vec2 v_texcoord;" + "void main() {" + "	v_texcoord = a_texcoord;"
                            + "	gl_Position = u_world * vec4(a_position, 1.0);" + "}",

                    "uniform sampler2D u_texture;" + "uniform vec4 u_color;" + "uniform int B_hasTexture;"
                            + "varying vec2 v_texcoord;" + "void main() {" + " vec4 texcolor = vec4(1.0);\n "
                            + " if (B_hasTexture > 0) {\n"
                            + "		texcolor = texture2D(u_texture, vec2(-v_texcoord.x, -v_texcoord.y));\n" + "	}\n"
                            + "	gl_FragColor = texcolor*u_color;\n" + "}") {
                @Override
                public void update(Environment environ, Camera c, Mesh mesh) {
                    super.update(environ, c, mesh);
                    if (this.currentMaterial.getTexture(Material.TEXTURE_DIFFUSE) != null) {
                        Texture diffuse = this.currentMaterial.getTexture(Material.TEXTURE_DIFFUSE);
                        Texture.activeTextureSlot(0);
                        diffuse.use();
                        this.setUniform("u_texture", 0);
                        this.setUniform("B_hasTexture", 1);
                    } else {
                        this.setUniform("B_hasTexture", 0);
                    }
                    ctransform.setTranslation(ctrans);
                    ctransform.setRotation(crot);
                    ctransform.setScale(cscale);
                    this.setUniform("u_color", currentColor);
                    this.setUniform("u_world", ctransform.getMatrix());
                    this.setUniform("u_view", mycam.getViewMatrix());
                    this.setUniform("u_proj", mycam.getProjectionMatrix());
                }

                @Override
                public void end() {
                    super.end();
                    Texture2D.clear();
                }
            };
            geom.setMesh(mesh);
            geom.setShader(shader);
            geom.getMaterial().setValue(Material.MATERIAL_DEPTHMASK, false);
            geom.getMaterial().setValue(Material.MATERIAL_DEPTHTEST, false);
            geom.getMaterial().setValue(Material.MATERIAL_CULLENABLED, false);
        }
    }

    public void draw(Texture2D tex, float x, float y, float width, float height) {
        draw(tex, x, y, width, height, white);
    }

    public void draw(Texture2D tex, float x, float y) {
        draw(tex, x, y, tex.getWidth(), tex.getHeight());
    }

    public void draw(Texture2D tex, float x, float y, float width, float height, Color4f color) {
        draw(tex, dtransform, x, y, width, height, color);
    }

    public void draw(Texture2D tex, Transform transform, float x, float y, float width, float height, Color4f color) {
        setup();
        currentColor = color;
        geom.getMaterial().setValue(Material.TEXTURE_DIFFUSE, tex);
        geom.getMaterial().setValue(Material.MATERIAL_BLENDMODE, 1);

        widthScale = 1f / (game.getInputManager().SCREEN_WIDTH);
        heightScale = 1f / (game.getInputManager().SCREEN_HEIGHT);
        cscale.set(width * widthScale, height * heightScale, 1.0f);
        ctrans.x = (2f * ((x * widthScale) + (cscale.x / 2)) - 1f);
        ctrans.y = (2f * ((y * heightScale) + (cscale.y / 2)) - 1f);
        ctrans.z = 1;

        crot = transform.getRotation();


        RenderManager.getRenderer().setDepthTest(false);
        RenderManager.getRenderer().setDepthMask(false);
        RenderManager.getRenderer().setBlend(true);
        RenderManager.getRenderer().setBlendMode(BlendMode.AlphaBlend);

        geom.render(null, camera);

        RenderManager.getRenderer().setDepthTest(true);
        RenderManager.getRenderer().setDepthMask(true);
        RenderManager.getRenderer().setBlend(false);
    }
}
