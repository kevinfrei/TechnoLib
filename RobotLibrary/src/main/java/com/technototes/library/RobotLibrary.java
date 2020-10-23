package com.technototes.library;

/** Root class for the Robot Library
 * @author Alex Stedman
 * I will put important stuff here
 */
public class RobotLibrary {
    /** Get library version
     *
     * @return Library version
     */
    public static String getVersion(){
        return "0.3.0";
    }

    /** Get if the library is a pre release
     *
     * @return If this library version is a pre release
     */
    public static boolean isPreRelease(){
        return true;
    }
}
