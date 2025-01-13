## Frontend Application

The frontend application provides a user-friendly interface for interacting with the banking services.

### Features
- Create new accounts with initial credit
- View account information including balance
- Display transaction history
- Simple and intuitive user interface

### Prerequisites
- Node.js
- NPM
- The backend services should be running (account, transaction, and user services)

### Running the Frontend

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

The application will be available at `http://localhost:3000`

### Using the Frontend Application

1. Creating a New Account:
   - Enter the Customer ID in the input field
   - Specify the Initial Credit amount (if desired)
   - Click "Create Account" button

2. Viewing Account Information:
   - The account details will automatically display after creation
   - You can see:
     - Customer Name
     - Account Balance
     - Transaction History

### Project Structure
```
frontend/
├── src/
│   ├── components/
│   │   ├── AccountCreation.jsx
│   │   ├── AccountInfo.jsx
│   │   └── TransactionList.jsx
│   ├── services/
│   │   ├── accountService.js
│   │   ├── transactionService.js
│   │   └── userService.js
│   └── App.jsx
├── package.json
└── vite.config.js
```

### Docker Support

The frontend is included in the Docker setup. To run everything together:

```bash
docker-compose up --build
```

This will start both the backend services and the frontend application. The frontend will be available at `http://localhost:3000`.

### Development

For local development:
1. Start the backend services first (as described in the main README)
2. Start the frontend in development mode:
```bash
cd frontend
npm run dev
```

### API Integration

The frontend integrates with the following backend endpoints:
- Create Account: POST http://localhost:8070/api/accounts
- Get User Info: GET http://localhost:8072/api/users/{id}/account-info

### Troubleshooting

Common issues:
1. If the frontend can't connect to backend services:
   - Ensure all backend services are running
   - Check the console for any CORS errors
   - Verify the API endpoints in the service files

2. If the page doesn't load:
   - Clear browser cache
   - Check browser console for errors
   - Verify Node.js version