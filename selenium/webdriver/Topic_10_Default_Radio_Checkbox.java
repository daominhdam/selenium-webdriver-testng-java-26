package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Default_Radio_Checkbox {
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
	}

	public void TC_01_Jotform() {
		driver.get("https://automationfc.github.io/multiple-fields/");

		// Chọn checkbox: Cancer - Fainting Spells
		checkToCheckboxOrRadio("//input[@value='Cancer']");
		checkToCheckboxOrRadio("//input[@value='Fainting Spells']");

		// Verify nó được chọn thành công
		Assert.assertTrue(isElementSelected("//input[@value='Cancer']"));
		Assert.assertTrue(isElementSelected("//input[@value='Fainting Spells']"));

		// Chọn Radio: 5+ days - 1-2 cups/day
		driver.findElement(By.xpath("//input[@value='5+ days']")).click();
		driver.findElement(By.xpath("//input[@value='1-2 cups/day']")).click();

		// Verify nó được chọn thành công
		Assert.assertTrue(isElementSelected("//input[@value='5+ days']"));
		Assert.assertTrue(isElementSelected("//input[@value='1-2 cups/day']"));

		/* Bỏ chọn thì chỉ cần click tiếp 1 lần nữa là nó bỏ chọn đi */
		// Bỏ Chọn checkbox: Cancer - Fainting Spells
		uncheckToCheckbox("//input[@value='Cancer']");
		uncheckToCheckbox("//input[@value='Fainting Spells']");

		// Verify nó được bỏ chọn thành công
		Assert.assertFalse(isElementSelected("//input[@value='Cancer']"));
		Assert.assertFalse(isElementSelected("//input[@value='Fainting Spells']"));

		/* Bỏ chọn thì phải chọn 1 radio khác nó sẽ bỏ chọn radio đang chọn đi */
		// Bỏ chọn Radio: 5+ days - 1-2 cups/day
		checkToCheckboxOrRadio("//input[@value='5+ days']");
		checkToCheckboxOrRadio("//input[@value='1-2 cups/day']");

		// Verify nó được chọn thành công
		Assert.assertTrue(isElementSelected("//input[@value='5+ days']"));
		Assert.assertTrue(isElementSelected("//input[@value='1-2 cups/day']"));
	}

	public void TC_02_Jotform_Select_All() {
		driver.get("https://automationfc.github.io/multiple-fields/");

		List<WebElement> allCheckboxes = driver.findElements(By.xpath("//input[@type='checkbox']"));

		/*** DÙNG HÀM ****/
		// Dùng vòng lặp để duyệt qua và click chọn
		for (WebElement checkbox : allCheckboxes) {
			checkToCheckboxOrRadio(checkbox);
		}

		// Dùng vòng lặp để duyệt qua và kiểm tra
		for (WebElement checkbox : allCheckboxes) {
			Assert.assertTrue(isElementSelected(checkbox));
		}

		/*** CODE CHAY ****/
		// Dùng vòng lặp để duyệt qua và click chọn
		for (WebElement checkbox : allCheckboxes) {
			if (!checkbox.isSelected()) {
				checkbox.click();
			}
		}

		// Dùng vòng lặp để duyệt qua và kiểm tra
		for (WebElement checkbox : allCheckboxes) {
			Assert.assertTrue(checkbox.isSelected());
		}

		// Bỏ chọn hết
		for (WebElement checkbox : allCheckboxes) {
			uncheckToCheckbox(checkbox);
		}

		// Dùng vòng lặp để duyệt qua và kiểm tra
		for (WebElement checkbox : allCheckboxes) {
			Assert.assertFalse(isElementSelected(checkbox));
		}
	}

	public void TC_03_Select_All() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		sleepInSecond(5);

		List<WebElement> allCheckboxes = driver.findElements(By.xpath("//div[@id='example']//input[@type='checkbox']"));
		
		// Vừa chọn + verify luôn
		for (WebElement checkbox : allCheckboxes) {
			checkToCheckboxOrRadio(checkbox);
		}

		// Vừa chọn + verify luôn
		for (WebElement checkbox : allCheckboxes) {
			uncheckToCheckbox(checkbox);
		}
	}

	@Test
	public void TC_04_Default() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		sleepInSecond(5);
		
		// Check
		checkToCheckboxOrRadio("//label[text()='Luggage compartment cover']/preceding-sibling::input");
		Assert.assertTrue(isElementSelected("//label[text()='Luggage compartment cover']/preceding-sibling::input"));
		
		// Uncheck
		uncheckToCheckbox("//label[text()='Luggage compartment cover']/preceding-sibling::input");
		Assert.assertFalse(isElementSelected("//label[text()='Luggage compartment cover']/preceding-sibling::input"));
	}
	
	public void checkToCheckboxOrRadio(String xpathLocator) {
		if (!driver.findElement(By.xpath(xpathLocator)).isSelected()) {
			driver.findElement(By.xpath(xpathLocator)).click();
		}
	}

	public void checkToCheckboxOrRadio(WebElement element) {
		if (!element.isSelected() && element.isEnabled()) {
			System.out.println("Click to element: " + element);
			element.click();
			Assert.assertTrue(isElementSelected(element));
		}
	}

	public void uncheckToCheckbox(String xpathLocator) {
		if (driver.findElement(By.xpath(xpathLocator)).isSelected()) {
			driver.findElement(By.xpath(xpathLocator)).click();
		}
	}

	public void uncheckToCheckbox(WebElement element) {
		if (element.isSelected() && element.isEnabled()) {
			element.click();
			Assert.assertFalse(isElementSelected(element));
		}
	}

	public boolean isElementSelected(String xpathLocator) {
		return driver.findElement(By.xpath(xpathLocator)).isSelected();
	}

	public boolean isElementSelected(WebElement element) {
		return element.isSelected();
	}

	@AfterClass
	public void afterClass() {
		// driver.quit();
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
