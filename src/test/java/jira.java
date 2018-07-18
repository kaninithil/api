import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class jira {

	public static void main(String[] args) throws InterruptedException {
		
		 WebDriver driver = new FirefoxDriver();

		    // Maximize Window
		   driver.manage().window().maximize();
		  
		  
		   driver.get(System.getProperty("http://jira.sandbox.extranet.group/login.jsp?permissionViolation=true&os_destination=%2Fbrowse%2FACO-5383&page_caps=&user_role="));
		    
		   Thread.sleep(1000);
		   driver.findElement(By.xpath(".//*[@id='login-form-username']")).sendKeys("kanimozhi.vetrayan");
		    driver.findElement(By.xpath(".//*[@id='login-form-password']")).sendKeys("kanimozhi.vetrayan");     
		    driver.findElement(By.xpath(".//*[@id='login-form-submit']")).click();

	}

}
