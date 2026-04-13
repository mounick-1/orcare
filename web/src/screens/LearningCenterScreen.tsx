import React from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { Colors } from '../theme/colors';
import { learningCategories } from '../data/learningData';
import { RootStackParamList } from '../navigation/AppNavigator';

type Nav = NativeStackNavigationProp<RootStackParamList>;

export default function LearningCenterScreen() {
  const navigation = useNavigation<Nav>();
  const totalModules = learningCategories.reduce((s, c) => s + c.modules.length, 0);

  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <View>
          <Text style={styles.title}>Learning Center</Text>
          <Text style={styles.subtitle}>{learningCategories.length} categories · {totalModules} modules</Text>
        </View>
        <View style={styles.headerBadge}>
          <Text style={styles.headerBadgeEmoji}>📚</Text>
        </View>
      </View>

      {/* Progress Banner */}
      <View style={styles.progressBanner}>
        <View style={styles.progressLeft}>
          <Text style={styles.progressTitle}>Keep Learning!</Text>
          <Text style={styles.progressSub}>Complete modules to earn points</Text>
        </View>
        <View style={styles.progressRight}>
          <Text style={styles.progressPoints}>0</Text>
          <Text style={styles.progressPtsLabel}>pts</Text>
        </View>
      </View>

      <FlatList
        data={learningCategories}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        showsVerticalScrollIndicator={false}
        renderItem={({ item }) => (
          <TouchableOpacity
            style={styles.card}
            onPress={() => navigation.navigate('LearningCategory', { categoryId: item.id })}
            activeOpacity={0.85}
          >
            <View style={[styles.cardIconBox, { backgroundColor: item.bgColor }]}>
              <Text style={styles.cardEmoji}>{item.icon}</Text>
            </View>
            <View style={styles.cardContent}>
              <View style={styles.cardTop}>
                <Text style={styles.cardTitle}>{item.title}</Text>
                <View style={[styles.countBadge, { backgroundColor: item.bgColor }]}>
                  <Text style={[styles.countText, { color: item.color }]}>{item.modules.length} modules</Text>
                </View>
              </View>
              <Text style={styles.cardDesc} numberOfLines={1}>{item.description}</Text>
              <View style={styles.progressBarBg}>
                <View style={[styles.progressBarFill, { backgroundColor: item.color, width: '0%' }]} />
              </View>
            </View>
            <Text style={[styles.cardArrow, { color: item.color }]}>›</Text>
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
  headerBadgeEmoji: { fontSize: 24 },

  progressBanner: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    backgroundColor: Colors.primary,
    margin: 20,
    marginBottom: 4,
    borderRadius: 18,
    padding: 18,
  },
  progressLeft: { gap: 4 },
  progressTitle: { fontSize: 16, fontWeight: '800', color: Colors.textInverse },
  progressSub: { fontSize: 12, color: 'rgba(255,255,255,0.7)' },
  progressRight: { alignItems: 'center' },
  progressPoints: { fontSize: 32, fontWeight: '900', color: Colors.textInverse },
  progressPtsLabel: { fontSize: 11, color: 'rgba(255,255,255,0.7)', fontWeight: '600' },

  list: { padding: 16, gap: 12 },
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
    shadowRadius: 8,
    elevation: 2,
  },
  cardIconBox: {
    width: 56,
    height: 56,
    borderRadius: 18,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },
  cardEmoji: { fontSize: 28 },
  cardContent: { flex: 1, gap: 6 },
  cardTop: { flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between', gap: 8 },
  cardTitle: { fontSize: 15, fontWeight: '700', color: Colors.textPrimary, flex: 1 },
  countBadge: { borderRadius: 20, paddingHorizontal: 8, paddingVertical: 3, flexShrink: 0 },
  countText: { fontSize: 10, fontWeight: '700' },
  cardDesc: { fontSize: 12, color: Colors.textSecondary },
  progressBarBg: {
    height: 4,
    backgroundColor: Colors.borderLight,
    borderRadius: 2,
    overflow: 'hidden',
  },
  progressBarFill: { height: 4, borderRadius: 2 },
  cardArrow: { fontSize: 28, fontWeight: '300', flexShrink: 0 },
});
