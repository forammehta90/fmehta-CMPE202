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
import java.util.List;
import java.util.Map.Entry;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
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
		classUML += "@enduml\n";
		System.out.println(classUML);
		drawClass(filename,classUML);
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
		
	}

	private void parseBody(ClassOrInterfaceDeclaration cid) {
		List<Node> nodes = cid.getChildrenNodes();
		for (Node n : nodes )
		{
			if (n instanceof FieldDeclaration)
			{
				getFieldDetails((FieldDeclaration)n);
			}
			
		}
		classUML += "}\n" ;	
	}

	private void getFieldDetails(FieldDeclaration fd) {
		/* Including Private and Public Attributes */
		if (fd.getModifiers() != Modifier.PRIVATE && fd.getModifiers() != Modifier.PUBLIC)
			return;
		
		Type t = fd.getType();
		
		if ( t instanceof ClassOrInterfaceType )
		{
			if (((ReferenceType) t).getArrayCount() > 0)
			{
				if (map.containsKey(((ClassOrInterfaceType) t).getName()))
				{
					buildRelation((ClassOrInterfaceType) t,"*");
				}
			}
		}
		
		if ( t instanceof ReferenceType)
		{
			Type type = ((ReferenceType) t).getType();
		}
		else
		
		printType(fd);
		
	}

	private void buildRelation(ClassOrInterfaceType t, String multiplicity) {
		ClassOrInterfaceDeclaration dependCID = map.get(t.getName());
		
		
	}

	private void printType(FieldDeclaration fd) {
		int mod = fd.getModifiers();
		
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
		classUML += s + " " +  fd.getVariables().get(0) + " ";
		classUML += ": " + fd.getType() + "\n";
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


