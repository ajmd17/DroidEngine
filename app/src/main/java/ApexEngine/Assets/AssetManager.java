package ApexEngine.Assets;

import ApexEngine.Assets.AssetLoader;
import ApexEngine.Assets.Apx.ApxModelLoader;
import ApexEngine.Assets.Obj.ObjModelLoader;
import ApexEngine.Rendering.Texture;
import ApexEngine.Scene.GameObject;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class AssetManager {
	public static Context context;
	private static HashMap<String, AssetLoader> loaders = new HashMap<String, AssetLoader>();

	public static void initDefaultLoaders() {
		// register the default asset loaders
		// create a static instance of each loader

		ObjModelLoader.getInstance();
		ApxModelLoader.getInstance();
		ShaderTextLoader.getInstance();
		TextLoader.getInstance();
		TextureLoader.getInstance();
	}

	public static String getAppPath() {
		return "./";
	}

	public static AssetLoader getDefaultLoader(String ext) {
		if (loaders.containsKey(ext)) {
			return loaders.get(ext);
		} else if (loaders.containsKey("." + ext)) {
			return loaders.get("." + ext);
		}

		if (ext.startsWith(".")) {
			if (loaders.containsKey(ext.substring(1)))
				return loaders.get(ext.substring(1));

		}

		return null;
	}

	public static void registerLoader(String ext, AssetLoader loader) {
		if (!loaders.containsKey(ext)) {
			loaders.put(ext, loader);
		} else {
			loaders.put(ext, loader);
		}
	}

	public static Object load(String filePath, AssetLoader loader) {
		InputStream stream = null;

		try {
			// try to load from Android assets
			stream = context.getAssets().open(filePath);
		} catch (IOException e1) {
			// try to load from Jar
			try {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				stream = classLoader.getResourceAsStream(filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		LoadedAsset asset = new LoadedAsset(stream, filePath);

		Object loaded = loader.load(asset);

		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return loaded;
	}

	public static Object load(String filePath) {
		String ext = filePath.substring(filePath.lastIndexOf("."));
		AssetLoader loader = getDefaultLoader(ext);
		return load(filePath, loader);
	}

	public static GameObject loadModel(String filePath) {
		Object loadedObject = load(filePath);
		if (loadedObject == null)
			return null;

		if (loadedObject instanceof GameObject)
			return (GameObject) loadedObject;

		return null;
	}

	public static Texture loadTexture(String filePath) {
		return (Texture)load(filePath,TextureLoader.getInstance());
	}

	public static String loadString(String filePath) {
		return (String) load(filePath, TextLoader.getInstance());
	}

	public static String loadResourceTextFile(String resourcePath) {
		return "";
	}

}
