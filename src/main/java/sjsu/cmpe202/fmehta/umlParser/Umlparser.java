
package sjsu.cmpe202.fmehta.umlParser;

/**
 * @author fmehta : 02/28/2017
 */
public class Umlparser {

	public static void main(String[] args) {
		System.out.println("args lenth" + args.length);
		if (args.length == 3)
		{
			if (args[0].equalsIgnoreCase("class"))
			{
				ClassParser cp = new ClassParser(args[1],args[2]);
				cp.start();
			}
			else
			{
				System.out.println("Invalid input" + args[0]);
				System.exit(0);
			}
		}
		else
		{
			System.out.println("Incorrect arguments.<diagram_reqd><Path><output filename>");
		}
			
	}

}
