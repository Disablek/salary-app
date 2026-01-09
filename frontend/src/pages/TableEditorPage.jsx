//TODO: ЗАМЕНИТЬ
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { mockApi } from '../services/mockApi';

// Простой компонент Модального окна (можно вынести в отдельный файл)
const Modal = ({ title, onClose, children }) => (
    <div className="modal-overlay" onClick={onClose}>
        <div className="modal-content" onClick={e => e.stopPropagation()}>
            <div className="modal-header">
                <h3 className="modal-title">{title}</h3>
                <button className="close-btn" onClick={onClose}>&times;</button>
            </div>
            {children}
        </div>
    </div>
);

export default function TableEditorPage() {
    const { id } = useParams();
    const [table, setTable] = useState(null);
    const [columns, setColumns] = useState([]);
    const [rows, setRows] = useState([]);
    const [cells, setCells] = useState([]);
    const [page, setPage] = useState(1);

    // Состояния для модального окна создания колонки
    const [showColModal, setShowColModal] = useState(false);
    const [newColData, setNewColData] = useState({
        title: '',
        key: '',
        activeInPage: '0', // Строка для select, потом парсим
        dataType: 'STRING'
    });

    useEffect(() => {
        loadData();
    }, [id, page]);

    const loadData = async () => {
        const t = await mockApi.getDataTableById(id);
        setTable(t);
        const cols = await mockApi.getColumns(id, page);
        setColumns(cols);
        const r = await mockApi.getRows(id);
        setRows(r);
        const c = await mockApi.getCells(id, page);
        setCells(c);
    };

    const getCellValue = (rowId, colId) => {
        const cell = cells.find(c => c.row_id === rowId && c.column_id === colId);
        return cell ? cell.value : '';
    };

    const handleCellChange = async (rowId, colId, value) => {
        const existingCell = cells.find(c => c.row_id === rowId && c.column_id === colId);
        const dto = {
            id: existingCell?.id,
            row_id: rowId,
            column_id: colId,
            value: value
        };
        await mockApi.saveCell(dto);
        // Локальное обновление для быстродействия
        setCells(prev => {
            const idx = prev.findIndex(c => c.row_id === rowId && c.column_id === colId);
            if (idx !== -1) {
                const copy = [...prev];
                copy[idx] = { ...copy[idx], value };
                return copy;
            }
            return [...prev, { ...dto, id: 'temp' }]; // В реале id придет с бэка
        });
    };

    const handleCreateColumn = async (e) => {
        e.preventDefault();
        if(!newColData.title) return;

        // Генерируем ключ из названия если он пуст
        const key = newColData.key || newColData.title.toLowerCase().replace(/\s+/g, '_');

        await mockApi.createColumn({
            dataTable_id: id,
            title: newColData.title,
            key: key,
            activeInPage: parseInt(newColData.activeInPage),
            dataType: newColData.dataType
        });

        setShowColModal(false);
        setNewColData({ title: '', key: '', activeInPage: '0', dataType: 'STRING' });
        loadData();
    };

    const addRow = async () => {
        await mockApi.createRow({ tableId: id, employeeId: null });
        loadData();
    };

    if (!table) return <div className="container">Загрузка...</div>;

    return (
        <div className="container" style={{ maxWidth: '100%' }}>

            {/* Шапка с названием и переключателем страниц */}
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 20 }}>
                <h1 style={{ margin: 0 }}>{table.name}</h1>
                <div style={{ display: 'flex', gap: 10, alignItems: 'center' }}>
                    <span style={{ fontWeight: 500, color: '#666' }}>Страница:</span>
                    <button
                        className={`btn ${page === 1 ? '' : 'btn-danger'}`}
                        style={{ background: page === 1 ? 'var(--primary)' : '#e5e7eb', color: page === 1 ? 'white' : 'black', width: 'auto' }}
                        onClick={() => setPage(1)}
                    >1</button>
                    <button
                        className={`btn ${page === 2 ? '' : 'btn-danger'}`}
                        style={{ background: page === 2 ? 'var(--primary)' : '#e5e7eb', color: page === 2 ? 'white' : 'black', width: 'auto' }}
                        onClick={() => setPage(2)}
                    >2</button>
                </div>
            </div>

            {/* Панель инструментов */}
            <div className="card" style={{ padding: '15px 20px', marginBottom: 20, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                    <button className="btn" style={{ width: 'auto', display: 'flex', alignItems: 'center', gap: 5 }} onClick={() => setShowColModal(true)}>
                        <span style={{ fontSize: '1.2rem', lineHeight: 1 }}>+</span> Добавить колонку
                    </button>
                </div>
                <div>
                    <button className="btn" style={{ width: 'auto', background: '#10b981' }} onClick={addRow}>
                        + Добавить сотрудника
                    </button>
                </div>
            </div>

            {/* Модальное окно создания колонки */}
            {showColModal && (
                <Modal title="Новая колонка" onClose={() => setShowColModal(false)}>
                    <form onSubmit={handleCreateColumn}>
                        <div className="form-group">
                            <label>Название (Заголовок)</label>
                            <input
                                className="form-input"
                                placeholder="Например: Оклад"
                                value={newColData.title}
                                onChange={e => setNewColData({...newColData, title: e.target.value})}
                                autoFocus
                                required
                            />
                        </div>

                        <div className="form-group">
                            <label>Системный ключ (необязательно)</label>
                            <input
                                className="form-input"
                                placeholder="salary_base"
                                value={newColData.key}
                                onChange={e => setNewColData({...newColData, key: e.target.value})}
                            />
                            <small style={{ color: '#666' }}>Если пусто, сгенерируется из названия</small>
                        </div>

                        <div className="form-row">
                            <div className="form-col">
                                <div className="form-group">
                                    <label>Тип данных</label>
                                    <select
                                        className="select-input"
                                        value={newColData.dataType}
                                        onChange={e => setNewColData({...newColData, dataType: e.target.value})}
                                    >
                                        <option value="STRING">Текст</option>
                                        <option value="NUMBER">Число</option>
                                        <option value="DATE">Дата</option>
                                        <option value="BOOLEAN">Да/Нет</option>
                                    </select>
                                </div>
                            </div>
                            <div className="form-col">
                                <div className="form-group">
                                    <label>Отображение</label>
                                    <select
                                        className="select-input"
                                        value={newColData.activeInPage}
                                        onChange={e => setNewColData({...newColData, activeInPage: e.target.value})}
                                    >
                                        <option value="0">На всех страницах</option>
                                        <option value="1">Только Страница 1</option>
                                        <option value="2">Только Страница 2</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div style={{ marginTop: 20 }}>
                            <button type="submit" className="btn">Создать</button>
                        </div>
                    </form>
                </Modal>
            )}

            {/* Таблица */}
            <div style={{ overflowX: 'auto', border: '1px solid #e5e7eb', borderRadius: 8 }}>
                <table style={{ margin: 0 }}>
                    <thead>
                    <tr>
                        <th style={{ minWidth: 200, position: 'sticky', left: 0, background: '#f9fafb', zIndex: 2, borderRight: '1px solid #e5e7eb' }}>
                            Сотрудник
                        </th>
                        {columns.map(col => (
                            <th key={col.id} style={{ minWidth: 150, borderRight: '1px solid #eee' }}>
                                <div style={{ display: 'flex', flexDirection: 'column' }}>
                                    <span>{col.title}</span>
                                    <span style={{
                                        fontSize: '0.65rem',
                                        textTransform: 'uppercase',
                                        background: '#e0e7ff',
                                        color: '#4338ca',
                                        padding: '2px 4px',
                                        borderRadius: 4,
                                        alignSelf: 'flex-start',
                                        marginTop: 4
                                    }}>
                                    {col.dataType}
                                </span>
                                </div>
                            </th>
                        ))}
                    </tr>
                    </thead>
                    <tbody>
                    {rows.length === 0 ? (
                        <tr>
                            <td colSpan={columns.length + 1} style={{ textAlign: 'center', padding: 30, color: '#666' }}>
                                В таблице пока нет данных. Добавьте сотрудника.
                            </td>
                        </tr>
                    ) : (
                        rows.map((row, idx) => (
                            <tr key={row.id}>
                                <td style={{ position: 'sticky', left: 0, background: '#fff', fontWeight: 600, borderRight: '1px solid #e5e7eb', boxShadow: '2px 0 5px -2px rgba(0,0,0,0.05)' }}>
                                    {idx + 1}
                                </td>
                                {columns.map(col => (
                                    <td key={col.id} style={{ padding: 0, borderRight: '1px solid #eee' }}>
                                        <input
                                            style={{
                                                width: '100%',
                                                border: 'none',
                                                padding: '12px 15px',
                                                outline: 'none',
                                                background: 'transparent',
                                                fontFamily: 'inherit'
                                            }}
                                            placeholder="..."
                                            value={getCellValue(row.id, col.id)}
                                            onChange={(e) => handleCellChange(row.id, col.id, e.target.value)}
                                        />
                                    </td>
                                ))}
                            </tr>
                        ))
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}