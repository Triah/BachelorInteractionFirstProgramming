package bachelor.project.nije214.thhym14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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

    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        drawWayPoints();
        drawRoute();
        drawRouteFromEnemy();
    }

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

    public void show() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        createPath(new Array<Vector2>());
        addPathNode(new Vector2(250,500));
        addPathNode(new Vector2(800, 500));
        addPathNode(new Vector2(800, 1000));
        addPathNode(new Vector2(250, 1000));
        addPathNode(new Vector2(250, 1500));
        addPathNode(new Vector2(800, 1500));
        addPathNode(new Vector2(800, HEIGHT));
        createShapeRenderer();
        getShapeRenderer().setAutoShapeType(true);
        createSprite(new Sprite(new Texture("badlogic.jpg")));
        setSpriteSize(75,75);
        setSpriteOrigin(getSprite().getHeight()/2,getSprite().getWidth()/2);
        createEnemyArray();
        createEnemy(new Enemy());
        setEnemyCenter(250,0);
        addEnemyToPath(getEnemy());
    }

    public void createEnemy(Enemy enemy){
        this.enemy = enemy;
    }

    public Enemy getEnemy(){
        return this.enemy;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void setSpriteSize(float w, float h){
        getSprite().setSize(w,h);
    }

    public void setSpriteOrigin(float h, float w){
        getSprite().setOrigin(h,w);
    }

    public void createSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public void createEnemyArray(){
        enemies = new Array<Enemy>();
    }

    public void setEnemyCenter(float x, float y){
        getEnemy().setCenter(x,y);
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