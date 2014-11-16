package com.radirius.mercury.tests;

import com.radirius.mercury.math.geometry.Matrix4f;

public class MatrixTest {
    public static void main(String[] args) {
        Matrix4f m1 = new Matrix4f().initZero()
                .set(0, 0, 15).set(1, 1, 14);

        Matrix4f m2 = new Matrix4f().initIdentity()
                .set(0, 0, 4).set(0, 1, 4);

        Matrix4f m3 = m1.copy().mul(m2);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(m3.get(i, j) + " ");
            }

            System.out.println();
        }
    }
}
