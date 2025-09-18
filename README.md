# Decode32

Java program that reads 32-bit machine-code binary files and converts them into human-readable assembly instructions. Built as part of a Computer Architecture course; I implemented the majority of logic including parsing, bit-field handling, and test suite.

---

## Tech & Tools

- Language: **Java**
- Build & Run: Shell script + command line
- Test data: `.bin`, `.machine`, sample mapping tables

---

## Project Structure

Decode32/
├── example.bin                 # Sample binary input file
├── test1.txt.machine           # Additional test input
├── instructions.txt            # Mapping from binary encoding → assembly (examples)
├── authors.txt                 # Team members (Blake Nelson, Grant Smith)
├── HW_2.java                   # Main decoding logic
├── HW_2_tctfilereader.java     # Reads the binary file & buffers data
├── float_bits_s2024.java       # Utility for special bit conversions (e.g. floats)
├── run.sh                      # Script: compile + run on test files

---

## Usage / Run Instructions

1. Clone the repo  
   git clone https://github.com/GrantSmith135/Decode32.git  
   cd Decode32

2. Compile  
   javac *.java

3. Run  
   ./run.sh example.bin

(You can replace `example.bin` with any other `.bin` or `.machine` test file.)

---

## My Contributions

- Wrote the core decoding logic (`HW_2.java`) to interpret 32-bit binary words into assembly instructions
- Built the file reader (`HW_2_tctfilereader.java`) including bit-level parsing
- Created test cases and sample binary files to verify correctness
- Utility functions, author metadata, and `float_bits_s2024.java` for handling special formats

---

## Example Output

10010011 01010010 00010010 10001010 → ADD R1, R2, R3
…

(Add actual sample outputs here if available.)

---

## Possible Extensions

- Support additional instruction sets / ISAs
- Include error detection (invalid opcodes)
- Add GUI front-end or web viewer for outputs
- Turn into a library with bindings for other languages
