//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:48 PM
//

package ApexEngine.Rendering.Animation;

import java.util.ArrayList;

import ApexEngine.Math.Quaternion;
import ApexEngine.Math.Vector3f;
import ApexEngine.Rendering.Animation.Bone;
import ApexEngine.Rendering.Animation.Keyframe;

public class AnimationTrack   
{
    private Bone bone;
    public ArrayList<Keyframe> frames = new ArrayList<Keyframe>();
    private Vector3f tmpVec = new Vector3f();
    private Quaternion tmpRot = new Quaternion();
    private Keyframe tmpFrame = new Keyframe(0,new Vector3f(),new Quaternion());
    public AnimationTrack(Bone bone) {
        setBone(bone);
    }

    public void setBone(Bone bone) {
        this.bone = bone;
    }

    public Bone getBone() {
        return bone;
    }

    public float getTrackLength() {
        return frames.get(frames.size() - 1).getTime();
    }

    public void addKeyframe(Keyframe frame) {
        frames.add(frame);
    }

    public Keyframe getPoseAt(float time) {
        int first = 0, second = -1;
        Keyframe currentKeyframe = null;
        Keyframe nextKeyframe = null;
        int n = frames.size() - 1;
        for (int i = 0;i < n;i++)
        {
            if (time >= frames.get(i).getTime() && time <= frames.get(i + 1).getTime())
            {
                first = i;
                second = i + 1;
            }
             
        }
        currentKeyframe = frames.get(first);
        tmpVec.set(currentKeyframe.getTranslation());
        tmpRot.set(currentKeyframe.getRotation());
        if (second > first)
        {
            nextKeyframe = frames.get(second);
            float fraction = (time - currentKeyframe.getTime()) / (nextKeyframe.getTime() - currentKeyframe.getTime());
            tmpVec.lerpStore(nextKeyframe.getTranslation(),fraction);
            Quaternion nextrot = nextKeyframe.getRotation();
            tmpRot.slerpStore(nextrot,fraction);
        }
         
        //   Keyframe res = new Keyframe(time, tmpVec, tmpRot);
        tmpFrame.setTime(time);
        tmpFrame.setTranslation(tmpVec);
        tmpFrame.setRotation(tmpRot);
        return tmpFrame;
    }

}


