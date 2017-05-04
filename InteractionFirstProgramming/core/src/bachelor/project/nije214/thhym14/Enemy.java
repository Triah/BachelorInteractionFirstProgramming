/**
 * Created by Administrator on 20-03-2017.
 */

package bachelor.project.nije214.thhym14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;



public class Enemy {

    private Vector2 velocity = new Vector2();
    private float speed;
    private Sprite sprite;
    private Array<Vector2> path;
    private int waypoint = 0;
    private Type type;
    private float health;
    private boolean isHit;
    private float pushTimer;


    public enum Type {
        BASIC, //standard tower
        SILLY //
    }

    public void setType(Type type){
        this.type = type;
    }

    public Type getType(){
        return this.type;
    }

    public boolean isHit(){return this.isHit;}

    public void setHit(boolean isHit){this.isHit = isHit;}

    public void createEnemy(String texture){
        createSprite(new Sprite(new Texture(texture)));
    }

    public void setCenter(float x, float y){
        getSprite().setCenter(x,y);
    }

    public void setHealth(float health){
        this.health = health;
    }

    public float getHealth(){
        return health;
    }

    public float getWidth(){
        return getSprite().getWidth();
    }

    public float getHeight(){
        return getSprite().getHeight();
    }

    public void setPath(Array<Vector2> path){
        this.path = path;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getSpeed(){
        return speed;
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

    public void setTimer(float pushTimer){this.pushTimer = pushTimer;}

    public float getTimer(){
        return pushTimer;
    }

    public float getAngle(){
        return (float) Math.atan2(path.get(waypoint).y - getY() - (sprite.getHeight()/2), path.get(waypoint).x - getX() - (sprite.getWidth()/2));
    }

    public void setVelocity(float angle, float speed){
        velocity.set((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
    }

    public Vector2 getVelocity(){
        return this.velocity;
    }

    public void setSpritePosition(float x, float velX, float y, float velY){
        getSprite().setPosition(x + velX * Gdx.graphics.getDeltaTime(), y+ + velY * Gdx.graphics.getDeltaTime());
    }

    public void setSpriteRotation(float angle){
        getSprite().setRotation(angle * MathUtils.radiansToDegrees);
    }

    public void incrementWaypoint(){
        this.waypoint++;
    }

    public boolean isWaypointReached() {
        return Math.abs(path.get(waypoint).x - getX()-getSprite().getHeight()/2) <=
                getSpeed() * Gdx.graphics.getDeltaTime() &&
                Math.abs(path.get(waypoint).y - getY()-getSprite().getWidth()/2) <=
                        getSpeed() * Gdx.graphics.getDeltaTime();
    }


//
    public void setVelocitySilly(float angle, float speed){
        velocity.set((float) Math.cos(angle+1) * speed, (float) Math.sin(angle+1) * speed);
    }

    public void pushBackEnemy(float x, float velX, float y, float velY){
            getSprite().setPosition(x + (velX) * Gdx.graphics.getDeltaTime(), y + (velY) * Gdx.graphics.getDeltaTime());
    }

//


    public int getWaypoint() {
        return waypoint;
    }

    public void dispose(){
        this.dispose();
    }
}

