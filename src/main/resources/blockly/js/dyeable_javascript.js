class FieldHexColor extends Blockly.FieldTextInput {
    doClassValidation_(newValue) {
        if (!newValue.match(/^[a-fA-F\d]{6}$/)) {
            return null;
        }

        return newValue;
    }
}

Blockly.fieldRegistry.register("field_hex_color", FieldHexColor);