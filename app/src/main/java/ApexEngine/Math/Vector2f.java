package ApexEngine.Math;

public class Vector2f {
    public static final Vector2f UnitX = new Vector2f(1.0f, 0.0f);
    public static final Vector2f UnitY = new Vector2f(0.0f, 1.0f);
    public static final Vector2f One = new Vector2f(1.0f, 1.0f);
    public static final Vector2f Zero = new Vector2f(0.0f, 0.0f);
    public float x, y;

    public Vector2f() {
        set(0.0f);
    }

    public Vector2f(Vector2f other) {
        set(other);
    }

    public Vector2f(float x, float y) {
        set(x, y);
    }

    public Vector2f(float xy) {
        set(xy);
    }

    public Vector2f set(Vector2f other) {
        this.x = other.x;
        this.y = other.y;
        return this;
    }

    public Vector2f set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2f set(float xy) {
        this.x = xy;
        this.y = xy;
        return this;
    }

    public Vector2f add(Vector2f other) {
        Vector2f res = new Vector2f();
        res.x = this.x + other.x;
        res.y = this.y + other.y;
        return res;
    }

    public Vector2f addStore(Vector2f other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2f subtract(Vector2f other) {
        Vector2f res = new Vector2f();
        res.x = this.x - other.x;
        res.y = this.y - other.y;
        return res;
    }

    public Vector2f subtractStore(Vector2f other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vector2f multiply(Vector2f other) {
        Vector2f res = new Vector2f();
        res.x = this.x * other.x;
        res.y = this.y * other.y;
        return res;
    }

    public Vector2f multiplyStore(Vector2f other) {
        this.x *= other.x;
        this.y *= other.y;
        return this;
    }

    public Vector2f multiply(float scalar) {
        Vector2f res = new Vector2f();
        res.x = this.x * scalar;
        res.y = this.y * scalar;
        return res;
    }

    public Vector2f multiplyStore(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector2f divide(Vector2f other) {
        Vector2f res = new Vector2f();
        res.x = this.x / other.x;
        res.y = this.y / other.y;
        return res;
    }

    public Vector2f divideStore(Vector2f other) {
        this.x /= other.x;
        this.y /= other.y;
        return this;
    }

    public Vector2f divide(float scalar) {
        Vector2f res = new Vector2f();
        res.x = this.x / scalar;
        res.y = this.y / scalar;
        return res;
    }

    public Vector2f divideStore(float scalar) {
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }

    public Vector2f negate() {
        return multiply(-1f);
    }

    public Vector2f negateStore() {
        return multiplyStore(-1f);
    }

    public Vector2f normalize() {
        Vector2f res = new Vector2f(this);
        float len = length();
        float len2 = len * len;
        if (len2 == 0 || len2 == 1) {
            return res;
        }

        res.multiplyStore(1.0f / (float) Math.sqrt(len2));
        return res;
    }

    public Vector2f normalizeStore() {
        float len = length();
        float len2 = len * len;
        if (len2 == 0 || len2 == 1) {
            return this;
        }

        return multiplyStore(1.0f / (float) Math.sqrt(len2));
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float dot(Vector2f other) {
        return this.x * other.x + this.y * other.y;
    }

    public float distanceSqr(Vector2f other) {
        double dx = x - other.x;
        double dy = y - other.y;
        return (float) (dx * dx + dy * dy);
    }

    public float distance(Vector2f other) {
        return (float) Math.sqrt(distanceSqr(other));
    }

    public float getX() {
        return x;
    }

    public void setX(float value) {
        x = value;
    }

    public float getY() {
        return y;
    }

    public void setY(float value) {
        y = value;
    }

    public boolean equals(Object obj) {
        try {
            if (!(obj instanceof Vector2f))
                return false;

            Vector2f vobj = (Vector2f) obj;
            if (vobj.x == x && vobj.y == y)
                return true;

            return false;
        } catch (RuntimeException __dummyCatchVar1) {
            throw __dummyCatchVar1;
        } catch (Exception __dummyCatchVar1) {
            throw new RuntimeException(__dummyCatchVar1);
        }

    }

    public String toString() {
        try {
            return "[" + x + ", " + y + "]";
        } catch (RuntimeException __dummyCatchVar2) {
            throw __dummyCatchVar2;
        } catch (Exception __dummyCatchVar2) {
            throw new RuntimeException(__dummyCatchVar2);
        }

    }

}


