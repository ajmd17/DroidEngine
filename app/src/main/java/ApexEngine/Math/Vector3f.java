package ApexEngine.Math;

public class Vector3f {
    public static final Vector3f Zero = new Vector3f(0.0f, 0.0f, 0.0f);
    public static final Vector3f UnitX = new Vector3f(1.0f, 0.0f, 0.0f);
    public static final Vector3f UnitY = new Vector3f(0.0f, 1.0f, 0.0f);
    public static final Vector3f UnitZ = new Vector3f(0.0f, 0.0f, 1.0f);
    public static final Vector3f One = new Vector3f(1.0f, 1.0f, 1.0f);
    public static final Vector3f NaN = new Vector3f(Float.NaN);
    public float x, y, z;
    private static Matrix4f tmpRot = new Matrix4f();

    public Vector3f() {
        set(0.0f);
    }

    public Vector3f(Vector3f other) {
        set(other);
    }

    public Vector3f(float x, float y, float z) {
        set(x, y, z);
    }

    public Vector3f(float xyz) {
        set(xyz);
    }

    public Vector3f(Vector4f other) {
        set(other);
    }

    public Vector3f set(Vector3f other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        return this;
    }

    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3f set(float xyz) {
        this.x = xyz;
        this.y = xyz;
        this.z = xyz;
        return this;
    }

    public Vector3f set(Vector4f other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        return this;
    }

    public Vector3f add(Vector3f other) {
        Vector3f res = new Vector3f();
        res.x = this.x + other.x;
        res.y = this.y + other.y;
        res.z = this.z + other.z;
        return res;
    }

    public Vector3f addStore(Vector3f other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        return this;
    }

    public Vector3f subtract(Vector3f other) {
        Vector3f res = new Vector3f();
        res.x = this.x - other.x;
        res.y = this.y - other.y;
        res.z = this.z - other.z;
        return res;
    }

    public Vector3f subtractStore(Vector3f other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        return this;
    }

    public Vector3f multiply(Vector3f other) {
        Vector3f res = new Vector3f();
        res.x = this.x * other.x;
        res.y = this.y * other.y;
        res.z = this.z * other.z;
        return res;
    }

    public Vector3f multiplyStore(Vector3f other) {
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
        return this;
    }

    public Vector3f multiply(float scalar) {
        Vector3f res = new Vector3f();
        res.x = this.x * scalar;
        res.y = this.y * scalar;
        res.z = this.z * scalar;
        return res;
    }

    public Vector3f multiplyStore(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    public Vector3f multiply(Matrix4f mat) {
        Vector3f res = new Vector3f();
        res.set(x * mat.values[Matrix4f.m00] + y * mat.values[Matrix4f.m01] + z * mat.values[Matrix4f.m02] + mat.values[Matrix4f.m03], x * mat.values[Matrix4f.m10] + y * mat.values[Matrix4f.m11] + z * mat.values[Matrix4f.m12] + mat.values[Matrix4f.m13], x * mat.values[Matrix4f.m20] + y * mat.values[Matrix4f.m21] + z * mat.values[Matrix4f.m22] + mat.values[Matrix4f.m23]);
        return res;
    }

    public Vector3f multiplyStore(Matrix4f mat) {
        return set(x * mat.values[Matrix4f.m00] + y * mat.values[Matrix4f.m01] + z * mat.values[Matrix4f.m02] + mat.values[Matrix4f.m03], x * mat.values[Matrix4f.m10] + y * mat.values[Matrix4f.m11] + z * mat.values[Matrix4f.m12] + mat.values[Matrix4f.m13], x * mat.values[Matrix4f.m20] + y * mat.values[Matrix4f.m21] + z * mat.values[Matrix4f.m22] + mat.values[Matrix4f.m23]);
    }

    public Vector3f divide(Vector3f other) {
        Vector3f res = new Vector3f();
        res.x = this.x / other.x;
        res.y = this.y / other.y;
        res.z = this.z / other.z;
        return res;
    }

    public Vector3f divideStore(Vector3f other) {
        this.x /= other.x;
        this.y /= other.y;
        this.z /= other.z;
        return this;
    }

    public Vector3f divide(float scalar) {
        Vector3f res = new Vector3f();
        res.x = this.x / scalar;
        res.y = this.y / scalar;
        res.z = this.z / scalar;
        return res;
    }

    public Vector3f divideStore(float scalar) {
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;
        return this;
    }

    public Vector3f negate() {
        return multiply(-1f);
    }

    public Vector3f negateStore() {
        return multiplyStore(-1f);
    }

    public Vector3f lerp(Vector3f to, float amt) {
        return add((to.subtract(this)).multiply(amt));
    }

    public Vector3f lerpStore(Vector3f to, float amt) {
        addStore((to.subtract(this)).multiply(amt));
        return this;
    }

    public Vector3f cross(Vector3f other) {
        Vector3f res = new Vector3f(this);
        float x1 = (res.y * other.z) - (res.z * other.y);
        float y1 = (res.z * other.x) - (res.x * other.z);
        float z1 = (res.x * other.y) - (res.y * other.x);
        res.set(x1, y1, z1);
        return res;
    }

    public Vector3f crossStore(Vector3f other) {
        float x1 = (this.y * other.z) - (this.z * other.y);
        float y1 = (this.z * other.x) - (this.x * other.z);
        float z1 = (this.x * other.y) - (this.y * other.x);
        return set(x1, y1, z1);
    }

    public Vector3f rotate(Vector3f axis, float angle) {
        Vector3f res = new Vector3f(this);
        tmpRot.setToRotation(axis, angle);
        res.multiplyStore(tmpRot);
        return res;
    }

    public Vector3f rotateStore(Vector3f axis, float angle) {
        tmpRot.setToRotation(axis, angle);
        return multiplyStore(tmpRot);
    }

    public Vector3f normalize() {
        Vector3f res = new Vector3f(this);
        float len = length();
        float len2 = len * len;
        if (len2 == 0 || len2 == 1) {
            return res;
        }

        res.multiplyStore(1.0f / (float) Math.sqrt(len2));
        return res;
    }

    public Vector3f normalizeStore() {
        float len = length();
        float len2 = len * len;
        if (len2 == 0 || len2 == 1) {
            return this;
        }

        return multiplyStore(1.0f / (float) Math.sqrt(len2));
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public float dot(Vector3f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public float distanceSqr(Vector3f other) {
        double dx = x - other.x;
        double dy = y - other.y;
        double dz = z - other.z;
        return (float) (dx * dx + dy * dy + dz * dz);
    }

    public float distance(Vector3f other) {
        return (float) Math.sqrt(distanceSqr(other));
    }

    public Vector4f toVector4f() {
        return new Vector4f(this);
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

    public boolean equals(Object obj) {
        try {
            if (!(obj instanceof Vector3f))
                return false;

            Vector3f vobj = (Vector3f) obj;
            if (vobj.x == x && vobj.y == y && vobj.z == z)
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
            return "[" + x + ", " + y + ", " + z + "]";
        } catch (RuntimeException __dummyCatchVar2) {
            throw __dummyCatchVar2;
        } catch (Exception __dummyCatchVar2) {
            throw new RuntimeException(__dummyCatchVar2);
        }

    }

}


