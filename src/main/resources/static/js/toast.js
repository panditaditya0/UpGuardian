 function showToast(message, toastType) {
      const toastId = `toast-${Date.now()}`;
      const toastHTML = `
        <div id="${toastId}" class="toast align-items-center text-white bg-`+toastType+` border-0 mb-2" role="alert" aria-live="assertive" aria-atomic="true">
          <div class="d-flex">
            <div class="toast-body">${message}</div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
          </div>
        </div>
      `;

      const toastContainer = document.getElementById('toastContainer');
      toastContainer.insertAdjacentHTML('beforeend', toastHTML);

      const toastEl = document.getElementById(toastId);
      const toast = new bootstrap.Toast(toastEl, { delay: 5000 });
      toast.show();

      toastEl.addEventListener('hidden.bs.toast', () => toastEl.remove());
    }