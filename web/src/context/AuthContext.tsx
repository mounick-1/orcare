import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import * as apiService from '../api/apiService';

interface User {
  _id: string;
  name: string;
  email: string;
  age?: number;
  gender?: string;
  language?: string;
  district?: string;
  state?: string;
  isEmailVerified?: boolean;
}

type AuthState = 'idle' | 'loading' | 'success' | 'error';

interface AuthContextType {
  user: User | null;
  token: string | null;
  isLoading: boolean;
  language: string;
  loginState: { status: AuthState; message: string };
  registerState: { status: AuthState; message: string };
  otpState: { status: AuthState; message: string };
  forgotState: { status: AuthState; message: string };
  profileUpdateState: { status: AuthState; message: string };
  login: (email: string, password: string) => Promise<void>;
  register: (name: string, email: string, password: string, age: number, gender: string, lang: string) => Promise<void>;
  verifyOtp: (email: string, otp: string, type: string) => Promise<void>;
  resendOtp: (email: string, type: string) => Promise<void>;
  forgotPassword: (email: string) => Promise<void>;
  resetPassword: (email: string, otp: string, newPassword: string) => Promise<void>;
  updateProfile: (data: Partial<User>) => Promise<void>;
  logout: () => Promise<void>;
  deleteAccount: () => Promise<void>;
  requestDeleteOtp: (email: string, password: string) => Promise<void>;
  confirmDeleteAccount: (email: string, otp: string) => Promise<void>;
  deleteAccountState: { status: AuthState; message: string };
  setLanguage: (lang: string) => Promise<void>;
  resetStates: () => void;
}

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>({ _id: 'guest', name: 'Guest', email: '' });
  const [token, setToken] = useState<string | null>('guest');
  const [isLoading, setIsLoading] = useState(true);
  const [language, setLang] = useState('English');

  const [loginState, setLoginState] = useState<{ status: AuthState; message: string }>({ status: 'idle', message: '' });
  const [registerState, setRegisterState] = useState<{ status: AuthState; message: string }>({ status: 'idle', message: '' });
  const [otpState, setOtpState] = useState<{ status: AuthState; message: string }>({ status: 'idle', message: '' });
  const [forgotState, setForgotState] = useState<{ status: AuthState; message: string }>({ status: 'idle', message: '' });
  const [profileUpdateState, setProfileUpdateState] = useState<{ status: AuthState; message: string }>({ status: 'idle', message: '' });
  const [deleteAccountState, setDeleteAccountState] = useState<{ status: AuthState; message: string }>({ status: 'idle', message: '' });

  // Restore session on startup
  useEffect(() => {
    (async () => {
      try {
        const storedToken = await AsyncStorage.getItem('authToken');
        const storedUser = await AsyncStorage.getItem('user');
        const storedLang = await AsyncStorage.getItem('language');
        if (storedToken && storedUser) {
          const parsedUser = JSON.parse(storedUser);
          setToken(storedToken);
          setUser(parsedUser);
          apiService.setAuthToken(storedToken);
        }
        if (storedLang) setLang(storedLang);
      } catch {
        // ignore
      } finally {
        setIsLoading(false);
      }
    })();
  }, []);

  async function saveSession(tok: string, usr: User) {
    await AsyncStorage.setItem('authToken', tok);
    await AsyncStorage.setItem('user', JSON.stringify(usr));
    apiService.setAuthToken(tok);
    setToken(tok);
    setUser(usr);
  }

  async function login(email: string, password: string) {
    setLoginState({ status: 'loading', message: '' });
    try {
      const data = await apiService.login(email, password);
      await saveSession(data.token, data.user);
      setLoginState({ status: 'success', message: 'Login successful' });
    } catch (e: any) {
      setLoginState({ status: 'error', message: e?.response?.data?.message ?? 'Login failed' });
    }
  }

  async function register(name: string, email: string, password: string, age: number, gender: string, lang: string) {
    setRegisterState({ status: 'loading', message: '' });
    try {
      await apiService.register(name, email, password, age, gender, lang);
      setRegisterState({ status: 'success', message: 'Registration successful! Please verify your email.' });
    } catch (e: any) {
      setRegisterState({ status: 'error', message: e?.response?.data?.message ?? 'Registration failed' });
    }
  }

  async function verifyOtp(email: string, otp: string, type: string) {
    setOtpState({ status: 'loading', message: '' });
    try {
      const data = await apiService.verifyOtp(email, otp, type);
      if (data.token && data.user) {
        await saveSession(data.token, data.user);
      }
      setOtpState({ status: 'success', message: 'Email verified successfully!' });
    } catch (e: any) {
      setOtpState({ status: 'error', message: e?.response?.data?.message ?? 'OTP verification failed' });
    }
  }

  async function resendOtp(email: string, type: string) {
    try {
      await apiService.resendOtp(email, type);
    } catch {
      // ignore
    }
  }

  async function forgotPassword(email: string) {
    setForgotState({ status: 'loading', message: '' });
    try {
      await apiService.forgotPassword(email);
      setForgotState({ status: 'success', message: 'Reset link sent to your email.' });
    } catch (e: any) {
      setForgotState({ status: 'error', message: e?.response?.data?.message ?? 'Failed to send reset email' });
    }
  }

  async function resetPassword(email: string, otp: string, newPassword: string) {
    setForgotState({ status: 'loading', message: '' });
    try {
      await apiService.resetPassword(email, otp, newPassword);
      setForgotState({ status: 'success', message: 'Password reset successful!' });
    } catch (e: any) {
      setForgotState({ status: 'error', message: e?.response?.data?.message ?? 'Password reset failed' });
    }
  }

  async function updateProfile(data: Partial<User>) {
    setProfileUpdateState({ status: 'loading', message: '' });
    try {
      const res = await apiService.updateProfile(data);
      const updated = { ...user, ...res.user } as User;
      await AsyncStorage.setItem('user', JSON.stringify(updated));
      setUser(updated);
      setProfileUpdateState({ status: 'success', message: 'Profile updated!' });
    } catch (e: any) {
      setProfileUpdateState({ status: 'error', message: e?.response?.data?.message ?? 'Update failed' });
    }
  }

  async function logout() {
    await AsyncStorage.multiRemove(['authToken', 'user']);
    apiService.setAuthToken(null);
    setToken(null);
    setUser(null);
    resetStates();
  }

  async function deleteAccount() {
    try {
      await apiService.deleteAccount();
    } catch {
      // ignore
    }
    await logout();
  }

  async function requestDeleteOtp(email: string, password: string) {
    setDeleteAccountState({ status: 'loading', message: '' });
    try {
      await apiService.requestDeleteOtp(email, password);
      setDeleteAccountState({ status: 'success', message: 'OTP sent to your email' });
    } catch (e: any) {
      setDeleteAccountState({ status: 'error', message: e?.response?.data?.message ?? 'Invalid credentials' });
    }
  }

  async function confirmDeleteAccount(email: string, otp: string) {
    setDeleteAccountState({ status: 'loading', message: '' });
    try {
      await apiService.confirmDeleteAccount(email, otp);
      // Wipe all local storage
      await AsyncStorage.multiRemove(['authToken', 'user', 'language']);
      apiService.setAuthToken(null);
      setToken(null);
      setUser(null);
      resetStates();
      setDeleteAccountState({ status: 'success', message: 'Account deleted' });
    } catch (e: any) {
      setDeleteAccountState({ status: 'error', message: e?.response?.data?.message ?? 'Invalid OTP' });
    }
  }

  async function setLanguage(lang: string) {
    await AsyncStorage.setItem('language', lang);
    setLang(lang);
  }

  function resetStates() {
    setLoginState({ status: 'idle', message: '' });
    setRegisterState({ status: 'idle', message: '' });
    setOtpState({ status: 'idle', message: '' });
    setForgotState({ status: 'idle', message: '' });
    setProfileUpdateState({ status: 'idle', message: '' });
  }

  return (
    <AuthContext.Provider
      value={{
        user, token, isLoading, language,
        loginState, registerState, otpState, forgotState, profileUpdateState,
        login, register, verifyOtp, resendOtp, forgotPassword, resetPassword,
        updateProfile, logout, deleteAccount, requestDeleteOtp, confirmDeleteAccount,
        deleteAccountState, setLanguage, resetStates,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used inside AuthProvider');
  return ctx;
}
