package ApexEngine.Assets.Apx;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ApexEngine.Assets.AssetLoader;
import ApexEngine.Assets.LoadedAsset;

public class ApxModelLoader extends AssetLoader {
	private static ApxModelLoader instance = new ApxModelLoader();
    public static ApxModelLoader getInstance() {
        return instance;
    }
    
    ApxHandler handler;
    
    public ApxModelLoader() {
    	super("apx");
    }

	@Override
	public Object load(LoadedAsset asset) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser;
		try {
			saxParser = factory.newSAXParser();
			handler = new ApxHandler(asset);
			
			saxParser.parse(asset.getData(), handler);
			
			
			return handler.nodes.get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void resetLoader() {
		handler.nodes.clear();
		handler.geoms.clear();
		handler.meshes.clear();
		handler.skeletonAssigns.clear();
		handler.skeletons.clear();
		handler.bones.clear();
		handler.boneAssigns.clear();
		handler.animations.clear();
		handler.hasAnimations = false;
		handler.positions.clear();
		handler.normals.clear();
		handler.texcoords0.clear();
		handler.texcoords1.clear();
	    handler.vertices.clear();
	    handler.faces.clear();
	    handler.geomMats.clear();
	    handler.mats.clear();
	    handler.node = false;
	    handler.geom = false;
	    handler.lastNode = null;
	}

}
