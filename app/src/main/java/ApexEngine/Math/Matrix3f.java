package ApexEngine.Math;

public class Matrix3f extends Matrix {
    public static final int m00 = 0, m01 = 1, m02 = 2, m03 = 3, m10 = 4, m11 = 5, m12 = 6, m13 = 7, m20 = 8, m21 = 9, m22 = 10, m23 = 11;
    public float[] values = new float[12];

    public Matrix3f() {
    }

    public Matrix3f(Matrix other) {
        super(other);
    }

    public float[] getValues() {
        return values;
    }

    public Matrix3f set(Matrix other) {
        if (other.getValues().length >= getValues().length) {
            for (int i = 0; i < getValues().length; i++) {
                getValues()[i] = other.getValues()[i];
            }
        } else if (other.getValues().length < getValues().length) {
            for (int i = 0; i < other.getValues().length; i++) {
                getValues()[i] = other.getValues()[i];
            }
        }

        return this;
    }

    public boolean equals(Object obj) {
        try {
            if (!(obj instanceof Matrix3f))
                return false;

            Matrix3f m_obj = (Matrix3f) obj;
            for (int i = 0; i < values.length; i++) {
                if (values[i] != m_obj.values[i])
                    return false;

            }
            return true;
        } catch (RuntimeException __dummyCatchVar0) {
            throw __dummyCatchVar0;
        } catch (Exception __dummyCatchVar0) {
            throw new RuntimeException(__dummyCatchVar0);
        }

    }

    public String toString() {
        try {
            String res = "[";
            res += values[Matrix3f.m00] + ", " + values[Matrix3f.m10] + ", " + values[Matrix3f.m20] + "\n";
            res += values[Matrix3f.m01] + ", " + values[Matrix3f.m11] + ", " + values[Matrix3f.m21] + "\n";
            res += values[Matrix3f.m02] + ", " + values[Matrix3f.m12] + ", " + values[Matrix3f.m22] + "\n";
            res += values[Matrix3f.m03] + ", " + values[Matrix3f.m13] + ", " + values[Matrix3f.m23];
            res += "]";
            return res;
        } catch (RuntimeException __dummyCatchVar2) {
            throw __dummyCatchVar2;
        } catch (Exception __dummyCatchVar2) {
            throw new RuntimeException(__dummyCatchVar2);
        }

    }

}


