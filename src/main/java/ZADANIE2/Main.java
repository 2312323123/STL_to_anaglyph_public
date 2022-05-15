package ZADANIE2;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Main {
    public static void main(String[] args) {
        /*
         * uruchom main
         * miala byc tutaj widoczna pomaranczowa kostka w pewnej perspektywie
         * to co widzimy nie przypomina kostki
         * twoim zadaniem jest zmienic ten fakt
         *
         * cos jest nie tak z wyswietlaniem
         * ogolnie w vertices znajduja sie dane dla 36 wierzcholkow, jeden wiersz na wierzcholek
         * edytujac ktores pliki z tego pakietu (np. Main.java, albo TrianglesDrawArray.java)
         * doprowadz do momentu w ktorym widoczna jest pomaranczowa kostka w perspektywie, a nie rozsiane trojkaty
         *
         * jesli chcesz to zrobic edytujac TrianglesDrawArrays.java, to polecam zobaczyc dostepne argumenty w:
         * https://www.khronos.org/registry/OpenGL-Refpages/gl4/html/glVertexAttribPointer.xhtml
        */

        Window window = new Window();

        int width = 800, height = 600;
        window.createWindow(width, height);

        boolean isRunning = true;

        float vertices[] = {
                -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,
                0.5f, -0.5f, -0.5f,  1.0f, 0.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,

                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
                -0.5f,  0.5f,  0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,

                -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  1.0f, 1.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,

                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f,  0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f
        };

        TrianglesDrawArrays kostka = new TrianglesDrawArrays(vertices);

        MyShader shader = new MyShader("rectangle3d", "rectangle3d");

        float[] model = new float[16];
        float[] view = new float[16];
        float[] projection = new float[16];

        while(isRunning) {
            isRunning = !window.update();

            /* tlo */
            glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);


            /* kostka */
            glBindVertexArray(kostka.VAO);
            glDrawArrays(GL_TRIANGLES, 0, 36);
//            System.out.println(vertices.length / 3);
            /* /kostka */

            /* kostka shader */
            model = new float[16];
//            new Matrix4f().rotate(new Quaternionf((float)Math.toRadians(30.0f), 0, 0, 1)).get(model);
            new Matrix4f().rotate((float)glfwGetTime() * (float)Math.toRadians(40.0f), new Vector3f(0.5f, 1.0f, 0.0f)).get(model);
            shader.setMat4("model", model);

            new Matrix4f().translate(new Vector3f(0.0f, 0.0f, -6.0f)).get(view);
            shader.setMat4("view", view);

            new Matrix4f().setPerspective((float)Math.toRadians(45.0f), (float)width / (float)height, 0.1f, 100.0f).get(projection);
            shader.setMat4("projection", projection);

            shader.use();
            /* /kostka shader */


            /* reszta */
            window.swapBuffers();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        kostka.destroy();
        shader.destroy();

        window.free();
    }
}
