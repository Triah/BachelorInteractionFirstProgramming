package bachelor.project.nije214.thhym14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Administrator on 07-04-2017.
 */

public class Bullet {
    private Enemy enemy;
    private Tower tower;

    private Vector2 velocity = new Vector2();
    private float speed;
    private Sprite sprite;
    private Array<Bullet> bullets;


    public void createBullet(){
    createSprite(new Sprite(new Texture("badlogic.jpg")));
}
    public void createSprite(Sprite sprite){
        this.sprite = sprite;
    }
    public Sprite getSprite(){return sprite;}
    public void setCenter(float x, float y){getSprite().setCenter(x,y);}
    public void setY(float y){sprite.setY(y);}
    public float getY(){return sprite.getY();}
    public void setX(float x){sprite.setX(x);}
    public Vector2 getVelocity(){
        return this.velocity;
    }
    public float getX(){
        return sprite.getX();
    }
    public void setSpeed(float speed){
        this.speed = speed;
    }
    public float getSpeed(){
        return speed;
    }
    public void createBulletArray(){
        bullets = new Array<Bullet>();
    }
    public Array<Bullet> getBulletArray(){
        return this.bullets;
    }



    public void setVelocity(float angle, float speed){
        velocity.set((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
    }


    public void setBulletPosition(float x, float velX, float y, float velY){
        getSprite().setPosition(x + velX * Gdx.graphics.getDeltaTime(), y + velY * Gdx.graphics.getDeltaTime());

    }

    public float getTowerToEnemyAngle(Enemy e, Tower t){
        return (float) Math.atan2(e.getY() - t.getY() - e.getSprite().getHeight()/2, e.getX() - t.getX()- e.getSprite().getWidth()/2);
    }

    /*
    /public void towerToEnemy(Enemy e, Tower t){}
    ////TODO: 09-04-2017  implement heatseeking bullet
    */
    
    /*
    /public void towerToEnemyLaser(Enemy e, Tower t){}
    ////TODO: 09-04-2017  implement laser bullet
    */


    }