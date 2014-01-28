varying vec4 color;

void main()
{
        color = vec4(1, 0, 0, 1);
        gl_Position = ftransform(); 
}