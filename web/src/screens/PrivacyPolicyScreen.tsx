import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { ArrowLeft, Shield } from 'lucide-react-native';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';

type Props = NativeStackScreenProps<RootStackParamList, 'PrivacyPolicy'>;

const sections = [
  {
    title: 'Information We Collect',
    icon: '📋',
    body: 'We collect information you provide directly, including your name, email address, age, gender, and preferred language when you register. We also collect usage data such as features you interact with, questions you ask the AI chatbot, and reminders you create.',
  },
  {
    title: 'How We Use Your Information',
    icon: '🎯',
    body: 'Your information is used to:\n• Provide and personalise the ORCare experience\n• Power the AI chatbot responses (via Google Gemini API)\n• Send reminder notifications you configure\n• Improve our services and content\n• Respond to your feedback and support requests',
  },
  {
    title: 'Data Storage & Security',
    icon: '🔒',
    body: 'Your data is stored securely on our servers with industry-standard encryption. Passwords are hashed and never stored in plain text. We use HTTPS for all data transmission. We do not sell your personal data to third parties.',
  },
  {
    title: 'Third-Party Services',
    icon: '🤝',
    body: 'ORCare uses Google Gemini AI to power the chatbot. Messages sent to the chatbot are processed by Google\'s API. Please review Google\'s privacy policy for details on how they handle AI conversation data. We do not use advertising networks or sell your data.',
  },
  {
    title: 'Your Rights',
    icon: '✅',
    body: 'You have the right to:\n• Access your personal data\n• Correct inaccurate information\n• Delete your account and all associated data\n• Export your data on request\n\nTo exercise these rights, contact us via Help & Feedback or delete your account in Profile settings.',
  },
  {
    title: 'Data Retention',
    icon: '🗂️',
    body: 'We retain your data for as long as your account is active. When you delete your account, all personal data is permanently removed within 30 days. Anonymised, aggregated usage statistics may be retained for service improvement.',
  },
  {
    title: 'Children\'s Privacy',
    icon: '👶',
    body: 'ORCare is not intended for children under 13 years of age. We do not knowingly collect personal information from children under 13. If you believe a child has provided us with their information, please contact us immediately.',
  },
  {
    title: 'Changes to This Policy',
    icon: '📝',
    body: 'We may update this Privacy Policy from time to time. We will notify you of significant changes through the app. Continued use of ORCare after changes constitutes your acceptance of the updated policy.',
  },
  {
    title: 'Contact Us',
    icon: '📬',
    body: 'If you have questions about this Privacy Policy or our data practices, please reach out via:\n• In-app: Profile → Help & Feedback\n• Email: privacy@orcare.app\n\nWe aim to respond to all privacy inquiries within 72 hours.',
  },
];

export default function PrivacyPolicyScreen({ navigation }: Props) {
  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity style={styles.backBtn} onPress={() => navigation.goBack()}>
          <ArrowLeft size={20} color={Colors.primary} strokeWidth={2.5} />
        </TouchableOpacity>
        <View style={styles.headerText}>
          <Text style={styles.title}>Privacy Policy</Text>
          <Text style={styles.subtitle}>Last updated: January 2025</Text>
        </View>
      </View>

      <ScrollView
        contentContainerStyle={styles.content}
        showsVerticalScrollIndicator={false}
      >
        {/* Hero */}
        <View style={styles.hero}>
          <View style={styles.heroIconRing}>
            <View style={styles.heroIconBox}>
              <Shield size={34} color={Colors.textInverse} strokeWidth={1.75} />
            </View>
          </View>
          <Text style={styles.heroTitle}>Your Privacy Matters</Text>
          <Text style={styles.heroSub}>
            ORCare is committed to protecting your personal information. This policy explains what we collect, how we use it, and your rights.
          </Text>
        </View>

        {/* Policy Sections */}
        {sections.map((section, i) => (
          <View key={i} style={styles.section}>
            <View style={styles.sectionHeader}>
              <View style={styles.sectionIconBox}>
                <Text style={styles.sectionIcon}>{section.icon}</Text>
              </View>
              <Text style={styles.sectionTitle}>{section.title}</Text>
            </View>
            <Text style={styles.sectionBody}>{section.body}</Text>
          </View>
        ))}

        {/* Footer note */}
        <View style={styles.footerNote}>
          <Text style={styles.footerText}>
            ORCare Web v1.0.0 · © 2025 ORCare. All rights reserved.
          </Text>
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

  hero: { alignItems: 'center', paddingVertical: 16, gap: 10 },
  heroIconRing: {
    width: 88,
    height: 88,
    borderRadius: 44,
    backgroundColor: Colors.accentLight,
    alignItems: 'center',
    justifyContent: 'center',
    borderWidth: 3,
    borderColor: Colors.accent + '40',
    marginBottom: 4,
  },
  heroIconBox: {
    width: 68,
    height: 68,
    borderRadius: 34,
    backgroundColor: Colors.accent,
    alignItems: 'center',
    justifyContent: 'center',
  },
  heroEmoji: { fontSize: 34 },
  heroTitle: { fontSize: 22, fontWeight: '800', color: Colors.textPrimary },
  heroSub: {
    fontSize: 13,
    color: Colors.textSecondary,
    textAlign: 'center',
    lineHeight: 20,
    paddingHorizontal: 8,
  },

  section: {
    backgroundColor: Colors.surface,
    borderRadius: 18,
    padding: 18,
    borderWidth: 1,
    borderColor: Colors.border,
    gap: 10,
  },
  sectionHeader: { flexDirection: 'row', alignItems: 'center', gap: 10 },
  sectionIconBox: {
    width: 36,
    height: 36,
    borderRadius: 10,
    backgroundColor: Colors.primaryLight,
    alignItems: 'center',
    justifyContent: 'center',
  },
  sectionIcon: { fontSize: 18 },
  sectionTitle: { fontSize: 15, fontWeight: '700', color: Colors.textPrimary, flex: 1 },
  sectionBody: {
    fontSize: 13,
    color: Colors.textSecondary,
    lineHeight: 22,
  },

  footerNote: { alignItems: 'center', paddingTop: 8 },
  footerText: { fontSize: 11, color: Colors.textMuted, textAlign: 'center' },
});
