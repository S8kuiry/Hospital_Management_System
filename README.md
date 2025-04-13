# 🏥 Hospital Management System (Java + MySQL + Swing)

This is a simple Hospital Management System built using **Java**, **Swing (GUI)**, and **MySQL** for managing **patients**, **doctors**, and **appointments**. The system supports inserting, updating, and searching hospital data through a user-friendly interface.

---

## 📌 Features

- ✅ Insert patient, doctor, and appointment records
- 📝 Update existing records using patient, doctor, and appointment IDs
- 🔍 Search appointments by:
  - Doctor name
  - Patient name
  - Appointment date
- 🧾 Display real-time results in a scrollable text area

---

## 🧰 Technologies Used

- Java (Swing for GUI)
- JDBC (Java Database Connectivity)
- MySQL (Database)
- NetBeans / IntelliJ IDEA / Eclipse (Recommended IDEs)

---

## 🏗️ Database Schema

Make sure your MySQL database (`subh`) contains the following tables:

```sql
CREATE TABLE doctors (
    doctor_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    specialization VARCHAR(100)
);

CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    gender VARCHAR(10)
);

CREATE TABLE appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    doctor_id INT,
    appointment_date DATE,
    status VARCHAR(50),
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)
);
