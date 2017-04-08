package bachelor.project.nije214.thhym14;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;



/**
 * Created by Administrator on 06-04-2017.
 */

public class Tower {
    private Sprite sprite;
    private int HP;
    private Type type;
    private int range;
    private int fireRate;
    //private int price

    enum Type {
        BASIC, //standard tower
        LASER, //laser projectiles
        FROST //slows movement speed of enemy
    }

    public void setType(Type t){ this.type = t;}
    public Type getType(){return type;}
    public void setHP(int HP){this.HP = HP;}
    public int getHP(){
        return HP;
    }
    public void setRange(int range){this.range = range;}
    public int getRange(){
        return range;
    }
    public void setfireRate(int fireRate){this.fireRate = fireRate;}
    public int getFireRate(){
        return fireRate;
    }


    public void createTower(){
        createSprite(new Sprite(new Texture("badlogic.jpg")));
    }

    public void createSprite(Sprite sprite){
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





    public void setSpritePosition(float x, float y){
        getSprite().setPosition(x , y );
    }

    public void dispose(){
        this.dispose();
    }
}

