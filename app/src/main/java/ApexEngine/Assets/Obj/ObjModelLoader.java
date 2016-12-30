package ApexEngine.Assets.Obj;

import ApexEngine.Assets.AssetLoader;
import ApexEngine.Assets.AssetManager;
import ApexEngine.Assets.LoadedAsset;
import ApexEngine.Assets.Obj.MtlAssetLoader;
import ApexEngine.Assets.Obj.ObjIndex;
import ApexEngine.Assets.Obj.ObjModelLoader;
import ApexEngine.Math.Vector2f;
import ApexEngine.Math.Vector3f;
import ApexEngine.Rendering.Material;
import ApexEngine.Rendering.Mesh;
import ApexEngine.Rendering.Vertex;
import ApexEngine.Scene.Geometry;
import ApexEngine.Scene.Node;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ObjModelLoader extends AssetLoader {
	private static ObjModelLoader instance = new ObjModelLoader();

	public static ObjModelLoader getInstance() {
		return instance;
	}

	protected ArrayList<String> names = new ArrayList<String>();
	protected ArrayList<String> namesMtl = new ArrayList<String>();
	protected ArrayList<ArrayList<ObjIndex>> objIndices = new ArrayList<ArrayList<ObjIndex>>();
	protected ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
	protected ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
	protected ArrayList<Vector2f> texCoords = new ArrayList<Vector2f>();
	protected ArrayList<Material> materials = new ArrayList<Material>();
	protected ArrayList<Material> mtlOrder = new ArrayList<Material>();
	protected boolean hasTexCoords = false, hasNormals = false;

	public void resetLoader() {
		objIndices.clear();
		hasTexCoords = false;
		hasNormals = false;
		texCoords.clear();
		positions.clear();
		normals.clear();
		names.clear();
		namesMtl.clear();
		materials.clear();
		mtlOrder.clear();
	}

	private ArrayList<ObjIndex> currentList() {
		if (objIndices.size() == 0)
			newMesh("child_0");

		return objIndices.get(objIndices.size() - 1);
	}

	public ObjModelLoader() {
		super("obj");
	}

	private void newMesh(String name) {
		int counter = 0;
		String nm = name;
		while (names.contains(nm)) {
			counter++;
			nm = name + "_" + String.valueOf(counter);
		}
		objIndices.add(new ArrayList<ObjIndex>());
		names.add((counter == 0 ? name : name + "_" + String.valueOf(counter)));
		namesMtl.add(name);
	}

	private ObjIndex parseObjIndex(String token) {
		String[] values = token.split("/");
		ObjIndex res = new ObjIndex();
		if (values.length > 0) {
			res.vertexIdx = Integer.valueOf(values[0]) - 1;
			if (values.length > 1) {
				if (!values[1].equals("")) {
					hasTexCoords = true;
					res.texCoordIdx = Integer.valueOf(values[1]) - 1;
				}

				if (values.length > 2) {
					hasNormals = true;
					res.normalIdx = Integer.valueOf(values[2]) - 1;
				}

			}

		}

		return res;
	}

	public static String[] removeEmptyStrings(String[] data) {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < data.length; i++) {
			if (!data[i].equals("")) {
				result.add(data[i]);
			}
		}
		String[] res = new String[result.size()];
		for(int i = 0; i < result.size(); i++) {
			res[i] = result.get(i);
		}
		return res;
	}

	public Material materialWithName(String name) {
		for (Material m : materials) {
			if (m.getName().equals(name))
				return m;

		}
		return new Material().setName(name);
	}

	@Override
	public Object load(LoadedAsset asset) {
		Node node = new Node();
		String modelName = asset.getFilePath().substring(asset.getFilePath().lastIndexOf("."));
		node.setName(modelName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(asset.getData()));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(" ");
				tokens = removeEmptyStrings(tokens);
				if (tokens.length == 0 || tokens[0].equals("#")) {
				} else if (tokens[0].equals("v")) {
					positions.add(new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
							Float.parseFloat(tokens[3])));
				} else if (tokens[0].equals("vn")) {
					normals.add(new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
							Float.parseFloat(tokens[3])));
				} else if (tokens[0].equals("vt")) {
					texCoords.add(new Vector2f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])));
				} else if (tokens[0].equals("f")) {
					ArrayList<ObjIndex> idx = currentList();
					for (int i = 0; i < tokens.length - 3; i++) {
						idx.add(parseObjIndex(tokens[1]));
						idx.add(parseObjIndex(tokens[2 + i]));
						idx.add(parseObjIndex(tokens[3 + i]));
					}
				} else if (tokens[0].equals("mtllib")) {
					String libLoc = tokens[1];
					String parentPath = new File(asset.getFilePath()).getParent();
					String mtlPath = parentPath + "\\" + libLoc;
					materials = (ArrayList<Material>) AssetManager.load(mtlPath, MtlAssetLoader.getInstance());
				} else if (tokens[0].equals("usemtl")) {
					String matName = tokens[1];
					// Material material = MaterialWithName(matName);
					// mtlOrder.Add(material);
					newMesh(matName);
				} else if (tokens[0].equals("g")) {
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < objIndices.size(); i++) {
			ArrayList<ObjIndex> c_idx = objIndices.get(i);
			ArrayList<Vertex> vertices = new ArrayList<Vertex>();
			for (int j = 0; j < c_idx.size(); j++) {
				Vertex vert = new Vertex(positions.get(c_idx.get(j).vertexIdx),
						hasTexCoords ? texCoords.get(c_idx.get(j).texCoordIdx) : null,
						hasNormals ? normals.get(c_idx.get(j).normalIdx) : null);
				vertices.add(vert);
			}
			Mesh mesh = new Mesh();
			mesh.setVertices(vertices);
			Geometry geom = new Geometry();
			geom.setName(names.get(i));
			geom.setMesh(mesh);
			geom.setMaterial(materialWithName(namesMtl.get(i)));
			node.addChild(geom);
		}
		resetLoader();
		return node;
	}

}
