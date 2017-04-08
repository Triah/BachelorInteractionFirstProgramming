package bachelor.project.nije214.thhym14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;

/**
 * Created by Administrator on 07-04-2017.
 */

public class Bullet {
    private Enemy enemy; //dependency til enemy
    private Tower tower; //dependency til tower.. er der en anden måde at gøre det på???
    private Vector2 velocity = new Vector2();
    private float speed;

    public Bullet(){
        CircleShape bulletShape = new CircleShape();
        bulletShape.setRadius(3 / 2 / 5);
    }


    public void towerToEnemy(Enemy e, Tower t){

    }

    public float getTowerToEnemyAngle(Enemy e, Tower t){
        return (float) Math.atan2(e.getY() - t.getY() - e.getSprite().getHeight()/2, e.getX() - t.getX()- e.getSprite().getWidth()/2);
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getSpeed(){
        return speed;
    }

    public void setVelocity(float angle, float speed){
        velocity.set((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
    }

    public void setBulletPosition(float x, float velX, float y, float velY){
        //getSprite().setPosition(x + velX * Gdx.graphics.getDeltaTime(), y + velY * Gdx.graphics.getDeltaTime());
    }






    }
