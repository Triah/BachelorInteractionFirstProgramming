package bachelor.project.nije214.thhym14;


import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Administrator on 14-04-2017.
 */

public class Collision {

    private Rectangle bounds;

// TODO: 14-04-2017  nullpointer ud over det hele

    public boolean simpleCircleCollision(Bullet b, Enemy e) {
        double xDif = e.getSprite().getX() - b.getSprite().getX();
        double yDif = e.getSprite().getY() - b.getSprite().getY();
        double distanceSquared = Math.pow(xDif, 2) + Math.pow(yDif, 2);
        return distanceSquared < Math.pow(e.getSprite().getWidth() + b.getSprite().getWidth(), 2);
    }


    public boolean isColliding(Rectangle enemy, Rectangle bullet){
        bounds = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());

        return enemy.overlaps(bounds);
    }
























}
