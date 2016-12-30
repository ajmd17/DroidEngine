package ApexEngine.Math;

public class MathUtil {
    public static final float PI = 3.14159265358979f;

    public static float toDegrees(float rad) {
        return rad * 180.0f / PI;
    }

    public static float toRadians(float deg) {
        return deg * PI / 180.0f;
    }

    public static float clamp(float val, float min, float max) {
        if (val > max) {
            return max;
        }

        if (val < min) {
            return min;
        }

        return val;
    }

    public static float lerp(float from, float to, float amt) {
        return from + amt * (to - from);
    }

    public static float min(float a, float b) {
        return Math.min(a, b);
    }

    public static float max(float a, float b) {
        return Math.max(a, b);
    }

    public static Vector2f min(Vector2f a, Vector2f b) {
        return new Vector2f(min(a.getX(), b.getX()), min(a.getY(), b.getY()));
    }

    public static Vector2f max(Vector2f a, Vector2f b) {
        return new Vector2f(max(a.getX(), b.getX()), max(a.getY(), b.getY()));
    }

    public static Vector3f min(Vector3f a, Vector3f b) {
        return new Vector3f(min(a.getX(), b.getX()), min(a.getY(), b.getY()), min(a.getZ(), b.getZ()));
    }

    public static Vector3f max(Vector3f a, Vector3f b) {
        return new Vector3f(max(a.getX(), b.getX()), max(a.getY(), b.getY()), max(a.getZ(), b.getZ()));
    }

    public static Vector4f min(Vector4f a, Vector4f b) {
        return new Vector4f(min(a.getX(), b.getX()), min(a.getY(), b.getY()), min(a.getZ(), b.getZ()), min(a.getW(), b.getW()));
    }

    public static Vector4f max(Vector4f a, Vector4f b) {
        return new Vector4f(max(a.getX(), b.getX()), max(a.getY(), b.getY()), max(a.getZ(), b.getZ()), max(a.getW(), b.getW()));
    }

    public static Vector2f round(Vector2f vec) {
        return new Vector2f((float) Math.round(vec.getX()), (float) Math.round(vec.getY()));
    }

    public static Vector3f round(Vector3f vec) {
        return new Vector3f((float) Math.round(vec.getX()), (float) Math.round(vec.getY()), (float) Math.round(vec.getZ()));
    }

    public static Vector4f round(Vector4f vec) {
        return new Vector4f((float) Math.round(vec.getX()), (float) Math.round(vec.getY()), (float) Math.round(vec.getZ()), (float) Math.round(vec.getW()));
    }

}


