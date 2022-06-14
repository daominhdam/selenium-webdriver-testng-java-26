package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Handle_Custom_Dropdown {
	WebDriver driver;
	WebDriverWait expliciWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");

//
	@BeforeClass
	public void beforeClass() {
		// SET tương đối: Máy Windows nào cũng chạy được
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		// Luôn khởi tạo wait sau driver -> vì nó cần giá trị driver để khởi tạo
		// Wait cho các element theo điều kiện có sẵn: visible / invisible/ presence/
		// clickable...
		expliciWait = new WebDriverWait(driver, 15);

		// Ép kiểu tường minh trong Java
		jsExecutor = (JavascriptExecutor) driver;

		// Wait cho việc tìm element: findElement/ findElements
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_JQuery() {

		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");

		selectitemInCustomDropdown("span#number-button", "ul#number-menu >li>div", "10");
		Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button>span.ui-selectmenu-text")).getText(),
				"10");

		selectitemInCustomDropdown("span#number-button", "ul#number-menu >li>div", "1");
		Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button>span.ui-selectmenu-text")).getText(),
				"1");

		selectitemInCustomDropdown("span#number-button", "ul#number-menu >li>div", "19");
		Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button>span.ui-selectmenu-text")).getText(),
				"15");

	}

	// Truyền qua tham số
	public void selectitemInCustomDropdown(String parentLocator, String childLocator, String expectedTextitem) {
		driver.findElement(By.cssSelector(parentLocator)).click();
		sleepInSecond(1);

		expliciWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator)));

		List<WebElement> allDropdownItems = driver.findElements(By.cssSelector(childLocator));

		// Duyệt qua vòng lặp
		for (WebElement item : allDropdownItems) {
			String actualTextitem = item.getText();
			System.out.println("Item text = " + actualTextitem);
			if (actualTextitem.equals(expectedTextitem)) {
				// jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				// sleepInSecond(2);
				sleepInSecond(2);
				jsExecutor.executeScript("arguments[0].click();", item);
				//item.click();
				sleepInSecond(2);
				break;
			}
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void sleepInSecond(long timeInsecond) {
		try {
			Thread.sleep(timeInsecond * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}