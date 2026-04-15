import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { ArrowLeft } from 'lucide-react-native';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { symptomDetailsMap, symptomsList } from '../data/symptomData';

type Props = NativeStackScreenProps<RootStackParamList, 'SymptomDetail'>;

export default function SymptomDetailScreen({ navigation, route }: Props) {
  const { symptomName } = route.params;
  const detail = symptomDetailsMap[symptomName.toLowerCase()];
  const symptom = symptomsList.find((s) => s.title.toLowerCase() === symptomName.toLowerCase());

  if (!detail) {
    return (
      <View style={styles.container}>
        <TouchableOpacity style={styles.backBtn} onPress={() => navigation.goBack()}>
          <ArrowLeft size={20} color={Colors.primary} strokeWidth={2.5} />
        </TouchableOpacity>
        <View style={styles.emptyState}>
          <Text style={styles.emptyEmoji}>🔍</Text>
          <Text style={styles.emptyText}>Symptom information not found.</Text>
        </View>
      </View>
    );
  }

  const accentColor = symptom?.color ?? Colors.primary;
  const bgColor = symptom?.bgColor ?? Colors.primaryLight;

  return (
    <View style={styles.container}>
      {/* Hero Header */}
      <View style={[styles.hero, { backgroundColor: bgColor }]}>
        <TouchableOpacity style={[styles.backBtn, { backgroundColor: accentColor + '20' }]} onPress={() => navigation.goBack()}>
          <ArrowLeft size={20} color={accentColor} strokeWidth={2.5} />
        </TouchableOpacity>

        <View style={styles.heroContent}>
          <View style={[styles.heroIconBox, { backgroundColor: accentColor + '25' }]}>
            <Text style={styles.heroEmoji}>{detail.icon}</Text>
          </View>
          <View style={styles.heroText}>
            <View style={[styles.tagPill, { backgroundColor: accentColor + '20' }]}>
              <Text style={[styles.tagText, { color: accentColor }]}>Symptom</Text>
            </View>
            <Text style={[styles.heroTitle, { color: accentColor }]}>
              {symptomName.replace(/\b\w/g, (c) => c.toUpperCase())}
            </Text>
          </View>
        </View>
      </View>

      <ScrollView contentContainerStyle={styles.content} showsVerticalScrollIndicator={false}>
        {/* What is Happening */}
        <InfoCard
          emoji="🔍"
          label="What is Happening?"
          text={detail.whatIsHappening}
          bgColor={Colors.infoLight}
          labelColor={Colors.info}
        />

        {/* What People Notice */}
        <InfoCard
          emoji="👁️"
          label="What People Notice"
          text={detail.whatPeopleNotice}
          bgColor={Colors.secondaryLight}
          labelColor={Colors.secondary}
        />

        {/* Possible Reasons */}
        <View style={[styles.sectionCard, { backgroundColor: Colors.amberLight }]}>
          <View style={styles.sectionHeader}>
            <Text style={styles.sectionEmoji}>❓</Text>
            <Text style={[styles.sectionLabel, { color: Colors.amber }]}>Possible Reasons</Text>
          </View>
          <View style={styles.bulletList}>
            {detail.possibleReasons.map((r, i) => (
              <View key={i} style={styles.bulletRow}>
                <View style={[styles.bulletDot, { backgroundColor: Colors.amber }]} />
                <Text style={styles.bulletText}>{r}</Text>
              </View>
            ))}
          </View>
        </View>

        {/* What To Do */}
        <View style={[styles.sectionCard, { backgroundColor: Colors.secondaryLight }]}>
          <View style={styles.sectionHeader}>
            <Text style={styles.sectionEmoji}>✅</Text>
            <Text style={[styles.sectionLabel, { color: Colors.secondary }]}>What You Can Do</Text>
          </View>
          <View style={styles.bulletList}>
            {detail.whatToDo.map((r, i) => (
              <View key={i} style={styles.bulletRow}>
                <View style={[styles.numberBadge, { backgroundColor: Colors.secondary }]}>
                  <Text style={styles.numberText}>{i + 1}</Text>
                </View>
                <Text style={styles.bulletText}>{r}</Text>
              </View>
            ))}
          </View>
        </View>

        {/* When to See Dentist */}
        <View style={styles.urgentCard}>
          <View style={styles.urgentHeader}>
            <Text style={styles.urgentEmoji}>🦷</Text>
            <Text style={styles.urgentTitle}>When to See a Dentist</Text>
          </View>
          <Text style={styles.urgentText}>{detail.whenToSeeDentist}</Text>
        </View>

        {/* Ask AI Banner */}
        <View style={styles.aiBanner}>
          <Text style={styles.aiBannerLabel}>Still have questions?</Text>
          <Text style={styles.aiBannerSub}>Our AI can give personalised guidance</Text>
        </View>

        <TouchableOpacity
          style={styles.aiBtn}
          onPress={() => navigation.navigate('Chatbot', { symptomName })}
          activeOpacity={0.85}
        >
          <View style={styles.aiBtnIcon}>
            <Text style={styles.aiBtnStar}>✦</Text>
          </View>
          <View style={styles.aiBtnText}>
            <Text style={styles.aiBtnTitle}>Ask AI About This Complaint</Text>
            <Text style={styles.aiBtnSub}>
              Chat with ORCare AI about{' '}
              {symptomName.replace(/\b\w/g, (c) => c.toUpperCase())}
            </Text>
          </View>
          <Text style={styles.aiBtnArrow}>→</Text>
        </TouchableOpacity>

        <View style={{ height: 32 }} />
      </ScrollView>
    </View>
  );
}

function InfoCard({
  emoji, label, text, bgColor, labelColor,
}: {
  emoji: string; label: string; text: string; bgColor: string; labelColor: string;
}) {
  return (
    <View style={[styles.sectionCard, { backgroundColor: bgColor }]}>
      <View style={styles.sectionHeader}>
        <Text style={styles.sectionEmoji}>{emoji}</Text>
        <Text style={[styles.sectionLabel, { color: labelColor }]}>{label}</Text>
      </View>
      <Text style={styles.sectionText}>{text}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.background },

  hero: {
    paddingTop: 56,
    paddingHorizontal: 20,
    paddingBottom: 24,
  },
  backBtn: {
    width: 38,
    height: 38,
    borderRadius: 12,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 16,
    alignSelf: 'flex-start',
  },
  backArrow: { fontSize: 18, fontWeight: '700' },
  heroContent: { flexDirection: 'row', alignItems: 'center', gap: 16 },
  heroIconBox: {
    width: 72,
    height: 72,
    borderRadius: 20,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },
  heroEmoji: { fontSize: 38 },
  heroText: { flex: 1, gap: 6 },
  tagPill: {
    alignSelf: 'flex-start',
    borderRadius: 20,
    paddingHorizontal: 10,
    paddingVertical: 3,
  },
  tagText: { fontSize: 11, fontWeight: '700', textTransform: 'uppercase', letterSpacing: 0.5 },
  heroTitle: { fontSize: 22, fontWeight: '800', lineHeight: 28 },

  content: { padding: 16, gap: 12 },

  sectionCard: { borderRadius: 18, padding: 16, gap: 12 },
  sectionHeader: { flexDirection: 'row', alignItems: 'center', gap: 8 },
  sectionEmoji: { fontSize: 18 },
  sectionLabel: { fontSize: 14, fontWeight: '800' },
  sectionText: { fontSize: 14, color: Colors.textPrimary, lineHeight: 22 },

  bulletList: { gap: 10 },
  bulletRow: { flexDirection: 'row', gap: 10, alignItems: 'flex-start' },
  bulletDot: {
    width: 6,
    height: 6,
    borderRadius: 3,
    marginTop: 8,
    flexShrink: 0,
  },
  numberBadge: {
    width: 22,
    height: 22,
    borderRadius: 11,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
    marginTop: 1,
  },
  numberText: { color: Colors.textInverse, fontSize: 11, fontWeight: '800' },
  bulletText: { flex: 1, fontSize: 14, color: Colors.textPrimary, lineHeight: 22 },

  urgentCard: {
    backgroundColor: Colors.errorLight,
    borderRadius: 18,
    padding: 16,
    borderLeftWidth: 4,
    borderLeftColor: Colors.error,
    gap: 10,
  },
  urgentHeader: { flexDirection: 'row', alignItems: 'center', gap: 8 },
  urgentEmoji: { fontSize: 18 },
  urgentTitle: { fontSize: 14, fontWeight: '800', color: Colors.error },
  urgentText: { fontSize: 14, color: Colors.textPrimary, lineHeight: 22 },

  aiBtn: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.primary,
    borderRadius: 18,
    padding: 16,
    gap: 14,
    shadowColor: Colors.primary,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 8,
  },
  aiBanner: {
    backgroundColor: Colors.primaryLight,
    borderRadius: 14,
    padding: 14,
    alignItems: 'center',
    gap: 4,
    borderWidth: 1,
    borderColor: Colors.primary + '30',
  },
  aiBannerLabel: { fontSize: 14, fontWeight: '700', color: Colors.primary },
  aiBannerSub: { fontSize: 12, color: Colors.textSecondary },

  aiBtnIcon: {
    width: 44,
    height: 44,
    borderRadius: 14,
    backgroundColor: 'rgba(255,255,255,0.15)',
    alignItems: 'center',
    justifyContent: 'center',
  },
  aiBtnStar: { fontSize: 22, color: Colors.textInverse, fontWeight: '300' },
  aiBtnText: { flex: 1 },
  aiBtnTitle: { fontSize: 15, fontWeight: '700', color: Colors.textInverse },
  aiBtnSub: { fontSize: 12, color: 'rgba(255,255,255,0.75)', marginTop: 2 },
  aiBtnArrow: { fontSize: 20, color: Colors.textInverse, fontWeight: '700' },

  emptyState: { flex: 1, alignItems: 'center', justifyContent: 'center', gap: 16 },
  emptyEmoji: { fontSize: 56 },
  emptyText: { fontSize: 16, color: Colors.textSecondary },
});
