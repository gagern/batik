/*****************************************************************************
 * Copyright (C) The Apache Software Foundation. All rights reserved.        *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the Apache Software License *
 * version 1.1, a copy of which has been included with this distribution in  *
 * the LICENSE file.                                                         *
 *****************************************************************************/

package org.apache.batik.apps.svgviewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Color;

import java.awt.geom.AffineTransform;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.apache.batik.util.gui.resource.ResourceManager;

/**
 * This class represents a viewer status bar.
 *
 * @author <a href="mailto:stephane@hillion.org">Stephane Hillion</a>
 * @version $Id$
 */
public class StatusBar extends JPanel {
    /**
     * The gui resources file name
     */
    protected final static String RESOURCES =
        "org.apache.batik.apps.svgviewer.resources.StatusBarMessages";

    /**
     * The resource bundle
     */
    protected static ResourceBundle bundle;

    /**
     * The resource manager
     */
    protected static ResourceManager rManager;
    static {
        bundle = ResourceBundle.getBundle(RESOURCES, Locale.getDefault());
        rManager = new ResourceManager(bundle);
    }

    /**
     * The x position/width label.
     */
    protected JLabel xPosition;

    /**
     * The y position/height label.
     */
    protected JLabel yPosition;

    /**
     * The zoom label.
     */
    protected JLabel zoom;

    /**
     * The message label
     */
    protected MessageArea message;

    /**
     * The main message
     */
    protected String mainMessage;

    /**
     * Creates a new status bar
     * @param rm the resource manager that finds the message
     */
    public StatusBar() {
        super(new BorderLayout(5, 5));

        JPanel p = new JPanel(new BorderLayout(0, 0));
        add("West", p);

        xPosition = new JLabel();
        BevelBorder bb;
        bb = new BevelBorder(BevelBorder.LOWERED,
                             getBackground().brighter().brighter(),
                             getBackground(),
                             getBackground().darker().darker(),
                             getBackground());
        xPosition.setBorder(bb);
        xPosition.setPreferredSize(new Dimension(70, 16));
        p.add("West", xPosition);

        yPosition = new JLabel();
        yPosition.setBorder(bb);
        yPosition.setPreferredSize(new Dimension(70, 16));
        p.add("Center", yPosition);

        zoom = new JLabel();
        zoom.setBorder(bb);
        zoom.setPreferredSize(new Dimension(70, 16));
        p.add("East", zoom);

        p = new JPanel(new BorderLayout(0, 0));
        message = new MessageArea();
        message.setForeground(zoom.getForeground());
        message.setBorder(bb);
        p.add(message);
        add(p);
        setMainMessage(rManager.getString("Panel.default_message"));
    }

    /**
     * Sets the x position.
     */
    public void setXPosition(int x) {
        xPosition.setText("x: " + x);
    }

    /**
     * Sets the width.
     */
    public void setWidth(int w) {
        xPosition.setText(rManager.getString("Position.width_letters") +
                          " " + w);
    }

    /**
     * Sets the y position.
     */
    public void setYPosition(int y) {
        yPosition.setText("y: " + y);
    }

    /**
     * Sets the height.
     */
    public void setHeight(int h) {
        yPosition.setText(rManager.getString("Position.height_letters") +
                          " " + h);
    }

    /**
     * Sets the zoom factor.
     */
    public void setZoom(float f) {
        f = (f > 0) ? f : -f;
        if (f == 1) {
            zoom.setText("1:1");
        } else if (f >= 1) {
            String s = Float.toString(f);
            if (s.length() > 6) {
                s = s.substring(0, 6);
            }
            zoom.setText("1:" + s);
        } else {
            String s = Float.toString(1 / f);
            if (s.length() > 6) {
                s = s.substring(0, 6);
            }
            zoom.setText(s + ":1");
        }
    }

    /**
     * Sets a temporary message
     * @param s the message
     */
    public void setMessage(String s) {
        mainMessage = s;
        setPreferredSize(new Dimension(0, getPreferredSize().height));
        new DisplayThread().start();
    }

    /**
     * Sets the main message
     * @param s the message
     */
    public void setMainMessage(String s) {
        mainMessage = s;
        message.setText(mainMessage = s);
        setPreferredSize(new Dimension(0, getPreferredSize().height));
    }

    /**
     * To display the main message
     */
    protected class DisplayThread extends Thread {
        public DisplayThread() {
            setPriority(Thread.MIN_PRIORITY);
        }

        public void run() {
            try {
                Thread.sleep(3000);
            } catch(InterruptedException e) {
            }

            message.setText(mainMessage);
        }
    }

    public class MessageArea extends JComponent {

        String text = "";
        TextLayout layout;
        FontRenderContext frc;
        HashMap textAttMap;

        public MessageArea() {
            super();
            textAttMap = new HashMap(2);
            textAttMap.put(TextAttribute.SIZE, new Float(12f));
            textAttMap.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
            frc = new FontRenderContext(new AffineTransform(), true, true);
        }

        public void setText(String s) {
            text = s;
            if (s.length() > 0) {
                layout = new TextLayout(text, textAttMap, frc);
            } else {
                layout = null;
            }
            repaint();
        }

        public void paint(Graphics g) {
            super.paint(g);
            Insets insets = getInsets();
            g.setColor(getForeground());
            if (layout != null)  {
                layout.draw((Graphics2D) g,
                    (float) getX()+insets.left+2,
                    (float) (getY()+getHeight()-insets.bottom-2));
            }
        }

    }
}
