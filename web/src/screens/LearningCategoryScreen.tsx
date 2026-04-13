import React from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { learningCategories } from '../data/learningData';

type Props = NativeStackScreenProps<RootStackParamList, 'LearningCategory'>;

export default function LearningCategoryScreen({ navigation, route }: Props) {
  const { categoryId } = route.params;
  const category = learningCategories.find((c) => c.id === categoryId);

  if (!category) {
    return (
      <View style={styles.container}>
        <TouchableOpacity style={styles.backBtn} onPress={() => navigation.goBack()}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <View style={styles.emptyState}>
          <Text style={styles.emptyEmoji}>📚</Text>
          <Text style={styles.emptyText}>Category not found.</Text>
        </View>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      {/* Hero Header */}
      <View style={[styles.hero, { backgroundColor: category.bgColor }]}>
        <TouchableOpacity
          style={[styles.backBtn, { backgroundColor: category.color + '20' }]}
          onPress={() => navigation.goBack()}
        >
          <Text style={[styles.backArrow, { color: category.color }]}>←</Text>
        </TouchableOpacity>

        <View style={styles.heroContent}>
          <View style={[styles.heroIconBox, { backgroundColor: category.color + '25' }]}>
            <Text style={styles.heroEmoji}>{category.icon}</Text>
          </View>
          <View style={styles.heroText}>
            <View style={[styles.tagPill, { backgroundColor: category.color + '20' }]}>
              <Text style={[styles.tagText, { color: category.color }]}>
                {category.modules.length} modules
              </Text>
            </View>
            <Text style={[styles.heroTitle, { color: category.color }]}>{category.title}</Text>
            <Text style={styles.heroDesc}>{category.description}</Text>
          </View>
        </View>
      </View>

      <FlatList
        data={category.modules}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        showsVerticalScrollIndicator={false}
        ListHeaderComponent={
          <Text style={styles.listHeader}>All Modules</Text>
        }
        renderItem={({ item, index }) => (
          <TouchableOpacity
            style={styles.moduleCard}
            onPress={() => navigation.navigate('ModuleDetail', { moduleId: item.id, categoryId: category.id })}
            activeOpacity={0.85}
          >
            <View style={[styles.indexBadge, { backgroundColor: category.bgColor }]}>
              <Text style={[styles.indexText, { color: category.color }]}>{index + 1}</Text>
            </View>

            <View style={styles.moduleContent}>
              <View style={styles.moduleTop}>
                <View style={[styles.moduleIconBox, { backgroundColor: category.bgColor }]}>
                  <Text style={styles.moduleEmoji}>{item.icon}</Text>
                </View>
                <View style={[styles.pointsBadge, { backgroundColor: Colors.amberLight }]}>
                  <Text style={[styles.pointsText, { color: Colors.amber }]}>⭐ {item.points} pts</Text>
                </View>
              </View>

              <Text style={styles.moduleTitle}>{item.title}</Text>
              <Text style={styles.moduleDesc} numberOfLines={2}>{item.description}</Text>

              <View style={styles.moduleMeta}>
                <View style={styles.metaItem}>
                  <Text style={styles.metaIcon}>📖</Text>
                  <Text style={styles.metaText}>{item.lessons.length} lessons</Text>
                </View>
                <View style={styles.metaDot} />
                <View style={styles.metaItem}>
                  <Text style={styles.metaIcon}>❓</Text>
                  <Text style={styles.metaText}>{item.quiz.length} quiz</Text>
                </View>
              </View>
            </View>

            <Text style={[styles.cardArrow, { color: category.color }]}>›</Text>
          </TouchableOpacity>
        )}
      />
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
  heroContent: { flexDirection: 'row', alignItems: 'flex-start', gap: 16 },
  heroIconBox: {
    width: 68,
    height: 68,
    borderRadius: 20,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },
  heroEmoji: { fontSize: 34 },
  heroText: { flex: 1, gap: 6 },
  tagPill: {
    alignSelf: 'flex-start',
    borderRadius: 20,
    paddingHorizontal: 10,
    paddingVertical: 3,
  },
  tagText: { fontSize: 11, fontWeight: '700', textTransform: 'uppercase', letterSpacing: 0.5 },
  heroTitle: { fontSize: 20, fontWeight: '800', lineHeight: 26 },
  heroDesc: { fontSize: 12, color: Colors.textSecondary, lineHeight: 18 },

  list: { padding: 16, gap: 12 },
  listHeader: {
    fontSize: 11,
    fontWeight: '800',
    color: Colors.textMuted,
    textTransform: 'uppercase',
    letterSpacing: 0.8,
    marginBottom: 4,
  },

  moduleCard: {
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
  indexBadge: {
    width: 36,
    height: 36,
    borderRadius: 18,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },
  indexText: { fontWeight: '800', fontSize: 15 },

  moduleContent: { flex: 1, gap: 6 },
  moduleTop: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center' },
  moduleIconBox: {
    width: 32,
    height: 32,
    borderRadius: 10,
    alignItems: 'center',
    justifyContent: 'center',
  },
  moduleEmoji: { fontSize: 16 },
  pointsBadge: {
    borderRadius: 20,
    paddingHorizontal: 8,
    paddingVertical: 3,
  },
  pointsText: { fontSize: 11, fontWeight: '700' },

  moduleTitle: { fontSize: 14, fontWeight: '700', color: Colors.textPrimary },
  moduleDesc: { fontSize: 12, color: Colors.textSecondary, lineHeight: 17 },

  moduleMeta: { flexDirection: 'row', alignItems: 'center', gap: 8, marginTop: 2 },
  metaItem: { flexDirection: 'row', alignItems: 'center', gap: 4 },
  metaIcon: { fontSize: 12 },
  metaText: { fontSize: 11, color: Colors.textMuted, fontWeight: '500' },
  metaDot: { width: 3, height: 3, borderRadius: 1.5, backgroundColor: Colors.textMuted },

  cardArrow: { fontSize: 26, fontWeight: '300', flexShrink: 0 },

  emptyState: { flex: 1, alignItems: 'center', justifyContent: 'center', gap: 16 },
  emptyEmoji: { fontSize: 56 },
  emptyText: { fontSize: 16, color: Colors.textSecondary },
});
