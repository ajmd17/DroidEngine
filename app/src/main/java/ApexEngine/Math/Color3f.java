package ApexEngine.Math;

public class Color3f extends Vector3f {
    public Color3f() {
        super();
    }

    public Color3f(float r, float g, float b) {
        super(r, g, b);
    }

    public Color3f(float rgb) {
        super(rgb);
    }

    public Color3f(Color3f other) {
        super(other);
    }

    public Color3f(Color4f other) {
        super(other);
    }

    public Color3f(Vector3f other) {
        super(other);
    }

    public Color3f(Vector4f other) {
        super(other);
    }

    public float getR() {
        return x;
    }

    public void setR(float value) {
        x = value;
    }

    public float getG() {
        return y;
    }

    public void setG(float value) {
        y = value;
    }

    public float getB() {
        return z;
    }

    public void setB(float value) {
        z = value;
    }

}


