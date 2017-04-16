package sjsu.cmpe202.fmehta.umlParser;

public enum UmlRelationType {

	    EX("<|--"),
	    IM("<|.."),
	    AS("--"),
	    DEP("<.."),
	    LOLI("()--");

	    private String relationship;
	    UmlRelationType(String s) {
	        this.relationship = s;
	    }
	    public String getRelationship() {
	        return relationship;
	    }
	}

