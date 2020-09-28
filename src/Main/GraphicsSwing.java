package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GraphicsSwing extends JFrame {

    private int WIDTH = 512;
    private int HEIGHT = 256;

	private Timer animationTimer = null;	
	
	private GraphicPanelInner graphicsPanel;
	private ControlPanelInner controlPanel;
	
	public GraphicsSwing () {
		setTitle("Capstone Project");

		// -- size of the frame: width, height
		setSize(WIDTH, HEIGHT);
		
		// -- center the frame on the screen
		setLocationRelativeTo(null);
		
		// -- shut down the entire application when the frame is closed
		//    if you don't include this the application will continue to
		
		//    run in the background and you'll have to kill it by pressing
		//    the red square in eclipse
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// -- set the layout manager and add items
		//    5, 5 is the border around the edges of the areas
		setLayout(new BorderLayout(5, 5));

		graphicsPanel = new GraphicPanelInner(WIDTH, HEIGHT);
		this.add(graphicsPanel, BorderLayout.CENTER);
		
		controlPanel = new ControlPanelInner();
		this.add(controlPanel, BorderLayout.WEST);

		// -- Timer will generate an event every 10mSec once it is started
		//    First parameter is the delay in mSec, second is the ActionListener
		animationTimer = new Timer(10,
				// -- ActionListener for the timer event
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.out.println("tic");
					}
				}
		);

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

    	public Timer getAnimationTimer() {
			return animationTimer;
		}

		public GraphicPanelInner (int width, int height) {
			super();
			this.setBackground(Color.white);
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

		private int nButtons = 6;
		private JButton buttons[];

		JTextField textField;

		public ControlPanelInner() {
			// -- set up buttons
			prepareButtonHandlers();

			// -- add buttons to panel layout manager
			setLayout(new GridLayout(10, 1, 2, 2));
			for (int i = 0; i < buttons.length; ++i) {
				this.add(buttons[i]);
				if (i == 1) {
					textField = new JTextField();
					//textField.setWidth(60);
					this.add(textField);
				}
			}
		}

		private void prepareButtonHandlers() {
			buttons = new JButton[nButtons];
			for (int i = 0; i < buttons.length; ++i) {
				buttons[i] = new JButton();
				buttons[i].setText("Button " + i);
				buttons[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						if (actionEvent.getSource() == buttons[0]) {
							animationTimer.start();
						} else if (actionEvent.getSource() == buttons[nButtons - 1]) {
							animationTimer.stop();
						} else if (actionEvent.getSource() == buttons[1]) {
							System.out.println(textField.getText());
						}
						// -- process the button
						System.out.println(actionEvent.getSource().toString());

						// -- and return focus back to the graphics panel
						graphicsPanel.requestFocus();

					}
				});
			}
		}

		public Dimension getPreferredSize() {
			return new Dimension(100, 500);
		}
	}
}