package ZADANIE1;

//import LWJGLattempt2.*;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class ShaderMain {
    public static void main(String[] args) {
        /* cos poszlo nie tak i trojkaty i kwadrat maja jakos zamienione shadery */
        /* zadaniem sprawic zeby  po lewej u gory byl zolty obracajacy sie kwadrat
           a po prawej u dolu kolorowe skalujace sie trojkaty */
        /* podpowiedz: nie trzeba nic pisac, wystarczy pozmieniac odpowiednie rzeczy */

        Window window = new Window();

        window.createWindow(640, 640);

        boolean isRunning = true;

        ColorTriangleNoShader triangle2 = new ColorTriangleNoShader(new float[] {
                -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f,
                0f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f,
                0.5f,  -0.5f, 0.0f, 0.0f, 0.0f, 1.0f,
                0.75f, 0.75f, 0.0f, 1.0f, 0.0f, 0.0f,
                0.75f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                0.0f,  0.75f, 0.0f, 0.0f, 0.0f, 1.0f
        });




        float vertices[] = {
                0.5f,  0.5f, 0.0f,  // top right
                0.5f, -0.5f, 0.0f,  // bottom right
                -0.5f, -0.5f, 0.0f,  // bottom left
                -0.5f,  0.5f, 0.0f   // top left
        };
        int indices[] = {  // note that we start from 0!
                0, 1, 3,  // first Triangle
                1, 2, 3   // second Triangle
        };

        TriangleDrawElements kwadrat = new TriangleDrawElements(vertices, indices, "triangle", "triangle2");




        MyShader ourShader = new MyShader("zad1trojkat", "zad1trojkat");

//        MyShader ourShader2 = new MyShader("triangledrawelements", "triangledrawelements");
        MyShader ourShader2 = new MyShader("zad1trojkat", "zad1czworokat");

        Transform myTransform = new Transform();
        myTransform.setPosition(new Vector3f(-0.25f, 0.25f, 0));

        Transform myTransform2 = new Transform();
        myTransform2.setPosition(new Vector3f(0.5f, -0.5f, 0));

        while(isRunning) {
            isRunning = !window.update();

            /* tlo */
            glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            /* trajkat */
            glBindVertexArray(triangle2.VAO);
            glDrawArrays(GL_TRIANGLES, 0, triangle2.vertices.length);
            /* /trajkat */

            /* prostokat shader */
            myTransform.setRotation(new Quaternionf((float)Math.cos(glfwGetTime() / 2), (float)Math.sin(glfwGetTime() / 2), 0, 0));
//            myTransform.setScale(new Vector3f(0.5f * (float)Math.cos(glfwGetTime()), 0.5f * (float)Math.cos(glfwGetTime()), 1));
            myTransform.setScale(new Vector3f(0.5f, 0.5f, 1));
            ourShader.setMat4("transform", myTransform.getTransformation());
            ourShader.use();
            /* /prostokat shader */


            /* prostokat */
            glBindVertexArray(kwadrat.VAO);
            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
            /* /prostokat */


            /* trojkat shader */
            myTransform2.setScale(new Vector3f(0.5f * (float)Math.cos(glfwGetTime()), 0.5f * (float)Math.cos(glfwGetTime()), 1));
            ourShader2.setMat4("transform", myTransform2.getTransformation());
            ourShader2.use();
            /* /trojkat shader */


            /* reszta */
            window.swapBuffers();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        glDeleteVertexArrays(triangle2.VAO);
        glDeleteBuffers(triangle2.VBO);
        ourShader.destroy();

        window.free();
    }
}
