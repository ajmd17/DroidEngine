package ApexEngine.Assets.Obj;


public class ObjIndex   
{
    public int vertexIdx, normalIdx, texCoordIdx;
    public ObjIndex(int v_idx, int n_idx, int t_idx) {
        vertexIdx = v_idx;
        normalIdx = n_idx;
        texCoordIdx = t_idx;
    }

    public ObjIndex() {
    }

}


