package sjsu.cmpe202.fmehta.umlParser;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class UmlRelation {

    private ClassOrInterfaceDeclaration currClassifier;
    private String mltplcty_currClassifier;
    private ClassOrInterfaceDeclaration depClassifier;
    private String multiplicityB;
    private UmlRelationType type;

    public UmlRelation(ClassOrInterfaceDeclaration currClassifier, String mltplcty_currClassifier, ClassOrInterfaceDeclaration depClassifier, String multiplicityB, UmlRelationType type) {
        this.currClassifier = currClassifier;
        this.mltplcty_currClassifier = mltplcty_currClassifier;
        this.depClassifier = depClassifier;
        this.multiplicityB = multiplicityB;
        this.type = type;
    }

    public ClassOrInterfaceDeclaration getA() {
        return currClassifier;
    }

    public void setA(ClassOrInterfaceDeclaration currClassifier) {
        this.currClassifier = currClassifier;
    }

    public String getMultiplicityA() {
        return mltplcty_currClassifier;
    }

    public void setMultiplicityA(String mltplcty_currClassifier) {
        this.mltplcty_currClassifier = mltplcty_currClassifier;
    }

    public ClassOrInterfaceDeclaration getB() {
        return depClassifier;
    }

    public void setB(ClassOrInterfaceDeclaration depClassifier) {
        this.depClassifier = depClassifier;
    }

    public String getMultiplicityB() {
        return multiplicityB;
    }

    public void setMultiplicityB(String multiplicityB) {
        this.multiplicityB = multiplicityB;
    }

    public UmlRelationType getType() {
        return type;
    }

    public void setType(UmlRelationType type) {
        this.type = type;
    }
}


