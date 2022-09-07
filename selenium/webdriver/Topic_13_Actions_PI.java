package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Actions_PI {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	Actions action;
	JavascriptExecutor jsExecutor;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac")) { // Mac
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		} else { // Windows
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		}

		driver = new ChromeDriver();
		action = new Actions(driver);
		jsExecutor = (JavascriptExecutor) driver;

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void TC_01_Hover() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");

		// Hover vao element
		action.moveToElement(driver.findElement(By.cssSelector("input#age"))).perform();
		sleepInSecond(3);

		Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(),
				"We ask for your age only for statistical purposes.");
	}

	public void TC_02_Hover() {
		driver.get("https://www.myntra.com/");

		driver.findElement(By.xpath("//header[@id='desktop-header-cnt']//a[text()='Kids']")).click();

		// Hover vào Kids menu link
		action.moveToElement(driver.findElement(By.xpath("//header[@id='desktop-header-cnt']//a[text()='Kids']")))
				.perform();
		sleepInSecond(3);

		action.click(driver.findElement(By.xpath("//header[@id='desktop-header-cnt']//a[text()='Home & Bath']")))
				.perform();

		Assert.assertEquals(driver.findElement(By.cssSelector("span.breadcrumbs-crumb")).getText(), "Kids Home Bath");
	}

	public void TC_03_Hover() {
		driver.get("https://www.fahasa.com/");

		action.moveToElement(driver.findElement(By.cssSelector("span.icon_menu"))).perform();
		sleepInSecond(3);

		driver.findElement(By.xpath("//div[@class='fhs_column_stretch']//a[text()='Kỹ Năng Sống']")).click();

		// Hàm getText() sẽ get text ở trên UI mà User nhìn thấy
		Assert.assertEquals(driver.findElement(By.xpath("//ol[@class='breadcrumb']/li/strong")).getText(),
				"KỸ NĂNG SỐNG");

		// Hàm isDisplayed() sẽ dùng text ở dưới HTML
		Assert.assertTrue(driver.findElement(By.xpath("//ol[@class='breadcrumb']/li/strong[text()='Kỹ năng sống']"))
				.isDisplayed());
	}

	public void TC_04_Click_And_Hold_Block() {
		driver.get("https://automationfc.github.io/jquery-selectable/");

		// Đang cần thao tác vs cả 12 cái số
		List<WebElement> listNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));
		System.out.println("Tổng số lượng number = " + listNumbers.size());

		// - Click vào số bắt đầu và giữ chuột trái (Chưa nhả chuột ra)
		// - Di chuột đến số kết thúc
		// - Nhả chuột trái ra
		action.clickAndHold(listNumbers.get(0)).moveToElement(listNumbers.get(9)).release().perform();
		sleepInSecond(3);

		List<WebElement> listNumberSelected = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		System.out.println("Tổng số lượng number đã chọn = " + listNumberSelected.size());

		// Verify về số lượng element đã chọn
		Assert.assertEquals(listNumberSelected.size(), 6);
	}

	public void TC_05_Click_And_Hold_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");

		// Đang cần thao tác vs cả 12 cái số
		List<WebElement> listNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));
		System.out.println("Tổng số lượng number = " + listNumbers.size());

		Keys key = null;
		if (osName.contains("Mac")) { // MAC
			key = Keys.COMMAND;
		} else { // Windows
			key = Keys.CONTROL;
		}

		// - Nhấn phím Ctrl xuống (Chưa nhả ra)
		action.keyDown(key).perform();

		// - Click vào các số cần chọn
		action.click(listNumbers.get(1)).click(listNumbers.get(3)).click(listNumbers.get(4)).click(listNumbers.get(6))
				.click(listNumbers.get(10)).perform();

		// - Nhả phím Ctrl ra
		action.keyDown(key).perform();

		List<WebElement> listNumberSelected = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		System.out.println("Tổng số lượng number đã chọn = " + listNumberSelected.size());

		// Verify về số lượng element đã chọn
		Assert.assertEquals(listNumberSelected.size(), 5);
	}

	@Test
	public void TC_06_Double_Click() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		if (driver.toString().contains("firefox")) {
			// Scroll tới element này trước
			jsExecutor.executeScript("arguments[0].scrollIntoView(true);",
					driver.findElement(By.xpath("//button[text()='Double click me']")));
			sleepInSecond(3);
		}

		action.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
		sleepInSecond(3);

		Assert.assertEquals(driver.findElement(By.cssSelector("p#demo")).getText(), "Hello Automation Guys!");
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
