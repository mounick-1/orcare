import React, { useState } from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity, TextInput } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { Colors } from '../theme/colors';
import { symptomsList } from '../data/symptomData';
import { RootStackParamList } from '../navigation/AppNavigator';

type Nav = NativeStackNavigationProp<RootStackParamList>;

export default function SymptomCheckerScreen() {
  const navigation = useNavigation<Nav>();
  const [search, setSearch] = useState('');
  const [searchFocused, setSearchFocused] = useState(false);

  const filtered = symptomsList.filter((s) =>
    s.title.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation.goBack()} style={styles.backBtn}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <View style={styles.headerText}>
          <Text style={styles.title}>Symptom Checker</Text>
          <Text style={styles.subtitle}>{symptomsList.length} symptoms tracked</Text>
        </View>
        <View style={styles.headerIcon}>
          <Text style={{ fontSize: 24 }}>🔍</Text>
        </View>
      </View>

      {/* Search */}
      <View style={[styles.searchRow, searchFocused && styles.searchRowFocused]}>
        <Text style={styles.searchIcon}>🔎</Text>
        <TextInput
          style={styles.searchInput}
          placeholder="Search symptoms..."
          placeholderTextColor={Colors.textMuted}
          value={search}
          onChangeText={setSearch}
          onFocus={() => setSearchFocused(true)}
          onBlur={() => setSearchFocused(false)}
        />
        {search.length > 0 && (
          <TouchableOpacity onPress={() => setSearch('')}>
            <Text style={styles.clearBtn}>✕</Text>
          </TouchableOpacity>
        )}
      </View>

      <FlatList
        data={filtered}
        numColumns={2}
        keyExtractor={(item) => item.title}
        columnWrapperStyle={styles.row}
        contentContainerStyle={styles.list}
        showsVerticalScrollIndicator={false}
        renderItem={({ item }) => (
          <TouchableOpacity
            style={[styles.card, { backgroundColor: item.bgColor, borderColor: item.color + '30' }]}
            onPress={() => navigation.navigate('SymptomDetail', { symptomName: item.title.toLowerCase() })}
            activeOpacity={0.8}
          >
            {/* Icon — centered */}
            <View style={[styles.iconBox, { backgroundColor: item.color + '20' }]}>
              <Text style={styles.icon}>{item.icon}</Text>
            </View>

            {/* Name — below icon */}
            <Text style={[styles.name, { color: item.color }]}>{item.title}</Text>

            {/* Divider */}
            <View style={[styles.divider, { backgroundColor: item.color + '25' }]} />

            {/* CTA */}
            <View style={styles.cta}>
              <Text style={[styles.ctaText, { color: item.color }]}>View Info</Text>
              <Text style={[styles.ctaArrow, { color: item.color }]}>→</Text>
            </View>
          </TouchableOpacity>
        )}
        ListEmptyComponent={
          <View style={styles.emptyState}>
            <Text style={styles.emptyEmoji}>🔍</Text>
            <Text style={styles.emptyText}>No symptoms match "{search}"</Text>
            <TouchableOpacity onPress={() => setSearch('')}>
              <Text style={styles.clearSearch}>Clear search</Text>
            </TouchableOpacity>
          </View>
        }
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.background },

  header: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 20,
    paddingTop: 56,
    paddingBottom: 20,
    backgroundColor: Colors.surface,
    borderBottomWidth: 1,
    borderBottomColor: Colors.border,
    gap: 12,
  },
  backBtn: {
    width: 38,
    height: 38,
    borderRadius: 12,
    backgroundColor: Colors.primaryLight,
    alignItems: 'center',
    justifyContent: 'center',
  },
  backArrow: { fontSize: 18, color: Colors.primary, fontWeight: '700' },
  headerText: { flex: 1 },
  title: { fontSize: 20, fontWeight: '800', color: Colors.textPrimary },
  subtitle: { fontSize: 12, color: Colors.textSecondary, marginTop: 2 },
  headerIcon: {
    width: 44,
    height: 44,
    borderRadius: 14,
    backgroundColor: Colors.primaryLight,
    alignItems: 'center',
    justifyContent: 'center',
  },

  searchRow: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.surface,
    marginHorizontal: 20,
    marginTop: 16,
    marginBottom: 4,
    borderRadius: 14,
    borderWidth: 1.5,
    borderColor: Colors.border,
    paddingHorizontal: 14,
    paddingVertical: 10,
    gap: 10,
  },
  searchRowFocused: {
    borderColor: Colors.primary,
    backgroundColor: 'rgba(255,140,66,0.08)',
  },
  searchIcon: { fontSize: 16 },
  searchInput: { flex: 1, fontSize: 14, color: Colors.textPrimary },
  clearBtn: { color: Colors.textMuted, fontSize: 16, fontWeight: '600' },

  list: { padding: 16, gap: 12, paddingBottom: 24 },
  row: { gap: 12 },

  card: {
    flex: 1,
    borderRadius: 20,
    padding: 16,
    alignItems: 'center',
    gap: 10,
    borderWidth: 1,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 1,
    shadowRadius: 6,
    elevation: 2,
  },

  iconBox: {
    width: 64,
    height: 64,
    borderRadius: 20,
    alignItems: 'center',
    justifyContent: 'center',
  },
  icon: { fontSize: 32 },

  name: {
    fontSize: 13,
    fontWeight: '800',
    textAlign: 'center',
    lineHeight: 18,
  },

  divider: {
    width: '100%',
    height: 1,
    borderRadius: 1,
  },

  cta: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 4,
  },
  ctaText: { fontSize: 11, fontWeight: '700' },
  ctaArrow: { fontSize: 13, fontWeight: '800' },

  emptyState: { alignItems: 'center', paddingTop: 60, gap: 12 },
  emptyEmoji: { fontSize: 48 },
  emptyText: { fontSize: 14, color: Colors.textSecondary },
  clearSearch: { fontSize: 13, color: Colors.primary, fontWeight: '700' },
});
