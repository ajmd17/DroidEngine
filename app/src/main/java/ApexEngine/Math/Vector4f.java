package ApexEngine.Math;

public class Vector4f {
    public static final Vector4f UnitXW = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
    public static final Vector4f UnitYW = new Vector4f(0.0f, 1.0f, 0.0f, 1.0f);
    public static final Vector4f UnitZW = new Vector4f(0.0f, 0.0f, 1.0f, 1.0f);
    public static final Vector4f UnitX = new Vector4f(1.0f, 0.0f, 0.0f, 0.0f);
    public static final Vector4f UnitY = new Vector4f(0.0f, 1.0f, 0.0f, 0.0f);
    public static final Vector4f UnitZ = new Vector4f(0.0f, 0.0f, 1.0f, 0.0f);
    public static final Vector4f UnitW = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
    public static final Vector4f One = new Vector4f(1.0f);
    public static final Vector4f Zero = new Vector4f(0.0f);
    public float x, y, z, w;

    public Vector4f() {
        set(0.0f);
    }

    public Vector4f(Vector4f other) {
        set(other);
    }

    public Vector4f(float x, float y, float z, float w) {
        set(x, y, z, w);
    }

    public Vector4f(float xyzw) {
        set(xyzw);
    }

    public Vector4f(Vector3f other) {
        set(other);
    }

    public Vector4f set(Vector4f other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.w = other.w;
        return this;
    }

    public Vector4f set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Vector4f set(float xyzw) {
        this.x = xyzw;
        this.y = xyzw;
        this.z = xyzw;
        this.w = xyzw;
        return this;
    }

    public Vector4f set(Vector3f other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.w = 1.0f;
        return this;
    }

    public Vector4f add(Vector4f other) {
        Vector4f res = new Vector4f();
        res.x = this.x + other.x;
        res.y = this.y + other.y;
        res.z = this.z + other.z;
        res.w = this.w + other.w;
        return res;
    }

    public Vector4f addStore(Vector4f other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        this.w += other.w;
        return this;
    }

    public Vector4f subtract(Vector4f other) {
        Vector4f res = new Vector4f();
        res.x = this.x - other.x;
        res.y = this.y - other.y;
        res.z = this.z - other.z;
        res.w = this.w - other.w;
        return res;
    }

    public Vector4f subtractStore(Vector4f other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        this.w -= other.w;
        return this;
    }

    public Vector4f multiply(Vector4f other) {
        Vector4f res = new Vector4f();
        res.x = this.x * other.x;
        res.y = this.y * other.y;
        res.z = this.z * other.z;
        res.w = this.w * other.w;
        return res;
    }

    public Vector4f multiplyStore(Vector4f other) {
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
        this.w *= other.w;
        return this;
    }

    public Vector4f multiply(float scalar) {
        Vector4f res = new Vector4f();
        res.x = this.x * scalar;
        res.y = this.y * scalar;
        res.z = this.z * scalar;
        res.w = this.w * scalar;
        return res;
    }

    public Vector4f multiplyStore(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        this.w *= scalar;
        return this;
    }

    public Vector4f divide(Vector4f other) {
        Vector4f res = new Vector4f();
        res.x = this.x / other.x;
        res.y = this.y / other.y;
        res.z = this.z / other.z;
        res.w = this.w / other.w;
        return res;
    }

    public Vector4f divideStore(Vector4f other) {
        this.x /= other.x;
        this.y /= other.y;
        this.z /= other.z;
        this.w /= other.w;
        return this;
    }

    public Vector4f divide(float scalar) {
        Vector4f res = new Vector4f();
        res.x = this.x / scalar;
        res.y = this.y / scalar;
        res.z = this.z / scalar;
        res.w = this.w / scalar;
        return res;
    }

    public Vector4f divideStore(float scalar) {
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;
        this.w /= scalar;
        return this;
    }

    public Vector4f negate() {
        return multiply(-1f);
    }

    public Vector4f negateStore() {
        return multiplyStore(-1f);
    }

    public Vector4f normalize() {
        Vector4f res = new Vector4f(this);
        float len = length();
        float len2 = len * len;
        if (len2 == 0 || len2 == 1) {
            return res;
        }

        res.multiplyStore(1.0f / (float) Math.sqrt(len2));
        return res;
    }

    public Vector4f normalizeStore() {
        float len = length();
        float len2 = len * len;
        if (len2 == 0 || len2 == 1) {
            return this;
        }

        return multiplyStore(1.0f / (float) Math.sqrt(len2));
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public float dot(Vector4f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
    }

    public float distanceSqr(Vector4f other) {
        double dx = x - other.x;
        double dy = y - other.y;
        double dz = z - other.z;
        double dw = w - other.w;
        return (float) (dx * dx + dy * dy + dz * dz + dw * dw);
    }

    public float distance(Vector4f other) {
        return (float) Math.sqrt(distanceSqr(other));
    }

    public Vector3f toVector3f() {
        return new Vector3f(this);
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

    public float getZ() {
        return z;
    }

    public void setZ(float value) {
        z = value;
    }

    public float getW() {
        return w;
    }

    public void setW(float value) {
        w = value;
    }

    public boolean equals(Object obj) {
        try {
            if (!(obj instanceof Vector4f))
                return false;

            Vector4f vobj = (Vector4f) obj;
            if (vobj.x == x && vobj.y == y && vobj.z == z && vobj.w == w)
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
            return "[" + x + ", " + y + ", " + z + ", " + w + "]";
        } catch (RuntimeException __dummyCatchVar2) {
            throw __dummyCatchVar2;
        } catch (Exception __dummyCatchVar2) {
            throw new RuntimeException(__dummyCatchVar2);
        }

    }

}
