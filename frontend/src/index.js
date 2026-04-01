import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
// Если ты не создавал index.css, убери эту строку,
// но лучше, чтобы стили из styles/App.css были подключены в App.js
// import './index.css';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <App />
    </React.StrictMode>
);