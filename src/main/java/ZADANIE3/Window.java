package ZADANIE3;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class Window {
    long window;

    public Window() {

    }

    public void createWindow(int x, int y) {
        if(!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW!");
        }

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        // ustalanie wersji na ktorej robic itp
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        window = glfwCreateWindow(x, y, "My LWJGL Program", 0, 0);
        if(window == 0) {
            throw new IllegalStateException("Failed to create window!");
        }

        // Opengl3.2
        glfwMakeContextCurrent(window);
        // inicjalizacja rzeczy z OpenGL
        GL.createCapabilities();

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (videoMode.width() - 640) / 2, (videoMode.height() - 480) / 2);

        glfwShowWindow(window);

        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public void swapBuffers() {
        glfwPollEvents();

        // OpenGL ma dwa bufory - renderowany i aktualnie wyswietlany
        glfwSwapBuffers(window);
    }

    public void free() {
        glfwTerminate();
    }

    public boolean update() {
        if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
            glfwSetWindowShouldClose(window, true);

        return glfwWindowShouldClose(window);
    }
}