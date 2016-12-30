package ApexEngine.Assets.Obj;

import ApexEngine.Assets.AssetLoader;
import ApexEngine.Assets.AssetManager;
import ApexEngine.Assets.LoadedAsset;
import ApexEngine.Assets.Obj.MtlAssetLoader;
import ApexEngine.Math.Vector4f;
import ApexEngine.Rendering.Material;
import ApexEngine.Rendering.Texture;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MtlAssetLoader extends AssetLoader {
	private static MtlAssetLoader instance = new MtlAssetLoader();

	public static MtlAssetLoader getInstance() throws Exception {
		return instance;
	}

	private ArrayList<Material> materials = new ArrayList<Material>();
	private ArrayList<String> matNames = new ArrayList<String>();

	public MtlAssetLoader() {
		super("mtl");
	}

	public static String[] removeEmptyStrings(String[] data) {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < data.length; i++) {
			if (!data[i].equals("")) {
				result.add(data[i]);
			}

		}
		String[] res = ((String[]) result.toArray());
		return res;
	}

	@Override
	public Object load(LoadedAsset asset) {
		if (!(new File(asset.getFilePath())).exists()) {
			return materials;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(asset.getData()));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(" ");
				tokens = removeEmptyStrings(tokens);
				if (tokens.length == 0 || tokens[0].equals("#")) {
				} else if (tokens[0].equals("newmtl")) {
					String name = tokens[1];
					materials.add(new Material());
					materials.get(materials.size() - 1).setName(name);
				} else if (tokens[0].equals("Ka")) {
					// ambient color
					String x_str = tokens[1];
					String y_str = tokens[2];
					String z_str = tokens[3];
					float x, y, z;
					x = Float.parseFloat(x_str);
					y = Float.parseFloat(y_str);
					z = Float.parseFloat(z_str);
					Vector4f color = new Vector4f(x, y, z, 1.0f);
				} else // materials[materials.Count -
						// 1].SetValue(Material.COLOR_AMBIENT, color);
				if (tokens[0].equals("Kd")) {
					// ambient color
					String x_str = tokens[1];
					String y_str = tokens[2];
					String z_str = tokens[3];
					float x, y, z;
					x = Float.parseFloat(x_str);
					y = Float.parseFloat(y_str);
					z = Float.parseFloat(z_str);
					Vector4f color = new Vector4f(x, y, z, 1.0f);
					materials.get(materials.size() - 1).setValue(Material.COLOR_DIFFUSE, color);
				} else if (tokens[0].equals("Ks")) {
					// ambient color
					String x_str = tokens[1];
					String y_str = tokens[2];
					String z_str = tokens[3];
					float x, y, z;
					x = Float.parseFloat(x_str);
					y = Float.parseFloat(y_str);
					z = Float.parseFloat(z_str);
					Vector4f color = new Vector4f(x, y, z, 1.0f);
					materials.get(materials.size() - 1).setValue(Material.COLOR_SPECULAR, color);
				} else if (tokens[0].equals("Ns")) {
					// ambient color
					String spec = tokens[1];
					float spec_f = Float.parseFloat(spec);
					materials.get(materials.size() - 1).setValue(Material.SHININESS, spec_f / 1000.0f);
				} else if (tokens[0].toLowerCase().equals("map_kd")) {
					// diffuse map
					String texName = tokens[tokens.length - 1];
					String parentPath = new File(asset.getFilePath()).getParent();
					String texPath = parentPath + "\\" + texName;
					if ((new File(texPath)).exists()) {
						Texture tex = AssetManager.loadTexture(texPath);
						materials.get(materials.size() - 1).setValue(Material.TEXTURE_DIFFUSE, tex);
					} else if ((new File(texName)).exists()) {
						Texture tex = AssetManager.loadTexture(texName);
						materials.get(materials.size() - 1).setValue(Material.TEXTURE_DIFFUSE, tex);
					}

				} else if (tokens[0].toLowerCase().equals("map_bump")) {
					// normal map
					String texName = tokens[tokens.length - 1];
					String parentPath = new File(asset.getFilePath()).getParent();
					String texPath = parentPath + "\\" + texName;
					if ((new File(texPath)).exists()) {
						Texture tex = AssetManager.loadTexture(texPath);
						materials.get(materials.size() - 1).setValue(Material.TEXTURE_NORMAL, tex);
					} else if ((new File(texName)).exists()) {
						Texture tex = AssetManager.loadTexture(texName);
						materials.get(materials.size() - 1).setValue(Material.TEXTURE_NORMAL, tex);
					}

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
		return materials;
	}

	public void resetLoader() {
		materials.clear();
		matNames.clear();
	}

}
