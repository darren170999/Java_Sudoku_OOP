import java.awt.*;        
import java.awt.event.*;  
import javax.swing.*;


//import jdk.internal.org.jline.terminal.impl.jna.win.Kernel32.KEY_EVENT_RECORD;
//import jdk.jfr.SettingControl;

import java.util.*;

public class SudokuMenuBar extends JFrame 
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final int MENU_LENGTH = 100;    // Size of the board
    public static final int MENU_HEIGHT = 100; // Size of the sub-grid
    JTextArea output;
    JScrollPane scrollPane;

    public SudokuMenuBar() 
    {
        JMenuBar menuBar;
        JMenu menu;
        //JMenu submenu;
        JMenuItem menuItem;
        JCheckBoxMenuItem cbMenuItem;
        //Create the menu bar.
        
        menuBar = new JMenuBar();
        //Build the first menu.
        this.setBounds(0, 200, MENU_LENGTH, MENU_HEIGHT);
        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_M); // alt + key M
        menuBar.add(menu);
        cbMenuItem = new JCheckBoxMenuItem("Progress");
        menu.add(cbMenuItem);
        cbMenuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Progress prog = new Progress();
            }
        });

        cbMenuItem = new JCheckBoxMenuItem("Timer");
        menu.add(cbMenuItem);
        cbMenuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Stopwatch timer = new Stopwatch();
            }
        });
        menuItem = new JMenuItem("Music");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                SoundClipTest music = new SoundClipTest();
            }
        });
        menuItem = new JMenuItem("StopMusic");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                SoundClipTest.stop();
            }
        });
        /*cbMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                AbstractButton aButton = (AbstractButton)event.getSource();
                boolean selected = aButton.getModel().isSelected();
                if(selected){
                    SoundClipTest music = new SoundClipTest();
                    
                } else {
                    music.stop();
                }
            }
        });*/
        menuItem = new JMenuItem("Reset");
        menu.setMnemonic(KeyEvent.VK_R); // alt + key R
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                SudokuGeneration.reset();
            }
        });
        menuItem = new JMenuItem("Help");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Help help = new Help();
            }
        });
        menuItem = new JMenuItem("Exit");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                System.exit(0);
            }
        });
        setJMenuBar(menuBar);
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Menu Bar");
        setSize(MENU_HEIGHT, MENU_LENGTH);
        setVisible(true);
    }
    public static void main(String[] args) 
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                new SudokuMenuBar();
            }
        });
    }
}