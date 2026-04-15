import React from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import {
  Droplets, AlertTriangle, Wind, Microscope, Zap, Flame,
  Wand2, ChevronRight,
} from 'lucide-react-native';
import { Colors } from '../theme/colors';
import { diseases, Disease } from '../data/diseaseData';
import { RootStackParamList } from '../navigation/AppNavigator';

type Nav = NativeStackNavigationProp<RootStackParamList>;

// Maps Android's Material icon choices → Lucide equivalents
const DiseaseIcon: Record<string, React.ComponentType<{ size: number; color: string; strokeWidth: number }>> = {
  gingivitis:    Droplets,
  cavities:      AlertTriangle,
  bad_breath:    Wind,
  oral_cancer:   Microscope,
  sensitivity:   Zap,
  mouth_ulcers:  Flame,
};

export default function OralDiseaseScreen() {
  const navigation = useNavigation<Nav>();

  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <View style={styles.headerLeft}>
          <Text style={styles.title}>Disease Database</Text>
          <Text style={styles.subtitle}>{diseases.length} conditions documented</Text>
        </View>
        <View style={styles.headerBadge}>
          <Wand2 size={22} color={Colors.primary} strokeWidth={2} />
        </View>
      </View>

      <FlatList
        data={diseases}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        showsVerticalScrollIndicator={false}
        ListHeaderComponent={
          <View style={styles.banner}>
            <View style={styles.bannerIcon}>
              <AlertTriangle size={24} color={Colors.amber} strokeWidth={2} />
            </View>
            <View style={styles.bannerText}>
              <Text style={styles.bannerTitle}>Early Detection Saves Teeth</Text>
              <Text style={styles.bannerSub}>Learn the signs before they become serious</Text>
            </View>
          </View>
        }
        renderItem={({ item }: { item: Disease }) => {
          const Icon = DiseaseIcon[item.id] ?? Sparkles;
          return (
            <TouchableOpacity
              style={[styles.card, { borderLeftColor: item.color }]}
              onPress={() => navigation.navigate('DiseaseDetail', { diseaseId: item.id })}
              activeOpacity={0.85}
            >
              <View style={styles.cardTop}>
                <View style={[styles.iconBox, { backgroundColor: item.bgColor }]}>
                  <Icon size={28} color={item.color} strokeWidth={1.75} />
                </View>
                <View style={styles.cardTitle}>
                  <Text style={[styles.diseaseName, { color: item.color }]}>{item.name}</Text>
                  <View style={styles.tagRow}>
                    <View style={[styles.tag, { backgroundColor: item.bgColor }]}>
                      <View style={[styles.tagDot, { backgroundColor: item.color }]} />
                      <Text style={[styles.tagText, { color: item.color }]}>Oral Condition</Text>
                    </View>
                  </View>
                </View>
                <View style={[styles.arrowBox, { backgroundColor: item.bgColor }]}>
                  <ChevronRight size={18} color={item.color} strokeWidth={2.5} />
                </View>
              </View>

              <Text style={styles.preview} numberOfLines={2}>{item.whatIsHappening}</Text>

              <View style={[styles.cardFooter, { borderTopColor: item.color + '20' }]}>
                <Text style={[styles.footerCTA, { color: item.color }]}>View Details →</Text>
              </View>
            </TouchableOpacity>
          );
        }}
        ListFooterComponent={<View style={{ height: 24 }} />}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.background },

  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 20,
    paddingTop: 56,
    backgroundColor: Colors.surface,
    borderBottomWidth: 1,
    borderBottomColor: Colors.border,
  },
  headerLeft: { gap: 2 },
  title: { fontSize: 22, fontFamily: 'Inter_800ExtraBold', color: Colors.textPrimary },
  subtitle: { fontSize: 12, color: Colors.textSecondary, marginTop: 2, fontFamily: 'Inter_400Regular' },
  headerBadge: {
    width: 48,
    height: 48,
    borderRadius: 16,
    backgroundColor: Colors.primaryLight,
    alignItems: 'center',
    justifyContent: 'center',
  },

  banner: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.warningLight,
    borderRadius: 16,
    padding: 16,
    gap: 14,
    borderWidth: 1,
    borderColor: Colors.amber + '40',
    marginBottom: 4,
  },
  bannerIcon: {
    width: 48,
    height: 48,
    borderRadius: 14,
    backgroundColor: Colors.amber + '20',
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },
  bannerText: { flex: 1, gap: 3 },
  bannerTitle: { fontSize: 14, fontFamily: 'Inter_700Bold', color: Colors.amber },
  bannerSub: { fontSize: 12, color: Colors.textSecondary, lineHeight: 18, fontFamily: 'Inter_400Regular' },

  list: { padding: 16, gap: 14 },

  card: {
    backgroundColor: Colors.surface,
    borderRadius: 18,
    borderWidth: 1,
    borderColor: Colors.border,
    borderLeftWidth: 4,
    overflow: 'hidden',
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 1,
    shadowRadius: 8,
    elevation: 2,
  },

  cardTop: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 16,
    gap: 12,
  },
  iconBox: {
    width: 60,
    height: 60,
    borderRadius: 18,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },

  cardTitle: { flex: 1, gap: 6 },
  diseaseName: { fontSize: 17, fontFamily: 'Inter_800ExtraBold', lineHeight: 22 },
  tagRow: { flexDirection: 'row' },
  tag: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 5,
    borderRadius: 20,
    paddingHorizontal: 8,
    paddingVertical: 3,
    alignSelf: 'flex-start',
  },
  tagDot: { width: 5, height: 5, borderRadius: 2.5 },
  tagText: { fontSize: 10, fontFamily: 'Inter_700Bold' },

  arrowBox: {
    width: 36,
    height: 36,
    borderRadius: 10,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },

  preview: {
    fontSize: 13,
    color: Colors.textSecondary,
    lineHeight: 20,
    paddingHorizontal: 16,
    paddingBottom: 12,
    fontFamily: 'Inter_400Regular',
  },

  cardFooter: {
    borderTopWidth: 1,
    paddingHorizontal: 16,
    paddingVertical: 10,
  },
  footerCTA: { fontSize: 12, fontFamily: 'Inter_700Bold' },
});
