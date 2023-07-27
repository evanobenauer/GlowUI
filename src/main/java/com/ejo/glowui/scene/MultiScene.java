package com.ejo.glowui.scene;

import java.util.ArrayList;
import java.util.Arrays;

//TODO: Create this template scene that can house multiple scenes, set their positions, and set their sizes. Like windows? Maybe add a drag bar to the top of each? yeah do that
public abstract class MultiScene extends Scene {

    private final ArrayList<Scene> sceneList;

    public MultiScene(String title, Scene... scenes) {
        super(title);
        this.sceneList = new ArrayList<>(Arrays.asList(scenes));
    }

    public ArrayList<Scene> getSceneList() {
        return sceneList;
    }

}
