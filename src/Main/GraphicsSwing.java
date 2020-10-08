package Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GraphicsSwing extends JFrame {

	private final GraphicPanelInner graphicsPanel;
	private ControlPanelInner controlPanel;

	public GraphicsSwing () {
		setTitle("Capstone Project");
		int HEIGHT = 512;
		int WIDTH = 512;
		setSize(WIDTH, HEIGHT);
		
		// -- center the frame on the screen
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// -- set the layout manager and add items
		//    5, 5 is the border around the edges of the areas
		setLayout(new BorderLayout(5, 5));
		graphicsPanel = new GraphicPanelInner(WIDTH, HEIGHT);
		this.add(graphicsPanel, BorderLayout.CENTER);
		controlPanel = new ControlPanelInner();
		this.add(controlPanel, BorderLayout.WEST);

        // -- paint the graphics canvas before the initial display
        graphicsPanel.repaint();
		// -- show the frame on the screen
		setVisible(true);
	
        // -- set keyboard focus to the graphics panel
		graphicsPanel.setFocusable(true);
        graphicsPanel.requestFocus();
	}


	// -- Inner class for the graphics panel
	public class GraphicPanelInner extends JPanel implements MouseMotionListener {

    	private RenderSurface renderSurface;

		public GraphicPanelInner (int width, int height) {
			super();
			this.setBackground(Color.DARK_GRAY);
			prepareActionHandlers();
			
			this.addMouseMotionListener(this);
			
	       	renderSurface = new RenderSurface((int)width, (int)height);
		}

        private void prepareActionHandlers() {
			// -- The JPanel can have a mouse listener if desired
			this.addMouseListener(new MouseListener() {

				public void mouseClicked(MouseEvent event) {

				}

				public void mouseEntered(MouseEvent event) {

				}

				public void mouseExited(MouseEvent event) {

				}

				public void mousePressed(MouseEvent event) {
	            	if (event.getButton() == MouseEvent.BUTTON1) {

	            	}
	            	else if (event.getButton() == MouseEvent.BUTTON3) {

	            	}
	            	graphicsPanel.requestFocus();
				}

				public void mouseReleased(MouseEvent event) {
	            	if (event.getButton() == MouseEvent.BUTTON1) {

	            	}
	            	else if (event.getButton() == MouseEvent.BUTTON3) {

	            	}
	            	graphicsPanel.requestFocus();
	            	repaint();
				}
			});
			
			// -- keyboard listener
			this.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent event) {

				}

				@Override
				public void keyPressed(KeyEvent event) {
	            	System.out.println(event.getKeyCode());
					graphicsPanel.repaint();
				}

				@Override
				public void keyReleased(KeyEvent event) {
					graphicsPanel.repaint();
				}
			});
        }
        
		@Override
		public void mouseDragged(MouseEvent event) {
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
      
		// -- this override sets the desired size of the JPanel which is
		//    used by some layout managers -- default desired size is 0,0
		//    which is, in general, not good -- will pull from layout manager
		public Dimension getPreferredSize() {
			return new Dimension(50, 50);
		}
		
		// -- this override is where all the painting should be done. 
		//    DO NOT call it directly. Rather, call repaint() and let the
		//    event handling system decide when to call it
		//    DO NOT put graphics function call elsewhere in the code, although
		//    legal, it's bad practice and could destroy the integrity of the
		//    display
		public void paint(Graphics g) {
			// -- the base class paintComponent(g) method ensures 
			//    the drawing area will be cleared properly. Do not
			//    modify any attributes of g prior to sending it to
			//    the base class
			super.paintComponent(g);
			
			// -- for legacy reasons the parameter comes in as type Graphics
			//    but it is really a Graphics2D object. Cast it up since the
			//    Graphics2D class is more capable
			Graphics2D graphicsContext = (Graphics2D)g;
        	int height = this.getHeight();
        	int width = this.getWidth();

        	graphicsContext.drawImage(renderSurface.toImage(), 0, 0, this.getWidth(), this.getHeight(), null);
 		}
	}
	
	// -- Inner class for control panel
	public class ControlPanelInner extends JPanel {

		private JButton[] buttons;
		private int nButtons = 3;

		JTextField textField;

		public ControlPanelInner() {
			// -- set up buttons
			prepareButtonHandlers();

			// -- add buttons to panel layout manager
			setLayout(new GridLayout(10, 1, 2, 2));
			for (int i = 0; i < buttons.length; ++i) {
				this.add(buttons[i]);
//				if (i == 1) {
//					textField = new JTextField();
//					//textField.setWidth(60);
//					this.add(textField);
//				}
			}
		}

		private void prepareButtonHandlers() {
			buttons = new JButton[nButtons];


			for (int i = 0; i < buttons.length; ++i) {
				if (i == 0) {
					buttons[i] = new JButton();
					buttons[i].setText("Load Image");
					buttons[i].setEnabled(true);
					buttons[i].addActionListener(actionEvent -> {
						if (actionEvent.getSource() == buttons[0]) {
							JFileChooser fc = new JFileChooser();
							FileNameExtensionFilter filter = new FileNameExtensionFilter(
									"JPG & PNG Images", "jpg", "png");
							fc.setFileFilter(filter);
							fc.setCurrentDirectory(new File(System.getProperty("user.home")));
							int returnVal = fc.showOpenDialog(null);
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								//This is where a real application would open the file.
								File file = fc.getSelectedFile();
								//String fileName = file.getAbsolutePath();
								BufferedImage bi;
								try {
									bi = Utilities.ImageRead(file);
									graphicsPanel.renderSurface.clearSurface();
									System.out.println(bi.toString());

									Utilities.renderImage(bi, graphicsPanel.renderSurface.getSurface());
									graphicsPanel.renderSurface.insertArray();
									graphicsPanel.repaint();
									graphicsPanel.requestFocus();
									buttons[1].setEnabled(true);
								} catch (IOException e) {
									e.printStackTrace();
								}
								//System.out.println("File saved in: " + fileName);
							} else {
								//error
							}
						}
					});
				}
				if (i == 1) {
					buttons[i] = new JButton();
					buttons[i].setText("Copy Image");
					buttons[i].setEnabled(false);
					buttons[i].addActionListener(actionEvent -> {
						if (actionEvent.getSource() == buttons[1]) {
							//display the image again
							buttons[2].setEnabled(true);

						}
					});
				}
				if (i == 2) {
					buttons[i] = new JButton();
					buttons[i].setText("Save Image");
					buttons[i].setEnabled(false);
					buttons[i].addActionListener(actionEvent -> {
						if (actionEvent.getSource() == buttons[2]) {
							// save as png
							//FileChooser is the method to make a dialog to find a place to save the file
							JFileChooser fc = new JFileChooser();
							fc.setCurrentDirectory(new File(System.getProperty("user.home")));
							int returnVal = fc.showSaveDialog(null);
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								//This is where a real application would open the file.
								File file = fc.getSelectedFile();
								String fileName = file.getAbsolutePath();
								int[][] im = new int[512][512];
								try {
									Utilities.ImageWrite(im, fileName + ".png");
								} catch (IOException e) {
									e.printStackTrace();
								}
								//System.out.println("File saved in: " + fileName);
							} else {
								//error
							}
						}
					});
				}
			}
		}

		public Dimension getPreferredSize() {
			return new Dimension(100, 500);
		}
	}
}