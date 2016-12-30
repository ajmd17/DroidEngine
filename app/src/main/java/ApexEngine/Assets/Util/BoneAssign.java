package ApexEngine.Assets.Util;


public class BoneAssign {
    int vertIndex, boneIndex;
    float weight;

    public int getVertexIndex() {
        return vertIndex;
    }

    public void setVertexIndex(int i) {
        this.vertIndex = i;
    }

    public float getBoneWeight() {
        return weight;
    }

    public void setBoneWeight(float f) {
        weight = f;
    }

    public int getBoneIndex() {
        return boneIndex;
    }

    public void setBoneIndex(int i) {
        boneIndex = i;
    }

    public BoneAssign(int vertIndex, float weight, int boneIndex) {
        this.vertIndex = vertIndex;
        this.weight = weight;
        this.boneIndex = boneIndex;
    }

}


