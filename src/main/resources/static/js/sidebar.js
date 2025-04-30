populateMenus();

async function toggleSubmenu(id) {
    const el = document.getElementById(id);
    el.classList.toggle("show");
}
//
//
//async function populateMenus() {
//    const url = "http://localhost:8081/analytics/jobType";
//    try {
//        const response = await fetch(url);
//         if (!response.ok) {
//             throw new Error(`Response status: ${response.status}`);
//         }
//
//          let menus = "";
//          const json = await response.json();
//          console.log("helllo" + json);
//           json.data.forEach(element => {
//                   menus += `
//                     <div class="menu-header" onclick="toggleSubmenu('settingsSubmenu')">
//                         <i class="bi bi-code-square" style="padding-left:10px"></i> <a href="/settings/users">`+element+`</a> <i class="bi bi-chevron-down" style="padding-right:10px"></i>
//                     </div>
//                     <ul class="submenu list-unstyled" id="settingsSubmenu"></ul>
//                     `;
//          });
//            console.log("dynamicMenus");
//            document.getElementById("dynamicMenus").innerHTML = menus;
//
////
////
////        const data = await response.json();
////        let listOfMenus = data['data'];
////        let menus = "";
////
////        listOfMenus.forEach(element => {
////            menus += `
////        <div class="menu-header" onclick="toggleSubmenu('settingsSubmenu')">
////            <i class="bi bi-code-square" style="padding-left:10px"></i> <a href="/settings/users">Api
////                Services</a> <i class="bi bi-chevron-down" style="padding-right:10px"></i>
////        </div>
////        <ul class="submenu list-unstyled" id="settingsSubmenu">
////        `;
////            let subMenus = element['subMenus'];
////            subMenus.forEach(subMenu => {
////                let indicator = subMenu.status == "OK" ? "success" : "failed"
////                menus+= `
////                <div class="menu-header">
////                    <a href="${subMenu.route}"><i class="bi status-indicator ${indicator}"></i>${subMenu.title}</a>
////                </div>
////                `;
////            });
////            menus += `</ul>`;
////        });
////        console.log(menus);
////        document.getElementById("dynamicMenus").innerHTML = menus;
////        console.log(listOfMenus);
//    } catch (error) {
//        console.error(error.message);
//    }
//}
