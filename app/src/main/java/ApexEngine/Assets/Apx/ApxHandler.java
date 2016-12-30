package ApexEngine.Assets.Apx;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import ApexEngine.Assets.AssetManager;
import ApexEngine.Assets.LoadedAsset;
import ApexEngine.Assets.Util.BoneAssign;
import ApexEngine.Math.Quaternion;
import ApexEngine.Math.Vector2f;
import ApexEngine.Math.Vector3f;
import ApexEngine.Math.Vector4f;
import ApexEngine.Rendering.Animation.Animation;
import ApexEngine.Rendering.Animation.AnimationController;
import ApexEngine.Rendering.Animation.AnimationTrack;
import ApexEngine.Rendering.Animation.Bone;
import ApexEngine.Rendering.Animation.Keyframe;
import ApexEngine.Rendering.Animation.Skeleton;
import ApexEngine.Rendering.Material;
import ApexEngine.Rendering.Mesh;
import ApexEngine.Rendering.Vertex;
import ApexEngine.Rendering.VertexAttributes;
import ApexEngine.Scene.GameObject;
import ApexEngine.Scene.Geometry;
import ApexEngine.Scene.Node;

public class ApxHandler extends DefaultHandler {

    protected ArrayList<Node> nodes = new ArrayList<Node>();
    protected ArrayList<Geometry> geoms = new ArrayList<Geometry>();
    protected ArrayList<Mesh> meshes = new ArrayList<Mesh>();
    protected ArrayList<Material> mats = new ArrayList<Material>();
    protected HashMap<Geometry, Material> geomMats = new HashMap<Geometry, Material>();
    protected ArrayList<Integer> skeletonAssigns = new ArrayList<Integer>();
    protected ArrayList<Skeleton> skeletons = new ArrayList<Skeleton>();
    protected ArrayList<ArrayList<Bone>> bones = new ArrayList<ArrayList<Bone>>();
    protected ArrayList<ArrayList<BoneAssign>> boneAssigns = new ArrayList<ArrayList<BoneAssign>>();
    protected ArrayList<Animation> animations = new ArrayList<Animation>();
    protected boolean hasAnimations = false;
    protected ArrayList<ArrayList<Vector3f>> positions = new ArrayList<ArrayList<Vector3f>>();
    protected ArrayList<ArrayList<Vector3f>> normals = new ArrayList<ArrayList<Vector3f>>();
    protected ArrayList<ArrayList<Vector2f>> texcoords0 = new ArrayList<ArrayList<Vector2f>>();
    protected ArrayList<ArrayList<Vector2f>> texcoords1 = new ArrayList<ArrayList<Vector2f>>();
    protected ArrayList<ArrayList<Vertex>> vertices = new ArrayList<ArrayList<Vertex>>();
    protected ArrayList<ArrayList<Integer>> faces = new ArrayList<ArrayList<Integer>>();
    protected boolean node = false, geom = false;
    protected Node lastNode = null;

    private LoadedAsset asset;

    public ApxHandler(LoadedAsset asset) {
        this.asset = asset;
    }

    private static Vector2f parseVector2(String str) {
        String replace = str.replace("[", "").replace("]", "");
        replace = replace.substring(replace.length() - 1);
        String[] tokens = replace.split(",");
        float x, y;
        x = Float.parseFloat(tokens[0].trim());
        y = Float.parseFloat(tokens[1].trim());
        return new Vector2f(x, y);
    }

    private static Vector3f parseVector3(String str) {
        String replace = str.replace("[", "").replace("]", "");
        replace = replace.substring(replace.length() - 1);
        String[] tokens = replace.split(",");
        float x, y, z;
        x = Float.parseFloat(tokens[0].trim());
        y = Float.parseFloat(tokens[1].trim());
        z = Float.parseFloat(tokens[2].trim());
        return new Vector3f(x, y, z);
    }

    private static Vector4f parseVector4(String str) {
        String replace = str.replace("[", "").replace("]", "");
        String[] tokens = replace.split(",");
        float x, y, z, w;
        System.out.println(replace);
        x = Float.parseFloat(tokens[0].trim());
        y = Float.parseFloat(tokens[1].trim());
        z = Float.parseFloat(tokens[2].trim());
        w = Float.parseFloat(tokens[3].trim());
        return new Vector4f(x, y, z, w);
    }

    private void endModel() {
        if (skeletons.size() > 0) {
            Skeleton skeleton = skeletons.get(skeletons.size() - 1);
            if (skeleton.getNumBones() > 0) {
                for (int i = 0; i < skeleton.getNumBones(); i++)
                    skeleton.getBone(i).setToBindingPose();
                skeleton.getBone(0).calculateBindingRotation();
                skeleton.getBone(0).calculateBindingTranslation();
                for (int i = 0; i < skeleton.getNumBones(); i++) {
                    skeleton.getBone(i).storeBindingPose();
                    skeleton.getBone(i).clearPose();
                }
                skeleton.getBone(0).updateTransform();
            }

            if (hasAnimations) {
                for (int j = 0; j < animations.size(); j++) {
                    skeletons.get(0).addAnimation(animations.get(j));
                }
                nodes.get(0).addController(new AnimationController(skeletons.get(0)));
            }

        }

        for (int i = 0; i < meshes.size(); i++) {
            ArrayList<Vertex> cVerts = vertices.get(i);
            ArrayList<Integer> cFaces = faces.get(i);
            ArrayList<Vector3f> cPos = positions.get(i);
            ArrayList<Vector3f> cNorm = normals.get(i);
            ArrayList<Vector2f> tc0 = texcoords0.get(i);
            ArrayList<Vector2f> tc1 = texcoords1.get(i);
            ArrayList<BoneAssign> ba = null;
            Mesh mesh = meshes.get(i);
            if (boneAssigns.size() > 0) {
                ba = boneAssigns.get(i);
            }

            int stride = 3;
            if (tc1.size() > 0)
                stride++;

            for (int j = 0; j < cFaces.size(); j += stride) {
                Vertex v = new Vertex(cPos.get(cFaces.get(j)));
                if (cNorm.size() > 0) {
                    mesh.getAttributes().setAttribute(VertexAttributes.NORMALS);
                    v.setNormal(cNorm.get(cFaces.get(j + 1)));
                }

                if (tc0.size() > 0) {
                    mesh.getAttributes().setAttribute(VertexAttributes.TEXCOORDS0);
                    v.setTexCoord1(tc0.get(cFaces.get(j + 2)));
                }

                if (tc1.size() > 0) {
                    mesh.getAttributes().setAttribute(VertexAttributes.TEXCOORDS1);
                    v.setTexCoord1(tc1.get(cFaces.get(j + 3)));
                }

                cVerts.add(v);
            }
            ArrayList<Integer> indexData = new ArrayList<Integer>();
            if (skeletons.size() > 0) {
                mesh.setSkeleton(skeletons.get(0));
                if (boneAssigns.size() > 0) {
                    for (int j = 0; j < ba.size(); j++) {
                        Vertex v = cVerts.get(ba.get(j).getVertexIndex());
                        v.addBoneIndex(ba.get(j).getBoneIndex());
                        v.addBoneWeight(ba.get(j).getBoneWeight());
                    }
                }

            }

            mesh.setVertices(cVerts);
            if (geoms.size() > 0) {
                Geometry parent = geoms.get(i);
                Material material = null;
                if (geomMats.containsKey(parent))
                    material = geomMats.get(parent);
                else
                    material = new Material();
                parent.setMesh(mesh);
                parent.setMaterial(material);
            }

        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals(ApxExporter.TOKEN_NODE)) {
            node = true;
            geom = false;
            String name = attributes.getValue(ApxExporter.TOKEN_NAME);
            Node n = new Node(name);
            if (lastNode != null)
                lastNode.addChild(n);

            lastNode = n;
            nodes.add(n);
        } else if (qName.equals(ApxExporter.TOKEN_GEOMETRY)) {
            node = false;
            geom = true;
            String name = attributes.getValue(ApxExporter.TOKEN_NAME);
            Geometry g = new Geometry();
            g.setName(name);
            if (lastNode != null)
                lastNode.addChild(g);

            geoms.add(g);
        } else if (qName.equals(ApxExporter.TOKEN_MATERIAL)) {
            Material mat = new Material();
            mats.add(mat);
            String bucketStr = attributes.getValue(ApxExporter.TOKEN_MATERIAL_BUCKET);
            ApexEngine.Rendering.RenderManager.Bucket bucket = ApexEngine.Rendering.RenderManager.Bucket.Opaque;
            if (bucketStr != null) {
                ApexEngine.Rendering.RenderManager.Bucket.valueOf(bucketStr);
            } else
                bucket = ApexEngine.Rendering.RenderManager.Bucket.Opaque;
            System.out.println("Bucket: " + bucket.toString());
            mat.setBucket(bucket);
        } else if (qName.equals(ApxExporter.TOKEN_MATERIAL_PROPERTY)) {
            Material lastMaterial = mats.get(mats.size() - 1);
            String name = attributes.getValue(ApxExporter.TOKEN_NAME);
            String type = attributes.getValue(ApxExporter.TOKEN_TYPE);
            String val = attributes.getValue(ApxExporter.TOKEN_VALUE);
            Object value = null;
            if (type.equals(ApxExporter.TOKEN_TYPE_STRING))
                value = val;
            else if (type.equals(ApxExporter.TOKEN_TYPE_INT))
                value = Integer.valueOf(val);
            else if (type.equals(ApxExporter.TOKEN_TYPE_BOOLEAN))
                value = Boolean.valueOf(val);
            else if (type.equals(ApxExporter.TOKEN_TYPE_FLOAT))
                value = Float.parseFloat(val);
            else if (type.equals(ApxExporter.TOKEN_TYPE_VECTOR2))
                value = parseVector2(val);
            else if (type.equals(ApxExporter.TOKEN_TYPE_VECTOR3))
                value = parseVector3(val);
            else if (type.equals(ApxExporter.TOKEN_TYPE_VECTOR4))
                value = parseVector4(val);
            else if (type.equals(ApxExporter.TOKEN_TYPE_TEXTURE)) {
                String texPath = val;
                String parentPath = new File(asset.getFilePath()).getParent();
                String finalTexPath = parentPath + "\\" + texPath;
                if ((new File(finalTexPath)).exists()) {
                    value = AssetManager.loadTexture(finalTexPath);
                } else if ((new File(texPath)).exists()) {
                    // absolute path
                    value = AssetManager.loadTexture(texPath);
                } else {
                    value = null;
                }
            }

            lastMaterial.setValue(name, value);
        } else if (qName.equals(ApxExporter.TOKEN_TRANSLATION)) {
            float x = Float.parseFloat(attributes.getValue("x"));
            float y = Float.parseFloat(attributes.getValue("y"));
            float z = Float.parseFloat(attributes.getValue("z"));
            Vector3f vec = new Vector3f(x, y, z);
            GameObject go = null;
            if (node)
                go = nodes.get(nodes.size() - 1);
            else if (geom)
                go = geoms.get(geoms.size() - 1);

            go.setLocalTranslation(vec);
        } else if (qName.equals(ApxExporter.TOKEN_ROTATION)) {
            float x = Float.parseFloat(attributes.getValue("x"));
            float y = Float.parseFloat(attributes.getValue("y"));
            float z = Float.parseFloat(attributes.getValue("z"));
            float w = Float.parseFloat(attributes.getValue("w"));
            Quaternion quat = new Quaternion(x, y, z, w);
            GameObject go = null;
            if (node)
                go = nodes.get(nodes.size() - 1);
            else if (geom)
                go = geoms.get(geoms.size() - 1);

            go.setLocalRotation(quat);
        } else if (qName.equals(ApxExporter.TOKEN_SCALE)) {
            float x = Float.parseFloat(attributes.getValue("x"));
            float y = Float.parseFloat(attributes.getValue("y"));
            float z = Float.parseFloat(attributes.getValue("z"));
            Vector3f vec = new Vector3f(x, y, z);
            GameObject go = null;
            if (node)
                go = nodes.get(nodes.size() - 1);
            else if (geom)
                go = geoms.get(geoms.size() - 1);

            go.setLocalScale(vec);
        } else if (qName.equals(ApxExporter.TOKEN_MESH)) {
            meshes.add(new Mesh());
        } else if (qName.equals(ApxExporter.TOKEN_VERTICES)) {
            ArrayList<Vertex> newVList = new ArrayList<Vertex>();
            vertices.add(newVList);
            ArrayList<Vector3f> newPList = new ArrayList<Vector3f>();
            positions.add(newPList);
            ArrayList<Vector3f> newNList = new ArrayList<Vector3f>();
            normals.add(newNList);
            ArrayList<Vector2f> newT0List = new ArrayList<Vector2f>();
            texcoords0.add(newT0List);
            ArrayList<Vector2f> newT1List = new ArrayList<Vector2f>();
            texcoords1.add(newT1List);
        } else if (qName.equals(ApxExporter.TOKEN_POSITION)) {
            ArrayList<Vector3f> pos = positions.get(positions.size() - 1);
            float x = Float.parseFloat(attributes.getValue("x"));
            float y = Float.parseFloat(attributes.getValue("y"));
            float z = Float.parseFloat(attributes.getValue("z"));
            Vector3f position = new Vector3f(x, y, z);
            pos.add(position);
        } else if (qName.equals(ApxExporter.TOKEN_NORMAL)) {
            ArrayList<Vector3f> nor = normals.get(normals.size() - 1);
            float x = Float.parseFloat(attributes.getValue("x"));
            float y = Float.parseFloat(attributes.getValue("y"));
            float z = Float.parseFloat(attributes.getValue("z"));
            Vector3f normal = new Vector3f(x, y, z);
            nor.add(normal);
        } else if (qName.equals(ApxExporter.TOKEN_TEXCOORD0)) {
            ArrayList<Vector2f> tc0 = texcoords0.get(texcoords0.size() - 1);
            float x = Float.parseFloat(attributes.getValue("x"));
            float y = Float.parseFloat(attributes.getValue("y"));
            Vector2f tc = new Vector2f(x, y);
            tc0.add(tc);
        } else if (qName.equals(ApxExporter.TOKEN_TEXCOORD1)) {
            ArrayList<Vector2f> tc1 = texcoords1.get(texcoords1.size() - 1);
            float x = Float.parseFloat(attributes.getValue("x"));
            float y = Float.parseFloat(attributes.getValue("y"));
            Vector2f tc = new Vector2f(x, y);
            tc1.add(tc);
        } else if (qName.equals(ApxExporter.TOKEN_FACES)) {
            faces.add(new ArrayList<Integer>());
        } else if (qName.equals(ApxExporter.TOKEN_FACE)) {
            ArrayList<Integer> fList = faces.get(faces.size() - 1);
            for (int i = 0; i < 3; i++) {
                String val = attributes.getValue("i" + String.valueOf(i));
                if (!val.equals("")) {
                    String[] tokens = val.split("/");
                    for (int j = 0; j < tokens.length; j++) {
                        fList.add(Integer.valueOf(tokens[j]));
                    }
                }

            }
        } else if (qName.equals(ApxExporter.TOKEN_SKELETON)) {
            skeletons.add(new Skeleton());
            bones.add(new ArrayList<Bone>());
        } else if (qName.equals(ApxExporter.TOKEN_SKELETON_ASSIGN)) {
            String assign = attributes.getValue(ApxExporter.TOKEN_ID);
            skeletonAssigns.add(Integer.valueOf(assign));
        } else if (qName.equals(ApxExporter.TOKEN_BONE)) {
            String name = attributes.getValue(ApxExporter.TOKEN_NAME);
            String parent = attributes.getValue(ApxExporter.TOKEN_PARENT);
            Bone bone = new Bone(name);
            ArrayList<Bone> lastBL = bones.get(bones.size() - 1);
            if (parent != null && !parent.equals("")) {
                for (Bone b : lastBL) {
                    if (b.getName().equals(parent)) {
                        b.addChild(bone);
                    }

                }
            }

            ArrayList<Bone> skel = bones.get(bones.size() - 1);
            skel.add(bone);
            Skeleton lastSkeleton = skeletons.get(skeletons.size() - 1);
            lastSkeleton.addBone(bone);
        } else if (qName.equals(ApxExporter.TOKEN_BONE_BINDPOSITION)) {
            float x = Float.parseFloat(attributes.getValue("x"));
            float y = Float.parseFloat(attributes.getValue("y"));
            float z = Float.parseFloat(attributes.getValue("z"));
            Vector3f vec = new Vector3f(x, y, z);
            ArrayList<Bone> skel = bones.get(bones.size() - 1);
            if (skel.size() > 0) {
                Bone lastBone = skel.get(skel.size() - 1);
                lastBone.setBindTranslation(vec);
            }

        } else if (qName.equals(ApxExporter.TOKEN_BONE_BINDROTATION)) {
            float x = Float.parseFloat(attributes.getValue("x"));
            float y = Float.parseFloat(attributes.getValue("y"));
            float z = Float.parseFloat(attributes.getValue("z"));
            float w = Float.parseFloat(attributes.getValue("w"));
            ArrayList<Bone> skel = bones.get(bones.size() - 1);
            if (skel.size() > 0) {
                Bone lastBone = skel.get(skel.size() - 1);
                // lastBone.SetBindAxisAngle(new Vector3f(x, y, z), w);
                lastBone.setBindRotation(new Quaternion(x, y, z, w));
            }

        } else if (qName.equals(ApxExporter.TOKEN_BONE_ASSIGNS)) {
            boneAssigns.add(new ArrayList<BoneAssign>());
        } else if (qName.equals(ApxExporter.TOKEN_BONE_ASSIGN)) {
            int vertIdx = Integer.valueOf(attributes.getValue(ApxExporter.TOKEN_VERTEXINDEX));
            int boneIdx = Integer.valueOf(attributes.getValue(ApxExporter.TOKEN_BONEINDEX));
            float boneWeight = Float.parseFloat(attributes.getValue(ApxExporter.TOKEN_BONEWEIGHT));
            ArrayList<BoneAssign> ba = boneAssigns.get(boneAssigns.size() - 1);
            ba.add(new BoneAssign(vertIdx, boneWeight, boneIdx));
        } else if (qName.equals(ApxExporter.TOKEN_ANIMATIONS)) {
            hasAnimations = true;
        } else if (qName.equals(ApxExporter.TOKEN_ANIMATION)) {
            String name = attributes.getValue(ApxExporter.TOKEN_NAME);
            Animation anim = new Animation(name);
            animations.add(anim);
        } else if (qName.equals(ApxExporter.TOKEN_ANIMATION_TRACK)) {
            String bone = attributes.getValue(ApxExporter.TOKEN_BONE);
            Bone b = skeletons.get(skeletons.size() - 1).getBone(bone);
            if (b != null) {
                AnimationTrack track = new AnimationTrack(b);
                animations.get(animations.size() - 1).addTrack(track);
            }

        } else if (qName.equals(ApxExporter.TOKEN_KEYFRAME)) {
            float time = Float.parseFloat(attributes.getValue(ApxExporter.TOKEN_TIME));
            Keyframe frame = new Keyframe(time, null, null);
            Animation canim = animations.get(animations.size() - 1);
            AnimationTrack ctrack = canim.getTrack(canim.getTracks().size() - 1);
            ctrack.addKeyframe(frame);
        } else if (qName.equals(ApxExporter.TOKEN_KEYFRAME_TRANSLATION)) {
            Animation canim = animations.get(animations.size() - 1);
            AnimationTrack ctrack = canim.getTrack(canim.getTracks().size() - 1);
            Keyframe lastFrame = ctrack.frames.get(ctrack.frames.size() - 1);
            float x = Float.parseFloat(attributes.getValue("x"));
            float y = Float.parseFloat(attributes.getValue("y"));
            float z = Float.parseFloat(attributes.getValue("z"));
            Vector3f vec = new Vector3f(x, y, z);
            lastFrame.setTranslation(vec);
        } else if (qName.equals(ApxExporter.TOKEN_KEYFRAME_ROTATION)) {
            Animation canim = animations.get(animations.size() - 1);
            AnimationTrack ctrack = canim.getTrack(canim.getTracks().size() - 1);
            Keyframe lastFrame = ctrack.frames.get(ctrack.frames.size() - 1);
            float x = Float.parseFloat(attributes.getValue("x"));
            float y = Float.parseFloat(attributes.getValue("y"));
            float z = Float.parseFloat(attributes.getValue("z"));
            float w = Float.parseFloat(attributes.getValue("w"));
            Quaternion rot = new Quaternion(x, y, z, w);
            lastFrame.setRotation(rot);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals(ApxExporter.TOKEN_NODE)) {
            if (lastNode != null) {
                if (lastNode.getParent() != null) {
                    lastNode = lastNode.getParent();
                } else {
                    lastNode = null;
                }
            }

            node = false;
        } else if (qName.equals(ApxExporter.TOKEN_GEOMETRY)) {
            geom = false;
        } else if (qName.equals(ApxExporter.TOKEN_MATERIAL)) {
            if (geoms.size() > 0) {
                int lastGeomIndex = geoms.size() - 1;
                Geometry parent = geoms.get(lastGeomIndex);
                int lastMatIndex = mats.size() - 1;
                Material m = mats.get(lastMatIndex);
                geomMats.put(parent, m);
            }

        } else if (qName.equals(ApxExporter.TOKEN_SKELETON)) {
        } else if (qName.equals(ApxExporter.TOKEN_MODEL)) {
            // end of model, load in meshes
            endModel();
        }

    }

}
