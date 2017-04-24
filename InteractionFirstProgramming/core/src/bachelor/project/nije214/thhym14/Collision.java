package bachelor.project.nije214.thhym14;

/**
 * Created by Administrator on 14-04-2017.
 */

public class Collision {

// TODO: 14-04-2017  nullpointer ud over det hele

    public boolean simpleCircleCollision(Bullet b, Enemy e) {
        double xDif = e.getSprite().getX() - b.getSprite().getX();
        double yDif = e.getSprite().getY() - b.getSprite().getY();
        double distanceSquared = Math.pow(xDif, 2) + Math.pow(yDif, 2);
        return distanceSquared < Math.pow(e.getSprite().getWidth() + b.getSprite().getWidth(), 2);
    }

}
