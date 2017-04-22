package bachelor.project.nije214.thhym14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;

public class Waypoint {

    private ShapeRenderer sr;
    private Array<Enemy> enemies;
    private Sprite sprite;
    private Enemy enemy;
    Array<Vector2> path;

    public void drawRouteFromEnemy(){
        getShapeRenderer().setAutoShapeType(true);
        setShapeRendererColor(Color.CYAN);
        getShapeRenderer().begin();
        for(int i = 0; i<getEnemyArray().size; i++){
            getShapeRenderer().line(new Vector2(
                    getEnemyArray().get(i).getX() + getEnemyArray().get(i).getWidth()/2,
                    getEnemyArray().get(i).getY() + getEnemyArray().get(i).getHeight()/2),
                    getPath().get(getEnemyArray().get(i).getWaypoint()));
        }
        getShapeRenderer().end();
    }

    public void drawRoute(){
        setShapeRendererColor(Color.WHITE);
        getShapeRenderer().begin(ShapeType.Line);
            Vector2 previous = getPath().first();
            for(Vector2 waypoint : getPath()) {
                getShapeRenderer().line(previous, waypoint);
                previous = waypoint;
            }
        getShapeRenderer().end();
    }

    public void drawWayPoints(){
        getShapeRenderer().begin(ShapeType.Filled);
            for (Vector2 waypoint : getPath()) {
                getShapeRenderer().circle(waypoint.x, waypoint.y, 10);
            }
        getShapeRenderer().end();
    }

    public void createShapeRenderer(){
        this.sr = new ShapeRenderer();
    }

    public ShapeRenderer getShapeRenderer(){
        return this.sr;
    }

    public void setShapeRendererColor(Color color){
        getShapeRenderer().setColor(color);
    }

    public Enemy getEnemy(){
        return this.enemy;
    }

    public void createSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public void createEnemyArray(){
        enemies = new Array<Enemy>();
    }

    public Array<Enemy> getEnemyArray(){
        return this.enemies;
    }

    public void addEnemyToPath(Enemy enemy){
        getEnemyArray().add(enemy);
    }

    public void createPath(Array<Vector2> path){
        this.path = path;
    }

    public Array<Vector2> getPath(){
        return this.path;
    }

    public void addPathNode(Vector2 node) {
        path.add(node);
    }

    public void dispose() {
        getShapeRenderer().dispose();
        sprite.getTexture().dispose();
    }

}