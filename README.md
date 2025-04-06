# Hospital Management System (OOP Assignment)

This project was developed as part of a university Object-Oriented Programming (OOP) course. It simulates a hospital management system with a modular structure, demonstrating core OOP principles and best practices in Java.

## 🏥 Features

- **Entity Management**: Manage patients, doctors, nurses, departments, diseases, injuries, fractures, and treatments.
- **Custom Exceptions**: Informative error messages using tailored exception classes.
- **Inheritance & Polymorphism**: Class hierarchy for medical and staff roles.
- **Developer Tools**:
  - **Test Mode**: Bypass login and enter as a doctor/nurse for quick debugging.
  - **Entity Generator**: Generate random, valid objects for testing.
  - **Data Saving Options**: Choose whether to persist changes or discard them after testing.
- **Navigation**: Stack-based `CardLayout` with a global Back button.
- **Constraints Enforcement**: Prevents invalid actions like creating a visit without a patient.
- **Word Export**: Uses Apache POI to export patient data to `.docx`.

## 🛠️ Technologies Used

- Java
- Apache POI
- Java Swing
- Custom utility & exception classes
- Serializable (for state management)

## 🚀 How to Use

- **Admin Login**  
  Use the following credentials to log in as an admin:  
  `ID: ADMIN`  
  `PASSWORD: ADMIN`

- **Registering & Testing Roles**  
  You can register any unregistered entity (e.g., nurse or doctor) and then sign in as them to explore the system with different permissions and role-based access.

## 🎓 Course Context

Assignment for an Object-Oriented Programming course. Focused on applying OOP concepts such as encapsulation, abstraction, inheritance, and polymorphism in a realistic software scenario.

## 📂 Project Structure

- `model/` – Entity classes (`Doctor`, `Patient`, `Disease`, etc.)
- `control/` – Core system controller (`Hospital.java`)
- `exceptions/` – Custom exception classes
- `utils/` – Logging and helper functions
- `view/` – GUI components (if applicable)

---

