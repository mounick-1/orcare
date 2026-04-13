import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity, ScrollView } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { useAuth } from '../context/AuthContext';

type Props = NativeStackScreenProps<RootStackParamList, 'LanguageSelection'>;

const languages = [
  { code: 'English', label: 'English', native: 'English', flag: '🇬🇧', desc: 'Default language' },
  { code: 'Tamil', label: 'Tamil', native: 'தமிழ்', flag: '🇮🇳', desc: 'தமிழ் மொழி' },
  { code: 'Telugu', label: 'Telugu', native: 'తెలుగు', flag: '🇮🇳', desc: 'తెలుగు భాష' },
  { code: 'Hindi', label: 'Hindi', native: 'हिंदी', flag: '🇮🇳', desc: 'हिंदी भाषा' },
];

export default function LanguageSelectionScreen({ navigation }: Props) {
  const { language, setLanguage } = useAuth();

  async function handleSelect(lang: string) {
    await setLanguage(lang);
  }

  return (
    <View style={styles.container}>
      {/* Hero */}
      <View style={styles.hero}>
        <View style={styles.heroIconRing}>
          <View style={styles.heroIconBox}>
            <Text style={styles.heroEmoji}>🌐</Text>
          </View>
        </View>
        <Text style={styles.heroTitle}>Choose Your Language</Text>
        <Text style={styles.heroSubtitle}>
          Select the language you're most comfortable with. You can change this anytime in settings.
        </Text>
      </View>

      <ScrollView
        contentContainerStyle={styles.langList}
        showsVerticalScrollIndicator={false}
      >
        {languages.map((lang) => {
          const isActive = language === lang.code;
          return (
            <TouchableOpacity
              key={lang.code}
              style={[styles.langCard, isActive && styles.langCardActive]}
              onPress={() => handleSelect(lang.code)}
              activeOpacity={0.8}
            >
              <View style={[styles.flagBox, isActive && styles.flagBoxActive]}>
                <Text style={styles.flag}>{lang.flag}</Text>
              </View>
              <View style={styles.langText}>
                <Text style={[styles.langLabel, isActive && styles.langLabelActive]}>
                  {lang.label}
                </Text>
                <Text style={styles.langNative}>{lang.native} · {lang.desc}</Text>
              </View>
              <View style={[styles.checkBox, isActive && styles.checkBoxActive]}>
                {isActive && <Text style={styles.checkIcon}>✓</Text>}
              </View>
            </TouchableOpacity>
          );
        })}
      </ScrollView>

      <View style={styles.footer}>
        <TouchableOpacity
          style={[styles.continueBtn, !language && styles.continueBtnDisabled]}
          onPress={() => navigation.navigate('Onboarding')}
        >
          <Text style={styles.continueBtnText}>Continue →</Text>
        </TouchableOpacity>
        <Text style={styles.footerNote}>You can change this later in Profile settings</Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.background,
    paddingHorizontal: 24,
    paddingTop: 60,
    paddingBottom: 32,
  },

  hero: { alignItems: 'center', marginBottom: 36, gap: 12 },
  heroIconRing: {
    width: 96,
    height: 96,
    borderRadius: 48,
    backgroundColor: Colors.primaryLight,
    alignItems: 'center',
    justifyContent: 'center',
    borderWidth: 3,
    borderColor: Colors.primary + '30',
  },
  heroIconBox: {
    width: 72,
    height: 72,
    borderRadius: 36,
    backgroundColor: Colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
  },
  heroEmoji: { fontSize: 36 },
  heroTitle: { fontSize: 24, fontWeight: '800', color: Colors.textPrimary, textAlign: 'center' },
  heroSubtitle: {
    fontSize: 13,
    color: Colors.textSecondary,
    textAlign: 'center',
    lineHeight: 20,
    paddingHorizontal: 8,
  },

  langList: { gap: 12, paddingBottom: 16 },
  langCard: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.surface,
    borderRadius: 18,
    padding: 16,
    borderWidth: 2,
    borderColor: Colors.border,
    gap: 14,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 1,
    shadowRadius: 6,
    elevation: 2,
  },
  langCardActive: {
    borderColor: Colors.primary,
    backgroundColor: Colors.primaryLight + '60',
  },
  flagBox: {
    width: 52,
    height: 52,
    borderRadius: 16,
    backgroundColor: Colors.background,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },
  flagBoxActive: { backgroundColor: Colors.primary + '15' },
  flag: { fontSize: 28 },
  langText: { flex: 1 },
  langLabel: { fontSize: 17, fontWeight: '700', color: Colors.textPrimary },
  langLabelActive: { color: Colors.primary },
  langNative: { fontSize: 12, color: Colors.textSecondary, marginTop: 2 },
  checkBox: {
    width: 28,
    height: 28,
    borderRadius: 14,
    borderWidth: 2,
    borderColor: Colors.border,
    alignItems: 'center',
    justifyContent: 'center',
  },
  checkBoxActive: {
    backgroundColor: Colors.primary,
    borderColor: Colors.primary,
  },
  checkIcon: { fontSize: 14, color: Colors.textInverse, fontWeight: '800' },

  footer: { gap: 12, marginTop: 16 },
  continueBtn: {
    backgroundColor: Colors.primary,
    borderRadius: 16,
    padding: 16,
    alignItems: 'center',
    shadowColor: Colors.primary,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 8,
  },
  continueBtnDisabled: { opacity: 0.7 },
  continueBtnText: { color: Colors.textInverse, fontSize: 16, fontWeight: '800' },
  footerNote: { fontSize: 12, color: Colors.textMuted, textAlign: 'center' },
});
