package bachelor.project.nije214.thhym14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;


import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nicolai on 10-05-2017.
 */
public class RaycastTest extends GameTest {
    @Test
    public void simpleRangeCheckInRange() throws Exception {
        Raycast ray = new Raycast();
        Enemy e = new Enemy();
        e.createEnemy("spider.png");
        e.getSprite().setSize(0,0);
        e.setX(300);
        e.setY(100);
        Tower t = new Tower();
        t.createTower("spider.png");
        t.getSprite().setSize(0,0);
        t.setX(100);
        t.setY(100);
        int sightradius = 200;

        Assert.assertTrue(ray.simpleRangeCheck(e, t, sightradius));
    }


    @Test
    public void simpleRangeCheckOutOfRange() throws Exception {
        Raycast ray = new Raycast();
        Enemy e = new Enemy();
        e.createEnemy("spider.png");
        e.getSprite().setSize(0,0);
        e.setX(300);
        e.setY(100);
        Tower t = new Tower();
        t.createTower("spider.png");
        t.getSprite().setSize(0,0);
        t.setX(100);
        t.setY(100);
        int sightradius = 199;

        Assert.assertFalse(ray.simpleRangeCheck(e, t, sightradius));
    }

}