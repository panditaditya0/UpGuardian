        const hamburger = document.getElementById('hamburger');
        const sidebar = document.getElementById('sidebar');
        const overlay = document.getElementById('overlay');

        hamburger.addEventListener('click', () => {
            sidebar.classList.toggle('open');
            overlay.classList.toggle('active');
            hamburger.classList.toggle('active');
        });

        overlay.addEventListener('click', () => {
            sidebar.classList.remove('open');
            overlay.classList.remove('active');
            hamburger.classList.remove('active');
        });


        populateSidebar();

        async function populateSidebar() {
                    const response = await fetch("http://localhost:8081/analytics/card-status");
                    try {
                        if (!response.ok) {
                            throw new Error(`Response status: ${response.status}`);
                        }
                        const json = await response.json();
                        let data = json.data;
                        let outerDashboardCardHtml = '';
                        json.data.forEach(element => {

                            let menus = `
                             <div class="menu-header" onclick="toggleSubmenu('settingsSubmenu')">
                                 <i class="`+ element.jobIcon + `" style="padding-left:10px"></i> <a href="/settings/users">` + element.jobTypeName + `</a> <i class="bi bi-chevron-down" style="padding-right:10px"></i>
                             </div>
                             <ul class="submenu list-unstyled" id="settingsSubmenu">
                             `;
                            document.getElementById("dynamicMenus").innerHTML += menus;
                        });
                    } catch (error) {
                        console.error(error.message);
                    }
                }