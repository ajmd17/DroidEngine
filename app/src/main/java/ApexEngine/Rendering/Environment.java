package ApexEngine.Rendering;

import java.util.ArrayList;

import ApexEngine.Math.Matrix4f;
import ApexEngine.Math.Vector4f;
import ApexEngine.Rendering.Light.AmbientLight;
import ApexEngine.Rendering.Light.DirectionalLight;
import ApexEngine.Rendering.Light.PointLight;

public class Environment {
    private DirectionalLight directionalLight = new DirectionalLight();
    private AmbientLight ambientLight = new AmbientLight();
    private ArrayList<PointLight> pointLights = new ArrayList<PointLight>();
    private Texture[] shadowMaps = new Texture[4];
    private Matrix4f[] shadowMatrices = new Matrix4f[4];
    private float[] shadowMapSplits = new float[4];
    private boolean shadowsEnabled = false;
    private float fogStart = 30f;
    private float fogEnd = 70;
    private Vector4f fogColor = new Vector4f(0.3f, 0.3f, 0.3f, 1.0f);
    private float elapsedTime = 0f;
    private float timePerFrame = 0f;

    public float getTimePerFrame() {
        return timePerFrame;
    }

    public void setTimePerFrame(float timePerFrame) {
        this.timePerFrame = timePerFrame;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(float value) {
        elapsedTime = value;
    }

    public float getFogStart() {
        return fogStart;
    }

    public void setFogStart(float value) {
        fogStart = value;
    }

    public float getFogEnd() {
        return fogEnd;
    }

    public void setFogEnd(float value) {
        fogEnd = value;
    }

    public Vector4f getFogColor() {
        return fogColor;
    }

    public void setFogColor(Vector4f value) {
        fogColor.set(value);
    }

    public ArrayList<PointLight> getPointLights() {
        return pointLights;
    }

    public DirectionalLight getDirectionalLight() {
        return directionalLight;
    }

    public void setDirectionalLight(DirectionalLight value) {
        directionalLight = value;
    }

    public boolean getShadowsEnabled() {
        return shadowsEnabled;
    }

    public void setShadowsEnabled(boolean value) {
        shadowsEnabled = value;
    }

    public Texture[] getShadowMaps() {
        return shadowMaps;
    }

    public void setShadowMaps(Texture[] value) {
        shadowMaps = value;
    }

    public Matrix4f[] getShadowMatrices() {
        return shadowMatrices;
    }

    public void setShadowMatrices(Matrix4f[] value) {
        shadowMatrices = value;
    }

    public float[] getShadowMapSplits() {
        return shadowMapSplits;
    }

    public void setShadowMapSplits(float[] value) {
        shadowMapSplits = value;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(AmbientLight value) {
        ambientLight = value;
    }

}
