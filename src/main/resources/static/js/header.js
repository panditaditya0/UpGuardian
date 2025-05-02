        const toggle = document.getElementById('darkModeToggle');
        const headerLogo = document.getElementById('headerLogo');

        toggle.addEventListener('change', () => {
            const html = document.documentElement;
            html.setAttribute('data-bs-theme', toggle.checked ? 'dark' : 'light');
            if (document.body.classList.contains('dark-mode')) {
                headerLogo.src = '/images/litemode_logo.png';
              } else {
                headerLogo.src = '/images/shield_logo.png';

              }
        });
        document.getElementById('darkModeToggle').addEventListener('change', function () {
            document.body.classList.toggle('dark-mode', this.checked);
        });