package ApexEngine.AndroidRenderer;

import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import ApexEngine.Game;
import ApexEngine.Assets.LoadedAsset;
import ApexEngine.Rendering.Mesh;
import ApexEngine.Rendering.Renderer;
import ApexEngine.Rendering.Shader;
import ApexEngine.Rendering.Shader.ShaderTypes;
import ApexEngine.Rendering.Texture.WrapMode;
import ApexEngine.Rendering.Util.MeshUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import ApexEngine.Rendering.Texture2D;
import ApexEngine.Rendering.VertexAttributes;

public class GLESRenderer extends Renderer {
	private FloatBuffer tmpMatBuffer;
	
	@Override
	public void createContext(Game game, int width, int height) {
		game.initInternal();
	}
	
    public static IntBuffer createFlippedBuffer(int... values) {
        IntBuffer buffer = ByteBuffer.allocateDirect(values.length*4).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(values);
        buffer.flip();
        return buffer;
    }
	
	public static FloatBuffer createFlippedBuffer(float[] vertices) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(vertices.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        for (int i = 0; i < vertices.length; i++) {
        	buffer.put(vertices[i]);
        }
        buffer.flip();
        return buffer;
    }

	@Override
	public void createMesh(Mesh mesh) {
		int buffers[] = new int[2];
		glGenBuffers(2, buffers, 0);
		mesh.vbo = buffers[0];
		mesh.ibo = buffers[1];
	}

	@Override
	public void uploadMesh(Mesh mesh) {
		mesh.size = mesh.indices.size();
	    
		
		float[] vertices = MeshUtil.createFloatBuffer(mesh);
	    FloatBuffer vertexFloatBuffer = createFlippedBuffer(vertices);
	    
	    int[] indexBuffer = new int[mesh.indices.size()];
	    for (int i = 0; i < mesh.indices.size(); i++)
	    {
	        indexBuffer[i] = mesh.indices.get(i);
	    }
	    IntBuffer intBuffer = createFlippedBuffer(indexBuffer);
	    
	    
	    glBindBuffer(GL_ARRAY_BUFFER, mesh.vbo);
	    glBufferData(GL_ARRAY_BUFFER, vertices.length * 4, vertexFloatBuffer, GL_STATIC_DRAW);
	    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.ibo);
	    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer.length * 4, intBuffer, GL_STATIC_DRAW);
	}
	
	@Override
	public void deleteMesh(Mesh mesh) {
		int[] vbo = new int[] { mesh.vbo }, 
			  ibo = new int[] { mesh.ibo };
		glDeleteBuffers(1, vbo, 0);
		glDeleteBuffers(1, ibo, 0);
		vbo = null;
		ibo = null;
	}

	@Override
	public void renderMesh(Mesh mesh) {
		 int primitiveType = GL_TRIANGLES;
         if (mesh.getPrimitiveType() == Mesh.PrimitiveTypes.Triangles)
             primitiveType = GL_TRIANGLES;
         else if (mesh.getPrimitiveType() == Mesh.PrimitiveTypes.TriangleStrip)
             primitiveType = GL_TRIANGLE_STRIP;
         else if (mesh.getPrimitiveType() == Mesh.PrimitiveTypes.TriangleFan)
             primitiveType = GL_TRIANGLE_FAN;
         else if (mesh.getPrimitiveType() == Mesh.PrimitiveTypes.Points)
             primitiveType = GL_POINTS;
         else if (mesh.getPrimitiveType() == Mesh.PrimitiveTypes.Lines)
             primitiveType = GL_LINES;
         
         glBindBuffer(GL_ARRAY_BUFFER, mesh.vbo);
         
         if (mesh.getAttributes().hasAttribute(VertexAttributes.POSITIONS)) {
             glEnableVertexAttribArray(0);
             glVertexAttribPointer(0, 3, GL_FLOAT, false, mesh.vertexSize * 4, mesh.getAttributes().getPositionOffset());
         }
         if (mesh.getAttributes().hasAttribute(VertexAttributes.TEXCOORDS0)) {
             glEnableVertexAttribArray(1);
             glVertexAttribPointer(1, 2, GL_FLOAT, false, mesh.vertexSize * 4, mesh.getAttributes().getTexCoord0Offset());
         }
         if (mesh.getAttributes().hasAttribute(VertexAttributes.TEXCOORDS1)) {
             glEnableVertexAttribArray(2);
             glVertexAttribPointer(2, 2, GL_FLOAT, false, mesh.vertexSize * 4, mesh.getAttributes().getTexCoord1Offset());
         }
         if (mesh.getAttributes().hasAttribute(VertexAttributes.NORMALS)) {
             glEnableVertexAttribArray(3);
             glVertexAttribPointer(3, 3, GL_FLOAT, false, mesh.vertexSize * 4, mesh.getAttributes().getNormalOffset());
         }
         if (mesh.getAttributes().hasAttribute(VertexAttributes.TANGENTS)) {
             glEnableVertexAttribArray(4);
             glVertexAttribPointer(4, 3, GL_FLOAT, false, mesh.vertexSize * 4, mesh.getAttributes().getTangentOffset());
         }
         if (mesh.getAttributes().hasAttribute(VertexAttributes.BITANGENTS)) {
             glEnableVertexAttribArray(5);
             glVertexAttribPointer(5, 3, GL_FLOAT, false, mesh.vertexSize * 4, mesh.getAttributes().getBitangentOffset());
         }
         if (mesh.getAttributes().hasAttribute(VertexAttributes.BONEWEIGHTS)) {
             glEnableVertexAttribArray(6);
             glVertexAttribPointer(6, 4, GL_FLOAT, false, mesh.vertexSize * 4, mesh.getAttributes().getBoneWeightOffset());
         }
         if (mesh.getAttributes().hasAttribute(VertexAttributes.BONEINDICES)) {
             glEnableVertexAttribArray(7);
             glVertexAttribPointer(7, 4, GL_FLOAT, false, mesh.vertexSize * 4, mesh.getAttributes().getBoneIndexOffset());
         }
         
         glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.ibo);
         glDrawElements(primitiveType, mesh.size, GL_UNSIGNED_INT, 0);
         
         glDisableVertexAttribArray(0);
         glDisableVertexAttribArray(1);
         glDisableVertexAttribArray(2);
         glDisableVertexAttribArray(3);
         glDisableVertexAttribArray(4);
         glDisableVertexAttribArray(5);
         glDisableVertexAttribArray(6);
         glDisableVertexAttribArray(7);
	}

	@Override
	public Texture2D loadTexture2D(LoadedAsset asset) {
		
		int _id[] = new int[1];
		int id;
		
		glGenTextures(1, _id, 0);
		
		id = _id[0];
		
		Texture2D res = new Texture2D(id);
		res.use();
		
		Bitmap bm = BitmapFactory.decodeStream(asset.getData());
		res.setWidth(bm.getWidth());
		res.setHeight(bm.getHeight());
		
    	glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
    	glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
    	GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bm, 0);
    	
    	glGenerateMipmap(GLES20.GL_TEXTURE_2D);
    	
    	Texture2D.clear();
		
		return res;
	}

	@Override
	public int genTexture() {
		int[] tex = new int[1];
		glGenTextures(1, tex, 0);
		return tex[0];
	}

	@Override
	public void genTextures(int n, int textures) {
		int[] tex = new int[1];
		glGenTextures(n, tex, 0);
		textures = tex[0];
	}

	@Override
	public void bindTexture2D(int i) {
		glBindTexture(GL_TEXTURE_2D, i);
	}

	@Override
	public void bindTexture3D(int i) {
		/* Not supported on OpenGL ES 2.0 */
	}

	@Override
	public void bindTextureCubemap(int i) {
		glBindTexture(GL_TEXTURE_CUBE_MAP, i);
	}

	@Override
	public void textureWrap2D(WrapMode s, WrapMode t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void textureWrapCube(WrapMode r, WrapMode s, WrapMode t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void textureFilter2D(int min, int mag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateMipmap2D() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateMipmapCubemap() {
	}

	@Override
	public void activeTextureSlot(int slot) {
		glActiveTexture(GL_TEXTURE0 + slot);
	}

	@Override
	public int generateShaderProgram() {
		 int id;
         id = glCreateProgram();
         if (id == 0) {
             try {
				throw new Exception("An error occurred while creating the shader!");
			} catch (Exception e) {
				e.printStackTrace();
			}
         }
         return id;
	}

	@Override
	public void bindShaderProgram(int id) {
		glUseProgram(id);
	}

	@Override
	public void compileShaderProgram(int id) {
		glBindAttribLocation(id, 0, Shader.A_POSITION);
		glBindAttribLocation(id, 1, Shader.A_TEXCOORD0);
		glBindAttribLocation(id, 2, Shader.A_TEXCOORD1);
		glBindAttribLocation(id, 3, Shader.A_NORMAL);
		glBindAttribLocation(id, 4, Shader.A_TANGENT);
		glBindAttribLocation(id, 5, Shader.A_BITANGENT);
		glBindAttribLocation(id, 6, Shader.A_BONEWEIGHT);
		glBindAttribLocation(id, 7, Shader.A_BONEINDEX);
		glLinkProgram(id);
		glValidateProgram(id);
	}

	@Override
	public void addShader(int id, String code, ShaderTypes type) {
		int stype = GL_VERTEX_SHADER;

        if (type == Shader.ShaderTypes.Vertex)
            stype = GL_VERTEX_SHADER;
        else if (type == Shader.ShaderTypes.Fragment)
            stype = GL_FRAGMENT_SHADER;

        int shader = glCreateShader(stype);
        if (shader == 0) {
            try {
				throw new Exception("Error creating shader.\n\tShader type: " + type + "\n\tCode: " + code);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        glShaderSource(shader, code);
        glCompileShader(shader);
        glAttachShader(id, shader);
        String info = "";
        info = glGetShaderInfoLog(shader);
        int[] status = new int[1]; 
        glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, status, 0);

        if (status[0] != 1) {
            try {
				throw new Exception("Shader compiler error!\nType: " + type.toString() +
				           info + "\n" + "Status Code: " + status);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}

	@Override
	public void setShaderUniform(int id, String name, int i) {
		int loc = glGetUniformLocation(id, name);
		glUniform1i(loc, i);
	}

	@Override
	public void setShaderUniform(int id, String name, float i) {
		int loc = glGetUniformLocation(id, name);
		glUniform1f(loc, i);
	}

	@Override
	public void setShaderUniform(int id, String name, float x, float y) {
		int loc = glGetUniformLocation(id, name);
		glUniform2f(loc, x, y);
	}

	@Override
	public void setShaderUniform(int id, String name, float x, float y, float z) {
		int loc = glGetUniformLocation(id, name);
		glUniform3f(loc, x, y, z);
	}

	@Override
	public void setShaderUniform(int id, String name, float x, float y, float z, float w) {
		int loc = glGetUniformLocation(id, name);
		glUniform4f(loc, x, y, z, w);
	}

	@Override
	public void setShaderUniform(int id, String name, float[] matrix) {
		if (tmpMatBuffer == null)
		{
			tmpMatBuffer = ByteBuffer.allocateDirect(matrix.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();

		}
		tmpMatBuffer.clear();
		for (int i = 0; i < matrix.length; i++)
		{
			tmpMatBuffer.put(matrix[i]);
		}
		tmpMatBuffer.flip();
		
		int loc = glGetUniformLocation(id, name);
		GLES20.glUniformMatrix4fv(loc, 1, false, tmpMatBuffer);
	}

	@Override
	public void genFramebuffers(int n, int framebuffers) {

	}

	@Override
	public int genFramebuffer() {
		int[] fb = new int[1];
		glGenFramebuffers(1, fb, 0);
		return fb[0];
	}

	@Override
	public void setupFramebuffer(int framebufferID, int colorTextureID, int depthTextureID, int width, int height) {
		bindTexture2D(colorTextureID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA4, width, height, 0, GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, colorTextureID, 0);

        glBindRenderbuffer(GL_RENDERBUFFER, depthTextureID);
        bindTexture2D(depthTextureID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT16, width, height, 0, GL_DEPTH_COMPONENT, GL_INT, (java.nio.ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthTextureID, 0);
	}

	@Override
	public void bindFramebuffer(int id) {
		glBindFramebuffer(GL_FRAMEBUFFER, id);
	}

	@Override
	public void setDepthTest(boolean depthTest) {
		if (depthTest)
			glEnable(GL_DEPTH_TEST);
		else
			glDisable(GL_DEPTH_TEST);
	}

	@Override
	public void setDepthMask(boolean depthMask) {
		glDepthMask(depthMask);
	}

	@Override
	public void setDepthClamp(boolean depthClamp) {
		/* Not supported in OpenGL ES 2.0 */
	}

	@Override
	public void setBlend(boolean blend) {
		if (blend)
			glEnable(GL_BLEND);
		else
			glDisable(GL_BLEND);
	}

	@Override
	public void setBlendMode(BlendMode blendMode) {
		if (blendMode == BlendMode.AlphaBlend) {
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}
	}

	@Override
	public void setCullFace(boolean cullFace) {
		if (cullFace)
			glEnable(GL_CULL_FACE);
		else
			glDisable(GL_CULL_FACE);
	}

	@Override
	public void setFaceToCull(Face face) {
		if (face == Face.Back) {
			glCullFace(GL_BACK);
		} else if (face == Face.Front) {
			glCullFace(GL_FRONT);
		}
	}

	@Override
	public void setFaceDirection(FaceDirection faceDirection) {
		if (faceDirection == FaceDirection.Ccw) {
			glFrontFace(GL_CCW);
		} else {
			glFrontFace(GL_CW);
		}
	}

	@Override
	public void viewport(int x, int y, int width, int height) {
		glViewport(x, y, width, height);
	}

	@Override
	public void clear(boolean clearColor, boolean clearDepth, boolean clearStencil) {
		if (clearColor && clearDepth && clearStencil)
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
        else if (clearColor && clearDepth)
        	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        else if (clearStencil && clearDepth)
        	glClear(GL_STENCIL_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        else if (clearColor && clearStencil)
        	glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
        else if (clearColor)
        	glClear(GL_COLOR_BUFFER_BIT);
        else if (clearDepth)
        	glClear(GL_DEPTH_BUFFER_BIT);
        else if (clearStencil)
        	glClear(GL_STENCIL_BUFFER_BIT);
	}

	@Override
	public void drawVertex(float x, float y) {
		/* Not supported on OpenGL ES 2.0 */
	}

	@Override
	public void drawVertex(float x, float y, float z) {
		/* Not supported on OpenGL ES 2.0 */
	}

	@Override
	public void clearColor(float r, float g, float b, float a) {
		glClearColor(r, g, b, a);
	}

	@Override
	public void copyScreenToTexture2D(int width, int height) {
	}

}
