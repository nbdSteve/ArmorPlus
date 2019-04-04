package dev.nuer.ca.method;

import org.bukkit.entity.Player;

public class GetPlayerFacingDirection {

    //Store if the bucket is generating in the positive x direction
    private boolean positiveX;
    //Store if the bucket is generating in the negative x direction
    private boolean negativeX;
    //Store if the bucket is generating in the positive z direction
    private boolean positiveZ;
    //Store if the bucket is generating in the negative z direction
    private boolean negativeZ;

    /**
     * Constructor to check the direction that the player is facing, use this direction for the
     * gen bucket
     *
     * @param facing     the players current yaw
     */
    public GetPlayerFacingDirection(double facing) {
        facing = (facing %= 360) >= 0 ? facing :
                (facing + 360);
        if (facing <= 22.5 || facing > 337.5) {
            positiveZ = true;
        } else if (facing > 292.5 && facing <= 337.5) {
            positiveX = true;
            positiveZ = true;
        } else if (facing > 247.5 && facing <= 292.5) {
            positiveX = true;
        } else if (facing > 202.5 && facing <= 247.5) {
            positiveX = true;
            negativeZ = true;
        } else if (facing > 157.5 && facing <= 202.5) {
            negativeZ = true;
        } else if (facing > 112.5 && facing <= 157.5) {
            negativeX = true;
            negativeZ = true;
        } else if (facing > 67.5 && facing <= 112.5) {
            negativeX = true;
        } else if (facing > 22.5 && facing <= 67.5) {
            negativeX = true;
            positiveZ = true;
        }
    }

    /**
     * Getter for positiveX boolean
     *
     * @return boolean
     */
    public boolean positiveX() {
        return positiveX;
    }

    /**
     * Getter for negativeX boolean
     *
     * @return boolean
     */
    public boolean negativeX() {
        return negativeX;
    }

    /**
     * Getter for positiveZ boolean
     *
     * @return boolean
     */
    public boolean positiveZ() {
        return positiveZ;
    }

    /**
     * Getter for negativeZ boolean
     *
     * @return boolean
     */
    public boolean negativeZ() {
        return negativeZ;
    }
}