# Inventory-System-Management
A Java-based Inventory Management System with role-based dashboards (Admin/User), product &amp; order tracking, and MySQL database integration.


# Inventory Management System

A Java-based Inventory Management System with role-based dashboards (Admin/User), product and order tracking, and MySQL database integration. Built using the DAO (Data Access Object) design pattern for clean separation between business logic and database operations.

## Features

- **User Authentication** — Login system with role-based access (Admin / User)
- **Admin Dashboard** — Manage products, view all orders, oversee inventory
- **User Dashboard** — Browse products, place orders
- **Product Management** — Add, update, view, and track product stock
- **Order Management** — Create and track customer/user orders
- **Database Integration** — MySQL connection via JDBC

## Tech Stack

- **Language:** Java
- **Database:** MySQL
- **Design Pattern:** DAO (Data Access Object)
- **Connectivity:** JDBC

## Project Structure

```
InventorySystem/
├── config/         # Database connection setup
├── dao/            # Data Access Objects (ProductDAO, UserDAO)
├── Dashboard/       # Admin and User dashboard UI logic
├── model/          # Data models (Product, User, Order)
├── service/        # Business logic layer
├── ui/             # Menu and user interface handling
└── Main.java       # Application entry point
```

## Prerequisites

- Java JDK 8 or higher
- MySQL Server
- MySQL JDBC Driver (Connector/J)

## Setup & Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/Inventory-System-Management.git
   cd Inventory-System-Management
   ```

2. **Set up the database**
   - Create a MySQL database for the project
   - Import the SQL schema (if available) or create the required tables manually

3. **Configure database connection**
   - Copy `config/DBConnection.example.java` to `config/DBConnection.java`
   - Update it with your own database URL, username, and password:
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/your_db_name";
     private static final String USER = "your_username";
     private static final String PASSWORD = "your_password";
     ```
   - **Note:** `config/DBConnection.java` is excluded via `.gitignore` to keep credentials private

4. **Compile and run**
   ```bash
   javac -d bin src/**/*.java
   java -cp bin Main
   ```

## Usage

- Run the application and log in with an Admin or User account
- **Admin** can manage products and view all system orders
- **User** can browse available products and place orders

## Security Note

Database credentials are kept out of version control. Make sure you never commit your real `DBConnection.java` file — only the `.example.java` template should be pushed to GitHub.

## License

This project is open source and available under the [MIT License](LICENSE).

## Contributing

Contributions, issues, and feature requests are welcome. Feel free to open a pull request or an issue.

DEVELOP BY RAYUTH DEV 
