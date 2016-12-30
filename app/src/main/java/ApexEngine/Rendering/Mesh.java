package ApexEngine.Rendering;

import java.util.ArrayList;

import ApexEngine.Math.BoundingBox;
import ApexEngine.Math.Matrix4f;
import ApexEngine.Math.Vector3f;
import ApexEngine.Rendering.Animation.Skeleton;

public class Mesh {
    public enum PrimitiveTypes {
        Triangles, TriangleStrip, TriangleFan, Quads, QuadStrip, Lines, Points, Patches
    }

    public int vbo, ibo, size, vertexSize;
    protected Skeleton skeleton = null;
    public VertexAttributes attribs = new VertexAttributes();
    public ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    public ArrayList<Integer> indices = new ArrayList<Integer>();
    public PrimitiveTypes primitiveType = PrimitiveTypes.Triangles;
    private BoundingBox boundingBox = null;
    private Material material = new Material();
    private boolean uploaded = false;

    public Mesh() {
        vbo = 0;
        ibo = 0;
    }

    public void dispose() {
        RenderManager.getRenderer().deleteMesh(this);

        try {
            if (skeleton != null) {
                if (skeleton.getAnimations() != null) {
                    skeleton.getAnimations().clear();
                }

                if (skeleton.getBones() != null) {
                    skeleton.getBones().clear();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material value) {
        material = value;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox value) {
        boundingBox = value;
    }

    public PrimitiveTypes getPrimitiveType() {
        return primitiveType;
    }

    public void setPrimitiveType(PrimitiveTypes value) {
        primitiveType = value;
    }

    public Skeleton getSkeleton() {
        return skeleton;
    }

    public void setSkeleton(Skeleton skeleton) {
        this.skeleton = skeleton;
    }

    public VertexAttributes getAttributes() {
        return attribs;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        ArrayList<Integer> idc = new ArrayList<Integer>();

        for (int i = 0; i < vertices.size(); i++) {
            idc.add(i);
        }

        setVertices(vertices, idc);
    }

    public void setVertices(ArrayList<Vertex> vertices, ArrayList<Integer> indices) {
        this.vertices = vertices;
        this.indices = indices;
        if (vertices.size() > 0) {
            if (vertices.get(0).getPosition() != null)
                attribs.setAttribute(VertexAttributes.POSITIONS);

            if (vertices.get(0).getNormal() != null)
                attribs.setAttribute(VertexAttributes.NORMALS);

            if (vertices.get(0).getTexCoord0() != null)
                attribs.setAttribute(VertexAttributes.TEXCOORDS0);

            if (vertices.get(0).getTexCoord1() != null)
                attribs.setAttribute(VertexAttributes.TEXCOORDS1);

            if (vertices.get(0).getTangent() != null)
                attribs.setAttribute(VertexAttributes.TANGENTS);

            if (vertices.get(0).getBitangent() != null)
                attribs.setAttribute(VertexAttributes.BITANGENTS);

            if (vertices.get(0).getBoneWeight(0) != 0 || vertices.get(0).getBoneWeight(1) != 0
                    || vertices.get(0).getBoneWeight(2) != 0 || vertices.get(0).getBoneWeight(3) != 0)
                attribs.setAttribute(VertexAttributes.BONEWEIGHTS);

            if (vertices.get(0).getBoneIndex(0) != 0 || vertices.get(0).getBoneIndex(1) != 0
                    || vertices.get(0).getBoneIndex(2) != 0 || vertices.get(0).getBoneIndex(3) != 0)
                attribs.setAttribute(VertexAttributes.BONEINDICES);

        }

        updateMesh();
    }

    private void updateMesh() {
        uploaded = false;
    }

    public void render() {
        if (!uploaded) {
            RenderManager.getRenderer().createMesh(this);
            RenderManager.getRenderer().uploadMesh(this);
            uploaded = true;
        }

        RenderManager.getRenderer().renderMesh(this);
    }

    public Mesh clone() {
        Mesh newMesh = new Mesh();
        newMesh.setVertices(vertices, indices);
        newMesh.primitiveType = this.primitiveType;
        // newMesh.skeleton = this.skeleton;
        newMesh.setMaterial(this.getMaterial().clone());
        return newMesh;
    }

    public BoundingBox createBoundingBox() {
        return createBoundingBox(vertices, indices);
    }

    public BoundingBox createBoundingBox(Vector3f worldTranslation) {
        return createBoundingBox(vertices, indices, worldTranslation);
    }

    public BoundingBox createBoundingBox(Matrix4f worldTransform) {
        return createBoundingBox(vertices, indices, worldTransform);
    }

    public BoundingBox createBoundingBox(ArrayList<Vertex> vertices, ArrayList<Integer> indices,
                                         Matrix4f worldTransform) {
        BoundingBox b = new BoundingBox(new Vector3f(Float.MAX_VALUE), new Vector3f(Float.MIN_VALUE));
        for (int i = 0; i < indices.size(); i++) {
            b.extend(vertices.get(indices.get(i)).getPosition().multiply(worldTransform));
        }
        return b;
    }

    public BoundingBox createBoundingBox(ArrayList<Vertex> vertices, ArrayList<Integer> indices, Vector3f worldTranslation) {
        BoundingBox b = new BoundingBox(new Vector3f(Float.MAX_VALUE), new Vector3f(Float.MIN_VALUE));

        for (int i = 0; i < indices.size(); i++) {
            b.extend(vertices.get(indices.get(i)).getPosition().add(worldTranslation));
        }

        return b;
    }

    public BoundingBox createBoundingBox(ArrayList<Vertex> vertices, ArrayList<Integer> indices) {
        BoundingBox b = new BoundingBox(new Vector3f(Float.MAX_VALUE), new Vector3f(Float.MIN_VALUE));

        for (int i = 0; i < indices.size(); i++) {
            b.extend(vertices.get(indices.get(i)).getPosition());
        }

        return b;
    }

}
