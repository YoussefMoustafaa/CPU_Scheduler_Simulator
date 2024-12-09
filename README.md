# Process Scheduling Application

A Java application designed to manage and simulate process scheduling. The application allows users to define various processes with attributes like arrival time, burst time, priority, and quantum, while supporting different process types and scheduling methods.

---

## Features

- **Process Management**:
  - Add processes with attributes: Name, Arrival Time, Burst Time, Priority, and Quantum.
  - Support for extended process types through inheritance (`PriorityProcess`, `FCAIProcess`).

- **Input Options**:
  - Accepts process data from user input or pre-defined strings.
  - Reads data line by line, supports multi-line input parsing.

- **GUI (Optional)**:
  - Includes Swing-based input interfaces with support for integer inputs using components like `JSpinner`.

- **Flexibility**:
  - Dynamic list handling for processes.
  - Processes can be created, sorted, and manipulated easily.

---

## Installation

1. Ensure you have **Java 8 or higher** installed.
2. Clone the repository:
   ```bash
   git clone https://github.com/your-username/your-repo.git
   ```
3. Navigate to the project directory:
   ```bash
   cd your-repo
   ```

---

## How to Run

### Command Line
1. Compile the code:
   ```bash
   javac -d bin src/*.java
   ```
2. Run the program:
   ```bash
   java -cp bin Main
   ```

### Using an IDE
1. Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse, VS Code).
2. Set up the project structure.
3. Run the `Main` class.

---

## Example Input

```plaintext
p1, 0, 17, 4, 4
p2, 3, 6, 9, 3
p3, 4, 10, 3, 5
p4, 29, 4, 8, 2
```

---

## Contributing

1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature/your-feature
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add your message"
   ```
4. Push the branch:
   ```bash
   git push origin feature/your-feature
   ```
5. Open a pull request.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Acknowledgments

- Inspired by scheduling algorithms and process management concepts.
- Developed with ❤️ and Java.

---

## Contact

For questions or feedback, feel free to reach out:

- **Name**: Your Name  
- **Email**: your.email@example.com  
- **GitHub**: [YourUsername](https://github.com/your-username)
