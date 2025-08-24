function simpleRepeatingInputMixin(mutatorContainer, mutatorInput, inputName, inputProvider, isProperInput = true,
        fieldNames = [], disableIfEmpty) {
    return {
        // Store number of inputs in XML as '<mutation inputs="inputCount_"></mutation>'
        mutationToDom: function () {
            var container = document.createElement('mutation');
            container.setAttribute('inputs', this.inputCount_);
            return container;
        },

        // Retrieve number of inputs from XML
        domToMutation: function (xmlElement) {
            this.inputCount_ = parseInt(xmlElement.getAttribute('inputs'), 10);
            this.updateShape_();
        },

        // Store number of inputs in JSON
        saveExtraState: function () {
            return {
                'inputCount': this.inputCount_
            };
        },

        // Retrieve number of inputs from JSON
        loadExtraState: function (state) {
            this.inputCount_ = state['inputCount'];
            this.updateShape_();
        },

        // "Split" this block into the correct number of inputs in the mutator UI
        decompose: function (workspace) {
            const containerBlock = workspace.newBlock(mutatorContainer);
            containerBlock.initSvg();
            var connection = containerBlock.getInput('STACK').connection;
            for (let i = 0; i < this.inputCount_; i++) {
                const inputBlock = workspace.newBlock(mutatorInput);
                inputBlock.initSvg();
                connection.connect(inputBlock.previousConnection);
                connection = inputBlock.nextConnection;
            }
            return containerBlock;
        },

        // Rebuild this block based on the number of inputs in the mutator UI
        compose: function (containerBlock) {
            let inputBlock = containerBlock.getInputTargetBlock('STACK');
            // Count number of inputs.
            const connections = [];
            const fieldValues = [];
            while (inputBlock && !inputBlock.isInsertionMarker()) {
                connections.push(inputBlock.valueConnection_);
                fieldValues.push(inputBlock.fieldValues_);
                inputBlock = inputBlock.nextConnection && inputBlock.nextConnection.targetBlock();
            }
            // Disconnect any children that don't belong. This is skipped if the provided input is a dummy input
            if (isProperInput) {
                for (let i = 0; i < this.inputCount_; i++) {
                    const connection = this.getInput(inputName + i) && this.getInput(inputName + i).connection.targetConnection;
                    if (connection && connections.indexOf(connection) == -1) {
                        connection.disconnect();
                    }
                }
            }
            this.inputCount_ = connections.length;
            this.updateShape_();
            // Reconnect any child blocks and update the field values
            for (let i = 0; i < this.inputCount_; i++) {
                if (isProperInput) {
                    Blockly.Mutator.reconnect(connections[i], this, inputName + i);
                }
                if (fieldValues[i]) {
                    for (let j = 0; j < fieldNames.length; j++) {
                        // If this is a new field, then keep its initial value, otherwise assign the stored value
                        if (fieldValues[i][j] != null)
                            this.getField(fieldNames[j] + i).setValue(fieldValues[i][j]);
                    }
                }
            }
        },

        // Keep track of the connected blocks, so that they don't get disconnected whenever an input is added or moved
        // This also keeps track of the field values
        saveConnections: function (containerBlock) {
            let inputBlock = containerBlock.getInputTargetBlock('STACK');
            let i = 0;
            while (inputBlock) {
                if (!inputBlock.isInsertionMarker()) {
                    const input = this.getInput(inputName + i);
                    if (input) {
                        if (isProperInput) {
                            inputBlock.valueConnection_ = input.connection.targetConnection;
                        }
                        inputBlock.fieldValues_ = [];
                        for (let j = 0; j < fieldNames.length; j++) {
                            const currentFieldName = fieldNames[j] + i;
                            inputBlock.fieldValues_[j] = this.getFieldValue(currentFieldName);
                        }
                    }
                    i++;
                }
                inputBlock = inputBlock.getNextBlock();
            }
        },

        // Add/remove inputs from this block
        updateShape_: function () {
            this.handleEmptyInput_(disableIfEmpty);
            // Add proper inputs
            for (let i = 0; i < this.inputCount_; i++) {
                if (!this.getInput(inputName + i))
                    inputProvider(this, inputName, i);
            }
            // Remove extra inputs
            for (let i = this.inputCount_; this.getInput(inputName + i); i++) {
                this.removeInput(inputName + i);
            }
        },

        // Handle the dummy "empty" input or warning for when there are no proper inputs
        handleEmptyInput_: function (disableIfEmpty) {
            if (disableIfEmpty === undefined) {
                if (this.inputCount_ && this.getInput('EMPTY')) {
                    this.removeInput('EMPTY');
                } else if (!this.inputCount_ && !this.getInput('EMPTY')) {
                    this.appendDummyInput('EMPTY').appendField(javabridge.t('blockly.block.' + this.type + '.empty'));
                }
            } else if (disableIfEmpty) {
                this.setWarningText(this.inputCount_ ? null : javabridge.t('blockly.block.' + this.type + '.empty'));
                this.setEnabled(this.inputCount_);
            }
        }
    }
}

Blockly.Blocks["config_start"] = {
    init: function () {
        this.appendDummyInput().appendField(javabridge.t("blockly.block.config_start"));
        this.setStyle("hat_blocks");
        this.setNextStatement(true);
        this.setColour(120);
        this.setTooltip(javabridge.t("blockly.block.config_start.tooltip"));
    }
}

class FieldRegistryName extends Blockly.FieldTextInput {
    /**
     * Validates that the input text is in the correct format:
     * lowercase, no spaces, underscores (_) as special characters, and numbers (0-9).
     * @param {string} newValue - The value to validate.
     * @return {string|null} The validated value or null if invalid.
     */
    doClassValidation_(newValue) {
        // Ensure newValue is a string and not undefined or null
        if (typeof newValue !== 'string') {
            return null;
        }

        // Check if the value matches the desired format
        if (!newValue.match(/^[a-z0-9_]+$/)) {
            return null;
        }

        return newValue;
    }
}

Blockly.fieldRegistry.register("registry_name", FieldRegistryName);

Blockly.Blocks['text_list_mutator_container'] = {
    init: function () {
        this.appendDummyInput().appendField(javabridge.t('blockly.block.text_list_mutator.container'));
        this.appendStatementInput('STACK');
        this.contextMenu = false;
        this.setColour('%{BKY_TEXTS_HUE}');
    }
};

Blockly.Blocks['text_list_mutator_input'] = {
    init: function () {
        this.appendDummyInput().appendField(javabridge.t('blockly.block.text_list_mutator.input'));
        this.setPreviousStatement(true);
        this.setNextStatement(true);
        this.contextMenu = false;
        this.fieldValues_ = [];
        this.setColour('%{BKY_TEXTS_HUE}');
    }
};

Blockly.Extensions.registerMutator('text_list_mutator', simpleRepeatingInputMixin(
        'text_list_mutator_container', 'text_list_mutator_input', 'entry',
        function(thisBlock, inputName, index) {
            thisBlock.appendDummyInput(inputName + index).setAlign(Blockly.Input.Align.RIGHT)
                .appendField(new Blockly.FieldTextInput(), 'entry' + index);
        }, false, ['entry'], true),
    undefined, ['text_list_mutator_input']);