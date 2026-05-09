# Frontend - Salary Application

A modern React TypeScript frontend for the university salary calculation system.

## Features

- **Authentication & Authorization**: Login, registration, token-based auth, role-based access control
- **Table Management**: Create, edit, and manage pedagogical worker tables with dynamic columns and rows
- **Coefficients Management**: CRUD operations for salary calculation coefficients
- **Admin Panel**: User management, role assignment, account activation
- **Responsive UI**: Clean Material-UI design with university styling
- **Real-time Updates**: Toast notifications and loading states

## Tech Stack

- **React 18** with TypeScript
- **Material-UI** for components
- **React Router** for navigation
- **Axios** for API calls
- **React Toastify** for notifications

## Getting Started

### Prerequisites

- Node.js 16+
- npm or yarn
- Backend API running on `http://localhost:8080`

### Installation

1. Install dependencies:
```bash
npm install
```

2. Start the development server:
```bash
npm start
```

The application will be available at `http://localhost:3000`

### Build for Production

```bash
npm run build
```

## Project Structure

```
src/
├── components/          # Reusable UI components
│   ├── Layout.tsx      # Layout wrapper
│   ├── Navbar.tsx      # Navigation bar
│   └── ProtectedRoute.tsx # Route protection
├── context/            # React context providers
│   └── AuthContext.tsx # Authentication state
├── pages/              # Page components
│   ├── LoginPage.tsx
│   ├── RegisterPage.tsx
│   ├── DashboardPage.tsx
│   ├── DataTablesPage.tsx
│   ├── TableEditorPage.tsx
│   ├── CoefficientsPage.tsx
│   ├── UserManagementPage.tsx
│   ├── ProfilePage.tsx
│   └── AdminPage.tsx
├── services/           # API services
│   └── api.ts          # Axios-based API client
├── types/              # TypeScript type definitions
│   └── index.ts        # All type definitions
└── styles/             # Global styles
```

## API Integration

The frontend integrates with the existing backend API:

- **Base URL**: `http://localhost:8080/api`
- **Authentication**: Bearer token in Authorization header
- **Endpoints**: Auth, Users, DataTables, Columns, Rows, Cells, Coefficients, Rules

## User Roles

- **USER**: Basic user, needs activation to access tables
- **SUPERUSER**: Administrator with full access including user management

## Key Features Implementation

### Authentication Flow
- Login/Register forms with validation
- JWT token storage and automatic header injection
- Route guards for protected pages
- Automatic logout on token expiration

### Table Editor
- Dynamic column creation and editing
- Row management with inline cell editing
- Support for different data types (text, number, date, boolean)
- Real-time saving to backend

### Role-Based Access
- Route-level protection
- UI elements visibility based on roles
- Activation status checks

### Error Handling
- Global error interceptors
- User-friendly error messages
- Loading states and skeletons

## Development

### Adding New Pages

1. Create component in `src/pages/`
2. Add route in `App.tsx`
3. Update navigation in `Navbar.tsx` if needed

### API Integration

1. Define types in `src/types/index.ts`
2. Add API methods in `src/services/api.ts`
3. Use in components with proper error handling

### Styling

Uses Material-UI theme system. Customize theme in `App.tsx`.

## Deployment

Build the application and serve the `build` folder with any static server.

For production deployment with backend, update `REACT_APP_API_URL` environment variable.

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can't go back!**

If you aren't satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you're on your own.

You don't have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn't feel obligated to use this feature. However we understand that this tool wouldn't be useful if you couldn't customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

### Code Splitting

This section has moved here: [https://facebook.github.io/create-react-app/docs/code-splitting](https://facebook.github.io/create-react-app/docs/code-splitting)

### Analyzing the Bundle Size

This section has moved here: [https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size](https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size)

### Making a Progressive Web App

This section has moved here: [https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app](https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app)

### Advanced Configuration

This section has moved here: [https://facebook.github.io/create-react-app/docs/advanced-configuration](https://facebook.github.io/create-react-app/docs/advanced-configuration)

### Deployment

This section has moved here: [https://facebook.github.io/create-react-app/docs/deployment](https://facebook.github.io/create-react-app/docs/deployment)

### `npm run build` fails to minify

This section has moved here: [https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify](https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify)
