//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:48 PM
//

package ApexEngine.Rendering.Animation;

import ApexEngine.Math.MathUtil;
import ApexEngine.Math.Quaternion;
import ApexEngine.Math.Vector3f;

public class Keyframe {
    private float time;
    private Vector3f translation = new Vector3f(Vector3f.Zero);
    private Quaternion rotation = new Quaternion();
    private Quaternion tmpRot = new Quaternion();
    private Vector3f tmpVec = new Vector3f();

    public Keyframe(float time, Vector3f translation, Quaternion rotation) {
        this.time = time;
        this.translation = translation;
        this.rotation = rotation;
    }

    public Keyframe(float time, Vector3f translation, Vector3f axis, float angleRad) {
        this.time = time;
        this.translation = translation;
        this.rotation = new Quaternion().setFromAxisRadNorm(axis.multiply(-1), angleRad);
    }

    public Quaternion getRotation() {
        return rotation;
    }

    public void setRotation(Quaternion q) {
        this.rotation = q;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float t) {
        this.time = t;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3f v) {
        this.translation = v;
    }

    public Keyframe blend(Keyframe to, float amt) {
        tmpVec.set(getTranslation());
        tmpVec.lerpStore(to.getTranslation(), amt);
        tmpRot.set(getRotation());
        tmpRot.slerpStore(to.getRotation(), amt);
        Keyframe c = new Keyframe(MathUtil.lerp(getTime(), to.getTime(), amt), tmpVec, tmpRot);
        return c;
    }

}


