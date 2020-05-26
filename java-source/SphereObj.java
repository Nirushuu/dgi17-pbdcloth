import processing.core.PApplet;

public class SphereObj {
    Vector position;
    float radius;
    PApplet p;
    public SphereObj (Vector position, float radius, PApplet parent) {
	this.radius = radius;
	p = parent;
	this.position = position;
    }


    public void draw() {
	p.pushMatrix();
	p.translate(position.x,position.y,position.z);
	p.sphere(radius);
	p.popMatrix();
    }

    public boolean collides (Vertex v) {
	Vector testVector = position.copy();
	testVector.subtract (v.position);
	if (testVector.getMagnitude() <= radius) {return true;}
	else {return false;}
    }
}
