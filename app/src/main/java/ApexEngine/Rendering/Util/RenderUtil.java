package ApexEngine.Rendering.Util;

import java.util.ArrayList;

import ApexEngine.Math.BoundingBox;
import ApexEngine.Math.Matrix4f;
import ApexEngine.Math.Transform;
import ApexEngine.Rendering.Mesh;
import ApexEngine.Rendering.RenderManager;
import ApexEngine.Scene.GameObject;
import ApexEngine.Scene.Geometry;
import ApexEngine.Scene.Node;

public class RenderUtil {
    public static ArrayList<Mesh> gatherMeshes(GameObject gameObject) {
        ArrayList<Mesh> meshes = new ArrayList<Mesh>();
        if (gameObject instanceof Node) {
            gatherMeshes((Node) gameObject, meshes);
        } else if (gameObject instanceof Geometry) {
            meshes.add(((Geometry) gameObject).getMesh());
        }

        return meshes;
    }

    public static ArrayList<Mesh> gatherMeshes(GameObject gameObject, ArrayList<Matrix4f> worldTransforms) {
        ArrayList<Mesh> meshes = new ArrayList<Mesh>();
        if (worldTransforms == null)
            worldTransforms = new ArrayList<Matrix4f>();

        if (gameObject instanceof Node) {
            gatherMeshes((Node) gameObject, meshes, worldTransforms);
        } else if (gameObject instanceof Geometry) {
            meshes.add(((Geometry) gameObject).getMesh());
            Transform ttransform = new Transform();
            ttransform.setTranslation(gameObject.getUpdatedWorldTranslation());
            ttransform.setRotation(gameObject.getUpdatedWorldRotation());
            ttransform.setScale(gameObject.getUpdatedWorldScale());
            Matrix4f matrix = ttransform.getMatrix();
            worldTransforms.add(matrix);
        }

        return meshes;
    }

    private static void gatherMeshes(Node node, ArrayList<Mesh> meshes) {
        for (GameObject child : node.getChildren()) {
            if (child instanceof Node) {
                gatherMeshes((Node) child, meshes);
            } else if (child instanceof Geometry) {
                meshes.add(((Geometry) child).getMesh());
            }

        }
    }

    private static void gatherMeshes(Node node, ArrayList<Mesh> meshes, ArrayList<Matrix4f> worldTransforms) {
        for (GameObject child : node.getChildren()) {
            if (child instanceof Node) {
                gatherMeshes((Node) child, meshes, worldTransforms);
            } else if (child instanceof Geometry) {
                meshes.add(((Geometry) child).getMesh());
                Transform ttransform = new Transform();
                ttransform.setTranslation(child.getUpdatedWorldTranslation());
                ttransform.setRotation(child.getUpdatedWorldRotation());
                ttransform.setScale(child.getUpdatedWorldScale());
                Matrix4f matrix = ttransform.getMatrix();
                worldTransforms.add(matrix);
            }

        }
    }

    public static ArrayList<Geometry> gatherGeometry(GameObject gameObject) {
        ArrayList<Geometry> geoms = new ArrayList<Geometry>();
        if (gameObject instanceof Node) {
            gatherGeometry((Node) gameObject, geoms);
        } else if (gameObject instanceof Geometry) {
            geoms.add((Geometry) gameObject);
        }

        return geoms;
    }

    private static void gatherGeometry(Node node, ArrayList<Geometry> geoms) {
        for (GameObject child : node.getChildren()) {
            if (child instanceof Node) {
                gatherGeometry((Node) child, geoms);
            } else if (child instanceof Geometry) {
                geoms.add((Geometry) child);
            }

        }
    }

    public static ArrayList<GameObject> gatherObjects(GameObject gameObject) {
        ArrayList<GameObject> objs = new ArrayList<GameObject>();
        objs.add(gameObject);
        if (gameObject instanceof Node) {
            gatherObjects((Node) gameObject, objs);
        }

        return objs;
    }

    private static void gatherObjects(Node node, ArrayList<GameObject> objs) {
        for (GameObject child : node.getChildren()) {
            if (child instanceof Node) {
                objs.add(child);
                gatherObjects((Node) child, objs);
            } else if (child instanceof Geometry) {
                objs.add(child);
            }

        }
    }

    public static void renderBoundingBox(BoundingBox boundingBox) {
        float offset = 0.1f;
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMin().y - offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMin().y - offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMin().y - offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMax().y + offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMax().y + offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMax().y + offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMax().y + offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMax().y + offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMin().y - offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMin().y - offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMax().y + offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMax().y + offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMax().y + offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMax().y + offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMin().y - offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMin().y - offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMin().y - offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMax().y + offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMin().y - offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMin().y - offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMin().y - offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMax().y + offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMax().y + offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMax().y + offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMin().y - offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMax().y + offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMin().y - offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMin().y - offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMin().y - offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMax().y + offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMin().y - offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMax().y + offset, boundingBox.getMax().z + offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMin().y - offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMax().y + offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMax().x + offset, boundingBox.getMin().y - offset, boundingBox.getMin().z - offset);
        RenderManager.getRenderer().drawVertex(boundingBox.getMin().x - offset, boundingBox.getMax().y + offset, boundingBox.getMin().z - offset);
    }

}


