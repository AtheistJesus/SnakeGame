package atheistjesus.snakegame;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Snake {
    public List<Rectangle> body = new ArrayList<>();
    public Rectangle head;
    private final Rectangle partOne, partTwo;
    private final double size = 19.9;

    public Snake() {
        Rectangle head = new Rectangle(280, 180, size, size);
        partOne = new Rectangle(260, 180, size, size);
        partTwo = new Rectangle(240, 180, size, size);
        head.setFill(Color.DARKGREEN);
        partOne.setFill(Color.GREEN);
        partTwo.setFill(Color.GREEN);
        body.add(head);
        body.add(partOne);
        body.add(partTwo);
        this.head = head;
    }

    public Rectangle tail() {
        return body.get(body.size() - 1);
    }

    public Rectangle grow() {
        Rectangle newPart = new Rectangle(tail().getX(), tail().getY(), size, size);
        newPart.setFill(Color.GREEN);
        body.add(newPart);
        return newPart;
    }

    public boolean hasCollided() {
        for (int i = 2; i < body.size(); i++) {
            if (body.get(0).getBoundsInParent().intersects(body.get(i).getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    public void move() {
        for (int i = body.size() - 1; i >= 1; i--) {
            body.get(i).setX(body.get(i - 1).getX());
            body.get(i).setY(body.get(i - 1).getY());
        }
    }

    public List<Rectangle> reset() {
        List<Rectangle> removed = new ArrayList<>();
        for (int i = 3; i < body.size(); i++) {
            removed.add(body.get(i));
        }
        if (body.size() > 3) {
            body.subList(3, body.size()).clear();
        }
        head.setX(280);
        head.setY(180);
        partOne.setX(260);
        partOne.setY(180);
        partTwo.setX(240);
        partTwo.setY(180);

        return removed;
    }
}