//TODO: ЗАМЕНИТЬ
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { mockApi } from '../services/mockApi';

export default function DataTablesPage() {
    const [tables, setTables] = useState([]);
    const [newTableName, setNewTableName] = useState('');

    useEffect(() => {
        loadTables();
    }, []);

    const loadTables = async () => {
        const data = await mockApi.getDataTables();
        setTables(data);
    };

    const handleCreate = async () => {
        if (!newTableName) return;
        await mockApi.createDataTable({ name: newTableName, description: 'Новая таблица' });
        setNewTableName('');
        loadTables();
    };

    return (
        <div className="container">
            <h1>Списки и Таблицы</h1>

            <div className="card" style={{ marginBottom: 20 }}>
                <h3>Создать новую таблицу</h3>
                <div style={{ display: 'flex', gap: 10 }}>
                    <input
                        className="form-input"
                        placeholder="Название таблицы"
                        value={newTableName}
                        onChange={e => setNewTableName(e.target.value)}
                    />
                    <button className="btn" onClick={handleCreate}>Создать</button>
                </div>
            </div>

            <div className="grid">
                {tables.map(table => (
                    <div key={table.id} className="card">
                        <h3>{table.name}</h3>
                        <p>{table.description}</p>
                        <Link to={`/datatables/${table.id}`} className="btn" style={{ display: 'inline-block', textAlign: 'center', textDecoration: 'none' }}>
                            Открыть
                        </Link>
                    </div>
                ))}
            </div>
        </div>
    );
}