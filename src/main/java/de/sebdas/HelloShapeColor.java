package de.sebdas;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static java.util.Arrays.asList;

public class HelloShapeColor extends Application {

    private static final double SCENE_WIDTH = 600.00;
    private static final double SCENE_HEIGHT = 400.00;

    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Shape Demo");

        final Group root = new Group();

        final Group controlGroup = new Group();
        final Slider slider = new Slider();
        slider.setValue(75.0);
        controlGroup.getChildren().add(slider);

        final Group progressGroup = new Group();

        final double startX = 50.0;
        final double startY = 50.0;
        final double minWidth = 0.0;
        final double maxWidth = 200.0;
        final double fixedHeight = 100.0;
        final double arcSize = 25.0;

        final Rectangle backgroundBar = new Rectangle(startX, startY, maxWidth, fixedHeight);
        backgroundBar.setFill(Color.GRAY);
        backgroundBar.setStroke(Color.TRANSPARENT);
        backgroundBar.setArcWidth(arcSize);
        backgroundBar.setArcHeight(arcSize);

        final Rectangle valueBar = new Rectangle(startX, startY, minWidth, fixedHeight);
        valueBar.setFill(Color.CORNFLOWERBLUE);
        valueBar.setStroke(Color.TRANSPARENT);
        valueBar.setArcWidth(arcSize);
        valueBar.setArcHeight(arcSize);

        final double frontWidth = 100.0;
        final double frontHeight = fixedHeight / 2.0;
        final double frontX = 100.0;
        final double frontY = startY + (fixedHeight - frontHeight) / 2.0;
        final Rectangle foregroundRectangle = new Rectangle(frontX, frontY, frontWidth, frontHeight);
        foregroundRectangle.setStroke(Color.TRANSPARENT);
        foregroundRectangle.setArcWidth(arcSize);
        foregroundRectangle.setArcHeight(arcSize);

        final Text text = new Text("Foo bar baz");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 18.0));
        text.setStroke(Color.TRANSPARENT);
        text.setFill(Color.BLACK);
        text.setAccessibleHelp("this is foo bar baz");
        text.setX(100.0);
        text.setY(100.0);

//        progressGroup.getChildren().addAll(backgroundBar, valueBar, foregroundRectangle);
        progressGroup.getChildren().addAll(backgroundBar, valueBar, text);

        final DoubleProperty progressValue = new SimpleDoubleProperty(75.0);
        progressGroup.setOnMouseDragged(event -> {
            final double x = clamp(event.getX(), startX, startX + maxWidth);
            final double normalizedX = (x - startX) * 100.0 / maxWidth;
            System.out.println(x + " " + normalizedX);
            progressValue.set(normalizedX);
        });
        progressValue.bindBidirectional(slider.valueProperty());
        valueBar.widthProperty().bind(progressValue.multiply(2.0));

        progressValue.addListener((observable, oldValue, newValue) -> {
            final LinearGradient linearGradient = createLinearGradient(pivot(observable), startX, maxWidth);
//            foregroundRectangle.setFill(linearGradient);
            text.setFill(linearGradient);
        });
//        foregroundRectangle.setFill(createLinearGradient(pivot(progressValue), startX, maxWidth));
        text.setFill(createLinearGradient(pivot(progressValue), startX, maxWidth));

        root.getChildren().addAll(controlGroup, progressGroup);

        final Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.LIGHTGRAY);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private double clamp(final double x, final double lowerBoundInclusive, final double upperBoundInclusive) {
        return Math.min(Math.max(lowerBoundInclusive, x), upperBoundInclusive);
    }

    private double pivot(final ObservableValue<? extends Number> progressValue) {
        return progressValue.getValue().doubleValue() * 0.01;
    }

    private LinearGradient createLinearGradient(final double pivot, final double startX, final double maxWidth) {
        return new LinearGradient(
                startX, 0.0, startX + maxWidth, 0.0,
                false, CycleMethod.NO_CYCLE,
                asList(
                        new Stop(0.0, Color.WHITE),
                        new Stop(pivot, Color.WHITE),
                        new Stop(pivot + 0.01, Color.BLACK),
                        new Stop(1.0, Color.BLACK)
                ));
    }
}
