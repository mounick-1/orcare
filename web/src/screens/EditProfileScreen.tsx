import React, { useState, useEffect } from 'react';
import {
  View, Text, StyleSheet, TextInput, TouchableOpacity,
  ScrollView, ActivityIndicator, Alert,
} from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { useAuth } from '../context/AuthContext';

type Props = NativeStackScreenProps<RootStackParamList, 'EditProfile'>;

const languages = ['English', 'Tamil', 'Telugu', 'Hindi'];

export default function EditProfileScreen({ navigation }: Props) {
  const { user, updateProfile, profileUpdateState, resetStates } = useAuth();
  const [name, setName] = useState(user?.name ?? '');
  const [district, setDistrict] = useState(user?.district ?? '');
  const [state, setState] = useState(user?.state ?? '');
  const [lang, setLang] = useState(user?.language ?? 'English');
  const [focusedField, setFocusedField] = useState<string | null>(null);

  useEffect(() => {
    if (profileUpdateState.status === 'success') {
      Alert.alert('Success', 'Profile updated successfully!');
      resetStates();
      navigation.goBack();
    } else if (profileUpdateState.status === 'error') {
      Alert.alert('Error', profileUpdateState.message);
      resetStates();
    }
  }, [profileUpdateState.status]);

  function handleSave() {
    if (!name.trim()) {
      Alert.alert('Error', 'Name cannot be empty');
      return;
    }
    updateProfile({ name: name.trim(), language: lang, district, state });
  }

  const focused = (f: string) => focusedField === f;

  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity style={styles.backBtn} onPress={() => navigation.goBack()}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <View style={styles.headerText}>
          <Text style={styles.title}>Edit Profile</Text>
          <Text style={styles.subtitle}>Update your information</Text>
        </View>
      </View>

      <ScrollView
        contentContainerStyle={styles.content}
        keyboardShouldPersistTaps="handled"
        showsVerticalScrollIndicator={false}
      >
        {/* Avatar Hero */}
        <View style={styles.avatarSection}>
          <View style={styles.avatarRing}>
            <View style={styles.avatar}>
              <Text style={styles.avatarText}>{(user?.name ?? 'G')[0].toUpperCase()}</Text>
            </View>
          </View>
          <Text style={styles.avatarName}>{user?.name ?? 'Guest'}</Text>
          <Text style={styles.avatarEmail}>{user?.email ?? ''}</Text>
        </View>

        {/* Personal Info Card */}
        <View style={styles.sectionCard}>
          <Text style={styles.sectionLabel}>Personal Information</Text>

          <View style={styles.inputGroup}>
            <Text style={styles.label}>Full Name *</Text>
            <View style={[styles.inputWrap, focused('name') && styles.inputWrapFocused]}>
              <Text style={styles.inputIcon}>👤</Text>
              <TextInput
                style={styles.input}
                value={name}
                onChangeText={setName}
                placeholder="Your name"
                placeholderTextColor={Colors.textMuted}
                onFocus={() => setFocusedField('name')}
                onBlur={() => setFocusedField(null)}
              />
            </View>
          </View>

          <View style={styles.inputGroup}>
            <Text style={styles.label}>Email Address</Text>
            <View style={[styles.inputWrap, styles.inputWrapDisabled]}>
              <Text style={styles.inputIcon}>📧</Text>
              <TextInput
                style={[styles.input, styles.inputDisabled]}
                value={user?.email ?? ''}
                editable={false}
                placeholderTextColor={Colors.textMuted}
              />
              <Text style={styles.lockIcon}>🔒</Text>
            </View>
            <Text style={styles.hint}>Email address cannot be changed</Text>
          </View>
        </View>

        {/* Location Card */}
        <View style={styles.sectionCard}>
          <Text style={styles.sectionLabel}>Location</Text>

          <View style={styles.inputGroup}>
            <Text style={styles.label}>District</Text>
            <View style={[styles.inputWrap, focused('district') && styles.inputWrapFocused]}>
              <Text style={styles.inputIcon}>🏙️</Text>
              <TextInput
                style={styles.input}
                value={district}
                onChangeText={setDistrict}
                placeholder="Your district"
                placeholderTextColor={Colors.textMuted}
                onFocus={() => setFocusedField('district')}
                onBlur={() => setFocusedField(null)}
              />
            </View>
          </View>

          <View style={styles.inputGroup}>
            <Text style={styles.label}>State</Text>
            <View style={[styles.inputWrap, focused('state') && styles.inputWrapFocused]}>
              <Text style={styles.inputIcon}>🗺️</Text>
              <TextInput
                style={styles.input}
                value={state}
                onChangeText={setState}
                placeholder="Your state"
                placeholderTextColor={Colors.textMuted}
                onFocus={() => setFocusedField('state')}
                onBlur={() => setFocusedField(null)}
              />
            </View>
          </View>
        </View>

        {/* Language Card */}
        <View style={styles.sectionCard}>
          <Text style={styles.sectionLabel}>Preferred Language</Text>
          <View style={styles.chipRow}>
            {languages.map((l) => (
              <TouchableOpacity
                key={l}
                style={[styles.chip, lang === l && styles.chipActive]}
                onPress={() => setLang(l)}
              >
                <Text style={styles.chipEmoji}>🌐</Text>
                <Text style={[styles.chipText, lang === l && styles.chipTextActive]}>{l}</Text>
                {lang === l && <Text style={styles.chipCheck}>✓</Text>}
              </TouchableOpacity>
            ))}
          </View>
        </View>

        {/* Save Button */}
        <TouchableOpacity
          style={[styles.saveBtn, profileUpdateState.status === 'loading' && styles.btnDisabled]}
          onPress={handleSave}
          disabled={profileUpdateState.status === 'loading'}
        >
          {profileUpdateState.status === 'loading' ? (
            <ActivityIndicator color={Colors.textInverse} />
          ) : (
            <Text style={styles.saveBtnText}>Save Changes ✓</Text>
          )}
        </TouchableOpacity>

        <View style={{ height: 32 }} />
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
    gap: 14,
  },
  backBtn: {
    width: 40,
    height: 40,
    borderRadius: 12,
    backgroundColor: Colors.primaryLight,
    alignItems: 'center',
    justifyContent: 'center',
  },
  backArrow: { fontSize: 18, color: Colors.primary, fontWeight: '700' },
  headerText: { flex: 1 },
  title: { fontSize: 20, fontWeight: '800', color: Colors.textPrimary },
  subtitle: { fontSize: 12, color: Colors.textSecondary, marginTop: 2 },

  content: { padding: 20, gap: 16 },

  avatarSection: { alignItems: 'center', paddingVertical: 20 },
  avatarRing: {
    width: 88,
    height: 88,
    borderRadius: 44,
    borderWidth: 3,
    borderColor: Colors.primaryLight,
    padding: 3,
    marginBottom: 12,
  },
  avatar: {
    flex: 1,
    borderRadius: 38,
    backgroundColor: Colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
  },
  avatarText: { color: Colors.textInverse, fontSize: 30, fontWeight: '900' },
  avatarName: { fontSize: 18, fontWeight: '700', color: Colors.textPrimary },
  avatarEmail: { fontSize: 13, color: Colors.textSecondary, marginTop: 2 },

  sectionCard: {
    backgroundColor: Colors.surface,
    borderRadius: 20,
    padding: 20,
    borderWidth: 1,
    borderColor: Colors.border,
    gap: 14,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 1,
    shadowRadius: 6,
    elevation: 2,
  },
  sectionLabel: {
    fontSize: 11,
    fontWeight: '800',
    color: Colors.textMuted,
    textTransform: 'uppercase',
    letterSpacing: 0.8,
  },

  inputGroup: { gap: 6 },
  label: { fontSize: 13, fontWeight: '600', color: Colors.textPrimary },
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
  inputWrapFocused: { borderColor: Colors.primary, backgroundColor: 'rgba(135,116,225,0.12)' },
  inputWrapDisabled: { backgroundColor: Colors.surfaceElevated },
  inputIcon: { fontSize: 16 },
  input: { flex: 1, paddingVertical: 12, fontSize: 14, color: Colors.textPrimary },
  inputDisabled: { color: Colors.textMuted },
  lockIcon: { fontSize: 14 },
  hint: { fontSize: 11, color: Colors.textMuted },

  chipRow: { flexDirection: 'row', flexWrap: 'wrap', gap: 8 },
  chip: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 6,
    paddingHorizontal: 14,
    paddingVertical: 9,
    borderRadius: 20,
    backgroundColor: Colors.background,
    borderWidth: 1.5,
    borderColor: Colors.border,
  },
  chipActive: { backgroundColor: Colors.primaryLight, borderColor: Colors.primary },
  chipEmoji: { fontSize: 14 },
  chipText: { fontSize: 13, color: Colors.textSecondary, fontWeight: '500' },
  chipTextActive: { color: Colors.primary, fontWeight: '700' },
  chipCheck: { fontSize: 12, color: Colors.primary, fontWeight: '800' },

  saveBtn: {
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
  saveBtnText: { color: Colors.textInverse, fontSize: 16, fontWeight: '800' },
});
