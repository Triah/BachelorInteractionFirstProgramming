package bachelor.project.nije214.thhym14;


import com.badlogic.gdx.math.Rectangle;

/**
 * Authors:
 * Nicolai Hedegaard Jensen <nije214@student.sdu.dk>
 * Thor Skou Hymøller <thhym14@student.sdu.dk>
 */
public class Collision {

    private Rectangle bounds;

    public boolean isColliding(Rectangle rect1, Rectangle rect2){
        bounds = new Rectangle(rect2.getX(), rect2.getY(), rect2.getWidth(), rect2.getHeight());
        return rect1.overlaps(bounds);
    }
}
