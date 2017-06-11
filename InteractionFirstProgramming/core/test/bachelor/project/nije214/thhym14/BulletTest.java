package bachelor.project.nije214.thhym14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nicolai on 10-05-2017.
 */
public class BulletTest extends GameTest {

    @Test
    public void setVelocity() throws Exception {
        Bullet b = new Bullet();
        b.createBullet("badlogic.jpg");
        b.setSpeed(100);
        b.setX(100);
        b.setY(100);

        Enemy e = new Enemy();
        e.createEnemy("badlogic.jpg");
        e.setSpeed(100);
        e.setCenter(100,100);

        Tower t = new Tower();
        t.createTower("badlogic.jpg");
        t.setCenter(200,200);

        float angle = (float) Math.atan2(e.getY() - t.getY() + e.getSprite().getHeight()/2,
                e.getX() - t.getX()+e.getSprite().getWidth()/2);

        b.setVelocity(angle, b.getSpeed());

        Vector2 velo = new Vector2((float) Math.cos(angle) * b.getSpeed(), (float) Math.sin(angle) * b.getSpeed());

        assertEquals(b.getVelocity(), velo);
    }

    @Test
    public void setBulletPosition() throws Exception {
// TODO: 11-05-2017 nicolailul
    }

    @Test
    public void getTowerToEnemyAngle() throws Exception {
        Enemy e = new Enemy();
        e.createEnemy("playButton.jpg");
        e.setCenter(500,1000);
        //e.setX(500);
        //e.setY(1000);

        Tower t = new Tower();
        t.createTower("playButton.jpg");
        t.setCenter(1000,500);
        //t.setX(1000);
        //t.setY(500);

        System.out.println(e.getY() - t.getY() + e.getSprite().getHeight()/2);
        System.out.println(e.getX() - t.getX()+e.getSprite().getWidth()/2);
        float bulletAngle = (float) Math.atan2(e.getY() - t.getY(),
                e.getX() - t.getX());

        System.out.println(Math.abs((Math.toDegrees(bulletAngle))));
        assertTrue(Math.round(Math.abs(Math.toDegrees(bulletAngle))) == 135f);


        // TODO: 11-05-2017 find ud af hvordan fuck det virker 

    }

}