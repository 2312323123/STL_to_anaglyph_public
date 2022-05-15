#version 330 core

out vec4 FragColor;

uniform sampler2D DecalTex;
uniform sampler2D DecalTex2;

in vec3 ourColor;
in vec2 myUV;

void main()
{
    vec4 myFragColor = texture(DecalTex,myUV);
    vec4 myFragColor2 = texture(DecalTex2,myUV);
    // FragColor = vec4(ourColor,1.0);

    //vec4 tempFragColor = vec4(ourColor, 1.0);
    if(length(myFragColor.xyz) > 0 || length(myFragColor2.xyz) > 0) {
        FragColor = myFragColor + myFragColor2;// + tempFragColor;
    } else {
        FragColor = vec4(0.2f, 0.2f, 0.2f, 0.2f);
    }
}