package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Custom_Radio_Checkbox {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
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
		jsExecutor = (JavascriptExecutor) driver;

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void TC_01_Custom_Checkbox() {
		driver.get("https://material.angular.io/components/checkbox/examples");

		// Case 1:
		// Thẻ input: bị ẩn nên ko click được
		// Thẻ input: dùng để verify được

		// Click
		// driver.findElement(By.xpath("//span[text()='Checked']/preceding-sibling::span/input")).click();
		// sleepInSecond(3);

		// Verify
		// Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Checked']/preceding-sibling::span/input")).isSelected());

		// Case 2:
		// Ko dùng thẻ input để click - thay thế bằng 1 thẻ đang hiển thị đại diện cho Checkbox/ Radio: span/ div/..
		// Các thẻ này lại ko verify được
		// driver.findElement(By.xpath("//span[text()='Checked']/preceding-sibling::span")).click();
		// sleepInSecond(3);

		// Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Checked']/preceding-sibling::span")).isSelected());

		// Case 3:
		// Thẻ span để click
		// Thẻ input để verify
		// Trong 1 dự án mà 1 element cần tới 2 locator để define thì sinh ra nhiều code/ cần phải maintain nhiều
		// Dễ gây hiểu nhầm (Confuse) cho người mới

		// driver.findElement(By.xpath("//span[text()='Checked']/preceding-sibling::span")).click();
		// sleepInSecond(3);
		// Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Checked']/preceding-sibling::span/input")).isSelected());

		// Case 4: Work-around
		// Thẻ input để click + verify
		// Hàm click() của WebElement ko click vào element bị ẩn được
		// Hàm click() của JavascriptExecutor để click: ko quan tâm element bị ẩn hay ko
		By checkedChekbox = By.xpath("//span[text()='Checked']/preceding-sibling::span/input");
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(checkedChekbox));
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(checkedChekbox).isSelected());
	}

	public void TC_02_Custom_Radio() {
		driver.get("https://material.angular.io/components/radio/examples");

		By springRadio = By.xpath("//span[contains(text(),'Spring')]/preceding-sibling::span/input");
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(springRadio));
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(springRadio).isSelected());
	}

	public void TC_03_Custom_VNDirect() {
		driver.get("https://account-v2.vndirect.com.vn/");

		By termCheckbox = By.xpath("//input[@name='acceptTerms']");
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(termCheckbox));
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(termCheckbox).isSelected());
	}

	@Test
	public void TC_04_Google_Docs() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");

		By canThoRadio = By.xpath("//div[@aria-label='Cần Thơ']");

		// Verify trước khi click
		Assert.assertEquals(driver.findElement(canThoRadio).getAttribute("aria-checked"), "false");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label='Cần Thơ' and @aria-checked='false']")).isDisplayed());
		

		// Click vào
		if (driver.findElement(canThoRadio).getAttribute("aria-checked").equals("false")) {
			driver.findElement(canThoRadio).click();
		}
		
		checkToCheckbox("//div[@aria-label='Cần Thơ']");
		sleepInSecond(3);

		// Verify sau khi click
		Assert.assertEquals(driver.findElement(canThoRadio).getAttribute("aria-checked"), "true");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label='Cần Thơ' and @aria-checked='true']")).isDisplayed());
		
		By miQuangCheckbox = By.xpath("//div[@aria-label='Mì Quảng']");
		
		if (driver.findElement(miQuangCheckbox).getAttribute("aria-checked").equals("false")) {
			driver.findElement(miQuangCheckbox).click();
		}
		
		checkToCheckbox("//div[@aria-label='Mì Quảng']");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(miQuangCheckbox).getAttribute("aria-checked"), "true");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label='Mì Quảng' and @aria-checked='true']")).isDisplayed());
		
		uncheckToCheckbox("//div[@aria-label='Mì Quảng']");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(miQuangCheckbox).getAttribute("aria-checked"), "false");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label='Mì Quảng' and @aria-checked='false']")).isDisplayed());
	}
	
	public void checkToCheckbox(String xpathLocator) {
		WebElement element = driver.findElement(By.xpath(xpathLocator));
		if (element.getAttribute("aria-checked").equals("false")) {
			element.click();
		}
	}

	public void uncheckToCheckbox(String xpathLocator) {
		WebElement element = driver.findElement(By.xpath(xpathLocator));
		if (element.getAttribute("aria-checked").equals("true")) {
			element.click();
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
