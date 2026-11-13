# Highly Available Notes App on AWS

Multi-user notes application (Spring Boot) deployed on AWS behind a load balancer with auto scaling. Users sign up, log in, and save, edit, or append their own notes.

**Skills:** EC2 · ALB · Target Group · Custom AMI · Auto Scaling · Nginx · MongoDB · Spring Boot

## Architecture

```
Internet → ALB → Target Group → App Servers (Nginx → Spring Boot) → MongoDB (EC2)
                                 └─ Auto Scaling Group (custom AMI)
```

> 📷 Add architecture diagram here

| Component | Details |
|-----------|---------|
| Load Balancer | ALB distributes traffic across app servers |
| App Servers | 2 × EC2, Nginx reverse proxy → Spring Boot |
| Auto Scaling | Launch Template from custom AMI keeps instances available |
| Database | 1 × EC2 with MongoDB (`mongod` installed manually) |

## Features

- User registration and login
- Personal notepad: save, view, edit, append
- Each user sees only their own notes

## Setup Summary

1. Launched a DB server and installed `mongod`.
2. Launched 2 app servers running Spring Boot, with Nginx proxying port 80 to the app.
3. Opened HTTP (port 80) in the app security group.
4. Created a Target Group and ALB, and registered both app servers.
5. Created a custom AMI of the app server and used it in a Launch Template and Auto Scaling Group.

## Security

- Security groups allow HTTP (80) to the app servers.
- MongoDB (27017) is reachable only from the app servers.
- Passwords are stored hashed.

## Testing

| Test | Result |
|------|--------|
| Open ALB DNS name | App loads |
| Target Group health | Both targets healthy 📷 |
| Terminate one instance | ASG launches a replacement 📷 |
