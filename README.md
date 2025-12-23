# 🚀 CODSOFT Java Internship Tasks

This repository contains all tasks completed as part of the **CODSOFT Java Internship Program**.  
All projects are **CLI-based Java applications** designed to run on Windows Command Prompt or any terminal.

---

## 📁 Repository Structure

```yaml
CODSOFT/
│
├── Task-01-Number-Guessing-Game/
│   └── src/
│       └── NumberGame.java
│
├── Task-02-Student-Grade-Calculator/
│   └── src/
│       └── StudentGradeCalculator.java
│
├── Task-03-ATM-Interface/
│   └── src/
│       └── ATMInterface.java
│
├── Task-04-Currency-Converter/
│   ├── CurrencyConverterCLI.java
│   └── json-20230227.jar
│
├── Task-05-Student-Management-System/
│   └── src/
│       └── StudentManagementSystem.java
│
└── README.md
```
## 🎯 Task 01: Number Guessing Game

### 📌 Description
A simple game where the computer generates a random number and the user tries to guess it.

### ▶ How to Run
```bash
javac NumberGame.java
java NumberGame
```
## 🎓 Task 02: Student Grade Calculator

### 📌 Description
- Takes marks (out of 100) for multiple subjects  
- Calculates total marks  
- Calculates average percentage  
- Assigns grade based on percentage  

### ▶ How to Run
```cmd
javac StudentGradeCalculator.java
java StudentGradeCalculator
```
## 🏦 Task 03: ATM Interface

### 📌 Description
- Simulates an ATM Machine  
- Options:  
  - Withdraw  
  - Deposit  
  - Check Balance  
- Includes input validation  

### ▶ How to Run
```cmd
javac ATMInterface.java
java ATMInterface
```
## 💱 Task 04: Currency Converter

### 📌 Description
- Converts currency using real-time exchange rates  
- Uses an external JSON API  
- Parses JSON response using `org.json` library  

### 📦 External Library Used
- `json-20230227.jar`  

Make sure both files are in the same folder:

```text
CurrencyConverterCLI.java
json-20230227.jar
```
### ▶ How to Run
```cmd
javac -cp ".;json-20230227.jar" CurrencyConverterCLI.java
java -cp ".;json-20230227.jar" CurrencyConverterCLI
```
## 🧑‍🎓 Task 05: Student Management System

### 📌 Description
A complete student management system that allows:
- Add Student  
- Edit Student  
- Remove Student  
- Search Student  
- Display All Students  
- File-based data storage  
- Fixed-width colored tables  
- Proper input validation  

### ▶ How to Run
```cmd
javac StudentManagementSystem.java
java StudentManagementSystem
```
### 🖥 Execution Mode
- All projects are **Command-Line Interface (CLI)** based  
- No GUI frameworks (Swing / JavaFX) are used  
- Output is displayed directly in the terminal  

---

### 👤 Author
**N. Ravi Teja Reddy**  
Java Intern – CODSOFT





