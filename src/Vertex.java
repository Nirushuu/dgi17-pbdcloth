

public class Vertex {

    float w;
    Vector position;
    Vector velocity;
    Vector p;

    public Vertex(Vector initialPosition, float mass) {
	position = initialPosition;
	velocity = new Vector(0,0,0);
	w = 1/mass;

    }
    
    //GENERATE P USED FOR SOLVING CONSTRAINTS
    public void setP(float timestep) {
	p = position.copy();
	Vector scaledVelocity = velocity.copy();
	scaledVelocity.scale(timestep);
	p.add(scaledVelocity);
    }

    //UPDATE POSITION AND VELOCITY AFTER SOLVING THE CONSTRAINTS
    public void update(float timestep) {
	Vector deltaPos = p.copy();
	deltaPos.subtract(position);
	deltaPos.scale(1/timestep);

	velocity = deltaPos.copy();
	position = p.copy();
    }
    
}
