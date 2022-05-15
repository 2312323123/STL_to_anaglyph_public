package ZADANIE1;

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
/*
package LWJGLattempt2;

        import static org.lwjgl.opengl.GL11.*;

        import org.lwjgl.glfw.GLFWVidMode;
        import org.lwjgl.opengl.GL;

        import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) {
        if(!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW!");
        }

        // ustalanie wersji na ktorej robic itp
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        long window = glfwCreateWindow(640, 480, "My LWJGL Program", 0, 0);
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

        // mesh
        Mesh testMesh = new Mesh();
        testMesh.create(new float[] {
                -1,-1,0,
                0,1,0,
                1,-1,0
        });

        while(!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            // puste canvas (kanwa? plotno)
            glClear(GL_COLOR_BUFFER_BIT);

            testMesh.draw();

            // OpenGL ma dwa bufory - renderowany i aktualnie wyswietlany
            glfwSwapBuffers(window);
        }
        testMesh.destroy();

        glfwTerminate();
    }
}
*/