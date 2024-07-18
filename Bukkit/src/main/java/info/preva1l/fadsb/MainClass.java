package info.preva1l.fadsb;

import javax.swing.*;
import java.awt.*;

public class MainClass {
    public static void main(String[] args) {
        System.err.println();
        System.err.println("ERROR: You cannot run this artifact as a standalone application!");
        System.err.println("This jar must be run as a bukkit plugin!");
        System.err.println();

        tryShowPopup();

        System.exit(0);
    }

    private static void tryShowPopup() {
        if (!GraphicsEnvironment.isHeadless()) {
            JOptionPane.showMessageDialog(null,
                    "ERROR: You cannot run this artifact as a standalone application!\nThis jar must be run as a bukkit plugin!",
                    "Finally a Decent Skyblock",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
