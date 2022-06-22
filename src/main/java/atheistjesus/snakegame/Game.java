package atheistjesus.snakegame;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
// import javafx.util.Duration;

public class Game extends Application {
    Snake snake = new Snake();
    public int score = 0;
    public int highScore = loadHighScore();
    char direction = '\u0000';

    public Game() throws IOException {}

    @Override
    public void start(Stage stage) {
        stage.setTitle("Snake Game");
        TranslateTransition transition = new TranslateTransition();
        Group layout = new Group();
        Scene grass = new Scene(layout, 440, 480);
        Group deathLayout = new Group();
        Scene gameOver = new Scene(deathLayout, 400, 400);
        gameOver.setFill(Color.BLACK);
        Label deathMessage = new Label("Game over!");
        deathMessage.setScaleX(5);
        deathMessage.setScaleY(5);
        deathMessage.setLayoutX(175);
        deathMessage.setLayoutY(100);
        deathMessage.setTextFill(Color.RED);
        Label finalScore = new Label();
        finalScore.setScaleX(4);
        finalScore.setScaleY(5);
        finalScore.setLayoutX(170);
        finalScore.setLayoutY(200);
        finalScore.setTextFill(Color.RED);
        deathLayout.getChildren().addAll(deathMessage, finalScore);
        Label scoreLabel = new Label("Score: " +  score);
        scoreLabel.setScaleX(2);
        scoreLabel.setScaleY(2);
        scoreLabel.setTranslateX(30);
        scoreLabel.setTranslateY(10);
        scoreLabel.setTextFill(Color.BLACK);
        Label highScoreLabel = new Label("High score: " + highScore);
        highScoreLabel.setScaleX(2);
        highScoreLabel.setScaleY(2);
        highScoreLabel.setTranslateX(250);
        highScoreLabel.setTranslateY(10);
        highScoreLabel.setTextFill(Color.BLACK);
        grass.setFill(Color.LIGHTGREEN);
        Rectangle scoreBoard = new Rectangle(0, 0, 440, 40);
        scoreBoard.setFill(Color.GREEN);
        layout.getChildren().addAll(scoreBoard);
        Rectangle border1 = new Rectangle(20, 39.9, 400, 20);
        border1.setFill(Color.BLACK);
        Rectangle border2 = new Rectangle(-0.1, 40, 20, 440);
        border2.setFill(Color.BLACK);
        Rectangle border3 = new Rectangle(420.1, 40, 20, 440);
        border3.setFill(Color.BLACK);
        Rectangle border4 = new Rectangle(20, 460.1, 400, 20);
        border4.setFill(Color.BLACK);
        layout.getChildren().addAll(border1, border2, border3, border4);
        transition.setNode(snake.head);
        drawVerticalLines(layout, grass);
        drawHorizontalLines(layout, grass);
        Circle apple = new Circle(330, 190, 9, Color.RED);
        for (Rectangle rect : snake.body) {
            layout.getChildren().add(rect);
        }
        layout.getChildren().addAll(apple, scoreLabel, highScoreLabel);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (direction == 'w') {
                    snake.move();
                    try {
                        Thread.sleep(100);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    snake.head.setY(snake.head.getY() - 20);
                }
                else if (direction == 'a') {
                    snake.move();
                    try {
                        Thread.sleep(100);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    snake.head.setX(snake.head.getX() - 20);
                }
                else if (direction == 's') {
                    snake.move();
                    try {
                        Thread.sleep(100);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    snake.head.setY(snake.head.getY() + 20);
                }
                else if (direction == 'd') {
                    snake.move();
                    try {
                        Thread.sleep(100);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    snake.head.setX(snake.head.getX() + 20);
                }
                if (snake.head.getBoundsInParent().intersects(apple.getBoundsInParent())) {
                    layout.getChildren().add(snake.grow());
                    int xPos = 30 + (int)(Math.random() * 20) * 20;
                    int yPos = 70 + (int)(Math.random() * 20) * 20;
                    apple.setCenterX(xPos);
                    apple.setCenterY(yPos);
                    score++;
                    scoreLabel.setText("Score: " + score);
                    if (score > highScore) {
                        highScore++;
                        try {
                            saveHighScore();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    highScoreLabel.setText("High score: " + highScore);
                }
                if (snake.hasCollided() || snake.head.getBoundsInParent().intersects(border1.getBoundsInParent())
                || snake.head.getBoundsInParent().intersects(border2.getBoundsInParent()) ||
                        snake.head.getBoundsInParent().intersects(border3.getBoundsInParent()) ||
                                snake.head.getBoundsInParent().intersects(border4.getBoundsInParent())) {
                    transition.stop();
                    finalScore.setText("Final score: " + score);
                    stage.setScene(gameOver);
                }
            }
        };
        grass.setOnKeyPressed(keyEvent -> {
            timer.start();
            if (keyEvent.getCode() == KeyCode.W && direction != 's' && direction != 'w') {
                direction = 'w';
                /*
                transition.stop();
                transition.setByX(0);
                transition.setByY(-400);
                transition.setDuration(Duration.seconds(5));
                transition.play();

                 */
            }
            else if (keyEvent.getCode() == KeyCode.A && direction != 'd' && direction != 'a' && direction != '\u0000') {
                direction = 'a';
                /*
                transition.stop();
                transition.setByX(-400);
                transition.setByY(0);
                transition.setDuration(Duration.seconds(5));
                transition.play();

                 */
            }
            else if (keyEvent.getCode() == KeyCode.S && direction != 'w' && direction != 's') {
                direction = 's';
                /*
                transition.stop();
                transition.setByX(0);
                transition.setByY(400);
                transition.setDuration(Duration.seconds(5));
                transition.play();

                 */
            }
            else if (keyEvent.getCode() == KeyCode.D && direction != 'a' && direction != 'd') { // OVER HERE!!!!!!!!!!
                direction = 'd';
                /*
                transition.stop();
                transition.setByX(400);
                transition.setByY(0);
                transition.setDuration(Duration.seconds(5));
                transition.play();

                 */
            }
        });
        Button playAgain = new Button("Play Again");
        playAgain.setLayoutX(160);
        playAgain.setLayoutY(300);
        playAgain.setStyle("-fx-background-color: black; -fx-border-color: red;" +
                "-fx-text-fill: red");
        playAgain.setOnAction(actionEvent -> {
            timer.stop();
            direction = '\u0000';
            score = 0;
            scoreLabel.setText("Score: " + score);
            apple.setCenterX(330);
            apple.setCenterY(190);
            stage.setScene(grass);
            layout.getChildren().removeAll(snake.reset());
        });
        deathLayout.getChildren().add(playAgain);
        stage.setScene(grass);
        stage.show();
    }

    private void drawHorizontalLines(Group layout, Scene scene) {
        for (int i = 60; i < scene.getHeight(); i += 20) {
            Line line = new Line();
            line.setStartX(20);
            line.setEndX(420);
            line.setLayoutY(i);
            layout.getChildren().add(line);
        }
    }

    private void drawVerticalLines(Group layout, Scene scene) {
        for (int i = 20; i < scene.getWidth(); i += 20) {
            Line line = new Line();
            line.setLayoutX(i);
            line.setStartY(60);
            line.setEndY(460);
            layout.getChildren().add(line);
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public void saveHighScore() throws IOException {
        File highScores = new File("src/main/java/HighScore.txt");
        FileWriter writer = new FileWriter(highScores);
        writer.write(highScore + "");
        writer.close();
    }

    public int loadHighScore() throws IOException {
        List<String> file = Files.readAllLines(Path.of("src/main/java/HighScore.txt"));
        return Integer.parseInt(file.get(0));
    }
}