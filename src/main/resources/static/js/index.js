function loadSettingsModal() {
  fetch('/settings/modal')
    .then(response => response.text())
    .then(html => {
      document.getElementById('settingsModalContainer').innerHTML = html;
      const modal = new bootstrap.Modal(document.getElementById('settingsModal'));
      modal.show();
    })
    .catch(error => {
      console.error('Error loading settings modal:', error);
    });
}