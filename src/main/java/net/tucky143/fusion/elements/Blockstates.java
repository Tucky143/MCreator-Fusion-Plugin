package net.tucky143.fusion.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.IWorkspaceDependent;
import net.mcreator.element.parts.TextureHolder;
import net.mcreator.element.types.interfaces.IBlockWithBoundingBox;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.workspace.Workspace;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.references.TextureReference;
import net.mcreator.workspace.resources.Model;
import net.mcreator.workspace.resources.TexturedModel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Blockstates extends GeneratableElement {
    public String block;

    public List<BlockstateListEntry> blockstateList = new ArrayList<>();

    public static class BlockstateListEntry implements IBlockWithBoundingBox {
        public BlockstateListEntry() {
            boundingBoxes = new ArrayList<>();
        }

        @TextureReference(TextureType.BLOCK)
        public TextureHolder texture;
        @TextureReference(TextureType.BLOCK)
        public TextureHolder textureTop;
        @TextureReference(TextureType.BLOCK)
        public TextureHolder textureLeft;
        @TextureReference(TextureType.BLOCK)
        public TextureHolder textureFront;
        @TextureReference(TextureType.BLOCK)
        public TextureHolder textureRight;
        @TextureReference(TextureType.BLOCK)
        public TextureHolder textureBack;
        @TextureReference(TextureType.BLOCK)
        public TextureHolder particleTexture;
        public int renderType;
        public String customModelName;
        public int luminance;
        public List<IBlockWithBoundingBox.BoxEntry> boundingBoxes;

        public int renderType() {
            return renderType;
        }
        public Model getItemModel(Workspace workspace) {
            Model.Type modelType = Model.Type.BUILTIN;
            if (this.renderType == 2) {
                modelType = Model.Type.JSON;
            } else if (this.renderType == 3) {
                modelType = Model.Type.OBJ;
            }

            return Model.getModelByParams(workspace, this.customModelName, modelType);
        }

        @Nonnull
        @Override
        public List<BoxEntry> getValidBoundingBoxes() {
            return (List)boundingBoxes.stream().filter(IBlockWithBoundingBox.BoxEntry::isNotEmpty).collect(Collectors.toList());
        }
    }

    public static class BlockstateEntry implements IWorkspaceDependent {
        public String block;
        public TextureHolder texture;
        public TextureHolder textureTop;
        public TextureHolder textureLeft;
        public TextureHolder textureFront;
        public TextureHolder textureRight;
        public TextureHolder textureBack;
        public TextureHolder particleTexture;
        public String customModelName;
        public int renderType;

        @Nullable
        transient Workspace workspace;

        @Override
        public void setWorkspace(@Nullable Workspace workspace) {
            this.workspace = workspace;
        }

        @Nullable
        @Override
        public Workspace getWorkspace() {
            return this.workspace;
        }

        public Model getItemModel() {
            Model.Type modelType = Model.Type.BUILTIN;
            if (renderType == 2) {
                modelType = Model.Type.JSON;
            } else if (renderType == 3) {
                modelType = Model.Type.OBJ;
            }

            return Model.getModelByParams(workspace, customModelName, modelType);
        }

        public Map<String, String> getTextureMap() {
            Model model = getItemModel();
            return (Map)(model instanceof TexturedModel && ((TexturedModel)model).getTextureMapping() != null ? ((TexturedModel)model).getTextureMapping().getTextureMap() : new HashMap());
        }

        public int renderType() {
            return renderType;
        }

    }

    public List<BlockstateEntry> getBlockstates() {
        List<BlockstateEntry> entries = new ArrayList<>();
        for (BlockstateListEntry state : blockstateList) {
            BlockstateEntry entry = new BlockstateEntry();
            entry.setWorkspace(getModElement().getWorkspace());
            entry.block = block;
            entry.particleTexture = state.particleTexture;
            entry.texture = state.texture;
            entry.textureBack = state.textureBack;
            entry.textureFront = state.textureFront;
            entry.textureLeft = state.textureLeft;
            entry.textureRight = state.textureRight;
            entry.textureTop = state.textureTop;
            entry.customModelName = state.customModelName;
            entry.renderType = state.renderType;
            entries.add(entry);
        }
        return entries;
    }
    public Blockstates(ModElement element) {
        super(element);
    }

    public int getBlockstateAmount() {
        return blockstateList.size();
    }

}
