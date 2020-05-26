public class CollisionConstraint {
    private Vector p;
    private Vertex b;
    private float d;
    private boolean equality;

    public CollisionConstraint (Vector p, Vertex b, float d, boolean equality)
    {
	this.p = p;
	this.b = b;
	this.d = d;
	this.equality = equality;
    }

    public boolean isEquality () {
	return equality;
    }

    public float evaluate () {
	Vector BA = p.copy();
	BA.subtract(b.p);
	return BA.getMagnitude()-d;
    }

    
    public void solve () {
	//SKIP SATISFIED INEQUALITIES
	if (!isEquality() && (evaluate() > 0)) {
	    return;
	}

	float reciprocal = 1/(b.w);
	float constraint = evaluate();
	Vector BANorm = p.copy();
	BANorm.subtract(b.p);
	BANorm.normalize();
	
	
	Vector deltaBp = BANorm.copy();
	deltaBp.scale(b.w*reciprocal*constraint);
	
	
	b.p.add(deltaBp);

    }

    public Vertex getVertex() {return b;}
    
}
