chrome.runtime.onMessage.addListener((message, sender, sendResponse) => {
    if (message.action === 'inspectPDF') {
        inspectFile(message.fileContent);
    }
  });
  
  function inspectFile(fileContent) {
    fetch('http://localhost:8080/api/upload', {  // Point to my backend service
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ fileContent: fileContent })  // Send the file content to my backend
    })
    .then(response => {
          if (!response.ok) {
              throw new Error('Network response was not ok: ' + response.statusText);
          }
          return response.text();
        })
        .then(data => {
          console.log('Inspection result from backend:', data);
        })
        .catch(error => console.error('Error inspecting file via backend:', error));
  }
  