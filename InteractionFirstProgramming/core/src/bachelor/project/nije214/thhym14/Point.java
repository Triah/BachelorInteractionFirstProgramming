package bachelor.project.nije214.thhym14;

/**
 * Created by Administrator on 14-04-2017.
 */

public class Point {
    private float x, y;

    /**
     * The constructor takes an x coordinate and a y coordinate and initializes
     * the class attributes.
     *
     * @param x The x coordinate of the Point
     * @param y The y coordinate of the Point
     */
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x coordinate of the Point.
     *
     * @return The x coordinate as an integer
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the x coordinate of the Point to the given value.
     *
     * @param x The x coordinate as an integer
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y coordinate of the Point.
     *
     * @return The y coordinate as an integer
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the y coordinate of the Point to the given value.
     *
     * @param y The y coordinate as an integer
     */
    public void setY(int y) {
        this.y = y;
    }

    // hashCode and equals needed for proper implementation of objects
    // used in collections
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
    }

    @Override
    public String toString() {
        return "Point: (" + x + ", " + y + ")";
    }
}
