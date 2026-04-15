import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { Search, Bot, Bell, Lightbulb, GraduationCap, Stethoscope } from 'lucide-react-native';
import { Colors } from '../theme/colors';
import { useAuth } from '../context/AuthContext';
import { getDailyTip } from '../data/tipData';
import { RootStackParamList } from '../navigation/AppNavigator';

type Nav = NativeStackNavigationProp<RootStackParamList>;

const quickActions = [
  { Icon: Search, label: 'Symptom Checker', sub: 'Check your symptoms', route: 'SymptomChecker', bg: Colors.primaryLight, fg: Colors.primary },
  { Icon: Bot, label: 'AI Chatbot', sub: 'Ask ORCare AI', route: 'Chatbot', bg: Colors.successLight, fg: Colors.success },
  { Icon: Bell, label: 'Reminders', sub: 'Daily hygiene alerts', route: 'Reminders', bg: Colors.amberLight, fg: Colors.amber },
  { Icon: Lightbulb, label: 'Daily Tips', sub: 'Oral health facts', route: 'DailyTips', bg: Colors.accentLight, fg: Colors.accent },
];

const facts = [
  { emoji: '🦠', text: 'Your mouth has over 700 species of bacteria. Proper cleaning keeps harmful ones in check.' },
  { emoji: '⏱️', text: 'Brushing for just 2 minutes twice a day can prevent 80% of cavities.' },
  { emoji: '🧵', text: 'Flossing reaches 40% of tooth surfaces that a brush cannot clean.' },
];

const stats = [
  { Icon: Bell, label: 'Reminders', value: '10', sub: 'Active', fg: Colors.primary },
  { Icon: GraduationCap, label: 'Modules', value: '24', sub: 'Available', fg: Colors.success },
  { Icon: Stethoscope, label: 'Diseases', value: '6', sub: 'In Database', fg: Colors.accent },
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
            style={[styles.actionCard, { borderColor: a.fg + '30' }]}
            onPress={() => navigation.navigate(a.route as any)}
            activeOpacity={0.8}
          >
            <View style={[styles.actionIconBox, { backgroundColor: a.bg }]}>
              <a.Icon size={22} color={a.fg} strokeWidth={2} />
            </View>
            <Text style={[styles.actionLabel, { color: Colors.textPrimary }]}>{a.label}</Text>
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
        {stats.map((s) => (
          <View key={s.label} style={styles.statCard}>
            <View style={[styles.statIconBox, { backgroundColor: s.fg + '15' }]}>
              <s.Icon size={18} color={s.fg} strokeWidth={2} />
            </View>
            <Text style={[styles.statValue, { color: s.fg }]}>{s.value}</Text>
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
    borderBottomWidth: 1,
    borderBottomColor: Colors.border,
  },
  headerLeft: { gap: 2 },
  greeting: { fontSize: 13, color: Colors.textSecondary, fontFamily: 'Inter_500Medium' },
  userName: { fontSize: 24, fontFamily: 'Inter_800ExtraBold', color: Colors.textPrimary, letterSpacing: -0.5 },
  avatarRing: {
    width: 48,
    height: 48,
    borderRadius: 24,
    borderWidth: 2,
    borderColor: Colors.primary + '40',
    padding: 2,
  },
  avatar: {
    flex: 1,
    borderRadius: 20,
    backgroundColor: Colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
  },
  avatarText: { color: Colors.textInverse, fontSize: 16, fontFamily: 'Inter_700Bold' },

  scoreBanner: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    backgroundColor: Colors.primary,
    marginHorizontal: 20,
    marginTop: 20,
    borderRadius: 20,
    padding: 24,
  },
  scoreLeft: { gap: 4 },
  scoreLabel: { fontSize: 11, color: 'rgba(255,255,255,0.75)', fontFamily: 'Inter_600SemiBold', textTransform: 'uppercase', letterSpacing: 0.8 },
  scoreValue: { fontSize: 28, fontFamily: 'Inter_800ExtraBold', color: Colors.textInverse },
  scoreHint: { fontSize: 12, color: 'rgba(255,255,255,0.7)', fontFamily: 'Inter_400Regular' },
  scoreCircle: {
    width: 76,
    height: 76,
    borderRadius: 38,
    backgroundColor: 'rgba(255,255,255,0.15)',
    alignItems: 'center',
    justifyContent: 'center',
    borderWidth: 2,
    borderColor: 'rgba(255,255,255,0.25)',
  },
  scoreEmoji: { fontSize: 20 },
  scoreNum: { fontSize: 20, fontFamily: 'Inter_800ExtraBold', color: Colors.textInverse },

  sectionTitle: {
    fontSize: 16,
    fontFamily: 'Inter_700Bold',
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
    borderRadius: 16,
    padding: 18,
    gap: 10,
    backgroundColor: Colors.surface,
    borderWidth: 1.5,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 1,
    shadowRadius: 4,
    elevation: 2,
  },
  actionIconBox: {
    width: 44,
    height: 44,
    borderRadius: 12,
    alignItems: 'center',
    justifyContent: 'center',
  },
  actionLabel: { fontSize: 14, fontFamily: 'Inter_700Bold' },
  actionSub: { fontSize: 11, color: Colors.textMuted, fontFamily: 'Inter_400Regular', lineHeight: 16 },

  tipCard: {
    backgroundColor: Colors.primary,
    marginHorizontal: 20,
    borderRadius: 20,
    padding: 22,
    gap: 10,
  },
  tipTop: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center' },
  tipBadge: {
    backgroundColor: 'rgba(255,255,255,0.2)',
    borderRadius: 20,
    paddingHorizontal: 10,
    paddingVertical: 4,
  },
  tipBadgeText: { color: Colors.textInverse, fontSize: 11, fontFamily: 'Inter_700Bold' },
  tipEmoji: { fontSize: 28 },
  tipTitle: { fontSize: 18, fontFamily: 'Inter_800ExtraBold', color: Colors.textInverse },
  tipDesc: { fontSize: 14, color: 'rgba(255,255,255,0.85)', lineHeight: 22, fontFamily: 'Inter_400Regular' },
  tipFooter: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginTop: 4 },
  tipCategoryPill: {
    backgroundColor: 'rgba(255,255,255,0.15)',
    borderRadius: 20,
    paddingHorizontal: 10,
    paddingVertical: 4,
  },
  tipCategoryText: { color: 'rgba(255,255,255,0.9)', fontSize: 11, fontFamily: 'Inter_600SemiBold' },
  tipMore: { color: 'rgba(255,255,255,0.75)', fontSize: 13, fontFamily: 'Inter_600SemiBold' },

  factCard: {
    flexDirection: 'row',
    backgroundColor: Colors.surface,
    marginHorizontal: 20,
    borderRadius: 16,
    padding: 18,
    gap: 14,
    alignItems: 'flex-start',
    borderWidth: 1,
    borderColor: Colors.border,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 1,
    shadowRadius: 4,
    elevation: 2,
  },
  factEmoji: { fontSize: 32 },
  factText: { flex: 1, fontSize: 14, color: Colors.textSecondary, lineHeight: 22, fontFamily: 'Inter_400Regular' },

  statsRow: { flexDirection: 'row', paddingHorizontal: 20, gap: 12 },
  statCard: {
    flex: 1,
    backgroundColor: Colors.surface,
    borderRadius: 16,
    padding: 16,
    alignItems: 'center',
    gap: 4,
    borderWidth: 1,
    borderColor: Colors.border,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 1,
    shadowRadius: 4,
    elevation: 2,
  },
  statIconBox: {
    width: 36,
    height: 36,
    borderRadius: 10,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 4,
  },
  statValue: { fontSize: 22, fontFamily: 'Inter_800ExtraBold' },
  statLabel: { fontSize: 11, fontFamily: 'Inter_700Bold', color: Colors.textPrimary },
  statSub: { fontSize: 10, color: Colors.textMuted, fontFamily: 'Inter_400Regular' },
});
