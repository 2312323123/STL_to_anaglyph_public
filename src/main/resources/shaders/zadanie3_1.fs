#version 330 core
out vec4 FragColor;

// uniform vec3 objectColor;
uniform vec3 lightColor;
uniform vec3 lightPos;

in vec3 Normal;
in vec3 FragPos;

void main()
{
vec3 objectColor = vec3(1.0, 1.0, 1.0);

    float ambientStrength = 0.2;
    vec3 ambient = ambientStrength * lightColor;

    vec3 norm = normalize(Normal);
    vec3 lightDir = normalize(lightPos * 100 - normalize(FragPos));

    float diff = abs(dot(norm, normalize(vec3(1.0, 2.0, 3.0))));
    float diffStrength = 0.8;
    vec3 diffuse = diff * lightColor * diffStrength;

    // vec3 result = mat3(0, 0, 0, 0.299, 0.587, 0.114, 0.299, 0.587, 0.114) * ((ambient + diffuse) * objectColor);
    //vec3 result = (ambient + diffuse) * objectColor;
    vec3 result = mat3(0, 0, 0, 0, 1, 0, 0, 0, 1) * ((ambient + diffuse) * objectColor);

    FragColor = vec4(result, 1.0);

    //if(gl_FragCoord.x < 400)
        //FragColor = vec4(1.0, 0.0, 0.0, 1.0);
    //else
        //FragColor = vec4(0.0, 1.0, 0.0, 1.0);
}