package webdriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Alert {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	Alert alert;
	String firefoxAuthenAutoIT = projectPath + "\\autoITScripts\\" + "authen_firefox.exe";
	String chromeAuthenAutoIT = projectPath + "\\autoITScripts\\" + "authen_chrome.exe";

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac")) { // Mac
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else { // Windows
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}

		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void TC_01_Accept_Alert() {
		driver.get("https://automationfc.github.io/basic-form/");

		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		sleepInSecond(3);

		// Switch to Alert (Khi alert đang xuất hiện)
		alert = driver.switchTo().alert();

		// Verify alert title trước khi accept alert
		Assert.assertEquals(alert.getText(), "I am a JS Alert");

		// Accept 1 Alert
		alert.accept(); // Xong thành công là nó mất Alert luôn
		sleepInSecond(3);

		// Verify accept Alert thành công
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked an alert successfully");
	}

	public void TC_02_Confirm_Alert() {
		driver.get("https://automationfc.github.io/basic-form/");

		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		sleepInSecond(3);

		// Switch to Alert (Khi alert đang xuất hiện)
		alert = driver.switchTo().alert();

		// Verify alert title trước khi accept alert
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");

		// Cancel 1 Alert
		alert.dismiss();
		sleepInSecond(3);

		// Verify accept Alert thành công
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked: Cancel");
	}

	public void TC_03_Prompt_Alert() {
		driver.get("https://automationfc.github.io/basic-form/");

		String keyword = "Selenium WebDriver";

		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		sleepInSecond(3);

		// Switch to Alert (Khi alert đang xuất hiện)
		alert = driver.switchTo().alert();

		// Verify alert title trước khi accept alert
		Assert.assertEquals(alert.getText(), "I am a JS prompt");

		alert.sendKeys(keyword);
		sleepInSecond(3);

		alert.accept();
		sleepInSecond(3);

		// Verify accept Alert thành công
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You entered: " + keyword);
	}

	public void TC_04_Accept_Alert_Login() {
		driver.get("https://demo.guru99.com/v4");

		// Click Login button
		driver.findElement(By.name("btnLogin")).click();
		sleepInSecond(3);

		alert = driver.switchTo().alert();

		// Verify Alert title
		Assert.assertEquals(alert.getText(), "User or Password is not valid");

		// Accept alert
		alert.accept();
		sleepInSecond(3);

		// Verify app Url
		Assert.assertEquals(driver.getCurrentUrl(), "https://demo.guru99.com/v4/index.php");
	}

	public void TC_05_Authentication_Alert() {
		// Pass hẳn User/ password vào Url trước khi open nó ra
		// Url: http://the-internet.herokuapp.com/basic_auth
		// Pass: Username/ password vào Url
		// http://username:password@the-internet.herokuapp.com/basic_auth
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		sleepInSecond(5);

		Assert.assertTrue(driver.findElement(By.cssSelector("div.example>p")).getText().contains("Congratulations! You must have the proper credentials."));
	}

	public void TC_06_Authentication_Alert() {
		driver.get("http://the-internet.herokuapp.com/");

		String basicAuthenUrl = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");

		driver.get(getAuthenticationUrl(basicAuthenUrl, "admin", "admin"));
		sleepInSecond(5);

		Assert.assertTrue(driver.findElement(By.cssSelector("div.example>p")).getText().contains("Congratulations! You must have the proper credentials."));
	}

	@Test
	public void TC_07_Authentication_Alert_AutoIT() throws IOException {
		// Bật script của AutoIT trước rồi mở cái site chứa Authentication Alert lên sau
		// Thực thi 1 file exe trong code Java
		if (driver.toString().contains("firefox")) {
			Runtime.getRuntime().exec(new String[] { firefoxAuthenAutoIT, "admin", "admin" });
		} else {
			Runtime.getRuntime().exec(new String[] { chromeAuthenAutoIT, "admin", "admin" });
		}
		
		driver.get("http://the-internet.herokuapp.com/basic_auth");
		sleepInSecond(5);

		Assert.assertTrue(driver.findElement(By.cssSelector("div.example>p")).getText().contains("Congratulations! You must have the proper credentials."));
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public String getAuthenticationUrl(String basicAuthenUrl, String userName, String password) {
		String[] authenUrlArray = basicAuthenUrl.split("//");
		basicAuthenUrl = authenUrlArray[0] + "//" + userName + ":" + password + "@" + authenUrlArray[1];
		return basicAuthenUrl;
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
