async function fetchApi() {
    const response = await fetch('http://localhost:8080/fetchApi');
    const data = await response.json();
    alert(data);
}

async function getCvesByYear() {
    const year = prompt('Enter year:');
    const response = await fetch(`http://localhost:8080/getCvesByYear/${year}`);
    const data = await response.json();
    updateTable(data);
}

async function getCvesScoreAbove() {
    const score = prompt('Enter The Score:');
    const response = await fetch(`http://localhost:8080/getCvesScoreAbove/${score}`);
    const data = await response.json();
    updateTable(data);
}

async function getCvesModifiedDaysAgo() {
    const days = prompt('Modified Before(Days):');
    const response = await fetch(`http://localhost:8080/getCvesModifiedDaysAgo/${days}`);
    const data = await response.json();
    updateTable(data);
}

function updateTable(data) {
    const tableBody = document.getElementById('dataTable').getElementsByTagName('tbody')[0];
    tableBody.innerHTML = '';

    const pageData = data;
    pageData.forEach(function (cve) {
        const row = tableBody.insertRow();
        row.insertCell(0).innerHTML = cve.cveID || 'N/A';
        row.insertCell(1).textContent = cve.sourceIdentifier || 'N/A';
        row.insertCell(2).textContent = cve.published || 'N/A';
        row.insertCell(3).textContent = cve.lastModified || 'N/A';
        row.insertCell(4).textContent = cve.vulnStatus || 'N/A';
        row.insertCell(5).textContent = cve.baseScore || 'N/A';
    });
}

async function updateDescTable(cseId) {
    const response = await fetch(`http://localhost:8080/getCveDesc/${cseId}`);
    const data = await response.json();

    const tableBody = document.getElementById('descTable').getElementsByTagName('tbody')[0];
    tableBody.innerHTML = '';

    const pageData = data;
    window.location = "description.html"
    pageData.forEach(function (desc) {
        const row = tableBody.insertRow();
        row.insertCell(0).textContent = cve.cveID || 'N/A';
        row.insertCell(1).textContent = cve.sourceIdentifier || 'N/A';
        row.insertCell(2).textContent = cve.published || 'N/A';
    });
}

