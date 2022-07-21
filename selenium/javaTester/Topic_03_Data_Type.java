package javaTester;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Topic_03_Data_Type {

	public static void main(String[] args) {
		// Thông tin của 1 nhân viên
		// Tên/ tuổi/ ngày tháng năm sinh/ giới tính/ quê quán/ lương

		// Ánh xạ cái thông tin này vào trong lập trình/ phần mềm

		// Cách khai báo 1 biến
		// 1 - kiểu dữ liệu của biến
		// 2 - tên biến
		// 3 - giá trị của biến

		// 2 cách khai báo và gán giá trị
		// 1 - vừa khai báo vừa gán trực tiếp luôn:
		// String name = "Automation Testing";

		// 2 - Khai báo trước rồi gán sau
		// String name;
		// name = "Automation Testing";
		// name = "Automation FC";

		// I - Kiểu dữ liệu nguyên thủy (Primitive)
		// (int/ long/ double (float)/ boolean)
		// Số nguyên: byte/ short/ int/ long (số mà ko có phần thập phân)
		byte bNumber = 5;

		short sNumber = 500;

		int iNumber = 6000;

		long lNumber = 1234932849;

		// Số thực: float/ double (số dạng có phần thập phân)
		float salary = 15.5f;

		double point = 9.8d;

		// Kí tự: char
		// Dấu nháy đơn ''
		// Chứa duy nhất 1 kí tự
		char a = 'a';

		// Logic: boolean
		boolean marriedStatus = true;
		marriedStatus = false;

		// II - Kiểu dữ liệu tham chiếu (Reference)
		// Chuỗi: String (Chữ/ số/ kí tự đặc biệt/..)
		// Dấu nháy đôi
		String emailInvalid = "afc@345@%!#.Gmail,com";

		// Class/ Interface (DateTime)
		Date date = new Date();

		WebDriver driver = new FirefoxDriver();

		// Đối tượng: Object
		Object students;

		// Array: Mảng (Khai báo số lượng dữ liệu trước) - Cố định số lượng
		int numbers[] = { 15, 20, 45 };
		String addresses[] = { "Da Nang", "Ha Noi", "HCM" };

		// List/ Set/ Queue (Collection) - Động
		List<Integer> studentNumber = new ArrayList<Integer>();
		List<String> studentAddress = new ArrayList<String>();
		
		Set<String> studentCity = new LinkedHashSet<String>();
	}

}
