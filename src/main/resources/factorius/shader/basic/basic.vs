#version 330

layout (location = 0) in vec3 inPosition;
layout (location = 1) in vec2 inTexCoord;

out vec2 texCoord;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

void main() {
	gl_Position = projectionMatrix * modelViewMatrix * vec4(inPosition, 1.0);
	texCoord = inTexCoord;
}