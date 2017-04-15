package bachelor.project.nije214.thhym14;

/**
 * Created by Administrator on 14-04-2017.
 */

public class Collision {

// TODO: 14-04-2017  nullpointer ud over det hele

    public boolean simpleCircleCollision(Bullet b, Enemy e) {
        double xDif = e.getX() - b.getX();
        double yDif = e.getY() - b.getY();
        double distanceSquared = Math.pow(xDif, 2) + Math.pow(yDif, 2);
        return distanceSquared < Math.pow(e.getWidth() + b.getWidth(), 2);
    }

}
