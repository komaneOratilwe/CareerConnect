import React, { useState, useEffect } from 'react';
import {
    createStudent,
    getAllStudents,
    deleteStudent,
    getStudentsByName,
    readStudent,
    updateStudent
} from '../services/studentService';
import {
    UserPlus,
    Users,
    Search,
    Trash2,
    Pencil,
    RefreshCw,
    CheckCircle2,
    Hash,
    User,
    Mail,
    Lock,
    X
} from 'lucide-react';

export default function AdminStudentPage() {
    const [formData, setFormData] = useState({
        studentNumber: '',
        name: '',
        email: '',
        password: ''
    });

    const [students, setStudents] = useState([]);
    const [searchQuery, setSearchQuery] = useState('');
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState({ type: '', text: '' });
    const [editingStudent, setEditingStudent] = useState(null);
    const [editFormData, setEditFormData] = useState({ studentNumber: '', name: '', email: '', password: '' });

    const fetchStudents = async () => {
        setLoading(true);
        try {
            const data = await getAllStudents();
            setStudents(data);
        } catch (err) {
            setMessage({ type: 'error', text: 'Failed to connect to Spring Boot backend.' });
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchStudents();
    }, []);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage({ type: '', text: '' });

        try {
            const result = await createStudent(formData);
            if (result) {
                setMessage({ type: 'success', text: 'Student registered successfully!' });
                setFormData({ studentNumber: '', name: '', email: '', password: '' });
                fetchStudents();
            } else {
                setMessage({ type: 'error', text: 'Registration failed. Check if details already exist.' });
            }
        } catch (err) {
            setMessage({ type: 'error', text: 'Error connecting to server. Ensure Spring Boot is running.' });
        }
    };

    const handleEditClick = async (studentNumber) => {
        try {
            const data = await readStudent(studentNumber);
            if (data) {
                setEditingStudent(data);
                setEditFormData(data);
            }
        } catch (err) {
            setMessage({ type: 'error', text: 'Failed to read student details.' });
        }
    };

    const handleUpdateSubmit = async (e) => {
        e.preventDefault();
        try {
            const result = await updateStudent(editFormData);
            if (result) {
                setMessage({ type: 'success', text: `Student ${result.studentNumber} updated successfully!` });
                setEditingStudent(null);
                fetchStudents();
            } else {
                setMessage({ type: 'error', text: 'Update failed. Check student number.' });
            }
        } catch (err) {
            setMessage({ type: 'error', text: 'Error updating student record.' });
        }
    };


    const handleDelete = async (studentNumber) => {
        if (window.confirm(`Are you sure you want to delete student ${studentNumber}?`)) {
            try {
                await deleteStudent(studentNumber);
                setMessage({ type: 'success', text: `Student ${studentNumber} deleted successfully.` });
                fetchStudents();
            } catch (err) {
                setMessage({ type: 'error', text: 'Failed to delete student.' });
            }
        }
    };

    const handleSearch = async (e) => {
        const query = e.target.value;
        setSearchQuery(query);

        if (query.trim() === '') {
            fetchStudents();
        } else {
            try {
                const results = await getStudentsByName(query);
                setStudents(results);
            } catch (err) {
                console.error("Search error:", err);
            }
        }
    };

    return (
        <div className="max-w-7xl mx-auto p-4 sm:p-6 lg:p-8 space-y-6">

            {/* Title */}
            <div className="flex items-center space-x-3 border-b border-ui-border pb-4">
                <Users className="w-8 h-8 text-brand-accent" />
                <div>
                    <h2 className="font-heading text-2xl font-extrabold text-text-main">Student Portal Directory</h2>
                    <p className="text-text-muted text-xs">Manage student registrations and database records in real-time</p>
                </div>
            </div>

            {/* Alert Messages */}
            {message.text && (
                <div className={`flex items-center space-x-2 p-4 mb-6 rounded-xl font-medium text-xs border shadow-sm ${
                    message.type === 'success'
                        ? 'bg-emerald-50 text-emerald-900 border-emerald-200'  
                        : 'bg-rose-50 text-rose-900 border-rose-200'       
                }`}>
                    <CheckCircle2 className={`w-4 h-4 shrink-0 ${
                        message.type === 'success' ? 'text-emerald-600' : 'text-rose-600'
                    }`} />
                    <span>{message.text}</span>
                </div>
            )}

            <div className="grid grid-cols-1 lg:grid-cols-12 gap-8">

                {/* Registration Form */}
                <div className="lg:col-span-5 bg-card-bg p-6 rounded-2xl border border-ui-border shadow-xl h-fit">
                    <div className="flex items-center space-x-2 mb-4 text-brand-accent">
                        <UserPlus className="w-5 h-5" />
                        <h3 className="font-heading text-lg font-bold text-text-main">Register New Student</h3>
                    </div>

                    <form onSubmit={handleSubmit} className="space-y-4">
                        <div>
                            <label className="block text-xs font-semibold text-text-muted mb-1.5">Student Number</label>
                            <div className="relative">
                                <Hash className="w-4 h-4 absolute left-3.5 top-3 text-text-muted" />
                                <input
                                    type="text"
                                    name="studentNumber"
                                    value={formData.studentNumber}
                                    onChange={handleChange}
                                    placeholder="e.g. 240456890"
                                    required
                                    className="w-full bg-app-bg border border-ui-border focus:border-brand-accent rounded-xl pl-10 pr-4 py-2.5 text-xs text-text-main placeholder-text-muted/60 focus:outline-none font-mono"
                                />
                            </div>
                        </div>

                        <div>
                            <label className="block text-xs font-semibold text-text-muted mb-1.5">Full Name</label>
                            <div className="relative">
                                <User className="w-4 h-4 absolute left-3.5 top-3 text-text-muted" />
                                <input
                                    type="text"
                                    name="name"
                                    value={formData.name}
                                    onChange={handleChange}
                                    placeholder="e.g. Ben Thamiso"
                                    required
                                    className="w-full bg-app-bg border border-ui-border focus:border-brand-accent rounded-xl pl-10 pr-4 py-2.5 text-xs text-text-main placeholder-text-muted/60 focus:outline-none"
                                />
                            </div>
                        </div>

                        <div>
                            <label className="block text-xs font-semibold text-text-muted mb-1.5">Email Address</label>
                            <div className="relative">
                                <Mail className="w-4 h-4 absolute left-3.5 top-3 text-text-muted" />
                                <input
                                    type="email"
                                    name="email"
                                    value={formData.email}
                                    onChange={handleChange}
                                    placeholder="e.g. ben@cput.ac.za"
                                    required
                                    className="w-full bg-app-bg border border-ui-border focus:border-brand-accent rounded-xl pl-10 pr-4 py-2.5 text-xs text-text-main placeholder-text-muted/60 focus:outline-none"
                                />
                            </div>
                        </div>

                        <div>
                            <label className="block text-xs font-semibold text-text-muted mb-1.5">Password</label>
                            <div className="relative">
                                <Lock className="w-4 h-4 absolute left-3.5 top-3 text-text-muted" />
                                <input
                                    type="password"
                                    name="password"
                                    value={formData.password}
                                    onChange={handleChange}
                                    placeholder="••••••••"
                                    required
                                    className="w-full bg-app-bg border border-ui-border focus:border-brand-accent rounded-xl pl-10 pr-4 py-2.5 text-xs text-text-main placeholder-text-muted/60 focus:outline-none font-mono"
                                />
                            </div>
                        </div>

                        <button
                            type="submit"
                            className="w-full bg-brand-primary hover:bg-brand-primary-hover text-white font-semibold py-2.5 rounded-xl text-xs shadow-lg shadow-brand-primary/20 transition-all hover:scale-[1.01] cursor-pointer"
                        >
                            Submit Registration
                        </button>
                    </form>
                </div>

                {/* Database Directory & Search */}
                <div className="lg:col-span-7 bg-card-bg p-6 rounded-2xl border border-ui-border shadow-xl flex flex-col justify-between">
                    <div>
                        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 mb-4">
                            <h3 className="font-heading text-lg font-bold text-text-main">Database Directory</h3>

                            <div className="flex items-center space-x-2">
                                {/* Search Bar */}
                                <div className="relative">
                                    <Search className="w-3.5 h-3.5 absolute left-3 top-2.5 text-text-muted" />
                                    <input
                                        type="text"
                                        value={searchQuery}
                                        onChange={handleSearch}
                                        placeholder="Search by name..."
                                        className="bg-app-bg border border-ui-border rounded-lg pl-8 pr-3 py-1.5 text-xs text-text-main placeholder-text-muted/60 focus:outline-none focus:border-brand-accent"
                                    />
                                </div>

                                {/* Refresh Button */}
                                <button
                                    onClick={fetchStudents}
                                    title="Refresh Table"
                                    className="p-1.5 text-text-muted hover:text-text-main bg-app-bg border border-ui-border rounded-lg transition-colors cursor-pointer"
                                >
                                    <RefreshCw className="w-3.5 h-3.5" />
                                </button>
                            </div>
                        </div>

                        {loading ? (
                            <p className="text-text-muted text-xs text-center py-12">Loading database records...</p>
                        ) : students.length === 0 ? (
                            <p className="text-text-muted text-xs text-center py-12">No student records found in MySQL database.</p>
                        ) : (
                            <div className="overflow-x-auto rounded-xl border border-ui-border">
                                <table className="w-full text-left text-xs text-text-main">
                                    <thead className="bg-app-bg text-text-muted font-mono uppercase text-[10px]">
                                    <tr>
                                        <th className="p-3">Student ID</th>
                                        <th className="p-3">Full Name</th>
                                        <th className="p-3">Email</th>
                                        <th className="p-3 text-right">Action</th>
                                    </tr>
                                    </thead>
                                    <tbody className="divide-y divide-ui-border">
                                    {students.map((std) => (
                                        <tr key={std.studentNumber} className="hover:bg-app-bg/50 transition-colors">
                                            <td className="p-3 font-mono text-brand-accent font-semibold">{std.studentNumber}</td>
                                            <td className="p-3 font-medium">{std.name}</td>
                                            <td className="p-3 text-text-muted">{std.email}</td>
                                            <td className="p-3 text-right">
                                                <button
                                                    onClick={() => handleEditClick(std.studentNumber)}
                                                    title="Edit Student"
                                                    className="p-1.5 text-text-muted hover:text-brand-accent hover:bg-card-hover rounded-lg transition-colors cursor-pointer"
                                                >
                                                    <Pencil className="w-3.5 h-3.5" />
                                                </button>

                                                <button
                                                    onClick={() => handleDelete(std.studentNumber)}
                                                    title="Delete Student"
                                                    className="p-1.5 text-text-muted hover:text-rose-400 hover:bg-rose-950/40 rounded-lg transition-colors cursor-pointer"
                                                >
                                                    <Trash2 className="w-3.5 h-3.5" />
                                                </button>
                                            </td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                        )}
                    </div>
                </div>

            </div>

            {editingStudent && (
                <div className="fixed inset-0 z-50 bg-black/70 backdrop-blur-sm flex items-center justify-center p-4">
                    <div className="bg-card-bg border border-ui-border rounded-2xl max-w-md w-full p-6 shadow-2xl space-y-4">
                        <div className="flex justify-between items-center border-b border-ui-border pb-3">
                            <h3 className="font-heading font-bold text-text-main">Edit Student Record</h3>
                            <button
                                onClick={() => setEditingStudent(null)}
                                className="p-1 text-text-muted hover:text-text-main rounded-lg"
                            >
                                <X className="w-4 h-4" />
                            </button>
                        </div>

                        <form onSubmit={handleUpdateSubmit} className="space-y-3">
                            <div>
                                <label className="block text-xs font-semibold text-text-muted mb-1">Student ID (Read Only)</label>
                                <input
                                    type="text"
                                    value={editFormData.studentNumber}
                                    disabled
                                    className="w-full bg-app-bg/50 border border-ui-border rounded-xl px-3 py-2 text-xs text-brand-accent font-mono cursor-not-allowed"
                                />
                            </div>

                            <div>
                                <label className="block text-xs font-semibold text-text-muted mb-1">Full Name</label>
                                <input
                                    type="text"
                                    value={editFormData.name}
                                    onChange={(e) => setEditFormData({ ...editFormData, name: e.target.value })}
                                    required
                                    className="w-full bg-app-bg border border-ui-border focus:border-brand-accent rounded-xl px-3 py-2 text-xs text-text-main"
                                />
                            </div>

                            <div>
                                <label className="block text-xs font-semibold text-text-muted mb-1">Email Address</label>
                                <input
                                    type="email"
                                    value={editFormData.email}
                                    onChange={(e) => setEditFormData({ ...editFormData, email: e.target.value })}
                                    required
                                    className="w-full bg-app-bg border border-ui-border focus:border-brand-accent rounded-xl px-3 py-2 text-xs text-text-main"
                                />
                            </div>

                            <div className="pt-2 flex justify-end space-x-2">
                                <button
                                    type="button"
                                    onClick={() => setEditingStudent(null)}
                                    className="px-4 py-2 rounded-xl text-xs text-text-muted hover:text-text-main bg-app-bg border border-ui-border cursor-pointer"
                                >
                                    Cancel
                                </button>
                                <button
                                    type="submit"
                                    className="px-4 py-2 rounded-xl text-xs font-semibold text-white bg-brand-primary hover:bg-brand-primary-hover shadow-md cursor-pointer"
                                >
                                    Save Changes
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}