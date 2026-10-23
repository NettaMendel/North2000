# 🏢 North2000 - Enterprise Management System

A desktop enterprise application built with **Java Swing** for managing the operations of **NorthWind** — an import and export company dealing with specialty food products, customers, employees, suppliers, orders, inventory, and managerial reporting.

The system is engineered following the **ECB (Entity-Control-Boundary)** / **MVC** architectural pattern, utilizing **Microsoft Access** via the **UCanAccess** pure Java JDBC driver, **JasperReports** for data visualizations and reports, and data interchange via **XML** and **JSON**.

---

## 📐 System Architecture (ECB Pattern)

The project follows a clean separation of concerns located inside `src/`:

```
src/
├── boundary/    # 🖥️ Presentation layer: Swing GUI frames, dialogs, custom components & JasperReports
├── control/     # ⚙️ Business logic, SQL queries, and database controllers (Singletons)
├── entity/      # 📦 Domain entities (OOP object models) and Access database file
└── util/        # 🛠️ Utility classes and GUI helper components
```

---

## 📂 Detailed File Structure & Directory Map

### 1. Presentation Layer – `src/boundary/`
Handles graphical rendering, user interactions, input validation, window navigation, and report generation:

| File | Description & Role |
| :--- | :--- |
| **`North2000.java`** | **Application Main Entry Point**. Initializes the Swing Event Dispatch Thread (EDT) and launches `MainMenu`. |
| **`MainMenu.java`** | **Home Dashboard**. Provides intuitive quick-action buttons to access employee management, order processing, reporting, and XML/JSON data transfer modules. |
| **`RootLayout.java`** | **Base Application Window (`JFrame`)**. Defines the universal top menu bar (`JMenuBar`) for consistent system-wide navigation (`Orders`, `Employees`, `Reports`, `XML/JSON`, `Exit`). |
| **`FrmOrders.java`** | **Order Management Form (`JFrame`)**. Full-featured order browser and editor with navigation (`First`, `Previous`, `Next`, `Last`, `Search`), new order creation, order removal, and customer/employee/shipper linking. |
| **`FrmOrdersPanel.java`** | **Order Management Panel (`JPanel`)**. Modular order management component for flexible embedding across containers. |
| **`FrmOrderDetailsInternal.java`** | **Order Line Items Sub-Form**. Embedded directly inside the order form; manages individual product line items, quantity, discounts, live price calculations, and line deletion/saving. |
| **`FrmEmployees.java`** | **Employee Management Form**. Displays employee profiles, contact information, photos, titles, and dates, with complete CRUD operations. |
| **`FrmReportMenu.java`** | **Managerial Reports Dialog**. Parameter selection interface (e.g., product selection) for triggering dynamic JasperReports. |
| **`RptCustomerOrders.jrxml / .jasper`** | Source template and compiled binary for the **Customer Orders Report**, featuring order groupings and aggregate totals. |
| **`RptProductOrdersByCountry.jrxml / .jasper`** | Source template and compiled binary for the **Product Orders by Destination Country** visual chart report. |
| **`images/`** | UI asset directory containing application logos, icons, and button artwork. |

---

### 2. Business Logic & Controller Layer – `src/control/`
Implements business rules, transaction logic, database operations, and data exchange. All controllers follow the **Singleton pattern**:

| File | Description & Role |
| :--- | :--- |
| **`Consts.java`** | **System Constants & Configuration**. Contains database connection strings (`CONN_STR`), SELECT queries, and Stored Procedure / CallableStatement definitions (`qryIns...`, `qryUpd...`, `qryDel...`). |
| **`OrderLogic.java`** | **Order Business Controller**. Handles order fetching, line item retrieval, total price calculations, order manipulation, and runtime compilation/display of JasperReports. |
| **`CustomerLogic.java`** | **Customer Controller & Data Transfer**. Manages customer CRUD operations and bidirectional data transfer: XML import/export (via W3C DOM) and JSON import/export (via JSON-Simple). |
| **`EmployeeLogic.java`** | **Employee Business Controller**. Manages employee persistence, updates, and lookups. |
| **`ProductLogic.java`** | **Product Business Controller**. Retrieves product catalogs and product lookups by ID (`getProduct(id)`). |

---

### 3. Domain Entity Layer – `src/entity/`
Pure Object-Oriented representations of organizational business entities with full object associations:

| File | Description |
| :--- | :--- |
| **`Order.java`** | Represents a customer order. Holds direct OOP references to `Customer`, `Employee`, `Shipper`, order dates, and shipping destination addresses. |
| **`OrderDetails.java`** | Represents an order line item. Links `Order` and `Product`, holding quantity, discount rate, unit price, and calculated line price. |
| **`Customer.java`** | Represents a client/company. Includes `customerID`, company name, contact person, address, phone, and fax. |
| **`Employee.java`** | Represents an organization staff member. Includes employee ID, name, title, hire date, birth date, contact details, and photo path. |
| **`Product.java`** | Represents an inventory item. Links to `Supplier` and `Category`, holding product name, unit price, and stock levels. |
| **`Category.java`** | Represents a product classification (e.g., Beverages, Condiments, Produce). |
| **`Supplier.java`** | Represents an external product vendor. Includes supplier ID, company name, address, contact details, and homepage. |
| **`Shipper.java`** | Represents a shipping carrier (e.g., Speedy Express, Federal Shipping). |
| **`north2000DB.accdb`** | **Primary Database File**. Microsoft Access database storing all relational tables, indexes, constraints, and stored queries. |

---

### 4. Utilities & Support – `src/util/`
| File | Description |
| :--- | :--- |
| **`JTextFieldLimiter.java`** | Custom `PlainDocument` extension that restricts input character length on Swing text input fields. |

---

### 5. Data Interchange & Libraries

- **`xml/customers.xml` & `resources/customers.xml`**: XML data files for customer import/export workflows.
- **`json/customers.json`**: JSON data file for JSON serialization and parsing routines.
- **`lib/`**: External runtime dependencies:
  - `UCanAccess-5.0.1.bin`: Pure Java JDBC driver for accessing Microsoft Access `.accdb`/`.mdb` files without ODBC dependencies.
  - `json-simple-2.3.0.jar`: Lightweight library for JSON encoding and decoding.
  - `jcalendar-1.4.jar`: GUI date-picker component library (`JDateChooser`).
  - `guava-19.0.jar`: Google Core Libraries for Java collections and utilities.
  - **JasperReports**: Reporting engine libraries for report compilation, parameter binding, and visual rendering.

---

## 🛠️ Technology Stack & Requirements

- **Language:** Java (Fully compatible with **JDK 21** / Java 17+)
- **User Interface:** Java Swing & AWT
- **Database:** Microsoft Access (`north2000DB.accdb`)
- **Database Connectivity:** JDBC via UCanAccess 5.0.1
- **Reporting Engine:** JasperReports
- **Data Formats:** XML (W3C DOM Parser) and JSON (JSON-Simple)

---

## 🚀 Build & Run Instructions

### Running from an IDE (Eclipse / IntelliJ / Antigravity IDE)
1. Import the repository as an **Existing Java Project**.
2. Ensure all JAR files in `lib/` and the UCanAccess driver folders are added to the **Java Build Path**.
3. Run the main class:
   ```
   src/boundary/North2000.java
   ```

### Command Line Build & Execution (Terminal / PowerShell)

**1. Compile all source files:**
```powershell
javac --release 21 -cp "bin;lib/*;lib/lib/*;lib/UCanAccess-5.0.1.bin/*;lib/UCanAccess-5.0.1.bin/lib/*" -d bin src/entity/*.java src/control/*.java src/boundary/*.java src/util/*.java
```

**2. Launch the application:**
```powershell
java -cp "bin;lib/*;lib/lib/*;lib/UCanAccess-5.0.1.bin/*;lib/UCanAccess-5.0.1.bin/lib/*" boundary.North2000
```

---

## ✨ Key Capabilities & Features

- **End-to-End Order Processing:** Browse seamlessly through hundreds of orders, update shipping carriers, and manage dynamic line items with instant subtotal and line-price calculations.
- **Pure OOP Architecture:** UI ComboBoxes and table components interact directly with rich domain entities (`Customer`, `Employee`, `Shipper`, `Product`) rather than untyped wrapper primitives.
- **Interactive Visual Reporting:** Generate parameter-driven JasperReports with interactive previews, print capabilities, and PDF export support.
- **Dual Format Data Transfer:** Synchronize customer datasets with external systems via integrated XML and JSON import/export tools directly accessible from the main interface.
