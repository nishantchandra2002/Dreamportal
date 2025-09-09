# Dream Portal QA Automation Project

This project contains automated functional tests for the "Dream Portal" website using Selenium, Java, and TestNG.

## 🧪 Tech Stack
- **Language:** Java 17
- **Automation Tool:** Selenium WebDriver
- **Testing Framework:** TestNG
- **Build Tool:** Apache Maven
- **Reporting:** Allure Report
- **Browser Driver Management:** WebDriverManager
- **Bonus:** OpenAI API for AI-based validation

## 🚀 How to Run
1. Clone the repository.
2. Make sure you have Java 17 and Maven installed.
3. (Optional) For the AI test, set your OpenAI API key as an environment variable:
   `export OPENAI_API_KEY='your-key-here'`
4. Navigate to the project root and run the tests using Maven:
   ```bash
   mvn clean test
   ```
5. To view the Allure report, run:
   ```bash
   allure serve target/allure-results
   ```

## 📂 Project Structure
The project follows the Page Object Model (POM) design pattern:
- `src/test/java/com/dreamportal/tests/base`: Base test for WebDriver setup/teardown.
- `src/test/java/com/dreamportal/tests/pages`: Page Object classes for each page of the application.
- `src/test/java/com/dreamportal/tests/tests`: Contains the TestNG test cases.
- `src/test/java/com/dreamportal/tests/services`: Contains the service for AI validation.