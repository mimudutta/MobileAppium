package Framework;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class base {
	
	public static AndroidDriver driver;
	public static AppiumDriverLocalService service;
	public static String JsFilePath = "C:\\Users\\mimu.dutta\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
	
	
	@BeforeClass
	public static AndroidDriver Capablities() throws MalformedURLException, InterruptedException {
		
		service = new AppiumServiceBuilder().withAppiumJS(new File(JsFilePath))
				.withIPAddress("0.0.0.0").usingPort(4723).build();
		//service.start();
		//Thread.sleep(10000);
		
		File appDir = new File("src/main/java");
		File app = new File(appDir,"ApiDemos-debug.apk");
		
		DesiredCapabilities cap = new DesiredCapabilities();			//New way of initialising
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "MimuEmulator_Pixel2XLAnd11");       
		cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		
//		UiAutomator2Options options =new UiAutomator2Options();		//New way of initialising
//		options.setDeviceName("MimuEmulator_Pixel2XLAnd11");
//		options.setApp(app.getAbsolutePath());
		
		driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"),cap);    //Use options instead of cap while using new way of initialising
		
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // old format
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // new format as per selenium4
		return driver;
	}
	
	public void longPressAction(WebElement element) {
		((JavascriptExecutor)driver).executeScript("mobile: longClickGesture", 
				ImmutableMap.of("elementId",((RemoteWebElement)element).getId(), "duration",3000));
	}
	
	@AfterClass
	public static void tearDown() {
		
		driver.quit();
		//service.stop();;
	}

}