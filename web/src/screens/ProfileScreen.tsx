import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, Alert } from 'react-native';
// Alert used for logout confirmation
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { Colors } from '../theme/colors';
import { useAuth } from '../context/AuthContext';
import { RootStackParamList } from '../navigation/AppNavigator';

type Nav = NativeStackNavigationProp<RootStackParamList>;

export default function ProfileScreen() {
  const navigation = useNavigation<Nav>();
  const { user, logout } = useAuth();

  const menuGroups = [
    {
      title: 'My Tools',
      items: [
        { emoji: '✏️', label: 'Edit Profile', sub: 'Update your information', onPress: () => navigation.navigate('EditProfile') },
        { emoji: '🔔', label: 'Reminders', sub: 'Manage hygiene alerts', onPress: () => navigation.navigate('Reminders') },
        { emoji: '💡', label: 'Daily Tips', sub: 'Browse all oral health tips', onPress: () => navigation.navigate('DailyTips') },
      ],
    },
    {
      title: 'Settings',
      items: [
        { emoji: '🛡️', label: 'Privacy & Security', sub: 'Password and data settings', onPress: () => navigation.navigate('PrivacySecurity') },
        { emoji: '📄', label: 'Privacy Policy', sub: 'Read our privacy policy', onPress: () => navigation.navigate('PrivacyPolicy') },
        { emoji: '💬', label: 'Help & Feedback', sub: 'FAQs and contact support', onPress: () => navigation.navigate('HelpFeedback') },
      ],
    },
    {
      title: 'Account',
      items: [
        {
          emoji: '🚪', label: 'Log Out', sub: 'Sign out of your account', color: Colors.rose,
          onPress: () => Alert.alert('Log Out', 'Are you sure?', [
            { text: 'Cancel', style: 'cancel' },
            { text: 'Log Out', style: 'destructive', onPress: logout },
          ]),
        },
      ],
    },
  ];

  return (
    <ScrollView style={styles.container} showsVerticalScrollIndicator={false}>
      {/* Profile Hero */}
      <View style={styles.hero}>
        <View style={styles.avatarRing}>
          <View style={styles.avatar}>
            <Text style={styles.avatarText}>{(user?.name ?? 'G')[0].toUpperCase()}</Text>
          </View>
        </View>
        <Text style={styles.name}>{user?.name ?? 'Guest User'}</Text>
        {user?.email ? <Text style={styles.email}>{user.email}</Text> : null}

        <View style={styles.badges}>
          {user?.gender && (
            <View style={styles.badge}>
              <Text style={styles.badgeText}>{user.gender}</Text>
            </View>
          )}
          {user?.age && (
            <View style={styles.badge}>
              <Text style={styles.badgeText}>Age {user.age}</Text>
            </View>
          )}
        </View>

        {/* Quick Stats */}
        <View style={styles.statsRow}>
          {[
            { emoji: '🔔', val: '10', lbl: 'Reminders' },
            { emoji: '📚', val: '24', lbl: 'Modules' },
            { emoji: '🦷', val: '6', lbl: 'Diseases' },
          ].map((s) => (
            <View key={s.lbl} style={styles.statItem}>
              <Text style={styles.statEmoji}>{s.emoji}</Text>
              <Text style={styles.statVal}>{s.val}</Text>
              <Text style={styles.statLbl}>{s.lbl}</Text>
            </View>
          ))}
        </View>
      </View>

      {/* Menu Groups */}
      {menuGroups.map((group) => (
        <View key={group.title} style={styles.group}>
          <Text style={styles.groupTitle}>{group.title}</Text>
          <View style={styles.groupCard}>
            {group.items.map((item, i) => (
              <TouchableOpacity
                key={item.label}
                style={[styles.menuItem, i < group.items.length - 1 && styles.menuItemBorder]}
                onPress={item.onPress}
                activeOpacity={0.7}
              >
                <View style={[styles.menuIconBox, { backgroundColor: (item as any).color ? Colors.roseLight : Colors.primaryLight }]}>
                  <Text style={styles.menuEmoji}>{item.emoji}</Text>
                </View>
                <View style={styles.menuText}>
                  <Text style={[styles.menuLabel, (item as any).color && { color: (item as any).color }]}>
                    {item.label}
                  </Text>
                  <Text style={styles.menuSub}>{item.sub}</Text>
                </View>
                <Text style={styles.menuArrow}>›</Text>
              </TouchableOpacity>
            ))}
          </View>
        </View>
      ))}

      <Text style={styles.version}>ORCare Web v1.0.0 · Powered by Gemini AI</Text>
      <View style={{ height: 32 }} />
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.background },

  hero: {
    alignItems: 'center',
    padding: 24,
    paddingTop: 56,
    backgroundColor: Colors.surface,
    borderBottomWidth: 1,
    borderBottomColor: Colors.border,
    gap: 8,
  },
  avatarRing: {
    width: 88,
    height: 88,
    borderRadius: 44,
    borderWidth: 3,
    borderColor: Colors.primary + '60',
    padding: 3,
    marginBottom: 4,
  },
  avatar: {
    flex: 1,
    borderRadius: 38,
    backgroundColor: Colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
  },
  avatarText: { color: Colors.textInverse, fontSize: 32, fontWeight: '900' },
  name: { fontSize: 22, fontWeight: '800', color: Colors.textPrimary },
  email: { fontSize: 13, color: Colors.textSecondary },
  badges: { flexDirection: 'row', flexWrap: 'wrap', gap: 8, justifyContent: 'center', marginTop: 4 },
  badge: {
    backgroundColor: Colors.primaryLight,
    borderRadius: 20,
    paddingHorizontal: 12,
    paddingVertical: 4,
  },
  badgeText: { color: Colors.primary, fontSize: 12, fontWeight: '600' },

  statsRow: {
    flexDirection: 'row',
    gap: 24,
    marginTop: 12,
    paddingTop: 16,
    borderTopWidth: 1,
    borderTopColor: Colors.border,
    width: '100%',
    justifyContent: 'center',
  },
  statItem: { alignItems: 'center', gap: 2 },
  statEmoji: { fontSize: 20 },
  statVal: { fontSize: 20, fontWeight: '800', color: Colors.primary },
  statLbl: { fontSize: 11, color: Colors.textSecondary },

  group: { paddingHorizontal: 20, marginTop: 24 },
  groupTitle: { fontSize: 12, fontWeight: '700', color: Colors.textMuted, textTransform: 'uppercase', letterSpacing: 0.8, marginBottom: 10 },
  groupCard: {
    backgroundColor: Colors.surface,
    borderRadius: 18,
    borderWidth: 1,
    borderColor: Colors.border,
    overflow: 'hidden',
  },
  menuItem: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 16,
    gap: 14,
  },
  menuItemBorder: { borderBottomWidth: 1, borderBottomColor: Colors.border },
  menuIconBox: {
    width: 40,
    height: 40,
    borderRadius: 12,
    alignItems: 'center',
    justifyContent: 'center',
  },
  menuEmoji: { fontSize: 20 },
  menuText: { flex: 1 },
  menuLabel: { fontSize: 15, fontWeight: '600', color: Colors.textPrimary },
  menuSub: { fontSize: 12, color: Colors.textSecondary, marginTop: 1 },
  menuArrow: { fontSize: 24, color: Colors.textMuted },

  version: { textAlign: 'center', color: Colors.textMuted, fontSize: 11, marginTop: 16 },
});
