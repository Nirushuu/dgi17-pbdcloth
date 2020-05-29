import processing.core.*;
import java.util.ArrayList;



/* The cloth class creates a cloth object which consists of vertices interconnected by distance constraints.
 * The vertices can be interpolated with triangles to draw a mesh
 *
 */
public class Cloth {
    PApplet p;
    int width;
    int length;
    Vertex[][] vertices;
    ArrayList<DistanceConstraint> constraints;
    ArrayList<CollisionConstraint> collisionConstraints;
    SphereObj sphere;

    float timestep = (float)0.1; //Timestep size
    Vector g;

    Boolean staticMode = true;   //Sets the two corner vertices to "infinite" mass
    Boolean gravityMode = true;  //Gravity added before solver
    Boolean meshMode = true;     //Draw the cloth as mesh
    Boolean stageMode = false;   //easter egg...
    Integer iterNum = 3;         //Number of solver iterations
    
    public Cloth (PApplet parent, SphereObj sphere, int width, int length, float spacing) {
	p = parent;
	this.width = width;
	this.length = length;
	Vector corner = new Vector(0,0,0);
	constraints = new ArrayList<DistanceConstraint>();
	this.sphere = sphere;
	
	//Gravity acceleration
	g = new Vector(0,(float)1,0);

	//Initialize size of arrays
	vertices = new Vertex[width][length];

	//Populate vertices and constraints
	for (int i = 0; i < width; i++) {
	    for (int j = 0; j < length; j++) {
		
		vertices[i][j] = new Vertex (corner.copy(), 1);
		corner.add(new Vector(spacing,0,0));
		

		//Add constraint along width
		if (i > 0) {
		    constraints.add(new DistanceConstraint(vertices[i-1][j], vertices[i][j], spacing, true));
		}
		//add constraint along length
		if (j > 0) {
		    constraints.add(new DistanceConstraint(vertices[i][j-1], vertices[i][j], spacing, true));
		}

	    }
	    corner.x = 0;
	    corner.add(new Vector(0,spacing,0));
	}

	//LOCK CORNERS
	vertices[0][0].w = 0;
	vertices[width-1][0].w = 0;
	
    }
    

    //Update state
    public void update() {
	
	if (gravityMode) {gravity();}
	solve(iterNum);

    }

    //Draw cloth
    public void draw() {

	if (meshMode) {drawMesh();}
	else {drawVertices();}

    }


    //Add gravity as external force.
    public void gravity () {
	for (int i = 0; i < width; i++) {
	    for (int j = 0; j < length; j++) {
		vertices[i][j].velocity.add(g);
	    }
	}

    }

    //From Muller 2006
    public void solve(int iterNum) {
	//apply external forces to all vertices
	

	//damp all velocities

	//get vector of positions p <- x + deltaT*v
	for (int i = 0; i < width; i++) {
	    for (int j = 0; j < length; j++) {
		vertices[i][j].setP(timestep);
	    }
	}

	//Generate collision constraints
	collisionConstraints = new ArrayList<CollisionConstraint>();
	for (int i = 0; i < width; i++) {
	    for (int j = 0; j < length; j++) {
		if(sphere.collides(vertices[i][j])) {
		    collisionConstraints.add(new CollisionConstraint(sphere.position.copy(),vertices[i][j],sphere.radius+5, false));
		}
	    }
	}

	//Modify p to satisfy all constrains, iterate n times
	for (int iter = 0; iter < iterNum; iter++) {
	    for (int i = 0; i < constraints.size(); i++) {
		constraints.get(i).solve();
	    }
	    for (int i = 0; i < collisionConstraints.size(); i++) {
		collisionConstraints.get(i).solve();
	    }
	}
	
	//Update position and velocities of all vertices
	for (int i = 0; i < width; i++) {
	    for (int j = 0; j < length; j++) {
		vertices[i][j].update(timestep);
	    }
	}
	
	
	//Add friction to colliding vertices
	//Take 10% off colliding vertices
	for (int i = 0; i < collisionConstraints.size(); i++) {
	    collisionConstraints.get(i).getVertex().velocity.scale((float)0.7);
	    }
	
    }



    //Draw vertices as ellipses
    public void drawVertices () {
       	for (int i = 0; i < width; i++) {
	    for (int j = 0; j < length; j++) {
		p.pushMatrix();		
		float x = vertices[i][j].position.x;
		float y = vertices[i][j].position.y;
		float z = vertices[i][j].position.z;
		p.translate(x,y,z);
		p.ellipse(0,0,1,1); //0,0 as i,j? instead of translate
		p.popMatrix();
	    }
	}
    }

    //Draw vertices interpolated as triangle mesh
    public void drawMesh () {
	//draw shape
	p.beginShape(p.TRIANGLE_STRIP);
	p.fill(128);
	for (int i = 1; i < width; i++) {
	    
	    //WEAVING
	    if (i%2 == 0) {
		for (int j = 0; j < length; j++) {
		    p.vertex(vertices[i-1][j].position.x,vertices[i-1][j].position.y,vertices[i-1][j].position.z);
		    p.vertex(vertices[i][j].position.x,vertices[i][j].position.y,vertices[i][j].position.z);
		}
	    }
	    else {
		for (int j = length-1; j >= 0; j--) {
		    p.vertex(vertices[i-1][j].position.x,vertices[i-1][j].position.y,vertices[i-1][j].position.z);
		    p.vertex(vertices[i][j].position.x,vertices[i][j].position.y,vertices[i][j].position.z);
		}
	    }
	
	}
	p.endShape();
    }

    //TOGGLES CORNER VERTEX STATIC
    public void toggleStatic () {
	vertices[0][0].w = (vertices[0][0].w+1)%2;
	vertices[width-1][0].w = (vertices[width-1][0].w+1)%2;
    }

    //Position setter of corners
    public void setPosition(int index1, int index2, Vector pos) {
	vertices[index1][index2].position = pos.copy();
    }

}

