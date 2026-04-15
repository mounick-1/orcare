import React, { useState, useEffect } from 'react';
import {
  View, Text, TextInput, TouchableOpacity, StyleSheet,
  ActivityIndicator, Alert, KeyboardAvoidingView, ScrollView,
} from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { useAuth } from '../context/AuthContext';

type Props = NativeStackScreenProps<RootStackParamList, 'ResetPassword'>;

export default function ResetPasswordScreen({ navigation, route }: Props) {
  const { email, otp } = route.params;
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showPass, setShowPass] = useState(false);
  const [focusedField, setFocusedField] = useState<string | null>(null);
  const { resetPassword, forgotState, resetStates } = useAuth();

  useEffect(() => {
    if (forgotState.status === 'success') {
      resetStates();
      Alert.alert('Password Reset!', 'Your password has been reset successfully. Please sign in with your new password.', [
        { text: 'Sign In', onPress: () => navigation.navigate('SignIn') },
      ]);
    } else if (forgotState.status === 'error') {
      Alert.alert('Error', forgotState.message);
      resetStates();
    }
  }, [forgotState.status]);

  function handleReset() {
    if (!newPassword || !confirmPassword) {
      Alert.alert('Error', 'Please fill in all fields.');
      return;
    }
    if (newPassword.length < 6) {
      Alert.alert('Error', 'Password must be at least 6 characters.');
      return;
    }
    if (newPassword !== confirmPassword) {
      Alert.alert('Error', 'Passwords do not match.');
      return;
    }
    resetPassword(email, otp, newPassword);
  }

  const focused = (f: string) => focusedField === f;

  return (
    <KeyboardAvoidingView style={{ flex: 1 }} behavior="padding">
      <ScrollView
        contentContainerStyle={styles.container}
        keyboardShouldPersistTaps="handled"
        showsVerticalScrollIndicator={false}
      >
        {/* Back */}
        <TouchableOpacity style={styles.backBtn} onPress={() => navigation.goBack()}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>

        {/* Hero */}
        <View style={styles.hero}>
          <View style={styles.iconRing}>
            <View style={styles.iconBox}>
              <Text style={styles.iconEmoji}>🔐</Text>
            </View>
          </View>
          <Text style={styles.title}>Create New Password</Text>
          <Text style={styles.subtitle}>
            Choose a strong password for your account
          </Text>
          <View style={styles.emailPill}>
            <Text style={styles.emailText}>{email}</Text>
          </View>
        </View>

        {/* Form Card */}
        <View style={styles.card}>
          {/* Password strength hint */}
          <View style={styles.hintBox}>
            <Text style={styles.hintIcon}>💡</Text>
            <Text style={styles.hintText}>
              Use at least 6 characters with a mix of letters, numbers, and symbols.
            </Text>
          </View>

          {/* New Password */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>New Password</Text>
            <View style={[styles.inputWrap, focused('password') && styles.inputWrapFocused]}>
              <Text style={styles.inputIcon}>🔒</Text>
              <TextInput
                style={[styles.input, { flex: 1 }]}
                placeholder="Minimum 6 characters"
                placeholderTextColor={Colors.textMuted}
                value={newPassword}
                onChangeText={setNewPassword}
                secureTextEntry={!showPass}
                onFocus={() => setFocusedField('password')}
                onBlur={() => setFocusedField(null)}
              />
              <TouchableOpacity onPress={() => setShowPass(!showPass)} style={styles.eyeBtn}>
                <Text style={styles.eyeIcon}>{showPass ? '🙈' : '👁️'}</Text>
              </TouchableOpacity>
            </View>
          </View>

          {/* Confirm Password */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Confirm New Password</Text>
            <View style={[styles.inputWrap, focused('confirm') && styles.inputWrapFocused]}>
              <Text style={styles.inputIcon}>✅</Text>
              <TextInput
                style={styles.input}
                placeholder="Re-enter new password"
                placeholderTextColor={Colors.textMuted}
                value={confirmPassword}
                onChangeText={setConfirmPassword}
                secureTextEntry={!showPass}
                onFocus={() => setFocusedField('confirm')}
                onBlur={() => setFocusedField(null)}
              />
            </View>
            {confirmPassword.length > 0 && (
              <Text style={[styles.matchText, newPassword === confirmPassword ? styles.matchOk : styles.matchFail]}>
                {newPassword === confirmPassword ? '✓ Passwords match' : '✗ Passwords do not match'}
              </Text>
            )}
          </View>

          <TouchableOpacity
            style={[styles.submitBtn, forgotState.status === 'loading' && styles.btnDisabled]}
            onPress={handleReset}
            disabled={forgotState.status === 'loading'}
          >
            {forgotState.status === 'loading' ? (
              <ActivityIndicator color={Colors.textInverse} />
            ) : (
              <Text style={styles.submitBtnText}>Reset Password ✓</Text>
            )}
          </TouchableOpacity>
        </View>
      </ScrollView>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    backgroundColor: Colors.background,
    padding: 24,
    paddingTop: 56,
    paddingBottom: 32,
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

  hero: { alignItems: 'center', marginBottom: 28, gap: 8 },
  iconRing: {
    width: 104,
    height: 104,
    borderRadius: 52,
    backgroundColor: Colors.primaryLight,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 12,
    borderWidth: 3,
    borderColor: Colors.primary + '40',
  },
  iconBox: {
    width: 80,
    height: 80,
    borderRadius: 40,
    backgroundColor: Colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
  },
  iconEmoji: { fontSize: 40 },
  title: { fontSize: 26, fontWeight: '800', color: Colors.textPrimary },
  subtitle: { fontSize: 14, color: Colors.textSecondary, textAlign: 'center' },
  emailPill: {
    backgroundColor: Colors.primaryLight,
    borderRadius: 20,
    paddingHorizontal: 16,
    paddingVertical: 6,
    marginTop: 4,
  },
  emailText: { color: Colors.primary, fontWeight: '700', fontSize: 13 },

  card: {
    backgroundColor: Colors.surface,
    borderRadius: 24,
    padding: 24,
    borderWidth: 1,
    borderColor: Colors.border,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 1,
    shadowRadius: 16,
    elevation: 4,
    gap: 16,
  },

  hintBox: {
    flexDirection: 'row',
    backgroundColor: Colors.primaryLight,
    borderRadius: 12,
    padding: 12,
    gap: 10,
    alignItems: 'flex-start',
  },
  hintIcon: { fontSize: 16 },
  hintText: { flex: 1, fontSize: 12, color: Colors.primary, lineHeight: 18, fontWeight: '500' },

  inputGroup: { gap: 6 },
  label: {
    fontSize: 13,
    fontWeight: '700',
    color: Colors.textPrimary,
    textTransform: 'uppercase',
    letterSpacing: 0.5,
  },
  inputWrap: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.background,
    borderRadius: 14,
    borderWidth: 1.5,
    borderColor: Colors.border,
    paddingHorizontal: 14,
    gap: 10,
  },
  inputWrapFocused: { borderColor: Colors.primary, backgroundColor: 'rgba(255,140,66,0.10)' },
  inputIcon: { fontSize: 16 },
  input: { flex: 1, paddingVertical: 14, fontSize: 15, color: Colors.textPrimary },
  eyeBtn: { padding: 4 },
  eyeIcon: { fontSize: 16 },

  matchText: { fontSize: 12, fontWeight: '600', marginTop: 2 },
  matchOk: { color: Colors.accent },
  matchFail: { color: Colors.rose },

  submitBtn: {
    backgroundColor: Colors.primary,
    borderRadius: 14,
    padding: 16,
    alignItems: 'center',
    shadowColor: Colors.primary,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 8,
  },
  btnDisabled: { opacity: 0.7 },
  submitBtnText: { color: Colors.textInverse, fontSize: 16, fontWeight: '800' },
});
