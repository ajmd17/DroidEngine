package ApexEngine;

import ApexEngine.Assets.AssetManager;
import ApexEngine.Math.Quaternion;
import ApexEngine.Math.Vector3f;
import ApexEngine.Rendering.Renderer;
import ApexEngine.Rendering.Texture2D;
import ApexEngine.Scene.Node;
import ApexEngine.Scene.Sprite;
import ApexEngine.UI.GUIManager;

public class TestGame extends Game {
    Quaternion tmpRot = new Quaternion();
    GUIManager guiMan;
    Texture2D tex;
    Sprite sprite;

    public TestGame(Renderer renderer) {
        super(renderer);
        this.setTitle("Android Test");
        // this.getRenderManager().setBackgroundColor(new Vector4f(0.7f, 0.1f,
        // 0.7f, 1.0f));
    }

    @Override
    public void init() {

        this.getEnvironment().getDirectionalLight().setDirection(new Vector3f(-1, 1, -1));

        Node n = (Node) AssetManager.load("models/monkeyhq.obj");
        n.setLocalTranslation(new Vector3f(0, 0, 5));
        n.setLocalRotation(new Quaternion().setFromAxis(Vector3f.UnitY, 180));
        rootNode.addChild(n);

        // n.getChildGeom(0).getMaterial().setValue(Material.COLOR_DIFFUSE, new
        // Vector4f(0.0f, 0.3f, 0.7f, 1.0f));

        tex = (Texture2D) AssetManager.loadTexture("textures/apex3d.png");
        sprite = new Sprite(tex);
        rootNode.addChild(sprite);

        guiMan = new GUIManager(this);
        /*Panel pnl = new Panel("mypanel", 80, 80, 500, 500);
		Button btn = new Button("hi", 0, 0) {

			@Override
			public void onMouseLeftClick() {
				System.out.println("click");
			}};
			guiMan.addControl(btn);
		guiMan.addControl(pnl);
		pnl.addChild(btn);
		btn.setLayout(Layout.CENTERED);
		
		
		rootNode.addChild(sprite = new Sprite(tex));
		
		Stage stage1 = new Stage("mystage");
		guiMan.addStage(stage1);
		guiMan.showStage("mystage");*/
    }

    @Override
    public void update() {
        //sprite.getLocalRotation().multiplyStore(tmpRot.setFromAxis(Vector3f.UnitZ, 0.5f));
        //sprite.setUpdateNeeded();
    }

    @Override
    public void render() {
    }
}
