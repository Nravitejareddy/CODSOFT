# CODSOFT
# 🚀 CODSOFT Java Internship Tasks

This repository contains all the tasks completed as part of the **CODSOFT Java Internship**.  
All projects are implemented using **Java** and executed via **Command Line Interface (CLI)**.

---

## 📁 Folder Structure

CODSOFT/
│
├── Task-01-Number-Guessing-Game/
│ └── src/
│ └── NumberGame.java
│
├── Task-02-Student-Grade-Calculator/
│ └── src/
│ └── StudentGradeCalculator.java
│
├── Task-03-ATM-Interface/
│ └── src/
│ └── ATMInterface.java
│
├── Task-04-Currency-Converter/
│ ├── CurrencyConverterCLI.java
│ └── json-20230227.jar
│
├── Task-05-Student-Management-System/
│ └── src/
│ └── StudentManagementSystem.java
│
└── README.md

yaml
Copy code

---

## 🛠 Requirements

- Java JDK 8 or above
- Command Prompt / Terminal
- Internet connection (for Currency Converter)

Check Java version:
```bash
java -version
🎯 Task 01: Number Guessing Game
📌 Description
A simple game where the system generates a random number and the user tries to guess it.
The program provides feedback until the correct number is guessed.

▶ How to Run
bash
Copy code
cd Task-01-Number-Guessing-Game/src
javac NumberGame.java
java NumberGame
🎓 Task 02: Student Grade Calculator
📌 Description
Takes marks obtained (out of 100) in each subject

Calculates total marks

Calculates average percentage

Assigns grades based on percentage

▶ How to Run
bash
Copy code
cd Task-02-Student-Grade-Calculator/src
javac StudentGradeCalculator.java
java StudentGradeCalculator
🏦 Task 03: ATM Interface
📌 Description
A CLI-based ATM simulation with the following features:

Withdraw money

Deposit money

Check account balance

Input validation for balance and amounts

▶ How to Run
bash
Copy code
cd Task-03-ATM-Interface/src
javac ATMInterface.java
java ATMInterface
💱 Task 04: Currency Converter
📌 Description
Converts currency from one type to another

Fetches real-time exchange rates using an API

Parses JSON response using an external library

📦 External Dependency
json-20230227.jar

▶ How to Run (IMPORTANT)
Ensure both files are in the same directory:

pgsql
Copy code
CurrencyConverterCLI.java
json-20230227.jar
Compile:

bash
Copy code
javac -cp ".;json-20230227.jar" CurrencyConverterCLI.java
Run:

bash
Copy code
java -cp ".;json-20230227.jar" CurrencyConverterCLI
🧑‍🎓 Task 05: Student Management System
📌 Description
A complete CLI-based Student Management System that supports:

Add student

Edit student

Remove student

Search student

Display all students

Input validation

Structured table output

▶ How to Run
bash
Copy code
cd Task-05-Student-Management-System/src
javac StudentManagementSystem.java
java StudentManagementSystem
🖥 Execution Mode
All tasks are Command Line Interface (CLI) based

No GUI (Swing / JavaFX) used

Output is displayed directly in the terminal

📸 Screenshots
Screenshots are optional

CLI output screenshots can be added if required by CODSOFT

📌 Notes
Folder structure is organized per task

Source files are placed inside src folders

No IDE-specific files included

Code is platform-independent

👤 Author
Ravi Teja
Java Intern – CODSOFT

