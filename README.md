# Advent of Code

This repository contains my solutions for the [Advent of Code](https://adventofcode.com/) programming puzzles. The
Advent of Code is an annual event that takes place every December, where a new programming puzzle is released each day
leading up to Christmas.

## Prerequisites

To run the code in this repository, you'll need to have the following installed on your machine:

- Java JDK (version 17 or later)
- Maven (version 3.6 or later)

## Setup

1. Clone the repository to your local machine:

```bash
git clone https://github.com/alglus/advent-of-code.git
```

2. Navigate to the project root directory:

```bash
cd advent-of-code
```

3. Build the project using Maven:

```bash
mvn clean install
```

## Usage

To run the code for a specific day, use the following command:

```bash
mvn exec:java -Dexec.mainClass=aoc<year>.Day<day>
```

- `<year>`: the year of the Advent of Code (e.g., 2022)
- `<day>`: the day of the puzzle, with single digits preceded by a zero (e.g., 01)

For example, to run the code for Day 1 of the 2022 event, use the following command:

```bash
mvn exec:java -Dexec.mainClass=aoc2022.Day01
```

The output of the program will be printed to the console.

## Testing

To run all tests, use the following command:

```bash
mvn test
```

If you would like to run the tests of a specific day, run:

```bash
mvn -Dtest=aoc<year>.Day<day>Tests test
```

So, for example, to run the tests of Day 2 of the 2022 event, run the following:

```bash
mvn -Dtest=aoc2022.Day02Test test
```

## License

This project is licensed under the GPL-3.0 License - see
the [LICENSE](https://github.com/alglus/advent-of-code/blob/main/LICENSE) file for details.