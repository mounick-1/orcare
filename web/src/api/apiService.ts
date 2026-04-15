import axios from 'axios';

const API_BASE_URL =
  process.env.EXPO_PUBLIC_API_BASE_URL ?? 'http://localhost:3000';

const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 30000,
  headers: { 'Content-Type': 'application/json' },
});

// Attach auth token to every request
export function setAuthToken(token: string | null) {
  if (token) {
    api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  } else {
    delete api.defaults.headers.common['Authorization'];
  }
}

// --- Auth ---
export async function login(email: string, password: string) {
  const res = await api.post('/api/auth/login', { email, password });
  return res.data;
}

export async function register(
  name: string,
  email: string,
  password: string,
  age: number,
  gender: string,
  language: string
) {
  const res = await api.post('/api/auth/register', { name, email, password, age, gender, language });
  return res.data;
}

export async function verifyOtp(email: string, otp: string, type: string) {
  const res = await api.post('/api/auth/verify-otp', { email, otp, type });
  return res.data;
}

export async function resendOtp(email: string, type: string) {
  const res = await api.post('/api/auth/resend-otp', { email, type });
  return res.data;
}

export async function forgotPassword(email: string) {
  const res = await api.post('/api/auth/forgot-password', { email });
  return res.data;
}

export async function resetPassword(email: string, otp: string, newPassword: string) {
  const res = await api.post('/api/auth/reset-password', { email, otp, newPassword });
  return res.data;
}

// --- Profile ---
export async function updateProfile(data: {
  name?: string;
  email?: string;
  language?: string;
  district?: string;
  state?: string;
}) {
  const res = await api.put('/api/users/profile', data);
  return res.data;
}

export async function requestDeleteOtp(email: string, password: string) {
  const res = await api.post('/api/auth/request-delete-otp', { email, password });
  return res.data;
}

export async function confirmDeleteAccount(email: string, otp: string) {
  const res = await api.post('/api/auth/confirm-delete-account', { email, otp });
  return res.data;
}

export async function deleteAccount() {
  const res = await api.delete('/api/users/profile');
  return res.data;
}

// --- Content ---
export async function fetchDiseases() {
  const res = await api.get('/api/content/diseases');
  return res.data;
}

export async function fetchLearningContent() {
  const res = await api.get('/api/content/learning');
  return res.data;
}

export async function submitFeedback(name: string, email: string, message: string) {
  const res = await api.post('/api/content/feedback', { name, email, message });
  return res.data;
}

// --- Chatbot (proxied through backend) ---

export interface ChatTurn {
  role: 'user' | 'model';
  parts: Array<{ text: string }>;
}

export async function sendGeminiMessage(
  history: ChatTurn[],
  newMessage: string
): Promise<string> {
  const res = await api.post('/api/chat/chat', { message: newMessage, history });
  return res.data.text;
}
