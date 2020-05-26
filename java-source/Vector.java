import processing.core.PVector;
import processing.core.PApplet;


/**
 *  Vector class
 *  @version 2.0
 */
class Vector {
   float x;
   float y;
   float z;

   //Empty constructor
   Vector() {
      x = 0;
      y = 0;
      z = 0;
   }

   //General constructor
   Vector(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   //////
   //////

   //Returns a copy of this
   Vector copy () {
      return new Vector(x,y,z);
   }


   void add (Vector vec) {
      x += vec.x;
      y += vec.y;
      z += vec.z;
   }


   void subtract (Vector vec) {
      x -= vec.x;
      y -= vec.y;
      z -= vec.z;
   }

   void scale (float factor) {
    x *= factor;
    y *= factor;
    z *= factor;
  }

   //Returns the magnitude of a vector
   float getMagnitude () {
      return PApplet.sqrt(PApplet.sq(x)+PApplet.sq(y)+PApplet.sq(z));
   }


   //Scales a vector to a given magnitude
   void setMagnitude (float mag) {

      float factor = mag/getMagnitude();
      scale(factor);
   }

    
    //Normalizes a vector
    void normalize (){
	setMagnitude((float)1.);
    }


    void print () {
	System.out.println("x: "+x+". y: "+y+". z: "+z+".");
    }

   //////
   //////

   //STATIC FUNCTIONS
   //Computes the dot product of two vectors
   static float dotProduct (Vector vec1, Vector vec2) {
      return (vec1.x*vec2.x+vec1.y*vec2.y+vec1.z*vec2.z);
   }

   //Computes the cross product of two vectors.
   static Vector crossProduct (Vector u, Vector v) {
      float newX = u.y*v.z - u.z*v.y;
      float newY = u.z*v.x - u.x*v.z;
      float newZ = u.x*v.y - u.y*v.x;
      return new Vector(newX,newY,newZ);
   }

    //Returns the distance between two vectors
    public static float getDistance(Vector v, Vector w) {
        Vector distance = v.copy();
        distance.subtract(w);
        float distancef = distance.getMagnitude();
        return distancef;
    }
}
