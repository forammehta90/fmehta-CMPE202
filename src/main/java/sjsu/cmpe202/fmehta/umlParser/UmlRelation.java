package sjsu.cmpe202.fmehta.umlParser;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class UmlRelation {

    private ClassOrInterfaceDeclaration currClassifier;
    private String multiplicityA;
    private ClassOrInterfaceDeclaration b;
    private String multiplicityB;
    private UmlRelationType type;

    public UmlRelation(ClassOrInterfaceDeclaration currClassifier, String multiplicityA, ClassOrInterfaceDeclaration b, String multiplicityB, UmlRelationType type) {
        this.currClassifier = currClassifier;
        this.multiplicityA = multiplicityA;
        this.b = b;
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
        return multiplicityA;
    }

    public void setMultiplicityA(String multiplicityA) {
        this.multiplicityA = multiplicityA;
    }

    public ClassOrInterfaceDeclaration getB() {
        return b;
    }

    public void setB(ClassOrInterfaceDeclaration b) {
        this.b = b;
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


