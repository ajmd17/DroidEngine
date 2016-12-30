//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:51 PM
//

package ApexEngine.Rendering;

import ApexEngine.Math.Vector2f;
import ApexEngine.Math.Vector3f;

public class Vertex {
    public Vector3f position;
    public Vector3f normal;
    public Vector2f texCoord0;
    public Vector2f texCoord1;
    public Vector3f tangent;
    public Vector3f bitangent;
    public float[] boneWeights = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
    public int[] boneIndices = new int[]{0, 0, 0, 0};
    private int boneISize = 0, boneWSize = 0;

    public Vertex(Vector3f pos) {
        position = pos;
    }

    public Vertex(Vector3f pos, Vector2f tc0) {
        position = pos;
        texCoord0 = tc0;
    }

    public Vertex(Vector3f pos, Vector2f tc0, Vector3f norm) {
        position = pos;
        texCoord0 = tc0;
        normal = norm;
    }

    public Vertex(Vertex other) {
        this.position = other.position;
        this.normal = other.normal;
        this.texCoord0 = other.texCoord0;
        this.texCoord1 = other.texCoord1;
        this.tangent = other.tangent;
        this.bitangent = other.bitangent;
        for (int i = 0; i < 4; i++) {
            this.boneIndices[i] = other.boneIndices[i];
            this.boneWeights[i] = other.boneWeights[i];
        }
    }

    public void setPosition(Vector3f pos) {
        position = pos;
    }

    public void setTexCoord0(Vector2f tc0) {
        texCoord0 = tc0;
    }

    public void setTexCoord1(Vector2f tc1) {
        texCoord0 = tc1;
    }

    public void setNormal(Vector3f norm) {
        normal = norm;
    }

    public void setTangent(Vector3f tang) {
        tangent = tang;
    }

    public void setBitangent(Vector3f bitang) {
        bitangent = bitang;
    }

    public void setBoneWeight(int i, float val) {
        boneWeights[i] = val;
    }

    public void setBoneIndex(int i, int val) {
        boneIndices[i] = val;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector2f getTexCoord0() {
        return texCoord0;
    }

    public Vector2f getTexCoord1() {
        return texCoord1;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public Vector3f getTangent() {
        return tangent;
    }

    public Vector3f getBitangent() {
        return bitangent;
    }

    public float getBoneWeight(int i) {
        return boneWeights[i];
    }

    public int getBoneIndex(int i) {
        return boneIndices[i];
    }

    public void addBoneWeight(float weight) {
        if (boneWSize < 4) {
            boneWeights[boneWSize++] = weight;
        }
    }

    public void addBoneIndex(int idx) {
        if (boneISize < 4) {
            boneIndices[boneISize++] = idx;
        }
    }
}


