#ifdef DEFAULT

#include <material>
#include <lighting>
#include <apex3d>

varying vec3 v_position;
varying vec3 v_normal;
varying vec2 v_texCoord0;

void main()
{
	vec4 diffuseTexture = vec4(1.0);
	vec2 texCoord = vec2(v_texCoord0.x, -v_texCoord0.y);
	
	if (Material_HasDiffuseMap == 1) 
	{
		diffuseTexture = texture2D(Material_DiffuseMap, texCoord);
		diffuseTexture.rgb = pow(diffuseTexture.rgb, vec3(2.2));
	}
	
	float specular = 0.0;
	float ndotl = 1.0;
	
	if (Material_PerPixelLighting == 1)
	{
		vec3 n = normalize(v_normal.xyz);
		vec3 v = normalize(Apex_CameraPosition - v_position.xyz);
		vec3 l = normalize(Env_DirectionalLight.direction);
		vec3 h = normalize(v+l);
		
		ndotl = LambertDirectional(n, l);
		
		specular = pow(max(dot(n, h), 0.0), Material_SpecularExponent);
		
	}
	
	diffuseTexture *= Material_DiffuseColor;
	
	diffuseTexture *= ndotl;
	diffuseTexture += vec3(specular);
	
	gl_FragColor = diffuseTexture;
	gl_FragColor.rgb = pow(gl_FragColor.rgb, vec3(1.0/2.2));
}

#endif