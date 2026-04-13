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

// --- Gemini Chatbot ---
const GEMINI_API_KEY = process.env.EXPO_PUBLIC_GEMINI_API_KEY ?? '';
const GEMINI_URL =
  'https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent';

export interface ChatTurn {
  role: 'user' | 'model';
  parts: Array<{ text: string }>;
}

export async function sendGeminiMessage(
  history: ChatTurn[],
  newMessage: string
): Promise<string> {
  const key = GEMINI_API_KEY;
  if (!key) {
    return 'Chatbot is not configured. Please add EXPO_PUBLIC_GEMINI_API_KEY to web/.env and restart.';
  }

  const systemPrompt =
    "You are ORCare AI, a friendly and knowledgeable oral health assistant. Your purpose is to provide helpful, accurate information about oral health, dental hygiene, and common dental conditions. Keep responses concise, warm, and easy to understand. Always recommend seeing a dentist for diagnosis and treatment. Do not provide medical diagnoses.";

  const contents: ChatTurn[] = [
    { role: 'user', parts: [{ text: systemPrompt }] },
    { role: 'model', parts: [{ text: "Hello! I'm ORCare AI, your friendly oral health assistant. How can I help you today?" }] },
    ...history,
    { role: 'user', parts: [{ text: newMessage }] },
  ];

  const response = await fetch(`${GEMINI_URL}?key=${key}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      contents,
      generationConfig: {
        temperature: 0.75,
        maxOutputTokens: 900,
      },
    }),
  });

  if (!response.ok) {
    const err = await response.text();
    throw new Error(`Gemini API error: ${err}`);
  }

  const data = await response.json();
  return data?.candidates?.[0]?.content?.parts?.[0]?.text ?? 'Sorry, I could not generate a response.';
}
