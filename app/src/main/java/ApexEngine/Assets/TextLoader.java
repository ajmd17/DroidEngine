package ApexEngine.Assets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextLoader extends AssetLoader {

	private static TextLoader instance;
	public static TextLoader getInstance()
	{
		if (instance == null)
			instance = new TextLoader();
		return instance;
	}
	
	public TextLoader() {
		super("txt");
	}
	
	@Override
	public Object load(LoadedAsset asset) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(asset.getData()));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}

	@Override
	public void resetLoader() {
		// TODO Auto-generated method stub
		
	}

}
