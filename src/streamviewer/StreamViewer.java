/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streamviewer;

import javax.swing.JFrame;

/**
 *
 * @author Kevin
 */
public class StreamViewer
{

    /**
     * @param args the command line arguments
     */
    private static JFrame mainFrame;
    private static LauncherPanel launchPanel;
    
    
    public static void main(String[] args)
    {
        
        
        mainFrame = new JFrame("Stream Launcher");
        launchPanel = new LauncherPanel();
        
        mainFrame.setContentPane(launchPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 400);
        mainFrame.setVisible(true);
    }
    
}
