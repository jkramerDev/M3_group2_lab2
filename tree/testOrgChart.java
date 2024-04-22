package tree;

public class testOrgChart {

	public static int DEPTH_FIRST_TEST = 0;
	public static int BREADTH_FIRST_TEST = 1;

	static Employee e1;
	static Employee e2;
	static Employee e3;
	static Employee e4;
	static Employee e5;
	static Employee e6;

	public static void main(String[] args) {

		// fill the org chart
		build_employees();

		// show breadth first
		System.out.println("\nBREADTH_FIRST_TEST");
		TEST_TREE(BREADTH_FIRST_TEST);

		// depth first
		System.out.println("\nDEPTH_FIRST_TEST");
		TEST_TREE(DEPTH_FIRST_TEST);

	}

	public static void build_employees() {
		e1 = new Employee();
		e1.setId(100);
		e1.setName("Rob");
		e1.setPosition("CEO");

		e2 = new Employee();
		e2.setId(200);
		e2.setName("todd");
		e2.setPosition("manager");

		e3 = new Employee();
		e3.setId(300);
		e3.setName("sally");
		e3.setPosition("worker");

		e4 = new Employee();
		e4.setId(400);
		e4.setName("ralph");
		e4.setPosition("manager");

		e5 = new Employee();
		e5.setId(500);
		e5.setName("ronnie");
		e5.setPosition("worker");

		e6 = new Employee();
		e6.setId(600);
		e6.setName("tim");
		e6.setPosition("manager");

	}

	public static void TEST_TREE(int type) {
		OrgChart orgChart = new OrgChartImpl();
		orgChart.addRoot(e1);
		orgChart.addDirectReport(e1, e2);
		// todd reports to rob

		//print(orgChart, type);

		orgChart.addDirectReport(e2, e3);
		// sally reports to todd

		//print(orgChart, type);

		orgChart.addDirectReport(e1, e4);
		// ralph reports to rob

		//print(orgChart, type);

		orgChart.addDirectReport(e4, e5);
		// ronnie reports to ralph

		//print(orgChart, type);
		orgChart.addDirectReport(e1, e6);
		// tim reports to rob

		print(orgChart, type);
		orgChart.removeEmployee(e2);
		// remove todd
		// sally should then report to rob

		System.out.println(" ");
		System.out.println("--- todd removed --- ");
		System.out.println(" ");
		print(orgChart, type);

	}

	static void print(OrgChart orgChart, int flag) {
		System.out.println("- - - ");

		if (flag == BREADTH_FIRST_TEST)
			orgChart.showOrgChartBreadthFirst();
		else
			orgChart.showOrgChartDepthFirst();

		
		System.out.println("- - - ");
		System.out.println(" ");
	}
	
	
		//INITIAL
			
			/*
			 * 										  Rob
			 * 									   /   |    \
			 * 									todd   ralph  tim 
			 * 									 /       |
			 * 								   sally    ronny
			 */
									

			/*
			breadth first:
				
				Rob 100 CEO
				todd 200 manager
				ralph 400 manager
				tim 600 manager
				sally 300 worker
				ronnie 500 worker 
							
			   depth first:
			   
				Rob 100 CEO
				todd 200 manager
				sally 300 worker
				ralph 400 manager
				ronnie 500 worker
				tim 600 manager
			
			 *
			 *
			 *
			 */
		
			//REMOVE TODD
			/*
	 		 * 	/*
			 * 										  Rob
			 * 									   /   |    \
			 * 									sally   ralph  tim 
			 * 									        |
			 * 								           ronnie
			 */

			 /* 	BREADTH FIRST:
				
				Rob 100 CEO
				ralph 400 manager
				tim 600 manager
				sally 300 worker
				ronnie 500 worker
				
				DEPTH FIRST:
			 * 
			 	Rob 100 CEO
				sally 300 worker
				ralph 400 manager
				ronnie 500 worker
				tim 600 manager
			 */
}
