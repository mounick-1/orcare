import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, Alert } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { useAuth } from '../context/AuthContext';

type Props = NativeStackScreenProps<RootStackParamList, 'PrivacySecurity'>;

export default function PrivacySecurityScreen({ navigation }: Props) {
  const { user, deleteAccount } = useAuth();

  const securityItems = [
    {
      icon: '🔑',
      label: 'Change Password',
      sub: 'Reset via email verification',
      color: Colors.primary,
      bgColor: Colors.primaryLight,
      onPress: () =>
        navigation.navigate('ForgotPassword'),
    },
    {
      icon: '📧',
      label: 'Email Address',
      sub: user?.email ?? 'Not set',
      color: Colors.secondary,
      bgColor: Colors.secondaryLight,
      onPress: () => Alert.alert('Email Address', 'Your email cannot be changed for security reasons.'),
    },
  ];

  const privacyItems = [
    {
      icon: '📄',
      label: 'Privacy Policy',
      sub: 'Read our full privacy policy',
      color: Colors.accent,
      bgColor: Colors.accentLight,
      onPress: () => navigation.navigate('PrivacyPolicy'),
    },
    {
      icon: '🍪',
      label: 'Data Usage',
      sub: 'We never sell your personal data',
      color: Colors.violet,
      bgColor: Colors.violetLight,
      onPress: () =>
        Alert.alert(
          'Data Usage',
          'ORCare uses your data only to provide personalised oral health guidance. We do not share or sell your data to third parties. AI conversations are processed by Google Gemini API.'
        ),
    },
    {
      icon: '📊',
      label: 'Analytics',
      sub: 'Anonymous usage analytics only',
      color: Colors.amber,
      bgColor: Colors.amberLight,
      onPress: () =>
        Alert.alert(
          'Analytics',
          'We collect anonymous, aggregated usage data to improve the app. No personally identifiable information is included in analytics.'
        ),
    },
  ];

  function handleDeleteAccount() {
    Alert.alert(
      'Delete Account',
      'This will permanently delete your account and all associated data. This action cannot be undone.',
      [
        { text: 'Cancel', style: 'cancel' },
        {
          text: 'Delete Account',
          style: 'destructive',
          onPress: deleteAccount,
        },
      ]
    );
  }

  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity style={styles.backBtn} onPress={() => navigation.goBack()}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <View style={styles.headerText}>
          <Text style={styles.title}>Privacy & Security</Text>
          <Text style={styles.subtitle}>Manage your account security</Text>
        </View>
      </View>

      <ScrollView contentContainerStyle={styles.content} showsVerticalScrollIndicator={false}>
        {/* Status Banner */}
        <View style={styles.statusBanner}>
          <View style={styles.statusIcon}>
            <Text style={styles.statusEmoji}>🛡️</Text>
          </View>
          <View style={styles.statusText}>
            <Text style={styles.statusTitle}>Account Secured</Text>
            <Text style={styles.statusSub}>Your data is encrypted and protected</Text>
          </View>
          <View style={styles.statusBadge}>
            <Text style={styles.statusBadgeText}>✓ Safe</Text>
          </View>
        </View>

        {/* Security Section */}
        <Text style={styles.sectionLabel}>Account Security</Text>
        <View style={styles.card}>
          {securityItems.map((item, i) => (
            <TouchableOpacity
              key={item.label}
              style={[styles.menuItem, i < securityItems.length - 1 && styles.menuBorder]}
              onPress={item.onPress}
              activeOpacity={0.7}
            >
              <View style={[styles.menuIconBox, { backgroundColor: item.bgColor }]}>
                <Text style={styles.menuIcon}>{item.icon}</Text>
              </View>
              <View style={styles.menuText}>
                <Text style={[styles.menuLabel, { color: item.color }]}>{item.label}</Text>
                <Text style={styles.menuSub}>{item.sub}</Text>
              </View>
              <Text style={styles.menuArrow}>›</Text>
            </TouchableOpacity>
          ))}
        </View>

        {/* Privacy Section */}
        <Text style={styles.sectionLabel}>Privacy</Text>
        <View style={styles.card}>
          {privacyItems.map((item, i) => (
            <TouchableOpacity
              key={item.label}
              style={[styles.menuItem, i < privacyItems.length - 1 && styles.menuBorder]}
              onPress={item.onPress}
              activeOpacity={0.7}
            >
              <View style={[styles.menuIconBox, { backgroundColor: item.bgColor }]}>
                <Text style={styles.menuIcon}>{item.icon}</Text>
              </View>
              <View style={styles.menuText}>
                <Text style={styles.menuLabel}>{item.label}</Text>
                <Text style={styles.menuSub}>{item.sub}</Text>
              </View>
              <Text style={styles.menuArrow}>›</Text>
            </TouchableOpacity>
          ))}
        </View>

        {/* Tips */}
        <View style={styles.tipBox}>
          <Text style={styles.tipTitle}>💡 Security Tips</Text>
          {[
            'Use a strong, unique password for ORCare',
            'Never share your OTP with anyone',
            'Log out from shared or public devices',
          ].map((tip, i) => (
            <View key={i} style={styles.tipRow}>
              <Text style={styles.tipDot}>•</Text>
              <Text style={styles.tipText}>{tip}</Text>
            </View>
          ))}
        </View>

        {/* Danger Zone */}
        <Text style={styles.sectionLabel}>Danger Zone</Text>
        <View style={[styles.card, styles.dangerCard]}>
          <View style={styles.dangerInfo}>
            <Text style={styles.dangerTitle}>Delete Account</Text>
            <Text style={styles.dangerSub}>
              Permanently removes your account and all data. Cannot be undone.
            </Text>
          </View>
          <TouchableOpacity style={styles.dangerBtn} onPress={handleDeleteAccount}>
            <Text style={styles.dangerBtnText}>🗑️ Delete Account</Text>
          </TouchableOpacity>
        </View>

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

  statusBanner: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.accentLight,
    borderRadius: 18,
    padding: 16,
    borderWidth: 1,
    borderColor: Colors.accent + '40',
    gap: 12,
  },
  statusIcon: {
    width: 44,
    height: 44,
    borderRadius: 22,
    backgroundColor: Colors.accent + '30',
    alignItems: 'center',
    justifyContent: 'center',
  },
  statusEmoji: { fontSize: 22 },
  statusText: { flex: 1 },
  statusTitle: { fontSize: 15, fontWeight: '700', color: Colors.accent },
  statusSub: { fontSize: 12, color: Colors.textSecondary, marginTop: 2 },
  statusBadge: {
    backgroundColor: Colors.accent + '25',
    borderRadius: 20,
    paddingHorizontal: 10,
    paddingVertical: 4,
    borderWidth: 1,
    borderColor: Colors.accent + '50',
  },
  statusBadgeText: { fontSize: 11, fontWeight: '700', color: Colors.accent },

  sectionLabel: {
    fontSize: 11,
    fontWeight: '800',
    color: Colors.textMuted,
    textTransform: 'uppercase',
    letterSpacing: 0.8,
    marginTop: 4,
  },

  card: {
    backgroundColor: Colors.surface,
    borderRadius: 18,
    borderWidth: 1,
    borderColor: Colors.border,
    overflow: 'hidden',
  },
  menuItem: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 16,
    gap: 14,
  },
  menuBorder: { borderBottomWidth: 1, borderBottomColor: Colors.border },
  menuIconBox: {
    width: 40,
    height: 40,
    borderRadius: 12,
    alignItems: 'center',
    justifyContent: 'center',
  },
  menuIcon: { fontSize: 20 },
  menuText: { flex: 1 },
  menuLabel: { fontSize: 15, fontWeight: '600', color: Colors.textPrimary },
  menuSub: { fontSize: 12, color: Colors.textSecondary, marginTop: 1 },
  menuArrow: { fontSize: 24, color: Colors.textMuted },

  tipBox: {
    backgroundColor: Colors.primaryLight,
    borderRadius: 16,
    padding: 16,
    gap: 8,
  },
  tipTitle: { fontSize: 13, fontWeight: '700', color: Colors.primary, marginBottom: 2 },
  tipRow: { flexDirection: 'row', gap: 8 },
  tipDot: { fontSize: 13, color: Colors.primary },
  tipText: { flex: 1, fontSize: 13, color: Colors.textSecondary, lineHeight: 20 },

  dangerCard: { borderColor: Colors.rose + '50' },
  dangerInfo: { padding: 16, paddingBottom: 8 },
  dangerTitle: { fontSize: 15, fontWeight: '700', color: Colors.rose },
  dangerSub: { fontSize: 12, color: Colors.textSecondary, marginTop: 4, lineHeight: 18 },
  dangerBtn: {
    margin: 16,
    marginTop: 4,
    padding: 14,
    borderRadius: 12,
    borderWidth: 1.5,
    borderColor: Colors.rose,
    alignItems: 'center',
  },
  dangerBtnText: { color: Colors.rose, fontSize: 14, fontWeight: '700' },
});
