import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const login = async (username: string, password: string) => {
  const response = await api.post('/auth/login', { username, password });
  return response.data;
};

export const getStudentProfile = async () => {
  const response = await api.get('/student/profile');
  return response.data;
};

export const getStudentPerformance = async () => {
  const response = await api.get('/student/performance');
  return response.data;
};

export const createStudent = async (studentData: any) => {
  const response = await api.post('/admin/create-student', studentData);
  return response.data;
};

export const getAllStudents = async () => {
  const response = await api.get('/admin/students');
  return response.data;
};

export default api;
