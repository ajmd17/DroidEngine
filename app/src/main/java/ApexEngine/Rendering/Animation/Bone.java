//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:48 PM
//

package ApexEngine.Rendering.Animation;

import ApexEngine.Math.Matrix4f;
import ApexEngine.Math.Quaternion;
import ApexEngine.Math.Vector3f;
import ApexEngine.Rendering.RenderManager;
import ApexEngine.Scene.Node;

public class Bone extends Node {
    private Matrix4f tmpMatrix = new Matrix4f();
    protected Bone parentBone = null;
    private Vector3f modelPos = new Vector3f();
    private Vector3f axis = new Vector3f();
    private float angle = 0f;
    private Vector3f localTrans = new Vector3f();
    private Keyframe currentPose;
    protected Quaternion modelRot = new Quaternion();
    protected Matrix4f mat = new Matrix4f();
    private Quaternion bindRot = new Quaternion();
    protected Matrix4f boneMatrix = new Matrix4f();
    private Vector3f bindTrans = new Vector3f();
    private Vector3f bindAxis = new Vector3f();
    private float bindAngle = 0f;
    private Quaternion invBindRot = new Quaternion();
    private Vector3f invBindPos = new Vector3f();
    private Vector3f tmpMpos = new Vector3f();
    private Matrix4f rotMatrix = new Matrix4f();
    private Quaternion poseRot = new Quaternion();
    private Quaternion tmpRot = new Quaternion();

    public Keyframe getCurrentPose() {
        return currentPose;
    }

    public Bone(String name) {
        this.name = name;
    }

    public void setParent(Bone parentBone) {
        this.parentBone = parentBone;
    }

    public Quaternion getPoseRotation() {
        return poseRot;
    }

    public Quaternion getInverseBindRotation() {
        return invBindRot;
    }

    public Vector3f getInverseBindPosition() {
        return invBindPos;
    }

    public Vector3f getBindTranslation() {
        return bindTrans;
    }

    public void setBindTranslation(Vector3f bindTrans) {
        this.bindTrans = bindTrans;
    }

    public Quaternion getBindRotation() {
        return bindRot;
    }

    public void setBindRotation(Quaternion bindRot) {
        this.bindRot = bindRot;
    }

    public Vector3f getModelTranslation() {
        return modelPos;
    }

    public Quaternion getModelRotation() {
        return modelRot;
    }

    public Quaternion calculateBindingRotation() {
        if (parentBone != null) {
            modelRot = parentBone.modelRot.multiply(getBindRotation());
        } else {
            modelRot = getBindRotation();
        }
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i) instanceof Bone) {
                Bone b = (Bone) children.get(i);
                b.calculateBindingRotation();
            }

        }
        return modelRot;
    }

    private void calculateBindingTranslation(Vector3f outv) {
        if (parentBone != null) {
            outv.set(parentBone.getModelRotation().multiply(getBindTranslation()));
            outv.addStore(parentBone.getModelTranslation());
        } else
            outv.set(getBindTranslation());
    }

    public Vector3f calculateBindingTranslation() {
        Vector3f outv = new Vector3f();
        calculateBindingTranslation(outv);
        modelPos.set(outv);
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i) instanceof Bone) {
                Bone b = (Bone) children.get(i);
                b.calculateBindingTranslation();
            }

        }
        return modelPos;
    }

    public void setBindAxisAngle(Vector3f axis, float angleRad) {
        setBindRotation(new Quaternion().setFromAxisRadNorm(axis, angleRad));
        bindAxis = axis;
        bindAngle = angle;
    }

    public Vector3f getBindAxis() {
        return bindAxis;
    }

    public float getBindAngle() {
        return bindAngle;
    }

    public void setToBindingPose() {
        this.localRotation = new Quaternion();
        this.localTranslation.set(getBindTranslation());
        this.poseRot.set(getBindRotation());
        updateTransform();
    }

    public void storeBindingPose() {
        this.invBindPos = this.modelPos.multiply(-1f);
        this.invBindRot = this.modelRot.inverse();
        this.localRotation = new Quaternion();
        this.localTranslation = new Vector3f();
    }

    public void clearPose() {
        poseRot.setToIdentity();
        updateTransform();
    }

    public void applyPose(Keyframe pose) {
        currentPose = pose;
        setLocalTranslation(pose.getTranslation());
        poseRot = pose.getRotation();
        updateTransform();
    }

    public void update(RenderManager renderManager) {
    }

    // do nothing
    public Matrix4f getBoneMatrix() {
        return boneMatrix;
    }

    public Quaternion getWorldRotation() {
        return modelRot.multiply(localRotation);
    }

    public void setLocalRotation(Quaternion localRot) {
        localRotation.set(localRot);
        updateTransform();
    }

    public void setLocalRotation(Vector3f axis, float deg) {
        localRotation.setFromAxis(axis, deg);
        updateTransform();
    }

    public void updateTransform() {
        tmpMpos.set(modelPos);
        tmpMpos.multiplyStore(-1f);
        rotMatrix.setToTranslation(tmpMpos);
        tmpRot.set(modelRot);
        tmpRot.multiplyStore(poseRot);
        tmpRot.multiplyStore(localRotation);
        tmpRot.multiplyStore(invBindRot);
        tmpMatrix.setToRotation(tmpRot);
        rotMatrix.multiplyStore(tmpMatrix);
        tmpMpos.multiplyStore(-1f);
        tmpMatrix.setToTranslation(tmpMpos);
        rotMatrix.multiplyStore(tmpMatrix);
        tmpMatrix.setToTranslation(localTranslation);
        rotMatrix.multiplyStore(tmpMatrix);
        boneMatrix.set(rotMatrix);
        if (parentBone != null) {
            boneMatrix.multiplyStore(parentBone.boneMatrix);
        }

        for (int i = 0; i < children.size(); i++) {
            if (children.get(i) instanceof Bone) {
                Bone b = (Bone) children.get(i);
                b.updateTransform();
            }

        }
    }

    public void setParent(Node par) {
        super.setParent(par);
        if (par instanceof Bone)
            this.parentBone = (Bone) par;

    }

}


