package ZADANIE3;

import org.joml.Matrix4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.lwjgl.assimp.Assimp.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Main {
    public static void main(String[] args) throws Exception {
        /*
         * ZADANIE 3
         * w tym zadaniu zajmiemy sie innym aspektem OpenGLa
         * Twoim zadaniem jest sprawic, zeby po wlaczeniu programu:
         * - pajak byl ustawiony twarza w nasza strone, do tego
         * - byl dwa razy wiekszy
         * oraz chce, zeby
         * - tlo stanowil gradient, najlepiej poziomy od bieli do czerni (zeby zasymulowac Ziemie)
        */


        String file;
        file = "s";
//        file = "Cube_3d_printing_sample";
//        file = "Eiffel_tower_sample";
//        file = "Menger_sponge_sample";
//        file = "Stanford_Bunny_sample";

        boolean basic = false;

        // shader -> transformacje -> render
        Window window = new Window();

        int width = 800, height = 600;
        window.createWindow(width, height);

        boolean isRunning = true;

        float vertices[] = {
                -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                -0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,

//                -0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
//                0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
//                0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
//                0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
//                -0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
//                -0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
//
//                -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
//                -0.5f,  0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
//                -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
//                -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
//                -0.5f, -0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
//                -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
//
//                0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
//                0.5f,  0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
//                0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
//                0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
//                0.5f, -0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
//                0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
//
//                -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
//                0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
//                0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
//                0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
//                -0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
//                -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
//
//                -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
//                0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
//                0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
//                0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
//                -0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
//                -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f
        };

        if(!basic) {

            AIScene scena = aiImportFile(System.getProperty("user.dir") + "/src/main/resources/" + file + ".stl", aiProcess_GenNormals | aiProcess_Triangulate | aiProcess_PreTransformVertices);

            if (scena == null) {
                throw new Exception("Error loading model");
            }

            int numMeshes = scena.mNumMeshes();
            PointerBuffer aiMeshes = scena.mMeshes();

            for (int i = 0; i < numMeshes; i++) {
                AIMesh aiMesh = AIMesh.create(aiMeshes.get(i));

                List<Float> vertices1 = new ArrayList<>();
                AIVector3D.Buffer aiVertices = aiMesh.mVertices();
                while (aiVertices.remaining() > 0) {
                    AIVector3D aiVertex = aiVertices.get();
                    vertices1.add(aiVertex.x());
                    vertices1.add(aiVertex.y());
                    vertices1.add(aiVertex.z());
                }

                List<Float> normals = new ArrayList<>();
                AIVector3D.Buffer aiNormals = aiMesh.mNormals();
                while (aiNormals.remaining() > 0) {
                    AIVector3D aiNormal = aiNormals.get();
                    normals.add(aiNormal.x());
                    normals.add(aiNormal.y());
                    normals.add(aiNormal.z());
                }

                List<Float> finallist = new ArrayList<>();
                for (int j = 0; j < vertices1.size(); j += 3) {
                    finallist.add(vertices1.get(j));
                    finallist.add(vertices1.get(j + 1));
                    finallist.add(vertices1.get(j + 2));
                    finallist.add(normals.get(j));
                    finallist.add(normals.get(j + 1));
                    finallist.add(normals.get(j + 2));
                }
                vertices = new float[finallist.size()];
                int index = 0;
                float max = Collections.max(vertices1);
                for (final Float value : finallist) {
                    vertices[index++] = value * 4 / max;
                }
            }
        }

        TrianglesDrawArrays kostka = new TrianglesDrawArrays(vertices);

        Shader shader1 = new Shader("kostka3d_anaglyph_2", "kostka3d_anaglyph_1");
        Shader shader2 = new Shader("kostka3d_anaglyph_2", "kostka3d_anaglyph_2");

        float[] model = new float[16];
        float[] view = new float[16];
        float[] projection = new float[16];

        Camera camera = new Camera(width, height, window);


        float vertices_suma[] = {
                // positions          // colors           // texture coords
                1.0f,  1.0f, 0.0f,   0.0f, 0.0f, 1.0f,   1.0f, 1.0f,   // top right
                1.0f, -1.0f, 0.0f,   0.0f, 0.0f, 1.0f,   1.0f, 0.0f,   // bottom right
                -1.0f, -1.0f, 0.0f,  0.0f, 0.0f, 1.0f,   0.0f, 0.0f,   // bottom left
                -1.0f,  1.0f, 0.0f,  0.0f, 0.0f, 1.0f,   0.0f, 1.0f    // top left
        };
        int indices_suma[] = {
                0, 1, 3, // first triangle
                1, 2, 3  // second triangle
        };

        TriangleDrawElements prostokat_suma = new TriangleDrawElements(vertices_suma, indices_suma);
        Shader shader_suma = new Shader("textures_vertex_suma", "textures_fragment_suma");
        TriangleDrawElements prostokat_suma_reset = new TriangleDrawElements(vertices_suma, indices_suma);
        Shader shader_suma_reset = new Shader("textures_vertex_suma", "textures_fragment_suma_reset");

        int colors[] = new int[2];
        int FBOs[] = new int[2];
        int rbos[] = new int[2];

        glGenTextures(colors);
        glGenFramebuffers(FBOs);
        glGenRenderbuffers(rbos);

        glBindTexture(GL_TEXTURE_2D, colors[0]);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);


        glBindFramebuffer(GL_FRAMEBUFFER, FBOs[0]);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, colors[0], 0);

        glBindRenderbuffer(GL_RENDERBUFFER, rbos[0]);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rbos[0]);


        // copy
        glBindTexture(GL_TEXTURE_2D, colors[1]);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glBindFramebuffer(GL_FRAMEBUFFER, FBOs[1]);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, colors[1], 0);

//        glBindRenderbuffer(GL_RENDERBUFFER, rbos[1]);
//        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height);
//        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rbos[1]);


        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_COMPLETE) {
            System.out.println("dziala");
        } else {
            System.out.println(glCheckFramebufferStatus(GL_FRAMEBUFFER));
        }


        shader_suma.use();

        /* learnopengl */
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, colors[0]);
        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, colors[1]);
        glBindVertexArray(prostokat_suma.VAO);

        shader_suma.setInt("DecalTex", 0);
        shader_suma.setInt("DecalTex2", 1);



        while(isRunning) {
            isRunning = !window.update();
            camera.update();
            ////////////////////////////////

//            textures.bindFirstTexture();
            glBindTexture(GL_TEXTURE_2D, colors[0]);
            glBindFramebuffer(GL_FRAMEBUFFER, FBOs[0]);
//            glBindRenderbuffer(GL_RENDERBUFFER, rbos[0]);

            /*/////// tlo *////////////////
            glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            glEnable(GL_DEPTH_TEST);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            /////////////////////////////////


            /*////////////// first eye shader *//////////////
            shader1.use();
            shader1.setVec3("objectColor", 1.0f, 1.0f, 1.0f);
            shader1.setVec3("lightColor",  1.0f, 1.0f, 1.0f);
            new Matrix4f().get(model);
            shader1.setMat4("model", model);
            camera.lookAtLeft.get(view);
            shader1.setMat4("view", view);
            new Matrix4f().setPerspective((float)Math.toRadians(45.0f), (float)width / (float)height, 0.1f, 100.0f).get(projection);
            shader1.setMat4("projection", projection);
            /*//////////// /first eye shader *//////////////
            kostka.draw();
            //////////////////////

//            textures.unbindTexture();
            glBindFramebuffer(GL_FRAMEBUFFER, 0);

//            textures.bindSecondTexture();
            glBindTexture(GL_TEXTURE_2D, colors[1]);
            glBindFramebuffer(GL_FRAMEBUFFER, FBOs[1]);
//            glBindRenderbuffer(GL_RENDERBUFFER, rbos[1]);

            shader_suma_reset.use();
            prostokat_suma_reset.draw();

            /*/////////// 2nd eye shader *///////////////
            shader2.use();
            shader2.setVec3("objectColor", 1.0f, 1.0f, 1.0f);
            shader2.setVec3("lightColor",  1.0f, 1.0f, 1.0f);
            new Matrix4f().get(model);
            shader2.setMat4("model", model);
            camera.lookAtRight.get(view);
            shader2.setMat4("view", view);
            new Matrix4f().setPerspective((float)Math.toRadians(45.0f), (float)width / (float)height, 0.1f, 100.0f).get(projection);
            shader2.setMat4("projection", projection);
            /*//////////// /2nd eye shader *////////////////
            kostka.draw();
            ////////////////////////////////

//            textures.unbindTexture();
            glBindFramebuffer(GL_FRAMEBUFFER, 0);


            shader_suma.use();

//            glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
//            glEnable(GL_DEPTH_TEST);
//            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            prostokat_suma.draw();


            /*//////////// reszta *////////////////
            window.swapBuffers();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        shader1.destroy();
        shader2.destroy();
        shader_suma.destroy();
        shader_suma_reset.destroy();
        kostka.destroy();
        prostokat_suma.destroy();
        prostokat_suma_reset.destroy();

        window.free();
    }
}
