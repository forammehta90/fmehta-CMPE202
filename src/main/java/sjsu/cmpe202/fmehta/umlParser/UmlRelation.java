package sjsu.cmpe202.fmehta.umlParser;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class UmlRelation {

    private ClassOrInterfaceDeclaration a;
    private String multiplicityA;
    private ClassOrInterfaceDeclaration b;
    private String multiplicityB;
    private UmlRelationType type;

    public UmlRelation(ClassOrInterfaceDeclaration a, String multiplicityA, ClassOrInterfaceDeclaration b, String multiplicityB, UmlRelationType type) {
        this.a = a;
        this.multiplicityA = multiplicityA;
        this.b = b;
        this.multiplicityB = multiplicityB;
        this.type = type;
    }

    public ClassOrInterfaceDeclaration getA() {
        return a;
    }

    public void setA(ClassOrInterfaceDeclaration a) {
        this.a = a;
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


