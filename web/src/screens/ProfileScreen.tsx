import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { Pencil, Bell, Lightbulb, Shield, FileText, HelpCircle, Trash2, ChevronRight, GraduationCap, Stethoscope } from 'lucide-react-native';
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
        { Icon: Pencil, label: 'Edit Profile', sub: 'Update your information', onPress: () => navigation.navigate('EditProfile'), fg: Colors.primary, bg: Colors.primaryLight },
        { Icon: Bell, label: 'Reminders', sub: 'Manage hygiene alerts', onPress: () => navigation.navigate('Reminders'), fg: Colors.amber, bg: Colors.amberLight },
        { Icon: Lightbulb, label: 'Daily Tips', sub: 'Browse all oral health tips', onPress: () => navigation.navigate('DailyTips'), fg: Colors.accent, bg: Colors.accentLight },
      ],
    },
    {
      title: 'Settings',
      items: [
        { Icon: Shield, label: 'Privacy & Security', sub: 'Password and data settings', onPress: () => navigation.navigate('PrivacySecurity'), fg: Colors.success, bg: Colors.successLight },
        { Icon: FileText, label: 'Privacy Policy', sub: 'Read our privacy policy', onPress: () => navigation.navigate('PrivacyPolicy'), fg: Colors.info, bg: Colors.infoLight },
        { Icon: HelpCircle, label: 'Help & Feedback', sub: 'FAQs and contact support', onPress: () => navigation.navigate('HelpFeedback'), fg: Colors.violet, bg: Colors.violetLight },
      ],
    },
    {
      title: 'Account',
      items: [
        {
          Icon: Trash2, label: 'Log Out', sub: 'Sign out of your account', fg: Colors.rose, bg: Colors.roseLight,
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
            { Icon: Bell, val: '10', lbl: 'Reminders', fg: Colors.primary },
            { Icon: GraduationCap, val: '24', lbl: 'Modules', fg: Colors.success },
            { Icon: Stethoscope, val: '6', lbl: 'Diseases', fg: Colors.accent },
          ].map((s) => (
            <View key={s.lbl} style={styles.statItem}>
              <View style={[styles.statIconBox, { backgroundColor: s.fg + '15' }]}>
                <s.Icon size={16} color={s.fg} strokeWidth={2} />
              </View>
              <Text style={[styles.statVal, { color: s.fg }]}>{s.val}</Text>
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
                <View style={[styles.menuIconBox, { backgroundColor: item.bg }]}>
                  <item.Icon size={18} color={item.fg} strokeWidth={2} />
                </View>
                <View style={styles.menuText}>
                  <Text style={[styles.menuLabel, { color: item.fg === Colors.rose ? Colors.rose : Colors.textPrimary }]}>
                    {item.label}
                  </Text>
                  <Text style={styles.menuSub}>{item.sub}</Text>
                </View>
                <ChevronRight size={18} color={Colors.textMuted} strokeWidth={2} />
              </TouchableOpacity>
            ))}
          </View>
        </View>
      ))}

      <Text style={styles.version}>ORCare v1.0.0 · Powered by Gemini AI</Text>
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
    borderColor: Colors.primary + '40',
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
  avatarText: { color: Colors.textInverse, fontSize: 32, fontFamily: 'Inter_800ExtraBold' },
  name: { fontSize: 22, fontFamily: 'Inter_800ExtraBold', color: Colors.textPrimary },
  email: { fontSize: 13, color: Colors.textSecondary, fontFamily: 'Inter_400Regular' },
  badges: { flexDirection: 'row', flexWrap: 'wrap', gap: 8, justifyContent: 'center', marginTop: 4 },
  badge: {
    backgroundColor: Colors.primaryLight,
    borderRadius: 20,
    paddingHorizontal: 12,
    paddingVertical: 4,
  },
  badgeText: { color: Colors.primary, fontSize: 12, fontFamily: 'Inter_600SemiBold' },

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
  statItem: { alignItems: 'center', gap: 4 },
  statIconBox: {
    width: 36,
    height: 36,
    borderRadius: 10,
    alignItems: 'center',
    justifyContent: 'center',
  },
  statVal: { fontSize: 20, fontFamily: 'Inter_800ExtraBold' },
  statLbl: { fontSize: 11, color: Colors.textSecondary, fontFamily: 'Inter_500Medium' },

  group: { paddingHorizontal: 20, marginTop: 24 },
  groupTitle: { fontSize: 11, fontFamily: 'Inter_700Bold', color: Colors.textMuted, textTransform: 'uppercase', letterSpacing: 0.8, marginBottom: 10 },
  groupCard: {
    backgroundColor: Colors.surface,
    borderRadius: 16,
    borderWidth: 1,
    borderColor: Colors.border,
    overflow: 'hidden',
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 1,
    shadowRadius: 4,
    elevation: 2,
  },
  menuItem: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 16,
    gap: 14,
  },
  menuItemBorder: { borderBottomWidth: 1, borderBottomColor: Colors.borderLight },
  menuIconBox: {
    width: 40,
    height: 40,
    borderRadius: 12,
    alignItems: 'center',
    justifyContent: 'center',
  },
  menuText: { flex: 1 },
  menuLabel: { fontSize: 15, fontFamily: 'Inter_600SemiBold', color: Colors.textPrimary },
  menuSub: { fontSize: 12, color: Colors.textSecondary, marginTop: 1, fontFamily: 'Inter_400Regular' },

  version: { textAlign: 'center', color: Colors.textMuted, fontSize: 11, marginTop: 16, fontFamily: 'Inter_400Regular' },
});
