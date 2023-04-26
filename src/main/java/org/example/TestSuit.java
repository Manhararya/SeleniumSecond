package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestSuit {
    // Create all drivers
    protected static WebDriver driver;
    //Create all methods
    public static void clickElement(By by){
        driver.findElement(by).click();
    }
    public static void typeText(By by, String text){
        driver.findElement(by).sendKeys(text);
    }
    public static String getTextFromElement(By by){
        return driver.findElement(by).getText();
    }
    public static long timestamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }
    @BeforeMethod
    public static void openBrowser(){
        driver = new ChromeDriver();
        driver.get("https://demo.nopcommerce.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @AfterMethod
    public static void closeBrowser(){
        driver.close();
    }
    // Create all Strings
    static String expectedRegistrationCompletedMessage = "Your registration completed";
    static String expectedCommunityPollVoteMessage = "Only registered users can vote";
    static String expectedEmailToFriendMessage = "Only registered customers can use email a friend feature";
    static String expectedCompareProductMessage = "You have no items to compare.";
    static String expectedShoppingCartMessage = "Leica T Mirrorless Digital Camera";
    static String expectedReferProductMessage = "Your message has been sent.";
    static String expectedAbleToVoteMessage = "18 vote(s)...";
    // execute all test cases
    @Test
    public static void verifyUserShouldBeAbleToRegisterSuccessfully(){
        // Open register page and fill all mandatory details in register form
        clickElement(By.className("ico-register"));
        typeText(By.id("FirstName"), "FirstNameTest");
        typeText(By.id("LastName"), "TestLAstName");
        typeText(By.name("Email"), "testEmail+"+timestamp()+"@gmail.com");
        typeText(By.id("Password"), "test12345");
        typeText(By.id("ConfirmPassword"), "test12345");
        // Complete registration
        clickElement(By.id("register-button"));
        // End of the process Actual message will come
        String actualMessage = getTextFromElement(By.xpath("//div [@ class= \"result\"]"));
        System.out.println("My Message:"+actualMessage);
        // Expected message
        Assert.assertEquals(actualMessage,expectedRegistrationCompletedMessage,"registration is not working");
    }
    @Test
    public static void verifyAbleToVoteToCommunityPoll(){
        // Choose polling option
        clickElement(By.id("pollanswers-2"));
        // Do Vote
        clickElement(By.id("vote-poll-1"));
        // Add driver
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Add method
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"poll-vote-error\"]")));
        // End of the process Actual message will come
        String actualMessage = getTextFromElement(By.xpath("//div[@class=\"poll-vote-error\"]"));
        System.out.println("My Message:"+actualMessage);
        // Expected message
        Assert.assertEquals(actualMessage,expectedCommunityPollVoteMessage, "Error message is disappearing");
    }
    @Test
    public static void verifyUserShouldBeAbleToSendEmailToFriend(){
        // Choose Apple MacBook Pro 13-inch
        clickElement(By.linkText("Apple MacBook Pro 13-inch"));
        // Go to email friend
        clickElement(By.className("email-a-friend"));
        // Put friend's email address
        typeText(By.className("friend-email"), "Arya25"+timestamp()+"@gmail.com");
        // Put your email address
        typeText(By.className("your-email"), "manhararya25+"+timestamp()+"@gmail.com");
        // Type message
        typeText(By.id("PersonalMessage"), "Hello");
        // Click on Send email button
        clickElement(By.name("send-email"));
        // End of the process Actual message will come
        String actualMessage = getTextFromElement(By.xpath("//div[@class=\"message-error validation-summary-errors\"]/ul[1]"));
        System.out.println("My Message:"+actualMessage);
        // Expected message
        Assert.assertEquals(actualMessage,expectedEmailToFriendMessage, "Can't sending email");
    }
    @Test
    public static void verifyUserShouldBeAbleToCompareProduct(){
        // Choose HTC One M8 Android L 5.0 Lollipop
        clickElement(By.linkText("HTC One M8 Android L 5.0 Lollipop"));
        // Click Compare button
        clickElement(By.xpath("//div[@class=\"compare-products\"]"));
        // Go to Gift Cards category
        clickElement(By.linkText("Gift Cards"));
        // Choose $25 Virtual Gift Card
        clickElement(By.linkText("$25 Virtual Gift Card"));
        // Click Compare button
        clickElement(By.xpath("//div[@class=\"compare-products\"]"));
        // Click compare option
        clickElement(By.linkText("product comparison"));
        // After comparison first product's name will come
        String actualMessage1 = getTextFromElement(By.linkText("HTC One M8 Android L 5.0 Lollipop"));
        System.out.println("First Product:"+actualMessage1);
        // After comparison second product's name will come
        String actualMessage2 = getTextFromElement(By.linkText("$25 Virtual Gift Card"));
        System.out.println("Second Product:"+actualMessage2);
        // Click all product
        clickElement(By.className("clear-list"));
        // End of the process Actual message will come
        String actualMessage3 = getTextFromElement(By.xpath("//div[@class=\"page-body\"]"));
        System.out.println("My Message:"+actualMessage3);
        // Expected message
        Assert.assertEquals(actualMessage3,expectedCompareProductMessage, "Can't comparing products");
    }
    @Test
    public static void verifyUserShouldBeAbleToAddProductToShoppingCart(){
        // Click Electronics category
        clickElement(By.linkText("Electronics"));
        // Click Camera & photo option
        clickElement(By.linkText("Camera & photo"));
        // Choose Leica T Mirrorless Digital Camera
        clickElement(By.linkText("Leica T Mirrorless Digital Camera"));
        // Add Leica T Mirrorless Digital Camera to cart
        clickElement(By.id("add-to-cart-button-16"));
        // Go to Shopping cart
        clickElement(By.linkText("Shopping cart"));
        // End of the process Actual message will come
        String actualMessage = getTextFromElement(By.xpath("//*[@id=\"shopping-cart-form\"]/div[1]/table/tbody/tr/td[3]"));
        System.out.println("My Message:"+actualMessage);
        // Expected message
        Assert.assertEquals(actualMessage,expectedShoppingCartMessage, "Product is not adding in shopping cart");
    }
    @Test
    public static void verifyUserShouldBeAbleToReferProductToFriend(){
        // Open register page and fill all mandatory details in register form
        clickElement(By.className("ico-register"));
        typeText(By.id("FirstName"), "Manhar");
        typeText(By.id("LastName"), "Arya");
        typeText(By.name("Email"), "manhararya25@gmail.com");
        typeText(By.id("Password"), "1111aaaa");
        typeText(By.id("ConfirmPassword"), "1111aaaa");
        // Complete registration
        clickElement(By.id("register-button"));
        // Open Login Page
        clickElement(By.className("ico-login"));
        // Enter your email address
        typeText(By.id("Email"), "manhararya25@gmail.com");
        // Enter Password
        typeText(By.id("Password"), "1111aaaa");
        // Click Log in button
        clickElement(By.xpath("//div[@class=\"buttons\"]//button[@class=\"button-1 login-button\"]"));
        // Choose Apple MacBook Pro 13-inch
        clickElement(By.linkText("Apple MacBook Pro 13-inch"));
        // Go to email friend
        clickElement(By.className("email-a-friend"));
        // Put friend's email address
        typeText(By.className("friend-email"), "bdfba@gmail.com");
        // Type message
        typeText(By.id("PersonalMessage"), "This MacBook is a best");
        // Click on Send email button
        clickElement(By.name("send-email"));
        // End of the process Actual message will come
        String actualMessage = getTextFromElement(By.xpath("//div[@class=\"result\"]"));
        System.out.println("My Message:"+actualMessage);
        // Expected message
        Assert.assertEquals(actualMessage,expectedReferProductMessage, "Successfully message send");
    }
    @Test
    public static void verifyUserShouldBeAbleToVote(){
        // Open register page and fill all mandatory details in register form
        clickElement(By.className("ico-register"));
        typeText(By.id("FirstName"), "Manhar");
        typeText(By.id("LastName"), "Arya");
        typeText(By.name("Email"), "manhararya25@gmail.com");
        typeText(By.id("Password"), "1111aaaa");
        typeText(By.id("ConfirmPassword"), "1111aaaa");
        // Complete registration
        clickElement(By.id("register-button"));
        // Open Login Page
        clickElement(By.className("ico-login"));
        // Enter your email address
        typeText(By.id("Email"), "manhararya25@gmail.com");
        // Enter Password
        typeText(By.id("Password"), "1111aaaa");
        // Click Log in button
        clickElement(By.xpath("//div[@class=\"buttons\"]//button[@class=\"button-1 login-button\"]"));
        // Choose polling option
        clickElement(By.id("pollanswers-2"));
        // Do Vote
        clickElement(By.id("vote-poll-1"));
        // End of the process Actual message will come
        String actualMessage = getTextFromElement(By.xpath("//span[@class=\"poll-total-votes\"]"));
        System.out.println("My Message:"+actualMessage);
        // Expected message
        Assert.assertEquals(actualMessage,expectedAbleToVoteMessage, "Total Votes are wrong");
    }
}