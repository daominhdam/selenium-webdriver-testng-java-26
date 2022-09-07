package webdriver;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Actions_PII {
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

	public void TC_01_Right_Click() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");

		// Click chuột phải vào button
		action.contextClick(driver.findElement(By.cssSelector("span.context-menu-one"))).perform();
		sleepInSecond(3);

		// Hover chuột vào context menu: Paste
		action.moveToElement(driver.findElement(By.cssSelector("li.context-menu-icon-paste"))).perform();
		sleepInSecond(3);

		// Verify Paste có trạng thái: hover/ visible
		Assert.assertTrue(
				driver.findElement(By.cssSelector("li.context-menu-icon-paste.context-menu-hover.context-menu-visible"))
						.isDisplayed());

		// Click vào Paste
		action.click(driver.findElement(By.cssSelector("li.context-menu-icon-paste"))).perform();
		sleepInSecond(3);

		// Handle Alert
		driver.switchTo().alert().accept();
		sleepInSecond(3);

		// Verify Paste có trạng thái: invisible
		Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-paste")).isDisplayed());

	}

	public void TC_02_Drag_Drop_HTML4() {
		driver.get("https://automationfc.github.io/kendo-drag-drop/");

		WebElement smallCircle = driver.findElement(By.cssSelector("div#draggable"));
		WebElement bigCircle = driver.findElement(By.cssSelector("div#droptarget"));

		action.dragAndDrop(smallCircle, bigCircle).perform();
		sleepInSecond(3);

		Assert.assertEquals(bigCircle.getText(), "You did great!");

		String rgbaColor = bigCircle.getCssValue("background-color");
		System.out.println("RGB color = " + rgbaColor);

		// Convert rgbaColor to Hexa color
		String hexaColor = Color.fromString(rgbaColor).asHex().toUpperCase();
		System.out.println("Hexa color = " + hexaColor);

		// Verify background color
		Assert.assertEquals(hexaColor, "#03A9F4");
	}

	public void TC_03_Drag_Drop_HTML5_Css() throws IOException {
		driver.get("https://automationfc.github.io/drag-drop-html5/");

		String jsContentFile = getContentFile(projectPath + "/dragAndDrop/drag_and_drop_helper.js");

		jsExecutor.executeScript(jsContentFile + "$('div#column-a').simulateDragDrop({dropTarget: 'div#column-b'});");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div#column-a>header")).getText(), "B");
		Assert.assertEquals(driver.findElement(By.cssSelector("div#column-b>header")).getText(), "A");

		jsExecutor.executeScript(jsContentFile + "$('div#column-a').simulateDragDrop({dropTarget: 'div#column-b'});");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div#column-a>header")).getText(), "A");
		Assert.assertEquals(driver.findElement(By.cssSelector("div#column-b>header")).getText(), "B");

		jsExecutor.executeScript(jsContentFile + "$('div#column-a').simulateDragDrop({dropTarget: 'div#column-b'});");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div#column-a>header")).getText(), "B");
		Assert.assertEquals(driver.findElement(By.cssSelector("div#column-b>header")).getText(), "A");
	}

	@Test
	public void TC_04_Drag_Drop_HTML5_Css_XPath() throws IOException, AWTException {
		driver.get("https://automationfc.github.io/drag-drop-html5/");
		
		dragAndDropHTML5("div#column-a", "div#column-b");
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div#column-a>header")).getText(), "B");
		Assert.assertEquals(driver.findElement(By.cssSelector("div#column-b>header")).getText(), "A");

		dragAndDropHTML5("div#column-a", "div#column-b");
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div#column-a>header")).getText(), "A");
		Assert.assertEquals(driver.findElement(By.cssSelector("div#column-b>header")).getText(), "B");
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

	public String getContentFile(String filePath) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(filePath);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}

	public void dragAndDropHTML5(String sourceLocator, String targetLocator) throws AWTException {

		WebElement source = driver.findElement(By.cssSelector(sourceLocator));
		WebElement target = driver.findElement(By.cssSelector(targetLocator));

		// Setup robot
		Robot robot = new Robot();
		robot.setAutoDelay(500);

		// Get size of elements
		Dimension sourceSize = source.getSize();
		Dimension targetSize = target.getSize();

		// Get center distance
		int xCentreSource = sourceSize.width / 2;
		int yCentreSource = sourceSize.height / 2;
		int xCentreTarget = targetSize.width / 2;
		int yCentreTarget = targetSize.height / 2;

		Point sourceLocation = source.getLocation();
		Point targetLocation = target.getLocation();
		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Make Mouse coordinate center of element
		sourceLocation.x += 20 + xCentreSource;
		sourceLocation.y += 110 + yCentreSource;
		targetLocation.x += 20 + xCentreTarget;
		targetLocation.y += 110 + yCentreTarget;

		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Move mouse to drag from location
		robot.mouseMove(sourceLocation.x, sourceLocation.y);

		// Click and drag
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x,
				((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

		// Move to final position
		robot.mouseMove(targetLocation.x, targetLocation.y);

		// Drop
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
