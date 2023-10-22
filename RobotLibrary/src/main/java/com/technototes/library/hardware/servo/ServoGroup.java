package com.technototes.library.hardware.servo;

import com.technototes.library.hardware.HardwareDeviceGroup;

/**
 * Class for servo group
 * This is useful, but needs to be moved into something not based on the HardwareDevice stuff
 *
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class ServoGroup extends Servo implements HardwareDeviceGroup<Servo> {

    private final Servo[] followers;

    /**
     * Create a servo group
     *
     * @param servos the servos
     */
    public ServoGroup(Servo... servos) {
        super(servos[0].getDevice());
        super.setInverted(servos[0].getInverted());
        followers = new Servo[servos.length - 1];
        System.arraycopy(servos, 1, followers, 0, followers.length);
    }

    @Override
    public Servo[] getFollowers() {
        return followers;
    }

    @Override
    public Servo[] getAllDevices() {
        Servo[] m = new Servo[followers.length + 1];
        m[0] = this;
        System.arraycopy(followers, 0, m, 1, m.length - 1);
        return m;
    }

    @Override
    public void propagate(double value) {
        for (Servo s : followers) {
            s.setPosition(value);
        }
    }

    @Override
    public Servo getDeviceNum(int i) {
        return (i == 0) ? this : followers[i - 1];
    }

    @Override
    public void setPosition(double position) {
        super.setPosition(position);
        propagate(position);
    }

    public void setPositions(double... positions) {
        for (int i = 0; i < positions.length && i < getDeviceCount(); i++) {
            getDeviceNum(i).setPosition(positions[i]);
        }
    }

    @Override
    public ServoGroup startAt(double position) {
        return (ServoGroup) super.startAt(position);
    }
}
