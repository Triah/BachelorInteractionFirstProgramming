package bachelor.project.nije214.thhym14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;


import com.badlogic.gdx.utils.Array;

/**
 * Created by Administrator on 07-04-2017.
 */

public class Bullet {

    private Vector2 velocity = new Vector2();
    private float speed;
    private Sprite sprite;
    private float damage;
    private BulletType bulletType;
    private boolean isHit;



    public enum BulletType {
        BASIC,
        PUSHBACK,
    }

    public boolean isHit(){return this.isHit;}

    public void setHit(boolean isHit){this.isHit = isHit;}

    public void setType(Bullet.BulletType bt){ this.bulletType = bt;}

    public Bullet.BulletType getType(){return bulletType;}

    public void createBullet(String texture){
    createSprite(new Sprite(new Texture(texture)));
    }

    public void createSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public Sprite getSprite(){return sprite;}

    public void setDamage(float damage){
        this.damage = damage;
    }

    public float getDamage(){
        return damage;
    }

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


    public void setVelocity(float angle, float speed){
        velocity.set((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
    }

    public void setBulletPosition(float x, float velX, float y, float velY){
        getSprite().setPosition(x + velX * Gdx.graphics.getDeltaTime(), y + velY * Gdx.graphics.getDeltaTime());
    }

    public float getTowerToEnemyAngle(Enemy e, Tower t) {
        return (float) Math.atan2(e.getY() - t.getY() + e.getSprite().getHeight()/2,
                e.getX() - t.getX()+e.getSprite().getWidth()/2);
    }


}
