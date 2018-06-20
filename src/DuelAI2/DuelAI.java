/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DuelAI2;

import static DuelAI2.Constants.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class DuelAI extends JApplet implements KeyListener {
    
    static int sizeX, sizeY;

    Timer timer;
    GamePanel game;
    Persist persist = new Persist();
    ActionListener looper = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            game.drawArena.GameLoop();
            game.repaint();
            requestFocus();
        }
    };

    @Override
    public void init() {
        sizeX = 1450;
        sizeY = 1200;
        this.setSize(sizeX, sizeY);
        game = new GamePanel();

        addKeyListener(this);
        setFocusable(true);
        requestFocus();

        timer = new Timer(2000 / 60, looper);
        timer.setRepeats(true);
        timer.start();

        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(
                    new Runnable() {

                        @Override
                        public void run() {
                            createGUI();
                        }
                    });
        } catch (InterruptedException | InvocationTargetException e) {
            System.err.println("createGUI didn't complete successfully");
        }

    }

    private void createGUI() {
        //Create and set up the content pane.

        game.setEnabled(true);
        game.setOpaque(true);
        setContentPane(game);
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyChar() == 'i') {
            MAX_WEIGHT += 1.0;
        }
        if (e.getKeyChar() == 'o') {
            MAX_WEIGHT -= 1.0;
        }
        if (e.getKeyChar() == 'k') {
            DRAW_THRESHOLD += .010;
        }
        if (e.getKeyChar() == 'l') {
            DRAW_THRESHOLD -= .010;
        }
        if (e.getKeyChar() == 'a') {
            MAX_WEIGHT = 20.0;
            DRAW_THRESHOLD = .20;
        }
        if (e.getKeyChar() == 'z') {
            SHOW_ONLY_DIRECTION = !SHOW_ONLY_DIRECTION;
        }
        if (e.getKeyChar() == 'q') {
            DRAW_ACTIVATION = !DRAW_ACTIVATION;
        }
        if (e.getKeyChar() == 'n') {
            DRAW_NET = !DRAW_NET;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}