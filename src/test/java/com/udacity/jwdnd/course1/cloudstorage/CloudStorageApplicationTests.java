package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}
	
	private void autoCreateNote() {
		try {
			doLogIn("noteauto", "noteauto");
		} catch (Exception e) {
			doMockSignUp("noteauto", "noteauto", "noteauto", "noteauto");
			doLogIn("noteauto", "noteauto");
		}

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		// Go to Notes tab
		driver.findElement(By.id("nav-notes-tab")).click();

		// Press add new note button
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note-btn")));
		WebElement buttonAddnotes = driver.findElement(By.id("add-note-btn"));
		buttonAddnotes.click();

		// Fill out the note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement inputTitle = driver.findElement(By.id("note-title"));
		inputTitle.click();
		inputTitle.sendKeys("Create Note Title 1");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement inputDescription = driver.findElement(By.id("note-description"));
		inputDescription.click();
		inputDescription.sendKeys("Create Note Description");

		// Submit
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-submit-btn")));
		WebElement submitNote = driver.findElement(By.id("note-submit-btn"));
		submitNote.click();

		// Back to home page
		driver.get("http://localhost:" + this.port + "/home");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();
	}
	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	/*Write a Selenium test that verifies that the home page is not accessible without logging in.*/
	@Test
	public void accessHomeNoLogin() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}
	
	/*Write a Selenium test that signs up a new user, logs that user in, verifies that they can access the home page, then logs out and verifies that the home page is no longer accessible.*/
	@Test
	public void singUppLoginLogoutAccessHomeNoLogin() {

		doMockSignUp("login","login","login","login");
		doLogIn("login", "login");
		
		// check is home page
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		//Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
		
		// logout
		WebElement logoutButton= driver.findElement(By.id("logoutButton"));
		logoutButton.click();
		webDriverWait.until(ExpectedConditions.titleContains("Login"));
	//	Assertions.assertEquals("Login", driver.getTitle());
		Assertions.assertEquals("http://localhost:" + this.port + "/login?logout", driver.getCurrentUrl());
	}

	/*Write a Selenium test that logs in an existing user, creates a note and verifies that the note details are visible in the note list.*/
	@Test
	public void doCreateNote() {
		try {
			doLogIn("notenew", "notenew");
		} catch (Exception e) {
			doMockSignUp("notenew", "notenew", "notenew", "notenew");
			doLogIn("notenew", "notenew");
		}

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		// Go to Notes tab
		driver.findElement(By.id("nav-notes-tab")).click();

		// Press add new note button
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note-btn")));
		WebElement buttonAddnotes = driver.findElement(By.id("add-note-btn"));
		buttonAddnotes.click();

		// Fill out the note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement inputTitle = driver.findElement(By.id("note-title"));
		inputTitle.click();
		inputTitle.sendKeys("Title 1");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement inputDescription = driver.findElement(By.id("note-description"));
		inputDescription.click();
		inputDescription.sendKeys("Description 1");

		// Submit
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-submit-btn")));
		WebElement submitNote = driver.findElement(By.id("note-submit-btn"));
		submitNote.click();

		// Back to home page
		driver.get("http://localhost:" + this.port + "/home");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();

		// Verify that the note was created
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTable")));
		WebElement table = driver.findElement(By.id("noteTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		WebElement firstNoteTitle = rows.get(1).findElement(By.tagName("th"));
		WebElement firstNoteDetail = (rows.get(1).findElements(By.tagName("td"))).get(1);

		Assertions.assertAll("Should return correct note",
				() -> Assertions.assertEquals("Title 1", firstNoteTitle.getText()),
				() -> Assertions.assertEquals("Description 1", firstNoteDetail.getText()));
	}

	/*Write a Selenium test that logs in an existing user with existing notes, clicks the edit note button on an existing note, changes the note data, saves the changes, and verifies that the changes appear in the note list.*/
	@Test
	public void doUpdateNote() {
		autoCreateNote();
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		// Go to Notes tab
		driver.findElement(By.id("nav-notes-tab")).click();

		// Press edit note button
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTable")));
		WebElement table = driver.findElement(By.id("noteTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		WebElement editbtn = rows.get(1).findElement(By.tagName("button"));
		editbtn.click();

		// Fill out the note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement inputTitle = driver.findElement(By.id("note-title"));
		inputTitle.click();
		inputTitle.clear();
		inputTitle.sendKeys("Note title edited");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement inputDescription = driver.findElement(By.id("note-description"));
		inputDescription.click();
		inputDescription.clear();
		inputDescription.sendKeys("Note description edited");

		// Submit
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-submit-btn")));
		WebElement submitNote = driver.findElement(By.id("note-submit-btn"));
		submitNote.click();

		// Back to home page
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();

		// Verify that the note was created
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTable")));
		table = driver.findElement(By.id("noteTable"));
		rows = table.findElements(By.tagName("tr"));
		WebElement firstNoteTitle = rows.get(1).findElement(By.tagName("th"));
		WebElement firstNoteDetail = (rows.get(1).findElements(By.tagName("td"))).get(1);

		Assertions.assertAll("Should return correct note",
				() -> Assertions.assertEquals("Note title edited", firstNoteTitle.getText()),
				() -> Assertions.assertEquals("Note description edited", firstNoteDetail.getText()));
	}
	
	/*Write a Selenium test that logs in an existing user with existing notes, clicks the delete note button on an existing note, and verifies that the note no longer appears in the note list.*/
	@Test
	public void doDeleteNote() {
		try {
			doLogIn("noteauto", "noteauto");
		} catch (Exception e) {
			doMockSignUp("noteauto","noteauto","noteauto","noteauto");
			doLogIn("noteauto", "noteauto");
		}

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		// Go to Notes tab
		driver.findElement(By.id("nav-notes-tab")).click();

		// Press edit note button
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTable")));
		WebElement table = driver.findElement(By.id("noteTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		WebElement deletebtn = rows.get(1).findElement(By.tagName("a"));
		deletebtn.click();

		// Back to home page
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();

		// Verify that the note was created
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTable")));
		table = driver.findElement(By.id("noteTable"));
		rows = table.findElements(By.tagName("tr"));

		Assertions.assertEquals(1, rows.size());
	}
	
	/*Write a Selenium test that logs in an existing user, creates a credential and verifies that the credential details are visible in the credential list.*/
	@Test
	public void doCreateCredential() {
		try {
			doLogIn("credential", "credential");
		} catch (Exception e) {
			doMockSignUp("credential","credential","credential","credential");
			doLogIn("credential", "credential");
		}

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		// Go to credential tab
		driver.findElement(By.id("nav-credentials-tab")).click();

		// Press add new credential button
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-credential-btn")));
		WebElement addNewCredentialButton= driver.findElement(By.id("add-credential-btn"));
		addNewCredentialButton.click();

		// Fill out the credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement inputUrl = driver.findElement(By.id("credential-url"));
		inputUrl.click();
		inputUrl.sendKeys("http://localhost:8080/login");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement inputUsername = driver.findElement(By.id("credential-username"));
		inputUsername.click();
		inputUsername.sendKeys("credential");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement inputPassword = driver.findElement(By.id("credential-password"));
		inputPassword.click();
		inputPassword.sendKeys("credential");

		// Submit
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-submit-btn")));
		WebElement submitNote = driver.findElement(By.id("credential-submit-btn"));
		submitNote.click();

		// Back to home page
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();

		// Verify that the credential was created
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		WebElement table = driver.findElement(By.id("credentialTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		WebElement firstCredentialUrl = rows.get(1).findElement(By.tagName("th"));
		WebElement firstCredentialPassword = (rows.get(1).findElements(By.tagName("td"))).get(1);

		Assertions.assertAll("Should return correct credential",
				() -> Assertions.assertEquals("http://localhost:8080/login", firstCredentialUrl.getText()),
				() -> Assertions.assertEquals("credential", firstCredentialPassword.getText()));
	}
	
	/*Write a Selenium test that logs in an existing user with existing credentials, clicks the edit credential button on an existing credential, changes the credential data, saves the changes, and verifies that the changes appear in the credential list.*/
	@Test
	public void doEditCredential() {
		try {
			doLogIn("credential", "credential");
		} catch (Exception e) {
			doMockSignUp("credential","credential","credential","credential");
			doLogIn("credential", "credential");
		}

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		// Go to credential tab
		driver.findElement(By.id("nav-credentials-tab")).click();

		// Press edit credential button
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		WebElement table = driver.findElement(By.id("credentialTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		WebElement editbtn = rows.get(1).findElement(By.tagName("button"));
		editbtn.click();

		// Edit the credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement inputUrl = driver.findElement(By.id("credential-url"));
		inputUrl.click();
		inputUrl.clear();
		inputUrl.sendKeys("http://localhost:8080/login/edit");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement inputUsername = driver.findElement(By.id("credential-username"));
		inputUsername.click();
		inputUsername.clear();
		inputUsername.sendKeys("usernameedited");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement inputPassword = driver.findElement(By.id("credential-password"));
		inputPassword.click();
		inputPassword.clear();
		inputPassword.sendKeys("456");

		// Submit
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-submit-btn")));
		WebElement submitNote = driver.findElement(By.id("credential-submit-btn"));
		submitNote.click();

		// Back to home page
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();

		// Verify that the credential was created
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		table = driver.findElement(By.id("credentialTable"));
		rows = table.findElements(By.tagName("tr"));
		WebElement firstCredentialUrl = rows.get(1).findElement(By.tagName("th"));
		WebElement firstCredentialPassword = (rows.get(1).findElements(By.tagName("td"))).get(1);

		Assertions.assertAll("Should return correct credential",
				() -> Assertions.assertEquals("http://localhost:8080/login/edit", firstCredentialUrl.getText()),
				() -> Assertions.assertEquals("usernameedited", firstCredentialPassword.getText()));
	}

	/*Write a Selenium test that logs in an existing user with existing credentials, clicks the delete credential button on an existing credential, and verifies that the credential no longer appears in the credential list.*/
	@Test
	public void doDeleteCredential() {
		try {
			doLogIn("credential", "credential");
		} catch (Exception e) {
			doMockSignUp("credential","credential","credential","credential");
			doLogIn("credential", "credential");
		}

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		// Go to credential tab
		driver.findElement(By.id("nav-credentials-tab")).click();

		// Press delete credential button
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		WebElement table = driver.findElement(By.id("credentialTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		WebElement deletebtn = rows.get(1).findElement(By.tagName("a"));
		deletebtn.click();

		// Back to home page
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();

		// Verify that the note was deleted
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		table = driver.findElement(By.id("credentialTable"));
		rows = table.findElements(By.tagName("tr"));

		Assertions.assertEquals(1, rows.size());
	}

}
