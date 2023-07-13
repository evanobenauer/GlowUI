package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import org.lwjgl.opengl.GL11;

public class TexturedRectUI extends RectangleUI {

    public TexturedRectUI(Vector pos, Vector size, ColorE color) {
        super(pos, size, color);
    }

    //TODO: Add textured rectangles then finally create a release
    @Override
    public void drawElement(Scene scene, Vector mousePos) {
        super.drawElement(scene, mousePos);
        //Add texture code here
    }


    private void drawTexturedRectangle() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

}
