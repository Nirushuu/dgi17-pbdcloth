import processing.core.*;


/**
 * Cloth simulator with Position-Based Dynamics.
 * Based on [Muller06]
 *
 * Uses processing v3.3.5
 *
 * new in version 0.2
 * gravity/external force
 * locked vertices
 * mesh
 *
 * new in version 0.3
 * collision detection 
 * friction
 *
 * new in version 0.4
 * small fixes
 *
 *
 * @version 0.4
 * @author Nils Lenart
 */
public class PBDCloth extends PApplet{
    PFont f;
    Cloth cloth;
    SphereObj sphere;


    int width = 30;
    int length = 30;
    float spacing = 8;
    int z = 0;
    

    //SETUP FUNCTIONS
    public static void main(String[] args) {
        PApplet.main("PBDCloth");
    }
    public void settings(){
        size(1000,700,P3D);
    }
    public void setup(){


	//Initialize font
        f = createFont("Arial",16,true);

	//Initialize sphere
	sphere = new SphereObj(new Vector(500,500,0), 100, this);
	
	//Initialize cloth
	cloth = new Cloth(this, sphere, width, length, spacing);

    }


    //MAIN LOOP
    public void draw() {
	background(255);
	lights();
	writeText();


	//for staging a screenshot
	/*
	if (cloth.stageMode) {
	cloth.setPosition(0,0, new Vector(400, 500, 50));
	cloth.setPosition(width-1, 0, new Vector(600, 500, 50));
	cloth.setPosition(0,length-1, new Vector(400, 500, -50));
	cloth.setPosition(width-1,length-1, new Vector(600, 500, -50));
	}
	*/

	//sets two corners of cloth to mouse position
	cloth.setPosition(0,0,new Vector(mouseX, mouseY, z));
	cloth.setPosition(width-1, 0, new Vector(mouseX+(width-1)*spacing, mouseY, z));

	//updates the state of the cloth and draws
	cloth.update();
	cloth.draw();

	//draws the sphere
	sphere.draw();
    }



    //Writes text to screen
    public void writeText() {
        textFont(f,15);
        fill(0);
        text("Cloth simulator v0.4",20,30,0);
	text("Locked vertex (s): "+cloth.staticMode.toString(), 20, 50, 0);
	text("Gravity (g): "+cloth.gravityMode.toString(), 20, 70, 0);
	text("Mesh (m): "+cloth.meshMode.toString(), 20, 90, 0);
	text("Solver iterations (i): "+cloth.iterNum.toString(), 20, 110, 0);
	text("Reset (r)", 20, 130, 0);
    }


    //CHECK FOR KEY-PRESS
    public void keyPressed() {
        if (key == 's') {cloth.toggleStatic(); cloth.staticMode = !cloth.staticMode;}
        if (key == 'g') {cloth.gravityMode = !cloth.gravityMode;}
        if (key == 'm') {cloth.meshMode = !cloth.meshMode;}
	if (key == 'i') {cloth.iterNum = (cloth.iterNum%10)+1;}
	if (key == 'z') {z = (z%10)+1;}
	if (key == 'p') {cloth.vertices[0][0].position.print();}
	if (key == 'r') {cloth = new Cloth(this, sphere, width, length, spacing);}
	if (key == 'x') {
	    cloth.stageMode = !cloth.stageMode;
	    cloth.vertices[0][length-1].w = (cloth.vertices[0][length-1].w+1)%2;
	    cloth.vertices[width-1][length-1].w = (cloth.vertices[width-1][length-1].w+1)%2;
	}
	
    }

}                                 
