package ApexEngine.Math;

public class Color4f extends Vector4f {
    public Color4f() {
        super();
    }

    public Color4f(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    public Color4f(float rgba) {
        super(rgba);
    }

    public Color4f(Color4f other) {
        super(other);
    }

    public Color4f(Vector3f other) {
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

    public float getA() {
        return w;
    }

    public void setA(float value) {
        w = value;
    }

}


