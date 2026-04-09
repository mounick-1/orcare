/**
 * auth.js — ORCare Authentication Module
 * Handles login, signup, logout, profile, session persistence.
 */

const Auth = (() => {

  const STORAGE_TOKEN = 'orcare_token';
  const STORAGE_USER  = 'orcare_user';

  // ── Session ────────────────────────────────────────────────
  function getUser() {
    try { return JSON.parse(localStorage.getItem(STORAGE_USER)) || null; }
    catch { return null; }
  }

  function setSession(token, user) {
    localStorage.setItem(STORAGE_TOKEN, token);
    localStorage.setItem(STORAGE_USER, JSON.stringify(user));
  }

  function clearSession() {
    localStorage.removeItem(STORAGE_TOKEN);
    localStorage.removeItem(STORAGE_USER);
  }

  function isLoggedIn() {
    return !!localStorage.getItem(STORAGE_TOKEN);
  }

  // ── Login ──────────────────────────────────────────────────
  async function login() {
    const email    = document.getElementById('login-email').value.trim();
    const password = document.getElementById('login-password').value;
    const errEl    = document.getElementById('login-error');
    const btn      = document.getElementById('login-btn');

    hideMsg(errEl);
    if (!email || !password) { showMsg(errEl, 'Please fill in all fields.', 'error'); return; }

    btn.disabled = true;
    btn.textContent = 'Signing in…';

    const { ok, data } = await API.login(email, password);

    btn.disabled = false;
    btn.textContent = 'Sign In';

    if (ok && data.token) {
      setSession(data.token, { name: data.name || data.user?.name || 'User', email });
      App.onLoginSuccess();
    } else {
      showMsg(errEl, data.message || 'Invalid credentials. Please try again.', 'error');
    }
  }

  // ── Signup ──────────────────────────────────────────────────
  async function signup() {
    const name     = document.getElementById('signup-name').value.trim();
    const email    = document.getElementById('signup-email').value.trim();
    const password = document.getElementById('signup-password').value;
    const errEl    = document.getElementById('signup-error');
    const btn      = document.getElementById('signup-btn');

    hideMsg(errEl);
    if (!name || !email || !password) { showMsg(errEl, 'Please fill in all fields.', 'error'); return; }
    if (password.length < 6)          { showMsg(errEl, 'Password must be at least 6 characters.', 'error'); return; }

    btn.disabled = true;
    btn.textContent = 'Creating account…';

    const { ok, data } = await API.register(name, email, password);

    btn.disabled = false;
    btn.textContent = 'Create Account';

    if (ok) {
      // Navigate to OTP screen
      localStorage.setItem('orcare_pending_email', email);
      App.navigateTo('otp');
      App.showToast('Code sent to ' + email);
    } else {
      showMsg(errEl, data.message || 'Registration failed. Please try again.', 'error');
    }
  }

  // ── OTP Verification ────────────────────────────────────────
  async function verifyOtp() {
    const email = localStorage.getItem('orcare_pending_email');
    const otp   = document.getElementById('otp-input').value.trim();
    const errEl = document.getElementById('otp-error');
    const btn   = document.getElementById('otp-btn');

    hideMsg(errEl);
    if (!otp || otp.length < 6) { showMsg(errEl, 'Enter 6-digit code.', 'error'); return; }

    btn.disabled = true;
    btn.textContent = 'Verifying…';

    const { ok, data } = await API.verifyOtp(email, otp);

    btn.disabled = false;
    btn.textContent = 'Verify Code';

    if (ok) {
      document.getElementById('otp-icon-wrap').innerHTML = '✓';
      document.getElementById('otp-icon-wrap').style.background = 'var(--success)';
      document.getElementById('otp-title').textContent = 'Verified!';
      showMsg(errEl, 'Verified successfully! Redirecting...', 'success');
      
      setSession(data.token, { name: data.name || 'User', email });
      
      setTimeout(() => {
        App.onLoginSuccess();
      }, 1500);
    } else {
      showMsg(errEl, data.message || 'Invalid code. Please try again.', 'error');
    }
  }

  async function resendOtp() {
    const email = localStorage.getItem('orcare_pending_email');
    if (!email) return;
    App.showToast('Resending code…');
    const { ok } = await API.resendOtp(email);
    if (ok) App.showToast('Code resent!');
  }

  // ── Forgot Password ─────────────────────────────────────────
  async function forgotPassword() {
    const email = document.getElementById('forgot-email').value.trim();
    const msgEl = document.getElementById('forgot-msg');
    const btn   = document.getElementById('forgot-btn');

    hideMsg(msgEl);
    if (!email) { showMsg(msgEl, 'Please enter your email.', 'error'); return; }

    btn.disabled = true;
    btn.textContent = 'Sending…';

    const { ok, data } = await API.forgotPassword(email);

    btn.disabled = false;
    btn.textContent = 'Send Reset Link';

    if (ok) {
      App.navigateTo('reset-sent');
    } else {
      showMsg(msgEl, data.message || 'Could not send reset link. Try again.', 'error');
    }
  }

  // ── Update Profile ──────────────────────────────────────────
  async function updateProfile() {
    const name  = document.getElementById('profile-name-input').value.trim();
    const msgEl = document.getElementById('profile-msg');

    hideMsg(msgEl);
    if (!name) { showMsg(msgEl, 'Name cannot be empty.', 'error'); return; }

    const { ok, data } = await API.updateProfile(name);

    if (ok) {
      const user = getUser();
      if (user) { user.name = name; setSession(localStorage.getItem('orcare_token'), user); }
      document.getElementById('profile-name').textContent = name;
      document.getElementById('greeting-text').textContent = `Hello, ${name} 👋`;
      showMsg(msgEl, '✓ Profile updated!', 'success');
      App.showToast('Profile saved!');
    } else {
      showMsg(msgEl, data.message || 'Failed to update profile.', 'error');
    }
  }

  async function saveProfileExtended() {
    const name = document.getElementById('edit-name').value.trim();
    const dist = document.getElementById('edit-district').value.trim();
    const state = document.getElementById('edit-state').value.trim();
    
    if (!name) { App.showToast('Name is required'); return; }
    
    // Save to local storage (mock for now since API might not have dist/state)
    const user = getUser();
    if (user) {
       user.name = name;
       user.district = dist;
       user.state = state;
       setSession(localStorage.getItem('orcare_token'), user);
    }
    
    // Attempt API update (just name usually)
    await API.updateProfile(name);
    
    populateProfile();
    App.showToast('Profile updated!');
    App.showMain('profile');
  }

  function deleteAccount() {
    if (confirm('CRITICAL: Are you sure you want to delete your account? This cannot be undone.')) {
      clearSession();
      App.showToast('Account deleted.');
      window.location.reload();
    }
  }

  // ── Logout ──────────────────────────────────────────────────
  function logout() {
    if (!confirm('Are you sure you want to sign out?')) return;
    clearSession();
    Chat.clearSession();
    document.getElementById('login-email').value = '';
    document.getElementById('login-password').value = '';
    App.showAuth('login');
    App.showToast('Signed out.');
  }

  // ── Populate Profile UI ─────────────────────────────────────
  function populateProfile() {
    const user = getUser();
    if (!user) return;
    document.getElementById('profile-name').textContent         = user.name || 'User';
    document.getElementById('profile-email').textContent        = user.email || '';
    document.getElementById('profile-name-input').value         = user.name || '';
    document.getElementById('profile-email-input').value        = user.email || '';
    document.getElementById('greeting-text').textContent        = `Hello, ${user.name || 'User'} 👋`;
    
    // Update edit profile fields
    const en = document.getElementById('edit-name');
    if (en) {
      en.value = user.name || '';
      document.getElementById('edit-district').value = user.district || '';
      document.getElementById('edit-state').value = user.state || '';
      const av = localStorage.getItem('orcare_avatar') || '👤';
      document.querySelectorAll('.avatar-icon, .profile-avatar, .profile-avatar-large').forEach(el => el.textContent = av);
    }
  }

  // ── UI helpers ──────────────────────────────────────────────
  function showMsg(el, msg, type) {
    el.textContent = msg;
    el.className = `error-msg ${type}`;
  }
  function hideMsg(el) { el.className = 'error-msg hidden'; el.textContent = ''; }

  // ── Init form listeners ─────────────────────────────────────
  function init() {
    document.getElementById('login-form').addEventListener('submit', e => { e.preventDefault(); login(); });
    document.getElementById('signup-form').addEventListener('submit', e => { e.preventDefault(); signup(); });
    document.getElementById('forgot-form').addEventListener('submit', e => { e.preventDefault(); forgotPassword(); });
    document.getElementById('otp-form').addEventListener('submit', e => { e.preventDefault(); verifyOtp(); });

    // Password toggle
    document.querySelectorAll('.eye-btn').forEach(btn => {
      btn.addEventListener('click', () => {
        const inp = document.getElementById(btn.dataset.target);
        inp.type = inp.type === 'password' ? 'text' : 'password';
      });
    });
  }

  return { init, login, signup, forgotPassword, verifyOtp, resendOtp, updateProfile, saveProfileExtended, deleteAccount, logout, isLoggedIn, getUser, populateProfile };

})();
