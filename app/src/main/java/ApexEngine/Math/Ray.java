package ApexEngine.Math;

public class Ray {
    private Vector3f direction = new Vector3f(), position = new Vector3f();

    public Ray() {
    }

    public Ray(Vector3f direction, Vector3f position) {
        this.direction.set(direction);
        this.position.set(position);
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f value) {
        direction.set(value);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f value) {
        position.set(value);
    }

}


