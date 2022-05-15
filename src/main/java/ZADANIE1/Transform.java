package ZADANIE1;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {
    private Vector3f position;
    private Quaternionf rotation;
    private Vector3f scale;
    // 4 * 4 = 16
    private float [] matrix;

    public Transform() {
        // srodek swiata
        position = new Vector3f();
        // bez rotacji
        rotation = new Quaternionf();
        // domyslna skala (1)
        scale = new Vector3f(1);

        matrix = new float[16];
    }

    public float[] getTransformation() {
        Matrix4f returnValue = new Matrix4f();

        // kolejnosc wazna
        returnValue.translate(position);
        returnValue.rotate(rotation);
        returnValue.scale(scale);

        returnValue.get(matrix);
        return matrix;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}


//package LWJGLattempt2;
//
//import org.joml.Matrix4f;
//import org.joml.Quaternionf;
//import org.joml.Vector3f;
//
//public class Transform {
//    private Vector3f position;
//    private Quaternionf rotation;
//    private Vector3f scale;
//
//    public Transform() {
//        // srodek swiata
//        position = new Vector3f();
//        // bez rotacji
//        rotation = new Quaternionf();
//        // domyslna skala (1)
//        scale = new Vector3f(1);
//    }
//
//    public Matrix4f getTransformation() {
//        Matrix4f returnValue = new Matrix4f();
//
//        // kolejnosc wazna
//        returnValue.translate(position);
//        returnValue.rotate(rotation);
//        returnValue.scale(scale);
//
//        return returnValue;
//    }
//
//    public Vector3f getPosition() {
//        return position;
//    }
//
//    public void setPosition(Vector3f position) {
//        this.position = position;
//    }
//
//    public Quaternionf getRotation() {
//        return rotation;
//    }
//
//    public void setRotation(Quaternionf rotation) {
//        this.rotation = rotation;
//    }
//
//    public Vector3f getScale() {
//        return scale;
//    }
//
//    public void setScale(Vector3f scale) {
//        this.scale = scale;
//    }
//}