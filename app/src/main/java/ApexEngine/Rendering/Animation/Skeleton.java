//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:48 PM
//

package ApexEngine.Rendering.Animation;

import java.util.ArrayList;

import ApexEngine.Rendering.Animation.Animation;
import ApexEngine.Rendering.Animation.Bone;

public class Skeleton   
{
    protected ArrayList<Bone> bones = new ArrayList<Bone>();
    protected ArrayList<Animation> animations = new ArrayList<Animation>();
    public void addAnimation(Animation anim) {
        animations.add(anim);
    }

    public ArrayList<Animation> getAnimations() {
        return animations;
    }

    public Animation getAnimation(String name) {
        for (int i = 0;i < animations.size();i++)
        {
            if (animations.get(i).getName().equals(name))
            {
                return animations.get(i);
            }
             
        }
        return null;
    }

    public Animation getAnimation(int i) {
        return animations.get(i);
    }

    public ArrayList<Bone> getBones() {
        return bones;
    }

    public int getNumBones() {
        return bones.size();
    }

    public void addBone(Bone bone) {
        bones.add(bone);
    }

    public Bone getBone(String name) {
        for (int i = 0;i < bones.size();i++)
        {
            if (bones.get(i).getName().equals(name))
            {
                return bones.get(i);
            }
             
        }
        return null;
    }

    public Bone getBone(int i) {
        return bones.get(i);
    }

}


