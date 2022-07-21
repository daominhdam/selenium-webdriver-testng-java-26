package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Web_Browser_Element_PIII {
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

	public void TC_01_Is_Displayed() {
		// - Có thể nhìn thấy
		// - Có kích thước cụ thể (rộng x cao)
		// - Phạm vi áp dụng: tất cả các loại element (Textbox/ TextArea/ Radio/ Link/ Button/..)

		driver.get("https://automationfc.github.io/basic-form/index.html");

		// Email Textbox
		WebElement emailTextbox = driver.findElement(By.cssSelector("input#mail"));

		if (emailTextbox.isDisplayed()) {
			emailTextbox.sendKeys("Automation Testing Textbox");
			System.out.println("Email textbox is displayed");
		} else {
			System.out.println("Email textbox is not displayed");
		}
		System.out.println(emailTextbox.isDisplayed());

		// Age Under 18 Radio Button
		WebElement ageUnder18Radio = driver.findElement(By.cssSelector("input#under_18"));

		if (ageUnder18Radio.isDisplayed()) {
			ageUnder18Radio.click();
			System.out.println("Age Under 18 radio is displayed");
		} else {
			System.out.println("Age Under 18 radio is not displayed");
		}

		System.out.println(ageUnder18Radio.isDisplayed());

		// Education TextArea
		WebElement educationTextarea = driver.findElement(By.cssSelector("textarea#edu"));
		if (educationTextarea.isDisplayed()) {
			educationTextarea.sendKeys("Automation Testing TextArea");
			System.out.println("Education TextArea is displayed");
		} else {
			System.out.println("Education TextArea is not displayed");
		}

		System.out.println(educationTextarea.isDisplayed());

		// Image 5 (Undisplayed)
		WebElement image5 = driver.findElement(By.xpath("//h5[text()='Name: User5']"));
		if (image5.isDisplayed()) {
			System.out.println("Image 5 is displayed");
		} else {
			System.out.println("Image 5 is not displayed");
		}

		// Nếu như element hiển thị thì hàm isDisplayed trả về giá trị là true
		// Nếu như element ko hiển thị thì hàm isDisplayed trả về giá trị là false
		System.out.println(image5.isDisplayed());
	}

	public void TC_02_Is_Enabled() {
		// - Có thể tương tác được = enabled -> true
		// - Không tương tác lên được = disabled -> false
		// - Phạm vi áp dụng: tất cả các loại element (Textbox/ TextArea/ Radio/ Link/ Button/..)

		driver.get("https://automationfc.github.io/basic-form/index.html");

		// Email Textbox
		WebElement emailTextbox = driver.findElement(By.cssSelector("input#mail"));
		if (emailTextbox.isEnabled()) {
			System.out.println("Email Textbox is enabled");
		} else {
			System.out.println("Email Textbox is disabled");
		}

		// Password Textbox
		WebElement passwordTextbox = driver.findElement(By.cssSelector("input#disable_password"));
		if (passwordTextbox.isEnabled()) {
			System.out.println("Password Textbox is enabled");
		} else {
			System.out.println("Password Textbox is disabled");
		}
	}

	public void TC_03_Is_Selected() {
		// - Đã được chọn hay chưa -> Selected -> true
		// - Chưa chọn -> Deselected -> false
		// - Phạm vi áp dụng: Radio Button/ Checkbox/ Dropdown (Default)

		driver.get("https://automationfc.github.io/basic-form/index.html");

		WebElement ageUnder18Radio = driver.findElement(By.cssSelector("input#under_18"));
		ageUnder18Radio.click();

		if (ageUnder18Radio.isSelected()) {
			System.out.println("Age Under 18 is selected");
		} else {
			System.out.println("Age Under 18 is de-selected");
		}

		// Java checkbox
		WebElement javaCheckbox = driver.findElement(By.cssSelector("input#java"));
		javaCheckbox.click();

		if (javaCheckbox.isSelected()) {
			System.out.println("Java checkbox is selected");
		} else {
			System.out.println("Java checkbox is de-selected");
		}
	}

	@Test
	public void TC_04_MailChimp() {
		driver.get("https://login.mailchimp.com/signup/");

		// Email/ Username Textbox
		driver.findElement(By.cssSelector("input#email")).sendKeys("automationfc@gmail.net");
		sleepInSecond(3);

		WebElement passwordTextbox = driver.findElement(By.cssSelector("input#new_password"));

		// Check lowercase (Viết thường)
		passwordTextbox.sendKeys("aaa");
		sleepInSecond(2);

		Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
		
		// Check uppercase (Viết hoa)
		passwordTextbox.clear();
		passwordTextbox.sendKeys("AAA");
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
		
		// Check number
		passwordTextbox.clear();
		passwordTextbox.sendKeys("123456");
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
		
		// Check special char
		passwordTextbox.clear();
		passwordTextbox.sendKeys("!@#$%^");
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
		
		// Check 8 char
		passwordTextbox.clear();
		passwordTextbox.sendKeys("ABCabc@@@");
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char completed']")).isDisplayed());

		// Full
		passwordTextbox.clear();
		passwordTextbox.sendKeys("ABCabc@@@123");
		sleepInSecond(2);

		Assert.assertTrue(driver.findElement(By.xpath("//h4[text()=\"Your password is secure and you're all set!\"]")).isDisplayed());

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
