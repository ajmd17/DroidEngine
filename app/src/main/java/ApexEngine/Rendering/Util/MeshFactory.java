package ApexEngine.Rendering.Util;

import java.util.ArrayList;

import ApexEngine.Math.Vector2f;
import ApexEngine.Math.Vector3f;
import ApexEngine.Rendering.Mesh;
import ApexEngine.Rendering.Mesh.PrimitiveTypes;
import ApexEngine.Rendering.Vertex;

public class MeshFactory   
{
    public static Mesh createQuad() {
        Mesh mesh = new Mesh();
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        vertices.add(new Vertex(new Vector3f(-1f, -1f, 0),new Vector2f(0,0),new Vector3f(0,0,1)));
        vertices.add(new Vertex(new Vector3f(1, -1f, 0),new Vector2f(1f, 0),new Vector3f(0,0,1)));
        vertices.add(new Vertex(new Vector3f(1f, 1f, 0),new Vector2f(1f, 1f),new Vector3f(0,0,1)));
        vertices.add(new Vertex(new Vector3f(-1f, 1f, 0),new Vector2f(0, 1f),new Vector3f(0,0,1)));
        mesh.setVertices(vertices);
        mesh.setPrimitiveType(PrimitiveTypes.TriangleFan);
        return mesh;
    }

    public static Mesh createCube(Vector3f min, Vector3f max) {
    	ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        vertices.add(new Vertex(new Vector3f(max.x,min.y,max.z)));
        vertices.add(new Vertex(new Vector3f(min.x,min.y,max.z)));
        vertices.add(new Vertex(new Vector3f(min.x,min.y,min.z)));
        vertices.add(new Vertex(new Vector3f(max.x,max.y,min.z)));
        vertices.add(new Vertex(new Vector3f(min.x,max.y,min.z)));
        vertices.add(new Vertex(new Vector3f(min.x,max.y,max.z)));
        vertices.add(new Vertex(new Vector3f(max.x,max.y,min.z)));
        vertices.add(new Vertex(new Vector3f(max.x,max.y,max.z)));
        vertices.add(new Vertex(new Vector3f(max.x,min.y,max.z)));
        vertices.add(new Vertex(new Vector3f(max.x,min.y,max.z)));
        vertices.add(new Vertex(new Vector3f(max.x,max.y,max.z)));
        vertices.add(new Vertex(new Vector3f(min.x,max.y,max.z)));
        vertices.add(new Vertex(new Vector3f(min.x,max.y,max.z)));
        vertices.add(new Vertex(new Vector3f(min.x,max.y,min.z)));
        vertices.add(new Vertex(new Vector3f(min.x,min.y,min.z)));
        vertices.add(new Vertex(new Vector3f(max.x,min.y,min.z)));
        vertices.add(new Vertex(new Vector3f(min.x,min.y,min.z)));
        vertices.add(new Vertex(new Vector3f(min.x,max.y,min.z)));
        vertices.add(new Vertex(new Vector3f(max.x,min.y,min.z)));
        vertices.add(new Vertex(new Vector3f(max.x,min.y,max.z)));
        vertices.add(new Vertex(new Vector3f(min.x,min.y,min.z)));
        vertices.add(new Vertex(new Vector3f(max.x,max.y,max.z)));
        vertices.add(new Vertex(new Vector3f(max.x,max.y,min.z)));
        vertices.add(new Vertex(new Vector3f(min.x,max.y,max.z)));
        vertices.add(new Vertex(new Vector3f(max.x,min.y,min.z)));
        vertices.add(new Vertex(new Vector3f(max.x,max.y,min.z)));
        vertices.add(new Vertex(new Vector3f(max.x,min.y,max.z)));
        vertices.add(new Vertex(new Vector3f(min.x,min.y,max.z)));
        vertices.add(new Vertex(new Vector3f(max.x,min.y,max.z)));
        vertices.add(new Vertex(new Vector3f(min.x,max.y,max.z)));
        vertices.add(new Vertex(new Vector3f(min.x,min.y,max.z)));
        vertices.add(new Vertex(new Vector3f(min.x,max.y,max.z)));
        vertices.add(new Vertex(new Vector3f(min.x,min.y,min.z)));
        vertices.add(new Vertex(new Vector3f(max.x,max.y,min.z)));
        vertices.add(new Vertex(new Vector3f(max.x,min.y,min.z)));
        vertices.add(new Vertex(new Vector3f(min.x,max.y,min.z)));
        Mesh mesh = new Mesh();
        mesh.setVertices(vertices);
        return mesh;
    }

}


