package ApexEngine.Rendering.Util;

import java.util.ArrayList;

import ApexEngine.Math.Matrix4f;
import ApexEngine.Math.Vector2f;
import ApexEngine.Math.Vector3f;
import ApexEngine.Rendering.Mesh;
import ApexEngine.Rendering.Vertex;
import ApexEngine.Rendering.VertexAttributes;
import ApexEngine.Scene.GameObject;
import ApexEngine.Scene.Geometry;

public class MeshUtil {
    /**
     * Calculates the bounding box of the mesh and sets the mesh to [0, 0, 0].
     *
     * @param mesh
     */
    public static void setToOrigin(Geometry geom) {
        Mesh mesh = geom.getMesh();
        if (mesh.getBoundingBox() == null)
            mesh.setBoundingBox(mesh.createBoundingBox());

        Vector3f difference = geom.getLocalTranslation().subtract(mesh.getBoundingBox().getCenter());
        for (int i = 0; i < mesh.indices.size(); i++) {
            mesh.vertices.get(mesh.indices.get(i)).setPosition(mesh.vertices.get(mesh.indices.get(i)).getPosition().add(difference));
        }
        mesh.setVertices(mesh.vertices, mesh.indices);
       /* if (geom.getController(RigidBodyControl.class) != null)
        {
            RigidBodyControl rbc = (RigidBodyControl)geom.getController(RigidBodyControl.class);
        }*/

        geom.setLocalTranslation(mesh.getBoundingBox().getCenter());
    }

    public static Mesh mergeMeshes(GameObject gameObject) {
        ArrayList<Mesh> meshes;
        ArrayList<Matrix4f> transforms = new ArrayList<Matrix4f>();
        meshes = RenderUtil.gatherMeshes(gameObject, transforms);
        return mergeMeshes(meshes, transforms);
    }

    public static Mesh mergeMeshes(ArrayList<Mesh> meshes, ArrayList<Matrix4f> transforms) {
        Mesh resMesh = new Mesh();
        ArrayList<Vertex> finalVertices = new ArrayList<Vertex>();
        for (int m = 0; m < meshes.size(); m++) {
            for (int i = 0; i < meshes.get(m).indices.size(); i++) {
                Vertex vertex = meshes.get(m).vertices.get(meshes.get(m).indices.get(i));
                Vertex newVert = new Vertex(vertex);
                newVert.setPosition(vertex.getPosition().multiply(transforms.get(m)));
                finalVertices.add(newVert);
            }
        }
        resMesh.setVertices(finalVertices);
        resMesh.setMaterial(meshes.get(0).getMaterial());
        return resMesh;
    }

    public static float[] createFloatBuffer(Mesh mesh) {
        ArrayList<Vertex> a = mesh.vertices;
        int vertSize = 0;
        int prevSize = 0;
        int offset = 0;
        if (mesh.getAttributes().hasAttribute(VertexAttributes.POSITIONS)) {
            offset += prevSize * 4;
            mesh.getAttributes().setPositionOffset(offset);
            prevSize = 3;
            vertSize += prevSize;
        }

        if (mesh.getAttributes().hasAttribute(VertexAttributes.TEXCOORDS0)) {
            offset += prevSize * 4;
            mesh.getAttributes().setTexCoord0Offset(offset);
            prevSize = 2;
            vertSize += prevSize;
        }

        if (mesh.getAttributes().hasAttribute(VertexAttributes.TEXCOORDS1)) {
            offset += prevSize * 4;
            mesh.getAttributes().setTexCoord1Offset(offset);
            prevSize = 2;
            vertSize += prevSize;
        }

        if (mesh.getAttributes().hasAttribute(VertexAttributes.NORMALS)) {
            offset += prevSize * 4;
            mesh.getAttributes().setNormalOffset(offset);
            prevSize = 3;
            vertSize += prevSize;
        }

        if (mesh.getAttributes().hasAttribute(VertexAttributes.TANGENTS)) {
            offset += prevSize * 4;
            mesh.getAttributes().setTangentOffset(offset);
            prevSize = 3;
            vertSize += prevSize;
        }

        if (mesh.getAttributes().hasAttribute(VertexAttributes.BITANGENTS)) {
            offset += prevSize * 4;
            mesh.getAttributes().setBitangentOffset(offset);
            prevSize = 3;
            vertSize += prevSize;
        }

        if (mesh.getAttributes().hasAttribute(VertexAttributes.BONEWEIGHTS)) {
            offset += prevSize * 4;
            mesh.getAttributes().setBoneWeightOffset(offset);
            prevSize = 4;
            vertSize += prevSize;
        }

        if (mesh.getAttributes().hasAttribute(VertexAttributes.BONEINDICES)) {
            offset += prevSize * 4;
            mesh.getAttributes().setBoneIndexOffset(offset);
            prevSize = 4;
            vertSize += prevSize;
        }

        mesh.vertexSize = vertSize;
        ArrayList<Float> floatBuffer = new ArrayList<Float>();
        for (int i = 0; i < mesh.vertices.size(); i++) {
            if (mesh.getAttributes().hasAttribute(VertexAttributes.POSITIONS)) {
                if (mesh.vertices.get(i).getPosition() != null) {
                    floatBuffer.add(mesh.vertices.get(i).getPosition().x);
                    floatBuffer.add(mesh.vertices.get(i).getPosition().y);
                    floatBuffer.add(mesh.vertices.get(i).getPosition().z);
                }

            }

            if (mesh.getAttributes().hasAttribute(VertexAttributes.TEXCOORDS0)) {
                if (mesh.vertices.get(i).getTexCoord0() != null) {
                    floatBuffer.add(mesh.vertices.get(i).getTexCoord0().x);
                    floatBuffer.add(mesh.vertices.get(i).getTexCoord0().y);
                }

            }

            if (mesh.getAttributes().hasAttribute(VertexAttributes.TEXCOORDS1)) {
                if (mesh.vertices.get(i).getTexCoord1() != null) {
                    floatBuffer.add(mesh.vertices.get(i).getTexCoord1().x);
                    floatBuffer.add(mesh.vertices.get(i).getTexCoord1().y);
                }

            }

            if (mesh.getAttributes().hasAttribute(VertexAttributes.NORMALS)) {
                if (mesh.vertices.get(i).getNormal() != null) {
                    floatBuffer.add(mesh.vertices.get(i).getNormal().x);
                    floatBuffer.add(mesh.vertices.get(i).getNormal().y);
                    floatBuffer.add(mesh.vertices.get(i).getNormal().z);
                }

            }

            if (mesh.getAttributes().hasAttribute(VertexAttributes.TANGENTS)) {
                if (mesh.vertices.get(i).getTangent() != null) {
                    floatBuffer.add(mesh.vertices.get(i).getTangent().x);
                    floatBuffer.add(mesh.vertices.get(i).getTangent().y);
                    floatBuffer.add(mesh.vertices.get(i).getTangent().z);
                }

            }

            if (mesh.getAttributes().hasAttribute(VertexAttributes.BITANGENTS)) {
                if (mesh.vertices.get(i).getBitangent() != null) {
                    floatBuffer.add(mesh.vertices.get(i).getBitangent().x);
                    floatBuffer.add(mesh.vertices.get(i).getBitangent().y);
                    floatBuffer.add(mesh.vertices.get(i).getBitangent().z);
                }

            }

            if (mesh.getAttributes().hasAttribute(VertexAttributes.BONEWEIGHTS)) {
                floatBuffer.add(a.get(i).getBoneWeight(0));
                floatBuffer.add(a.get(i).getBoneWeight(1));
                floatBuffer.add(a.get(i).getBoneWeight(2));
                floatBuffer.add(a.get(i).getBoneWeight(3));
            }

            if (mesh.getAttributes().hasAttribute(VertexAttributes.BONEINDICES)) {
                floatBuffer.add((float) a.get(i).getBoneIndex(0));
                floatBuffer.add((float) a.get(i).getBoneIndex(1));
                floatBuffer.add((float) a.get(i).getBoneIndex(2));
                floatBuffer.add((float) a.get(i).getBoneIndex(3));
            }

        }
        float[] finalFloatBuffer = new float[floatBuffer.size()];
        for (int i = 0; i < floatBuffer.size(); i++) {
            finalFloatBuffer[i] = floatBuffer.get(i);
        }
        return finalFloatBuffer;
    }

    public static void calculateTangents(ArrayList<Vertex> vertices, ArrayList<Integer> indices) {
        for (int i = 0; i < indices.size(); i += 3) {
            try {
                Vertex v0 = vertices.get(indices.get(i));
                Vertex v1 = vertices.get(indices.get(i + 1));
                Vertex v2 = vertices.get(indices.get(i + 2));
                Vector2f uv0 = v0.getTexCoord0();
                Vector2f uv1 = v1.getTexCoord0();
                Vector2f uv2 = v2.getTexCoord0();
                Vector3f edge1 = v1.getPosition().subtract(v0.getPosition());
                Vector3f edge2 = v2.getPosition().subtract(v0.getPosition());
                Vector2f edge1uv = uv1.subtract(uv0);
                Vector2f edge2uv = uv2.subtract(uv0);
                float cp = edge1uv.y * edge2uv.x - edge1uv.x * edge2uv.y;
                if (cp != 0.0f) {
                    float mul = 1.0f / cp;
                    Vector3f tangent = new Vector3f().set(edge1.multiply(-edge2uv.y).addStore(edge2.multiply(edge1uv.y)));
                    tangent.multiplyStore(mul);
                    Vector3f bitangent = new Vector3f().set(edge1.multiply(-edge2uv.x).addStore(edge2.multiply(edge1uv.x)));
                    bitangent.multiplyStore(mul);
                    tangent.normalizeStore();
                    bitangent.normalizeStore();
                    v0.setTangent(tangent);
                    v1.setTangent(tangent);
                    v2.setTangent(tangent);
                    v0.setBitangent(bitangent);
                    v1.setBitangent(bitangent);
                    v2.setBitangent(bitangent);
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }

        }
    }

}


