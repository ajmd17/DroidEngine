package ApexEngine.Math;

public class Quaternion {
    public float x, y, z, w;
    private Vector3f tempZ = new Vector3f();
    private Vector3f tempX = new Vector3f();
    private Vector3f tempY = new Vector3f();

    public Quaternion() {
        set(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public Quaternion(ApexEngine.Math.Quaternion other) {
        set(other);
    }

    public Quaternion(float x, float y, float z, float w) {
        set(x, y, z, w);
    }

    public ApexEngine.Math.Quaternion set(ApexEngine.Math.Quaternion other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.w = other.w;
        return this;
    }

    public ApexEngine.Math.Quaternion set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public ApexEngine.Math.Quaternion multiply(ApexEngine.Math.Quaternion other) {
        ApexEngine.Math.Quaternion res = new ApexEngine.Math.Quaternion();
        float x1 = x * other.w + y * other.z - z * other.y + w * other.x;
        float y1 = -x * other.z + y * other.w + z * other.x + w * other.y;
        float z1 = x * other.y - y * other.x + z * other.w + w * other.z;
        float w1 = -x * other.x - y * other.y - z * other.z + w * other.w;
        res.x = x1;
        res.y = y1;
        res.z = z1;
        res.w = w1;
        return res;
    }

    public ApexEngine.Math.Quaternion multiplyStore(ApexEngine.Math.Quaternion other) {
        float x1 = x * other.w + y * other.z - z * other.y + w * other.x;
        float y1 = -x * other.z + y * other.w + z * other.x + w * other.y;
        float z1 = x * other.y - y * other.x + z * other.w + w * other.z;
        float w1 = -x * other.x - y * other.y - z * other.z + w * other.w;
        this.x = x1;
        this.y = y1;
        this.z = z1;
        this.w = w1;
        return this;
    }

    public Vector3f multiply(Vector3f vec) {
        Vector3f res = new Vector3f();
        float vx = vec.x, vy = vec.y, vz = vec.z;
        res.x = w * w * vx + 2 * y * w * vz - 2 * z * w * vy + x * x * vx + 2 * y * x * vy + 2 * z * x * vz - z * z * vx - y * y * vx;
        res.y = 2 * x * y * vx + y * y * vy + 2 * z * y * vz + 2 * w * z * vx - z * z * vy + w * w * vy - 2 * x * w * vz - x * x * vy;
        res.z = 2 * x * z * vx + 2 * y * z * vy + z * z * vz - 2 * w * y * vx - y * y * vz + 2 * w * x * vy - x * x * vz + w * w * vz;
        return res;
    }

    public Vector3f multiplyStore(Vector3f vec) {
        float tempX, tempY;
        tempX = w * w * vec.x + 2 * y * w * vec.z - 2 * z * w * vec.y + x * x * vec.x + 2 * y * x * vec.y + 2 * z * x * vec.z - z * z * vec.x - y * y * vec.x;
        tempY = 2 * x * y * vec.x + y * y * vec.y + 2 * z * y * vec.z + 2 * w * z * vec.x - z * z * vec.y + w * w * vec.y - 2 * x * w * vec.z - x * x * vec.y;
        vec.z = 2 * x * z * vec.x + 2 * y * z * vec.y + z * z * vec.z - 2 * w * y * vec.x - y * y * vec.z + 2 * w * x * vec.y - x * x * vec.z + w * w * vec.z;
        vec.x = tempX;
        vec.y = tempY;
        return vec;
    }

    public ApexEngine.Math.Quaternion slerp(ApexEngine.Math.Quaternion to, float amt) {
        // quaternion to return
        ApexEngine.Math.Quaternion qm = new ApexEngine.Math.Quaternion();
        // Calculate angle between them.
        float cosHalfTheta = w * to.w + x * to.x + y * to.y + z * to.z;
        // if qa=qb or qa=-qb then theta = 0 and we can return qa
        if ((float) Math.abs(cosHalfTheta) >= 1.0f) {
            qm.w = w;
            qm.x = x;
            qm.y = y;
            qm.z = z;
            return qm;
        }

        // Calculate temporary values.
        float halfTheta = (float) Math.acos(cosHalfTheta);
        float sinHalfTheta = (float) Math.sqrt(1.0 - cosHalfTheta * cosHalfTheta);
        // if theta = 180 degrees then result is not fully defined
        // we could rotate around any axis normal to qa or qb
        if ((float) Math.abs(sinHalfTheta) < 0.001f) {
            // fabs is floating point absolute
            qm.w = (w * 0.5f + to.w * 0.5f);
            qm.x = (x * 0.5f + to.x * 0.5f);
            qm.y = (y * 0.5f + to.y * 0.5f);
            qm.z = (z * 0.5f + to.z * 0.5f);
            return qm;
        }

        float ratioA = (float) Math.sin((1 - amt) * halfTheta) / sinHalfTheta;
        float ratioB = (float) Math.sin(amt * halfTheta) / sinHalfTheta;
        //calculate Quaternion.
        qm.w = (w * ratioA + to.w * ratioB);
        qm.x = (x * ratioA + to.x * ratioB);
        qm.y = (y * ratioA + to.y * ratioB);
        qm.z = (z * ratioA + to.z * ratioB);
        return qm;
    }

    public ApexEngine.Math.Quaternion slerpStore(ApexEngine.Math.Quaternion to, float amt) {
        // Calculate angle between them.
        float cosHalfTheta = w * to.w + x * to.x + y * to.y + z * to.z;
        // if qa=qb or qa=-qb then theta = 0 and we can return qa
        if ((float) Math.abs(cosHalfTheta) >= 1.0f) {
            return this;
        }

        // Calculate temporary values.
        float halfTheta = (float) Math.acos(cosHalfTheta);
        float sinHalfTheta = (float) Math.sqrt(1.0 - cosHalfTheta * cosHalfTheta);
        // if theta = 180 degrees then result is not fully defined
        // we could rotate around any axis normal to qa or qb
        if ((float) Math.abs(sinHalfTheta) < 0.001f) {
            // fabs is floating point absolute
            this.w = (w * 0.5f + to.w * 0.5f);
            this.x = (x * 0.5f + to.x * 0.5f);
            this.y = (y * 0.5f + to.y * 0.5f);
            this.z = (z * 0.5f + to.z * 0.5f);
            return this;
        }

        float ratioA = (float) Math.sin((1 - amt) * halfTheta) / sinHalfTheta;
        float ratioB = (float) Math.sin(amt * halfTheta) / sinHalfTheta;
        //calculate Quaternion.
        this.w = (w * ratioA + to.w * ratioB);
        this.x = (x * ratioA + to.x * ratioB);
        this.y = (y * ratioA + to.y * ratioB);
        this.z = (z * ratioA + to.z * ratioB);
        return this;
    }

    public float normalize() {
        return w * w + x * x + y * y + z * z;
    }

    public ApexEngine.Math.Quaternion inverse() {
        float n = normalize();
        ApexEngine.Math.Quaternion res = new ApexEngine.Math.Quaternion();
        if (n > 0.0) {
            float invN = 1.0f / n;
            res.set(-x * invN, -y * invN, -z * invN, w * invN);
        }

        return res;
    }

    public ApexEngine.Math.Quaternion inverseStore() {
        float n = normalize();
        if (n > 0.0) {
            float invN = 1.0f / n;
            this.x = -x * invN;
            this.y = -y * invN;
            this.z = -z * invN;
            this.w = w * invN;
        }

        return this;
    }

    public int getGimbalPole() {
        float amt = y * x + z * w;
        return amt > 0.499f ? 1 : (amt < -0.499f ? -1 : 0);
    }

    public float getRollRad() {
        int pole = getGimbalPole();
        return pole == 0 ? (float) Math.atan2(2.0f * (w * z + y * x), 1.0f - 2.0f * (x * x + z * z)) : (float) pole * 2.0f * (float) Math.atan2(y, w);
    }

    public float getRoll() {
        return MathUtil.toDegrees(getRollRad());
    }

    public float getPitchRad() {
        int pole = getGimbalPole();
        return pole == 0 ? (float) Math.asin(MathUtil.clamp(2.0f * (w * x - z * y), -1.0f, 1.0f)) : pole * MathUtil.PI * 0.5f;
    }

    public float getPitch() {
        return MathUtil.toDegrees(getPitchRad());
    }

    public float getYawRad() {
        int pole = getGimbalPole();
        return pole == 0 ? (float) Math.atan2(2.0f * (y * w + x * z), 1.0f - 2.0f * (y * y + x * x)) : 0.0f;
    }

    public float getYaw() {
        return MathUtil.toDegrees(getYawRad());
    }

    public ApexEngine.Math.Quaternion setFromAxis(Vector3f axis, float deg) {
        return setFromAxisRad(axis, MathUtil.toRadians(deg));
    }

    public ApexEngine.Math.Quaternion setFromAxisRad(Vector3f axis, float rad) {
        Vector3f newVec = new Vector3f(axis);
        newVec.normalizeStore();
        return setFromAxisRadNorm(newVec, rad);
    }

    public ApexEngine.Math.Quaternion setFromAxisRadNorm(Vector3f axis, float rad) {
        if (axis.x == 0.0f && axis.y == 0.0f && axis.z == 0.0f) {
            set(0.0f, 0.0f, 0.0f, 1.0f);
        } else {
            float halfAngle = rad / 2.0f;
            float sinHalfAngle = (float) Math.sin(halfAngle);
            this.w = (float) Math.cos(halfAngle);
            this.x = sinHalfAngle * axis.x;
            this.y = sinHalfAngle * axis.y;
            this.z = sinHalfAngle * axis.z;
        }
        return this;
    }

    public ApexEngine.Math.Quaternion setFromAxes(float xx, float xy, float xz, float yx, float yy, float yz, float zx, float zy, float zz) {
        float amt = xx + yy + zz;
        if (amt >= 0.0f) {
            float s = (float) Math.sqrt(amt + 1);
            this.w = 0.5f * s;
            this.x = (zy - yz) * (0.5f / s);
            this.y = (xz - zx) * (0.5f / s);
            this.z = (yx - xy) * (0.5f / s);
        } else if ((xx > yy) && (xx > zz)) {
            float s = (float) Math.sqrt(1 + xx - yy - zz);
            this.x = s * 0.5f;
            this.y = (yx + xy) * (0.5f / s);
            this.z = (xz + zx) * (0.5f / s);
            this.w = (zy - yz) * (0.5f / s);
        } else if (yy > zz) {
            float s = (float) Math.sqrt(1 + yy - xx - zz);
            this.y = s * 0.5f;
            this.x = (yx + xy) * (0.5f / s);
            this.z = (zy + yz) * (0.5f / s);
            this.w = (xz - zx) * (0.5f / s);
        } else {
            float s = (float) Math.sqrt(1 + zz - xx - yy);
            this.z = s * 0.5f;
            this.x = (xz + zx) * (0.5f / s);
            this.y = (zy + yz) * (0.5f / s);
            this.w = (yx - xy) * (0.5f / s);
        }
        return this;
    }

    public ApexEngine.Math.Quaternion setFromMatrix(Matrix4f mat) {
        setFromAxes(mat.values[Matrix4f.m00], mat.values[Matrix4f.m10], mat.values[Matrix4f.m20], mat.values[Matrix4f.m01], mat.values[Matrix4f.m11], mat.values[Matrix4f.m21], mat.values[Matrix4f.m02], mat.values[Matrix4f.m12], mat.values[Matrix4f.m22]);
        return this;
    }

    public ApexEngine.Math.Quaternion setToIdentity() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 1.0f;
        return this;
    }

    public ApexEngine.Math.Quaternion setToLookAt(Vector3f dir, Vector3f up) {
        tempZ.set(dir);
        tempZ.normalizeStore();
        tempX.set(up);
        tempX.crossStore(dir);
        tempX.normalizeStore();
        tempY.set(dir);
        tempY.crossStore(tempX);
        tempY.normalizeStore();
        setFromAxes(tempX.x, tempX.y, tempX.z, tempY.x, tempY.y, tempY.z, tempZ.x, tempZ.y, tempZ.z);
        return this;
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
            if (!(obj instanceof ApexEngine.Math.Quaternion))
                return false;

            ApexEngine.Math.Quaternion vobj = (ApexEngine.Math.Quaternion) obj;
            if (vobj.x == x && vobj.y == y && vobj.z == z && vobj.w == w)
                return true;

            return false;
        } catch (RuntimeException __dummyCatchVar0) {
            throw __dummyCatchVar0;
        } catch (Exception __dummyCatchVar0) {
            throw new RuntimeException(__dummyCatchVar0);
        }

    }

    public String toString() {
        try {
            return "[" + x + ", " + y + ", " + z + ", " + w + "]";
        } catch (RuntimeException __dummyCatchVar1) {
            throw __dummyCatchVar1;
        } catch (Exception __dummyCatchVar1) {
            throw new RuntimeException(__dummyCatchVar1);
        }

    }

}


