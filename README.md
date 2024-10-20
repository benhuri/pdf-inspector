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

## 1. Cloning the Repository

Clone the repository to your local machine:

```bash
git clone https://github.com/benhuri/pdf-inspector.git
cd pdf-inspector
```
---

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

### Step 3: API Endpoint
The backend provides the following API endpoint:

- **POST** `/api/upload`: Receives a PDF file's Base64 content and inspects it.
---

## 3. Chrome Extension Setup

### Step 1: Load the Extension
1. Open Google Chrome and navigate to `chrome://extensions/`.
2. Enable **Developer Mode** (top-right corner).
3. Click on **Load Unpacked** and select the `pdf-inspector-extension` directory from the cloned repository.
4. The extension should now appear in your Chrome toolbar.

### Step 2: Using the Extension
1. Go to ChatGPT or any other website that support PDF attaching, and select a PDF file.
---

## 4. Test Webpage

I've added a test webpage where you can choose a PDF file to see the inspection results. This allows you to try out the **PDF Inspector** extension and verify how the backend processes PDF files.

You can access the test webpage and upload a PDF to inspect its content.
---

## 5. Inspecting Logs

- **Backend Logs:** Check `logs/pdfInspector.log` for detailed logs, or view them in the console where you started the service.
  - If the PDF inspection found secrets or flagged content, search the log for `"File inspection didn't pass"`.
  - If the PDF passed the inspection, search for `"File inspection passed successfully!"`.
  
- **Extension Logs:** Open the browser's Developer Tools (F12 or `Ctrl+Shift+I`), go to the Console tab, and view logs related to the PDF content and inspection results.
---

## Dependencies

### Backend
- **Spring Boot** 3.1.4
- **Apache PDFBox** 2.0.24
- **Log4j2** for logging
- **JSON Library** for processing API responses

### Extension
- **Chrome Manifest Version 3**
- Uses **JavaScript** for file reading, Base64 conversion, and communication with the backend.

