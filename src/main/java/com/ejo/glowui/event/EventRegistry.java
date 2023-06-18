package com.ejo.glowui.event;

import com.ejo.glowui.event.events.KeyPressEvent;
import com.ejo.glowui.event.events.MouseClickEvent;
import com.ejo.glowui.event.events.RenderEvent;
import com.ejo.glowui.event.events.TickEvent;
import com.ejo.glowlib.event.EventE;

/**
 * GlowUI takes advantage of the GlowLib Event System. This system allows these select events to be called and acted upon in different
 * classes without modifying the Window class. It is useful for importing GlowUI into any project.
 */
public class EventRegistry {

    public static KeyPressEvent EVENT_KEY_PRESS = new KeyPressEvent();
    public static MouseClickEvent EVENT_MOUSE_CLICK = new MouseClickEvent();
    public static RenderEvent EVENT_RENDER = new RenderEvent();
    public static TickEvent EVENT_TICK = new TickEvent();
    public static EventE EVENT_RUN_MAINTENANCE = new EventE();

}
