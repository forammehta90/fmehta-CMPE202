package sjsu.cmpe202.fmehta.umlParser;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class UmlRelation {

    private ClassOrInterfaceDeclaration currClassifier;
    private String mltplcty_currClassifier;
    private ClassOrInterfaceDeclaration depClassifier;
    private String mltplcty_depClassifier;
    private UmlRelationType type;

    public UmlRelation(ClassOrInterfaceDeclaration currClassifier, String mltplcty_currClassifier, ClassOrInterfaceDeclaration depClassifier, String mltplcty_depClassifier, UmlRelationType type) {
        this.currClassifier = currClassifier;
        this.mltplcty_currClassifier = mltplcty_currClassifier;
        this.depClassifier = depClassifier;
        this.mltplcty_depClassifier = mltplcty_depClassifier;
        this.type = type;
    }

    public ClassOrInterfaceDeclaration get_currClassifier() {
        return currClassifier;
    }

    public void set_currClassifier(ClassOrInterfaceDeclaration currClassifier) {
        this.currClassifier = currClassifier;
    }

    public String getMultiplicity_currClassifier() {
        return mltplcty_currClassifier;
    }

    public void setMultiplicity_currClassifier(String mltplcty_currClassifier) {
        this.mltplcty_currClassifier = mltplcty_currClassifier;
    }

    public ClassOrInterfaceDeclaration getB() {
        return depClassifier;
    }

    public void setB(ClassOrInterfaceDeclaration depClassifier) {
        this.depClassifier = depClassifier;
    }

    public String getMultiplicityB() {
        return mltplcty_depClassifier;
    }

    public void setMultiplicityB(String mltplcty_depClassifier) {
        this.mltplcty_depClassifier = mltplcty_depClassifier;
    }

    public UmlRelationType getType() {
        return type;
    }

    public void setType(UmlRelationType type) {
        this.type = type;
    }
}


