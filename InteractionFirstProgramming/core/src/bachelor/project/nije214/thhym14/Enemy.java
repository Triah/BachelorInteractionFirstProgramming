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
    private float health;

    public void createEnemy(){
        createSprite(new Sprite(new Texture("badlogic.jpg")));
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

    public Array<Vector2> getPath(){
        return path;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getSpeed(){
        return speed;
    }

    public void draw(SpriteBatch batch){
        getSprite().draw(batch);
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

    public float getAngle(){
        return (float) Math.atan2(path.get(waypoint).y - getY() - sprite.getHeight()/2, path.get(waypoint).x - getX() - sprite.getWidth()/2);
    }

    public void setVelocity(float angle, float speed){
        velocity.set((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
    }

    public Vector2 getVelocity(){
        return this.velocity;
    }

    public void setSpritePosition(float x, float velX, float y, float velY){
        getSprite().setPosition(x + velX * Gdx.graphics.getDeltaTime(), y + velY * Gdx.graphics.getDeltaTime());
    }

    public void setSpriteRotation(float angle){
        getSprite().setRotation(angle * MathUtils.radiansToDegrees);
    }

    public void setSpriteCenter(float x, float y){
        //sættes til waypoint path.getPath().x og y
        getSprite().setCenter(x,y);
    }

    public void incrementWaypoint(){
        this.waypoint++;
    }


    public boolean isWaypointReached() {
        //Afstand til waypoint i forhold maks afstand enemy kan flytte sig på 1 frame
        return Math.abs(path.get(waypoint).x - getX()-getSprite().getHeight()/2) <=
                getSpeed() * Gdx.graphics.getDeltaTime() &&
                Math.abs(path.get(waypoint).y - getY()-getSprite().getWidth()/2) <=
                        getSpeed() * Gdx.graphics.getDeltaTime();
    }


    public int getWaypoint() {
        return waypoint;
    }


    public void dispose(){
        this.dispose();
    }
}

