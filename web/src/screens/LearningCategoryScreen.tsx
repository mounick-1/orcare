import React from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { ArrowLeft, PlayCircle } from 'lucide-react-native';
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
          <ArrowLeft size={20} color={Colors.primary} strokeWidth={2.5} />
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
      {/* Colored Hero */}
      <View style={[styles.hero, { backgroundColor: category.bgColor }]}>
        <TouchableOpacity
          style={[styles.backBtn, { backgroundColor: category.color + '20' }]}
          onPress={() => navigation.goBack()}
        >
          <ArrowLeft size={20} color={category.color} strokeWidth={2.5} />
        </TouchableOpacity>

        <View style={styles.heroContent}>
          <View style={[styles.heroIconBox, { backgroundColor: category.color + '30' }]}>
            <Text style={styles.heroEmoji}>{category.icon}</Text>
          </View>
          <View style={styles.heroMeta}>
            <View style={[styles.tagPill, { backgroundColor: category.color + '20' }]}>
              <Text style={[styles.tagText, { color: category.color }]}>
                {category.modules.length} Modules
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
          <Text style={styles.listLabel}>All Modules</Text>
        }
        renderItem={({ item, index }) => (
          <TouchableOpacity
            style={[styles.card, { borderColor: category.color + '25' }]}
            onPress={() => navigation.navigate('ModuleDetail', { moduleId: item.id, categoryId: category.id })}
            activeOpacity={0.85}
          >
            {/* Module number + icon row */}
            <View style={styles.cardTop}>
              <View style={[styles.numberCircle, { backgroundColor: category.bgColor }]}>
                <Text style={[styles.numberText, { color: category.color }]}>{index + 1}</Text>
              </View>
              <View style={styles.cardTopRight}>
                <View style={[styles.iconPill, { backgroundColor: category.bgColor }]}>
                  <Text style={styles.moduleEmoji}>{item.icon}</Text>
                  <Text style={[styles.iconPillText, { color: category.color }]}>{item.title}</Text>
                </View>
                <View style={[styles.pointsBadge, { backgroundColor: Colors.amberLight }]}>
                  <Text style={[styles.pointsText, { color: Colors.amber }]}>⭐ {item.points} pts</Text>
                </View>
              </View>
            </View>

            {/* Description */}
            <Text style={styles.moduleDesc} numberOfLines={2}>{item.description}</Text>

            {/* Meta row: lessons + quiz */}
            <View style={styles.metaRow}>
              <View style={[styles.metaBadge, { backgroundColor: category.bgColor }]}>
                <Text style={styles.metaEmoji}>📖</Text>
                <Text style={[styles.metaText, { color: category.color }]}>{item.lessons.length} Lessons</Text>
              </View>
              <View style={[styles.metaBadge, { backgroundColor: Colors.primaryLight }]}>
                <Text style={styles.metaEmoji}>🧠</Text>
                <Text style={[styles.metaText, { color: Colors.primary }]}>{item.quiz.length} Quiz Q's</Text>
              </View>
              <View style={[styles.startBtn, { backgroundColor: category.bgColor }]}>
                <PlayCircle size={14} color={category.color} strokeWidth={2} />
                <Text style={[styles.startBtnText, { color: category.color }]}>Start</Text>
              </View>
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
    width: 72,
    height: 72,
    borderRadius: 22,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },
  heroEmoji: { fontSize: 36 },
  heroMeta: { flex: 1, gap: 6 },
  tagPill: {
    alignSelf: 'flex-start',
    borderRadius: 20,
    paddingHorizontal: 10,
    paddingVertical: 4,
  },
  tagText: { fontSize: 11, fontWeight: '800', textTransform: 'uppercase', letterSpacing: 0.6 },
  heroTitle: { fontSize: 21, fontWeight: '900', lineHeight: 26 },
  heroDesc: { fontSize: 12, color: Colors.textSecondary, lineHeight: 18 },

  list: { padding: 16, gap: 12 },
  listLabel: {
    fontSize: 11,
    fontWeight: '800',
    color: Colors.textMuted,
    textTransform: 'uppercase',
    letterSpacing: 0.8,
    marginBottom: 4,
  },

  card: {
    backgroundColor: Colors.surface,
    borderRadius: 18,
    padding: 16,
    gap: 12,
    borderWidth: 1,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 1,
    shadowRadius: 8,
    elevation: 2,
  },

  cardTop: {
    flexDirection: 'row',
    alignItems: 'flex-start',
    gap: 12,
  },
  numberCircle: {
    width: 34,
    height: 34,
    borderRadius: 17,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
    marginTop: 2,
  },
  numberText: { fontSize: 14, fontWeight: '900' },

  cardTopRight: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    flexWrap: 'wrap',
    gap: 8,
  },
  iconPill: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 6,
    borderRadius: 20,
    paddingHorizontal: 10,
    paddingVertical: 5,
    flexShrink: 1,
  },
  moduleEmoji: { fontSize: 14 },
  iconPillText: { fontSize: 13, fontWeight: '700', flexShrink: 1 },

  pointsBadge: {
    borderRadius: 20,
    paddingHorizontal: 8,
    paddingVertical: 4,
    flexShrink: 0,
  },
  pointsText: { fontSize: 11, fontWeight: '700' },

  moduleDesc: { fontSize: 12, color: Colors.textSecondary, lineHeight: 18 },

  metaRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
  },
  metaBadge: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 4,
    borderRadius: 20,
    paddingHorizontal: 10,
    paddingVertical: 5,
  },
  metaEmoji: { fontSize: 11 },
  metaText: { fontSize: 11, fontWeight: '700' },

  startBtn: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 4,
    marginLeft: 'auto' as any,
    paddingHorizontal: 12,
    paddingVertical: 5,
    borderRadius: 20,
  },
  startBtnText: { fontSize: 12, fontWeight: '800' },

  emptyState: { flex: 1, alignItems: 'center', justifyContent: 'center', gap: 16 },
  emptyEmoji: { fontSize: 56 },
  emptyText: { fontSize: 16, color: Colors.textSecondary },
});
