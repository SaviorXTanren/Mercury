package com.radirius.mercury.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.lwjgl.opengl.GL11;

import com.radirius.mercury.math.geometry.Matrix4f;
import com.radirius.mercury.resource.Loader;
import com.radirius.mercury.resource.Resource;

/**
 * An object version of shaders. Does all of the tedious stuff for you and lets
 * you use the shader easily.
 * <p/>
 * Note that if you are using custom shader, you can not add more attributes.
 * You can only define uniforms, do so before calling the rendering operation.
 * Here is a list of locations of the attributes, make sure you don't break it
 * in the vertex shader.
 * <p/>
 * <table>
 * <tr>
 * <th>Location</th>
 * <th>Attribute</th>
 * </tr>
 * <tr>
 * <td>0</td>
 * <td><code>in vec2
 * position</code></td>
 * </tr>
 * <tr>
 * <td>1</td>
 * <td><code>in vec4 color</code></td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td><code>in
 * vec2 texCoords</code></td>
 * </tr>
 * </table>
 * <p/>
 * For most of the cases, the default shader is fine. Only add a custom shader
 * if you think that the default one isn't sufficient.
 *
 * @author wessles, opiop65, Jeviny, Sri Harsha Chilakapati
 */
public class Shader implements Resource {
	/**
	 * The vertex shader type.
	 */
	public static final int VERTEX_SHADER = 0;

	/**
	 * The fragment shader type.
	 */
	public static final int FRAGMENT_SHADER = 1;
	public static Shader DEFAULT_SHADER;
	private final int programObject;

	/**
	 * @param programObject The id for the program object you wish to
	 *        encapsulate.
	 */
	public Shader(int programObject) {
		this.programObject = programObject;
	}

	/**
	 * Staticly 'use().'
	 */
	public static void useShader(Shader shader) {
		shader.use();
	}

	/**
	 * Set the shader to default.
	 */
	public static void releaseShaders() {
		glUseProgram(DEFAULT_SHADER.programObject);
	}

	/**
	 * Returns A shader based off of the two program objects vert and frag.
	 */
	public static Shader getShader(int vert, int frag) {
		int program = glCreateProgram();

		if (program == 0)
			return null;

		glAttachShader(program, vert);
		glAttachShader(program, frag);

		glLinkProgram(program);

		if (glGetShaderi(vert, GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println(glGetShaderInfoLog(program, glGetShaderi(program, GL_INFO_LOG_LENGTH)));

			return null;
		}

		glValidateProgram(program);

		if (glGetShaderi(program, GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
			System.err.println(glGetShaderInfoLog(program, glGetShaderi(program, GL_INFO_LOG_LENGTH)));

			return null;
		}

		return new Shader(program);
	}

	/**
	 * @param vertexIn The vertex shader's file
	 * @param fragmentIn The fragment shader's file. Returns A shader based off
	 *        of the files in vin and fin.
	 */
	public static Shader getShader(InputStream vertexIn, InputStream fragmentIn) {
		return getShader(readShader(vertexIn), readShader(fragmentIn));
	}

	/**
	 * @param vertexSource The source of the vertex shader.
	 * @param fragmentSource The source of the fragment shader. Returns A shader
	 *        based off of the sources vertexSource and fragmentSource.
	 */
	public static Shader getShader(String vertexSource, String fragmentSource) {
		int vertShader = 0;
		int fragShader = 0;

		try {
			vertShader = createVertexShader(vertexSource);
			fragShader = createFragmentShader(fragmentSource);
		}
		catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
		finally {
			if (vertShader == 0 || fragShader == 0)
				return null;
		}

		int program = glCreateProgram();

		if (program == 0)
			return null;

		glAttachShader(program, vertShader);
		glAttachShader(program, fragShader);

		glLinkProgram(program);
		if (glGetShaderi(program, GL_LINK_STATUS) == GL11.GL_FALSE) {
			System.err.println(glGetShaderInfoLog(program, glGetShaderi(program, GL_INFO_LOG_LENGTH)));
			return null;
		}

		glValidateProgram(program);
		if (glGetShaderi(program, GL_LINK_STATUS) == GL11.GL_FALSE) {
			System.err.println(glGetShaderInfoLog(program, glGetShaderi(program, GL_INFO_LOG_LENGTH)));
			return null;
		}

		return new Shader(program);
	}

	/**
	 * @param source The stream to the source file.
	 * @param type The type of shader (the other half will use the MERCury
	 *        default shader). Returns A shader based off of the stream source,
	 *        of the type type.
	 */
	public static Shader getShader(InputStream source, int type) {
		return getShader(readShader(source), type);
	}

	/**
	 * @param source The source of the shader.
	 * @param type The type of shader (the other half will use the Mercury
	 *        defaults). Returns A shader based off of the source of the type
	 *        type.
	 */
	public static Shader getShader(String source, int type) {
		int vertShader = 0;
		int fragShader = 0;

		try {
			if (type == Shader.FRAGMENT_SHADER) {
				vertShader = createVertexShader(readShader(Loader.streamFromClasspath("com/radirius/mercury/graphics/res/default_shader.vert")));
				fragShader = createFragmentShader(source);
			} else if (type == Shader.VERTEX_SHADER) {
				fragShader = createFragmentShader(readShader(Loader.streamFromClasspath("com/radirius/mercury/graphics/res/default_shader.frag")));
				vertShader = createVertexShader(source);
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
		finally {
			if (vertShader == 0 || fragShader == 0)
				return null;
		}

		int program = glCreateProgram();

		if (program == 0)
			return null;

		glAttachShader(program, vertShader);
		glAttachShader(program, fragShader);

		glLinkProgram(program);
		if (glGetShaderi(program, GL_LINK_STATUS) == GL11.GL_FALSE) {
			System.err.println(glGetShaderInfoLog(program, glGetShaderi(program, GL_INFO_LOG_LENGTH)));
			return null;
		}

		glValidateProgram(program);
		if (glGetShaderi(program, GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
			System.err.println(glGetShaderInfoLog(program, glGetShaderi(program, GL_INFO_LOG_LENGTH)));
			return null;
		}

		return new Shader(program);
	}

	private static int createVertexShader(String source) {
		return createShader(source, GL_VERTEX_SHADER);
	}

	private static int createFragmentShader(String source) {
		return createShader(source, GL_FRAGMENT_SHADER);
	}

	private static int createShader(String shaderSource, int shaderType) {
		int shader = 0;

		try {
			shader = glCreateShader(shaderType);

			if (shader == 0)
				return 0;

			glShaderSource(shader, shaderSource);
			glCompileShader(shader);

			if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL11.GL_FALSE)
				throw new RuntimeException("Error creating shader: " + glGetShaderInfoLog(shader, glGetShaderi(shader, GL_INFO_LOG_LENGTH)));

			return shader;
		}
		catch (Exception exc) {
			glDeleteShader(shader);

			throw exc;
		}
	}

	private static String readShader(InputStream in) {
		StringBuilder source = new StringBuilder();

		Exception exception = null;

		BufferedReader reader;

		try {
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			Exception innerExc = null;

			try {
				String line;

				while ((line = reader.readLine()) != null)
					source.append(line).append('\n');
			}
			catch (Exception exc) {
				exception = exc;
			}
			finally {
				try {
					reader.close();
				}
				catch (Exception exc) {
					if (innerExc == null)
						innerExc = exc;
					else
						exc.printStackTrace();
				}
			}

			if (innerExc != null)
				throw innerExc;
		}
		catch (Exception exc) {
			exception = exc;
		}
		finally {
			try {
				in.close();
			}
			catch (Exception exc) {
				if (exception == null)
					exception = exc;
				else
					exc.printStackTrace();
			}

			if (exception != null)
				try {
					throw exception;
				}
				catch (Exception e) {
					e.printStackTrace();
				}
		}

		return source.toString();
	}

	/**
	 * Loads the default shaders for Mercury (not to be confused with shader 0).
	 */
	public static void loadDefaultShaders() {
		if (DEFAULT_SHADER == null)
			DEFAULT_SHADER = Shader.getShader(Loader.streamFromClasspath("com/radirius/mercury/graphics/res/default_shader.vert"), Loader.streamFromClasspath("com/radirius/mercury/graphics/res/default_shader.frag"));
	}

	/**
	 * Returns The id for the encapsulated program object.
	 */
	public int getProgramObject() {
		return programObject;
	}

	/**
	 * Uses the shader (only use if you know what you are doing).
	 */
	public void use() {
		glUseProgram(programObject);
	}

	/**
	 * Sets the shader to default.
	 */
	public void release() {
		releaseShaders();
	}

	/**
	 * Passes a uniform variable into the shader
	 *
	 * @param name The name of the variable.
	 * @param values The values you wish to pass in.
	 */
	public void setUniformf(String name, float... values) {
		int location = glGetUniformLocation(programObject, name);

		if (values.length == 1)
			glUniform1f(location, values[0]);
		else if (values.length == 2)
			glUniform2f(location, values[0], values[1]);
		else if (values.length == 3)
			glUniform3f(location, values[0], values[1], values[2]);
		else if (values.length == 4)
			glUniform4f(location, values[0], values[1], values[2], values[3]);
	}

	/**
	 * Passes a uniform variable into the shader
	 *
	 * @param name The name of the variable.
	 * @param values The values you wish to pass in.
	 */
	public void setUniformi(String name, int... values) {
		int location = glGetUniformLocation(programObject, name);

		if (values.length == 1)
			glUniform1i(location, values[0]);
		else if (values.length == 2)
			glUniform2i(location, values[0], values[1]);
		else if (values.length == 3)
			glUniform3i(location, values[0], values[1], values[2]);
		else if (values.length == 4)
			glUniform4i(location, values[0], values[1], values[2], values[3]);
	}

	public void setUniformMatrix4(String name, Matrix4f mat) {
		int location = glGetUniformLocation(programObject, name);
		glUniformMatrix4(location, false, mat.getAsFloatBuffer());
	}

	@Override
	public void clean() {
		glDeleteProgram(programObject);
	}
}
