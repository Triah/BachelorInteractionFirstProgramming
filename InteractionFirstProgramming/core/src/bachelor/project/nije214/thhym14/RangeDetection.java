package bachelor.project.nije214.thhym14;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;


/**
 * Authors:
 * Nicolai Hedegaard Jensen <nije214@student.sdu.dk>
 * Thor Skou Hymøller <thhym14@student.sdu.dk>
 */

public class RangeDetection {

    public boolean simpleRangeCheck(Enemy enemy, Tower tower, int sightRadius) {
        Vector2 enemyvec = new Vector2(enemy.getX(), enemy.getY());
        Vector2 towervec = new Vector2(tower.getX(), tower.getY());
        return enemyvec.dst(towervec)< sightRadius;
    }
}
