document.addEventListener('change', function(event) {
    if (event.target.type === 'file' && event.target.files[0].type === 'application/pdf') {
        const file = event.target.files[0];
        
        // Read the file content
        const reader = new FileReader();
        
        reader.onload = () => {
            // Ensure the result is defined
            if (reader.result) {
                const arrayBuffer = reader.result; // The file content as ArrayBuffer
                // Convert ArrayBuffer to Base64
                const base64String = arrayBufferToBase64(arrayBuffer);

                // Send the content to the background script
                chrome.runtime.sendMessage({ action: 'inspectPDF', fileContent: base64String });
            } else {
                console.error("FileReader result is undefined.");
            }
        };
        
        reader.onerror = () => {
            console.error("Error reading file:", reader.error);
        };

        reader.readAsArrayBuffer(file); // Read the file as an ArrayBuffer
    }
});

// Helper function to convert ArrayBuffer to Base64
function arrayBufferToBase64(buffer) {
    let binary = '';
    const bytes = new Uint8Array(buffer);
    const len = bytes.byteLength;
    for (let i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[i]);
    }
    return btoa(binary); // Convert binary string to Base64
}
