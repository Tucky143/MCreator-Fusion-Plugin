{
  "type": "${modid}:${modid}_loot_modifier",
  "conditions": [
    {
      "condition": "forge:loot_table_id",
      "loot_table_id": "${data.modifiedTable}"
    }
  ],
  "lootTable": "${w.getWorkspace().getModElementByName(data.modifierTable).getGeneratableElement().getResourceLocation()}"
}