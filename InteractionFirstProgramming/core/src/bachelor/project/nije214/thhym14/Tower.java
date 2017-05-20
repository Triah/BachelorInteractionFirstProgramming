package bachelor.project.nije214.thhym14;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;


/**
 * Authors:
 * Nicolai Hedegaard Jensen <nije214@student.sdu.dk>
 * Thor Skou Hymøller <thhym14@student.sdu.dk>
 */

public class Tower {
    private Sprite sprite;
    private float HP;
    private Type type;
    private float range;
    private float fireRate;
    private float timer;
    private boolean active;

    public enum Type {
        BASIC, //standard tower
        FROST //slows movement speed of enemy
    }

    public void setActive(boolean b){
        active = b;
    }

    public boolean getActive(){
        return active;
    }

    public void setTimer(float timer){this.timer = timer;}

    public float getTimer(){
        return timer;
    }

    public void setType(Type t){ this.type = t;}

    public Type getType(){return type;}

    public void setHP(float HP){this.HP = HP;}

    public float getHP(){
        return HP;
    }

    public void setRange(float range){this.range = range;}

    public float getRange(){
        return range;
    }

    public void setfireRate(float fireRate){this.fireRate = fireRate;}

    public float getFireRate(){
        return fireRate;
    }

    public void setCenter(float x, float y){
        getSprite().setCenter(x,y);
    }

    public void createTower(String texture){
        createSprite(new Sprite(new Texture(texture)));
    }

    private void createSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void setY(float y){
        sprite.setY(y);
    }

    public float getY(){
        return sprite.getY();
    }

    public void setX(float x){
        sprite.setX(x);
    }

    public float getX(){
        return sprite.getX();
    }

    public void setCircle(ShapeRenderer sr){
        sr.setAutoShapeType(true);
        sr.begin();
        sr.circle(this.getX() + this.getSprite().getWidth() / 2, this.getY() + this.getSprite().getHeight() / 2, this.getRange());
        sr.end();
    }

}

