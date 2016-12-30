package ApexEngine.Rendering.Animation;

import java.util.ArrayList;

import ApexEngine.Math.Matrix4f;
import ApexEngine.Rendering.Camera;
import ApexEngine.Rendering.Environment;
import ApexEngine.Rendering.Mesh;
import ApexEngine.Rendering.Shader;
import ApexEngine.Rendering.ShaderProperties;

public abstract class AnimatedShader extends Shader {
	private ArrayList<String> boneNames = new ArrayList<String>();
	private boolean isSkinningInit = false;

	public AnimatedShader(ShaderProperties properties, String vs_code, String fs_code) {
		super(properties, vs_code, fs_code);
	}

	public AnimatedShader(ShaderProperties properties, String vs_code, String fs_code, String gs_code) {
		super(properties, vs_code, fs_code, gs_code);
	}

	private void initSkinning(Mesh mesh) {
		for (int i = 0; i < properties.getInt("NUM_BONES"); i++) {
			boneNames.add("Bone[" + i + "]");
		}
	}

	private void updateSkinning(Mesh mesh) {
		for (int i = 0; i < boneNames.size(); i++) {
			Matrix4f boneMat = mesh.getSkeleton().getBone(i).getBoneMatrix();
			setUniform(boneNames.get(i), boneMat);
		}
	}

	public void update(Environment environment, Camera cam, Mesh mesh) {
		super.update(environment, cam, mesh);
		if (mesh.getSkeleton() != null) {
			if (!isSkinningInit) {
				initSkinning(mesh);
				isSkinningInit = true;
			}

			updateSkinning(mesh);
		}

	}

}
