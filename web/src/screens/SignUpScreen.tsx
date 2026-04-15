import React, { useState, useEffect } from 'react';
import {
  View, Text, TextInput, TouchableOpacity, StyleSheet,
  ActivityIndicator, KeyboardAvoidingView, ScrollView, Alert,
} from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { useAuth } from '../context/AuthContext';

type Props = NativeStackScreenProps<RootStackParamList, 'SignUp'>;

const genders = ['Male', 'Female', 'Other', 'Prefer not to say'];
const languages = ['English', 'Tamil', 'Telugu', 'Hindi'];

export default function SignUpScreen({ navigation }: Props) {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [age, setAge] = useState('');
  const [gender, setGender] = useState('');
  const [lang, setLang] = useState('English');
  const [showPass, setShowPass] = useState(false);
  const [focusedField, setFocusedField] = useState<string | null>(null);

  const { register, registerState, resetStates } = useAuth();

  useEffect(() => {
    if (registerState.status === 'success') {
      resetStates();
      navigation.navigate('OtpVerification', { email: email.trim().toLowerCase(), type: 'register' });
    } else if (registerState.status === 'error') {
      Alert.alert('Registration Failed', registerState.message);
      resetStates();
    }
  }, [registerState.status]);

  function handleRegister() {
    if (!name || !email || !password || !age || !gender) {
      Alert.alert('Error', 'Please fill in all fields');
      return;
    }
    if (password !== confirmPassword) {
      Alert.alert('Error', 'Passwords do not match');
      return;
    }
    if (isNaN(parseInt(age)) || parseInt(age) < 1 || parseInt(age) > 120) {
      Alert.alert('Error', 'Please enter a valid age');
      return;
    }
    register(name.trim(), email.trim().toLowerCase(), password, parseInt(age), gender, lang);
  }

  const focused = (field: string) => focusedField === field;

  return (
    <KeyboardAvoidingView style={{ flex: 1 }} behavior="padding">
      <ScrollView
        contentContainerStyle={styles.container}
        keyboardShouldPersistTaps="handled"
        showsVerticalScrollIndicator={false}
      >
        {/* Header */}
        <View style={styles.pageHeader}>
          <TouchableOpacity style={styles.backBtn} onPress={() => navigation.goBack()}>
            <Text style={styles.backArrow}>←</Text>
          </TouchableOpacity>
          <View>
            <Text style={styles.pageTitle}>Create Account</Text>
            <Text style={styles.pageSubtitle}>Join ORCare for better oral health</Text>
          </View>
        </View>

        {/* Step badge */}
        <View style={styles.stepBadge}>
          <Text style={styles.stepText}>🦷 Personal Information</Text>
        </View>

        {/* Form */}
        <View style={styles.card}>
          {/* Name */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Full Name</Text>
            <View style={[styles.inputWrap, focused('name') && styles.inputWrapFocused]}>
              <Text style={styles.inputIcon}>👤</Text>
              <TextInput
                style={styles.input}
                placeholder="John Doe"
                placeholderTextColor={Colors.textMuted}
                value={name}
                onChangeText={setName}
                onFocus={() => setFocusedField('name')}
                onBlur={() => setFocusedField(null)}
              />
            </View>
          </View>

          {/* Email */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Email Address</Text>
            <View style={[styles.inputWrap, focused('email') && styles.inputWrapFocused]}>
              <Text style={styles.inputIcon}>📧</Text>
              <TextInput
                style={styles.input}
                placeholder="you@example.com"
                placeholderTextColor={Colors.textMuted}
                value={email}
                onChangeText={setEmail}
                keyboardType="email-address"
                autoCapitalize="none"
                onFocus={() => setFocusedField('email')}
                onBlur={() => setFocusedField(null)}
              />
            </View>
          </View>

          {/* Age + Gender */}
          <View style={styles.row}>
            <View style={[styles.inputGroup, { flex: 1 }]}>
              <Text style={styles.label}>Age</Text>
              <View style={[styles.inputWrap, focused('age') && styles.inputWrapFocused]}>
                <Text style={styles.inputIcon}>🎂</Text>
                <TextInput
                  style={styles.input}
                  placeholder="25"
                  placeholderTextColor={Colors.textMuted}
                  value={age}
                  onChangeText={setAge}
                  keyboardType="numeric"
                  onFocus={() => setFocusedField('age')}
                  onBlur={() => setFocusedField(null)}
                />
              </View>
            </View>
          </View>

          {/* Gender */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Gender</Text>
            <View style={styles.chipRow}>
              {genders.map((g) => (
                <TouchableOpacity
                  key={g}
                  style={[styles.chip, gender === g && styles.chipActive]}
                  onPress={() => setGender(g)}
                >
                  <Text style={[styles.chipText, gender === g && styles.chipTextActive]}>{g}</Text>
                </TouchableOpacity>
              ))}
            </View>
          </View>

          {/* Language */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Preferred Language</Text>
            <View style={styles.chipRow}>
              {languages.map((l) => (
                <TouchableOpacity
                  key={l}
                  style={[styles.chip, lang === l && styles.chipActive]}
                  onPress={() => setLang(l)}
                >
                  <Text style={[styles.chipText, lang === l && styles.chipTextActive]}>{l}</Text>
                </TouchableOpacity>
              ))}
            </View>
          </View>
        </View>

        <View style={styles.stepBadge}>
          <Text style={styles.stepText}>🔒 Security</Text>
        </View>

        <View style={styles.card}>
          {/* Password */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Password</Text>
            <View style={[styles.inputWrap, focused('password') && styles.inputWrapFocused]}>
              <Text style={styles.inputIcon}>🔒</Text>
              <TextInput
                style={[styles.input, { flex: 1 }]}
                placeholder="Minimum 6 characters"
                placeholderTextColor={Colors.textMuted}
                value={password}
                onChangeText={setPassword}
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
            <Text style={styles.label}>Confirm Password</Text>
            <View style={[styles.inputWrap, focused('confirm') && styles.inputWrapFocused]}>
              <Text style={styles.inputIcon}>✅</Text>
              <TextInput
                style={styles.input}
                placeholder="Re-enter password"
                placeholderTextColor={Colors.textMuted}
                value={confirmPassword}
                onChangeText={setConfirmPassword}
                secureTextEntry={!showPass}
                onFocus={() => setFocusedField('confirm')}
                onBlur={() => setFocusedField(null)}
              />
            </View>
          </View>
        </View>

        <TouchableOpacity
          style={[styles.submitBtn, registerState.status === 'loading' && styles.btnDisabled]}
          onPress={handleRegister}
          disabled={registerState.status === 'loading'}
        >
          {registerState.status === 'loading' ? (
            <ActivityIndicator color={Colors.textInverse} />
          ) : (
            <Text style={styles.submitBtnText}>Create Account →</Text>
          )}
        </TouchableOpacity>

        <View style={styles.signinRow}>
          <Text style={styles.signinText}>Already have an account? </Text>
          <TouchableOpacity onPress={() => navigation.navigate('SignIn')}>
            <Text style={styles.signinLink}>Sign In</Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  container: { flexGrow: 1, backgroundColor: Colors.background, padding: 20, paddingTop: 56, paddingBottom: 32 },

  pageHeader: { flexDirection: 'row', alignItems: 'center', gap: 16, marginBottom: 24 },
  backBtn: {
    width: 40,
    height: 40,
    borderRadius: 12,
    backgroundColor: Colors.surface,
    borderWidth: 1,
    borderColor: Colors.border,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },
  backArrow: { fontSize: 18, color: Colors.primary, fontWeight: '700' },
  pageTitle: { fontSize: 22, fontWeight: '800', color: Colors.textPrimary },
  pageSubtitle: { fontSize: 13, color: Colors.textSecondary, marginTop: 2 },

  stepBadge: {
    backgroundColor: Colors.primaryLight,
    borderRadius: 10,
    paddingHorizontal: 14,
    paddingVertical: 7,
    alignSelf: 'flex-start',
    marginBottom: 12,
    marginTop: 4,
  },
  stepText: { fontSize: 12, fontWeight: '700', color: Colors.primary },

  card: {
    backgroundColor: Colors.surface,
    borderRadius: 20,
    padding: 20,
    borderWidth: 1,
    borderColor: Colors.border,
    gap: 16,
    marginBottom: 16,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 1,
    shadowRadius: 8,
    elevation: 2,
  },

  row: { flexDirection: 'row', gap: 12 },
  inputGroup: { gap: 6 },
  label: { fontSize: 12, fontWeight: '700', color: Colors.textPrimary, textTransform: 'uppercase', letterSpacing: 0.5 },

  inputWrap: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.background,
    borderRadius: 12,
    borderWidth: 1.5,
    borderColor: Colors.border,
    paddingHorizontal: 12,
    gap: 8,
  },
  inputWrapFocused: { borderColor: Colors.primary, backgroundColor: 'rgba(255,140,66,0.10)' },
  inputIcon: { fontSize: 15 },
  input: { flex: 1, paddingVertical: 12, fontSize: 14, color: Colors.textPrimary },
  eyeBtn: { padding: 4 },
  eyeIcon: { fontSize: 16 },

  chipRow: { flexDirection: 'row', flexWrap: 'wrap', gap: 8 },
  chip: {
    paddingHorizontal: 14,
    paddingVertical: 8,
    borderRadius: 20,
    backgroundColor: Colors.background,
    borderWidth: 1.5,
    borderColor: Colors.border,
  },
  chipActive: { backgroundColor: Colors.primaryLight, borderColor: Colors.primary },
  chipText: { fontSize: 13, color: Colors.textSecondary, fontWeight: '500' },
  chipTextActive: { color: Colors.primary, fontWeight: '700' },

  submitBtn: {
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
  submitBtnText: { color: Colors.textInverse, fontSize: 16, fontWeight: '800' },

  signinRow: { flexDirection: 'row', justifyContent: 'center' },
  signinText: { color: Colors.textSecondary, fontSize: 14 },
  signinLink: { color: Colors.primary, fontSize: 14, fontWeight: '700' },
});
