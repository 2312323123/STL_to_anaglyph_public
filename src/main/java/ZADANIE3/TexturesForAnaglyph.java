package ZADANIE3;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class TexturesForAnaglyph {
    public int colors[] = new int[2];
    public int FBOs[] = new int[2];

    public TexturesForAnaglyph(int width, int height) {
        glGenTextures(colors);
        glBindTexture(GL_TEXTURE_2D, colors[0]);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glGenFramebuffers(FBOs);
        glBindFramebuffer(GL_FRAMEBUFFER, FBOs[0]);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, colors[0], 0);


        // copy
        glBindTexture(GL_TEXTURE_2D, colors[1]);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glBindFramebuffer(GL_FRAMEBUFFER, FBOs[1]);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, colors[1], 0);
    }

    void bindShaderAndVAO(Shader shader, int VAO, String uniformVar1, String uniformVar2) {
        shader.use();

        /* learnopengl */
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, colors[0]);
        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, colors[1]);
        glBindVertexArray(VAO);

        shader.setInt(uniformVar1, 0);
        shader.setInt(uniformVar2, 1);
    }

    void bindFirstTexture() {
        glBindTexture(GL_TEXTURE_2D, colors[0]);
        glBindFramebuffer(GL_FRAMEBUFFER, FBOs[0]);
    }

    void bindSecondTexture() {
        glBindTexture(GL_TEXTURE_2D, colors[1]);
        glBindFramebuffer(GL_FRAMEBUFFER, FBOs[1]);
    }

    void unbindTexture() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }
}
