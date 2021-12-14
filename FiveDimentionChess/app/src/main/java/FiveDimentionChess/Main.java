package FiveDimentionChess;

import processing.core.PApplet;

public class Main extends PApplet {
    
    public static void main(String[] args) {
        startGUI();
    }

    public static void startGUI() {
        PApplet.main(GUI.class.getName());
    }
}
