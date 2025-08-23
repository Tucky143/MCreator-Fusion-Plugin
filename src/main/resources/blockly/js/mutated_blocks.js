Blockly.Blocks['item_list_mutator_container'] = {
    init: function () {
        this.appendDummyInput().appendField(javabridge.t('blockly.block.item_list_mutator.container'));
        this.appendStatementInput('STACK');
        this.contextMenu = false;
        this.setColour(350);
    }
};

Blockly.Blocks['item_list_mutator_input'] = {
    init: function () {
        this.appendDummyInput().appendField(javabridge.t('blockly.block.item_list_mutator.input'));
        this.setPreviousStatement(true);
        this.setNextStatement(true);
        this.contextMenu = false;
        this.fieldValues_ = [];
        this.setColour(350);
    }
};

Blockly.Extensions.registerMutator('item_list_mutator', simpleRepeatingInputMixin(
        'item_list_mutator_container', 'item_list_mutator_input', 'entry',
        function(thisBlock, inputName, index) {
            thisBlock.appendValueInput(inputName + index).setCheck('MCItem').setAlign(Blockly.Input.Align.RIGHT)
                .appendField(javabridge.t('blockly.block.item_list.ingredient') + ' ' + index + ':');
        }),
    undefined, ['item_list_mutator_input']);