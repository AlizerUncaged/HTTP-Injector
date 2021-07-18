/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respite.http.injector;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JButton;

/**
 *
 * @author user
 */
public class GradientButton extends JButton {
        private GradientButton() {
            super("Gradient Button");
            setContentAreaFilled(false);
            setFocusPainted(false); // used for demonstration
        }

        @Override
        protected void paintComponent(Graphics g) {
            final Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(new GradientPaint(
                    new Point(0, 0), 
                    Color.WHITE, 
                    new Point(0, getHeight()), 
                    Color.PINK.darker()));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();

            super.paintComponent(g);
        }

        public static GradientButton newInstance() {
            return new GradientButton();
        }
    }
