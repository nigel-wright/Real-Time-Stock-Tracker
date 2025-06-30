# Stock Tracker Backend

## Prerequisites
- Java 21
- Maven
- PostgreSQL database

## Setup
1. Set up environment variables (create `.env` file or set system variables):
   ```
   SERVER_PORT=3000
   DB_URL=jdbc:postgresql://localhost:5432/stockproject
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   JWT_SECRET=your_secure_jwt_secret_key
   ```

2. Make sure your PostgreSQL database is running

## Run
```bash
mvn spring-boot:run
```

The backend will start on port 3000 (or your configured port).

## API Endpoints
- `POST /users/sign-up` - User registration
- `POST /login/sign-in` - User authentication
- `POST /login/change-password` - Change password 