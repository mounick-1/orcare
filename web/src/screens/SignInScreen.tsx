import React, { useState, useEffect } from 'react';
import {
  View, Text, TextInput, TouchableOpacity, StyleSheet,
  ActivityIndicator, KeyboardAvoidingView, ScrollView, Alert,
} from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { Mail, Lock, Eye, EyeOff } from 'lucide-react-native';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { useAuth } from '../context/AuthContext';

type Props = NativeStackScreenProps<RootStackParamList, 'SignIn'>;

export default function SignInScreen({ navigation }: Props) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPass, setShowPass] = useState(false);
  const [focusedField, setFocusedField] = useState<string | null>(null);
  const { login, loginState, resetStates } = useAuth();

  useEffect(() => {
    if (loginState.status === 'success') {
      resetStates();
      navigation.replace('MainTabs');
    } else if (loginState.status === 'error') {
      Alert.alert('Login Failed', loginState.message);
      resetStates();
    }
  }, [loginState.status]);

  function handleLogin() {
    if (!email.trim() || !password) {
      Alert.alert('Error', 'Please enter email and password');
      return;
    }
    login(email.trim().toLowerCase(), password);
  }

  return (
    <KeyboardAvoidingView style={{ flex: 1 }} behavior="padding">
      <ScrollView
        contentContainerStyle={styles.container}
        keyboardShouldPersistTaps="handled"
        showsVerticalScrollIndicator={false}
      >
        {/* Hero */}
        <View style={styles.hero}>
          <View style={styles.logoRing}>
            <View style={styles.logoBox}>
              <Text style={styles.logoText}>✦</Text>
            </View>
          </View>
          <Text style={styles.appName}>ORCare</Text>
          <Text style={styles.heroSub}>Oral Health Companion</Text>
        </View>

        {/* Card */}
        <View style={styles.card}>
          <Text style={styles.cardTitle}>Welcome Back</Text>
          <Text style={styles.cardSubtitle}>Sign in to your account</Text>

          <View style={styles.form}>
            <View style={styles.inputGroup}>
              <Text style={styles.label}>Email Address</Text>
              <View style={[styles.inputWrap, focusedField === 'email' && styles.inputWrapFocused]}>
                <Mail size={16} color={focusedField === 'email' ? Colors.primary : Colors.textMuted} strokeWidth={2} />
                <TextInput
                  style={styles.input}
                  placeholder="you@example.com"
                  placeholderTextColor={Colors.textMuted}
                  value={email}
                  onChangeText={setEmail}
                  keyboardType="email-address"
                  autoCapitalize="none"
                  autoComplete="email"
                  onFocus={() => setFocusedField('email')}
                  onBlur={() => setFocusedField(null)}
                />
              </View>
            </View>

            <View style={styles.inputGroup}>
              <Text style={styles.label}>Password</Text>
              <View style={[styles.inputWrap, focusedField === 'password' && styles.inputWrapFocused]}>
                <Lock size={16} color={focusedField === 'password' ? Colors.primary : Colors.textMuted} strokeWidth={2} />
                <TextInput
                  style={[styles.input, { flex: 1 }]}
                  placeholder="Enter your password"
                  placeholderTextColor={Colors.textMuted}
                  value={password}
                  onChangeText={setPassword}
                  secureTextEntry={!showPass}
                  autoComplete="password"
                  onFocus={() => setFocusedField('password')}
                  onBlur={() => setFocusedField(null)}
                />
                <TouchableOpacity onPress={() => setShowPass(!showPass)} style={styles.eyeBtn}>
                  {showPass
                    ? <EyeOff size={18} color={Colors.textMuted} strokeWidth={2} />
                    : <Eye size={18} color={Colors.textMuted} strokeWidth={2} />
                  }
                </TouchableOpacity>
              </View>
            </View>

            <TouchableOpacity
              style={styles.forgotLink}
              onPress={() => navigation.navigate('ForgotPassword')}
            >
              <Text style={styles.forgotText}>Forgot Password?</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={[styles.loginBtn, loginState.status === 'loading' && styles.btnDisabled]}
              onPress={handleLogin}
              disabled={loginState.status === 'loading'}
            >
              {loginState.status === 'loading' ? (
                <ActivityIndicator color={Colors.textInverse} />
              ) : (
                <Text style={styles.loginBtnText}>Sign In →</Text>
              )}
            </TouchableOpacity>
          </View>
        </View>

        <View style={styles.dividerRow}>
          <View style={styles.divider} />
          <Text style={styles.dividerText}>New to ORCare?</Text>
          <View style={styles.divider} />
        </View>

        <TouchableOpacity
          style={styles.signupBtn}
          onPress={() => navigation.navigate('SignUp')}
        >
          <Text style={styles.signupBtnText}>Create an Account</Text>
        </TouchableOpacity>

        <Text style={styles.footer}>Powered by Google Gemini AI</Text>
      </ScrollView>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    backgroundColor: Colors.background,
    padding: 24,
    paddingTop: 64,
    paddingBottom: 32,
  },

  hero: { alignItems: 'center', marginBottom: 32 },
  logoRing: {
    width: 96,
    height: 96,
    borderRadius: 48,
    backgroundColor: Colors.primaryLight,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 14,
    borderWidth: 2,
    borderColor: Colors.primary + '30',
  },
  logoBox: {
    width: 72,
    height: 72,
    borderRadius: 36,
    backgroundColor: Colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
  },
  logoText: { fontSize: 36, color: Colors.textInverse },
  appName: { fontSize: 30, fontFamily: 'Inter_800ExtraBold', color: Colors.textPrimary, letterSpacing: -0.5 },
  heroSub: { fontSize: 13, color: Colors.textMuted, marginTop: 4, fontFamily: 'Inter_400Regular' },

  card: {
    backgroundColor: Colors.surface,
    borderRadius: 20,
    padding: 24,
    borderWidth: 1,
    borderColor: Colors.border,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 1,
    shadowRadius: 16,
    elevation: 4,
    marginBottom: 20,
  },
  cardTitle: { fontSize: 22, fontFamily: 'Inter_800ExtraBold', color: Colors.textPrimary, marginBottom: 4 },
  cardSubtitle: { fontSize: 13, color: Colors.textSecondary, fontFamily: 'Inter_400Regular', marginBottom: 24 },

  form: { gap: 16 },
  inputGroup: { gap: 6 },
  label: { fontSize: 12, fontFamily: 'Inter_600SemiBold', color: Colors.textPrimary, textTransform: 'uppercase', letterSpacing: 0.5 },
  inputWrap: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.background,
    borderRadius: 12,
    borderWidth: 1.5,
    borderColor: Colors.border,
    paddingHorizontal: 14,
    gap: 10,
  },
  inputWrapFocused: { borderColor: Colors.primary, backgroundColor: Colors.primaryLight },
  input: {
    flex: 1,
    paddingVertical: 13,
    fontSize: 14,
    color: Colors.textPrimary,
    fontFamily: 'Inter_400Regular',
  },
  eyeBtn: { padding: 4 },

  forgotLink: { alignSelf: 'flex-end', marginTop: -8 },
  forgotText: { color: Colors.primary, fontSize: 13, fontFamily: 'Inter_600SemiBold' },

  loginBtn: {
    backgroundColor: Colors.primary,
    borderRadius: 12,
    padding: 15,
    alignItems: 'center',
    marginTop: 4,
    shadowColor: Colors.shadow,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 1,
    shadowRadius: 12,
  },
  btnDisabled: { opacity: 0.7 },
  loginBtnText: { color: Colors.textInverse, fontSize: 15, fontFamily: 'Inter_700Bold' },

  dividerRow: { flexDirection: 'row', alignItems: 'center', gap: 12, marginBottom: 16 },
  divider: { flex: 1, height: 1, backgroundColor: Colors.border },
  dividerText: { fontSize: 12, color: Colors.textMuted, fontFamily: 'Inter_500Medium' },

  signupBtn: {
    borderWidth: 1.5,
    borderColor: Colors.border,
    borderRadius: 12,
    padding: 14,
    alignItems: 'center',
    backgroundColor: Colors.surface,
  },
  signupBtnText: { color: Colors.textPrimary, fontSize: 14, fontFamily: 'Inter_600SemiBold' },

  footer: { textAlign: 'center', color: Colors.textMuted, fontSize: 11, marginTop: 24, fontFamily: 'Inter_400Regular' },
});
