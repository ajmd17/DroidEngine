package ApexEngine.Math;

public class Transform {
    protected Vector3f translation;
    protected ApexEngine.Math.Quaternion rotation;
    protected Vector3f scale;
    protected Matrix4f matrix = new Matrix4f(), transMat = new Matrix4f(), rotMat = new Matrix4f(), scaleMat = new Matrix4f(), rotScale = new Matrix4f();
    private boolean matrixUpdateNeeded = false;

    public Transform() {
        translation = new Vector3f(0, 0, 0);
        rotation = new ApexEngine.Math.Quaternion(0, 0, 0, 1);
        scale = new Vector3f(1, 1, 1);
    }

    public Transform(Transform other) {
        this.translation = other.translation;
        this.rotation = other.rotation;
        this.scale = other.scale;
    }

    protected void updateMatrix() {
        transMat.setToTranslation(translation);
        rotMat.setToRotation(rotation);
        scaleMat.setToScaling(scale);
        rotScale.set(rotMat);
        rotScale.multiplyStore(scaleMat);
        rotScale.multiplyStore(transMat);
        this.matrix = rotScale;
    }

    public Matrix4f getMatrix() {
        if (matrixUpdateNeeded) {
            updateMatrix();
            matrixUpdateNeeded = false;
        }

        return matrix;
    }

    public void setTranslation(Vector3f v) {
        translation.set(v);
        matrixUpdateNeeded = true;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setRotation(ApexEngine.Math.Quaternion q) {
        rotation.set(q);
        matrixUpdateNeeded = true;
    }

    public ApexEngine.Math.Quaternion getRotation() {
        return rotation;
    }

    public void setScale(Vector3f v) {
        scale.set(v);
        matrixUpdateNeeded = true;
    }

    public Vector3f getScale() {
        return scale;
    }

    public boolean equals(Object obj) {
        try {
            if (!(obj instanceof Transform))
                return false;

            Transform tobj = (Transform) obj;
            if (!tobj.getTranslation().equals(translation))
                return false;

            if (!tobj.getRotation().equals(rotation))
                return false;

            if (!tobj.getScale().equals(scale))
                return false;

            return true;
        } catch (RuntimeException __dummyCatchVar0) {
            throw __dummyCatchVar0;
        } catch (Exception __dummyCatchVar0) {
            throw new RuntimeException(__dummyCatchVar0);
        }

    }

}


