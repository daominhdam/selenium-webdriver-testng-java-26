package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Selenium_By {
//		- Bước 1: Mở browser lên
//		- Bước 2: Nhập vào Url
//		- Bước 3: Click vào My Account để mở trang login ra
//		- Bước 4: Click login
//		- Bước 5: Verify lỗi hiển thị
//		- Bước 6: Đóng browser

	// Khai báo 1 biến để đại diện cho thư viện Selenium WebDriver
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		// Bước 1: Mở browser lên
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		// Bấm cho maximize browser lên
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_() {
		// Bước 2: Nhập vào Url
		driver.get("http://live.techpanda.org/index.php/customer/account/login/");
		
		// Bước 3: Click vào My Account để mở trang login ra
		
		// HTML của element (Email Textbox)
//		<input type="email" autocapitalize="none" 
//				autocorrect="off" spellcheck="false" 
//				name="login[username]" value="" 
//				id="email" class="input-text required-entry validate-email" 
//				title="Email Address">
		
		// HTML của element (Email Textbox)
		// input - thẻ của element này (tagname)
		// attribute name - type autocapitalize  autocorrect spellcheck name value id class title
		// attribute value - email none off ..
		
		// Xpath Format
		//tagname[@attribute-name='attribute-value']
		
		//input[@name='login[username]']  -> *
		//input[@id='email']  -> *
		//input[@title='Email Address']   -> *
		
		// CSS Format:	tagname[attribute-name='attribute-value']
		
		// ID - Email Textbox
		driver.findElement(By.id("email"));
		
		// Class - New User form
		// 1 - Giá trị ko có khoảng trắng -> Lấy hết
		// 2 - Giá trị chứa khoảng trắng -> Lấy 1 phần
		driver.findElement(By.className("new-users"));
		
		// Name - Email Textbox
		driver.findElement(By.name("login[username]"));
		
		// Tagname - Tìm xem có bao nhiêu element/ page
		driver.findElements(By.tagName("a"));
		
		// LinkText (Link) - Text tuyệt đối
		driver.findElement(By.linkText("SEARCH TERMS"));
		
		// Partial LinkText (Link) - Text tương đối/ tuyệt đối
		driver.findElement(By.partialLinkText("SEARCH TERMS"));
		driver.findElement(By.partialLinkText("CH TER"));
		driver.findElement(By.partialLinkText("SEARCH"));
		driver.findElement(By.partialLinkText("TERMS"));
		
		// Css - Cover được hết cả 6 loại trên
		driver.findElement(By.cssSelector("input[name='login[username]']"));
		driver.findElement(By.cssSelector("input[id='email']"));
		driver.findElement(By.cssSelector("input[title='Email Address']"));
		
		// XPath
		driver.findElement(By.xpath("//input[@name='login[username]']"));
		driver.findElement(By.xpath("//input[@id='email']"));
		driver.findElement(By.xpath("//input[@title='Email Address']"));
	}


	@AfterClass
	public void afterClass() {
		// Bước 6: Đóng browser
		driver.quit();
	}

}
