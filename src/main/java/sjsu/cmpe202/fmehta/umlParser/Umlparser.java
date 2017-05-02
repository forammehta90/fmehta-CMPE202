
package sjsu.cmpe202.fmehta.umlParser;

/**
 * @author fmehta : 02/28/2017
 */
public class Umlparser {

	public static void main(String[] args) {
		System.out.println("args lenth" + args.length);
		if (args.length == 2)
		{
				String output_file = args[0]+"\\"+args[1]+".png";
				System.out.println("output_file"+output_file);
				ClassParser cp = new ClassParser(args[0],output_file);
				cp.start();
		}
		else
		{
			System.out.println("Incorrect arguments.<diagram_reqd><Path><output filename>");
			System.exit(0);
		}
			
	}

}
