import React, { useState, useEffect } from 'react';
import {
  View, Text, StyleSheet, TouchableOpacity, TextInput, Alert, ScrollView,
} from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { ArrowLeft } from 'lucide-react-native';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { useAuth } from '../context/AuthContext';

type Props = NativeStackScreenProps<RootStackParamList, 'DeleteAccount'>;
type Step = 'credentials' | 'otp' | 'success';

export default function DeleteAccountScreen({ navigation }: Props) {
  const { requestDeleteOtp, confirmDeleteAccount, deleteAccountState, resetStates } = useAuth();

  const [step, setStep] = useState<Step>('credentials');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [passwordVisible, setPasswordVisible] = useState(false);
  const [otp, setOtp] = useState('');
  const [errorMsg, setErrorMsg] = useState('');

  useEffect(() => {
    if (deleteAccountState.status === 'success') {
      if (step === 'credentials') {
        setStep('otp');
        setErrorMsg('');
      } else if (step === 'otp') {
        setStep('success');
        setErrorMsg('');
      }
    }
    if (deleteAccountState.status === 'error') {
      setErrorMsg(deleteAccountState.message);
    }
  }, [deleteAccountState]);

  // On success step, navigate to sign in
  useEffect(() => {
    if (step === 'success') {
      const timer = setTimeout(() => {
        resetStates();
        navigation.reset({ index: 0, routes: [{ name: 'SignIn' }] });
      }, 2500);
      return () => clearTimeout(timer);
    }
  }, [step]);

  function handleSendOtp() {
    setErrorMsg('');
    if (!email || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
      setErrorMsg('Enter a valid email address');
      return;
    }
    if (!password) {
      setErrorMsg('Password is required');
      return;
    }
    requestDeleteOtp(email.trim(), password);
  }

  function handleConfirm() {
    setErrorMsg('');
    if (otp.length < 6) {
      setErrorMsg('Enter the 6-digit code');
      return;
    }
    confirmDeleteAccount(email.trim(), otp);
  }

  const isLoading = deleteAccountState.status === 'loading';

  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        {step !== 'success' && (
          <TouchableOpacity
            style={styles.backBtn}
            onPress={() => {
              if (step === 'otp') { setStep('credentials'); setErrorMsg(''); }
              else navigation.goBack();
            }}
          >
            <ArrowLeft size={20} color={Colors.primary} strokeWidth={2.5} />
          </TouchableOpacity>
        )}
        <View style={styles.headerCenter}>
          <Text style={styles.headerTitle}>
            {step === 'credentials' ? 'Delete Account' : step === 'otp' ? 'Verify Identity' : 'Account Deleted'}
          </Text>
        </View>
      </View>

      <ScrollView contentContainerStyle={styles.content} showsVerticalScrollIndicator={false}>
        {/* Icon */}
        <View style={[styles.iconCircle, step === 'success' && styles.iconCircleSuccess]}>
          <Text style={styles.iconEmoji}>
            {step === 'success' ? '✅' : step === 'otp' ? '📧' : '🗑️'}
          </Text>
        </View>

        <Text style={styles.title}>
          {step === 'credentials' ? 'Close Your Account'
            : step === 'otp' ? 'Check Your Email'
            : 'Done'}
        </Text>
        <Text style={styles.subtitle}>
          {step === 'credentials'
            ? 'Enter your email and password to verify your identity.'
            : step === 'otp'
            ? `A 6-digit code was sent to ${email}. Enter it to confirm deletion.`
            : 'Your account and all data have been permanently removed.'}
        </Text>

        {/* Error */}
        {!!errorMsg && (
          <View style={styles.errorBanner}>
            <Text style={styles.errorText}>⚠️ {errorMsg}</Text>
          </View>
        )}

        {/* Step: Credentials */}
        {step === 'credentials' && (
          <>
            <View style={styles.card}>
              <Text style={styles.inputLabel}>Email Address</Text>
              <TextInput
                style={styles.input}
                value={email}
                onChangeText={(t) => { setEmail(t); setErrorMsg(''); }}
                placeholder="you@example.com"
                placeholderTextColor={Colors.textMuted}
                keyboardType="email-address"
                autoCapitalize="none"
              />

              <Text style={[styles.inputLabel, { marginTop: 12 }]}>Password</Text>
              <View style={styles.passwordRow}>
                <TextInput
                  style={[styles.input, { flex: 1, marginRight: 8 }]}
                  value={password}
                  onChangeText={(t) => { setPassword(t); setErrorMsg(''); }}
                  placeholder="Your password"
                  placeholderTextColor={Colors.textMuted}
                  secureTextEntry={!passwordVisible}
                />
                <TouchableOpacity onPress={() => setPasswordVisible(!passwordVisible)} style={styles.eyeBtn}>
                  <Text style={styles.eyeText}>{passwordVisible ? '🙈' : '👁️'}</Text>
                </TouchableOpacity>
              </View>

              <TouchableOpacity
                style={[styles.deleteBtn, isLoading && styles.btnDisabled]}
                onPress={handleSendOtp}
                disabled={isLoading}
                activeOpacity={0.85}
              >
                <Text style={styles.deleteBtnText}>{isLoading ? 'Sending...' : 'Send Verification Code'}</Text>
              </TouchableOpacity>
            </View>

            {/* Warning */}
            <View style={styles.warningBox}>
              <Text style={styles.warningTitle}>⚠️ This will permanently delete:</Text>
              {['Your account and profile', 'All AI chat history', 'Learning progress and quiz results', 'All credentials from our database'].map((item, i) => (
                <Text key={i} style={styles.warningItem}>• {item}</Text>
              ))}
            </View>
          </>
        )}

        {/* Step: OTP */}
        {step === 'otp' && (
          <View style={styles.card}>
            <Text style={styles.inputLabel}>6-Digit Verification Code</Text>
            <TextInput
              style={[styles.input, styles.otpInput]}
              value={otp}
              onChangeText={(t) => { if (t.length <= 6 && /^\d*$/.test(t)) { setOtp(t); setErrorMsg(''); } }}
              placeholder="······"
              placeholderTextColor={Colors.textMuted}
              keyboardType="numeric"
              maxLength={6}
            />
            <Text style={styles.otpHint}>Code expires in 10 minutes</Text>

            <TouchableOpacity
              style={[styles.deleteBtn, isLoading && styles.btnDisabled]}
              onPress={handleConfirm}
              disabled={isLoading}
              activeOpacity={0.85}
            >
              <Text style={styles.deleteBtnText}>{isLoading ? 'Deleting...' : 'Permanently Delete Account'}</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.resendBtn}
              onPress={() => { setOtp(''); setErrorMsg(''); requestDeleteOtp(email.trim(), password); }}
              disabled={isLoading}
            >
              <Text style={styles.resendText}>Resend Code</Text>
            </TouchableOpacity>
          </View>
        )}

        {/* Step: Success */}
        {step === 'success' && (
          <View style={styles.successCard}>
            <Text style={styles.successText}>
              All your data has been permanently removed from our servers. You will be redirected to login.
            </Text>
          </View>
        )}

        <View style={{ height: 40 }} />
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.background },

  header: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 20,
    paddingTop: 56,
    backgroundColor: Colors.surface,
    borderBottomWidth: 1,
    borderBottomColor: Colors.border,
  },
  backBtn: {
    width: 40,
    height: 40,
    borderRadius: 12,
    backgroundColor: Colors.primaryLight,
    alignItems: 'center',
    justifyContent: 'center',
    marginRight: 12,
  },
  backArrow: { fontSize: 18, color: Colors.primary, fontWeight: '700' },
  headerCenter: { flex: 1 },
  headerTitle: { fontSize: 20, fontWeight: '800', color: Colors.textPrimary },

  content: { padding: 20, alignItems: 'center', gap: 16 },

  iconCircle: {
    width: 88,
    height: 88,
    borderRadius: 44,
    backgroundColor: Colors.rose + '20',
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: 8,
  },
  iconCircleSuccess: { backgroundColor: Colors.secondary + '20' },
  iconEmoji: { fontSize: 40 },

  title: { fontSize: 22, fontWeight: '900', color: Colors.textPrimary, textAlign: 'center' },
  subtitle: { fontSize: 13, color: Colors.textSecondary, textAlign: 'center', lineHeight: 20, paddingHorizontal: 8 },

  errorBanner: {
    backgroundColor: Colors.rose + '20',
    borderRadius: 12,
    padding: 12,
    borderWidth: 1,
    borderColor: Colors.rose + '40',
    width: '100%',
  },
  errorText: { color: Colors.rose, fontSize: 13, fontWeight: '600' },

  card: {
    backgroundColor: Colors.surface,
    borderRadius: 24,
    padding: 20,
    width: '100%',
    gap: 4,
    borderWidth: 1,
    borderColor: Colors.border,
  },
  inputLabel: { fontSize: 12, fontWeight: '700', color: Colors.textSecondary, marginBottom: 6, textTransform: 'uppercase', letterSpacing: 0.5 },
  input: {
    backgroundColor: Colors.surfaceElevated,
    borderRadius: 12,
    padding: 14,
    fontSize: 15,
    color: Colors.textPrimary,
    borderWidth: 1,
    borderColor: Colors.border,
  },
  passwordRow: { flexDirection: 'row', alignItems: 'center' },
  eyeBtn: { width: 48, height: 48, alignItems: 'center', justifyContent: 'center' },
  eyeText: { fontSize: 20 },

  deleteBtn: {
    backgroundColor: Colors.rose,
    borderRadius: 14,
    padding: 16,
    alignItems: 'center',
    marginTop: 16,
  },
  btnDisabled: { opacity: 0.6 },
  deleteBtnText: { color: '#FFFFFF', fontSize: 15, fontWeight: '800' },

  otpInput: {
    fontSize: 28,
    fontWeight: '900',
    textAlign: 'center',
    letterSpacing: 12,
    color: Colors.rose,
    backgroundColor: Colors.rose + '10',
    borderColor: Colors.rose + '50',
  },
  otpHint: { fontSize: 11, color: Colors.textMuted, textAlign: 'center', marginTop: 4 },
  resendBtn: { alignItems: 'center', padding: 12 },
  resendText: { color: Colors.textMuted, fontSize: 13, fontWeight: '600' },

  warningBox: {
    backgroundColor: Colors.rose + '10',
    borderRadius: 16,
    padding: 16,
    width: '100%',
    borderWidth: 1,
    borderColor: Colors.rose + '30',
    gap: 6,
  },
  warningTitle: { fontSize: 13, fontWeight: '700', color: Colors.rose, marginBottom: 4 },
  warningItem: { fontSize: 13, color: Colors.textSecondary, lineHeight: 20 },

  successCard: {
    backgroundColor: Colors.secondary + '15',
    borderRadius: 20,
    padding: 24,
    width: '100%',
    borderWidth: 1,
    borderColor: Colors.secondary + '40',
    alignItems: 'center',
  },
  successText: { fontSize: 14, color: Colors.textSecondary, textAlign: 'center', lineHeight: 22 },
});
