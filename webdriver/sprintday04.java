package webdriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.apache.poi.xssf.usermodel.XSSFCell;                   
import org.apache.poi.xssf.usermodel.XSSFSheet;                                       
import org.apache.poi.xssf.usermodel.XSSFWorkbook;                                    
import org.openqa.selenium.By;                                                       
import org.openqa.selenium.WebDriver;                                                 
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;


public class sprintday04 {

	private static XSSFWorkbook workbook;

	public static void main(String[] args) throws IOException, InterruptedException {
		System.setProperty("webdriver.chrome.driver","./softwares/chromedriver.exe");		
		WebDriver driver= new ChromeDriver();

		File src= new File("C:\\Users\\Pavan\\workspace\\selenium\\propertyfile\\sprint.property"); 
		FileInputStream fis = new FileInputStream(src);		                                                                                              
		Properties prop = new Properties();                                                                 // Load the properties File
		prop.load(fis);
		driver.navigate().to(prop.getProperty("url"));                                                      // used Navigation API
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));                                   // implicit wait

		FileInputStream fis1 = new FileInputStream("./propertyfile/sprint04.xlsx");
		workbook = new XSSFWorkbook(fis1);
		XSSFSheet ws = workbook.getSheetAt(0);
		int rownum  = ws.getLastRowNum();
		int colnum = ws.getRow(0).getPhysicalNumberOfCells();
		System.out.println("the number of columns are: "+colnum);
		System.out.println("the number of rows are: "+rownum);
		XSSFCell email;
		XSSFCell pass;
		email=ws.getRow(1).getCell(0);
		//Thread.sleep(2000);
		pass=ws.getRow(1).getCell(1);
		//Thread.sleep(2000);
		driver.findElement(By.id(prop.getProperty("uid"))).sendKeys(email.toString());
		driver.findElement(By.name(prop.getProperty("pname"))).sendKeys(pass.toString());
		driver.findElement(By.xpath(prop.getProperty("loginbtn"))).click();
		Assert.assertTrue(driver.findElement(By.xpath(prop.getProperty("logo"))).isDisplayed());     // after login asserting for logo
		System.out.println("logo validated successfully by assertion");
		WebElement wel =driver.findElement(By.partialLinkText("Welcome"));                          // assertion for welcome message
		if (wel.isDisplayed()) {
			System.out.println("welcome message is displayed.....by assertion");
		}
		else {
			System.err.println("welcome message is not displyayed...");
		}


		//Admin   

		driver.findElement(By.linkText(prop.getProperty("Alinktxt"))).click();                       // click on admin tab
		System.out.println("im in Admin tab");
		XSSFCell username;
		XSSFCell empname;                                                                           //data import form excel
		username=ws.getRow(1).getCell(2);
		empname=ws.getRow(1).getCell(3);		
		driver.findElement(By.id(prop.getProperty("usrnmid"))).click();		                       // username 
		driver.findElement(By.id(prop.getProperty("usrnmid"))).sendKeys(username.toString());
		System.out.println("username is : "+username.toString());
		WebElement roll1 = driver.findElement(By.xpath(prop.getProperty("rollxpath")));                  // user roll
		Select s1 = new Select(roll1);
		s1.selectByValue("2");
		String rl = driver.findElement(By.xpath("//*[@id='searchSystemUser_userType']/option[3]")).getText();
		System.out.println("the employee roll is: "+rl);
		driver.findElement(By.xpath(prop.getProperty("enamexpath"))).click();                            // employee name 
		driver.findElement(By.xpath(prop.getProperty("enamexpath"))).sendKeys(empname.toString());
		System.out.println("employee name is : "+empname.toString());
		WebElement status1 = driver.findElement(By.xpath(prop.getProperty("statusxpath")));              // employee status
		Select s2 = new Select(status1);
		s2.selectByValue("1");
		String st = driver.findElement(By.xpath("//*[@id='searchSystemUser_status']/option[2]")).getText();
		System.out.println("the status of employee is: "+st);
		driver.findElement(By.id(prop.getProperty("searchbtnid"))).click();	                         // searching for user details	
		System.out.println("successfully searched for user details");



		//Myinfo (edit)

		driver.findElement(By.linkText(prop.getProperty("Mylinktxt"))).click();                   // click on Myinfo tab
		System.out.println("Im in MyInfo tab");
		driver.findElement(By.xpath(prop.getProperty("editxpth"))).click();
		XSSFCell DLnumb;                                                                          //data import form excel		                                                                     
		for(int i=4;i<colnum;i=i+2){                                                              // iteration
			DLnumb=ws.getRow(1).getCell(i);
			driver.findElement(By.id(prop.getProperty("DLnumid"))).clear();
			driver.findElement(By.id(prop.getProperty("DLnumid"))).sendKeys(DLnumb.toString());			
			driver.findElement(By.xpath(prop.getProperty("savexpath"))).click();
			Thread.sleep(2000);
			System.out.println("lisence details changed successfully");
		}
		driver.findElement(By.id("welcome")).click();                                                 // logout
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='welcome-menu']/ul/li[3]/a")).click();
		System.out.println("logout successfull");
	}
}
