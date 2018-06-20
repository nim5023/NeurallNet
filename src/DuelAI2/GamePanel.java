/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DuelAI2;

import static DuelAI2.Constants.*;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Nick
 */
public class GamePanel extends JPanel {
    DrawArena drawArena = new DrawArena();

    GamePanel() {
        this.setBackground(Color.BLACK);
        drawArena.initializeArena();
    }
    Painter painter = new Painter();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(DRAW_NET){            
            painter.paintNet((Graphics2D)g, drawArena);
        } else {
            painter.paintGame(g, drawArena);    
        }
    }
}
