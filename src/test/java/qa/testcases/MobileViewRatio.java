package qa.testcases;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import qa.base.Base;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;

public class MobileViewRatio extends Base {
    private WebDriver driver;
    private final String screenshotDir = "C:\\Users\\Riseup Labs\\eclipse-workspace\\MobileRatioView\\RatioViewScreenshots";

    @BeforeClass
    public void setup() throws InterruptedException {
        driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
        createDirectoryIfNotExists(screenshotDir);
    }

    @Test
    public void testMobileViewRatios() {
        captureScreenshot(375, 667, "iPhone_6_7_8");
        captureScreenshot(414, 736, "iPhone_8_Plus");
        captureScreenshot(375, 812, "iPhone_X");
        captureScreenshot(320, 568, "iPhone_SE");
        captureScreenshot(414, 896, "iPhone_11_Pro_Max");
        captureScreenshot(390, 844, "iPhone_12_Pro");
        captureScreenshot(428, 926, "iPhone_14_Pro");
        captureScreenshot(768, 1024, "iPad_Mini");
        captureScreenshot(1024, 1366, "iPad_Air");
        captureScreenshot(1024, 1366, "iPad_Pro");
        captureScreenshot(360, 760, "Samsung_Galaxy_S23_Ultra");
        captureScreenshot(412, 915, "Google_Pixel_4_XL");
        captureScreenshot(412, 869, "Google_Pixel_5");
        captureScreenshot(360, 740, "Samsung_Galaxy_S21");
    }

    @AfterMethod
    public void captureFailedTestScreenshot(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String methodName = result.getMethod().getMethodName();
            captureScreenshotOnFailure(methodName);
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    private void captureScreenshot(int width, int height, String modelName) {
        Screenshot screenshot = takeScreenshot(width, height);
        String filePath = screenshotDir + File.separator + modelName + ".png";
        saveScreenshotToFile(screenshot, filePath);
    }

    private void captureScreenshotOnFailure(String methodName) {
        Screenshot screenshot = new AShot().takeScreenshot(driver);
        String filePath = screenshotDir + File.separator + methodName + "_Failure.png";
        saveScreenshotToFile(screenshot, filePath);
    }

    private Screenshot takeScreenshot(int width, int height) {
        driver.manage().window().setSize(new Dimension(width, height));
        return new AShot().coordsProvider(new WebDriverCoordsProvider())
                .shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(1.25f), 1000))
                .takeScreenshot(driver);
    }

    private void saveScreenshotToFile(Screenshot screenshot, String filePath) {
        new File(filePath).getParentFile().mkdirs();
        try {
            ImageIO.write(screenshot.getImage(), "PNG", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + directoryPath);
            } else {
                System.out.println("Failed to create directory: " + directoryPath);
            }
        }
    }
}
