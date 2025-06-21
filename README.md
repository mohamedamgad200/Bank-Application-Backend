# 🏦 Bank Application - Backend

This is a **Spring Boot-based banking application** that simulates core banking functionalities including user registration, account management, and money transfers. The goal of this project is to demonstrate the use of Spring Boot, Spring Security, and JPA in building a real-world RESTful backend system.

## 🚀 Features

- 🔐 Secure user registration and login using Spring Security and JWT
- 💳 Bank account creation with automatic IBAN generation
- 🔄 Money transfer between accounts with transaction history
- 📈 Role-based access (Admin vs Customer)
- 📜 Detailed API response and error handling

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA
- MySql
- Maven

## 📂 Project Structure

```
com.example.bank.application
├── config         # Security and application config
├── controller     # REST Controllers
├── model          # Entities (User, Account, Transaction)
├── repository     # Spring Data JPA interfaces
├── service        # Business logic
└── util           # Helper classes and JWT utilities
```

## 📦 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/mohamedamgad200/Bank-Application-Backend.git
   cd Bank-Application-Backend
   ```

2. Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/bankapp
   spring.datasource.username=your_db_user
   spring.datasource.password=your_db_pass
   ```

3. Run the project:
   ```bash
   mvn spring-boot:run
   ```
## 🤝 Contribute

Pull requests are welcome! Feel free to fork this repo and improve it. Issues and feedback are also appreciated.
