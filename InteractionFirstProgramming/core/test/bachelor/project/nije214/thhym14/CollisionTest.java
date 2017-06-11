package bachelor.project.nije214.thhym14;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nicolai on 10-05-2017.
 */
public class CollisionTest extends GameTest {
    @Test
    public void isColliding() throws Exception {
        Enemy e = new Enemy();
        Bullet b = new Bullet();
        Collision c = new Collision();

        e.createEnemy("badlogic.jpg");
        e.setX(100);
        e.setY(100);

        b.createBullet("badlogic.jpg");
        b.setX(100);
        b.setY(100);

        assertTrue(c.isColliding(e.getSprite().getBoundingRectangle(), b.getSprite().getBoundingRectangle()));
    }

    @Test
    public void isNotColliding() throws Exception {
        Enemy e = new Enemy();
        Bullet b = new Bullet();
        Collision c = new Collision();

        e.createEnemy("playButton.jpg");
        e.setX(100);
        e.setY(100);

        b.createBullet("playButton.jpg");
        b.setX(100);
        b.setY(400);



        assertFalse(c.isColliding(e.getSprite().getBoundingRectangle(), b.getSprite().getBoundingRectangle()));
    }
}