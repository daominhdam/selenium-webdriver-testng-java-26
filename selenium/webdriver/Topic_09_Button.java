package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Button {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac")) { // Mac
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else { // Windows
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}

		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Fahasa() {
		driver.get("https://www.fahasa.com/customer/account/create");
		sleepInSecond(4);

		// Switch qua Iframe trước khi close popup
		driver.switchTo().frame("preview-notification-frame");

		// Close popup
		driver.findElement(By.cssSelector("a#NC_CLOSE_ICON>img")).click();
		sleepInSecond(3);

		// Quay về trang trước đó chứa iframe
		driver.switchTo().defaultContent();

		// Chuyển qua tab Login
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();

		// Verify 'Đăng nhập' button is disabled
		Assert.assertFalse(driver.findElement(By.cssSelector("button.fhs-btn-login")).isEnabled());

		// Enter value to Email/ password textbox
		driver.findElement(By.cssSelector("input#login_username")).sendKeys("dam@gmail.com");
		driver.findElement(By.cssSelector("input#login_password")).sendKeys("123456");

		// Verify 'Đăng nhập' button is enabled
		Assert.assertTrue(driver.findElement(By.cssSelector("button.fhs-btn-login")).isEnabled());

		// Verify 'Đăng nhập' button - background color (RED)
		String rgbaColor = driver.findElement(By.cssSelector("button.fhs-btn-login")).getCssValue("background-color");
		System.out.println("RGB color = " + rgbaColor);

		// Convert rgbaColor to Hexa color
		String hexaColor = Color.fromString(rgbaColor).asHex().toUpperCase();
		System.out.println("Hexa color = " + hexaColor);

		// Verify background color
		Assert.assertEquals(hexaColor, "#C92127");
	}

	@Test
	public void TC_02_() {

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	// Sleep cứng (Static wait)
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
