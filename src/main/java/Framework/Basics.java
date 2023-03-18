package Framework;

import java.net.MalformedURLException;
import java.time.Duration;

import javax.annotation.concurrent.Immutable;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class Basics extends base{

	
	@Test(enabled = false)
	//**************Go to WIFI setting page, click on it and type text on it***************//
	public void WiFiSettingName() throws MalformedURLException, InterruptedException {
		
		//======Syntax to identify element using Android UI Automator=========
		//driver.findElement(new AppiumBy.ByAndroidUIAutomator("attribute("value")"));
		
		System.out.println(driver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiSelector().clickable(true)")).getSize());
		
		driver.findElement(new AppiumBy.ByAndroidUIAutomator("text(\"Preference\")")).click();   //By. is from Selenium package and AppiumBy. is explicitely for Appium packages..(when writing xpath using accessiblityID/androidUIAutomator) use AppiumBy.
		//driver.findElement(By.xpath("//android.widget.TextView[@text=\"Preference\"]")).click();
		
		  driver.findElement(By.xpath("//android.widget.TextView[@text=\"3. Preference dependencies\"]")).click(); 
		  driver.findElement(By.xpath("//android.widget.TextView[@text=\"WiFi\"]/parent::*[@class=\"android.widget.RelativeLayout\"]/following-sibling::android.widget.LinearLayout/	\r\n"
		  + "android.widget.CheckBox")).click(); driver.findElement(By.xpath("//android.widget.TextView[@text=\"WiFi settings\"]")).click();
		  
		  String getAlertBoxTitle = driver.findElement(AppiumBy.id("android:id/alertTitle")).getText();
		  Assert.assertEquals(getAlertBoxTitle, "WiFi settings");
		  
		  driver.findElement(By.id("android:id/edit")).clear();
		  driver.findElement(By.id("android:id/edit")).sendKeys("MimuTypedKey");
		  driver.findElement(By.xpath("//android.widget.Button[@text=\"OK\"]")).click();
				
	}
	
	@Test(enabled = false)
	//********************Long Press******************************//
	public void longPressGesture() throws InterruptedException {
		
		driver.findElement(AppiumBy.accessibilityId("Views")).click();
		driver.findElement(AppiumBy.accessibilityId("Expandable Lists")).click();
		driver.findElement(AppiumBy.accessibilityId("1. Custom Adapter")).click();
		
		WebElement ele = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"People Names\"]"));
//		((JavascriptExecutor)driver).executeScript("mobile: longClickGesture", 
//				ImmutableMap.of("elementId",((RemoteWebElement)ele).getId(), "duration",3000));
		longPressAction(ele);
		Assert.assertTrue(driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Sample menu\"]")).isDisplayed());
	
	}
	
	@Test(enabled = false)
	//********************Scroll**********************//
	public void scroll() throws InterruptedException {
		
		driver.findElement(AppiumBy.accessibilityId("Views")).click();
		
		//----------user the below if you know where to scroll------------
		//driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"WebView\"))"));
		//driver.findElement(AppiumBy.accessibilityId("WebView")).click();
		//driver.findElement(AppiumBy.xpath("//android.view.View[contains(@text,\"Selenium\")]")).isDisplayed();
		//Assert.assertTrue(driver.findElement(AppiumBy.xpath("//android.view.View[contains(@text,\"Selenium\")]")).isDisplayed());
		
		
		//-------------user have no prior idea of where to scroll of if the element exist----------------------
		boolean canScrollMore;
		do {
			canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.builder()
					   .put("left", 100)
					   .put("top", 100)			//you can use elements also if known
					   .put("width", 200)
					   .put("height", 500)    //adjust here
					   .put("direction", "down")
					   .put("percent", 3.0)
					   .build());
		
			if(driver.findElement(AppiumBy.accessibilityId("Picker")).isDisplayed()) {
				driver.findElement(AppiumBy.accessibilityId("Picker")).click();
				Assert.assertTrue(driver.findElement(By.id("android:id/numberpicker_input")).isDisplayed());
				break;
			}
		}while(canScrollMore);
		
	}
	
	@Test(enabled = false)
	public void Swipe() {
		driver.findElement(AppiumBy.accessibilityId("Views")).click();
		driver.findElement(AppiumBy.accessibilityId("Gallery")).click();
		driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@text,\"Photos\")]")).click();
		WebElement firstImg = driver.findElement(AppiumBy.xpath("(//android.widget.ImageView)[1]"));
		String ele = firstImg.getAttribute("focusable");
		System.out.println("============="+ele);
		Assert.assertEquals(ele, "true");
		
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
			    "elementId", ((RemoteWebElement)firstImg).getId(),
			    "direction", "left",
			    "percent", 0.75
			));
		ele = firstImg.getAttribute("focusable");
		System.out.println("============="+ele);
		Assert.assertEquals(ele, "false");
		
	}

}
