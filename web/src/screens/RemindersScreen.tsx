import React, { useState, useMemo } from 'react';
import {
  View, Text, StyleSheet, FlatList, TouchableOpacity, Switch, Alert, TextInput, Modal,
} from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { ArrowLeft } from 'lucide-react-native';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { Reminder, defaultReminders } from '../data/reminderData';

type Props = NativeStackScreenProps<RootStackParamList, 'Reminders'>;

type TimePeriod = 'morning' | 'afternoon' | 'evening';

const periodInfo: Record<TimePeriod, { label: string; icon: string; subLabel: string; color: string; bgColor: string }> = {
  morning: { label: 'Morning Routine', icon: '🌅', subLabel: '5 AM – 12 PM', color: '#F59E0B', bgColor: '#F59E0B1A' },
  afternoon: { label: 'Afternoon Care', icon: '☀️', subLabel: '12 PM – 6 PM', color: '#22C55E', bgColor: '#22C55E1A' },
  evening: { label: 'Evening Routine', icon: '🌙', subLabel: '6 PM – 12 AM', color: '#8B5CF6', bgColor: '#8B5CF61A' },
};

function getTimePeriod(time: string): TimePeriod {
  const hour = parseInt(time.split(':')[0], 10);
  if (hour >= 5 && hour < 12) return 'morning';
  if (hour >= 12 && hour < 18) return 'afternoon';
  return 'evening';
}

type ListItem =
  | { type: 'header'; period: TimePeriod; count: number; activeCount: number }
  | { type: 'reminder'; reminder: Reminder };

export default function RemindersScreen({ navigation }: Props) {
  const [reminders, setReminders] = useState<Reminder[]>(defaultReminders);
  const [editing, setEditing] = useState<Reminder | null>(null);
  const [editTime, setEditTime] = useState('');

  function toggleReminder(id: number) {
    setReminders((prev) =>
      prev.map((r) => (r.id === id ? { ...r, isEnabled: !r.isEnabled } : r))
    );
  }

  function openEdit(reminder: Reminder) {
    setEditing(reminder);
    setEditTime(reminder.time);
  }

  function saveEdit() {
    if (!editing) return;
    const timeRegex = /^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$/;
    if (!timeRegex.test(editTime)) {
      Alert.alert('Invalid time', 'Please enter time in HH:MM format (e.g., 07:30)');
      return;
    }
    setReminders((prev) =>
      prev.map((r) => (r.id === editing.id ? { ...r, time: editTime } : r))
    );
    setEditing(null);
  }

  const activeCount = reminders.filter((r) => r.isEnabled).length;

  const listData = useMemo<ListItem[]>(() => {
    const periods: TimePeriod[] = ['morning', 'afternoon', 'evening'];
    const items: ListItem[] = [];
    for (const period of periods) {
      const group = reminders
        .filter((r) => getTimePeriod(r.time) === period)
        .sort((a, b) => a.time.localeCompare(b.time));
      if (group.length > 0) {
        items.push({
          type: 'header',
          period,
          count: group.length,
          activeCount: group.filter((r) => r.isEnabled).length,
        });
        items.push(...group.map((r) => ({ type: 'reminder' as const, reminder: r })));
      }
    }
    return items;
  }, [reminders]);

  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity style={styles.backBtn} onPress={() => navigation.goBack()}>
          <ArrowLeft size={20} color={Colors.primary} strokeWidth={2.5} />
        </TouchableOpacity>
        <View style={styles.headerText}>
          <Text style={styles.title}>Reminders</Text>
          <Text style={styles.subtitle}>Manage your oral hygiene alerts</Text>
        </View>
        <View style={styles.activeBadge}>
          <Text style={styles.activeEmoji}>🔔</Text>
          <Text style={styles.activeBadgeText}>{activeCount} active</Text>
        </View>
      </View>

      {/* Stats Banner */}
      <View style={styles.statsBanner}>
        <View style={styles.statItem}>
          <Text style={styles.statVal}>{activeCount}</Text>
          <Text style={styles.statLabel}>Active</Text>
        </View>
        <View style={styles.statsDivider} />
        <View style={styles.statItem}>
          <Text style={styles.statVal}>{reminders.length - activeCount}</Text>
          <Text style={styles.statLabel}>Paused</Text>
        </View>
        <View style={styles.statsDivider} />
        <View style={styles.statItem}>
          <Text style={styles.statVal}>{reminders.length}</Text>
          <Text style={styles.statLabel}>Total</Text>
        </View>
      </View>

      <FlatList
        data={listData}
        keyExtractor={(item) =>
          item.type === 'header' ? `header-${item.period}` : `reminder-${item.reminder.id}`
        }
        contentContainerStyle={styles.list}
        showsVerticalScrollIndicator={false}
        renderItem={({ item }) => {
          if (item.type === 'header') {
            const info = periodInfo[item.period];
            return (
              <View style={[styles.sectionHeader, { borderLeftColor: info.color }]}>
                <View style={[styles.sectionIconBox, { backgroundColor: info.bgColor }]}>
                  <Text style={styles.sectionIcon}>{info.icon}</Text>
                </View>
                <View style={styles.sectionHeaderText}>
                  <Text style={[styles.sectionLabel, { color: info.color }]}>{info.label}</Text>
                  <Text style={styles.sectionSubLabel}>{info.subLabel}</Text>
                </View>
                <View style={[styles.sectionBadge, { backgroundColor: info.bgColor }]}>
                  <Text style={[styles.sectionBadgeText, { color: info.color }]}>
                    {item.activeCount}/{item.count} on
                  </Text>
                </View>
              </View>
            );
          }

          const r = item.reminder;
          return (
            <View style={[styles.card, !r.isEnabled && styles.cardDisabled]}>
              <View style={[styles.iconBox, { backgroundColor: r.bgColor }]}>
                <Text style={styles.icon}>{r.icon}</Text>
              </View>
              <View style={styles.info}>
                <Text style={[styles.reminderTitle, !r.isEnabled && styles.disabledTitle]}>
                  {r.title}
                </Text>
                <TouchableOpacity onPress={() => r.isEnabled && openEdit(r)} disabled={!r.isEnabled}>
                  <View style={[styles.timeTag, { backgroundColor: r.isEnabled ? r.bgColor : Colors.borderLight }]}>
                    <Text style={[styles.timeText, { color: r.isEnabled ? r.color : Colors.textMuted }]}>
                      ⏰ {r.time}
                    </Text>
                    {r.isEnabled && (
                      <Text style={[styles.editHint, { color: r.color }]}>edit</Text>
                    )}
                  </View>
                </TouchableOpacity>
              </View>
              <Switch
                value={r.isEnabled}
                onValueChange={() => toggleReminder(r.id)}
                trackColor={{ false: Colors.borderLight, true: r.color + '50' }}
                thumbColor={r.isEnabled ? r.color : Colors.textMuted}
              />
            </View>
          );
        }}
        ListFooterComponent={<View style={{ height: 24 }} />}
      />

      {/* Edit Time Modal */}
      <Modal
        visible={editing !== null}
        transparent
        animationType="fade"
        onRequestClose={() => setEditing(null)}
      >
        <View style={styles.modalOverlay}>
          <View style={styles.modalCard}>
            <View style={styles.modalHeader}>
              <View style={[styles.modalIcon, { backgroundColor: editing?.bgColor ?? Colors.primaryLight }]}>
                <Text style={styles.modalIconEmoji}>{editing?.icon}</Text>
              </View>
              <View style={styles.modalHeaderText}>
                <Text style={styles.modalTitle}>Edit Time</Text>
                <Text style={styles.modalSubtitle}>{editing?.title}</Text>
              </View>
            </View>

            <TextInput
              style={styles.modalInput}
              value={editTime}
              onChangeText={setEditTime}
              placeholder="HH:MM"
              placeholderTextColor={Colors.textMuted}
              keyboardType="numbers-and-punctuation"
              maxLength={5}
            />

            <Text style={styles.modalHint}>Format: HH:MM (e.g., 07:30, 22:00)</Text>

            <View style={styles.modalBtns}>
              <TouchableOpacity style={styles.modalCancelBtn} onPress={() => setEditing(null)}>
                <Text style={styles.modalCancelText}>Cancel</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.modalSaveBtn} onPress={saveEdit}>
                <Text style={styles.modalSaveText}>Save Time</Text>
              </TouchableOpacity>
            </View>
          </View>
        </View>
      </Modal>
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
  activeBadge: {
    alignItems: 'center',
    backgroundColor: Colors.primaryLight,
    borderRadius: 12,
    paddingHorizontal: 10,
    paddingVertical: 6,
    gap: 2,
  },
  activeEmoji: { fontSize: 16 },
  activeBadgeText: { fontSize: 11, color: Colors.primary, fontWeight: '700' },

  statsBanner: {
    flexDirection: 'row',
    backgroundColor: Colors.primary,
    marginHorizontal: 16,
    marginTop: 16,
    borderRadius: 18,
    padding: 16,
  },
  statItem: { flex: 1, alignItems: 'center', gap: 2 },
  statVal: { fontSize: 24, fontWeight: '900', color: Colors.textInverse },
  statLabel: { fontSize: 11, color: 'rgba(255,255,255,0.7)', fontWeight: '600' },
  statsDivider: { width: 1, backgroundColor: 'rgba(255,255,255,0.2)', marginVertical: 4 },

  list: { padding: 16, gap: 10 },

  sectionHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 12,
    borderRadius: 14,
    borderLeftWidth: 3,
    paddingHorizontal: 14,
    paddingVertical: 12,
    marginTop: 6,
    backgroundColor: Colors.surfaceElevated,
    borderWidth: 1,
    borderColor: Colors.border,
  },
  sectionIconBox: {
    width: 40,
    height: 40,
    borderRadius: 12,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },
  sectionIcon: { fontSize: 20 },
  sectionHeaderText: { flex: 1, gap: 2 },
  sectionLabel: { fontSize: 14, fontWeight: '800' },
  sectionSubLabel: { fontSize: 11, color: Colors.textMuted, fontWeight: '500' },
  sectionBadge: { borderRadius: 20, paddingHorizontal: 10, paddingVertical: 4 },
  sectionBadgeText: { fontSize: 11, fontWeight: '700' },

  card: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.surface,
    borderRadius: 18,
    padding: 14,
    gap: 14,
    borderWidth: 1,
    borderColor: Colors.border,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 1,
    shadowRadius: 6,
    elevation: 2,
  },
  cardDisabled: { opacity: 0.55 },
  iconBox: {
    width: 46,
    height: 46,
    borderRadius: 14,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },
  icon: { fontSize: 22 },
  info: { flex: 1, gap: 6 },
  reminderTitle: { fontSize: 14, fontWeight: '700', color: Colors.textPrimary },
  disabledTitle: { color: Colors.textMuted },
  timeTag: {
    flexDirection: 'row',
    alignItems: 'center',
    alignSelf: 'flex-start',
    borderRadius: 20,
    paddingHorizontal: 10,
    paddingVertical: 4,
    gap: 6,
  },
  timeText: { fontSize: 12, fontWeight: '600' },
  editHint: { fontSize: 10, fontWeight: '700', textTransform: 'uppercase', letterSpacing: 0.5 },

  modalOverlay: {
    flex: 1,
    backgroundColor: Colors.shadowNeutral,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 24,
  },
  modalCard: {
    backgroundColor: Colors.surface,
    borderRadius: 24,
    padding: 24,
    width: '100%',
    gap: 16,
    shadowColor: Colors.shadowNeutral,
    shadowOffset: { width: 0, height: 8 },
    shadowOpacity: 1,
    shadowRadius: 24,
    elevation: 12,
  },
  modalHeader: { flexDirection: 'row', alignItems: 'center', gap: 14 },
  modalIcon: {
    width: 48,
    height: 48,
    borderRadius: 14,
    alignItems: 'center',
    justifyContent: 'center',
  },
  modalIconEmoji: { fontSize: 24 },
  modalHeaderText: { flex: 1 },
  modalTitle: { fontSize: 18, fontWeight: '800', color: Colors.textPrimary },
  modalSubtitle: { fontSize: 13, color: Colors.textSecondary, marginTop: 2 },

  modalInput: {
    borderWidth: 2,
    borderColor: Colors.primary,
    borderRadius: 14,
    padding: 14,
    fontSize: 28,
    fontWeight: '800',
    color: Colors.primary,
    textAlign: 'center',
    letterSpacing: 8,
    backgroundColor: Colors.primaryLight,
  },
  modalHint: { fontSize: 12, color: Colors.textMuted, textAlign: 'center' },

  modalBtns: { flexDirection: 'row', gap: 12 },
  modalCancelBtn: {
    flex: 1,
    padding: 14,
    borderRadius: 14,
    alignItems: 'center',
    backgroundColor: Colors.surfaceElevated,
    borderWidth: 1,
    borderColor: Colors.border,
  },
  modalCancelText: { color: Colors.textSecondary, fontWeight: '700', fontSize: 15 },
  modalSaveBtn: {
    flex: 1,
    padding: 14,
    borderRadius: 14,
    alignItems: 'center',
    backgroundColor: Colors.primary,
  },
  modalSaveText: { color: Colors.textInverse, fontWeight: '800', fontSize: 15 },
});
