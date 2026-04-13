import React, { useState } from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { tipsList, tipCategories, getDailyTip } from '../data/tipData';

type Props = NativeStackScreenProps<RootStackParamList, 'DailyTips'>;

const categoryColors: Record<string, { bg: string; text: string }> = {
  All: { bg: Colors.primaryLight, text: Colors.primary },
  Hygiene: { bg: Colors.secondaryLight, text: Colors.secondary },
  Food: { bg: Colors.amberLight, text: Colors.amber },
  Lifestyle: { bg: Colors.infoLight, text: Colors.info },
  'Myth Busting': { bg: Colors.errorLight, text: Colors.error },
  'Age 7-9': { bg: Colors.violetLight, text: Colors.violet },
};
// Fix dark-theme backgrounds for tip cards
const getDarkBg = (color: string) => color + '1A';

export default function DailyTipsScreen({ navigation }: Props) {
  const [selectedCategory, setSelectedCategory] = useState('All');
  const dailyTip = getDailyTip();

  const filtered =
    selectedCategory === 'All' ? tipsList : tipsList.filter((t) => t.category === selectedCategory);

  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity style={styles.backBtn} onPress={() => navigation.goBack()}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <View style={styles.headerText}>
          <Text style={styles.title}>Daily Tips</Text>
          <Text style={styles.subtitle}>{tipsList.length} oral health tips</Text>
        </View>
        <View style={styles.headerBadge}>
          <Text style={{ fontSize: 24 }}>💡</Text>
        </View>
      </View>

      <FlatList
        data={filtered}
        keyExtractor={(item) => item.id.toString()}
        contentContainerStyle={styles.list}
        showsVerticalScrollIndicator={false}
        ListHeaderComponent={
          <View style={styles.listHeaderArea}>
            {/* Today's Tip Banner */}
            <View style={styles.dailyCard}>
              <View style={styles.dailyTop}>
                <View style={styles.dailyBadge}>
                  <Text style={styles.dailyBadgeText}>TODAY'S TIP</Text>
                </View>
                <Text style={styles.dailyEmoji}>{dailyTip.icon}</Text>
              </View>
              <Text style={styles.dailyTitle}>{dailyTip.title}</Text>
              <Text style={styles.dailyDesc}>{dailyTip.description}</Text>
              <View style={styles.dailyCategoryTag}>
                <Text style={styles.dailyCategoryText}>{dailyTip.category}</Text>
              </View>
            </View>

            {/* Category Filter */}
            <FlatList
              data={tipCategories}
              horizontal
              keyExtractor={(item) => item}
              showsHorizontalScrollIndicator={false}
              contentContainerStyle={styles.filterList}
              style={styles.filterScroll}
              renderItem={({ item }) => {
                const colors = categoryColors[item] ?? { bg: Colors.primaryLight, text: Colors.primary };
                const isActive = selectedCategory === item;
                return (
                  <TouchableOpacity
                    style={[
                      styles.filterChip,
                      { backgroundColor: isActive ? colors.text : colors.bg },
                    ]}
                    onPress={() => setSelectedCategory(item)}
                    activeOpacity={0.8}
                  >
                    <Text style={[
                      styles.filterChipText,
                      { color: isActive ? Colors.textInverse : colors.text }
                    ]}>
                      {item}
                    </Text>
                  </TouchableOpacity>
                );
              }}
            />

            <Text style={styles.sectionTitle}>
              {selectedCategory === 'All' ? 'All Tips' : selectedCategory}
              <Text style={styles.sectionCount}> ({filtered.length})</Text>
            </Text>
          </View>
        }
        renderItem={({ item }) => {
          const colors = categoryColors[item.category] ?? { bg: Colors.primaryLight, text: Colors.primary };
          return (
            <View style={styles.tipCard}>
              <View style={[styles.tipIconBox, { backgroundColor: colors.bg }]}>
                <Text style={styles.tipEmoji}>{item.icon}</Text>
              </View>
              <View style={styles.tipContent}>
                <View style={styles.tipTop}>
                  <Text style={styles.tipTitle} numberOfLines={2}>{item.title}</Text>
                  <View style={[styles.categoryBadge, { backgroundColor: colors.bg }]}>
                    <Text style={[styles.categoryText, { color: colors.text }]}>{item.category}</Text>
                  </View>
                </View>
                <Text style={styles.tipDesc} numberOfLines={2}>{item.description}</Text>
              </View>
            </View>
          );
        }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.background },

  header: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 20,
    paddingTop: 56,
    backgroundColor: Colors.surface,
    borderBottomWidth: 1,
    borderBottomColor: Colors.border,
    gap: 14,
  },
  backBtn: {
    width: 40,
    height: 40,
    borderRadius: 12,
    backgroundColor: Colors.primaryLight,
    alignItems: 'center',
    justifyContent: 'center',
  },
  backArrow: { fontSize: 18, color: Colors.primary, fontWeight: '700' },
  headerText: { flex: 1 },
  title: { fontSize: 20, fontWeight: '800', color: Colors.textPrimary },
  subtitle: { fontSize: 12, color: Colors.textSecondary, marginTop: 2 },
  headerBadge: {
    width: 48,
    height: 48,
    borderRadius: 16,
    backgroundColor: Colors.amberLight,
    alignItems: 'center',
    justifyContent: 'center',
  },

  list: { gap: 10 },
  listHeaderArea: { padding: 16, gap: 16 },

  dailyCard: {
    backgroundColor: Colors.primary,
    borderRadius: 20,
    padding: 20,
    gap: 10,
    shadowColor: Colors.primary,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 12,
    elevation: 6,
  },
  dailyTop: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center' },
  dailyBadge: {
    backgroundColor: 'rgba(255,255,255,0.2)',
    borderRadius: 20,
    paddingHorizontal: 10,
    paddingVertical: 4,
  },
  dailyBadgeText: {
    color: Colors.textInverse,
    fontSize: 10,
    fontWeight: '800',
    letterSpacing: 1,
  },
  dailyEmoji: { fontSize: 32 },
  dailyTitle: { fontSize: 17, fontWeight: '800', color: Colors.textInverse, lineHeight: 24 },
  dailyDesc: { fontSize: 13, color: 'rgba(255,255,255,0.85)', lineHeight: 20 },
  dailyCategoryTag: {
    alignSelf: 'flex-start',
    backgroundColor: 'rgba(255,255,255,0.15)',
    borderRadius: 20,
    paddingHorizontal: 10,
    paddingVertical: 4,
  },
  dailyCategoryText: { fontSize: 11, color: Colors.textInverse, fontWeight: '700' },

  filterScroll: { marginHorizontal: -4 },
  filterList: { gap: 8, paddingHorizontal: 4 },
  filterChip: {
    paddingHorizontal: 16,
    paddingVertical: 8,
    borderRadius: 20,
  },
  filterChipText: { fontSize: 13, fontWeight: '700' },

  sectionTitle: {
    fontSize: 14,
    fontWeight: '800',
    color: Colors.textPrimary,
    marginTop: 4,
  },
  sectionCount: { color: Colors.textMuted, fontWeight: '400' },

  tipCard: {
    flexDirection: 'row',
    backgroundColor: Colors.surface,
    borderRadius: 16,
    padding: 14,
    gap: 12,
    borderWidth: 1,
    borderColor: Colors.border,
    alignItems: 'flex-start',
    marginHorizontal: 16,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 1,
    shadowRadius: 4,
    elevation: 1,
  },
  tipIconBox: {
    width: 46,
    height: 46,
    borderRadius: 14,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },
  tipEmoji: { fontSize: 22 },
  tipContent: { flex: 1, gap: 6 },
  tipTop: { flexDirection: 'row', alignItems: 'flex-start', gap: 8 },
  tipTitle: { fontSize: 13, fontWeight: '700', color: Colors.textPrimary, flex: 1, lineHeight: 18 },
  categoryBadge: {
    borderRadius: 20,
    paddingHorizontal: 8,
    paddingVertical: 3,
    flexShrink: 0,
  },
  categoryText: { fontSize: 10, fontWeight: '700' },
  tipDesc: { fontSize: 12, color: Colors.textSecondary, lineHeight: 17 },
});
