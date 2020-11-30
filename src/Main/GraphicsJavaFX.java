/*
Homework 5
Name: Joseph Audras
Professor: Dr. Reinhart
Class: CSC 405-1
Date due: 3-5-20
*/

package Main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;

public class GraphicsJavaFX extends Application {
    int WIDTH = 512;
    int HEIGHT = 512;

    //the loaded image, does not get modified
    public FrameBuffer inImg;
    public BufferedImage inBI;
    //the saving image, this is the modified inImg
    public FrameBuffer outImg;
    public BufferedImage outBI;

    private Scene mainScene;
    private BorderPane pane;
    private GraphicsCanvasInner graphicsCanvas;
    private ControlBoxInner controlBox;

    public void launchApp(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        mainStage.setTitle("Image Processing Application");
        graphicsCanvas = new GraphicsCanvasInner(WIDTH, HEIGHT);
        controlBox = new ControlBoxInner();

        pane = new BorderPane();
        pane.setLeft(controlBox);
        pane.setCenter(graphicsCanvas);

        prepareActionHandlers(pane);
        mainScene = new Scene(pane);
        mainStage.setScene(mainScene);

        graphicsCanvas.repaint();
        mainStage.show();
        pane.requestFocus();
    }

    private void prepareActionHandlers(Pane container) {
        container.setOnKeyPressed(event -> {
            System.out.println(event.getCode().toString());
            graphicsCanvas.repaint();
        });
        container.setOnKeyReleased(event -> graphicsCanvas.repaint());
    }

    // -- Inner class for Graphics
    public class GraphicsCanvasInner extends Canvas  {

        private GraphicsContext graphicsContext;
        private RenderSurface renderSurface;

        public GraphicsCanvasInner(int height, int width) {
            super(height, width);

            graphicsContext = this.getGraphicsContext2D();
            prepareActionHandlers();

            renderSurface = new RenderSurface((int)height, (int)width);
        }

        //update display
        public void repaint() {
            double height = this.getHeight();
            double width = this.getWidth();

            graphicsContext.clearRect(0, 0, height, width);
            graphicsContext.setStroke(Color.RED);
            //sc.render(renderSurface.getSurface());
            graphicsContext.drawImage(renderSurface, 0, 0, this.getHeight(), this.getWidth());
        }

        private void prepareActionHandlers() {
            this.setOnMousePressed(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {

                }
                else if (event.getButton() == MouseButton.SECONDARY) {

                }
                pane.requestFocus();
            });
            this.setOnMouseReleased(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                }
                pane.requestFocus();
                repaint();
            });
            this.setOnMouseDragged(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                }
                pane.requestFocus();
                repaint();
            });
        }
    }


    // -- Inner class for Controls
    public class ControlBoxInner extends VBox {

        private Button buttons[];
        private int nButtons = 10;

        private TextField button0;
        private TextField button1;
        private TextField button2;

        public ControlBoxInner() {
            super();

            // -- set up buttons
            prepareButtonHandlers();

            // -- add the buttons to an V (vertical) Box (container)
            for (int i = 0; i < buttons.length; ++i) {
                this.getChildren().add(buttons[i]);
                if (i == 0) {
                    button0 = new TextField();
                    button0.setMaxWidth(100);
                    this.getChildren().add(button0); //fixed point
                } else if (i == 1) {
                    button1 = new TextField();
                    button1.setMaxWidth(100);
                    this.getChildren().add(button1); //angle in degrees
                } else if (i == 2) {
                    button2 = new TextField();
                    button2.setMaxWidth(100);
                    this.getChildren().add(button2); //scaling / translation
                }
            }
        }

        private void prepareButtonHandlers() {
            buttons = new Button[nButtons];

            //textboxes and enter buttons
            for (int i = 0; i < buttons.length; ++i) {
                buttons[i] = new Button();
                buttons[i].setMnemonicParsing(true);
                buttons[i].setText("Enter box " + i + ":"); //trans (x,y,z) and rotate (deg)
                buttons[i].setOnAction(actionEvent -> {
                    if (actionEvent.getSource() == buttons[0]) {

                    }
                    else if (actionEvent.getSource() == buttons[1]) {

                    }
                    else if (actionEvent.getSource() == buttons[2]) {

                    }
                    // -- process the button
                    //System.out.println(actionEvent.getSource().toString());
                    // -- and return focus back to the pane
                    pane.requestFocus();
                });
            }

            //action buttons
            int i = 3;
            buttons[i] = new Button();
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Load");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[3]) {

                }
                // focus back to the pane
                pane.requestFocus();
            });

            i = 4;
            buttons[i] = new Button();
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Translate");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[4]) {

                }
                // focus back to the pane
                pane.requestFocus();
            });

            i = 5;
            buttons[i] = new Button();
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Scale");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[5]) {

                }
                // focus back to the pane
                pane.requestFocus();
            });

            i = 6;
            buttons[i] = new Button();
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Rotate X");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[6]) {

                }
            });

            i = 7;
            buttons[i] = new Button();
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Rotate Y");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[7]) {

                }
            });

            i = 8;
            buttons[i] = new Button();
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Rotate Z");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[8]) {

                }
            });
        }
    }
}
