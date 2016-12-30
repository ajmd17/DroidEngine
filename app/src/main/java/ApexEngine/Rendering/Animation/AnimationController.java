//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:48 PM
//

package ApexEngine.Rendering.Animation;

import ApexEngine.Scene.Components.Controller;

public class AnimationController extends Controller {
    public enum PlayState {
        Playing,
        Stopped,
        Paused
    }

    public enum LoopMode {
        Loop,
        PlayOnce
    }

    private float speed = 1.0f;
    private float time = 0f;
    private Animation currentAnim;
    private Animation lastAnim;
    private PlayState playState = PlayState.Stopped;
    private LoopMode loopMode = LoopMode.Loop;
    private Skeleton skeleton = null;
    private float blend = 0.0f;

    public AnimationController(Skeleton skeleton) {
        this.skeleton = skeleton;
    }

    public Skeleton getSkeleton() {
        return this.skeleton;
    }

    public void setLoopMode(LoopMode loopMode) {
        this.loopMode = loopMode;
    }

    public LoopMode getLoopMode() {
        return loopMode;
    }

    public void setPlayState(PlayState playState) {
        this.playState = playState;
        if (playState == PlayState.Stopped) {
            resetAnimation();
            clearPose();
        }

    }

    public PlayState getPlayState() {
        return this.playState;
    }

    public void play() {
        setPlayState(PlayState.Playing);
    }

    public void pause() {
        setPlayState(PlayState.Paused);
    }

    public void stop() {
        setPlayState(PlayState.Stopped);
    }

    public Animation getCurrentAnimation() {
        return this.currentAnim;
    }

    public void playAnimation(String name, float speed) {
        if (currentAnim == null || !currentAnim.getName().equals(name) || playState != PlayState.Playing || this.speed != speed) {
            resetAnimation();
            setAnimation(skeleton.getAnimations().indexOf(skeleton.getAnimation(name)));
            setSpeed(speed);
            setPlayState(PlayState.Playing);
        }

    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void playAnimation(int i, float speed) {
        if (i != -1) {
            Animation anim = skeleton.getAnimation(i);
            playAnimation(anim.getName(), speed);
        } else {
            setAnimation(-1);
        }
    }

    public void playAnimation(int i) {
        playAnimation(i, 1.0f);
    }

    public void playAnimation(String name) {
        playAnimation(name, 1.0f);
    }

    public void setAnimation(int index) {
        if (currentAnim != null) {
            if (lastAnim == null)
                lastAnim = currentAnim;
            else {
                if (lastAnim != currentAnim)
                    lastAnim = currentAnim;

            }
        }

        if (index != -1) {
            currentAnim = skeleton.getAnimation(index);
            resetAnimation();
        } else {
            clearPose();
        }
    }

    public void applyAnimation() {
        if (lastAnim != null)
            this.currentAnim.applyBlend(time, lastAnim, 1.0f - blend);
        else
            this.currentAnim.apply(time);
    }

    private void resetAnimation() {
        time = 0f;
        blend = 0.0f;
        if (currentAnim != null) {
            applyAnimation();
        }

    }

    public void clearPose() {
        for (int i = 0; i < skeleton.getNumBones(); i++)
            skeleton.getBone(i).clearPose();
    }

    public void init() {
    }

    public void update() {
        if (playState == PlayState.Playing) {
            if (currentAnim != null) {
                if (blend < 1.0f)
                    blend += 0.1f;
                 
                /* GameTime.GetDeltaTime() */
                /* GameTime.GetDeltaTime() */
                time += 0.01f * speed;
                if (time > currentAnim.getTrackLength()) {
                    time = 0f;
                    if (loopMode == LoopMode.PlayOnce) {
                        stop();
                    }

                }

                // if (handler != null)
                //   handler.OnAnimDone(currentAnim.GetName());
                // if (handler != null)
                //    handler.OnAnimLoop(currentAnim.GetName());
                applyAnimation();
            } else {
                setPlayState(PlayState.Stopped);
            }
        }

    }

}


