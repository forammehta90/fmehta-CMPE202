uml-class-generator-java

A Parser which converts Java Source Code into a UML Class Diagram
________________________________________
Compile Instructions

Requirements:
•	Java JDK version 1.8

The program expects following arguments:

1.	Path:
•	Full path of the folder which contains all the .java source files. The program picks only the .java files and ignores other files.
•	Example - C:\foram\cmpe202_umlParser\code\uml-parser-test-4
2.	Filename:
•	One word String.
•	Example – class_diagram
Example: - To generate class diagram
java -jar classparser.jar C:\foram\cmpe202_umlParser\code\uml-parser-test-4 class_diagram
^creates class_diagram.png in folder C:\foram\cmpe202_umlParser\code\uml-parser-test-4
________________________________________
Details of libraries and tools used
There are 2 parts of this UML parser program:
•	Parser – The parser reads all the java source code in the provided source path, and creates a grammar language that is interpretable by the UML generator
•	UML Generator – This part just generates a diagram as per the input provided

Parser: For parsing the JAVA code into a usable grammar, I have used the javaparser library: https://github.com/javaparser/javaparser
The library provides various methods and classes that read the source code and provide access to each sub-unit of the code via various methods or classes.

UML Generator: For generating the class diagram from the parsed code returned by the parser, I have used PlantUML tool. http://plantuml.com/
PlantUML is a free online tool for creating and publishing UML diagrams, which currently supports class and use case diagrams.

Tools
 Eclipse IDE: Used to write, compile, and test project code. The executable jar file can be conveniently tested using Eclipse IDE at all stages of creation, compilation and debug.
● Apache Maven: It is a build automation tool. There are two ways in which Maven can be used to build software. One aspect is that Maven can be used to describe how software is built and the second is that it also describes what the dependencies are. Maven has a central repository in which all its components and dependencies are published. These can be downloaded easily when needed.
● Java Parser GitHub Code:
•	https://github.com/javaparser/javaparser
•	Jar File: Javaparser¬core¬2.2.2.jar
•	This code is used to parse Java Source Code. Like Maven Central, it contains project binaries. Maven Project has been created, and dependencies of this tool have been added to POM.xml file
● Plant UML
•	http://plantuml.com/
•	Jar File : plantuml.jar version:8031
•	Java Source Code UML diagram is generated using this tool.
•	Graphviz software is required to use this tool and generate diagrams. Graphviz must be installed into your system. Download Graphviz from: http://www.graphviz.org/


