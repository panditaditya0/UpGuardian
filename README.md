# 🤖 UpGuardian: AI-First Uptime & API Monitoring

# 🛡️ UpGuardian

> A real-time AI-augmented monitoring system built with Spring Boot to ensure your APIs, services, and infrastructure are always up, secure, and responsive.

---

## 🚀 Overview

**UpGuardian** is a powerful uptime and performance monitoring tool for modern systems. It combines **real-time monitoring** with **AI-based predictive failure detection**, giving you early alerts and actionable insights for:

- ✅ **REST API Health & Response Times**
- 📡 **Server Uptime via Heartbeats**
- 🔐 **SSL Certificate Expiry Tracking**
- 🧾 **Invoice Payment Alerts**
- 🐳 **Dockerized Microservices Monitoring**
- 🤖 **AI-Powered Failure Prediction**
- ⏱️ **Polls Automatically Every defined time**

---

## 📊 Key Features

### 🔍 API Monitoring
- Tracks HTTP status, latency, and response body
- Supports all HTTP methods
- Automatically retries failed requests
- Summarized in an intuitive dashboard with filters

### 🖥️ Server Heartbeat (Uptime)
- Pings services and logs real-time availability
- Displays uptime %, last seen, and failure streaks
- Notifies when service is unreachable for configured intervals

### 🔒 SSL Certificate Monitoring
- Checks SSL expiration dates for domains
- Sends alerts for certificates nearing expiry
- Prevents potential downtime due to invalid certificates

### 🧠 AI-Powered Failure Prediction
- Learns from historical trends and errors
- Predicts API/service failures using lightweight ML models
- Flags anomalies even before errors occur

### 🧾 Invoice Payment Alerts
- Tracks recurring invoice due dates and payment statuses
- Sends reminders or alerts when payment is pending
- Integrates with billing APIs or internal tools

### 🐳 Dockerized Service Monitoring
- Monitors container health via Docker APIs
- Detects restart loops, failures, and resource usage spikes
- Visualizes service health on dashboard

---

## 🖥️ Live Dashboard

> Clean. Responsive. Informative.

- 📈 Uptime trend & API latency charts (via Chart.js)
- 🌙 Dark mode toggle
- ✅ Toast notifications for quick insights
- 📂 Breadcrumb navigation: `Home > Dashboard > [Service]`
- 📅 Date filters for viewing historical data

---

## ⚙️ Tech Stack

| Layer      | Technology                  |
|------------|-----------------------------|
| Backend    | Spring Boot, Java 17        |
| Frontend   | Thymeleaf, Bootstrap 5, JS  |
| Charts     | Chart.js                    |
| Scheduler  | Spring Task Scheduler       |
| DB         | PostgreSQL / MySQL / H2     |
| Container  | Docker (for service monitors) |
| AI Engine  | Embedded ML via Java/Python (Pluggable) |

---

## 🧠 Why UpGuardian?

**Downtime costs money.**  
UpGuardian doesn’t just report incidents — it helps **prevent them**.

- 🚨 **AI-Powered Failure Prediction**  
  Learns from past downtimes and usage trends to detect anomalies *before they turn into failures*.

- 📡 **Real-Time Monitoring (Every 1 min)**  
  Whether it's a broken API, an unreachable Docker container, or a soon-to-expire certificate — UpGuardian has your back.

- 📊 **Clean Analytics Dashboard**  
  Visualizes health trends, average latencies, error spikes, and more — with built-in dark mode.

---

## ⚡ Features at a Glance

| Feature                             | Description                                                                 |
|-------------------------------------|-----------------------------------------------------------------------------|
| 🤖 **AI-Based Failure Prediction**   | Learns from historical behavior to flag risk patterns                       |
| ✅ **REST API Health Monitoring**    | Tracks uptime, response times, and errors                                   |
| 📡 **Server Uptime (Heartbeats)**    | Sends heartbeat checks every minute to ensure services are alive            |
| 🔐 **SSL Certificate Validation**    | Monitors domain certificates and alerts before expiry                       |
| 🧾 **Invoice Payment Alerts**        | Tracks invoice due dates & sends smart alerts                               |
| 🐳 **Docker Container Monitoring**   | Observes container health & resource metrics via Docker API                 |
| 🌙 **Dark Mode UI + Responsive**     | Modern dashboard with charts, filters, and toast alerts                     |

---

## 📷 Dashboard Preview

> Dark Mode · Real-time Charts · Toast Alerts · Status Cards

<img src="https://your-screenshot-url.com/dashboard.png" alt="Dashboard Preview" width="100%"/>

---

## 🚀 Getting Started

```bash
# Clone and navigate
git clone https://github.com/your-username/upguardian.git
cd upguardian

# Run
./mvnw spring-boot:run
