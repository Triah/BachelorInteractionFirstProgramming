/**
 * Created by Administrator on 20-03-2017.
 */

package bachelor.project.nije214.thhym14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Enemy extends Sprite {

    private Vector2 velocity = new Vector2();
    private float speed = 100;

    private Array<Vector2> path;
    private int waypoint = 0;

    public Enemy(Sprite sprite, Array<Vector2> path) {
        super(sprite);
        this.path = path;
    }

    //@Override
    public void draw(SpriteBatch spriteBatch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }

    public void update(float delta) {
        //Vinklen mellem nuværende position og næste waypoint
        float angle = (float) Math.atan2(path.get(waypoint).y - getY() - getHeight()/2, path.get(waypoint).x - getX()-getWidth()/2);
        //Hastighed på x og y aksen
        velocity.set((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
        //Position i forhold til tid
        setPosition(getX() + velocity.x * delta, getY() + velocity.y * delta);
        //setCenter(getX() + velocity.x * delta, getY() + velocity.y * delta);

        setRotation(angle * MathUtils.radiansToDegrees);


        if (isWaypointReached()) {
            //setPosition(path.get(waypoint).x, path.get(waypoint).y);
            setCenter(path.get(waypoint).x, path.get(waypoint).y);
            if (waypoint + 1 >= path.size)
                waypoint = 0;
            else
                waypoint++;
        }
    }

    public boolean isWaypointReached() {
        //Afstand til waypoint i forhold maks afstand enemy kan flytte sig på 1 frame
        return Math.abs(path.get(waypoint).x - getX()-getHeight()/2) <= speed * Gdx.graphics.getDeltaTime() && Math.abs(path.get(waypoint).y - getY()-getWidth()/2) <= speed * Gdx.graphics.getDeltaTime();
    }

    public Array<Vector2> getPath() {
        return path;
    }

    public int getWaypoint() {
        return waypoint;
    }

}

