# Magento Automation Framework

This repository contains an automation framework for testing the **Magento** website: [Magento Software Testing Board](https://magento.softwaretestingboard.com/).

The framework is built using **Selenium** with **SafariDriver**, and the tests are managed using **TestNG**.

## Test Cases

### 1. **Create Account Test**  
This test case automates the process of creating a new user account on the Magento website.  
It performs the following actions:
- Fills in the registration form with **first name**, **last name**, **email**, **password**, and **confirm password**.
- Submits the form.
- Verifies that the account is successfully created and the user is logged in.

### 2. **Login Test**  
This test case automates the process of logging into an existing account on the Magento website.  
It performs the following actions:
- Fills in the **email** and **password** fields.
- Clicks on the **Sign In** button.
- Verifies that the user is successfully logged in.

## Prerequisites

Before running the tests, make sure you have the following installed on your system:

- **Java** version 17 or higher
- **Maven** (for managing dependencies and running tests)
- **Selenium WebDriver** 4.x
- **SafariDriver** (installed and configured for Safari)
- **TestNG** (for running the test cases)

