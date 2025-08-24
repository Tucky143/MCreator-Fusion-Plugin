{
  "type": "neoforge:add_table",
  "conditions": [
    {
      "condition": "neoforge:loot_table_id",
      "loot_table_id": "${data.modifiedTable}"
    }
  ],
  "table": "${w.getWorkspace().getModElementByName(data.modifierTable).getGeneratableElement().getResourceLocation()}"
}