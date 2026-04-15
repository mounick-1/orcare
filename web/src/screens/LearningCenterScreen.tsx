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
        <View style={styles.headerLeft}>
          <Text style={styles.title}>Learning Center</Text>
          <Text style={styles.subtitle}>{learningCategories.length} categories · {totalModules} modules</Text>
        </View>
        <View style={styles.headerBadge}>
          <Text style={styles.headerBadgeEmoji}>📚</Text>
        </View>
      </View>

      <FlatList
        data={learningCategories}
        keyExtractor={(item) => item.id}
        numColumns={2}
        columnWrapperStyle={styles.row}
        contentContainerStyle={styles.list}
        showsVerticalScrollIndicator={false}
        ListHeaderComponent={
          <View style={styles.statsBar}>
            <View style={styles.statChip}>
              <Text style={styles.statChipVal}>{learningCategories.length}</Text>
              <Text style={styles.statChipLabel}>Categories</Text>
            </View>
            <View style={[styles.statChip, { backgroundColor: Colors.secondaryLight }]}>
              <Text style={[styles.statChipVal, { color: Colors.secondary }]}>{totalModules}</Text>
              <Text style={[styles.statChipLabel, { color: Colors.secondary }]}>Modules</Text>
            </View>
            <View style={[styles.statChip, { backgroundColor: Colors.accentLight }]}>
              <Text style={[styles.statChipVal, { color: Colors.accent }]}>Free</Text>
              <Text style={[styles.statChipLabel, { color: Colors.accent }]}>Access</Text>
            </View>
          </View>
        }
        renderItem={({ item }) => (
          <TouchableOpacity
            style={[styles.card, { borderColor: item.color + '30' }]}
            onPress={() => navigation.navigate('LearningCategory', { categoryId: item.id })}
            activeOpacity={0.85}
          >
            {/* Colored top band */}
            <View style={[styles.cardBand, { backgroundColor: item.bgColor }]}>
              <View style={[styles.cardIconBox, { backgroundColor: item.color + '25' }]}>
                <Text style={styles.cardEmoji}>{item.icon}</Text>
              </View>
              <View style={[styles.moduleBadge, { backgroundColor: item.color + '20' }]}>
                <Text style={[styles.moduleBadgeText, { color: item.color }]}>{item.modules.length} modules</Text>
              </View>
            </View>

            {/* Card body */}
            <View style={styles.cardBody}>
              <Text style={[styles.cardTitle, { color: item.color }]}>{item.title}</Text>
              <Text style={styles.cardDesc} numberOfLines={2}>{item.description}</Text>
            </View>

            {/* Card footer */}
            <View style={[styles.cardFooter, { borderTopColor: item.color + '20' }]}>
              <Text style={[styles.cardCTA, { color: item.color }]}>Start Learning</Text>
              <Text style={[styles.cardArrow, { color: item.color }]}>→</Text>
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
  headerBadgeEmoji: { fontSize: 24 },

  statsBar: {
    flexDirection: 'row',
    gap: 10,
    paddingBottom: 4,
  },
  statChip: {
    flex: 1,
    backgroundColor: Colors.primaryLight,
    borderRadius: 14,
    padding: 12,
    alignItems: 'center',
    gap: 2,
  },
  statChipVal: { fontSize: 18, fontWeight: '900', color: Colors.primary },
  statChipLabel: { fontSize: 10, fontWeight: '700', color: Colors.primary, textTransform: 'uppercase' },

  list: { padding: 16, gap: 12 },
  row: { gap: 12 },

  card: {
    flex: 1,
    backgroundColor: Colors.surface,
    borderRadius: 20,
    borderWidth: 1,
    overflow: 'hidden',
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 1,
    shadowRadius: 8,
    elevation: 3,
  },

  cardBand: {
    padding: 16,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
  },
  cardIconBox: {
    width: 52,
    height: 52,
    borderRadius: 16,
    alignItems: 'center',
    justifyContent: 'center',
  },
  cardEmoji: { fontSize: 26 },
  moduleBadge: {
    borderRadius: 20,
    paddingHorizontal: 8,
    paddingVertical: 4,
    alignSelf: 'flex-start',
  },
  moduleBadgeText: { fontSize: 10, fontWeight: '800' },

  cardBody: { padding: 12, paddingTop: 8, gap: 4 },
  cardTitle: { fontSize: 14, fontWeight: '800', lineHeight: 20 },
  cardDesc: { fontSize: 11, color: Colors.textSecondary, lineHeight: 16 },

  cardFooter: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    padding: 12,
    paddingTop: 10,
    borderTopWidth: 1,
  },
  cardCTA: { fontSize: 11, fontWeight: '700' },
  cardArrow: { fontSize: 14, fontWeight: '800' },
});
