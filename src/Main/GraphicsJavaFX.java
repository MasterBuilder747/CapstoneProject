/*
Homework 5
Name: Joseph Audras
Professor: Dr. Reinhart
Class: CSC 405-1
Date due: 3-5-20
*/

package Main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphicsJavaFX extends Application {

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
        graphicsCanvas = new GraphicsCanvasInner(512, 512);
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

        public GraphicsCanvasInner(int width, int height) {
            super(height, width);
            graphicsContext = this.getGraphicsContext2D();
            prepareActionHandlers();

            renderSurface = new RenderSurface(width, height);
        }

        //update display
        public void repaint() {
            double height = this.getHeight();
            double width = this.getWidth();

            graphicsContext.clearRect(0, 0, width, height);
            graphicsContext.setStroke(Color.RED);
            //sc.render(renderSurface.getSurface());
            graphicsContext.drawImage(renderSurface, 0, 0, this.getWidth(), this.getHeight());
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
        private int nButtons = 17;

        private TextField button0;
        private TextField button1;
        private TextField button2;
        private TextField button3;
        private TextField button4;

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
                    this.getChildren().add(button0);
                } else if (i == 1) {
                    button1 = new TextField();
                    button1.setMaxWidth(100);
                    this.getChildren().add(button1);
                } else if (i == 2) {
                    button2 = new TextField();
                    button2.setMaxWidth(100);
                    this.getChildren().add(button2);
                } else if (i == 3) {
                    button3 = new TextField();
                    button3.setMaxWidth(100);
                    this.getChildren().add(button3);
                } else if (i == 4) {
                    button4 = new TextField();
                    button4.setMaxWidth(100);
                    this.getChildren().add(button4);
                }
            }
        }

        private void prepareButtonHandlers() {
            buttons = new Button[nButtons];

            //textboxes and enter buttons
            for (int i = 0; i < buttons.length; ++i) {
                buttons[i] = new Button();
                buttons[i].setDisable(true);
                buttons[i].setMnemonicParsing(true);
                if (i == 0) {
                    buttons[i].setText("a, R, G, B");
                }
                if (i == 1) {
                    buttons[i].setText("a, R, G, B");
                }
                if (i == 2) {
                    buttons[i].setText("Width");
                }
                if (i == 3) {
                    buttons[i].setText("Height");
                }
                if (i == 4) {
                    buttons[i].setText("Opacity");
                }
                buttons[i].setOnAction(actionEvent -> {
                    // -- process the button
                    //System.out.println(actionEvent.getSource().toString());
                    // -- and return focus back to the pane
                    pane.requestFocus();
                });
            }

            //action buttons
            int i = 5;
            buttons[i] = new Button();
            buttons[i].setDisable(false);
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Load");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[5]) {
                    FileChooser fc = new FileChooser();
                    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                            "JPG & PNG Images", "jpg", "png");
                    fc.setSelectedExtensionFilter(filter);
                    fc.setInitialDirectory(new File(System.getProperty("user.home")));
                    File f = fc.showOpenDialog(null);
                    try {
                        inBI = ImageIO.read(f);//Utilities.ImageRead(file);
                        System.out.println(inBI.toString());
                        //store the loaded image into the global loading fb
                        inImg = Util.BiToFB(inBI);

                        //clear the surface
                        //change the window size to the loaded image dimensions
                        //set the new rendersurface to that size
                        //write the image on the rendersurface
                        //render it
                        graphicsCanvas.renderSurface.clearSurface();
                        Util.copyFrameBuffer(inImg, graphicsCanvas.renderSurface.getSurface());
                        graphicsCanvas.renderSurface.insertArray();
                        graphicsCanvas.repaint();
                        graphicsCanvas.requestFocus();
                        buttons[5].setDisable(false);
                        buttons[6].setDisable(false);
                        buttons[7].setDisable(false);
                        buttons[9].setDisable(false);
                        buttons[10].setDisable(false);
                        buttons[11].setDisable(false);
                        buttons[12].setDisable(false);
                        buttons[13].setDisable(false);
                        buttons[14].setDisable(false);
                        buttons[15].setDisable(false);
                        buttons[16].setDisable(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // focus back to the pane
                pane.requestFocus();
            });

            i = 6;
            buttons[i] = new Button();
            buttons[i].setDisable(true);
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Copy/Reset");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[6]) {
                    outImg = new FrameBuffer(inImg.height(), inImg.width(0));
                    Util.copyFrameBuffer(inImg, outImg);
                    outBI = Util.FBtoBi(outImg);
                    buttons[8].setDisable(false);
                }
                //render the new image
                graphicsCanvas.renderSurface.clearSurface();
                Util.copyFrameBuffer(outImg, graphicsCanvas.renderSurface.getSurface());
                graphicsCanvas.renderSurface.insertArray();
                graphicsCanvas.repaint();
                graphicsCanvas.requestFocus();

                // focus back to the pane
                pane.requestFocus();
            });

            i = 7;
            buttons[i] = new Button();
            buttons[i].setDisable(true);
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Colorize");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[7]) {
                    String[] rgb = button0.getText().split(",\\s*");
                    int[] intRGB = Util.stringToIntArray(rgb);
                    Pixel oldColor = new Pixel(intRGB[0], intRGB[1], intRGB[2], intRGB[3]);
                    String[] rgb2 = button1.getText().split(",\\s*");
                    int[] intRGB2 = Util.stringToIntArray(rgb2);
                    Pixel newColor = new Pixel(intRGB2[0], intRGB2[1], intRGB2[2], intRGB2[3]);
                    outImg = Util.colorPixel(inImg, oldColor.toInt(), newColor.toInt());
                    outBI = Util.FBtoBi(outImg);

                    //render the new image
                    graphicsCanvas.renderSurface.clearSurface();
                    Util.copyFrameBuffer(outImg, graphicsCanvas.renderSurface.getSurface());
                    graphicsCanvas.renderSurface.insertArray();
                    graphicsCanvas.repaint();
                    graphicsCanvas.requestFocus();

                    //ready to save
                    buttons[8].setDisable(false);
                }
                // focus back to the pane
                pane.requestFocus();
            });

            i = 8;
            buttons[i] = new Button();
            buttons[i].setDisable(true);
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Save");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[8]) {
                    // save as png
                    //FileChooser is the method to make a dialog to find a place to save the file
                    FileChooser fc = new FileChooser();
                    fc.setInitialDirectory(new File(System.getProperty("user.home")));
                    File f = fc.showSaveDialog(null);
                    String fileName = f.getAbsolutePath();
                    try {
                        Util.ImageWrite(outBI, outImg, fileName + ".png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("File saved in: " + fileName + ".png");
                }
            });

            i = 9;
            buttons[i] = new Button();
            buttons[i].setDisable(true);
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Resize Canvas");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[9]) {
                    int height = Integer.parseInt(button2.getText());
                    int width = Integer.parseInt(button3.getText());
                    outImg = Util.resizeCanvas(inImg, width, height);
                    outBI = Util.FBtoBi(outImg);
                    System.out.println("Image canvas resized to: " + width + " x " + height);

                    //ready to save
                    buttons[8].setDisable(false);
                }
            });

            i = 10;
            buttons[i] = new Button();
            buttons[i].setDisable(true);
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Opacity");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[10]) {
                    int opacity = Integer.parseInt(button4.getText());
                    outImg = Util.changeOpacity(inImg, opacity);
                    outBI = Util.FBtoBi(outImg);

                    //render the new image
                    graphicsCanvas.renderSurface.clearSurface();
                    Util.copyFrameBuffer(outImg, graphicsCanvas.renderSurface.getSurface());
                    graphicsCanvas.renderSurface.insertArray();
                    graphicsCanvas.repaint();
                    graphicsCanvas.requestFocus();

                    //ready to save
                    buttons[8].setDisable(false);

//                    graphicsCanvas.renderSurface.resizeWindow(Integer.parseInt(button2.getText()), Integer.parseInt(button3.getText()));
                }
            });

            i = 11;
            buttons[i] = new Button();
            buttons[i].setDisable(true);
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Greyscale");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[11]) {
                    outImg = Util.greyScale(inImg);
                    outBI = Util.FBtoBi(outImg);

                    //render the new image
                    graphicsCanvas.renderSurface.clearSurface();
                    Util.copyFrameBuffer(outImg, graphicsCanvas.renderSurface.getSurface());
                    graphicsCanvas.renderSurface.insertArray();
                    graphicsCanvas.repaint();
                    graphicsCanvas.requestFocus();

                    //ready to save
                    buttons[8].setDisable(false);
                }
                // focus back to the pane
                pane.requestFocus();
            });

            i = 12;
            buttons[i] = new Button();
            buttons[i].setDisable(true);
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Negative");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[12]) {
                    outImg = Util.negative(inImg);
                    outBI = Util.FBtoBi(outImg);

                    //render the new image
                    graphicsCanvas.renderSurface.clearSurface();
                    Util.copyFrameBuffer(outImg, graphicsCanvas.renderSurface.getSurface());
                    graphicsCanvas.renderSurface.insertArray();
                    graphicsCanvas.repaint();
                    graphicsCanvas.requestFocus();

                    //ready to save
                    buttons[8].setDisable(false);
                }
                // focus back to the pane
                pane.requestFocus();
            });

            i = 13;
            buttons[i] = new Button();
            buttons[i].setDisable(true);
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Sepia");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[13]) {
                    outImg = Util.sepia(inImg);
                    outBI = Util.FBtoBi(outImg);

                    //render the new image
                    graphicsCanvas.renderSurface.clearSurface();
                    Util.copyFrameBuffer(outImg, graphicsCanvas.renderSurface.getSurface());
                    graphicsCanvas.renderSurface.insertArray();
                    graphicsCanvas.repaint();
                    graphicsCanvas.requestFocus();

                    //ready to save
                    buttons[8].setDisable(false);
                }
                // focus back to the pane
                pane.requestFocus();
            });

            i = 14;
            buttons[i] = new Button();
            buttons[i].setDisable(true);
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Mirror Y");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[14]) {
                    outImg = Util.mirrorY(inImg);
                    outBI = Util.FBtoBi(outImg);

                    //render the new image
                    graphicsCanvas.renderSurface.clearSurface();
                    Util.copyFrameBuffer(outImg, graphicsCanvas.renderSurface.getSurface());
                    graphicsCanvas.renderSurface.insertArray();
                    graphicsCanvas.repaint();
                    graphicsCanvas.requestFocus();

                    //ready to save
                    buttons[8].setDisable(false);
                }
                // focus back to the pane
                pane.requestFocus();
            });
            i = 15;
            buttons[i] = new Button();
            buttons[i].setDisable(true);
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Mirror X");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[15]) {
                    outImg = Util.mirrorX(inImg);
                    outBI = Util.FBtoBi(outImg);

                    //render the new image
                    graphicsCanvas.renderSurface.clearSurface();
                    Util.copyFrameBuffer(outImg, graphicsCanvas.renderSurface.getSurface());
                    graphicsCanvas.renderSurface.insertArray();
                    graphicsCanvas.repaint();
                    graphicsCanvas.requestFocus();

                    //ready to save
                    buttons[8].setDisable(false);
                }
                // focus back to the pane
                pane.requestFocus();
            });

            i = 16;
            buttons[i] = new Button();
            buttons[i].setDisable(true);
            buttons[i].setMnemonicParsing(true);
            buttons[i].setText("Blur");
            buttons[i].setOnAction(actionEvent -> {
                if (actionEvent.getSource() == buttons[16]) {
                    int[] filter = {1, 2, 1, 2, 4, 2, 1, 2, 1};
                    int filterWidth = 3;
                    outBI = Util.blur(inBI, filter, filterWidth);
                    outImg = Util.BiToFB(outBI);

                    //render the new image
                    graphicsCanvas.renderSurface.clearSurface();
                    Util.copyFrameBuffer(outImg, graphicsCanvas.renderSurface.getSurface());
                    graphicsCanvas.renderSurface.insertArray();
                    graphicsCanvas.repaint();
                    graphicsCanvas.requestFocus();

                    //ready to save
                    buttons[8].setDisable(false);
                }
                // focus back to the pane
                pane.requestFocus();
            });
        }
    }
}
