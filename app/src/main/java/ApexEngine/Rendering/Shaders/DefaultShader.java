package ApexEngine.Rendering.Shaders;

import ApexEngine.Assets.AssetManager;
import ApexEngine.Assets.TextLoader;
import ApexEngine.Rendering.Camera;
import ApexEngine.Rendering.Environment;
import ApexEngine.Rendering.Material;
import ApexEngine.Rendering.Mesh;
import ApexEngine.Rendering.Renderer.BlendMode;
import ApexEngine.Rendering.Shader;
import ApexEngine.Rendering.RenderManager;
import ApexEngine.Rendering.ShaderProperties;
import ApexEngine.Rendering.Texture;
import ApexEngine.Rendering.Animation.AnimatedShader;

public class DefaultShader extends AnimatedShader {
	public DefaultShader(ShaderProperties properties) {
		super(properties, (String)AssetManager.load("shaders/default.vert"), (String)AssetManager.load("shaders/default.frag"));
	}

	public void applyMaterial(Material material) {
		super.applyMaterial(material);

		this.setUniform(MATERIAL_AMBIENTCOLOR, material.getVector4f(Material.COLOR_AMBIENT));
		this.setUniform(MATERIAL_DIFFUSECOLOR, material.getVector4f(Material.COLOR_DIFFUSE));
		this.setUniform(MATERIAL_SPECULARCOLOR, material.getVector4f(Material.COLOR_SPECULAR));
		this.setUniform(MATERIAL_SHININESS, material.getFloat(Material.SHININESS));
		this.setUniform(MATERIAL_ROUGHNESS, material.getFloat(Material.ROUGHNESS));
		this.setUniform(MATERIAL_METALNESS, material.getFloat(Material.METALNESS));
		this.setUniform(MATERIAL_SPECULARTECHNIQUE, material.getInt(Material.TECHNIQUE_SPECULAR));
		this.setUniform(MATERIAL_SPECULAREXPONENT, material.getFloat(Material.SPECULAR_EXPONENT));
		this.setUniform(MATERIAL_PERPIXELLIGHTING, material.getInt(Material.TECHNIQUE_PER_PIXEL_LIGHTING));
		int blendMode = material.getInt(Material.MATERIAL_BLENDMODE);
		if (blendMode == 1) {
			RenderManager.getRenderer().setBlend(true);
			RenderManager.getRenderer().setBlendMode(BlendMode.AlphaBlend);
		}

	}

	public void end() {
		super.end();
		RenderManager.getRenderer().setBlend(false);
	}

	public void update(Environment environment, Camera cam, Mesh mesh) {
		super.update(environment, cam, mesh);
		environment.getDirectionalLight().bindLight(0, this);
		// environment.getAmbientLight().bindLight(0, this);
		for (int i = 0; i < environment.getPointLights().size(); i++) {
			// environment.getPointLights().get(i).bindLight(i, this);
		}
		// setUniform(ENV_NUMPOINTLIGHTS, environment.getPointLights().size());
		// setUniform(ENV_FOGSTART, environment.getFogStart());
		// setUniform(ENV_FOGEND, environment.getFogEnd());
		// setUniform(ENV_FOGCOLOR, environment.getFogColor());
		if (currentMaterial != null) {
			Texture diffuseTex = currentMaterial.getTexture(Material.TEXTURE_DIFFUSE);
			if (diffuseTex != null) {
				Texture.activeTextureSlot(0);
				diffuseTex.use();
				setUniform("Material_DiffuseMap", 0);
				setUniform("Material_HasDiffuseMap", 1);
			} else {
				setUniform("Material_HasDiffuseMap", 0);
			}
			Texture normalTex = currentMaterial.getTexture(Material.TEXTURE_NORMAL);
			if (normalTex != null) {
				Texture.activeTextureSlot(1);
				normalTex.use();
				setUniform("Material_NormalMap", 1);
				setUniform("Material_HasNormalMap", 1);
			} else {
				setUniform("Material_HasNormalMap", 0);
			}
			Texture heightTex = currentMaterial.getTexture(Material.TEXTURE_HEIGHT);
			if (heightTex != null) {
				Texture.activeTextureSlot(2);
				heightTex.use();
				setUniform("Material_HeightMap", 2);
				setUniform("Material_HasHeightMap", 1);
			} else {
				setUniform("Material_HasHeightMap", 0);
			}
			Texture envTex = currentMaterial.getTexture(Material.TEXTURE_ENV);
			if (envTex != null) {
				Texture.activeTextureSlot(3);
				envTex.use();
				setUniform("Material_EnvironmentMap", 3);
				setUniform("Material_HasEnvironmentMap", 1);
			} else {
				setUniform("Material_HasEnvironmentMap", 0);
			}
		}

		if (environment.getShadowsEnabled()) {
			setUniform("Env_ShadowsEnabled", 1);
			for (int i = 0; i < 4; i++) {
				Texture.activeTextureSlot(3 + i);
				environment.getShadowMaps()[i].use();
				setUniform("Env_ShadowMap" + String.valueOf(i), 3 + i);
				setUniform("Env_ShadowMatrix" + String.valueOf(i), environment.getShadowMatrices()[i]);
				setUniform("Env_ShadowMapSplits[" + String.valueOf(i) + "]", environment.getShadowMapSplits()[i]);
			}
		}
	}

}
