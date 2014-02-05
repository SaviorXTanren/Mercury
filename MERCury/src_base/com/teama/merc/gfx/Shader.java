package com.teama.merc.gfx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

import com.teama.merc.res.Loader;
import com.teama.merc.res.Resource;

/**
 * An object version of shaders. Does all of the tedious stuff for you and lets you use the shader easily.
 * 
 * @from merc in com.teama.merc.gfx
 * @authors wessles, opiop65
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Shader implements Resource
{
    public static final int DEFAULT_SHADER = 0;
    public static final int VERTEX_SHADER = 0;
    public static final int FRAGMENT_SHADER = 1;
    
    private int programobject;
    
    public Shader(int programobject)
    {
        this.programobject = programobject;
    }
    
    public int getProgramObject()
    {
        return programobject;
    }
    
    public void use()
    {
        ARBShaderObjects.glUseProgramObjectARB(programobject);
    }
    
    public void release()
    {
        ARBShaderObjects.glUseProgramObjectARB(DEFAULT_SHADER);
    }
    
    public void setUniformf(String name, float... values)
    {
        int location = ARBShaderObjects.glGetUniformLocationARB(programobject, name);
        
        if (values.length == 1)
            ARBShaderObjects.glUniform1fARB(location, values[0]);
        else if (values.length == 2)
            ARBShaderObjects.glUniform2fARB(location, values[0], values[1]);
        else if (values.length == 3)
            ARBShaderObjects.glUniform3fARB(location, values[0], values[1], values[2]);
        else if (values.length == 4)
            ARBShaderObjects.glUniform4fARB(location, values[0], values[1], values[2], values[3]);
    }
    
    public void setUniformi(String name, int... values)
    {
        int location = ARBShaderObjects.glGetUniformLocationARB(programobject, name);
        
        if (values.length == 1)
            ARBShaderObjects.glUniform1iARB(location, values[0]);
        else if (values.length == 2)
            ARBShaderObjects.glUniform2iARB(location, values[0], values[1]);
        else if (values.length == 3)
            ARBShaderObjects.glUniform3iARB(location, values[0], values[1], values[2]);
        else if (values.length == 4)
            ARBShaderObjects.glUniform4iARB(location, values[0], values[1], values[2], values[3]);
    }
    
    @Override
    public void clean()
    {
        
    }
    
    public static void useShader(Shader shader)
    {
        shader.use();
    }
    
    public static void releaseShaders()
    {
        ARBShaderObjects.glUseProgramObjectARB(DEFAULT_SHADER);
    }
    
    public static Shader getShader(int vert, int frag)
    {
        int program = ARBShaderObjects.glCreateProgramObjectARB();
        
        if (program == 0)
            return null;
        
        ARBShaderObjects.glAttachObjectARB(program, vert);
        ARBShaderObjects.glAttachObjectARB(program, frag);
        
        ARBShaderObjects.glLinkProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE)
        {
            System.err.println(ARBShaderObjects.glGetInfoLogARB(program, ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
            return null;
        }
        
        ARBShaderObjects.glValidateProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE)
        {
            System.err.println(ARBShaderObjects.glGetInfoLogARB(program, ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
            return null;
        }
        
        return new Shader(program);
    }
    
    public static Shader getShader(InputStream invert, InputStream infrag)
    {
        int vertShader = 0;
        int fragShader = 0;
        
        try
        {
            vertShader = createVertexShader(invert);
            fragShader = createFragmentShader(infrag);
        } catch (Exception exc)
        {
            exc.printStackTrace();
            return null;
        } finally
        {
            if (vertShader == 0 || fragShader == 0)
                return null;
        }
        
        int program = ARBShaderObjects.glCreateProgramObjectARB();
        
        if (program == 0)
            return null;
        
        ARBShaderObjects.glAttachObjectARB(program, vertShader);
        ARBShaderObjects.glAttachObjectARB(program, fragShader);
        
        ARBShaderObjects.glLinkProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE)
        {
            System.err.println(ARBShaderObjects.glGetInfoLogARB(program, ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
            return null;
        }
        
        ARBShaderObjects.glValidateProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE)
        {
            System.err.println(ARBShaderObjects.glGetInfoLogARB(program, ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
            return null;
        }
        
        return new Shader(program);
    }
    
    public static Shader getShader(InputStream in, int type)
    {
        int vertShader = 0;
        int fragShader = 0;
        
        try
        {
            if (type == Shader.FRAGMENT_SHADER)
            {
                vertShader = createVertexShader(Loader.streamFromClasspath("com/teama/merc/gfx/default.vs"));
                fragShader = createFragmentShader(in);
            } else if (type == Shader.VERTEX_SHADER)
            {
                fragShader = createFragmentShader(Loader.streamFromClasspath("com/teama/merc/gfx/default.fs"));
                vertShader = createVertexShader(in);
            }
        } catch (Exception exc)
        {
            exc.printStackTrace();
            return null;
        } finally
        {
            if (vertShader == 0 || fragShader == 0)
                return null;
        }
        
        int program = ARBShaderObjects.glCreateProgramObjectARB();
        
        if (program == 0)
            return null;
        
        ARBShaderObjects.glAttachObjectARB(program, vertShader);
        ARBShaderObjects.glAttachObjectARB(program, fragShader);
        
        ARBShaderObjects.glLinkProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE)
        {
            System.err.println(ARBShaderObjects.glGetInfoLogARB(program, ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
            return null;
        }
        
        ARBShaderObjects.glValidateProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE)
        {
            System.err.println(ARBShaderObjects.glGetInfoLogARB(program, ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
            return null;
        }
        
        return new Shader(program);
    }
    
    private static int createVertexShader(InputStream in) throws Exception
    {
        return createShader(in, ARBVertexShader.GL_VERTEX_SHADER_ARB);
    }
    
    private static int createFragmentShader(InputStream in) throws Exception
    {
        return createShader(in, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
    }
    
    private static int createShader(InputStream in, int shaderType) throws Exception
    {
        int shader = 0;
        try
        {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
            
            if (shader == 0)
                return 0;
            
            ARBShaderObjects.glShaderSourceARB(shader, readShader(in));
            ARBShaderObjects.glCompileShaderARB(shader);
            
            if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
                throw new RuntimeException("Error creating shader: " + ARBShaderObjects.glGetInfoLogARB(shader, ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
            
            return shader;
        } catch (Exception exc)
        {
            ARBShaderObjects.glDeleteObjectARB(shader);
            throw exc;
        }
    }
    
    private static String readShader(InputStream in) throws Exception
    {
        StringBuilder source = new StringBuilder();
        
        Exception exception = null;
        
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            
            Exception innerExc = null;
            try
            {
                String line;
                while ((line = reader.readLine()) != null)
                    source.append(line).append('\n');
            } catch (Exception exc)
            {
                exception = exc;
            } finally
            {
                try
                {
                    reader.close();
                } catch (Exception exc)
                {
                    if (innerExc == null)
                        innerExc = exc;
                    else
                        exc.printStackTrace();
                }
            }
            
            if (innerExc != null)
                throw innerExc;
        } catch (Exception exc)
        {
            exception = exc;
        } finally
        {
            try
            {
                in.close();
            } catch (Exception exc)
            {
                if (exception == null)
                    exception = exc;
                else
                    exc.printStackTrace();
            }
            
            if (exception != null)
                throw exception;
        }
        
        return source.toString();
    }
    
    public static Shader getDefaultShader()
    {
        return Shader.getShader(Loader.streamFromClasspath("com/teama/merc/gfx/default.vs"), Loader.streamFromClasspath("com/teama/merc/gfx/default.fs"));
    }
}