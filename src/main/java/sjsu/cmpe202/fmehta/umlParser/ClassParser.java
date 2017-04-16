package sjsu.cmpe202.fmehta.umlParser;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;

import net.sourceforge.plantuml.SourceStringReader;

public class ClassParser {

	private final String path;
	private final String filename;
	private String classUML ="";
	private HashMap<String, ClassOrInterfaceDeclaration> map = new HashMap<String, ClassOrInterfaceDeclaration>();
	ClassOrInterfaceDeclaration currCID = null;
	private Map<String, UmlRelation> assrelationMap = new HashMap<String, UmlRelation>();
	private Map<String, UmlRelation> deprelationMap = new HashMap<String, UmlRelation>();
	private Map<String, UmlRelation> relationMap = new HashMap<String, UmlRelation>();
	
	
	public ClassParser(String path, String filename) {
		this.path = path;
		this.filename = filename;
	}

	public void start() {
			
		classUML = "" ;
		classUML += "@startuml\n";
		
		
		File folder = new File(path);
		for ( File f : folder.listFiles())
		{
			if ( f.getName().endsWith(".java") && f.isFile() )
			{
				try {
						InputStream	is = new FileInputStream(f);
						CompilationUnit	cu = JavaParser.parse(is);
					    mapInsert(cu);
					} 
				catch (ParseException e) {
						e.printStackTrace();
					}
				catch (IOException e) {
		                e.printStackTrace();
		            }
			}
		}
	
		for(Entry<String,ClassOrInterfaceDeclaration> entry : map.entrySet())
		{
			getClassOrInterface(entry.getValue());
		}
		
		printUMLRelation();
		
		classUML += "@enduml\n";
		System.out.println(classUML);
		drawClass(filename,classUML);
	}
	
    private void printUMLRelation() {
        for (Map.Entry<String, UmlRelation> entry : assrelationMap.entrySet()) {
            UmlRelation rel = entry.getValue();
            classUML += rel.get_currClassifier().getName() + " ";
            if (rel.getType() == UmlRelationType.AS && rel.getMultiplicity_currClassifier().length() > 0) {
            	classUML += "\"" + rel.getMultiplicity_currClassifier() + "\"";	
            }
            classUML += " " + rel.getType().getRelationship() + " ";
            if (rel.getType() == UmlRelationType.AS && rel.getMultiplicityB().length() > 0) {
                classUML += "\"" + rel.getMultiplicityB() + "\"";
            }
            classUML += " " + rel.getB().getName() + "\n";
        }
        
        for (Map.Entry<String, UmlRelation> entry : deprelationMap.entrySet()) {
            UmlRelation rel = entry.getValue();
            classUML += rel.get_currClassifier().getName() + " ";
            if (rel.getType() == UmlRelationType.AS && rel.getMultiplicity_currClassifier().length() > 0) {
            	classUML += "\"" + rel.getMultiplicity_currClassifier() + "\"";	
            }
            classUML += " " + rel.getType().getRelationship() + " ";
            if (rel.getType() == UmlRelationType.AS && rel.getMultiplicityB().length() > 0) {
                classUML += "\"" + rel.getMultiplicityB() + "\"";
            }
            classUML += " " + rel.getB().getName() + "\n";
        }
        
        for (Map.Entry<String, UmlRelation> entry : relationMap.entrySet()) {
            UmlRelation rel = entry.getValue();
            classUML += rel.get_currClassifier().getName() + " ";
            if (rel.getType() == UmlRelationType.AS && rel.getMultiplicity_currClassifier().length() > 0) {
            	classUML += "\"" + rel.getMultiplicity_currClassifier() + "\"";	
            }
            classUML += " " + rel.getType().getRelationship() + " ";
            if (rel.getType() == UmlRelationType.AS && rel.getMultiplicityB().length() > 0) {
                classUML += "\"" + rel.getMultiplicityB() + "\"";
            }
            classUML += " " + rel.getB().getName() + "\n";
        }

    }	
	

	private void drawClass(String fname, String input) {
		OutputStream png = null;
		try {
			png = new FileOutputStream(fname);
			SourceStringReader read = new SourceStringReader(input);
			read.generateImage(png);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}finally {
            if (png != null) {
                try {
                    png.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}

	private void getClassOrInterface(ClassOrInterfaceDeclaration cid) {
		this.currCID = cid;
		
	    if (cid.isInterface())
		{
	    	classUML += "interface " + cid.getName() + " {\n";
	    	parseBody(cid);
		}
		else
		{
			classUML += "class " + cid.getName() + " {\n" ;
			parseBody(cid);
		}
        // 2. inheritance
        List<ClassOrInterfaceType> inheritanceList = cid.getExtends();
        if (inheritanceList != null) {
            for (ClassOrInterfaceType classType : inheritanceList) {
                String name = classType.getName();
                if (map.containsKey(name)) {
                    String relationKey = name + "_" + cid.getName();
                    relationMap.put(relationKey,
                            new UmlRelation(map.get(name), "", cid, "", UmlRelationType.EX));
                }
            }
        }

        // 3. implementation
        List<ClassOrInterfaceType> interfaceList = cid.getImplements();
        if (interfaceList != null) {
            for (ClassOrInterfaceType interfaceType : interfaceList) {
                String name = interfaceType.getName();
                if (map.containsKey(name)) {
                    String relationKey = name + "_" + cid.getName();
                    relationMap.put(relationKey,
                            new UmlRelation(map.get(name), "", cid, "", UmlRelationType.IM));
                }
            }
        }
	    
		
	}

	private void parseBody(ClassOrInterfaceDeclaration cid) {
		List<Node> nodes = cid.getChildrenNodes();
		for (Node n : nodes )
		{
			if (n instanceof FieldDeclaration)
			{
				getFieldDetails((FieldDeclaration)n);
			} else if (n instanceof MethodDeclaration) {
                getMethodDetails((MethodDeclaration) n);
			} else if (n instanceof ConstructorDeclaration) {
                getConstructorDetails((ConstructorDeclaration) n);
			}
		}
		classUML += "}\n" ;	
	}

	private void getConstructorDetails(ConstructorDeclaration cd) {
		if (!((cd.getModifiers() & ModifierSet.PUBLIC) != 0))
		return;
		
		String ret = printType(cd.getModifiers());
		classUML += ret + cd.getName() + "( " ;
		
		List<Parameter> parameters = cd.getParameters();
        Map<String, List<VariableDeclaratorId>> attributeMap = new HashMap<>();

        Map<String, String> attributeNameMap = new HashMap<>();
		
		if (parameters != null && parameters.size() > 0)
		{
			int x = 0;
			for (Parameter p : parameters)
			{
				Type t = p .getType();
				if ( x >0 )
				{
					classUML += ", " ;
				}
				classUML += p.getId().getName() + " : " + t;
				Type type = ((ReferenceType) t).getType();
                if (type instanceof ClassOrInterfaceType) {
                    String depKeyname = ((ClassOrInterfaceType) type).getName();
                    if (map.containsKey(depKeyname) ) {
                    		if (!currCID.isInterface())
                    			printDependency(depKeyname);
                            List<VariableDeclaratorId> methodId = new LinkedList<>();
                            methodId.add(p.getId());
                            attributeMap.put(depKeyname, methodId);
                            attributeNameMap.put(p.getId().getName(), depKeyname);

                    }
                }
			}
		}
		classUML += ")\n" ;
		BlockStmt body = cd.getBlock();
		getBody(attributeMap, attributeNameMap, body);
		
		
	}

	private void getMethodDetails(MethodDeclaration md) {
		System.out.println("md.getName() + md.getModifiers" + md.getName() + md.getModifiers());
		if (!((md.getModifiers() & ModifierSet.PUBLIC) != 0))
		return;
		
		String ret = printType(md.getModifiers());
		classUML += ret + md.getName() + "( " ;
		
		List<Parameter> parameters = md.getParameters();
        Map<String, List<VariableDeclaratorId>> attributeMap = new HashMap<>();

        Map<String, String> attributeNameMap = new HashMap<>();
		
		if (parameters != null && parameters.size() > 0 )
		{
			int x = 0;
			for (Parameter p : parameters)
			{
				Type t = p .getType();
				if ( x >0 )
				{
					classUML += ", " ;
				}
				classUML += p.getId().getName() + " : " + t;
				Type type = ((ReferenceType) t).getType();
                if (type instanceof ClassOrInterfaceType) {
                    String depKeyname = ((ClassOrInterfaceType) type).getName();
                    if (map.containsKey(depKeyname) ) {
                    		if (!currCID.isInterface())
                    			printDependency(depKeyname);
                            List<VariableDeclaratorId> methodId = new LinkedList<>();
                            methodId.add(p.getId());
                            attributeMap.put(depKeyname, methodId);
                            attributeNameMap.put(p.getId().getName(), depKeyname);

                    }
                }
			}
		}
		classUML += ") : " + md.getType() + "\n" ;
		BlockStmt body = md.getBody();
		getBody(attributeMap, attributeNameMap, body);
	}
	
	private void getBody(Map<String, List<VariableDeclaratorId>> attributeMap, Map<String, String> attributeNameMap,
			BlockStmt body) {
		
		do
		{	
			if (body == null) {
            break;
		  }
        List<Statement> expStmntList = body.getStmts();
        if (expStmntList == null) {
            break;
        }
        for (Statement stmt : expStmntList) {
            if (stmt instanceof ExpressionStmt) {
                Expression expression = ((ExpressionStmt) stmt).getExpression();
                if (expression instanceof VariableDeclarationExpr) {
                    String depKeyName = ((VariableDeclarationExpr) expression).getType().toString();
                    if (map.containsKey(depKeyName)) {
                        // dependency
                        printDependency(depKeyName);

                        List<VariableDeclaratorId> list = null;
                        if (attributeMap.containsKey(depKeyName)) {
                            list = attributeMap.get(depKeyName);
                        } else {
                            list = new LinkedList<>();
                            attributeMap.put(depKeyName,
                                    list);
                        }
                        list.add(((VariableDeclarationExpr) expression).getVars().get(0).getId());
                        attributeNameMap.put(((VariableDeclarationExpr) expression).getVars().get(0).getId().getName(),
                        		depKeyName);
                    }
                }
            }
        }
	}while(false);

		
	}

	private void printDependency(String depKeyname) {
		ClassOrInterfaceDeclaration depCID = map.get(depKeyname);
    	String depKey = getAssosciation(depKeyname, currCID.getName());
        if (!deprelationMap.containsKey(depKey) && depCID.isInterface()) {
            deprelationMap.put(depKey,
                    new UmlRelation(depCID, "", this.currCID, "", UmlRelationType.DEP));
        }
    	/*if (depCID.isInterface()) {
            deprelationMap.put(depKey,
                    new UmlRelation(depCID, "", this.currCID, "", UmlRelationType.DEP));
    	}*/
		
	}

	private void getFieldDetails(FieldDeclaration fd) {
		
		String ret = null;
		
		/* Including Private and Public Attributes */
		if (fd.getModifiers() != Modifier.PRIVATE && fd.getModifiers() != Modifier.PUBLIC && fd.getModifiers() != Modifier.PROTECTED)
			return;
		
		Type t = fd.getType();
		
		if (t instanceof ReferenceType) 
		{	
			Type refType = ((ReferenceType) t).getType();
			if (refType instanceof ClassOrInterfaceType) {
				if (((ReferenceType) t).getArrayCount() > 0) { // A a[]
					if (map.containsKey(((ClassOrInterfaceType) refType).getName())) {
						buildRelation((ClassOrInterfaceType) refType, "*");
					}
				} else if (((ClassOrInterfaceType) refType).getTypeArgs() != null) { 
					// Collection<A>
					System.out.println("((ClassOrInterfaceType) refType).getTypeArgs()" + ((ClassOrInterfaceType) refType).getTypeArgs());
					Type refArg = ((ClassOrInterfaceType) refType).getTypeArgs().get(0);
					if (refArg instanceof ReferenceType) {
                    	Type subType = ((ReferenceType) refArg).getType();
                  if (subType instanceof ClassOrInterfaceType) {
						if (map.containsKey(((ClassOrInterfaceType) subType).getName())) {
							buildRelation((ClassOrInterfaceType) subType, "*");
						}
					}
					}
				} else if (map.containsKey(((ClassOrInterfaceType) refType).getName())) {
					// A a,
					buildRelation((ClassOrInterfaceType) refType, "1");
				} else { // String s; Collection<String>
					 ret = printType(fd.getModifiers());
					 classUML += ret + " " +  fd.getVariables().get(0) + " ";
					 classUML += ": " + fd.getType() + "\n";
				}
			}
			else {
				 ret = printType(fd.getModifiers());
				 classUML += ret + " " +  fd.getVariables().get(0) + " ";
				 classUML += ": " + fd.getType() + "\n";
			}
		}
		// int i ; int[] i
		else
		{
			 ret = printType(fd.getModifiers());
			 classUML += ret + " " +  fd.getVariables().get(0) + " ";
			 classUML += ": " + fd.getType() + "\n";
		}
		
	}

	private void buildRelation(ClassOrInterfaceType t, String multiplicity) {
		ClassOrInterfaceDeclaration dependCID = map.get(t.getName());
		String assosciateKey = getAssosciation(currCID.getName(), dependCID.getName());
        if (assrelationMap.containsKey(assosciateKey)) {
            UmlRelation r = assrelationMap.get(assosciateKey);
            r.setMultiplicity_currClassifier(multiplicity);
        }
        else {
        	assrelationMap.put(assosciateKey, new UmlRelation(currCID,
                    "",
                    dependCID,
                    multiplicity,
                    UmlRelationType.AS));
        }
	}
	
    private String getAssosciation(String name1, String name2) {
        if (name1.compareTo(name2) < 0) {
            return name1 + "_" + name2;
        }
        return name2 + "_" + name1;
    }

	private String printType(int mod) {
		String s;
		switch (mod)
		{
			case Modifier.PUBLIC:
				s="+";
				break;
			case Modifier.PRIVATE:
				s="-";
				break;
			default:
				s="";
				break;
		}
		System.out.println("s" + s); 
		return s;
	}

	private void mapInsert(CompilationUnit cu) {
		List<Node> nodes = cu.getChildrenNodes();
		for (Node n : nodes)
		{
			if( n instanceof ClassOrInterfaceDeclaration)
			{
				map.put(((ClassOrInterfaceDeclaration)n).getName(),(ClassOrInterfaceDeclaration)n);
			}
		}	
		
	}

		
		
	}


