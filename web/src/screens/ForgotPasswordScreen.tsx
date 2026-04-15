import React, { useState, useEffect } from 'react';
import {
  View, Text, TextInput, TouchableOpacity, StyleSheet,
  ActivityIndicator, Alert, KeyboardAvoidingView, ScrollView,
} from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { ArrowLeft, Lock, Mail, Info } from 'lucide-react-native';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { useAuth } from '../context/AuthContext';

type Props = NativeStackScreenProps<RootStackParamList, 'ForgotPassword'>;

export default function ForgotPasswordScreen({ navigation }: Props) {
  const [email, setEmail] = useState('');
  const [focused, setFocused] = useState(false);
  const { forgotPassword, forgotState, resetStates } = useAuth();

  useEffect(() => {
    if (forgotState.status === 'success') {
      resetStates();
      Alert.alert('Email Sent', 'Check your inbox for the reset code.', [
        {
          text: 'OK',
          onPress: () =>
            navigation.navigate('OtpVerification', {
              email: email.trim().toLowerCase(),
              type: 'forgot',
            }),
        },
      ]);
    } else if (forgotState.status === 'error') {
      Alert.alert('Error', forgotState.message);
      resetStates();
    }
  }, [forgotState.status]);

  function handleSubmit() {
    if (!email.trim()) {
      Alert.alert('Error', 'Please enter your email address');
      return;
    }
    forgotPassword(email.trim().toLowerCase());
  }

  return (
    <KeyboardAvoidingView style={{ flex: 1 }} behavior="padding">
      <ScrollView
        contentContainerStyle={styles.container}
        keyboardShouldPersistTaps="handled"
        showsVerticalScrollIndicator={false}
      >
        {/* Back */}
        <TouchableOpacity style={styles.backBtn} onPress={() => navigation.goBack()}>
          <ArrowLeft size={20} color={Colors.primary} strokeWidth={2.5} />
        </TouchableOpacity>

        {/* Hero */}
        <View style={styles.hero}>
          <View style={styles.iconRing}>
            <View style={styles.iconBox}>
              <Lock size={40} color={Colors.textInverse} strokeWidth={1.75} />
            </View>
          </View>
          <Text style={styles.title}>Forgot Password?</Text>
          <Text style={styles.subtitle}>
            No worries! Enter your email and we'll send you a reset code.
          </Text>
        </View>

        {/* Form Card */}
        <View style={styles.card}>
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Email Address</Text>
            <View style={[styles.inputWrap, focused && styles.inputWrapFocused]}>
              <Mail size={16} color={Colors.textMuted} strokeWidth={1.75} />
              <TextInput
                style={styles.input}
                placeholder="you@example.com"
                placeholderTextColor={Colors.textMuted}
                value={email}
                onChangeText={setEmail}
                keyboardType="email-address"
                autoCapitalize="none"
                onFocus={() => setFocused(true)}
                onBlur={() => setFocused(false)}
              />
            </View>
          </View>

          <TouchableOpacity
            style={[styles.submitBtn, forgotState.status === 'loading' && styles.btnDisabled]}
            onPress={handleSubmit}
            disabled={forgotState.status === 'loading'}
          >
            {forgotState.status === 'loading' ? (
              <ActivityIndicator color={Colors.textInverse} />
            ) : (
              <Text style={styles.submitBtnText}>Send Reset Code →</Text>
            )}
          </TouchableOpacity>

          <View style={styles.infoBox}>
            <Info size={16} color={Colors.primary} strokeWidth={2} />
            <Text style={styles.infoText}>
              Check your spam folder if you don't see the email within a few minutes.
            </Text>
          </View>
        </View>

        <TouchableOpacity style={styles.backLink} onPress={() => navigation.goBack()}>
          <Text style={styles.backLinkText}>← Back to Sign In</Text>
        </TouchableOpacity>
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

  hero: { alignItems: 'center', marginBottom: 32 },
  iconRing: {
    width: 104,
    height: 104,
    borderRadius: 52,
    backgroundColor: Colors.amberLight,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 20,
    borderWidth: 3,
    borderColor: Colors.amber + '40',
  },
  iconBox: {
    width: 80,
    height: 80,
    borderRadius: 40,
    backgroundColor: Colors.amber,
    alignItems: 'center',
    justifyContent: 'center',
  },
  iconEmoji: { fontSize: 40 },
  title: { fontSize: 26, fontWeight: '800', color: Colors.textPrimary, marginBottom: 10 },
  subtitle: {
    fontSize: 14,
    color: Colors.textSecondary,
    textAlign: 'center',
    lineHeight: 22,
    paddingHorizontal: 8,
  },

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
    marginBottom: 24,
  },

  inputGroup: { gap: 6 },
  label: { fontSize: 13, fontWeight: '700', color: Colors.textPrimary, textTransform: 'uppercase', letterSpacing: 0.5 },
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
  inputWrapFocused: { borderColor: Colors.primary, backgroundColor: 'rgba(37,99,235,0.08)' },
  inputIcon: { fontSize: 16 },
  input: {
    flex: 1,
    paddingVertical: 14,
    fontSize: 15,
    color: Colors.textPrimary,
  },

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

  infoBox: {
    flexDirection: 'row',
    backgroundColor: Colors.primaryLight,
    borderRadius: 12,
    padding: 12,
    gap: 10,
    alignItems: 'flex-start',
  },
  infoIcon: { fontSize: 16 },
  infoText: { flex: 1, fontSize: 12, color: Colors.primary, lineHeight: 18, fontWeight: '500' },

  backLink: { alignItems: 'center' },
  backLinkText: { color: Colors.primary, fontSize: 14, fontWeight: '700' },
});
