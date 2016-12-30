//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:48 PM
//

package ApexEngine.Rendering.Animation;

import java.util.ArrayList;

import ApexEngine.Math.MathUtil;

public class Animation {
    protected String name;
    protected ArrayList<AnimationTrack> tracks = new ArrayList<AnimationTrack>();

    public Animation(String name) {
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<AnimationTrack> getTracks() {
        return tracks;
    }

    public AnimationTrack getTrack(int i) {
        return tracks.get(i);
    }

    public void addTrack(AnimationTrack track) {
        tracks.add(track);
    }

    public float getTrackLength() {
        return tracks.get(tracks.size() - 1).getTrackLength();
    }

    public void apply(float time) {
        for (int i = 0; i < tracks.size(); i++) {
            AnimationTrack track = tracks.get(i);
            track.getBone().clearPose();
            track.getBone().applyPose(track.getPoseAt(time));
        }
    }

    public void applyBlend(float time, Animation toBlend, float blendAmt) {
        for (int i = 0; i < tracks.size(); i++) {
            AnimationTrack track = tracks.get(i);
            if (blendAmt <= 0.001f) {
                track.getBone().clearPose();
            }

            if (track.getBone().getCurrentPose() != null)
                track.getBone().applyPose(track.getBone().getCurrentPose().blend(track.getPoseAt(time), MathUtil.clamp(blendAmt, 0f, 1f)));
            else
                track.getBone().applyPose(track.getPoseAt(time));
        }
    }

}


