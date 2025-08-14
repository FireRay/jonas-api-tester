# jonas-api-tester

[![CI](https://github.com/FireRay/jonas-api-tester/actions/workflows/ci.yml/badge.svg)](https://github.com/FireRay/jonas-api-tester/actions/workflows/ci.yml)

🚀 Automated REST API Testing Framework  
Built with **Java**, **JUnit 5**, **RestAssured**, **Hamcrest**, and **Maven**.

## ✅ Features
- `GET` request test for `/posts/1`
- `POST` request test with JSON payload
- `PUT` request test to update existing post
- `DELETE` request test to remove post
- **Motivation meta-tests** — lightweight, passing-by-design tests used to reinforce coding flow and consistency.  
  These serve as a quick warm-up before running the main test suite.


## 📂 Structure
`````
jonas-api-tester/
├── src/
│ └── test/
│ └── java/
│ ├── motivation/
│ │ └── MotivationTests.java
│ └── ApiTest.java
├── pom.xml
└── README.md
`````
