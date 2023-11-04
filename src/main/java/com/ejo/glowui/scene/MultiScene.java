package com.ejo.glowui.scene;

import com.ejo.glowlib.math.Vector;

import java.util.ArrayList;
import java.util.Arrays;

//TODO: Create this template scene that can house multiple scenes, set their positions, and set their sizes. Like windows? Maybe add a drag bar to the top of each? yeah do that
public abstract class MultiScene extends Scene {

    private final ArrayList<Scene> sceneList;

    public MultiScene(String title, Scene... scenes) {
        super(title);
        this.sceneList = new ArrayList<>(Arrays.asList(scenes));
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void onKeyPress(int key, int scancode, int action, int mods) {
        super.onKeyPress(key, scancode, action, mods);
    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        super.onMouseClick(button, action, mods, mousePos);
    }

    @Override
    public void onMouseScroll(int scroll, Vector mousePos) {
        super.onMouseScroll(scroll, mousePos);
    }

    public ArrayList<Scene> getSceneList() {
        return sceneList;
    }

}
