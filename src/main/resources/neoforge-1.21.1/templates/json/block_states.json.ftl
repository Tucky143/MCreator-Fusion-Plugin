<#assign states = w.hasElementsOfType("blockstates")?then(w.getGElementsOfType("blockstates")?filter(states -> states.block == name), "")>
<#assign blockstates = states?has_content?then(states[0], "")>
<#if data.rotationMode?? && (data.rotationMode == 1 || data.rotationMode == 3)>
<#if data.enablePitch>
{
  "variants": {
    "face=floor,facing=north<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}"
    },
    "face=floor,facing=east<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "y": 90
    },
    "face=floor,facing=south<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "y": 180
    },
    "face=floor,facing=west<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "y": 270
    },
    "face=wall,facing=north<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "x": 90
    },
    "face=wall,facing=east<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "x": 90,
      "y": 90
    },
    "face=wall,facing=south<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "x": 90,
      "y": 180
    },
    "face=wall,facing=west<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "x": 90,
      "y": 270
    },
    "face=ceiling,facing=north<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "x": 180,
      "y": 180
    },
    "face=ceiling,facing=east<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "x": 180,
      "y": 270
    },
    "face=ceiling,facing=south<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "x": 180
    },
    "face=ceiling,facing=west<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "x": 180,
      "y": 90
    }<#if blockstates != "">,</#if>
    <#if blockstates != "">
    <#list blockstates.blockstateList as state>
        "face=floor,facing=north,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}"
        },
        "face=floor,facing=east,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "y": 90
        },
        "face=floor,facing=south,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "y": 180
        },
        "face=floor,facing=west,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "y": 270
        },
        "face=wall,facing=north,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "x": 90
        },
        "face=wall,facing=east,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "x": 90,
          "y": 90
        },
        "face=wall,facing=south,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "x": 90,
          "y": 180
        },
        "face=wall,facing=west,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "x": 90,
          "y": 270
        },
        "face=ceiling,facing=north,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "x": 180,
          "y": 180
        },
        "face=ceiling,facing=east,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "x": 180,
          "y": 270
        },
        "face=ceiling,facing=south,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "x": 180
        },
        "face=ceiling,facing=west,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "x": 180,
          "y": 90
        }<#sep>,
    </#list>
    </#if>
  }
}
<#else>
{
  "variants": {
    "facing=north<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}"
    },
    "facing=east<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "y": 90
    },
    "facing=south<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "y": 180
    },
    "facing=west<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "y": 270
    }<#if blockstates != "">,</#if>
    <#if blockstates != "">
    <#list blockstates.blockstateList as state>
        "facing=north,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}"
        },
        "facing=east,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "y": 90
        },
        "facing=south,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "y": 180
        },
        "facing=west,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "y": 270
        }<#sep>,
    </#list>
    </#if>
  }
}
</#if>
<#elseif data.rotationMode?? && (data.rotationMode == 2 || data.rotationMode == 4)>
{
  "variants": {
    "facing=north<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}"
    },
    "facing=east<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "y": 90
    },
    "facing=south<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "y": 180
    },
    "facing=west<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "y": 270
    },
    "facing=up<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "x": 270
    },
    "facing=down<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "x": 90
    }<#if blockstates != "">,</#if>
    <#if blockstates != "">
    <#list blockstates.blockstateList as state>
        "facing=north,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}"
        },
        "facing=east,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "y": 90
        },
        "facing=south,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "y": 180
        },
        "facing=west,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "y": 270
        },
        "facing=up,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "x": 270
        },
        "facing=down,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "x": 90
        }<#sep>,
    </#list>
    </#if>
  }
}
<#elseif data.rotationMode?? && data.rotationMode == 5>
{
  "variants": {
    "axis=x<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "x": 90,
      "y": 90
    },
    "axis=y<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}"
    },
    "axis=z<#if blockstates != "">,blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}",
      "x": 90
    }<#if blockstates != "">,</#if>
    <#if blockstates != "">
    <#list blockstates.blockstateList as state>
        "axis=x,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "x": 90,
          "y": 90
        },
        "axis=y,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}"
        },
        "axis=z,blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}",
          "x": 90
        }<#sep>,
    </#list>
    </#if>
  }
}
<#else>
{
  "variants": {
    "<#if blockstates != "">blockstate=0</#if>": {
      "model": "${modid}:block/${registryname}"
    }<#if blockstates != "">,</#if>
    <#if blockstates != "">
    <#list blockstates.blockstateList as state>
        "blockstate=${state?index + 1}": {
          "model": "${modid}:block/${blockstates.getModElement().getRegistryName()}_blockstate_${state?index}"
        }<#sep>,
    </#list>
    </#if>
  }
}
</#if>