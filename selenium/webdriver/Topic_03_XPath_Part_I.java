package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_03_XPath_Part_I {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_() {
		driver.get("https://www.facebook.com/");
		
		// Locator code gọn hơn
		driver.findElement(By.xpath("//span[contains(text(),\"'s happened?\")]"));
		
		// Locator code xấu hơn - khó đọc hơn
		driver.findElement(By.xpath("//*[@id=\"Password\"]"));
	}

	@Test
	public void TC_02_() {

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
