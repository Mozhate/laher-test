package com.laher.test.entity;

/**
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/7
 */
public class Fire {
    private Room room;

    public Fire(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
