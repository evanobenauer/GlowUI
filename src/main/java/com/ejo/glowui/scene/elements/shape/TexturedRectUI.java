package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import org.lwjgl.opengl.GL11;

public class TexturedRectUI extends RectangleUI {

    public TexturedRectUI(Scene scene, Vector pos, Vector size, ColorE color) {
        super(scene, pos, size, color);
    }

    //TODO: Add textured rectangles then finally create a release
    @Override
    public void draw() {
        super.draw();
        //Add texture code here
    }


    private void drawTexturedRectangle() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

}
