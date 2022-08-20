package javaTester;

public class Topic_05_String_Split {
	
	public static void main(String[] args) {
		String basicAuthenUrl = "http://the-internet.herokuapp.com/basic_auth";
		
		String[] authenUrlArray = basicAuthenUrl.split("//");
		
		System.out.println(authenUrlArray[0]);
		System.out.println(authenUrlArray[1]);
		
		basicAuthenUrl  = authenUrlArray[0] + "//" + "admin" + ":" + "admin" + "@" + authenUrlArray[1]; 
		
		System.out.println(basicAuthenUrl);
		
		// driver.get(basicAuthenUrl);
		
	}

}
