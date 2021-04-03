package omega.utils;
import java.awt.Graphics2D;
import java.awt.Color;
import omega.Screen;
import omega.tabPane.IconManager;
import java.io.File;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.LinkedList;
import java.awt.image.BufferedImage;
import omega.comp.NoCaretField;
import omega.comp.TextComp;
import omega.plugin.Plugin;
import javax.swing.JDialog;

import static omega.utils.UIManager.*;
import static omega.settings.Screen.*;
public class QuickFileOpener extends JDialog implements Plugin{

     private TextComp titleComp;
     private TextComp closeComp;
     private TextComp mainTabComp;
     private TextComp rightTabComp;
     private TextComp bottomTabComp;
     private NoCaretField pathField;
     private TextComp connectorComp;
     private int pressX;
     private int pressY;
     private LinkedList<BufferedImage> images = new LinkedList<>();
     
     public QuickFileOpener(){
          super(Screen.getScreen());
     }
     
     @Override
     public void init(){
          setUndecorated(true);
          setLayout(null);
          setResizable(false);
          setTitle("Quick File Opener");
          setModal(false);
          setSize(400, 160);
          setLocationRelativeTo(null);
          
     	titleComp = new TextComp("Quick File Opener", c1, c3, c2, null);
          titleComp.setBounds(0, 0, getWidth() - 30, 30);
          titleComp.setFont(PX16);
          titleComp.setClickable(false);
          titleComp.setArc(0, 0);
          titleComp.addMouseListener(new MouseAdapter(){
               @Override
               public void mousePressed(MouseEvent e){
                    pressX = e.getX();
                    pressY = e.getY();
               }
          });
          titleComp.addMouseMotionListener(new MouseAdapter(){
               @Override
               public void mouseDragged(MouseEvent e){
                    setLocation(e.getXOnScreen() - pressX, e.getYOnScreen() - pressY);
               }
          });
          add(titleComp);

          closeComp = new TextComp("x", c1, c2, c3, ()->setVisible(false));
          closeComp.setBounds(getWidth() - 30, 0, 30, 30);
          closeComp.setFont(PX16);
          closeComp.setArc(0, 0);
          add(closeComp);

          pathField = new NoCaretField("", "Enter File Path", isDarkMode() ? c1 : Color.BLACK, c2, c3);
          pathField.setBounds(0, 30, getWidth(), 40);
          pathField.setFont(PX18);
          add(pathField);
          addKeyListener(pathField);

          mainTabComp = new TextComp("Open on Main Tab Panel", c1, c2, c3, ()->{
               File file = new File(pathField.getText());
               if(file.exists())
                    getIDE().loadFile(file);
          });
          mainTabComp.setBounds(0, 70, getWidth(), 30);
          mainTabComp.setFont(PX16);
          mainTabComp.setArc(0, 0);
          add(mainTabComp);

          rightTabComp = new TextComp("Open on Right Tab Panel", c1, c2, c3, ()->{
               File file = new File(pathField.getText());
               if(file.exists())
                    getIDE().loadFileOnRightTabPanel(file);
          });
          rightTabComp.setBounds(0, 100, getWidth(), 30);
          rightTabComp.setFont(PX16);
          rightTabComp.setArc(0, 0);
          add(rightTabComp);

          bottomTabComp = new TextComp("Open on Bottom Tab Panel", c1, c2, c3, ()->{
               File file = new File(pathField.getText());
               if(file.exists())
                    getIDE().loadFileOnBottomTabPanel(file);
          });
          bottomTabComp.setBounds(0, 130, getWidth(), 30);
          bottomTabComp.setFont(PX16);
          bottomTabComp.setArc(0, 0);
          add(bottomTabComp);

          connectorComp = new TextComp("", c1, c2, c3, ()->setVisible(true)){
               @Override
               public void draw(Graphics2D g){
               	g.drawImage(IconManager.fileImage, getWidth()/2 - IconManager.fileImage.getWidth()/2, 
          	          getHeight()/2 - IconManager.fileImage.getHeight()/2, null);
               }
          };
          connectorComp.setBounds(0, 160, 40, 40);
          connectorComp.setArc(0, 0);
     }
     
     @Override
     public void enable() {
          getIDE().getSideMenu().add(connectorComp);
     }
     
     @Override
     public void disable() {
          getIDE().getSideMenu().remove(connectorComp);
     }
     
     @Override
     public BufferedImage getImage() {
          return IconManager.fileImage;
     }
     
     @Override
     public LinkedList<BufferedImage> getImages() {
          return images;
     }
     
     @Override
     public String getName() {
          return "Quick File Opener";
     }
     
     @Override
     public String getVersion() {
          return "v1.8";
     }
     
     @Override
     public String getDescription() {
          return "Just a simple path field based file opener\nto open external project files without navigation!";
     }
     
     @Override
     public String getAuthor() {
          return "Omega UI";
     }
     
     @Override
     public String getCopyright() {
          return "Copyright (c) 2021 Omega UI. All Right Reserved.";
     }
     
     @Override
     public boolean needsRestart() {
          return false;
     }
     
}
