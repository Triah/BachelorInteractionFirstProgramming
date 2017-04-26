package bachelor.project.nije214.thhym14;


import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Administrator on 14-04-2017.
 */

public class Collision {

    private Rectangle bounds;

    public boolean isColliding(Rectangle enemy, Rectangle bullet){
        bounds = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());

        return enemy.overlaps(bounds);
    }
}
