package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Textbox_TextArea {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String firstName, lastName, employeeID, editFirstName, editLastName;
	String immigrationNumber, comments;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac")) { // Mac
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else { // Windows
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}

		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		firstName = "Luis";
		lastName = "Suarez";
		editFirstName = "Mohammed";
		editLastName = "Salah";
		immigrationNumber = "774703475";
		comments = "79 Madeira Way\nMadeira Beach\nFL 33708 USA";
	}

	@Test
	public void TC_01_Textbox_TextArea() {
		driver.get("https://opensource-demo.orangehrmlive.com/index.php");

		// Nhập vào User/ Password textbox
		driver.findElement(By.cssSelector("input#txtUsername")).sendKeys("Admin");
		driver.findElement(By.cssSelector("input#txtPassword")).sendKeys("admin123");

		// Click Login button
		driver.findElement(By.cssSelector("input#btnLogin")).click();
		sleepInSecond(5);

		// Mở màn hình Add Employee
		driver.get("https://opensource-demo.orangehrmlive.com/index.php/pim/addEmployee");

		// Nhập dữ liệu vào màn hình Add Employee
		driver.findElement(By.cssSelector("input#firstName")).sendKeys(firstName);
		driver.findElement(By.cssSelector("input#lastName")).sendKeys(lastName);

		// Lưu giá trị của Employee ID vào biến
		// Lấy ra giá trị + Gán vào biến
		employeeID = driver.findElement(By.cssSelector("input#employeeId")).getAttribute("value");

		// Click Save button
		driver.findElement(By.cssSelector("input#btnSave")).click();

		// Verify the fields are disabled
		Assert.assertFalse(driver.findElement(By.cssSelector("input#personal_txtEmpFirstName")).isEnabled());
		Assert.assertFalse(driver.findElement(By.cssSelector("input#personal_txtEmpLastName")).isEnabled());
		Assert.assertFalse(driver.findElement(By.cssSelector("input#personal_txtEmployeeId")).isEnabled());

		// Verify actual value the same expected value
		Assert.assertEquals(driver.findElement(By.cssSelector("input#personal_txtEmpFirstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.cssSelector("input#personal_txtEmpLastName")).getAttribute("value"), lastName);
		Assert.assertEquals(driver.findElement(By.cssSelector("input#personal_txtEmployeeId")).getAttribute("value"), employeeID);

		// Click to Save button
		driver.findElement(By.cssSelector("input#btnSave")).click();
		sleepInSecond(3);

		// Verify the fields are enabled
		Assert.assertTrue(driver.findElement(By.cssSelector("input#personal_txtEmpFirstName")).isEnabled());
		Assert.assertTrue(driver.findElement(By.cssSelector("input#personal_txtEmpLastName")).isEnabled());
		Assert.assertTrue(driver.findElement(By.cssSelector("input#personal_txtEmployeeId")).isEnabled());

		// Edit the field: FirstName/ LastName
		driver.findElement(By.cssSelector("input#personal_txtEmpFirstName")).clear();
		driver.findElement(By.cssSelector("input#personal_txtEmpFirstName")).sendKeys(editFirstName);
		driver.findElement(By.cssSelector("input#personal_txtEmpLastName")).clear();
		driver.findElement(By.cssSelector("input#personal_txtEmpLastName")).sendKeys(editLastName);

		// Click to Save button
		driver.findElement(By.cssSelector("input#btnSave")).click();
		sleepInSecond(3);

		// Verify the fields are disabled
		Assert.assertFalse(driver.findElement(By.cssSelector("input#personal_txtEmpFirstName")).isEnabled());
		Assert.assertFalse(driver.findElement(By.cssSelector("input#personal_txtEmpLastName")).isEnabled());
		Assert.assertFalse(driver.findElement(By.cssSelector("input#personal_txtEmployeeId")).isEnabled());

		// Verify actual value the same expected value
		Assert.assertEquals(driver.findElement(By.cssSelector("input#personal_txtEmpFirstName")).getAttribute("value"), editFirstName);
		Assert.assertEquals(driver.findElement(By.cssSelector("input#personal_txtEmpLastName")).getAttribute("value"), editLastName);
		Assert.assertEquals(driver.findElement(By.cssSelector("input#personal_txtEmployeeId")).getAttribute("value"), employeeID);
		
		// Open Immigration tab
		driver.findElement(By.xpath("//a[text()='Immigration']")).click();
		
		// Click to Add button
		driver.findElement(By.cssSelector("input#btnAdd")).click();
		
		// Enter to Immigration number textbox and Comments textarea
		driver.findElement(By.cssSelector("input#immigration_number")).sendKeys(immigrationNumber);
		driver.findElement(By.cssSelector("textarea#immigration_comments")).sendKeys(comments);
		
		// Click to Save button
		driver.findElement(By.cssSelector("input#btnSave")).click();
		sleepInSecond(3);
		
		// Click to Passport link
		driver.findElement(By.xpath("//a[text()='Passport']")).click();
		
		// Verify actual value the same expected value
		Assert.assertEquals(driver.findElement(By.cssSelector("input#immigration_number")).getAttribute("value"), immigrationNumber);
		Assert.assertEquals(driver.findElement(By.cssSelector("textarea#immigration_comments")).getAttribute("value"), comments);
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
