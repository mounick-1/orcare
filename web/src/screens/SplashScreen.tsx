import React, { useEffect } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { useAuth } from '../context/AuthContext';

type Props = NativeStackScreenProps<RootStackParamList, 'Splash'>;

export default function SplashScreen({ navigation }: Props) {
  const { token } = useAuth();

  useEffect(() => {
    const t = setTimeout(() => {
      navigation.replace(token ? 'MainTabs' : 'SignIn');
    }, 2200);
    return () => clearTimeout(t);
  }, []);

  return (
    <View style={styles.container}>
      {/* Ambient glow blobs */}
      <View style={styles.glowTop} />
      <View style={styles.glowBottom} />

      <View style={styles.inner}>
        {/* Nested ring logo */}
        <View style={styles.logoWrap}>
          <View style={styles.outerRing}>
            <View style={styles.middleRing}>
              <View style={styles.logoBox}>
                <Text style={styles.logoStar}>✦</Text>
              </View>
            </View>
          </View>
          {/* Floating sparkles */}
          <Text style={[styles.spark, { top: 8, right: 8 }]}>✦</Text>
          <Text style={[styles.spark, { bottom: 12, left: 6, fontSize: 8 }]}>✦</Text>
          <Text style={[styles.spark, { top: 20, left: 0, fontSize: 10 }]}>✦</Text>
        </View>

        <Text style={styles.appName}>ORCare</Text>
        <Text style={styles.tagline}>Your Oral Health Companion</Text>

        <View style={styles.geminiBadge}>
          <Text style={styles.geminiStar}>✦</Text>
          <Text style={styles.geminiText}>Powered by Gemini AI</Text>
        </View>
      </View>

      <View style={styles.footer}>
        <View style={styles.dots}>
          {[0, 1, 2].map((i) => (
            <View key={i} style={[styles.dot, i === 1 && styles.dotActive]} />
          ))}
        </View>
        <Text style={styles.version}>v1.0.0</Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.background,
    justifyContent: 'space-between',
    paddingVertical: 64,
    alignItems: 'center',
    overflow: 'hidden',
  },

  glowTop: {
    position: 'absolute',
    top: -100,
    left: '50%',
    width: 300,
    height: 300,
    borderRadius: 150,
    backgroundColor: 'rgba(37,99,235,0.10)',
    transform: [{ translateX: -150 }],
  },
  glowBottom: {
    position: 'absolute',
    bottom: -80,
    right: -60,
    width: 200,
    height: 200,
    borderRadius: 100,
    backgroundColor: 'rgba(34,197,94,0.08)',
  },

  inner: { flex: 1, alignItems: 'center', justifyContent: 'center', gap: 20 },

  logoWrap: { position: 'relative', marginBottom: 8 },
  outerRing: {
    width: 136,
    height: 136,
    borderRadius: 68,
    borderWidth: 1,
    borderColor: 'rgba(37,99,235,0.20)',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'rgba(37,99,235,0.04)',
  },
  middleRing: {
    width: 108,
    height: 108,
    borderRadius: 54,
    borderWidth: 1,
    borderColor: 'rgba(37,99,235,0.35)',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'rgba(37,99,235,0.07)',
  },
  logoBox: {
    width: 80,
    height: 80,
    borderRadius: 40,
    backgroundColor: Colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
    shadowColor: Colors.shadow,
    shadowOffset: { width: 0, height: 0 },
    shadowOpacity: 1,
    shadowRadius: 24,
    elevation: 16,
  },
  logoStar: { fontSize: 40, color: Colors.textInverse },

  spark: { position: 'absolute', fontSize: 12, color: Colors.primary, opacity: 0.6 },

  appName: {
    fontSize: 42,
    fontFamily: 'Inter_800ExtraBold',
    color: Colors.textPrimary,
    letterSpacing: 1,
  },
  tagline: {
    fontSize: 14,
    fontFamily: 'Inter_400Regular',
    color: Colors.textSecondary,
    letterSpacing: 0.3,
  },

  geminiBadge: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 6,
    backgroundColor: Colors.primaryLight,
    borderRadius: 20,
    paddingHorizontal: 14,
    paddingVertical: 6,
    borderWidth: 1,
    borderColor: Colors.primary + '30',
    marginTop: 4,
  },
  geminiStar: { fontSize: 12, color: Colors.primary },
  geminiText: { fontSize: 12, color: Colors.primary, fontFamily: 'Inter_600SemiBold' },

  footer: { alignItems: 'center', gap: 12 },
  dots: { flexDirection: 'row', gap: 6 },
  dot: { width: 6, height: 6, borderRadius: 3, backgroundColor: Colors.border },
  dotActive: { width: 24, height: 6, borderRadius: 3, backgroundColor: Colors.primary },
  version: { fontSize: 11, color: Colors.textMuted, fontFamily: 'Inter_400Regular' },
});
