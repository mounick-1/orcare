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
      navigation.replace('LanguageSelection');
    }, 2200);
    return () => clearTimeout(t);
  }, []);

  return (
    <View style={styles.container}>
      {/* Ambient glow blobs */}
      <View style={styles.glowTop} />
      <View style={styles.glowBottom} />

      <View style={styles.inner}>
        {/* Gemini-style sparkle logo */}
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

        {/* Gemini badge */}
        <View style={styles.geminiBadge}>
          <Text style={styles.geminiStar}>✦</Text>
          <Text style={styles.geminiText}>Powered by Gemini AI</Text>
        </View>
      </View>

      <View style={styles.footer}>
        {/* Loading dots */}
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

  // Ambient glow
  glowTop: {
    position: 'absolute',
    top: -100,
    left: '50%',
    width: 300,
    height: 300,
    borderRadius: 150,
    backgroundColor: 'rgba(135,116,225,0.15)',
    transform: [{ translateX: -150 }],
  },
  glowBottom: {
    position: 'absolute',
    bottom: -80,
    right: -60,
    width: 200,
    height: 200,
    borderRadius: 100,
    backgroundColor: 'rgba(74,158,255,0.1)',
  },

  inner: { flex: 1, alignItems: 'center', justifyContent: 'center', gap: 20 },

  // Gemini-style nested ring logo
  logoWrap: {
    position: 'relative',
    marginBottom: 8,
  },
  outerRing: {
    width: 136,
    height: 136,
    borderRadius: 68,
    borderWidth: 1,
    borderColor: 'rgba(135,116,225,0.25)',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'rgba(135,116,225,0.04)',
  },
  middleRing: {
    width: 108,
    height: 108,
    borderRadius: 54,
    borderWidth: 1,
    borderColor: 'rgba(135,116,225,0.4)',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'rgba(135,116,225,0.08)',
  },
  logoBox: {
    width: 80,
    height: 80,
    borderRadius: 40,
    backgroundColor: Colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
    shadowColor: Colors.primary,
    shadowOffset: { width: 0, height: 0 },
    shadowOpacity: 0.6,
    shadowRadius: 24,
    elevation: 16,
  },
  logoStar: {
    fontSize: 40,
    color: Colors.textInverse,
    fontWeight: '300',
  },

  // Floating sparkles
  spark: {
    position: 'absolute',
    fontSize: 12,
    color: Colors.primary,
    opacity: 0.7,
  },

  appName: {
    fontSize: 42,
    fontWeight: '900',
    color: Colors.textPrimary,
    letterSpacing: 2,
  },
  tagline: {
    fontSize: 14,
    color: Colors.textSecondary,
    fontWeight: '400',
    letterSpacing: 0.5,
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
    borderColor: Colors.primary + '40',
    marginTop: 4,
  },
  geminiStar: { fontSize: 12, color: Colors.primary },
  geminiText: { fontSize: 12, color: Colors.primary, fontWeight: '600' },

  footer: { alignItems: 'center', gap: 12 },
  dots: { flexDirection: 'row', gap: 6 },
  dot: {
    width: 6,
    height: 6,
    borderRadius: 3,
    backgroundColor: Colors.border,
  },
  dotActive: {
    width: 24,
    height: 6,
    borderRadius: 3,
    backgroundColor: Colors.primary,
  },
  version: { fontSize: 11, color: Colors.textMuted },
});
