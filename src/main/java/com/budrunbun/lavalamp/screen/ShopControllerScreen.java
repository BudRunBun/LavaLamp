package com.budrunbun.lavalamp.screen;

import com.budrunbun.lavalamp.block.ShopControllerBlock;
import com.budrunbun.lavalamp.tileentity.ShopControllerTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ShopControllerScreen extends Screen {
    private Button confirmButton;
    private final ShopControllerTileEntity controller;
    private TextFieldWidget rotXEdit;
    private TextFieldWidget rotYEdit;
    private TextFieldWidget rotZEdit;

    public ShopControllerScreen(ShopControllerTileEntity tile) {
        super(new TranslationTextComponent(Util.makeTranslationKey("block", new ShopControllerBlock().getRegistryName())));
        controller = tile;
    }

    @Override
    public void tick() {
        rotXEdit.tick();
        rotYEdit.tick();
        rotZEdit.tick();
    }

    @Override
    protected void init() {
        confirmButton = this.addButton(new Button(this.width / 2 - 4 - 150, 210, 150, 20, I18n.format("controller_block.gui.done"),
                button -> {
                    controller.setRotations(Integer.parseInt(rotXEdit.getText()), Integer.parseInt(rotYEdit.getText()), Integer.parseInt(rotZEdit.getText()));
                    Minecraft.getInstance().displayGuiScreen(null);
                }));

        this.rotXEdit = new TextFieldWidget(this.font, this.width / 2 - 152, 80, 80, 20, I18n.format("controller_block.rotation.x"));
        this.rotXEdit.setMaxStringLength(15);
        this.rotXEdit.setText(String.valueOf(controller.getRotationX()));
        this.children.add(this.rotXEdit);

        this.rotYEdit = new TextFieldWidget(this.font, this.width / 2 - 72, 80, 80, 20, I18n.format("controller_block.rotation.y"));
        this.rotYEdit.setMaxStringLength(15);
        this.rotYEdit.setText(String.valueOf(controller.getRotationY()));
        this.children.add(this.rotYEdit);

        this.rotZEdit = new TextFieldWidget(this.font, this.width / 2 + 8, 80, 80, 20, I18n.format("controller_block.rotation.z"));
        this.rotZEdit.setMaxStringLength(15);
        this.rotZEdit.setText(String.valueOf(controller.getRotationZ()));
        this.children.add(this.rotZEdit);

    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();

        this.rotXEdit.render(p_render_1_, p_render_2_, p_render_3_);
        this.rotYEdit.render(p_render_1_, p_render_2_, p_render_3_);
        this.rotZEdit.render(p_render_1_, p_render_2_, p_render_3_);

        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    @Override
    public void resize(@Nonnull Minecraft first, int second, int third) {
        String s4 = this.rotXEdit.getText();
        String s5 = this.rotYEdit.getText();
        String s6 = this.rotZEdit.getText();
        this.init(first, second, third);
        this.rotXEdit.setText(s4);
        this.rotYEdit.setText(s5);
        this.rotZEdit.setText(s6);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
