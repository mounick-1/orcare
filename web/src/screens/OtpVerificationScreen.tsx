import React, { useState, useEffect, useRef } from 'react';
import {
  View, Text, TextInput, TouchableOpacity, StyleSheet,
  ActivityIndicator, Alert,
} from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { ArrowLeft } from 'lucide-react-native';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { useAuth } from '../context/AuthContext';

type Props = NativeStackScreenProps<RootStackParamList, 'OtpVerification'>;

export default function OtpVerificationScreen({ navigation, route }: Props) {
  const { email, type } = route.params;
  const [otp, setOtp] = useState(['', '', '', '', '', '']);
  const [timer, setTimer] = useState(60);
  const refs = useRef<Array<TextInput | null>>([]);
  const { verifyOtp, resendOtp, otpState, resetStates } = useAuth();

  useEffect(() => {
    const interval = setInterval(() => {
      setTimer((t) => (t > 0 ? t - 1 : 0));
    }, 1000);
    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    if (otpState.status === 'success') {
      resetStates();
      if (type === 'register') {
        navigation.replace('Onboarding');
      } else if (type === 'forgot') {
        navigation.replace('ResetPassword', { email, otp: otp.join('') });
      } else {
        navigation.replace('SignIn');
      }
    } else if (otpState.status === 'error') {
      Alert.alert('Error', otpState.message);
      resetStates();
    }
  }, [otpState.status]);

  function handleChange(value: string, index: number) {
    const newOtp = [...otp];
    newOtp[index] = value.slice(-1);
    setOtp(newOtp);
    if (value && index < 5) {
      refs.current[index + 1]?.focus();
    }
  }

  function handleKeyPress(key: string, index: number) {
    if (key === 'Backspace' && !otp[index] && index > 0) {
      refs.current[index - 1]?.focus();
    }
  }

  function handleVerify() {
    const code = otp.join('');
    if (code.length < 6) {
      Alert.alert('Error', 'Please enter all 6 digits');
      return;
    }
    verifyOtp(email, code, type);
  }

  function handleResend() {
    if (timer === 0) {
      setTimer(60);
      setOtp(['', '', '', '', '', '']);
      resendOtp(email, type);
    }
  }

  const progress = (60 - timer) / 60;

  return (
    <View style={styles.container}>
      {/* Back */}
      <TouchableOpacity style={styles.backBtn} onPress={() => navigation.goBack()}>
        <ArrowLeft size={20} color={Colors.primary} strokeWidth={2.5} />
      </TouchableOpacity>

      {/* Hero */}
      <View style={styles.hero}>
        <View style={styles.iconRing}>
          <View style={styles.iconBox}>
            <Text style={styles.iconEmoji}>📧</Text>
          </View>
        </View>
        <Text style={styles.title}>Verify Your Email</Text>
        <Text style={styles.subtitle}>
          We sent a 6-digit code to
        </Text>
        <View style={styles.emailPill}>
          <Text style={styles.emailText}>{email}</Text>
        </View>
      </View>

      {/* OTP Boxes */}
      <View style={styles.otpRow}>
        {otp.map((digit, i) => (
          <TextInput
            key={i}
            ref={(r) => { refs.current[i] = r; }}
            style={[
              styles.otpBox,
              digit ? styles.otpBoxFilled : null,
            ]}
            value={digit}
            onChangeText={(v) => handleChange(v, i)}
            onKeyPress={({ nativeEvent }) => handleKeyPress(nativeEvent.key, i)}
            keyboardType="numeric"
            maxLength={1}
            selectTextOnFocus
          />
        ))}
      </View>

      {/* Timer */}
      <View style={styles.timerRow}>
        <View style={styles.timerBg}>
          <View style={[styles.timerFill, { width: `${progress * 100}%` as any }]} />
        </View>
        <Text style={styles.timerText}>
          {timer > 0 ? `Resend in ${timer}s` : 'Code expired'}
        </Text>
      </View>

      {/* Verify Button */}
      <TouchableOpacity
        style={[styles.verifyBtn, otpState.status === 'loading' && styles.btnDisabled]}
        onPress={handleVerify}
        disabled={otpState.status === 'loading'}
      >
        {otpState.status === 'loading' ? (
          <ActivityIndicator color={Colors.textInverse} />
        ) : (
          <Text style={styles.verifyBtnText}>Verify Code ✓</Text>
        )}
      </TouchableOpacity>

      {/* Resend */}
      <View style={styles.resendRow}>
        <Text style={styles.resendText}>Didn't receive the code? </Text>
        <TouchableOpacity onPress={handleResend} disabled={timer > 0}>
          <Text style={[styles.resendLink, timer > 0 && styles.resendLinkDisabled]}>
            Resend Code
          </Text>
        </TouchableOpacity>
      </View>

      {/* Info */}
      <View style={styles.infoBox}>
        <Text style={styles.infoIcon}>💡</Text>
        <Text style={styles.infoText}>
          Check your spam/junk folder if you don't see the email within a minute.
        </Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.background,
    padding: 24,
    paddingTop: 56,
  },

  backBtn: {
    width: 40,
    height: 40,
    borderRadius: 12,
    backgroundColor: Colors.surface,
    borderWidth: 1,
    borderColor: Colors.border,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 32,
  },
  backArrow: { fontSize: 18, color: Colors.primary, fontWeight: '700' },

  hero: { alignItems: 'center', marginBottom: 36 },
  iconRing: {
    width: 100,
    height: 100,
    borderRadius: 50,
    backgroundColor: Colors.primaryLight,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 20,
    borderWidth: 3,
    borderColor: Colors.primary + '30',
  },
  iconBox: {
    width: 76,
    height: 76,
    borderRadius: 38,
    backgroundColor: Colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
  },
  iconEmoji: { fontSize: 38 },
  title: { fontSize: 24, fontWeight: '800', color: Colors.textPrimary, marginBottom: 8 },
  subtitle: { fontSize: 14, color: Colors.textSecondary, marginBottom: 10 },
  emailPill: {
    backgroundColor: Colors.primaryLight,
    borderRadius: 20,
    paddingHorizontal: 16,
    paddingVertical: 6,
  },
  emailText: { color: Colors.primary, fontWeight: '700', fontSize: 14 },

  otpRow: {
    flexDirection: 'row',
    justifyContent: 'center',
    gap: 10,
    marginBottom: 24,
  },
  otpBox: {
    width: 50,
    height: 58,
    borderRadius: 14,
    borderWidth: 2,
    borderColor: Colors.border,
    backgroundColor: Colors.surface,
    textAlign: 'center',
    fontSize: 24,
    fontWeight: '800',
    color: Colors.textPrimary,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 1,
    shadowRadius: 4,
    elevation: 2,
  },
  otpBoxFilled: {
    borderColor: Colors.primary,
    backgroundColor: Colors.primaryLight,
    color: Colors.primary,
  },

  timerRow: { alignItems: 'center', gap: 8, marginBottom: 28 },
  timerBg: {
    width: '100%',
    height: 4,
    backgroundColor: Colors.borderLight,
    borderRadius: 2,
    overflow: 'hidden',
  },
  timerFill: {
    height: 4,
    backgroundColor: Colors.secondary,
    borderRadius: 2,
  },
  timerText: { fontSize: 13, color: Colors.textMuted, fontWeight: '600' },

  verifyBtn: {
    backgroundColor: Colors.primary,
    borderRadius: 14,
    padding: 16,
    alignItems: 'center',
    marginBottom: 20,
    shadowColor: Colors.primary,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 8,
  },
  btnDisabled: { opacity: 0.7 },
  verifyBtnText: { color: Colors.textInverse, fontSize: 16, fontWeight: '800' },

  resendRow: { flexDirection: 'row', justifyContent: 'center', marginBottom: 24 },
  resendText: { color: Colors.textSecondary, fontSize: 14 },
  resendLink: { color: Colors.primary, fontSize: 14, fontWeight: '700' },
  resendLinkDisabled: { color: Colors.textMuted },

  infoBox: {
    flexDirection: 'row',
    backgroundColor: Colors.primaryLight,
    borderRadius: 12,
    padding: 14,
    gap: 10,
    alignItems: 'flex-start',
  },
  infoIcon: { fontSize: 16 },
  infoText: { flex: 1, fontSize: 12, color: Colors.primary, lineHeight: 18, fontWeight: '500' },
});
