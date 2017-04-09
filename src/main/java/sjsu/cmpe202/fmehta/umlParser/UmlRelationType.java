package sjsu.cmpe202.fmehta.umlParser;

public enum UmlRelationType {

	    EX("<|--"),
	    IM("<|.."),
	    AS("--"),
	    DEP("<.."),
	    LOLI("()--");

	    private String s;
	    UmlRelationType(String s) {
	        this.s = s;
	    }
	    public String getS() {
	        return s;
	    }
	}

