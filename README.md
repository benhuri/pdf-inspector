# PDF Inspector

This repository contains a **PDF Inspector** Chrome extension and its corresponding backend service. The extension allows users to select a PDF file, which is then inspected by a backend service for content analysis.

## Project Structure

- **pdf-inspector-backend**: Backend service built with Spring Boot to inspect PDF contents.
- **pdf-inspector-extension**: Chrome extension to allow users to upload PDFs for inspection.

## Prerequisites

Ensure the following dependencies and tools are installed on your system:

- **Java 17** (for backend)
- **Maven** (for building and running the backend service)
- **Chrome Browser** (for installing the extension)
- **Git** (optional for cloning the repository)

## Getting Started

### 1. Cloning the Repository

Clone the repository to your local machine:

```bash
git clone https://github.com/benhuri/pdf-inspector.git
cd pdf-inspector
```

## 2. Backend Service Setup

### Step 1: Install Dependencies
Ensure you have **Java 17** installed. You can verify this by running:

```bash
java -version
```
Next, navigate to the pdf-inspector-backend directory and install the dependencies using Maven:

```bash
cd pdf-inspector-backend
mvn clean install
```

### Step 2: Running the Backend Service
Once dependencies are installed, you can run the Spring Boot backend service:

```bash
mvn spring-boot:run
```

The backend will be running at http://localhost:8080.
