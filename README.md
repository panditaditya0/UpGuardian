# UpGuardian

A robust Spring Boot-based monitoring solution to track:

- ✅ REST API health & response times  
- 📡 Server uptime (ping/heartbeat)  
- 🔐 SSL certificate validity (for domains)  
- ⏱️ Monitoring frequency: Every 1 minute

---

## 📌 Features

- **API Monitoring**  
  - Checks response status, time, and data integrity  
  - Supports multiple HTTP methods (GET, POST, etc.)

- **Heartbeat Monitoring**  
  - Pings servers/microservices every minute  
  - Displays real-time status in the dashboard

- **SSL Certificate Monitoring**  
  - Validates SSL certificates for expiration & security  
  - Sends alerts for soon-to-expire domains

- **Dashboard with Charts and Status Cards**  
  - Built using HTML, Bootstrap, and Thymeleaf  
  - Supports dark mode  
  - Includes metrics like average response time, total failures, uptime %

- **Notification System**  
  - Toast alerts for real-time feedback  
  - Email or webhook integration ready (optional)

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Spring Boot, Java 17 |
| Frontend | HTML5, Bootstrap 5, Thymeleaf |
| Scheduler | Spring Task Scheduler (1-min interval) |
| Database | H2 / PostgreSQL / MySQL |
| Charts | Chart.js (for visualizing heartbeat & uptime data) |

---

## 🚀 Getting Started

### 1. Clone the repo
```bash
git clone https://github.com/your-username/api-uptime-monitor.git
cd api-uptime-monitor
