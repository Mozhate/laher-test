package com.laher.test.entity;

/**
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/7
 */
public class Sprinkler {
    private Room room;
    private boolean on = false;

    public Sprinkler(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
