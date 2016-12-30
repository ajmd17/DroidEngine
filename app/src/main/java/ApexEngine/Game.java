package ApexEngine;

import java.util.ArrayList;

import ApexEngine.Assets.AssetManager;
import ApexEngine.Input.InputManager;
import ApexEngine.Math.Vector3f;
import ApexEngine.Rendering.Camera;
import ApexEngine.Rendering.Cameras.DefaultCamera;
import ApexEngine.Rendering.Environment;
import ApexEngine.Rendering.RenderManager;
import ApexEngine.Rendering.Renderer;
import ApexEngine.Rendering.SpriteRenderer;
import ApexEngine.Scene.Components.GameComponent;
import ApexEngine.Scene.Node;

public abstract class Game {
    private InputManager inputManager = new InputManager();
    protected Node rootNode = new Node("root");
    protected Camera cam;
    protected String windowTitle = "Apex3D Game";
    private RenderManager renderManager;
    protected ArrayList<GameComponent> components = new ArrayList<GameComponent>();
    private Environment environment = new Environment();
    Vector3f tmpVec = new Vector3f();

    public Game(Renderer renderer) {
        AssetManager.initDefaultLoaders();

        cam = new DefaultCamera(inputManager, 55);
        renderManager = new RenderManager(renderer, cam);
        renderManager.setSpriteRenderer(new SpriteRenderer(this));
    }

    public String getTitle() {
        return windowTitle;
    }

    public void setTitle(String value) {
        windowTitle = value;
    }

    public ArrayList<GameComponent> getGameComponents() {
        return components;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment value) {
        environment = value;
    }

    public void addComponent(GameComponent cmp) {
        components.add(cmp);
        cmp.cam = cam;
        rootNode.addChild(cmp.rootNode);
        cmp.init();
    }

    public void removeComponent(GameComponent cmp) {
        components.remove(cmp);
        rootNode.removeChild(cmp.rootNode);
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public RenderManager getRenderManager() {
        return renderManager;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public void setRootNode(Node value) {
        rootNode = value;
    }

    public Camera getCamera() {
        return cam;
    }

    public void setCamera(Camera value) {
        cam = value;
    }

    public void run() {
        RenderManager.getRenderer().createContext(this, 1080, 720);
    }

    public void initInternal() {
        renderManager.init();
        init();
    }

    public void updateInternal() {
        moveToOrigin();
        for (GameComponent cmp : components)
            cmp.update();
        environment.setElapsedTime(environment.getElapsedTime() + 0.1f);
        cam.update();
        rootNode.update(renderManager);
        update();
    }

    public void renderInternal() {
        renderManager.render(getEnvironment(), cam);
        render();
    }

    private void moveToOrigin() {
        float maxDist = 25;
        Vector3f position = cam.getTranslation();
        if (position.x > maxDist) {
            rootNode.getLocalTranslation().addStore(tmpVec.set(-maxDist, 0, 0));
            rootNode.setUpdateNeeded();
            cam.getTranslation().set(0, position.y, position.z);
            cam.updateCamera();
            cam.updateMatrix();
            rootNode.update(renderManager);
        }

        if (position.z > maxDist) {
            rootNode.getLocalTranslation().addStore(tmpVec.set(0, 0, -maxDist));
            rootNode.setUpdateNeeded();
            cam.getTranslation().set(position.x, position.y, 0);
            cam.updateCamera();
            cam.updateMatrix();
            rootNode.update(renderManager);
        }

        if (position.x < -maxDist) {
            rootNode.getLocalTranslation().addStore(tmpVec.set(maxDist, 0, 0));
            rootNode.setUpdateNeeded();
            cam.getTranslation().set(0, position.y, position.z);
            cam.updateCamera();
            cam.updateMatrix();
            rootNode.update(renderManager);
        }

        if (position.z < -maxDist) {
            rootNode.getLocalTranslation().addStore(tmpVec.set(0, 0, maxDist));
            rootNode.setUpdateNeeded();
            cam.getTranslation().set(position.x, position.y, 0);
            cam.updateCamera();
            cam.updateMatrix();
            rootNode.update(renderManager);
        }
    }

    public abstract void init();

    public abstract void update();

    public abstract void render();

}
