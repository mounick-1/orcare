import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { Colors } from '../theme/colors';
import { useAuth } from '../context/AuthContext';
import { getDailyTip } from '../data/tipData';
import { RootStackParamList } from '../navigation/AppNavigator';

type Nav = NativeStackNavigationProp<RootStackParamList>;

const quickActions = [
  { emoji: '🔍', label: 'Symptom Checker', sub: 'Check your symptoms', route: 'SymptomChecker', bg: Colors.primaryLight, fg: Colors.primary },
  { emoji: '🤖', label: 'AI Chatbot', sub: 'Ask ORCare AI', route: 'Chatbot', bg: Colors.secondaryLight, fg: Colors.secondary },
  { emoji: '🔔', label: 'Reminders', sub: 'Daily hygiene alerts', route: 'Reminders', bg: Colors.amberLight, fg: Colors.amber },
  { emoji: '💡', label: 'Daily Tips', sub: 'Oral health facts', route: 'DailyTips', bg: Colors.accentLight, fg: Colors.accent },
];

const facts = [
  { emoji: '🦠', text: 'Your mouth has over 700 species of bacteria. Proper cleaning keeps harmful ones in check.' },
  { emoji: '⏱️', text: 'Brushing for just 2 minutes twice a day can prevent 80% of cavities.' },
  { emoji: '🧵', text: 'Flossing reaches 40% of tooth surfaces that a brush cannot clean.' },
];

export default function HomeScreen() {
  const navigation = useNavigation<Nav>();
  const { user } = useAuth();
  const dailyTip = getDailyTip();
  const hour = new Date().getHours();
  const greeting = hour < 12 ? 'Good Morning' : hour < 17 ? 'Good Afternoon' : 'Good Evening';
  const fact = facts[new Date().getDate() % facts.length];

  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.content} showsVerticalScrollIndicator={false}>

      {/* ── Header ── */}
      <View style={styles.header}>
        <View style={styles.headerLeft}>
          <Text style={styles.greeting}>{greeting} 👋</Text>
          <Text style={styles.userName}>{user?.name ?? 'there'}</Text>
        </View>
        <View style={styles.avatarRing}>
          <View style={styles.avatar}>
            <Text style={styles.avatarText}>{(user?.name ?? 'G')[0].toUpperCase()}</Text>
          </View>
        </View>
      </View>

      {/* ── Health Score Banner ── */}
      <View style={styles.scoreBanner}>
        <View style={styles.scoreLeft}>
          <Text style={styles.scoreLabel}>Oral Health Score</Text>
          <Text style={styles.scoreValue}>Good</Text>
          <Text style={styles.scoreHint}>Keep up your routine!</Text>
        </View>
        <View style={styles.scoreCircle}>
          <Text style={styles.scoreEmoji}>🦷</Text>
          <Text style={styles.scoreNum}>82</Text>
        </View>
      </View>

      {/* ── Quick Actions ── */}
      <Text style={styles.sectionTitle}>Quick Actions</Text>
      <View style={styles.actionsGrid}>
        {quickActions.map((a) => (
          <TouchableOpacity
            key={a.label}
            style={[styles.actionCard, { backgroundColor: a.bg }]}
            onPress={() => navigation.navigate(a.route as any)}
            activeOpacity={0.8}
          >
            <View style={[styles.actionIconBox, { backgroundColor: a.fg + '22' }]}>
              <Text style={styles.actionEmoji}>{a.emoji}</Text>
            </View>
            <Text style={[styles.actionLabel, { color: a.fg }]}>{a.label}</Text>
            <Text style={styles.actionSub}>{a.sub}</Text>
          </TouchableOpacity>
        ))}
      </View>

      {/* ── Daily Tip ── */}
      <Text style={styles.sectionTitle}>Today's Tip</Text>
      <TouchableOpacity
        style={styles.tipCard}
        onPress={() => navigation.navigate('DailyTips')}
        activeOpacity={0.9}
      >
        <View style={styles.tipTop}>
          <View style={styles.tipBadge}>
            <Text style={styles.tipBadgeText}>Day {new Date().getDate()}</Text>
          </View>
          <Text style={styles.tipEmoji}>{dailyTip.icon}</Text>
        </View>
        <Text style={styles.tipTitle}>{dailyTip.title}</Text>
        <Text style={styles.tipDesc}>{dailyTip.description}</Text>
        <View style={styles.tipFooter}>
          <View style={styles.tipCategoryPill}>
            <Text style={styles.tipCategoryText}>{dailyTip.category}</Text>
          </View>
          <Text style={styles.tipMore}>View all →</Text>
        </View>
      </TouchableOpacity>

      {/* ── Did You Know ── */}
      <Text style={styles.sectionTitle}>Did You Know?</Text>
      <View style={styles.factCard}>
        <Text style={styles.factEmoji}>{fact.emoji}</Text>
        <Text style={styles.factText}>{fact.text}</Text>
      </View>

      {/* ── Stats Row ── */}
      <Text style={styles.sectionTitle}>Your Progress</Text>
      <View style={styles.statsRow}>
        {[
          { emoji: '🔔', label: 'Reminders', value: '10', sub: 'Active' },
          { emoji: '📚', label: 'Modules', value: '24', sub: 'Available' },
          { emoji: '🦷', label: 'Diseases', value: '6', sub: 'In Database' },
        ].map((s) => (
          <View key={s.label} style={styles.statCard}>
            <Text style={styles.statEmoji}>{s.emoji}</Text>
            <Text style={styles.statValue}>{s.value}</Text>
            <Text style={styles.statLabel}>{s.label}</Text>
            <Text style={styles.statSub}>{s.sub}</Text>
          </View>
        ))}
      </View>

      <View style={{ height: 32 }} />
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.background },
  content: { paddingBottom: 16 },

  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 20,
    paddingTop: 56,
    paddingBottom: 20,
    backgroundColor: Colors.surface,
  },
  headerLeft: { gap: 2 },
  greeting: { fontSize: 13, color: Colors.textSecondary, fontWeight: '500' },
  userName: { fontSize: 24, fontWeight: '800', color: Colors.textPrimary },
  avatarRing: {
    width: 52,
    height: 52,
    borderRadius: 26,
    borderWidth: 2,
    borderColor: Colors.primaryLight,
    padding: 2,
  },
  avatar: {
    flex: 1,
    borderRadius: 22,
    backgroundColor: Colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
  },
  avatarText: { color: Colors.textInverse, fontSize: 18, fontWeight: '800' },

  scoreBanner: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    backgroundColor: Colors.primary,
    marginHorizontal: 20,
    marginTop: 20,
    borderRadius: 20,
    padding: 20,
  },
  scoreLeft: { gap: 4 },
  scoreLabel: { fontSize: 12, color: 'rgba(255,255,255,0.7)', fontWeight: '600', textTransform: 'uppercase', letterSpacing: 0.5 },
  scoreValue: { fontSize: 26, fontWeight: '800', color: Colors.textInverse },
  scoreHint: { fontSize: 12, color: 'rgba(255,255,255,0.7)' },
  scoreCircle: {
    width: 72,
    height: 72,
    borderRadius: 36,
    backgroundColor: 'rgba(255,255,255,0.15)',
    alignItems: 'center',
    justifyContent: 'center',
    borderWidth: 2,
    borderColor: 'rgba(255,255,255,0.3)',
  },
  scoreEmoji: { fontSize: 20 },
  scoreNum: { fontSize: 18, fontWeight: '800', color: Colors.textInverse },

  sectionTitle: {
    fontSize: 17,
    fontWeight: '700',
    color: Colors.textPrimary,
    paddingHorizontal: 20,
    marginTop: 24,
    marginBottom: 12,
  },

  actionsGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    paddingHorizontal: 20,
    gap: 12,
  },
  actionCard: {
    width: '47%',
    borderRadius: 20,
    padding: 18,
    gap: 10,
  },
  actionIconBox: {
    width: 44,
    height: 44,
    borderRadius: 14,
    alignItems: 'center',
    justifyContent: 'center',
  },
  actionEmoji: { fontSize: 22 },
  actionLabel: { fontSize: 14, fontWeight: '800' },
  actionSub: { fontSize: 11, color: Colors.textSecondary, lineHeight: 16 },

  tipCard: {
    backgroundColor: Colors.primary,
    marginHorizontal: 20,
    borderRadius: 20,
    padding: 20,
    gap: 10,
  },
  tipTop: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center' },
  tipBadge: {
    backgroundColor: 'rgba(255,255,255,0.2)',
    borderRadius: 20,
    paddingHorizontal: 10,
    paddingVertical: 4,
  },
  tipBadgeText: { color: Colors.textInverse, fontSize: 11, fontWeight: '700' },
  tipEmoji: { fontSize: 28 },
  tipTitle: { fontSize: 18, fontWeight: '800', color: Colors.textInverse },
  tipDesc: { fontSize: 14, color: 'rgba(255,255,255,0.85)', lineHeight: 22 },
  tipFooter: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginTop: 4 },
  tipCategoryPill: {
    backgroundColor: 'rgba(255,255,255,0.15)',
    borderRadius: 20,
    paddingHorizontal: 10,
    paddingVertical: 4,
  },
  tipCategoryText: { color: 'rgba(255,255,255,0.9)', fontSize: 11, fontWeight: '600' },
  tipMore: { color: 'rgba(255,255,255,0.7)', fontSize: 13, fontWeight: '600' },

  factCard: {
    flexDirection: 'row',
    backgroundColor: Colors.surface,
    marginHorizontal: 20,
    borderRadius: 18,
    padding: 18,
    gap: 14,
    alignItems: 'flex-start',
    borderWidth: 1,
    borderColor: Colors.border,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 1,
    shadowRadius: 8,
    elevation: 2,
  },
  factEmoji: { fontSize: 32 },
  factText: { flex: 1, fontSize: 14, color: Colors.textSecondary, lineHeight: 22 },

  statsRow: { flexDirection: 'row', paddingHorizontal: 20, gap: 12 },
  statCard: {
    flex: 1,
    backgroundColor: Colors.surface,
    borderRadius: 18,
    padding: 16,
    alignItems: 'center',
    gap: 2,
    borderWidth: 1,
    borderColor: Colors.border,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 1,
    shadowRadius: 8,
    elevation: 2,
  },
  statEmoji: { fontSize: 22, marginBottom: 4 },
  statValue: { fontSize: 22, fontWeight: '800', color: Colors.primary },
  statLabel: { fontSize: 11, fontWeight: '700', color: Colors.textPrimary },
  statSub: { fontSize: 10, color: Colors.textMuted },
});
