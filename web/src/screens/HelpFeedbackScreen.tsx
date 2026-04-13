import React, { useState } from 'react';
import {
  View, Text, StyleSheet, TextInput, TouchableOpacity,
  ScrollView, ActivityIndicator, Alert, KeyboardAvoidingView,
} from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { useAuth } from '../context/AuthContext';
import { submitFeedback } from '../api/apiService';

type Props = NativeStackScreenProps<RootStackParamList, 'HelpFeedback'>;

const topics = ['General Question', 'Bug Report', 'Feature Request', 'Account Issue', 'Other'];

const faqs = [
  {
    q: 'How do I reset my password?',
    a: 'Go to Sign In → Forgot Password, enter your email, and follow the OTP instructions.',
  },
  {
    q: 'How does the AI chatbot work?',
    a: 'The AI is powered by Google Gemini and specialises in oral health questions. It provides guidance but is not a substitute for professional dental care.',
  },
  {
    q: 'Can I change my language?',
    a: 'Yes. Go to Profile → Edit Profile and choose your preferred language.',
  },
  {
    q: 'How do I set up reminders?',
    a: 'Navigate to Profile → Reminders and tap the + button to create brushing, flossing, or mouthwash reminders.',
  },
];

export default function HelpFeedbackScreen({ navigation }: Props) {
  const { user } = useAuth();
  const [topic, setTopic] = useState('');
  const [message, setMessage] = useState('');
  const [focused, setFocused] = useState(false);
  const [loading, setLoading] = useState(false);
  const [expandedFaq, setExpandedFaq] = useState<number | null>(null);

  async function handleSubmit() {
    if (!topic) {
      Alert.alert('Missing Topic', 'Please select a topic for your feedback.');
      return;
    }
    if (!message.trim() || message.trim().length < 10) {
      Alert.alert('Message Too Short', 'Please write at least 10 characters.');
      return;
    }

    setLoading(true);
    try {
      const fullMessage = `[${topic}] ${message.trim()}`;
      await submitFeedback(
        user?.name ?? 'Anonymous',
        user?.email ?? 'no-email@orcare.app',
        fullMessage
      );
      Alert.alert('Feedback Sent!', "Thank you! We'll review your message and get back to you if needed.", [
        { text: 'OK', onPress: () => navigation.goBack() },
      ]);
    } catch {
      Alert.alert('Failed to Send', 'Please check your connection and try again.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <KeyboardAvoidingView style={{ flex: 1 }} behavior="padding">
      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity style={styles.backBtn} onPress={() => navigation.goBack()}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <View style={styles.headerText}>
          <Text style={styles.title}>Help & Feedback</Text>
          <Text style={styles.subtitle}>We'd love to hear from you</Text>
        </View>
      </View>

      <ScrollView
        contentContainerStyle={styles.content}
        keyboardShouldPersistTaps="handled"
        showsVerticalScrollIndicator={false}
      >
        {/* Hero */}
        <View style={styles.hero}>
          <View style={styles.heroIconRing}>
            <View style={styles.heroIconBox}>
              <Text style={styles.heroEmoji}>💬</Text>
            </View>
          </View>
          <Text style={styles.heroTitle}>How can we help?</Text>
          <Text style={styles.heroSub}>Browse FAQs or send us a message</Text>
        </View>

        {/* FAQ Section */}
        <Text style={styles.sectionTitle}>Frequently Asked Questions</Text>
        <View style={styles.card}>
          {faqs.map((faq, i) => (
            <TouchableOpacity
              key={i}
              style={[styles.faqItem, i < faqs.length - 1 && styles.faqBorder]}
              onPress={() => setExpandedFaq(expandedFaq === i ? null : i)}
              activeOpacity={0.7}
            >
              <View style={styles.faqRow}>
                <Text style={styles.faqQ}>{faq.q}</Text>
                <Text style={styles.faqChevron}>{expandedFaq === i ? '▲' : '▼'}</Text>
              </View>
              {expandedFaq === i && (
                <Text style={styles.faqA}>{faq.a}</Text>
              )}
            </TouchableOpacity>
          ))}
        </View>

        {/* Feedback Form */}
        <Text style={styles.sectionTitle}>Send Feedback</Text>
        <View style={styles.card}>
          {/* Topic */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Topic *</Text>
            <View style={styles.chipRow}>
              {topics.map((t) => (
                <TouchableOpacity
                  key={t}
                  style={[styles.chip, topic === t && styles.chipActive]}
                  onPress={() => setTopic(t)}
                >
                  <Text style={[styles.chipText, topic === t && styles.chipTextActive]}>{t}</Text>
                </TouchableOpacity>
              ))}
            </View>
          </View>

          {/* Sender info (read only) */}
          {user && (
            <View style={styles.senderBox}>
              <Text style={styles.senderIcon}>👤</Text>
              <View>
                <Text style={styles.senderName}>{user.name}</Text>
                <Text style={styles.senderEmail}>{user.email}</Text>
              </View>
            </View>
          )}

          {/* Message */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Message *</Text>
            <View style={[styles.textAreaWrap, focused && styles.textAreaFocused]}>
              <TextInput
                style={styles.textArea}
                placeholder="Describe your question or feedback in detail..."
                placeholderTextColor={Colors.textMuted}
                value={message}
                onChangeText={setMessage}
                multiline
                numberOfLines={5}
                textAlignVertical="top"
                onFocus={() => setFocused(true)}
                onBlur={() => setFocused(false)}
              />
            </View>
            <Text style={styles.charCount}>{message.length} characters</Text>
          </View>

          <TouchableOpacity
            style={[styles.submitBtn, loading && styles.btnDisabled]}
            onPress={handleSubmit}
            disabled={loading}
          >
            {loading ? (
              <ActivityIndicator color={Colors.textInverse} />
            ) : (
              <Text style={styles.submitBtnText}>Send Feedback ✦</Text>
            )}
          </TouchableOpacity>
        </View>

        {/* Contact note */}
        <View style={styles.noteBox}>
          <Text style={styles.noteIcon}>ℹ️</Text>
          <Text style={styles.noteText}>
            For urgent issues, responses are typically within 24–48 hours.
          </Text>
        </View>

        <View style={{ height: 32 }} />
      </ScrollView>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
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

  hero: { alignItems: 'center', paddingVertical: 16, gap: 8 },
  heroIconRing: {
    width: 88,
    height: 88,
    borderRadius: 44,
    backgroundColor: Colors.secondaryLight,
    alignItems: 'center',
    justifyContent: 'center',
    borderWidth: 3,
    borderColor: Colors.secondary + '40',
    marginBottom: 4,
  },
  heroIconBox: {
    width: 68,
    height: 68,
    borderRadius: 34,
    backgroundColor: Colors.secondary,
    alignItems: 'center',
    justifyContent: 'center',
  },
  heroEmoji: { fontSize: 34 },
  heroTitle: { fontSize: 20, fontWeight: '800', color: Colors.textPrimary },
  heroSub: { fontSize: 13, color: Colors.textSecondary },

  sectionTitle: {
    fontSize: 12,
    fontWeight: '700',
    color: Colors.textMuted,
    textTransform: 'uppercase',
    letterSpacing: 0.8,
    marginTop: 4,
  },

  card: {
    backgroundColor: Colors.surface,
    borderRadius: 20,
    padding: 20,
    borderWidth: 1,
    borderColor: Colors.border,
    gap: 16,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 1,
    shadowRadius: 8,
    elevation: 2,
  },

  faqItem: { paddingVertical: 12 },
  faqBorder: { borderBottomWidth: 1, borderBottomColor: Colors.border },
  faqRow: { flexDirection: 'row', alignItems: 'center', gap: 8 },
  faqQ: { flex: 1, fontSize: 14, fontWeight: '600', color: Colors.textPrimary },
  faqChevron: { fontSize: 10, color: Colors.textMuted },
  faqA: { fontSize: 13, color: Colors.textSecondary, lineHeight: 20, marginTop: 8 },

  inputGroup: { gap: 8 },
  label: {
    fontSize: 12,
    fontWeight: '700',
    color: Colors.textPrimary,
    textTransform: 'uppercase',
    letterSpacing: 0.5,
  },

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
  chipText: { fontSize: 12, color: Colors.textSecondary, fontWeight: '500' },
  chipTextActive: { color: Colors.primary, fontWeight: '700' },

  senderBox: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.primaryLight,
    borderRadius: 12,
    padding: 12,
    gap: 10,
  },
  senderIcon: { fontSize: 20 },
  senderName: { fontSize: 14, fontWeight: '700', color: Colors.primary },
  senderEmail: { fontSize: 12, color: Colors.textSecondary, marginTop: 1 },

  textAreaWrap: {
    backgroundColor: Colors.background,
    borderRadius: 14,
    borderWidth: 1.5,
    borderColor: Colors.border,
    padding: 12,
  },
  textAreaFocused: { borderColor: Colors.primary, backgroundColor: 'rgba(135,116,225,0.08)' },
  textArea: {
    fontSize: 14,
    color: Colors.textPrimary,
    minHeight: 120,
  },
  charCount: { fontSize: 11, color: Colors.textMuted, textAlign: 'right' },

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
  btnDisabled: { opacity: 0.6 },
  submitBtnText: { color: Colors.textInverse, fontSize: 16, fontWeight: '800' },

  noteBox: {
    flexDirection: 'row',
    backgroundColor: Colors.primaryLight,
    borderRadius: 12,
    padding: 14,
    gap: 10,
    alignItems: 'flex-start',
  },
  noteIcon: { fontSize: 16 },
  noteText: { flex: 1, fontSize: 12, color: Colors.primary, lineHeight: 18, fontWeight: '500' },
});
