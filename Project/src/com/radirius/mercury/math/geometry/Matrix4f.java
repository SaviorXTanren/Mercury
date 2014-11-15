package com.radirius.mercury.math.geometry;

import com.radirius.mercury.graphics.GraphicsUtils;

import java.nio.FloatBuffer;

/**
 * A simple Matrix4f class to use in shaders and simple transformations like
 * scaling, translating and rotating.
 *
 * @author Sri Harsha Chilakapati
 */
public class Matrix4f {

    // The elements array
    private float m[][];

    // A FloatBuffer to send to the native memory
    private FloatBuffer mBuffer;

    /**
     * Constructs a new Matrix4f initialized to identity
     */
    public Matrix4f() {
        m = new float[4][4];
        mBuffer = GraphicsUtils.allocateFloatBuffer(16);

        initIdentity();
    }

    /**
     * Initializes the matrix to Identity matrix
     *
     * @return This matrix for chaining operations
     */
    public Matrix4f initIdentity() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                m[i][j] = (i == j) ? 1 : 0;
            }
        }

        return this;
    }

    /**
     * Initializes the matrix to Zero matrix
     *
     * @return This matrix for chaining operations
     */
    public Matrix4f initZero() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                m[i][j] = 0;
            }
        }

        return this;
    }

    /**
     * @return A copy of this matrix
     */
    public Matrix4f copy() {
        Matrix4f m = new Matrix4f();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                m.m[i][j] = this.m[i][j];
            }
        }

        return m;
    }

    /**
     * Performs matrix addition between two matrices
     *
     * @param m The matrix to add to this
     * @return This matrix for chaining operations
     */
    public Matrix4f add(Matrix4f m) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.m[i][j] += m.m[i][j];
            }
        }

        return this;
    }

    /**
     * Performs matrix subtraction between two matrices
     *
     * @param m The matrix to subtract from this
     * @return This matrix for chaining operations
     */
    public Matrix4f sub(Matrix4f m) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.m[i][j] -= m.m[i][j];
            }
        }

        return this;
    }

    /**
     * Performs matrix multiplication between two matrices
     *
     * @param m The matrix to multiply with this
     * @return This matrix for chaining operations
     */
    public Matrix4f mul(Matrix4f m) {
        float temp[][] = new float[4][4];

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                for (int k = 0; k < 4; k++) {
                    temp[i][j] += this.m[k][j] * m.m[j][k];
                }
            }
        }

        return this;
    }

    /**
     * Initializes this matrix into a rotation matrix over z-axis
     *
     * @return This matrix for chaining operations
     */
    public Matrix4f initRotation(float rz) {
        initIdentity();

        m[0][0] = (float) Math.cos(Math.toRadians(rz));
        m[1][2] = m[0][0];
        m[0][1] = (float) Math.sin(Math.toRadians(rz));
        m[1][0] = -m[0][1];

        return this;
    }

    /**
     * Initializes this matrix into a scaling matrix
     *
     * @return This matrix for chaining operations
     */
    public Matrix4f initScale(float sx, float sy) {
        initIdentity();

        m[0][0] = sx;
        m[1][1] = sy;

        return this;
    }

    /**
     * Initializes this matrix into a scaling matrix
     *
     * @return This matrix for chaining operations
     */
    public Matrix4f initScale(float s) {
        return initScale(s, s);
    }

    /**
     * Initializes this matrix into a translation matrix
     *
     * @return This matrix for chaining operations
     */
    public Matrix4f initTranslation(float tx, float ty) {
        initIdentity();

        m[0][3] = tx;
        m[1][3] = ty;

        return this;
    }

    /**
     * Initializes this matrix into a transformation matrix, which
     * transforms the vertices into orthographic projection.
     *
     * @param left   The left most coordinate of the Display
     * @param right  The right most coordinate of the Display
     * @param bottom The bottom most coordinate of the Display
     * @param top    The top most coordinate of the Display
     * @param zNear  The near depth clipping plane
     * @param zFar   The far depth clipping plane
     * @return This matrix for chaining operations
     */
    public Matrix4f initOrtho(float left, float right, float bottom, float top, float zNear, float zFar) {
        initIdentity();

        // Some initialization
        float tx = -(right + left) / (right - left);
        float ty = -(top + bottom) / (top - bottom);
        float tz = -(zFar + zNear) / (zFar - zNear);

        // Set the matrix values
        m[0][0] = 2 / (right - left);
        m[1][1] = 2 / (top - bottom);
        m[2][2] = 2 / (zNear - zFar);
        m[0][3] = tx;
        m[1][3] = ty;
        m[2][3] = tz;

        return this;
    }

    /**
     * Gets a value in this matrix at a specific row and column
     *
     * @param x The row of the matrix
     * @param y The column of the matrix
     * @return The value at [x][y] in the matrix
     */
    public float get(int x, int y) {
        return m[x][y];
    }

    /**
     * Sets a value in this matrix at a specific row and column
     *
     * @param x   The row of the matrix
     * @param y   The column of the matrix
     * @param val The value to be set
     * @return This matrix for sizing operations
     */
    public Matrix4f set(int x, int y, float val) {
        m[x][y] = val;

        return this;
    }

    /**
     * Sets the elements in this matrix to the elements
     * of another matrix.
     *
     * @param m The source matrix whose matrix to copy.
     * @return This matrix for chaining operations
     */
    public Matrix4f setTo(Matrix4f m) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.m[i][j] = m.m[i][j];
            }
        }

        return this;
    }

    /**
     * @return A FloatBuffer which contains this matrix in column-major order.
     */
    public FloatBuffer getAsFloatBuffer() {
        mBuffer.clear();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mBuffer.put(m[j][i]);
            }
        }

        mBuffer.flip();

        return mBuffer;
    }
}
