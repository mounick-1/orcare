import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { diseases } from '../data/diseaseData';

type Props = NativeStackScreenProps<RootStackParamList, 'DiseaseDetail'>;

export default function DiseaseDetailScreen({ navigation, route }: Props) {
  const { diseaseId } = route.params;
  const disease = diseases.find((d) => d.id === diseaseId);

  if (!disease) {
    return (
      <View style={styles.container}>
        <TouchableOpacity style={styles.backBtn} onPress={() => navigation.goBack()}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <View style={styles.emptyState}>
          <Text style={styles.emptyEmoji}>🔬</Text>
          <Text style={styles.emptyText}>Disease not found.</Text>
        </View>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      {/* Hero */}
      <View style={[styles.hero, { backgroundColor: disease.bgColor }]}>
        <TouchableOpacity
          style={[styles.backBtn, { backgroundColor: disease.color + '20' }]}
          onPress={() => navigation.goBack()}
        >
          <Text style={[styles.backArrow, { color: disease.color }]}>←</Text>
        </TouchableOpacity>

        <View style={styles.heroContent}>
          <View style={[styles.heroIconBox, { backgroundColor: disease.color + '25' }]}>
            <Text style={styles.heroEmoji}>{disease.icon}</Text>
          </View>
          <View style={styles.heroText}>
            <View style={[styles.tagPill, { backgroundColor: disease.color + '20' }]}>
              <Text style={[styles.tagText, { color: disease.color }]}>Oral Disease</Text>
            </View>
            <Text style={[styles.heroTitle, { color: disease.color }]}>{disease.name}</Text>
          </View>
        </View>
      </View>

      <ScrollView contentContainerStyle={styles.content} showsVerticalScrollIndicator={false}>
        {/* What is Happening */}
        <InfoCard
          emoji="🔍"
          label="What is Happening?"
          text={disease.whatIsHappening}
          bgColor={Colors.infoLight}
          labelColor={Colors.info}
        />

        {/* What People Notice */}
        <InfoCard
          emoji="👁️"
          label="What People Notice"
          text={disease.whatPeopleNotice}
          bgColor={Colors.secondaryLight}
          labelColor={Colors.secondary}
        />

        {/* Why It Happens */}
        <InfoCard
          emoji="❓"
          label="Why It Happens"
          text={disease.whyItHappens}
          bgColor={Colors.amberLight}
          labelColor={Colors.amber}
        />

        {/* Why You Shouldn't Ignore */}
        <InfoCard
          emoji="⚠️"
          label="Why You Shouldn't Ignore It"
          text={disease.whyNotIgnore}
          bgColor={Colors.errorLight}
          labelColor={Colors.error}
        />

        {/* When to See Dentist */}
        <View style={styles.urgentCard}>
          <View style={styles.urgentHeader}>
            <Text style={styles.urgentEmoji}>🦷</Text>
            <Text style={styles.urgentTitle}>When to See a Dentist</Text>
          </View>
          <Text style={styles.urgentText}>{disease.whenToSeeDentist}</Text>
        </View>

        {/* AI Button */}
        <TouchableOpacity
          style={styles.aiBtn}
          onPress={() => navigation.navigate('Chatbot', { symptomName: disease.name })}
          activeOpacity={0.85}
        >
          <View style={styles.aiBtnIcon}>
            <Text style={{ fontSize: 20 }}>🤖</Text>
          </View>
          <View style={styles.aiBtnText}>
            <Text style={styles.aiBtnTitle}>Ask ORCare AI</Text>
            <Text style={styles.aiBtnSub}>Get personalized advice about {disease.name}</Text>
          </View>
          <Text style={styles.aiBtnArrow}>→</Text>
        </TouchableOpacity>

        <View style={{ height: 24 }} />
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

  sectionCard: { borderRadius: 18, padding: 16, gap: 10 },
  sectionHeader: { flexDirection: 'row', alignItems: 'center', gap: 8 },
  sectionEmoji: { fontSize: 18 },
  sectionLabel: { fontSize: 14, fontWeight: '800' },
  sectionText: { fontSize: 14, color: Colors.textPrimary, lineHeight: 22 },

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
  aiBtnIcon: {
    width: 44,
    height: 44,
    borderRadius: 14,
    backgroundColor: 'rgba(255,255,255,0.2)',
    alignItems: 'center',
    justifyContent: 'center',
  },
  aiBtnText: { flex: 1 },
  aiBtnTitle: { fontSize: 15, fontWeight: '700', color: Colors.textInverse },
  aiBtnSub: { fontSize: 12, color: 'rgba(255,255,255,0.75)', marginTop: 2 },
  aiBtnArrow: { fontSize: 20, color: Colors.textInverse, fontWeight: '700' },

  emptyState: { flex: 1, alignItems: 'center', justifyContent: 'center', gap: 16 },
  emptyEmoji: { fontSize: 56 },
  emptyText: { fontSize: 16, color: Colors.textSecondary },
});
