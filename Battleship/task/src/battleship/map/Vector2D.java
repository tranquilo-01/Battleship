package battleship.map;

import java.util.Objects;

public class Vector2D implements Comparable<Object> {
    public final int x;
    public final int y;


    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2d = (Vector2D) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String toString() {
        String result;
        result = "(" + this.x + "," + this.y + ")";
        return result;
    }

    boolean precedes(Vector2D other) {
        return this.x <= other.x && this.y <= other.y && !this.equals(other);
    }

    boolean follows(Vector2D other) {
        return this.x >= other.x && this.y >= other.y && !this.equals(other);
    }

    public boolean isInBounds(Vector2D lowerBound, Vector2D upperBound){
        return (this.follows(lowerBound) && this.precedes(upperBound)) || this.equals(lowerBound) || this.equals(upperBound);
    }

    public Vector2D bottomRight(Vector2D other) {
        int x = Math.max(this.x, other.x);
        int y = Math.max(this.y, other.y);
        return new Vector2D(x, y);
    }

    public Vector2D topLeft(Vector2D other) {
        int x = Math.min(this.x, other.x);
        int y = Math.min(this.y, other.y);
        return new Vector2D(x, y);
    }

    Vector2D add(Vector2D other) {
        int x = this.x + other.x;
        int y = this.y + other.y;
        return new Vector2D(x, y);
    }

    Vector2D subtract(Vector2D other) {
        int x = this.x - other.x;
        int y = this.y - other.y;
        return new Vector2D(x, y);
    }

    Vector2D opposite() {
        int x = -this.x;
        int y = -this.y;
        return new Vector2D(x, y);
    }


    @Override
    public int compareTo(Object o) {
        if (this.precedes((Vector2D) o)) {
            return -1;
        } else if (this.follows((Vector2D) o)) {
            return 1;
        } else {
            return 0;
        }
    }

    public int compareX(Vector2D other) {
        return this.x - other.x;
    }

    public int compareY(Vector2D other) {
        return this.y - other.y;
    }

    public double distance(Vector2D other){
        int xdiff = this.compareX(other);
        int ydiff = this.compareY(other);

        return Math.sqrt(xdiff * xdiff + ydiff * ydiff);
    }

}
