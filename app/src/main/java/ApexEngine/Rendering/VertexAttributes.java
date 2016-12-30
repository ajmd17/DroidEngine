//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:51 PM
//

package ApexEngine.Rendering;

import java.util.ArrayList;

import ApexEngine.Rendering.VertexAttributes;

public class VertexAttributes   
{
    public static final int POSITIONS = 0, NORMALS = 1, TEXCOORDS0 = 2, TEXCOORDS1 = 3, TANGENTS = 4, BITANGENTS = 5, BONEWEIGHTS = 6, BONEINDICES = 7;
    protected int posOffset, tc0Offset, tc1Offset, normalOffset, boneIndexOffset, boneWeightOffset, tangentOffset, bitangentOffset;
    private ArrayList<Integer> attribs = new ArrayList<Integer>();
    public VertexAttributes()  {
    }

    public void setAttribute(int attrib)  {
        if (!attribs.contains(attrib))
        {
            attribs.add(attrib);
        }
         
    }

    public boolean hasAttribute(int attrib)  {
        return attribs.contains(attrib);
    }

    public int getPositionOffset()  {
        return posOffset;
    }

    public int getTexCoord0Offset()  {
        return tc0Offset;
    }

    public int getTexCoord1Offset()  {
        return tc1Offset;
    }

    public int getNormalOffset()  {
        return normalOffset;
    }

    public int getTangentOffset()  {
        return tangentOffset;
    }

    public int getBitangentOffset()  {
        return bitangentOffset;
    }

    public int getBoneWeightOffset()  {
        return boneWeightOffset;
    }

    public int getBoneIndexOffset()  {
        return boneIndexOffset;
    }

    public void setPositionOffset(int offset)  {
        posOffset = offset;
    }

    public void setTexCoord0Offset(int offset)  {
        tc0Offset = offset;
    }

    public void setTexCoord1Offset(int offset)  {
        tc1Offset = offset;
    }

    public void setNormalOffset(int offset)  {
        normalOffset = offset;
    }

    public void setTangentOffset(int offset)  {
        tangentOffset = offset;
    }

    public void setBitangentOffset(int offset)  {
        bitangentOffset = offset;
    }

    public void setBoneWeightOffset(int offset)  {
        boneWeightOffset = offset;
    }

    public void setBoneIndexOffset(int offset)  {
        boneIndexOffset = offset;
    }

    public String toString() {
        try
        {
            String fstr = "Vertex Attributes:\n";
            for (int i = 0;i < attribs.size();i++)
            {
                int p = attribs.get(i);
                String str = "";
                if (p == VertexAttributes.POSITIONS)
                {
                    str = "Positions";
                }
                else if (p == VertexAttributes.TEXCOORDS0)
                {
                    str = "Texture Coordinates (0)";
                }
                else if (p == VertexAttributes.TEXCOORDS1)
                {
                    str = "Texture Coordinates (1)";
                }
                else if (p == VertexAttributes.NORMALS)
                {
                    str = "Normals";
                }
                else if (p == VertexAttributes.TANGENTS)
                {
                    str = "Tangents";
                }
                else if (p == VertexAttributes.BITANGENTS)
                {
                    str = "Bitangents";
                }
                else if (p == VertexAttributes.BONEWEIGHTS)
                {
                    str = "Bone Weights";
                }
                else if (p == VertexAttributes.BONEINDICES)
                {
                    str = "Bone Indices";
                }
                        
                fstr += "\t" + str + "\n";
            }
            return fstr;
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


