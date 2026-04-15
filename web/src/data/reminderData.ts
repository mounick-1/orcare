export interface Reminder {
  id: number;
  title: string;
  time: string;
  isEnabled: boolean;
  icon: string;
  color: string;
  bgColor: string;
}

export const defaultReminders: Reminder[] = [
  { id: 1, title: 'Morning Brushing', time: '07:00', isEnabled: true, icon: '🪥', color: '#4A9EFF', bgColor: '#4A9EFF1A' },
  { id: 2, title: 'Night Brushing', time: '22:00', isEnabled: true, icon: '🌙', color: '#8B5CF6', bgColor: '#8B5CF61A' },
  { id: 3, title: 'Flossing', time: '22:15', isEnabled: true, icon: '🧵', color: '#22C55E', bgColor: '#22C55E1A' },
  { id: 4, title: 'Mouthwash (Morning)', time: '07:30', isEnabled: true, icon: '🧴', color: '#38BDF8', bgColor: '#38BDF81A' },
  { id: 5, title: 'Mouthwash (Evening)', time: '20:30', isEnabled: true, icon: '🧴', color: '#C084FC', bgColor: '#C084FC1A' },
  { id: 6, title: 'Tongue Cleaning', time: '07:15', isEnabled: true, icon: '👅', color: '#F59E0B', bgColor: '#F59E0B1A' },
  { id: 7, title: 'Gum Massage', time: '07:20', isEnabled: true, icon: '💆', color: '#F472B6', bgColor: '#F472B61A' },
  { id: 8, title: 'Water Intake', time: '13:00', isEnabled: true, icon: '💧', color: '#29D9A7', bgColor: '#29D9A71A' },
  { id: 9, title: 'Sugar-Free Rinse', time: '14:00', isEnabled: true, icon: '🌀', color: '#C084FC', bgColor: '#C084FC1A' },
  { id: 10, title: 'Weekly Check-up', time: '10:00', isEnabled: true, icon: '📅', color: '#FF6B6B', bgColor: '#FF6B6B1A' },
];
