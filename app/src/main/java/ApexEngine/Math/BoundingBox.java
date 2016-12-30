package ApexEngine.Math;

public class BoundingBox {
    private Matrix4f matrix = new Matrix4f();
    private Vector3f max = new Vector3f(Float.MIN_VALUE), min = new Vector3f(Float.MAX_VALUE), center = new Vector3f();
    public Vector3f dimension = new Vector3f();
    private Vector3f[] corners = new Vector3f[8];

    public BoundingBox() {
        for (int i = 0; i < corners.length; i++)
            corners[i] = new Vector3f();
    }

    public BoundingBox(Vector3f dimMin, Vector3f dimMax) {
        for (int i = 0; i < corners.length; i++)
            corners[i] = new Vector3f();
        createBoundingBox(dimMin, dimMax);
    }

    public Matrix4f getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix4f value) {
        matrix.set(value);
    }

    public Vector3f getMin() {
        return min;
    }

    public Vector3f getMax() {
        return max;
    }

    public Vector3f getCenter() {
        return center;
    }

    public Vector3f[] getCorners() {
        return corners;
    }

    private void updateCorners() {
        corners[0].set(max.x, max.y, max.z);
        corners[1].set(min.x, max.y, max.z);
        corners[2].set(min.x, max.y, min.z);
        corners[3].set(max.x, max.y, min.z);
        corners[4].set(max.x, min.y, max.z);
        corners[5].set(min.x, min.y, max.z);
        corners[6].set(min.x, min.y, min.z);
        corners[7].set(max.x, min.y, min.z);
    }

    public BoundingBox createBoundingBox(Vector3f minimum, Vector3f maximum) {
        return set(minimum, maximum);
    }

    public BoundingBox extend(BoundingBox b) {
        return extend(b.getMin()).extend(b.getMax());
    }

    public BoundingBox extend(Vector3f point) {
        return set(MathUtil.min(min, point), MathUtil.max(max, point));
    }

    public BoundingBox set(Vector3f minimum, Vector3f maximum) {
        min.set(minimum);
        max.set(maximum);
        center.set(min).addStore(max).multiplyStore(0.5f);
        dimension.set(max).subtractStore(min).multiplyStore(0.5f);
        updateCorners();
        return this;
    }

    public BoundingBox clear() {
        return set(new Vector3f(0), new Vector3f(0));
    }

    /**
     * Get the intersection point between the bounding box and a ray.
     *
     * @param box
     * @return
     */
    public Vector3f intersect(Ray ray) {
        ;
        final float epsilon = 0.00001f;
        float tMin = Float.NaN, tMax = Float.NaN;
        if (Math.abs(ray.getDirection().getX()) < epsilon) {
            if (ray.getPosition().getX() < getMin().getX() || ray.getPosition().getX() > getMax().getX())
                return Vector3f.NaN;

        } else {
            tMin = (getMin().getX() - ray.getPosition().getX()) / ray.getDirection().getX();
            tMax = (getMax().getX() - ray.getPosition().getX()) / ray.getDirection().getX();
            if (tMin > tMax) {
                float temp = tMin;
                tMin = tMax;
                tMax = temp;
            }

        }
        if (Math.abs(ray.getDirection().getY()) < epsilon) {
            if (ray.getPosition().getY() < getMin().getY() || ray.getPosition().getY() > getMax().getY())
                return Vector3f.NaN;

        } else {
            float tMinY = (getMin().getY() - ray.getPosition().getY()) / ray.getDirection().getY();
            float tMaxY = (getMax().getY() - ray.getPosition().getY()) / ray.getDirection().getY();
            if (tMinY > tMaxY) {
                float temp = tMinY;
                tMinY = tMaxY;
                tMaxY = temp;
            }

            if ((tMin != Float.NaN && tMin > tMaxY) || (tMax != Float.NaN && tMinY > tMax))
                return Vector3f.NaN;

            if (tMin == Float.NaN || tMinY > tMin)
                tMin = tMinY;

            if (tMax == Float.NaN || tMaxY < tMax)
                tMax = tMaxY;

        }
        if (Math.abs(ray.getDirection().getZ()) < epsilon) {
            if (ray.getPosition().getZ() < getMin().getZ() || ray.getPosition().getZ() > getMax().getZ())
                return Vector3f.NaN;

        } else {
            float tMinZ = (getMin().getZ() - ray.getPosition().getZ()) / ray.getDirection().getZ();
            float tMaxZ = (getMax().getZ() - ray.getPosition().getZ()) / ray.getDirection().getZ();
            if (tMinZ > tMaxZ) {
                float temp = tMinZ;
                tMinZ = tMaxZ;
                tMaxZ = temp;
            }

            if ((tMin != Float.NaN && tMin > tMaxZ) || (tMax != Float.NaN && tMinZ > tMax))
                return Vector3f.NaN;

            if (tMin == Float.NaN || tMinZ > tMin)
                tMin = tMinZ;

            if (tMax == Float.NaN || tMaxZ < tMax)
                tMax = tMaxZ;

        }
        if ((tMin == Float.NaN && tMin < 0) && tMax > 0)
            return Vector3f.NaN;

        if (tMin < 0)
            return Vector3f.NaN;

        return ray.getPosition().add(ray.getDirection().multiply(tMin));
    }

    public boolean equals(Object obj) {
        try {
            if (!(obj instanceof BoundingBox))
                return false;

            BoundingBox bb_obj = (BoundingBox) obj;
            if (bb_obj.getMax().equals(getMax()) && bb_obj.getMin().equals(getMin()))
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
            String str = "Max: " + getMax().toString() + "\nMin: " + getMin().toString();
            return str;
        } catch (RuntimeException __dummyCatchVar2) {
            throw __dummyCatchVar2;
        } catch (Exception __dummyCatchVar2) {
            throw new RuntimeException(__dummyCatchVar2);
        }

    }

}


