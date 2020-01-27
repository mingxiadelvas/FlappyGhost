// Alexandre Julien 1012389 et Ming-Xia Delvas 1175967

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.canvas.*;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;

public class View extends Application {
	private static final int WIDTH = 640; // Width from specification
	private static final int HEIGHT = 440; // Height from specification
	private static int canvasHeight = HEIGHT - 40;
	private Text scoreCount; // Score text box
	private GraphicsContext context; // Canvas's graphic context
	private Button pauseButton; // Pause/Resume button
	private Image backgroundImg; // Scrolling background image
	private double totalOffset; // Total offset for scrolling loop
	private AnimationTimer timer;
	private long lastTimeRendered;
	
	@Override
	public void start(Stage primaryStage) {
		try {	
			
			// Root and scene
			VBox root = new VBox();
			Scene mainScene = new Scene(root, WIDTH, HEIGHT);
						
			// Canvas with background
			Canvas canvas = new Canvas(WIDTH, canvasHeight);
			setBackgroundImg(new Image("/assets/bg.png"));
			setContext(canvas.getGraphicsContext2D());
			root.getChildren().add(canvas);
			
			// Initiate controller.
			Controller controller = new Controller(this);

			// Add key pressed events on scene
			mainScene.setOnKeyPressed((event) -> {
				if(event.getCode() == KeyCode.SPACE) {
					controller.handleGhostJump();
				} else if (event.getCode() == KeyCode.ESCAPE) {
					Platform.exit(); 
				}
			});
			
			/* Lorsqu’on clique ailleurs sur la scène,le focus retourne sur le canvas */
			mainScene.setOnMouseClicked((event) -> {
				canvas.requestFocus();
			});
			
			// Hbox for buttons
			HBox buttonsBar = new HBox();
			
			// Pause button
			Button pauseBtn = new Button("Pause");
			pauseBtn.setOnMouseClicked((event) -> {
				controller.handlePauseButton();
				canvas.requestFocus();
			});
			setPauseButton(pauseBtn);
			buttonsBar.getChildren().add(pauseBtn);
			
			// Separator 1
			Separator sep1 = new Separator();
			sep1.setOrientation(Orientation.VERTICAL);
			buttonsBar.getChildren().add(sep1);

			// Debug checkbox
			CheckBox debugCheckBox = new CheckBox("Mode debug");
			buttonsBar.getChildren().add(debugCheckBox);
			debugCheckBox.setOnMouseClicked((event) -> {
				controller.handleDebugCheckbox();
				canvas.requestFocus();
			});
			
			// Separator 2
			Separator sep2 = new Separator();
			sep2.setOrientation(Orientation.VERTICAL);
			buttonsBar.getChildren().add(sep2);
			
			// Score text
			Text initScoreCount = new Text("Score: 0");
			setScoreCount(initScoreCount);
			buttonsBar.getChildren().add(initScoreCount);
			buttonsBar.setAlignment(Pos.CENTER);
			buttonsBar.setPadding(new Insets(6));
			root.getChildren().add(buttonsBar);
			
			primaryStage.setTitle("Flappy Ghost");
			primaryStage.getIcons().add(new Image("./assets/ghost.png"));
			primaryStage.setScene(mainScene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
			/* Après l’exécution de la fonction, lefocus va automatiquement au canvas */
			Platform.runLater(() -> {
				canvas.requestFocus();
			});
			
			// Start timer to animate game
			this.timer = new AnimationTimer() {			
				@Override
				public void handle(long now) {
					if (getlastTimeRendered() == 0) {
						setlastTimeRendered(now);
						return;
					}
					
					double deltaTime = (now - getlastTimeRendered()) * 1e-9;
					
					getContext().clearRect(0, 0, getWidth(), getHeight()); // Clear canvas
					controller.handleFrame(deltaTime); // Ask controller what to draw
					controller.handleSpawn(now); // Tell controller the time since we last animated
					setlastTimeRendered(now);
				}
			};
			timer.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void drawBackground(double offset) {
		incrementTotalOffset(offset);
		
		// Reset offset if both images are outside
		if (getTotalOffset() > getWidth()) { setTotalOffset(0); }
		
		// Offset dual background images
		getContext().drawImage(getBackgroundImg(), -getTotalOffset(), 0);
		getContext().drawImage(getBackgroundImg(), getWidth() - getTotalOffset(), 0);
	}
	
	public void drawCircle(int radius, double x, double y, String color) {
		// Set color other than the default black if needs be
		color = color.toLowerCase();
		switch (color) {
			case "yellow" : context.setFill(Color.YELLOW);
				break;
			case "red" : context.setFill(Color.RED);
				break;			
			default : context.setFill(Color.BLACK);
				break;
		}
		double diameter = radius * 2;
		context.fillOval(x, y, diameter, diameter);
	}
	
	public void drawImage(String image, int radius, double x, double y) {
		double diameter = radius * 2;
		context.drawImage(new Image(image, diameter, diameter, true, true), x, y);
	}
	
	public void updatePauseButton(String str) {
        Platform.runLater(() -> {
        	pauseButton.setText(str);
        });
    }
	
	public void updateScore(int score) {
        Platform.runLater(() -> {
        	String str = "Score: " + Integer.toString(score);
            scoreCount.setText(str);
        });
    }
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	public int getWidth() {
		return View.WIDTH;
	}
	
	public int getHeight() {
		return View.HEIGHT;
	}
		
	public double getCanvasHeight() {
		return View.canvasHeight;
	}
	
	public double getTotalOffset() {
		return totalOffset;
	}

	public void setTotalOffset(double newTotalOffset) {
		this.totalOffset = newTotalOffset;
	}
	
	public void incrementTotalOffset(double increment) {
		this.totalOffset += increment;
	}
	
	public void setPauseButton(Button pauseBtn) {
		this.pauseButton = pauseBtn;
	}
	
	public void setScoreCount(Text scoreCount) {
		this.scoreCount = scoreCount;
	}

	public GraphicsContext getContext() {
		return context;
	}
	
	public void setContext(GraphicsContext context) {
		this.context = context;
	}

	public Image getBackgroundImg() {
		return backgroundImg;
	}

	public void setBackgroundImg(Image backgroundImg) {
		this.backgroundImg = backgroundImg;
	}	
	
	public AnimationTimer getTimer() {
		return timer;
	}

	public long getlastTimeRendered() {
		return lastTimeRendered;
	}

	public void setlastTimeRendered(long lastTimeRendered) {
		this.lastTimeRendered = lastTimeRendered;
	}
}
