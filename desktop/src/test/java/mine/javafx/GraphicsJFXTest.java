package mine.javafx;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import mine.paint.MineColor;
import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;

public class GraphicsJFXTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Canvas canvas = new Canvas(640, 480);
		StackPane pane = new StackPane();
		pane.getChildren().addAll(canvas);

        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
		MineGraphics g = new GraphicsJFX(gc);
		
		fillRectTest(g);
		
		
		drawImageTest(pane);
		
		//createShape(layout);
		
		//rotateShape(pane);
	}
	
	public static void main(String... args) {
		launch(args);
	}
	
	public void fillRectTest(MineGraphics g) {
		g.setColor(MineColor.RED);
		g.fillRect(10, 10, 100, 200);	
	}
	
	public void drawImageTest(Pane root) throws Exception {
		MineImageLoader mil = new ImageLoaderJFX();
		
		MineImage img = mil.load("mine/image/new.png");
		
		ImageView view = new ImageView();
		view.setImage((Image)img.getImage());
		view.setLayoutX(10);
		view.setLayoutY(10);
		root.getChildren().add(view);
		
	    RotateTransition rt = new RotateTransition(new Duration(1000), view);
	    rt.setFromAngle(0);
	    rt.setToAngle(360);
	    rt.play();
	}
	
	public void createShape(Pane root){
	    Rectangle r = new Rectangle(20, 20, 50, 50);
	    r.setFill(Color.CYAN);
	    root.getChildren().add(r);
	     
	    TranslateTransition tt = new TranslateTransition(new Duration(1000), r);
	    tt.setFromX(20);
	    tt.setToX(100);
	    tt.setAutoReverse(true);
	    tt.setCycleCount(10);
	    tt.play();
	}
	
	public void rotateShape(Pane root){
	    Rectangle r = new Rectangle(210, 210, 50, 50);
	    r.setFill(Color.FUCHSIA);
	    root.getChildren().add(r);
	     
	    RotateTransition rt = new RotateTransition(new Duration(1000), r);
	    rt.setFromAngle(0);
	    rt.setToAngle(360);
	    rt.play();
	   
	}
}
