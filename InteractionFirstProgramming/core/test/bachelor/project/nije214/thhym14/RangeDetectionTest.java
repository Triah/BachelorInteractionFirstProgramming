package bachelor.project.nije214.thhym14;


import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Nicolai on 10-05-2017.
 */
public class RangeDetectionTest extends GameTest {
    @Test
    public void simpleRangeCheckInRange() throws Exception {
        RangeDetection rd = new RangeDetection();
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

        Assert.assertTrue(rd.simpleRangeCheck(e, t, sightradius));
    }


    @Test
    public void simpleRangeCheckOutOfRange() throws Exception {
        RangeDetection rd = new RangeDetection();
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

        Assert.assertFalse(rd.simpleRangeCheck(e, t, sightradius));
    }

}