package ApexEngine.Scene;

import java.util.ArrayList;

import ApexEngine.Math.BoundingBox;
import ApexEngine.Math.Matrix4f;
import ApexEngine.Math.Quaternion;
import ApexEngine.Math.Transform;
import ApexEngine.Math.Vector3f;
import ApexEngine.Rendering.RenderManager;
import ApexEngine.Scene.Components.Controller;

public abstract class GameObject {
    protected RenderManager renderManager = null;
    private boolean updateNeeded = true;
    protected ArrayList<Controller> controls = new ArrayList<Controller>();
    protected Transform worldTransform = new Transform();
    protected Vector3f localTranslation;
    protected Vector3f localScale;
    protected Quaternion localRotation;
    protected Matrix4f worldMatrix = new Matrix4f();
    protected boolean attachedToRoot = false;
    protected String name = "";
    protected Node parent;
    private Vector3f wtrans = new Vector3f(), wscl = new Vector3f();
    private Quaternion wrot = new Quaternion();

    public GameObject() {
        localTranslation = new Vector3f(0, 0, 0);
        localScale = new Vector3f(1, 1, 1);
        localRotation = new Quaternion(0, 0, 0, 1);
        worldTransform = new Transform();
        parent = null;
    }

    public GameObject(String name) {
        this.name = name;
        localTranslation = new Vector3f(0, 0, 0);
        localScale = new Vector3f(1, 1, 1);
        localRotation = new Quaternion(0, 0, 0, 1);
        worldTransform = new Transform();
        parent = null;
    }

    public abstract BoundingBox getWorldBoundingBox();

    public abstract BoundingBox getLocalBoundingBox();

    public abstract void updateWorldBoundingBox();

    public abstract void updateLocalBoundingBox();

    public void addController(Controller ctrl) {
        if (!hasController(ctrl.getClass())) {
            controls.add(ctrl);
            ctrl.setGameObject(this);
            ctrl.init();
        } else {
            try {
                throw new Exception("Cannot add two controllers of the same type!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeController(Controller ctrl) {
        if (controls.contains(ctrl)) {
            ctrl.setGameObject(null);
            controls.remove(ctrl);
        }

    }

    public boolean hasController(Class<?> ctrlType) {
        for (int i = 0; i < controls.size(); i++) {
            if (controls.get(i).getClass() == ctrlType) {
                return true;
            }

        }
        return false;
    }

    public Controller getController(Class<?> ctrlType) {
        for (int i = 0; i < controls.size(); i++) {
            if (controls.get(i).getClass() == ctrlType) {
                return controls.get(i);
            }

        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        name = value;
    }

    public void setParent(Node newParent) {
        this.parent = newParent;
        setUpdateNeeded();
        if (parent == null) {
            updateParents();
        }

    }

    public Node getParent() {
        return parent;
    }

    public Transform getWorldTransform() {
        return worldTransform;
    }

    public Vector3f getLocalTranslation() {
        return localTranslation;
    }

    public Vector3f getWorldTranslation() {
        return worldTransform.getTranslation();
    }

    public void setLocalTranslation(Vector3f vec) {
        localTranslation.set(vec);
        setUpdateNeeded();
    }

    public Vector3f getWorldScale() {
        return worldTransform.getScale();
    }

    public Vector3f getLocalScale() {
        return localScale;
    }

    public void setLocalScale(Vector3f value) {
        localScale.set(value);
        setUpdateNeeded();
    }

    public Quaternion getLocalRotation() {
        return localRotation;
    }

    public Quaternion getWorldRotation() {
        return worldTransform.getRotation();
    }

    public void setLocalRotation(Quaternion quat) {
        localRotation.set(quat);
        setUpdateNeeded();
    }

    protected void updateWorldTranslation(Vector3f outw) {
        outw.addStore(localTranslation);
        if (parent != null) {
            Node p = parent;
            p.updateWorldTranslation(outw);
        }

    }

    public Vector3f getUpdatedWorldTranslation() {
        wtrans.set(0, 0, 0);
        updateWorldTranslation(wtrans);
        return wtrans;
    }

    protected void updateWorldScale(Vector3f outw) {
        outw.multiplyStore(localScale);
        if (parent != null) {
            Node p = parent;
            p.updateWorldScale(outw);
        }

    }

    public Vector3f getUpdatedWorldScale() {
        wscl.set(1, 1, 1);
        updateWorldScale(wscl);
        return wscl;
    }

    protected void updateWorldRotation(Quaternion outw) {
        outw.multiplyStore(localRotation);
        if (parent != null) {
            Node p = parent;
            p.updateWorldRotation(outw);
        }

    }

    public Quaternion getUpdatedWorldRotation() {
        wrot.setToIdentity();
        updateWorldRotation(wrot);
        return wrot;
    }

    public Matrix4f getWorldMatrix() {
        return worldMatrix;
    }

    public void setUpdateNeeded() {
        updateNeeded = true;
    }

    public void update(RenderManager renderManager) {
        this.renderManager = renderManager;
        if (updateNeeded) {
            updateTransform();
            updateParents();
            updateWorldBoundingBox();
            updateLocalBoundingBox();
            updateNeeded = false;
        }

        for (int i = 0; i < controls.size(); i++) {
            controls.get(i).update();
        }
    }

    public void updateTransform() {
        Vector3f worldTrans = getUpdatedWorldTranslation();
        Quaternion worldRot = getUpdatedWorldRotation();
        Vector3f worldScale = getUpdatedWorldScale();

        worldTransform.setTranslation(worldTrans);

        worldTransform.setRotation(worldRot);
        worldTransform.setScale(worldScale);
        worldMatrix = worldTransform.getMatrix();
    }

    public void updateParents() {
        attachedToRoot = calcAttachedToRoot();
    }

    public void setWorldTransformPhysics(Vector3f trans, Quaternion rot, Vector3f scl) {
        worldTransform.setTranslation(trans);
        worldTransform.setRotation(rot);
        worldTransform.setScale(scl);
        worldMatrix = worldTransform.getMatrix();
        updateWorldBoundingBox();
        updateLocalBoundingBox();
    }

    public boolean getAttachedToRoot() {
        return attachedToRoot;
    }

    public abstract GameObject clone();

    protected boolean calcAttachedToRoot() {
        if (name.equals("root"))
            return true;

        if (parent == null)
            return false;
        else {
            Node par = parent;
            while (!par.getName().equals("root")) {
                Node pp = par.getParent();
                if (pp != null) {
                    par = pp;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

}
