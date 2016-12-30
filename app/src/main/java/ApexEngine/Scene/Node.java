//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:51 PM
//

package ApexEngine.Scene;

import java.util.ArrayList;

import ApexEngine.Math.BoundingBox;
import ApexEngine.Math.Quaternion;
import ApexEngine.Math.Vector3f;
import ApexEngine.Rendering.RenderManager;
import ApexEngine.Scene.GameObject;
import ApexEngine.Scene.Geometry;
import ApexEngine.Scene.Node;

public class Node  extends GameObject 
{
    protected ArrayList<GameObject> children = new ArrayList<GameObject>();
    protected BoundingBox worldBoundingBox, localBoundingBox;
    public Node() {
        super();
    }

    public Node(String name) {
        super(name);
    }

    public BoundingBox getWorldBoundingBox() {
        if (worldBoundingBox == null)
        {
            worldBoundingBox = new BoundingBox();
            updateWorldBoundingBox();
        }
         
        return worldBoundingBox;
    }

    public BoundingBox getLocalBoundingBox() {
        if (localBoundingBox == null)
        {
            localBoundingBox = new BoundingBox();
            updateLocalBoundingBox();
        }
         
        return localBoundingBox;
    }

    public void updateWorldBoundingBox() {
        if (worldBoundingBox != null)
        {
            worldBoundingBox.clear();
            for (GameObject child : children)
            {
                worldBoundingBox.extend(child.getWorldBoundingBox());
            }
        }
         
    }

    public void updateLocalBoundingBox() {
        if (localBoundingBox != null)
        {
            localBoundingBox.clear();
            for (GameObject child : children)
            {
                localBoundingBox.extend(child.getLocalBoundingBox());
            }
        }
         
    }

    public ArrayList<GameObject> getChildren()  {
        return children;
    }

    public void addChild(GameObject child)  {
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(GameObject child)  {
        children.remove(child);
        child.setParent(null);
        child.update(renderManager);
    }

    public GameObject getChild(int i)  {
        return children.get(i);
    }

    public Geometry getChildGeom(int i)  {
        return (Geometry)children.get(i);
    }

    public Node getChildNode(int i)  {
        return (Node)children.get(i);
    }

    public GameObject getChild(String name)  {
        for (int i = 0;i < children.size();i++)
        {
            if (children.get(i).getName().equals(name))
            {
                return children.get(i);
            }
             
        }
        return null;
    }

    public Geometry getChildGeom(String name)  {
        return (Geometry)getChild(name);
    }

    public Node getChildNode(String name)  {
        return (Node)getChild(name);
    }

    public void setWorldTransformPhysics(Vector3f trans, Quaternion rot, Vector3f scl) {
        super.setWorldTransformPhysics(trans,rot,scl);
        for (GameObject gi : children)
            gi.setWorldTransformPhysics(trans.add(gi.getLocalTranslation()),rot.multiply(gi.getLocalRotation()),scl.multiply(gi.getLocalScale()));
    }

    public void update(RenderManager renderManager)  {
        super.update(renderManager);
        for (int i = 0;i < children.size();i++)
        {
            children.get(i).update(renderManager);
        }
    }

    public void setUpdateNeeded()  {
        super.setUpdateNeeded();
        for (int i = 0;i < children.size();i++)
        {
            children.get(i).setUpdateNeeded();
        }
    }

    public GameObject clone() {
        try
        {
            Node res = new Node(this.name);
            res.setLocalTranslation(this.getLocalTranslation());
            res.setLocalScale(this.getLocalScale());
            res.setLocalRotation(this.getLocalRotation());
            for (int i = 0;i < children.size();i++)
            {
                res.addChild(children.get(i).clone());
            }
            return res;
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


