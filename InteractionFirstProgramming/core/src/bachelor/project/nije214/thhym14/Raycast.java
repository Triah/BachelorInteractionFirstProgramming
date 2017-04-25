package bachelor.project.nije214.thhym14;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Point2D;

/**
 * Created by Administrator on 14-04-2017.
 */

public class Raycast {


    private Point enemy;
    private Point tower;
    private int i = 0;
   //private ShapeRenderer sr;


    /*
    HACKET LØSNING
    TODO: 14-04-2017  der er lige pt. 2 forskellige 'raycast' 1 legit og 1 hack. Test hvad der virker bedst/er nødvendigt

    */
    public boolean rangeCheckHack(Enemy enemy, Tower tower, int sightRadius) {
        Vector2 enemyvec = new Vector2(enemy.getX(), enemy.getY());
        Vector2 towervec = new Vector2(tower.getX(), tower.getY());
        return enemyvec.dst(towervec)< sightRadius ;
    }
    /**
     * Set the player as a Point based on the given Entity's position.
     *
     * @param tower The Entity, whose coordinates should be used to set the player
     */
    private void setTower(Tower tower) {
        this.tower = new Point(tower.getX() + tower.getSprite().getWidth()/2, tower.getY() + tower.getSprite().getHeight()/2);
    }

    /**
     * Set the enemy as a Point based on the given Entity's position.
     *
     * @param enemy The Entity, whose coordinates should be used to set the enemy
     */
    private void setEnemy(Enemy enemy) {
        this.enemy = new Point(enemy.getX() + enemy.getSprite().getWidth()/2, enemy.getY()+enemy.getSprite().getHeight()/2);
    }

    /**
     * Check if the player is in the given x and y coordinates
     *
     * @param x The x position to check as an integer
     * @param y The y position to check as an integer
     * @return true if the player is in the given coordinates, false otherwise
     */
    private boolean isEnemyInField(float x, float y) {
        return (Math.abs(x - enemy.getX()) <= 10) && (Math.abs(y - enemy.getY()) <= 10);
    }

    /**
     * Checks if the player is in the enemy's sight, given the enemy's sightRadius
     *
     * @param enemy An instance of Entity, representing the enemy
     * @param tower An instance of Entity, representing the player
     * @param sightRadius The distance, the enemy can see
     * @return true if the enemy can see the player, false otherwise
     */
    public boolean isPlayerInSight(Enemy enemy, Tower tower, int sightRadius) {
        setTower(tower);
        setEnemy(enemy);
        return makeRays(0, 360, sightRadius);
    }


    /*
    public void circleshape(Vector2 vec) {

        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.setColor(Color.CYAN);
        sr.begin();
        //for(Tower t : tower.getTowerArray){
        sr.circle(vec.x, vec.y, 250);
        Gdx.gl.glLineWidth((2));
        sr.end();
        //Vector2 vec1 = new Vector2(tower.getX(), tower.getY());
        //circleshape(vec1);
    }
    */




    /**
     * Cast rays ranging from the startAngle to the endAngle every 4 degrees.
     * The ray range is defined by the sightRadius. This code is optimized by
     * using pre-calculated arrays for sine and cosine, since accessing arrays
     * is inexpensive. If the ray hits the player, this method returns true.
     *
     * @param startAngle At which angle to start casting
     * @param endAngle At which angle to stop casting
     * @param sightRadius How far the rays should go
     * @return true if player is found, false otherwise
     */
    private boolean makeRays(int startAngle, int endAngle, int sightRadius) {
        this.i = 0;
        float px = tower.getX();
        float py = tower.getY();
        int degreeLeap = 45;

        // Iterate through the angles
        for (int i = startAngle; i < endAngle; i += degreeLeap) {
            double ax = sintable[i];
            double ay = costable[i];

            double x = px;
            double y = py;

            // Iterate through the length of the ray
            for (int j = 0; j < sightRadius; j++) {
                x += ax;
                y += ay;



                float roundedX = Math.round(x);
                float roundedY = Math.round(y);


                // If ray is out of range, continue to next ray
                if (roundedX < 0 || roundedY < 0) {
                    break;
                }

                // If we found the player, return true
                if (isEnemyInField(roundedX, roundedY)) {

                    Vector2 vec1 = new Vector2(tower.getX(), tower.getY());
                    //circleshape(vec1);
                    Vector2 vec2 = new Vector2(roundedX, roundedY);
                    System.out.println(vec1.dst(vec2));

                    return true;
                }
            }
        }
        // No player was found - return false
        return false;
    }

    //<editor-fold defaultstate="collapsed" desc="Pre-calculated sin/cos tables">
    private static final double[] sintable = new double[]{
            0.00000, 0.01745, 0.03490, 0.05234, 0.06976, 0.08716, 0.10453,
            0.12187, 0.13917, 0.15643, 0.17365, 0.19081, 0.20791, 0.22495, 0.24192,
            0.25882, 0.27564, 0.29237, 0.30902, 0.32557, 0.34202, 0.35837, 0.37461,
            0.39073, 0.40674, 0.42262, 0.43837, 0.45399, 0.46947, 0.48481, 0.50000,
            0.51504, 0.52992, 0.54464, 0.55919, 0.57358, 0.58779, 0.60182, 0.61566,
            0.62932, 0.64279, 0.65606, 0.66913, 0.68200, 0.69466, 0.70711, 0.71934,
            0.73135, 0.74314, 0.75471, 0.76604, 0.77715, 0.78801, 0.79864, 0.80902,
            0.81915, 0.82904, 0.83867, 0.84805, 0.85717, 0.86603, 0.87462, 0.88295,
            0.89101, 0.89879, 0.90631, 0.91355, 0.92050, 0.92718, 0.93358, 0.93969,
            0.94552, 0.95106, 0.95630, 0.96126, 0.96593, 0.97030, 0.97437, 0.97815,
            0.98163, 0.98481, 0.98769, 0.99027, 0.99255, 0.99452, 0.99619, 0.99756,
            0.99863, 0.99939, 0.99985, 1.00000, 0.99985, 0.99939, 0.99863, 0.99756,
            0.99619, 0.99452, 0.99255, 0.99027, 0.98769, 0.98481, 0.98163, 0.97815,
            0.97437, 0.97030, 0.96593, 0.96126, 0.95630, 0.95106, 0.94552, 0.93969,
            0.93358, 0.92718, 0.92050, 0.91355, 0.90631, 0.89879, 0.89101, 0.88295,
            0.87462, 0.86603, 0.85717, 0.84805, 0.83867, 0.82904, 0.81915, 0.80902,
            0.79864, 0.78801, 0.77715, 0.76604, 0.75471, 0.74314, 0.73135, 0.71934,
            0.70711, 0.69466, 0.68200, 0.66913, 0.65606, 0.64279, 0.62932, 0.61566,
            0.60182, 0.58779, 0.57358, 0.55919, 0.54464, 0.52992, 0.51504, 0.50000,
            0.48481, 0.46947, 0.45399, 0.43837, 0.42262, 0.40674, 0.39073, 0.37461,
            0.35837, 0.34202, 0.32557, 0.30902, 0.29237, 0.27564, 0.25882, 0.24192,
            0.22495, 0.20791, 0.19081, 0.17365, 0.15643, 0.13917, 0.12187, 0.10453,
            0.08716, 0.06976, 0.05234, 0.03490, 0.01745, 0.00000, -0.01745, -0.03490,
            -0.05234, -0.06976, -0.08716, -0.10453, -0.12187, -0.13917, -0.15643,
            -0.17365, -0.19081, -0.20791, -0.22495, -0.24192, -0.25882, -0.27564,
            -0.29237, -0.30902, -0.32557, -0.34202, -0.35837, -0.37461, -0.39073,
            -0.40674, -0.42262, -0.43837, -0.45399, -0.46947, -0.48481, -0.50000,
            -0.51504, -0.52992, -0.54464, -0.55919, -0.57358, -0.58779, -0.60182,
            -0.61566, -0.62932, -0.64279, -0.65606, -0.66913, -0.68200, -0.69466,
            -0.70711, -0.71934, -0.73135, -0.74314, -0.75471, -0.76604, -0.77715,
            -0.78801, -0.79864, -0.80902, -0.81915, -0.82904, -0.83867, -0.84805,
            -0.85717, -0.86603, -0.87462, -0.88295, -0.89101, -0.89879, -0.90631,
            -0.91355, -0.92050, -0.92718, -0.93358, -0.93969, -0.94552, -0.95106,
            -0.95630, -0.96126, -0.96593, -0.97030, -0.97437, -0.97815, -0.98163,
            -0.98481, -0.98769, -0.99027, -0.99255, -0.99452, -0.99619, -0.99756,
            -0.99863, -0.99939, -0.99985, -1.00000, -0.99985, -0.99939, -0.99863,
            -0.99756, -0.99619, -0.99452, -0.99255, -0.99027, -0.98769, -0.98481,
            -0.98163, -0.97815, -0.97437, -0.97030, -0.96593, -0.96126, -0.95630,
            -0.95106, -0.94552, -0.93969, -0.93358, -0.92718, -0.92050, -0.91355,
            -0.90631, -0.89879, -0.89101, -0.88295, -0.87462, -0.86603, -0.85717,
            -0.84805, -0.83867, -0.82904, -0.81915, -0.80902, -0.79864, -0.78801,
            -0.77715, -0.76604, -0.75471, -0.74314, -0.73135, -0.71934, -0.70711,
            -0.69466, -0.68200, -0.66913, -0.65606, -0.64279, -0.62932, -0.61566,
            -0.60182, -0.58779, -0.57358, -0.55919, -0.54464, -0.52992, -0.51504,
            -0.50000, -0.48481, -0.46947, -0.45399, -0.43837, -0.42262, -0.40674,
            -0.39073, -0.37461, -0.35837, -0.34202, -0.32557, -0.30902, -0.29237,
            -0.27564, -0.25882, -0.24192, -0.22495, -0.20791, -0.19081, -0.17365,
            -0.15643, -0.13917, -0.12187, -0.10453, -0.08716, -0.06976, -0.05234,
            -0.03490, -0.01745, -0.00000
    };

    private static final double[] costable = new double[]{
            1.00000, 0.99985, 0.99939, 0.99863, 0.99756, 0.99619, 0.99452,
            0.99255, 0.99027, 0.98769, 0.98481, 0.98163, 0.97815, 0.97437, 0.97030,
            0.96593, 0.96126, 0.95630, 0.95106, 0.94552, 0.93969, 0.93358, 0.92718,
            0.92050, 0.91355, 0.90631, 0.89879, 0.89101, 0.88295, 0.87462, 0.86603,
            0.85717, 0.84805, 0.83867, 0.82904, 0.81915, 0.80902, 0.79864, 0.78801,
            0.77715, 0.76604, 0.75471, 0.74314, 0.73135, 0.71934, 0.70711, 0.69466,
            0.68200, 0.66913, 0.65606, 0.64279, 0.62932, 0.61566, 0.60182, 0.58779,
            0.57358, 0.55919, 0.54464, 0.52992, 0.51504, 0.50000, 0.48481, 0.46947,
            0.45399, 0.43837, 0.42262, 0.40674, 0.39073, 0.37461, 0.35837, 0.34202,
            0.32557, 0.30902, 0.29237, 0.27564, 0.25882, 0.24192, 0.22495, 0.20791,
            0.19081, 0.17365, 0.15643, 0.13917, 0.12187, 0.10453, 0.08716, 0.06976,
            0.05234, 0.03490, 0.01745, 0.00000, -0.01745, -0.03490, -0.05234, -0.06976,
            -0.08716, -0.10453, -0.12187, -0.13917, -0.15643, -0.17365, -0.19081,
            -0.20791, -0.22495, -0.24192, -0.25882, -0.27564, -0.29237, -0.30902,
            -0.32557, -0.34202, -0.35837, -0.37461, -0.39073, -0.40674, -0.42262,
            -0.43837, -0.45399, -0.46947, -0.48481, -0.50000, -0.51504, -0.52992,
            -0.54464, -0.55919, -0.57358, -0.58779, -0.60182, -0.61566, -0.62932,
            -0.64279, -0.65606, -0.66913, -0.68200, -0.69466, -0.70711, -0.71934,
            -0.73135, -0.74314, -0.75471, -0.76604, -0.77715, -0.78801, -0.79864,
            -0.80902, -0.81915, -0.82904, -0.83867, -0.84805, -0.85717, -0.86603,
            -0.87462, -0.88295, -0.89101, -0.89879, -0.90631, -0.91355, -0.92050,
            -0.92718, -0.93358, -0.93969, -0.94552, -0.95106, -0.95630, -0.96126,
            -0.96593, -0.97030, -0.97437, -0.97815, -0.98163, -0.98481, -0.98769,
            -0.99027, -0.99255, -0.99452, -0.99619, -0.99756, -0.99863, -0.99939,
            -0.99985, -1.00000, -0.99985, -0.99939, -0.99863, -0.99756, -0.99619,
            -0.99452, -0.99255, -0.99027, -0.98769, -0.98481, -0.98163, -0.97815,
            -0.97437, -0.97030, -0.96593, -0.96126, -0.95630, -0.95106, -0.94552,
            -0.93969, -0.93358, -0.92718, -0.92050, -0.91355, -0.90631, -0.89879,
            -0.89101, -0.88295, -0.87462, -0.86603, -0.85717, -0.84805, -0.83867,
            -0.82904, -0.81915, -0.80902, -0.79864, -0.78801, -0.77715, -0.76604,
            -0.75471, -0.74314, -0.73135, -0.71934, -0.70711, -0.69466, -0.68200,
            -0.66913, -0.65606, -0.64279, -0.62932, -0.61566, -0.60182, -0.58779,
            -0.57358, -0.55919, -0.54464, -0.52992, -0.51504, -0.50000, -0.48481,
            -0.46947, -0.45399, -0.43837, -0.42262, -0.40674, -0.39073, -0.37461,
            -0.35837, -0.34202, -0.32557, -0.30902, -0.29237, -0.27564, -0.25882,
            -0.24192, -0.22495, -0.20791, -0.19081, -0.17365, -0.15643, -0.13917,
            -0.12187, -0.10453, -0.08716, -0.06976, -0.05234, -0.03490, -0.01745,
            -0.00000, 0.01745, 0.03490, 0.05234, 0.06976, 0.08716, 0.10453, 0.12187,
            0.13917, 0.15643, 0.17365, 0.19081, 0.20791, 0.22495, 0.24192, 0.25882,
            0.27564, 0.29237, 0.30902, 0.32557, 0.34202, 0.35837, 0.37461, 0.39073,
            0.40674, 0.42262, 0.43837, 0.45399, 0.46947, 0.48481, 0.50000, 0.51504,
            0.52992, 0.54464, 0.55919, 0.57358, 0.58779, 0.60182, 0.61566, 0.62932,
            0.64279, 0.65606, 0.66913, 0.68200, 0.69466, 0.70711, 0.71934, 0.73135,
            0.74314, 0.75471, 0.76604, 0.77715, 0.78801, 0.79864, 0.80902, 0.81915,
            0.82904, 0.83867, 0.84805, 0.85717, 0.86603, 0.87462, 0.88295, 0.89101,
            0.89879, 0.90631, 0.91355, 0.92050, 0.92718, 0.93358, 0.93969, 0.94552,
            0.95106, 0.95630, 0.96126, 0.96593, 0.97030, 0.97437, 0.97815, 0.98163,
            0.98481, 0.98769, 0.99027, 0.99255, 0.99452, 0.99619, 0.99756, 0.99863,
            0.99939, 0.99985, 1.00000
    };
    //</editor-fold>
}
