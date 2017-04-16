package sjsu.cmpe202.fmehta.umlParser;

public enum UmlRelationType {

	    EX("<|--"),
	    IM("<|.."),
	    AS("--"),
	    DEP("<..");

	    private String relationship;
	    UmlRelationType(String s) {
	        this.relationship = s;
	    }
	    public String getRelationship() {
	        return relationship;
	    }
	}

