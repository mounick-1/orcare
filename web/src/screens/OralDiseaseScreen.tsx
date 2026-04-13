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
      <View style={styles.header}>
        <View>
          <Text style={styles.title}>Disease Database</Text>
          <Text style={styles.subtitle}>Tap any condition to learn more</Text>
        </View>
        <View style={styles.headerBadge}>
          <Text style={{ fontSize: 24 }}>🔬</Text>
        </View>
      </View>

      {/* Info Banner */}
      <View style={styles.banner}>
        <Text style={styles.bannerEmoji}>⚠️</Text>
        <View style={styles.bannerText}>
          <Text style={styles.bannerTitle}>Early Detection Saves Teeth</Text>
          <Text style={styles.bannerSub}>Learn the signs before they become serious</Text>
        </View>
      </View>

      <FlatList
        data={diseases}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        showsVerticalScrollIndicator={false}
        renderItem={({ item }) => (
          <TouchableOpacity
            style={styles.card}
            onPress={() => navigation.navigate('DiseaseDetail', { diseaseId: item.id })}
            activeOpacity={0.85}
          >
            <View style={[styles.iconBox, { backgroundColor: item.bgColor }]}>
              <Text style={styles.icon}>{item.icon}</Text>
            </View>
            <View style={styles.cardBody}>
              <Text style={styles.cardTitle}>{item.name}</Text>
              <Text style={styles.cardPreview} numberOfLines={2}>{item.whatIsHappening}</Text>
              <View style={styles.cardTag}>
                <View style={[styles.dot, { backgroundColor: item.color }]} />
                <Text style={[styles.tagText, { color: item.color }]}>Tap to learn more</Text>
              </View>
            </View>
            <Text style={[styles.arrow, { color: item.color }]}>›</Text>
          </TouchableOpacity>
        )}
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
    margin: 16,
    borderRadius: 14,
    padding: 14,
    gap: 12,
    borderWidth: 1,
    borderColor: Colors.amberLight,
  },
  bannerEmoji: { fontSize: 24 },
  bannerText: { flex: 1 },
  bannerTitle: { fontSize: 14, fontWeight: '700', color: Colors.amber },
  bannerSub: { fontSize: 12, color: Colors.textSecondary, marginTop: 2 },

  list: { paddingHorizontal: 16, paddingBottom: 16, gap: 12 },
  card: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.surface,
    borderRadius: 18,
    padding: 16,
    gap: 14,
    borderWidth: 1,
    borderColor: Colors.border,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 1,
    shadowRadius: 6,
    elevation: 2,
  },
  iconBox: {
    width: 56,
    height: 56,
    borderRadius: 18,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },
  icon: { fontSize: 28 },
  cardBody: { flex: 1, gap: 4 },
  cardTitle: { fontSize: 15, fontWeight: '700', color: Colors.textPrimary },
  cardPreview: { fontSize: 12, color: Colors.textSecondary, lineHeight: 18 },
  cardTag: { flexDirection: 'row', alignItems: 'center', gap: 6, marginTop: 4 },
  dot: { width: 6, height: 6, borderRadius: 3 },
  tagText: { fontSize: 11, fontWeight: '600' },
  arrow: { fontSize: 30, fontWeight: '300', flexShrink: 0 },
});
