package de.sebdas;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloArc extends Application {

    private static final double SCENE_WIDTH = 600.0;
    private static final double SCENE_HEIGHT = 400.0;

    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Arc Demo");

        final Button button1 = new Button("Switch to scene 2");
        final Button button2 = new Button("Switch to scene 1");

        final Scene scene1 = scene1(button1);
        final Scene scene2 = scene2(button2);

        button1.setOnAction(event -> primaryStage.setScene(scene2));
        button2.setOnAction(event -> primaryStage.setScene(scene1));

        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    private Scene scene1(final Button... buttons) {
        final Group root = new Group();

        final FlowPane buttonGroup = new FlowPane();
        buttonGroup.getChildren().addAll(buttons);

        final Group progressGroup = new Group();
        root.getChildren().addAll(buttonGroup, progressGroup);
        final Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.LIGHTBLUE);

        final Circle backgroundCircle = new Circle(100.0, 100.0, 50.0);
        backgroundCircle.setFill(Color.TRANSPARENT);
        backgroundCircle.setStroke(Color.GRAY);
        backgroundCircle.setStrokeWidth(10.0);

        final Arc valueArc = new Arc(100.0, 100.0, 50.0, 50.0, 90.0, -270.0);
        progressGroup.setTranslateX(100);
        progressGroup.setTranslateY(100);

        valueArc.setType(ArcType.OPEN);
        valueArc.setFill(Color.TRANSPARENT);
        valueArc.setStroke(Color.CORNFLOWERBLUE);
        valueArc.setStrokeWidth(10.0);
        valueArc.setStrokeLineCap(StrokeLineCap.ROUND);

        final Slider slider = new Slider();

        valueArc.lengthProperty().bind(slider.valueProperty().negate().multiply(3.6));

        final Line lineForDebugging = new Line(100.0, 0.0, 100.0, 100.0);
        progressGroup.getChildren().add(lineForDebugging);

        final Group progressIndicatorGroup = new Group();
        progressIndicatorGroup.getChildren().addAll(backgroundCircle, valueArc);

        progressGroup.getChildren().addAll(progressIndicatorGroup, slider);
        return scene;
    }

    private Scene scene2(final Button... buttons) {
        final Group root = new Group();

        final FlowPane buttonGroup = new FlowPane();
        buttonGroup.getChildren().addAll(buttons);
        final Button rotationButton = new Button("Toggle rotation");
        buttonGroup.getChildren().add(rotationButton);

        final Group progressGroup = new Group();
        root.getChildren().addAll(buttonGroup, progressGroup);
        final Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.LIGHTCORAL);

        final Circle backgroundCircle = new Circle(100.0, 100.0, 50.0);
        backgroundCircle.setFill(Color.TRANSPARENT);
        backgroundCircle.setStroke(Color.GRAY);
        backgroundCircle.setStrokeWidth(10.0);

        final Arc valueArc = new Arc(100.0, 100.0, 50.0, 50.0, 90.0, -180.0);
        progressGroup.setTranslateX(100);
        progressGroup.setTranslateY(100);

        valueArc.setType(ArcType.OPEN);
        valueArc.setFill(Color.TRANSPARENT);
        valueArc.setStroke(Color.CORNFLOWERBLUE);
        valueArc.setStrokeWidth(10.0);
        valueArc.setStrokeLineCap(StrokeLineCap.ROUND);

        final Line lineForDebugging = new Line(100.0, 0.0, 100.0, 100.0);
        progressGroup.getChildren().add(lineForDebugging);

        final Group progressIndicatorGroup = new Group();
        progressIndicatorGroup.getChildren().addAll(backgroundCircle, valueArc);

        final RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000.0), progressIndicatorGroup);
        rotateTransition.setByAngle(360.0);
        rotateTransition.setCycleCount(Integer.MAX_VALUE);
//        rotateTransition.setOnFinished(event -> rotateTransition.play());
        rotateTransition.setInterpolator(Interpolator.LINEAR);
//        rotateTransition.play();
        rotationButton.setOnAction(event -> {
            if (rotateTransition.getStatus() == Animation.Status.RUNNING) {
                rotateTransition.pause();
            } else {
                rotateTransition.play();
            }
        });
        rotateTransition.play();

        progressGroup.getChildren().add(progressIndicatorGroup);
        return scene;
    }
}
