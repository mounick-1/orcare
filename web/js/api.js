/**
 * api.js — ORCare Web API Layer
 * All backend calls go through this module.
 * Base URL is configurable for different environments.
 */

const API = (() => {

  // ── Configuration ──────────────────────────────────────────
  // Change SERVER_IP to match your backend machine's IP address
  const SERVER_IP   = '192.168.1.5';   // same as Android RetrofitClient
  const SERVER_PORT = '3000';
  const BASE_URL    = `http://${SERVER_IP}:${SERVER_PORT}/`;

  // Removed hardcoded Gemini API Key - now proxying securely through the node.js backend.
  // ── Helpers ─────────────────────────────────────────────────
  function getToken() { return localStorage.getItem('orcare_token'); }

  function defaultHeaders(json = true) {
    const h = {};
    if (json) h['Content-Type'] = 'application/json';
    const t = getToken();
    if (t) h['Authorization'] = `Bearer ${t}`;
    return h;
  }

  async function request(path, options = {}) {
    const url = BASE_URL + path.replace(/^\//, '');
    try {
      const res = await fetch(url, {
        ...options,
        headers: { ...defaultHeaders(), ...(options.headers || {}) }
      });
      const data = await res.json().catch(() => ({}));
      return { ok: res.ok, status: res.status, data };
    } catch (e) {
      console.error('[API]', path, e);
      return { ok: false, status: 0, data: { message: 'Network error. Check your connection.' } };
    }
  }

  // ── Auth Endpoints ─────────────────────────────────────────
  async function login(email, password) {
    return request('api/auth/login', {
      method: 'POST',
      body: JSON.stringify({ email, password })
    });
  }

  async function register(name, email, password) {
    return request('api/auth/register', {
      method: 'POST',
      body: JSON.stringify({ name, email, password })
    });
  }

  async function forgotPassword(email) {
    return request('api/auth/forgot-password', {
      method: 'POST',
      body: JSON.stringify({ email })
    });
  }

  async function resendOtp(email) {
    return request('api/auth/resend-otp', {
      method: 'POST',
      body: JSON.stringify({ email })
    });
  }

  async function verifyOtp(email, otp) {
    return request('api/auth/verify-otp', {
      method: 'POST',
      body: JSON.stringify({ email, otp })
    });
  }

  async function updateProfile(name) {
    return request('api/users/profile', {
      method: 'PUT',
      body: JSON.stringify({ name })
    });
  }

  // ── Content Endpoints ───────────────────────────────────────
  async function getDiseases() {
    return request('api/content/diseases');
  }

  async function getLearning() {
    return request('api/content/learning');
  }

  async function submitFeedback(message, rating) {
    return request('api/content/feedback', {
      method: 'POST',
      body: JSON.stringify({ message, rating })
    });
  }

  // ── Chat History ────────────────────────────────────────────
  async function saveChatHistory(messages) {
    return request('api/chat/save', {
      method: 'POST',
      body: JSON.stringify({ messages })
    });
  }

  async function getChatHistory() {
    return request('api/chat/history');
  }

  // ── AI Chat (via Secure Backend) ────────────────────────────
  /**
   * Sends a multi-turn conversation securely through the backend.
   * @param {Array} historyItems - Array of {role, parts:[{text}]} objects
   * @returns {Promise<string>} - AI response text
   */
  async function askGemini(historyItems) {
    try {
      if (!historyItems || historyItems.length === 0) return null;

      // The backend expects { message: string, history: Array }
      const lastItem = historyItems[historyItems.length - 1];
      const messageText = lastItem.parts[0].text;
      const priorHistory = historyItems.slice(0, historyItems.length - 1);

      const res = await fetch(BASE_URL + 'api/chat/chat', {
        method: 'POST',
        headers: { ...defaultHeaders() },
        body: JSON.stringify({
          message: messageText,
          history: priorHistory
        })
      });

      const json = await res.json();
      if (!res.ok) {
        const errMsg = json?.message || `HTTP ${res.status}`;
        throw new Error(errMsg);
      }

      return json.text || null;

    } catch (e) {
      console.error('[Gemini/Backend API]', e);
      throw e;
    }
  }

  // ── Public API ──────────────────────────────────────────────
  return {
    BASE_URL,
    login,
    register,
    forgotPassword,
    verifyOtp,
    resendOtp,
    updateProfile,
    getDiseases,
    getLearning,
    submitFeedback,
    saveChatHistory,
    getChatHistory,
    askGemini,
    getToken
  };

})();
