package ZADANIE2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int ID;

    public Shader(String vertexPath, String fragmentPath) {
        String vertexCode;
        String fragmentCode;

        vertexCode = readSource(vertexPath + ".vs");
        fragmentCode = readSource(fragmentPath + ".fs");

        // vertex Shader
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexCode);
        glCompileShader(vertexShader);

        int success;

        success = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            System.err.println(glGetShaderInfoLog(vertexShader));
            return;
        }

        // fragment Shader
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentCode);
        glCompileShader(fragmentShader);

        success = glGetShaderi(fragmentShader, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            System.err.println(glGetShaderInfoLog(fragmentShader));
            return;
        }


        ID = glCreateProgram();
        glAttachShader(ID, vertexShader);
        glAttachShader(ID, fragmentShader);
        glLinkProgram(ID);

        success = glGetProgrami(ID, GL_LINK_STATUS);
        if(success == GL_FALSE) {
            System.err.println(glGetProgramInfoLog(fragmentShader));
            return;
        }

        glValidateProgram(ID);
        success = glGetProgrami(ID, GL_VALIDATE_STATUS);
        if (success == GL_FALSE) {
            throw new IllegalStateException("Failed to validate shaders!");
        }

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    public void use() {
        glUseProgram(ID);
    }

//    public void setBool(String name, boolean value) {
//        glUniform1i(glGetUniformLocation(ID, name), value);
//    }

    public void setInt(String name, int value) {
        glUniform1i(glGetUniformLocation(ID, name), value);
    }

    public void setFloat(String name, float value) {
        glUniform1f(glGetUniformLocation(ID, name), value);
    }

    public void setMat4(String name, float[] value) {
        glUniformMatrix4fv(glGetUniformLocation(ID, name), false, value);
    }


    public void destroy() {
        glDeleteProgram(ID);
    }


    private String readSource(String file) {
        BufferedReader reader = null;
        StringBuilder sourceBuilder = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/shaders/" + file)));

            String line;

            while ((line = reader.readLine()) != null) {
                sourceBuilder.append(line + "\n");
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sourceBuilder.toString();
    }
}
