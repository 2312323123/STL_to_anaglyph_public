package stl_to_anaglyph;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {
    Vector3f cameraPos = new Vector3f(0.0f, 0.0f, 6.0f);
    Vector3f cameraTarget = new Vector3f(0.0f, 0.0f, 0.0f);
    Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
    Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);

    Matrix4f lookAtLeft;
    Matrix4f lookAtRight;
    Vector3f buffer = new Vector3f();
    Vector3f buffer2 = new Vector3f();

    float cameraSpeed;
    float deltaTime;
    float lastFrame = 0.0f;
    float currentFrame;

    Vector3f cameraDirection = new Vector3f();

    float lastX, lastY;

    float yaw = -90.0f, pitch = 0;
    boolean firstMouse = true;

    Window window;

    public Camera(int width, int height, Window windowObject) {
        lastX = width/2;
        lastY = height/2;
        this.window = windowObject;

        glfwSetCursorPosCallback(windowObject.window, (long theWindow, double posX, double posY) -> mouseCallBack((float)posX, (float)posY));
    }

    void update() {
        currentFrame = (float)glfwGetTime();
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;
        cameraSpeed = 2.5f * deltaTime;

        if (glfwGetKey(window.window, GLFW_KEY_W) == GLFW_PRESS) {
            cameraFront.mul(cameraSpeed, buffer);
            cameraPos.add(buffer);
        }
        if (glfwGetKey(window.window, GLFW_KEY_S) == GLFW_PRESS) {
            cameraFront.mul(cameraSpeed, buffer);
            cameraPos.sub(buffer);
        }
        if (glfwGetKey(window.window, GLFW_KEY_A) == GLFW_PRESS) {
//                System.out.println(cameraPos);
            cameraFront.cross(cameraUp, buffer);
            cameraPos.sub(buffer.normalize().mul(cameraSpeed));
//                System.out.println(cameraPos);
//                System.out.println("\n\n");
        }
        if (glfwGetKey(window.window, GLFW_KEY_D) == GLFW_PRESS) {
            cameraFront.cross(cameraUp, buffer);
            cameraPos.add(buffer.normalize().mul(cameraSpeed));
        }
        if (glfwGetKey(window.window, GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS) {
            cameraFront.cross(cameraUp, buffer);
            cameraFront.cross(buffer, buffer);
            cameraPos.add(buffer.normalize().mul(cameraSpeed));
        }
        if (glfwGetKey(window.window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
            cameraFront.cross(cameraUp, buffer);
            cameraFront.cross(buffer, buffer);
            cameraPos.add(buffer.normalize().mul(cameraSpeed));
        }
        if (glfwGetKey(window.window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            cameraFront.cross(cameraUp, buffer);
            cameraFront.cross(buffer, buffer);
            cameraPos.sub(buffer.normalize().mul(cameraSpeed));
        }

        cameraDirection.x = (float)Math.cos(Math.toRadians(yaw)) * (float)Math.cos(Math.toRadians(pitch));
        cameraDirection.z = (float)Math.sin(Math.toRadians(yaw)) * (float)Math.cos(Math.toRadians(pitch));
        cameraDirection.y = (float)Math.sin(Math.toRadians(pitch));

        cameraPos.add(cameraFront, buffer);

        cameraDirection.cross(cameraUp, buffer2);
        lookAtLeft = new Matrix4f().lookAt(cameraPos, buffer, cameraUp).translate(buffer2.normalize().mul(0.1f));
        lookAtRight = new Matrix4f().lookAt(cameraPos, buffer, cameraUp).translate(buffer2.mul(-1f));
    }

    void mouseCallBack(float xPos, float yPos) {
        if (firstMouse)
        {
            lastX = xPos;
            lastY = yPos;
            firstMouse = false;
        }

        float xoffset = xPos - lastX;
        float yoffset = lastY - yPos;
        lastX = xPos;
        lastY = yPos;

        final float sensitivity = 0.1f;
        xoffset *= sensitivity;
        yoffset *= sensitivity;

        yaw += xoffset;
        pitch += yoffset;

        if(pitch > 89.0f)
            pitch =  89.0f;
        if(pitch < -89.0f)
            pitch = -89.0f;
        if(yaw > 360.0f)
            yaw -= 360.0f;
        if(yaw < -360.0f)
            yaw += 360.0f;

        Vector3f direction = new Vector3f();

        direction.x = (float)Math.cos(Math.toRadians(yaw)) * (float)Math.cos(Math.toRadians(pitch));
        direction.z = (float)Math.sin(Math.toRadians(yaw)) * (float)Math.cos(Math.toRadians(pitch));
        direction.y = (float)Math.sin(Math.toRadians(pitch));

        cameraFront = direction.normalize();
    }
}
