<#assign element = w.getWorkspace().getModElementByName(data.block).getGeneratableElement()>
{
  "parent": "block/cube",
  "textures": {
    "down": "${data.texture.format("%s:block/%s")}",
    "up": "${data.textureTop?has_content?then(data.textureTop, data.texture).format("%s:block/%s")}",
    "north": "${data.textureFront?has_content?then(data.textureFront, data.texture).format("%s:block/%s")}",
    "east": "${data.textureLeft?has_content?then(data.textureLeft, data.texture).format("%s:block/%s")}",
    "south": "${data.textureBack?has_content?then(data.textureBack, data.texture).format("%s:block/%s")}",
    "west": "${data.textureRight?has_content?then(data.textureRight, data.texture).format("%s:block/%s")}",
    "particle": "${data.particleTexture?has_content?then(data.particleTexture, element.particleTexture?has_content?then(element.particleTexture, element.texture)).format("%s:block/%s")}"
  },
  "render_type": "${element.getRenderType()}"
}