public class DistanceConstraint {
    private Vertex a;
    private Vertex b;
    private float d;
    private boolean equality;

    public DistanceConstraint (Vertex a, Vertex b, float d, boolean equality) {
	this.a = a;
	this.b = b;
	this.d = d;
	this.equality = equality;
    }

    public boolean isEquality () {
	return equality;
    }

    public float evaluate () {
	Vector BA = a.p.copy();
	BA.subtract(b.p);
	return BA.getMagnitude()-d;
    }

    
    public void solve () {
	//SKIP SATISFIED INEQUALITIES
	if (!isEquality() && (evaluate() > 0)) {
	    return;
	}

	float reciprocal = 1/(a.w+b.w);
	float constraint = evaluate();
	Vector BANorm = a.p.copy();
	BANorm.subtract(b.p);
	BANorm.normalize();
	
	Vector deltaAp = BANorm.copy();
	deltaAp.scale(-a.w*reciprocal*constraint);
	
	Vector deltaBp = BANorm.copy();
	deltaBp.scale(b.w*reciprocal*constraint);
	
	a.p.add(deltaAp);
	b.p.add(deltaBp);

    }
    
}
