#version 100

#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec2 v_lightPos;
varying vec4 v_position;

uniform sampler2D u_texture;

void main()
{
    float distance = sqrt(pow(abs(v_lightPos.x - v_position.x),2.0) + pow(abs(v_lightPos.y - v_position.y),2.0));
    gl_FragColor = v_color * texture2D(u_texture,v_texCoords)*max(1.0,min(2.,(0.7/distance)));
}