import React from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { Colors } from '../theme/colors';
import { diseases } from '../data/diseaseData';
import { RootStackParamList } from '../navigation/AppNavigator';

type Nav = NativeStackNavigationProp<RootStackParamList>;

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
          <Text style={{ fontSize: 24 }}>🔬</Text>
        </View>
      </View>

      <FlatList
        data={diseases}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        showsVerticalScrollIndicator={false}
        ListHeaderComponent={
          /* Info Banner */
          <View style={styles.banner}>
            <View style={styles.bannerIcon}>
              <Text style={{ fontSize: 26 }}>⚠️</Text>
            </View>
            <View style={styles.bannerText}>
              <Text style={styles.bannerTitle}>Early Detection Saves Teeth</Text>
              <Text style={styles.bannerSub}>Learn the signs before they become serious</Text>
            </View>
          </View>
        }
        renderItem={({ item }) => (
          <TouchableOpacity
            style={[styles.card, { borderLeftColor: item.color }]}
            onPress={() => navigation.navigate('DiseaseDetail', { diseaseId: item.id })}
            activeOpacity={0.85}
          >
            {/* Top row: icon + name + arrow */}
            <View style={styles.cardTop}>
              <View style={[styles.iconBox, { backgroundColor: item.bgColor }]}>
                <Text style={styles.icon}>{item.icon}</Text>
              </View>
              <View style={styles.cardTitle}>
                <Text style={[styles.diseaseName, { color: item.color }]}>{item.name}</Text>
                <View style={[styles.tagRow]}>
                  <View style={[styles.tag, { backgroundColor: item.bgColor }]}>
                    <View style={[styles.tagDot, { backgroundColor: item.color }]} />
                    <Text style={[styles.tagText, { color: item.color }]}>Oral Condition</Text>
                  </View>
                </View>
              </View>
              <View style={[styles.arrowBox, { backgroundColor: item.bgColor }]}>
                <Text style={[styles.arrow, { color: item.color }]}>›</Text>
              </View>
            </View>

            {/* Preview text */}
            <Text style={styles.preview} numberOfLines={2}>{item.whatIsHappening}</Text>

            {/* Footer */}
            <View style={[styles.cardFooter, { borderTopColor: item.color + '20' }]}>
              <Text style={[styles.footerCTA, { color: item.color }]}>View Details →</Text>
            </View>
          </TouchableOpacity>
        )}
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
  title: { fontSize: 22, fontWeight: '800', color: Colors.textPrimary },
  subtitle: { fontSize: 12, color: Colors.textSecondary, marginTop: 2 },
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
  bannerTitle: { fontSize: 14, fontWeight: '800', color: Colors.amber },
  bannerSub: { fontSize: 12, color: Colors.textSecondary, lineHeight: 18 },

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
  icon: { fontSize: 30 },

  cardTitle: { flex: 1, gap: 6 },
  diseaseName: { fontSize: 17, fontWeight: '800', lineHeight: 22 },
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
  tagText: { fontSize: 10, fontWeight: '700' },

  arrowBox: {
    width: 36,
    height: 36,
    borderRadius: 10,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },
  arrow: { fontSize: 22, fontWeight: '700', lineHeight: 26 },

  preview: {
    fontSize: 13,
    color: Colors.textSecondary,
    lineHeight: 20,
    paddingHorizontal: 16,
    paddingBottom: 12,
  },

  cardFooter: {
    borderTopWidth: 1,
    paddingHorizontal: 16,
    paddingVertical: 10,
  },
  footerCTA: { fontSize: 12, fontWeight: '700' },
});
