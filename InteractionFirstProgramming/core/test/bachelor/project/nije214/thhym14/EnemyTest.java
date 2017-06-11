package bachelor.project.nije214.thhym14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nicolai on 10-05-2017.
 */
public class EnemyTest extends GameTest {

    @Test
    public void getAngle() throws Exception {
        Enemy e = new Enemy();
        e.createEnemy("badlogic.jpg");
        e.setCenter(50, 100);

        Waypoint wp = new Waypoint();
        wp.createPath(new Array<Vector2>());
        wp.addPathNode(new Vector2(100,50));

        float pathangle =(float) Math.atan2(wp.getPath().get(0).y - e.getY() - e.getSprite().getHeight()/2, wp.getPath().get(0).x - e.getX() - e.getSprite().getWidth()/2);

        assertTrue(Math.round(Math.abs(Math.toDegrees(pathangle))) == 45f);
    }

    @Test
    public void getVelocity() throws Exception {
        Enemy e = new Enemy();
        e.createEnemy("badlogic.jpg");
        e.setSpeed(100);
        e.setX(50);
        e.setY(100);

        Waypoint wp = new Waypoint();
        wp.createPath(new Array<Vector2>());
        wp.addPathNode(new Vector2(100,50));
        float angle =(float) Math.atan2(wp.getPath().get(0).y - e.getY(), wp.getPath().get(0).x - e.getX());

        e.setVelocity(angle, e.getSpeed());


        // TODO: 10-05-2017  forklar mat i rapport

        Vector2 velo = new Vector2((float) Math.cos(angle) * e.getSpeed(), (float) Math.sin(angle) * e.getSpeed());

        assertEquals(e.getVelocity(), velo);

    }

    @Test
    public void isWaypointReached() throws Exception {
        Enemy e = new Enemy();
        e.createEnemy("playButton.jpg");
        e.setSpeed(100);
        e.setCenter(0,0);
        e.getSprite().setOriginCenter();

        Waypoint wp = new Waypoint();
        wp.createPath(new Array<Vector2>());
        wp.addPathNode(new Vector2(100,100));

        boolean isReached = Math.abs(wp.getPath().get(0).x - e.getX()-e.getSprite().getHeight()/2) <=
                e.getSpeed()  &&
                Math.abs(wp.getPath().get(0).y - e.getY()-e.getSprite().getWidth()/2) <=
                        e.getSpeed();


        assertTrue(isReached);
    }

    @Test
    public void isWaypointNotReached() throws Exception {
        Enemy e = new Enemy();
        e.createEnemy("playButton.jpg");
        e.setSpeed(99);
        e.setCenter(0,0);
        e.getSprite().setOriginCenter();

        Waypoint wp = new Waypoint();
        wp.createPath(new Array<Vector2>());
        wp.addPathNode(new Vector2(100,100));

        boolean isReached = Math.abs(wp.getPath().get(0).x - e.getX()-e.getSprite().getHeight()/2) <=
                e.getSpeed()  &&
                Math.abs(wp.getPath().get(0).y - e.getY()-e.getSprite().getWidth()/2) <=
                        e.getSpeed();

        assertFalse(isReached);
    }

    @Test
    public void setVelocitySilly() throws Exception {
        Enemy e = new Enemy();
        e.createEnemy("playButton.jpg");
        e.setSpeed(100);
        e.setX(50);
        e.setY(100);

        Waypoint wp = new Waypoint();
        wp.createPath(new Array<Vector2>());
        wp.addPathNode(new Vector2(100,50));
        float angle =(float) Math.atan2(wp.getPath().get(0).y - e.getY(), wp.getPath().get(0).x - e.getX());

        e.setVelocitySilly(angle, e.getSpeed());


        // TODO: 10-05-2017  forklar mat i rapport

        Vector2 velo = new Vector2((float) Math.cos(angle+1) * e.getSpeed(), (float) Math.sin(angle+1) * e.getSpeed());

        assertEquals(e.getVelocity(), velo);

    }

}