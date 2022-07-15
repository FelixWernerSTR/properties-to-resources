package de.fw.devops.utils.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

/**
 * @author  Felix Werner
 */
public class Main extends JFrame {
  
  /**
   * Create the frame.
   */
  public Main() {
    setTitle("properties-to-resources");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 900, 400);
    
    var propertiesToResourcesPanel = new PropertiesToResourcesView();
    new PropertiesToResourcesController(propertiesToResourcesPanel);
    propertiesToResourcesPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    
    setContentPane(propertiesToResourcesPanel);
  }
  
  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          Main frame = new Main();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
  
}
