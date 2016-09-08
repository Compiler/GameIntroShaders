#version 130

attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;


varying vec4 v_color;
varying vec2 pos;
varying vec2 v_texCoords;
varying float f_time;

uniform mat4 u_projTrans;
uniform float time;

void main() {
    v_color = a_color;
    v_texCoords = a_texCoord0;
    
    
    //a_position.x + 75 * sin(time + a_position.y * 2)
    //a_position.y + 15 * cos(time + a_position.x * 2)
    
    vec4 newPos = vec4(a_position.x,a_position.y + 15 * (1+cos(time + a_position.x * 2)), a_position.z, a_position.w);
    gl_Position = u_projTrans * newPos;
   
   
    gl_Position = u_projTrans * a_position;
    f_time = time;
    pos = a_position.xy;
}