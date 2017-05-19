package bachelor.project.nije214.thhym14;


import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Administrator on 14-04-2017.
 */

public class Collision {

    private Rectangle bounds;

    public boolean isColliding(Rectangle rect1, Rectangle rect2){
        bounds = new Rectangle(rect2.getX(), rect2.getY(), rect2.getWidth(), rect2.getHeight());
        return rect1.overlaps(bounds);
    }
}
