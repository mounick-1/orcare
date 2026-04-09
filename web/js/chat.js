/**
 * chat.js — ORCare AI Chat Module
 * Handles Gemini 2.5 Flash integration, conversation history,
 * message rendering, typing indicator, and session management.
 */

const Chat = (() => {

  const SYSTEM_PROMPT =
    'You are ORCare AI, a friendly and knowledgeable oral health assistant built into the ORCare app. ' +
    'Help users understand oral diseases, symptoms, home remedies, and when to see a dentist. ' +
    'Be concise and warm. Use **bold** for key terms. ' +
    'Always recommend seeing a dentist for serious or persistent issues.';

  // Conversation history sent to Gemini (role/parts format)
  let history      = [];
  let isGenerating = false;
  let isFirstMsg   = true;

  // ── Session Control ─────────────────────────────────────────
  function newSession() {
    history      = [];
    isFirstMsg   = true;
    isGenerating = false;
    clearMessages();
    showEmpty(true);
    App.showToast('New chat started');
  }

  function clearSession() {
    history = [];
    isFirstMsg = true;
    isGenerating = false;
    clearMessages();
    showEmpty(true);
  }

  // ── Send from input field ───────────────────────────────────
  async function send() {
    if (isGenerating) return;
    const input = document.getElementById('chat-input');
    const text  = input.value.trim();
    if (!text) return;
    input.value = '';
    autoResize(input);
    await sendMessage(text);
  }

  // ── Send symptom query (called from HomeScreen / symptomChecker) ─
  async function startSymptom(symptom) {
    App.showMain('chatbot');
    await new Promise(r => setTimeout(r, 120)); // brief delay for screen transition
    const query = `I have a concern about: ${symptom}. What are the common causes, symptoms, and home remedies?`;
    await sendMessage(query);
  }

  // ── Core message handler ────────────────────────────────────
  async function sendMessage(text) {
    if (isGenerating) return;
    isGenerating = true;

    showEmpty(false);
    appendUserMessage(text);
    updateSendBtn(true);

    // Add user turn to history
    const userContent = isFirstMsg
      ? `${SYSTEM_PROMPT}\n\nUser question: ${text}`
      : text;
    isFirstMsg = false;

    history.push({ role: 'user', parts: [{ text: userContent }] });

    // Show typing indicator
    const typingId = appendTyping();

    try {
      const responseText = await API.askGemini(history);

      removeTyping(typingId);

      if (responseText) {
        history.push({ role: 'model', parts: [{ text: responseText }] });
        appendAIMessage(responseText);
        // Auto-save chat to backend (fire-and-forget)
        API.saveChatHistory(buildSavableHistory()).catch(() => {});
      } else {
        history.pop(); // remove failed user turn
        appendAIMessage('⚠️ I couldn\'t generate a response. Please try again.');
      }

    } catch (err) {
      removeTyping(typingId);
      history.pop(); // remove user turn on failure
      appendAIMessage(friendlyError(err));
    }

    isGenerating = false;
    updateSendBtn(false);
    scrollToBottom();
  }

  // ── Render: User Message ────────────────────────────────────
  function appendUserMessage(text) {
    const list = document.getElementById('messages-list');
    const row  = document.createElement('div');
    row.className = 'message-row user';
    row.innerHTML = `<div class="msg-bubble user">${escHtml(text)}</div>`;
    list.appendChild(row);
    scrollToBottom();
  }

  // ── Render: AI Message (supports **bold** markdown) ─────────
  function appendAIMessage(text) {
    const list = document.getElementById('messages-list');
    const row  = document.createElement('div');
    row.className = 'message-row ai';
    row.innerHTML = `
      <div class="msg-avatar">✦</div>
      <div>
        <div class="msg-bubble ai">${renderMarkdown(text)}</div>
        <div class="msg-label">ORCare Assistant</div>
      </div>`;
    list.appendChild(row);
    scrollToBottom();
  }

  // ── Render: Typing indicator ────────────────────────────────
  function appendTyping() {
    const list = document.getElementById('messages-list');
    const id   = 'typing-' + Date.now();
    const row  = document.createElement('div');
    row.className = 'message-row ai';
    row.id = id;
    row.innerHTML = `
      <div class="msg-avatar">✦</div>
      <div class="msg-bubble ai typing">
        <div class="typing-dots">
          <div class="dot"></div>
          <div class="dot"></div>
          <div class="dot"></div>
        </div>
      </div>`;
    list.appendChild(row);
    scrollToBottom();
    return id;
  }

  function removeTyping(id) {
    const el = document.getElementById(id);
    if (el) el.remove();
  }

  // ── Helpers ─────────────────────────────────────────────────
  function clearMessages() { document.getElementById('messages-list').innerHTML = ''; }

  function showEmpty(show) {
    document.getElementById('chat-empty').style.display = show ? '' : 'none';
  }

  function scrollToBottom() {
    const el = document.getElementById('chat-messages');
    el.scrollTop = el.scrollHeight;
  }

  function updateSendBtn(loading) {
    const btn = document.getElementById('send-btn');
    const icon = document.getElementById('send-icon');
    btn.disabled = loading;
    if (loading) {
      icon.outerHTML = '<div id="send-icon"><div class="spinner"></div></div>';
    } else {
      document.getElementById('send-icon').outerHTML = '<span id="send-icon">➤</span>';
    }
  }

  // Render **bold** markdown to <strong> safely
  function renderMarkdown(text) {
    return escHtml(text)
      .replace(/\*\*(.+?)\*\*/g, '<strong>$1</strong>')
      .replace(/\n/g, '<br>');
  }

  function escHtml(t) {
    return t.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;');
  }

  function buildSavableHistory() {
    return history
      .filter(h => h.role !== 'system')
      .map(h => ({ role: h.role, text: h.parts[0]?.text || '' }));
  }

  function friendlyError(err) {
    const msg = err?.message || '';
    if (msg.includes('400'))                            return '⚠️ Model unavailable. Try again shortly.';
    if (msg.includes('401') || msg.includes('403'))     return '⚠️ API key issue. Please contact support.';
    if (msg.includes('429'))                            return '⚠️ Quota reached. Please try again later.';
    if (msg.includes('network') || msg.includes('fetch')) return '📡 No internet. Check your connection.';
    return `⚠️ Error: ${msg.slice(0, 100) || 'Unknown error'}`;
  }

  // Auto-resize textarea
  function autoResize(el) {
    el.style.height = 'auto';
    el.style.height = Math.min(el.scrollHeight, 120) + 'px';
  }

  // ── Init ────────────────────────────────────────────────────
  function init() {
    const input = document.getElementById('chat-input');

    // Send on Enter (Shift+Enter = newline)
    input.addEventListener('keydown', e => {
      if (e.key === 'Enter' && !e.shiftKey) {
        e.preventDefault();
        send();
      }
    });

    // Auto-resize textarea
    input.addEventListener('input', () => autoResize(input));

    // Suggestion chips
    document.getElementById('suggestion-chips').addEventListener('click', e => {
      const chip = e.target.closest('.chip');
      if (chip) sendMessage(chip.dataset.q);
    });
  }

  return { init, send, startSymptom, newSession, clearSession };

})();
